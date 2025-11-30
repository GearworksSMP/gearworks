package com.gearworkssmp.gearworks.items;

import com.gearworkssmp.gearworks.Gearworks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class LaserSword extends SwordItem {

	public LaserSword(ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings) {
		super(material, attackDamage, attackSpeed, settings);
	}

	// Override use action if needed (here we simply return success)
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		return TypedActionResult.success(user.getStackInHand(hand));
	}

	@Override
	public boolean postHit(ItemStack stack, net.minecraft.entity.LivingEntity target, net.minecraft.entity.LivingEntity attacker) {
		// Play laser sword hit sound when hitting an entity
		if (attacker.getWorld().isClient) {
			World world = attacker.getWorld();
			int index = world.random.nextInt(Gearworks.LASERSWORD_HIT_SOUNDS.length);
			world.playSound(attacker.getX(), attacker.getY(), attacker.getZ(),
					Gearworks.LASERSWORD_HIT_SOUNDS[index],
					SoundCategory.PLAYERS, 1.0F, 1.0F, false);
		}
		return super.postHit(stack, target, attacker);
	}
}
