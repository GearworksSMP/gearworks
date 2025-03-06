package com.gearworkssmp.gearworks.items.renderers;

import com.gearworkssmp.gearworks.items.models.LaserSwordModel;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

import java.awt.*;

// TODO this currently does not work but it could be nice for the future to add some glow to the Laser Sword
public class LaserSwordRenderer implements BuiltinItemRenderer {

	// Load your Blockbench model (this is placeholder code).
	private final LaserSwordModel model;

	public LaserSwordRenderer() {
		// Replace with your actual model loading code.
		this.model = new LaserSwordModel();
	}

	@Override
	public void render(ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		// Render the base (non-glowing) parts of the model.
		model.renderBase(matrices, vertexConsumers, light, overlay);

		// Calculate a dynamic hue value based on system time (cycles every 3 seconds)
		float hue = (System.currentTimeMillis() % 3000L) / 3000f;
		int glowColor = Color.HSBtoRGB(hue, 1.0f, 1.0f);

		// Render the glowing top part of the model using the computed glowColor.
		model.renderGlow(matrices, vertexConsumers, light, overlay, glowColor);
	}
}
