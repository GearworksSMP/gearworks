package com.gearworkssmp.gearworks;
import com.gearworkssmp.gearworks.blocks.renderers.SwoltersLargeDumbbellBlockEntityRenderer;
import com.gearworkssmp.gearworks.item.ModBlockEntities;
import com.gearworkssmp.gearworks.item.ModItems;

import com.gearworkssmp.gearworks.items.renderers.CogWeightRenderer;

import com.gearworkssmp.gearworks.items.renderers.LaserSwordRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.hit.HitResult;

public class GearworksClient implements ClientModInitializer {
	private boolean wasAttacking = false;

	@Override
	public void onInitializeClient() {
		BuiltinItemRendererRegistry.INSTANCE.register(ModItems.SWOLTERS_LARGE_DUMBBELL, new CogWeightRenderer());

		BlockEntityRendererRegistry.register(
				ModBlockEntities.SWOLTERS_LARGE_DUMBBELL_BLOCK_ENTITY,
				SwoltersLargeDumbbellBlockEntityRenderer::new
		);

		BuiltinItemRendererRegistry.INSTANCE.register(ModItems.LASERSWORD, new LaserSwordRenderer());

		ClientPlayNetworking.registerGlobalReceiver(Gearworks.SWITCH_SERVER_EVENT_ID, (client, handler, buf, responseSender) -> {
			String message = buf.readString(); // Read the message from the server

			// Handle the packet on the client thread
			client.execute(() -> {
				// Execute any client-side logic with the received data
				if (message.equals(Gearworks.SWITCH_SERVER_MESSAGE)) {
					MinecraftServer server = client.getServer();
					if (server != null) {
						onJoinServer(server);
					}
				}
			});
		});

		// Listen for left-click (attack key) events to handle miss sounds.
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			MinecraftClient mc = MinecraftClient.getInstance();
			if (mc.player == null || mc.world == null) return;

			boolean isAttacking = mc.options.attackKey.isPressed();
			// Detect a new left-click (transition from not pressed to pressed).
			if (!wasAttacking && isAttacking) {
				ItemStack stack = mc.player.getMainHandStack();
				if (stack.getItem() == ModItems.LASERSWORD) {
					// Perform a ray trace from the player's perspective.
					HitResult hitResult = mc.player.raycast(5.0D, mc.getTickDelta(), false);
					if (hitResult.getType() == HitResult.Type.MISS || hitResult.getType() == HitResult.Type.BLOCK) {
						// Play a random miss sound.
						int index = mc.world.random.nextInt(Gearworks.LASERSWORD_MISS_SOUNDS.length);
						mc.world.playSound(mc.player.getX(), mc.player.getY(), mc.player.getZ(),
								Gearworks.LASERSWORD_MISS_SOUNDS[index],
								net.minecraft.sound.SoundCategory.PLAYERS, 1.0F, 1.0F, false);
					}
				}
			}
			wasAttacking = isAttacking;
		});
	}

	private void onJoinServer(MinecraftServer server) {
		// Do nothing for now
	}

}
