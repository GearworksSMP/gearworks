package com.gearworkssmp.gearworks.items.materials;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class LaserSwordMaterial implements ToolMaterial {
	@Override
	public int getDurability() {
		return 2031; // Adjust as needed
	}

	@Override
	public float getMiningSpeedMultiplier() {
		return 8.0F;
	}

	@Override
	public float getAttackDamage() {
		return 10.0F;
	}

	@Override
	public int getMiningLevel() {
		return 3;
	}

	@Override
	public int getEnchantability() {
		return 15;
	}

	@Override
	public Ingredient getRepairIngredient() {
		// Use a custom repair item if desired.
		return Ingredient.ofItems(Items.NETHERITE_INGOT);
	}
}
