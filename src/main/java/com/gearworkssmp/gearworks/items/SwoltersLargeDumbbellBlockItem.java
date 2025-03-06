package com.gearworkssmp.gearworks.items;

import com.google.common.collect.ImmutableMultimap;

import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class SwoltersLargeDumbbellBlockItem extends BlockItem {

	private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
	public SwoltersLargeDumbbellBlockItem(Block block, Settings settings) {
		super(block, settings);
		ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
		// Add a low base attack damage (e.g., +2.0 damage)
		builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE,
				new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", 7.0, EntityAttributeModifier.Operation.ADDITION));
		// And a relatively low attack speed (which helps make it feel heavy)
		builder.put(EntityAttributes.GENERIC_ATTACK_SPEED,
				new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -3.6, EntityAttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		if (slot == EquipmentSlot.MAINHAND) {
			return attributeModifiers;
		}
		return super.getAttributeModifiers(slot);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		return super.useOnBlock(context);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (!world.isClient && entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			if (livingEntity.getMainHandStack() == stack || livingEntity.getOffHandStack() == stack) {
				StatusEffectInstance slowness = new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 1, false, false, false);
				StatusEffectInstance jumpReduction = new StatusEffectInstance(StatusEffects.JUMP_BOOST, 20, 252, false, false, false);
				livingEntity.addStatusEffect(slowness);
				livingEntity.addStatusEffect(jumpReduction);

				// players get a strength buff if holding the item for 30 seconds
				int holdTime = stack.getOrCreateNbt().getInt("HoldTime");
				holdTime++;
				stack.getOrCreateNbt().putInt("HoldTime", holdTime);

				if (holdTime == 600) {
					StatusEffectInstance strengthEffect = new StatusEffectInstance(StatusEffects.STRENGTH, 1200, 3, false, true, true);
					livingEntity.addStatusEffect(strengthEffect);
					stack.getOrCreateNbt().putInt("HoldTime", 0);
				}
			} else {
				// Reset the counter if the item isn't held.
				stack.getOrCreateNbt().putInt("HoldTime", 0);
			}
		}
		super.inventoryTick(stack, world, entity, slot, selected);
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!attacker.getWorld().isClient) {
			// Calculate the difference in position to determine knockback direction.
			double diffX = target.getX() - attacker.getX();
			double diffZ = target.getZ() - attacker.getZ();
			double magnitude = Math.sqrt(diffX * diffX + diffZ * diffZ);
			if (magnitude != 0) {
				// Apply strong knockback. Adjust knockbackStrength as desired.
				float knockbackStrength = 2.5F;
				target.takeKnockback(knockbackStrength, -1 * (diffX / magnitude), -1 * (diffZ / magnitude));
			}
		}
		return super.postHit(stack, target, attacker);
	}
}
