package com.gearworkssmp.gearworks.item;

import com.gearworkssmp.gearworks.blocks.entities.SwoltersLargeDumbbellBlockEntity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
	public static BlockEntityType<SwoltersLargeDumbbellBlockEntity> SWOLTERS_LARGE_DUMBBELL_BLOCK_ENTITY;

	public static void registerBlockEntities() {
		SWOLTERS_LARGE_DUMBBELL_BLOCK_ENTITY = Registry.register(
				Registries.BLOCK_ENTITY_TYPE,
				new Identifier("gearworks", "swolters_large_dumbbell"),
				BlockEntityType.Builder.create(
						SwoltersLargeDumbbellBlockEntity::new, ModBlocks.SWOLTERS_LARGE_DUMBBELL_BLOCK
				).build(null)
		);
	}
}
