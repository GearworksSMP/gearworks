package com.gearworkssmp.gearworks.blocks.renderers;

import com.gearworkssmp.gearworks.Gearworks;
import com.gearworkssmp.gearworks.blocks.SwoltersLargeDumbbellBlock;
import com.gearworkssmp.gearworks.blocks.entities.SwoltersLargeDumbbellBlockEntity;
import com.gearworkssmp.gearworks.blocks.partials.DumbbellPart;
import com.gearworkssmp.gearworks.items.models.CogWeight;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

public class SwoltersLargeDumbbellBlockEntityRenderer implements BlockEntityRenderer<SwoltersLargeDumbbellBlockEntity> {
	private final CogWeight model;
	private static final Identifier TEXTURE = new Identifier("gearworks", "textures/item/cog_weight.png");

	public SwoltersLargeDumbbellBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
		// Initialize the model from your exported model data.
		ModelPart root = CogWeight.getTexturedModelData().createModel();
		this.model = new CogWeight(root);
	}

	@Override
	public void render(SwoltersLargeDumbbellBlockEntity entity, float tickDelta, MatrixStack matrices,
					   VertexConsumerProvider vertexConsumers, int light, int overlay) {
		Direction facing = entity.getCachedState().get(SwoltersLargeDumbbellBlock.FACING);
		// Only render the LEFT part.
		if ((facing == Direction.SOUTH || facing == Direction.WEST) && entity.getCachedState().get(SwoltersLargeDumbbellBlock.PART) == DumbbellPart.RIGHT) {
			return;
		} else if ((facing == Direction.NORTH || facing == Direction.EAST) && entity.getCachedState().get(SwoltersLargeDumbbellBlock.PART) == DumbbellPart.LEFT) {
			return;
		}
		matrices.push();
		// Retrieve the block's facing property from its state.
		// The asRotation() method returns an angle (in degrees) corresponding to the direction.
		float rotationDegrees = facing.asRotation();

		// Apply a rotation around the Y axis based on the block's facing.
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotationDegrees));

		// Adjust these transforms so that the placed block aligns as desired.
		if (entity.getCachedState().get(SwoltersLargeDumbbellBlock.PART) == DumbbellPart.RIGHT) {
			matrices.translate(0.0F, 1.5F, -0.5F);
		} else {
			matrices.translate(0.0F, 1.5F, 0.5F);
		}
		//float scale = 1F;
		//matrices.scale(scale, scale, scale);
		matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180F));

		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(TEXTURE));
		int fullBright = 15728880;
		this.model.render(matrices, vertexConsumer, fullBright, overlay, 1F, 1F, 1F, 1F);
		matrices.pop();
	}
}
