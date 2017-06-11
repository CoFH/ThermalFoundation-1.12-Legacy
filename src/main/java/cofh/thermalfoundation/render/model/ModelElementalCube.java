package cofh.thermalfoundation.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMagmaCube;

public class ModelElementalCube extends ModelBase {

	public static final ModelElementalCube INSTANCE = new ModelElementalCube();

	final ModelRenderer[] segments = new ModelRenderer[8];
	final ModelRenderer core;

	public ModelElementalCube() {

		for (int i = 0; i < segments.length; ++i) {
			int j = 0;
			int k = i;

			if (i == 2) {
				j = 24;
				k = 10;
			} else if (i == 3) {
				j = 24;
				k = 19;
			}
			segments[i] = new ModelRenderer(this, j, k);
			segments[i].addBox(-4.0F, (float) (16 + i), -4.0F, 8, 1, 8);
		}
		core = new ModelRenderer(this, 0, 16);
		core.addBox(-2.0F, 18.0F, -2.0F, 4, 4, 4);
	}

	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

		setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		core.render(scale);

		for (ModelRenderer modelrenderer : segments) {
			modelrenderer.render(scale);
		}
	}

	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {

		EntityMagmaCube entitymagmacube = (EntityMagmaCube) entitylivingbaseIn;
		float f = entitymagmacube.prevSquishFactor + (entitymagmacube.squishFactor - entitymagmacube.prevSquishFactor) * partialTickTime;

		if (f < 0.0F) {
			f = 0.0F;
		}
		for (int i = 0; i < segments.length; ++i) {
			segments[i].rotationPointY = (float) (-(4 - i)) * f * 1.7F;
		}
	}

}
