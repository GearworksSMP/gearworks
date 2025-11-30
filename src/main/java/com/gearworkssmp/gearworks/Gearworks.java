package com.gearworkssmp.gearworks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.gearworkssmp.gearworks.events.ScheduledSpawn;

import com.gearworkssmp.gearworks.item.ModBlockEntities;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import com.gearworkssmp.gearworks.logging.BlockChangeAggregator;
import com.gearworkssmp.gearworks.logging.BlockChangeEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import net.minecraft.util.math.random.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gearworkssmp.gearworks.events.PlayerDeathCallback;
import com.gearworkssmp.gearworks.item.ModItems;
import com.gearworkssmp.gearworks.item.ModLootTableModifiers;
import com.gearworkssmp.gearworks.item.ModMobSpawnModifier;
import com.simibubi.create.Create;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

public class Gearworks implements ModInitializer {
	public static final String ID = "gearworks";
	public static final String NAME = "Gearworks";
	public static final String CLIENT_CHANNEL = "client_channel";
	public static final String SWITCH_SERVER_MESSAGE = "SWITCHSERVER";
	public static final Identifier SWITCH_SERVER_EVENT_ID = new Identifier(ID, CLIENT_CHANNEL);
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
	public static final Map<UUID, Long> SCHEDULED_REVERTS = new HashMap<>();
	public static final List<ScheduledSpawn> SCHEDULED_CREEPER_SPAWNS = new ArrayList<>();
	public static final Map<UUID, Long> PINATA_BURSTS = new HashMap<>();
	public static final Map<UUID, Long> SCHEDULED_VEX_DESPAWN = new HashMap<>();
	public static final String[] BETA_PARTICIPANTS = {
			"uberswe",
			"Goofyahhhcat",
			"SilentDemon678",
			"ItzAmbie",
			"baconmemes",
			"GamerLeonLP",
			"Ishnula",
			"TrentTinkers",
			"kivatornado",
			"BluetoothBread",
			"Guybrrush",
			"Phoenix10_9",
			"Arcadiuz",
			"BaniaKac",
			"kieranstinkyfeet",
			"Prancing_Blackie",
			"Dante_Dravon",
			"jcwnnr",
			"RobloticGuy",
			"Racakata",
			"Caztheaxolotl",
			"SeanieMac99",
			"ZALKIE",
			"_Kuha",
			"ViperSoup",
			"prancingpachy"
	};
	public static final SoundEvent LASERSWORD_MISS_1 = SoundEvent.of(new Identifier(ID, "lasersword_miss_1"));
	public static final SoundEvent LASERSWORD_MISS_2 = SoundEvent.of(new Identifier(ID, "lasersword_miss_2"));
	public static final SoundEvent LASERSWORD_MISS_3 = SoundEvent.of(new Identifier(ID, "lasersword_miss_3"));
	public static final SoundEvent[] LASERSWORD_MISS_SOUNDS = new SoundEvent[]{
			LASERSWORD_MISS_1, LASERSWORD_MISS_2, LASERSWORD_MISS_3
	};

	public static final SoundEvent LASERSWORD_HIT_1 = SoundEvent.of(new Identifier(ID, "lasersword_hit_1"));
	public static final SoundEvent LASERSWORD_HIT_2 = SoundEvent.of(new Identifier(ID, "lasersword_hit_2"));
	public static final SoundEvent[] LASERSWORD_HIT_SOUNDS = new SoundEvent[]{
			LASERSWORD_HIT_1, LASERSWORD_HIT_2
	};

	@Override
	public void onInitialize() {
		LOGGER.info("Create addon mod [{}] is loading!", NAME);
		ModItems.registerModItems();
		ModBlockEntities.registerBlockEntities();
		ModLootTableModifiers.modifyLootTables();
		ModMobSpawnModifier.modifyMobSpawns();
		registerEvents();

		// Initialize DB and event logging
		try {
			BlockChangeAggregator.init();
			BlockChangeEvents.register();
		} catch (Exception e) {
			LOGGER.error("Failed to initialize block logging", e);
		}
		// Flush on server stop to avoid losing events
		ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
			try { BlockChangeAggregator.flushAll(); } catch (Exception e) { LOGGER.error("Block log final flush failed", e); }
		});

		Registry.register(Registries.SOUND_EVENT, new Identifier(ID, "lasersword_miss_1"), LASERSWORD_MISS_1);
		Registry.register(Registries.SOUND_EVENT, new Identifier(ID, "lasersword_miss_2"), LASERSWORD_MISS_2);
		Registry.register(Registries.SOUND_EVENT, new Identifier(ID, "lasersword_miss_3"), LASERSWORD_MISS_3);
		Registry.register(Registries.SOUND_EVENT, new Identifier(ID, "lasersword_hit_1"), LASERSWORD_HIT_1);
		Registry.register(Registries.SOUND_EVENT, new Identifier(ID, "lasersword_hit_2"), LASERSWORD_HIT_2);

		ServerTickEvents.END_SERVER_TICK.register(server -> {
			// Periodic flush of aggregated block events
			try { BlockChangeAggregator.flushIfDue(); } catch (Exception e) { LOGGER.error("Block log periodic flush failed", e); }
			long currentTime = server.getOverworld().getTime();
			// Get current game time from the overworld (or any stable dimension)

			// We'll collect any players whose revert time has passed
			List<UUID> toRevert = new ArrayList<>();

			for (Map.Entry<UUID, Long> entry : SCHEDULED_REVERTS.entrySet()) {
				UUID playerId = entry.getKey();
				long revertTime = entry.getValue();

				if (currentTime >= revertTime) {
					// Time to revert the scale
					ServerPlayerEntity player = server.getPlayerManager().getPlayer(playerId);
					if (player != null) {
						revertScale(player);
					}
					toRevert.add(playerId);
				}
			}

			// Remove completed tasks from the map
			for (UUID id : toRevert) {
				SCHEDULED_REVERTS.remove(id);
			}

			List<UUID> toRemove = new ArrayList<>();

			for (Map.Entry<UUID, Long> entry : PINATA_BURSTS.entrySet()) {
				UUID sheepId = entry.getKey();
				long burstTime = entry.getValue();

				if (currentTime >= burstTime) {
					// Time to burst this pinata
					// Locate the sheep and burst it
					for (ServerWorld world : server.getWorlds()) {
						var entity = world.getEntity(sheepId);
						if (entity instanceof SheepEntity sheep) {
							burstPinata(sheep);
							toRemove.add(sheepId);
							break;
						}
					}
				}
			}

			// Remove completed tasks
			for (UUID id : toRemove) {
				PINATA_BURSTS.remove(id);
			}

			toRemove = new ArrayList<>();

			for (Map.Entry<UUID, Long> entry : SCHEDULED_VEX_DESPAWN.entrySet()) {
				UUID vexId = entry.getKey();
				long despawnTime = entry.getValue();
				if (currentTime >= despawnTime) {
					// Time to remove this Vex
					for (ServerWorld world : server.getWorlds()) {
						var entity = world.getEntity(vexId);
						if (entity != null) {
							entity.remove(Entity.RemovalReason.DISCARDED);
						}
					}
					toRemove.add(vexId);
				}
			}

			for (UUID id : toRemove) {
				SCHEDULED_VEX_DESPAWN.remove(id);
			}

			List<ScheduledSpawn> toSpawnNow = new ArrayList<>();

			// Check scheduled spawns
			for (ScheduledSpawn task : SCHEDULED_CREEPER_SPAWNS) {
				if (currentTime >= task.spawnTime) {
					toSpawnNow.add(task);
				}
			}

			// Spawn and remove them from the main list
			for (ScheduledSpawn task : toSpawnNow) {
				spawnCreeper(server, task);
				SCHEDULED_CREEPER_SPAWNS.remove(task);
			}
		});
	}

	public static void registerEvents() {
		PlayerDeathCallback.EVENT.register(Gearworks::onPlayerDeathEvent);
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ServerPlayerEntity player = handler.getPlayer();
			sendServerJoinEventToClient(player);
			revertScale(player);
		});
	}

	private static void sendServerJoinEventToClient(ServerPlayerEntity player) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeString(SWITCH_SERVER_MESSAGE);

		// Send the packet to the specific player
		ServerPlayNetworking.send(player, SWITCH_SERVER_EVENT_ID, buf);
	}

	public static void onPlayerDeathEvent(ServerPlayerEntity player, DamageSource source) {
		LOGGER.info("Player died: {}", player.getName().getString());
		player.totalExperience = 0;
		player.experienceLevel = 0;
		player.experienceProgress = 0.0f;
	}

	public static boolean isBetaParticipant(PlayerEntity player) {
		for (String name : BETA_PARTICIPANTS) {
			if (name.equalsIgnoreCase(player.getGameProfile().getName())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isCloseToHalloween() {
		LocalDate today = LocalDate.now();
		// Define the start and end dates for the range
		LocalDate startDate = LocalDate.of(today.getYear(), 10, 1);  // October 1st
		LocalDate endDate = LocalDate.of(today.getYear(), 11, 5);    // November 5th

		// Check if today is within the range (inclusive)
		return (today.isEqual(startDate) || today.isAfter(startDate)) &&
				(today.isEqual(endDate) || today.isBefore(endDate));
	}

	public static void revertScale(PlayerEntity player) {
		ScaleData scaleData = ScaleTypes.BASE.getScaleData(player);
		scaleData.setTargetScale(1.0f);
		scaleData.setScaleTickDelay(60);

		// Remove Speed III effect if present
		if (player.hasStatusEffect(StatusEffects.SPEED)) {
			player.removeStatusEffect(StatusEffects.SPEED);
		}
	}

	private void spawnCreeper(MinecraftServer server, ScheduledSpawn task) {
		ServerWorld world = server.getOverworld();

		// Locate the player if needed
		ServerPlayerEntity player = server.getPlayerManager().getPlayer(task.playerUuid);

		if (player == null) {
			return; // Player not online, skip
		}

		CreeperEntity creeper = EntityType.CREEPER.create(world);
		if (creeper != null) {
			creeper.refreshPositionAndAngles(task.x, task.y, task.z, world.random.nextFloat() * 360F, 0.0F);
			world.spawnEntity(creeper);
			creeper.setTarget(player);
		}
	}

	public static void burstPinata(SheepEntity sheep) {
		ServerWorld world = (ServerWorld) sheep.getWorld();
		double x = sheep.getX();
		double y = sheep.getY();
		double z = sheep.getZ();

		// Kill/remove the sheep
		sheep.remove(Entity.RemovalReason.DISCARDED);

		// Drop some loot
		// Define a set of possible items
		Item[] lootPool = {
				Items.APPLE, Items.GOLD_INGOT, Items.FEATHER,
				Items.EMERALD, Items.COOKIE, Items.BREAD, ModItems.TRICK_OR_TREAT_CANDY
		};

		Random random = world.getRandom();
		int itemCount = 5 + random.nextInt(6); // 5 to 10 items

		for (int i = 0; i < itemCount; i++) {
			Item chosen = lootPool[random.nextInt(lootPool.length)];
			ItemStack stack = new ItemStack(chosen, 1 + random.nextInt(3)); // 1-3 of chosen item
			ItemEntity itemEntity = new ItemEntity(world, x, y, z, stack);
			world.spawnEntity(itemEntity);
		}

		// Optional: spawn particles or play a sound
		world.playSound(null, x, y, z, SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST, SoundCategory.PLAYERS, 1.0F, 1.0F);
	}
}
