package cofh.thermalfoundation.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelElemental extends ModelBase {

	public static ModelElemental instance = new ModelElemental();

	protected final ModelRenderer[] elementalRods = new ModelRenderer[12];
	protected final ModelRenderer elementalHead;

	public ModelElemental() {

		for (int i = 0; i < elementalRods.length; i++) {
			elementalRods[i] = new ModelRenderer(this, 0, 16);
			elementalRods[i].addBox(0.0F, 0.0F, 0.0F, 2, 8, 2);
		}
		elementalHead = new ModelRenderer(this, 0, 0);
		elementalHead.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
	}

	@Override
	public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7) {

		setRotationAngles(par2, par3, par4, par5, par6, par7, entity);
		elementalHead.render(par7);

		for (int i = 0; i < elementalRods.length; i++) {
			elementalRods[i].render(par7);
		}
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {

		int i;
		float f = par3 * (float) Math.PI * -0.1F;

		for (i = 0; i < 4; i++) {
			elementalRods[i].rotationPointY = -2.0F + MathHelper.cos((i * 2 + par3) * 0.25F);
			elementalRods[i].rotationPointX = MathHelper.cos(f) * 9.0F;
			elementalRods[i].rotationPointZ = MathHelper.sin(f) * 9.0F;
			++f;
		}
		f = ((float) Math.PI / 4F) + par3 * (float) Math.PI * 0.03F;

		for (i = 4; i < 8; i++) {
			elementalRods[i].rotationPointY = 2.0F + MathHelper.cos((i * 2 + par3) * 0.25F);
			elementalRods[i].rotationPointX = MathHelper.cos(f) * 7.0F;
			elementalRods[i].rotationPointZ = MathHelper.sin(f) * 7.0F;
			++f;
		}
		f = 0.47123894F + par3 * (float) Math.PI * -0.05F;

		for (i = 8; i < 12; i++) {
			elementalRods[i].rotationPointY = 11.0F + MathHelper.cos((i * 1.5F + par3) * 0.5F);
			elementalRods[i].rotationPointX = MathHelper.cos(f) * 5.0F;
			elementalRods[i].rotationPointZ = MathHelper.sin(f) * 5.0F;
			++f;
		}
		elementalHead.rotateAngleY = par4 / (180F / (float) Math.PI);
		elementalHead.rotateAngleX = par5 / (180F / (float) Math.PI);
	}

}
