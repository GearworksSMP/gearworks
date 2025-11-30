package com.gearworkssmp.gearworks.item;

import java.util.Random;
import java.util.random.RandomGenerator;

import com.gearworkssmp.gearworks.Gearworks;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;

public class ModMobSpawnModifier {
	public static void modifyMobSpawns() {
		boolean coinFlip;
		try {
			coinFlip = RandomGenerator.getDefault().nextInt(2) == 1;
		} catch (Throwable t) { // Fallback for environments where RandomGenerator default isn't available
			coinFlip = new Random().nextInt(2) == 1;
		}
		if (Gearworks.isCloseToHalloween() && coinFlip) {
			ServerEntityEvents.ENTITY_LOAD.register((Entity entity, ServerWorld world) -> {
				if (entity instanceof ZombieEntity) {
					entity.equipStack(EquipmentSlot.HEAD, ModItems.JACK_O_LANTERN.getDefaultStack());
				}
				if (entity instanceof SkeletonEntity) {
					entity.equipStack(EquipmentSlot.HEAD, ModItems.JACK_O_LANTERN.getDefaultStack());
				}
				if (entity instanceof CreeperEntity) {
					entity.equipStack(EquipmentSlot.HEAD, ModItems.JACK_O_LANTERN.getDefaultStack());
				}
			});
		}
	}
}
