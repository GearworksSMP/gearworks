package com.gearworkssmp.gearworks.item;

import java.util.Random;
import java.util.random.RandomGenerator;

import com.gearworkssmp.gearworks.Gearworks;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;

public class ModMobSpawnModifier {
	public static void modifyMobSpawns() {
		boolean coinFlip;
		try {
			coinFlip = RandomGenerator.getDefault().nextInt(2) == 1;
		} catch (Throwable t) { // Fallback for environments where RandomGenerator default isn't available
			coinFlip = new Random().nextInt(2) == 1;
		}
		if (Gearworks.isCloseToHalloween() && coinFlip) {
			LivingEntityEvents.ON_JOIN_WORLD.register((Entity entity, World world, boolean loadedFromDisk) -> {
				if (entity instanceof ZombieEntity) {
					entity.equipStack(EquipmentSlot.HEAD, ModItems.JACK_O_LANTERN.getDefaultStack());
				}
				if (entity instanceof SkeletonEntity) {
					entity.equipStack(EquipmentSlot.HEAD, ModItems.JACK_O_LANTERN.getDefaultStack());
				}
				if (entity instanceof CreeperEntity) {
					entity.equipStack(EquipmentSlot.HEAD, ModItems.JACK_O_LANTERN.getDefaultStack());
				}
				return true;
			});
		}
	}
}
