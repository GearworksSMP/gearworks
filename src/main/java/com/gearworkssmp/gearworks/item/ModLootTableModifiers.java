package com.gearworkssmp.gearworks.item;

import com.gearworkssmp.gearworks.Gearworks;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTableModifiers {
	private static final Identifier WARDEN_ID = new Identifier("minecraft", "entities/warden");
	private static final Identifier ENDERMAN_ID = new Identifier("minecraft", "entities/enderman");
	private static final Identifier ENDERMITE_ID = new Identifier("minecraft", "entities/endermite");
	private static final Identifier GHAST_ID = new Identifier("minecraft", "entities/ghast");
	private static final Identifier PHANTOM_ID = new Identifier("minecraft", "entities/phantom");
	private static final Identifier ZOGLIN_ID = new Identifier("minecraft", "entities/zoglin");
	private static final Identifier ZOMBIE_VILLAGER_ID = new Identifier("minecraft", "entities/zombie_villager");
	private static final Identifier ZOMBIE = new Identifier("minecraft", "entities/zombie");
	private static final Identifier SKELETON = new Identifier("minecraft", "entities/skeleton");
	private static final Identifier CREEPER = new Identifier("minecraft", "entities/creeper");
	private static final Identifier STRAY_ID = new Identifier("minecraft", "entities/stray");
	private static final Identifier TRADER_LLAMA = new Identifier("minecraft", "entities/trader_llama");

	public static void modifyLootTables() {
		LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (WARDEN_ID.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1))
						.conditionally(RandomChanceLootCondition.builder(1F))
						.with(ItemEntry.builder(ModItems.ECHOING_CORE))
						.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F)).build());
				tableBuilder.pool(poolBuilder.build());
			}
			if (ENDERMAN_ID.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1))
						.conditionally(RandomChanceLootCondition.builder(0.8F * halloweenModifier()))
						.with(ItemEntry.builder(ModItems.ENDER_FRAGMENT))
						.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F)).build());
				tableBuilder.pool(poolBuilder.build());
			}
			if (GHAST_ID.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1))
						.conditionally(RandomChanceLootCondition.builder(halloweenModifier()))
						.with(ItemEntry.builder(ModItems.ECTOPLASM))
						.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F)).build());
				tableBuilder.pool(poolBuilder.build());
			}
			if (PHANTOM_ID.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1))
						.conditionally(RandomChanceLootCondition.builder(0.5F * halloweenModifier()))
						.with(ItemEntry.builder(ModItems.ECTOPLASM))
						.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F)).build());
				tableBuilder.pool(poolBuilder.build());
			}
			if (ZOGLIN_ID.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1))
						.conditionally(RandomChanceLootCondition.builder(halloweenModifier()))
						.with(ItemEntry.builder(ModItems.SHADOW_ESSENCE))
						.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F)).build());
				tableBuilder.pool(poolBuilder.build());
			}
			if (ZOMBIE_VILLAGER_ID.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1))
						.conditionally(RandomChanceLootCondition.builder(halloweenModifier()))
						.with(ItemEntry.builder(ModItems.SHADOW_ESSENCE))
						.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F)).build());
				tableBuilder.pool(poolBuilder.build());
			}
			if (ENDERMITE_ID.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1))
						.conditionally(RandomChanceLootCondition.builder(0.5F))
						.with(ItemEntry.builder(ModItems.SHADOW_ESSENCE))
						.with(ItemEntry.builder(ModItems.ECTOPLASM))
						.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F)).build());
				tableBuilder.pool(poolBuilder.build());
			}
			if (STRAY_ID.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1))
						.conditionally(RandomChanceLootCondition.builder(0.8F * halloweenModifier()))
						.with(ItemEntry.builder(ModItems.ECTOPLASM))
						.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F)).build());
				tableBuilder.pool(poolBuilder.build());
			}
			if (TRADER_LLAMA.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1))
						.conditionally(RandomChanceLootCondition.builder(0.8F * halloweenModifier()))
						.with(ItemEntry.builder(ModItems.ECTOPLASM))
						.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F)).build());
				tableBuilder.pool(poolBuilder.build());
			}
			if (Gearworks.isCloseToHalloween()) {
				if (ZOMBIE.equals(id)) {
					LootPool.Builder poolBuilder = LootPool.builder()
							.rolls(ConstantLootNumberProvider.create(1))
							.conditionally(RandomChanceLootCondition.builder(0.5F))
							.with(ItemEntry.builder(ModItems.JACK_O_LANTERN))
							.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F)).build());
					tableBuilder.pool(poolBuilder.build());
				}
				if (SKELETON.equals(id)) {
					LootPool.Builder poolBuilder = LootPool.builder()
							.rolls(ConstantLootNumberProvider.create(1))
							.conditionally(RandomChanceLootCondition.builder(0.5F))
							.with(ItemEntry.builder(ModItems.JACK_O_LANTERN))
							.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F)).build());
					tableBuilder.pool(poolBuilder.build());
				}
				if (CREEPER.equals(id)) {
					LootPool.Builder poolBuilder = LootPool.builder()
							.rolls(ConstantLootNumberProvider.create(1))
							.conditionally(RandomChanceLootCondition.builder(0.5F))
							.with(ItemEntry.builder(ModItems.JACK_O_LANTERN))
							.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F)).build());
					tableBuilder.pool(poolBuilder.build());
				}
			}
			if (id.toString().contains("archaeology") && !id.toString().contains("common") && !id.toString().contains("generic")) {
				float chance = 0.2F;
				if (id.toString().contains("rare")) {
					chance = 1.0F;
				}
				LootPool.Builder poolBuilder = LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1))
						.conditionally(RandomChanceLootCondition.builder(chance))
						.with(ItemEntry.builder(ModItems.EXPLORER_HAT))
						.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F)).build());
				tableBuilder.pool(poolBuilder.build());
			}

		}));
	}

	private static float halloweenModifier() {
		if (Gearworks.isCloseToHalloween()) {
			return 1.0f;
		} else {
			return 0.1f;
		}
	}
}
