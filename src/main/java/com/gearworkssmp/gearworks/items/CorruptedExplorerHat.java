package com.gearworkssmp.gearworks.items;

import com.gearworkssmp.gearworks.special.TrailRuinManager;
import com.google.common.collect.Multimap;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraft.world.gen.structure.Structure;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class CorruptedExplorerHat extends TrinketItem implements TrinketRenderer {
	private static final String NOTIFIED_STRUCTURES_KEY = "NotifiedStructures";

	public CorruptedExplorerHat(Settings settings) {
		super(settings);
	}

	public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
		return super.getModifiers(stack, slot, entity, uuid);
	}

	@Override
	public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if (entity instanceof AbstractClientPlayerEntity player) {
			ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
			TrinketRenderer.followBodyRotations(entity, (BipedEntityModel<LivingEntity>) contextModel);
			TrinketRenderer.translateToFace(matrices, (PlayerEntityModel<AbstractClientPlayerEntity>) contextModel, player, headYaw, headPitch - 5.0f);
			matrices.scale(-0.675f, -0.675f, 0.675f);
			matrices.translate(0f, -0.05f, 0.40f);
			itemRenderer.renderItem(entity, stack, ModelTransformationMode.HEAD, false, matrices, vertexConsumers, entity.getWorld(), light, OverlayTexture.DEFAULT_UV, 0);
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("item.gearworks.corrupted_explorer_hat.tooltip"));
		super.appendTooltip(stack, world, tooltip, context);
	}

	@Override
	public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
		World world = entity.getEntityWorld();
		if (world instanceof ServerWorld serverWorld) {
			if (!world.isClient && entity instanceof PlayerEntity player) {
				// Once every minute it runs the check
				if (world.random.nextInt(20 * 60) == 0) {
					for (BlockPos ruinPos : TrailRuinManager.getTrailRuins()) {
						if (ruinPos != null && !hasNotified(stack, ruinPos)) {
							markNotified(stack, ruinPos);
							String message = String.format("An interesting location has been found nearby: [%d, %d, %d]",
									ruinPos.getX(), ruinPos.getY(), ruinPos.getZ());
							player.sendMessage(Text.literal(message), false);
						}
					}
				}
			}
		}
		super.tick(stack, slot, entity);
	}

	private boolean hasNotified(ItemStack stack, BlockPos pos) {
		NbtCompound tag = stack.getOrCreateNbt();
		if (tag.contains(NOTIFIED_STRUCTURES_KEY)) {
			// Type 8 corresponds to strings in NBT
			NbtList list = tag.getList(NOTIFIED_STRUCTURES_KEY, 8);
			String posString = pos.getX() + "," + pos.getY() + "," + pos.getZ();
			for (int i = 0; i < list.size(); i++) {
				if (list.getString(i).equals(posString)) {
					return true;
				}
			}
		}
		return false;
	}

	private void markNotified(ItemStack stack, BlockPos pos) {
		NbtCompound tag = stack.getOrCreateNbt();
		NbtList list;
		if (tag.contains(NOTIFIED_STRUCTURES_KEY)) {
			list = tag.getList(NOTIFIED_STRUCTURES_KEY, 8);
		} else {
			list = new NbtList();
			tag.put(NOTIFIED_STRUCTURES_KEY, list);
		}
		String posString = pos.getX() + "," + pos.getY() + "," + pos.getZ();
		list.add(NbtString.of(posString));
	}
}
