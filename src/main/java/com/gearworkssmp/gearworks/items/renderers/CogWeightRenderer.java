package com.gearworkssmp.gearworks.items.renderers;

import com.gearworkssmp.gearworks.items.models.CogWeight;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRenderer;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class CogWeightRenderer implements BuiltinItemRenderer {
	private final CogWeight model;
	private static final Identifier TEXTURE = new Identifier("gearworks", "textures/item/cog_weight.png");

	public CogWeightRenderer() {
		ModelPart root = CogWeight.getTexturedModelData().createModel();
		this.model = new CogWeight(root);
	}

	@Override
	public void render(ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		float scale = 1.0F;
		matrices.scale(scale, scale, scale);
		// Positive y moves the item upwards from the hand, the direction of the thumb if thumbs up (in front of the player)
		// Positive x moves the item to the right
		matrices.translate(0.0F, -0.5F, 0.5F);
		//matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180F));
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(TEXTURE));
		this.model.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
		matrices.pop();
	}
}
