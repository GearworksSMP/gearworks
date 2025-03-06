package com.gearworkssmp.gearworks.items;

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
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ExplorerHat extends TrinketItem implements TrinketRenderer {
	public ExplorerHat(Settings settings) {
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
			TrinketRenderer.translateToFace(matrices, (PlayerEntityModel<AbstractClientPlayerEntity>) contextModel, player, headYaw, headPitch);
			matrices.scale(-0.625f,-0.625f,0.625f);
			matrices.translate(0f,0f,0.45f);
			itemRenderer.renderItem(entity, stack, ModelTransformationMode.HEAD, false, matrices, vertexConsumers, entity.getWorld(), light, OverlayTexture.DEFAULT_UV, 0);
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("item.gearworks.explorer_hat.tooltip"));
		super.appendTooltip(stack, world, tooltip, context);
	}
}
