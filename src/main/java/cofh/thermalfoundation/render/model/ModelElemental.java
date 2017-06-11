package cofh.thermalfoundation.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly (Side.CLIENT)
public class ModelElemental extends ModelBase {

	public static final ModelElemental INSTANCE = new ModelElemental();

	final ModelRenderer[] elementalRods = new ModelRenderer[12];
	final ModelRenderer elementalHead;

	public ModelElemental() {

		for (int i = 0; i < elementalRods.length; i++) {
			elementalRods[i] = new ModelRenderer(this, 0, 16);
			elementalRods[i].addBox(0.0F, 0.0F, 0.0F, 2, 8, 2);
		}
		elementalHead = new ModelRenderer(this, 0, 0);
		elementalHead.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

		setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		elementalHead.render(scale);

		for (ModelRenderer elementalRod : elementalRods) {
			elementalRod.render(scale);
		}
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {

		float f = ageInTicks * (float) Math.PI * -0.1F;
		int i;

		for (i = 0; i < 4; i++) {
			elementalRods[i].rotationPointY = -2.0F + MathHelper.cos((i * 2 + ageInTicks) * 0.25F);
			elementalRods[i].rotationPointX = MathHelper.cos(f) * 9.0F;
			elementalRods[i].rotationPointZ = MathHelper.sin(f) * 9.0F;
			++f;
		}
		f = ((float) Math.PI / 4F) + ageInTicks * (float) Math.PI * 0.03F;

		for (i = 4; i < 8; i++) {
			elementalRods[i].rotationPointY = 2.0F + MathHelper.cos((i * 2 + ageInTicks) * 0.25F);
			elementalRods[i].rotationPointX = MathHelper.cos(f) * 7.0F;
			elementalRods[i].rotationPointZ = MathHelper.sin(f) * 7.0F;
			++f;
		}
		f = 0.47123894F + ageInTicks * (float) Math.PI * -0.05F;

		for (i = 8; i < 12; i++) {
			elementalRods[i].rotationPointY = 11.0F + MathHelper.cos((i * 1.5F + ageInTicks) * 0.5F);
			elementalRods[i].rotationPointX = MathHelper.cos(f) * 5.0F;
			elementalRods[i].rotationPointZ = MathHelper.sin(f) * 5.0F;
			++f;
		}
		elementalHead.rotateAngleY = netHeadYaw / (180F / (float) Math.PI);
		elementalHead.rotateAngleX = headPitch / (180F / (float) Math.PI);
	}

}
