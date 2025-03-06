package com.gearworkssmp.gearworks.items.models;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;

public class LaserSwordModel {

	/**
	 * Renders the base (non-glowing) parts of the model.
	 */
	public void renderBase(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		// Insert your model’s base rendering code here.
	}

	/**
	 * Renders the glowing top part of the model.
	 *
	 * @param glowColor the color tint to apply (as an ARGB integer)
	 */
	public void renderGlow(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, int glowColor) {
		// Insert your model’s glow rendering code here.
		// You may use the glowColor to tint the emissive texture.
	}
}
