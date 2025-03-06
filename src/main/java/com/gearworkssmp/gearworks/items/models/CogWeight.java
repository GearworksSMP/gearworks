package com.gearworkssmp.gearworks.items.models;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class CogWeight extends EntityModel<Entity> {
	private final ModelPart cogweight;
	private final ModelPart bone;
	private final ModelPart bone2;

	public CogWeight(ModelPart root) {
		this.cogweight = root.getChild("cogweight");
		this.bone = this.cogweight.getChild("bone");
		this.bone2 = this.cogweight.getChild("bone2");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData cogweight = modelPartData.addChild("cogweight", ModelPartBuilder.create().uv(0, 29).cuboid(-2.0F, -3.0F, -3.0F, 4.0F, 4.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, 16.5F, 1.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData bone = cogweight.addChild("bone", ModelPartBuilder.create().uv(28, 0).cuboid(0.0F, -11.0F, -2.0F, 8.0F, 8.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(2.0F, -9.0F, -6.0F, 4.0F, 4.0F, 10.0F, new Dilation(0.0F))
				.uv(0, 14).cuboid(-2.0F, -13.0F, -1.0F, 12.0F, 12.0F, 2.0F, new Dilation(0.0F))
				.uv(28, 12).cuboid(7.5F, -8.5F, -1.5F, 5.0F, 3.0F, 3.0F, new Dilation(0.0F))
				.uv(40, 40).cuboid(2.5F, -3.5F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F))
				.uv(28, 18).cuboid(-4.5F, -8.5F, -1.5F, 5.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 6.0F, -7.0F));

		ModelPartData cube_r1 = bone.addChild("cube_r1", ModelPartBuilder.create().uv(40, 32).cuboid(-1.5F, -2.5F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(8.6391F, -2.4467F, 0.0F, 0.0F, 3.1416F, -0.7854F));

		ModelPartData cube_r2 = bone.addChild("cube_r2", ModelPartBuilder.create().uv(28, 40).cuboid(-0.5F, 4.0F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(3.25F, -7.75F, 0.0F, 0.0F, 0.0F, 0.7854F));

		ModelPartData cube_r3 = bone.addChild("cube_r3", ModelPartBuilder.create().uv(40, 24).cuboid(-0.5F, -9.0F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(3.25F, -6.25F, 0.0F, 0.0F, 0.0F, -0.7854F));

		ModelPartData cube_r4 = bone.addChild("cube_r4", ModelPartBuilder.create().uv(28, 32).cuboid(-1.5F, -2.5F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(8.6391F, -11.5533F, 0.0F, 0.0F, -1.5708F, 0.7854F));

		ModelPartData cube_r5 = bone.addChild("cube_r5", ModelPartBuilder.create().uv(28, 24).cuboid(-1.5F, -2.5F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, -13.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData bone2 = cogweight.addChild("bone2", ModelPartBuilder.create().uv(28, 0).mirrored().cuboid(0.0F, -11.0F, 21.0F, 8.0F, 8.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
				.uv(0, 0).mirrored().cuboid(2.0F, -9.0F, 17.0F, 4.0F, 4.0F, 10.0F, new Dilation(0.0F)).mirrored(false)
				.uv(0, 14).mirrored().cuboid(-2.0F, -13.0F, 22.0F, 12.0F, 12.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
				.uv(28, 24).mirrored().cuboid(2.5F, -15.5F, 21.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
				.uv(40, 40).mirrored().cuboid(2.5F, -3.5F, 21.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
				.uv(28, 18).mirrored().cuboid(7.5F, -8.5F, 21.5F, 5.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(4.0F, 6.0F, 35.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData cube_r6 = bone2.addChild("cube_r6", ModelPartBuilder.create().uv(40, 32).mirrored().cuboid(-1.5F, -2.5F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.6391F, -2.4467F, 23.0F, 0.0F, -1.5708F, 0.7854F));

		ModelPartData cube_r7 = bone2.addChild("cube_r7", ModelPartBuilder.create().uv(28, 40).mirrored().cuboid(-2.5F, 4.0F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(4.75F, -7.75F, 23.0F, 0.0F, 0.0F, -0.7854F));

		ModelPartData cube_r8 = bone2.addChild("cube_r8", ModelPartBuilder.create().uv(40, 24).mirrored().cuboid(-2.5F, -9.0F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(4.75F, -6.25F, 23.0F, 0.0F, 0.0F, 0.7854F));

		ModelPartData cube_r9 = bone2.addChild("cube_r9", ModelPartBuilder.create().uv(28, 12).mirrored().cuboid(-2.5F, -1.5F, -1.5F, 5.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.0F, -7.0F, 23.0F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r10 = bone2.addChild("cube_r10", ModelPartBuilder.create().uv(28, 32).mirrored().cuboid(-0.5F, -9.0F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(3.25F, -6.25F, 23.0F, 0.0F, 0.0F, -0.7854F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		cogweight.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}
