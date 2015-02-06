package cofh.thermalfoundation.render.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelBlizz extends ModelBase {

	public static ModelBlizz instance = new ModelBlizz();

	protected final ModelRenderer[] blizzSticks = new ModelRenderer[12];
	protected final ModelRenderer blizzHead;

	public ModelBlizz() {

		for (int i = 0; i < blizzSticks.length; i++) {
			blizzSticks[i] = new ModelRenderer(this, 0, 16);
			blizzSticks[i].addBox(0.0F, 0.0F, 0.0F, 2, 8, 2);
		}
		blizzHead = new ModelRenderer(this, 0, 0);
		blizzHead.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
	}

	@Override
	public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7) {

		setRotationAngles(par2, par3, par4, par5, par6, par7, entity);
		blizzHead.render(par7);

		for (int i = 0; i < blizzSticks.length; i++) {
			blizzSticks[i].render(par7);
		}
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {

		float f6 = par3 * (float) Math.PI * -0.1F;
		int i;

		for (i = 0; i < 4; i++) {
			blizzSticks[i].rotationPointY = -2.0F + MathHelper.cos((i * 2 + par3) * 0.25F);
			blizzSticks[i].rotationPointX = MathHelper.cos(f6) * 9.0F;
			blizzSticks[i].rotationPointZ = MathHelper.sin(f6) * 9.0F;
			++f6;
		}
		f6 = ((float) Math.PI / 4F) + par3 * (float) Math.PI * 0.03F;

		for (i = 4; i < 8; i++) {
			blizzSticks[i].rotationPointY = 2.0F + MathHelper.cos((i * 2 + par3) * 0.25F);
			blizzSticks[i].rotationPointX = MathHelper.cos(f6) * 7.0F;
			blizzSticks[i].rotationPointZ = MathHelper.sin(f6) * 7.0F;
			++f6;
		}
		f6 = 0.47123894F + par3 * (float) Math.PI * -0.05F;

		for (i = 8; i < 12; i++) {
			blizzSticks[i].rotationPointY = 11.0F + MathHelper.cos((i * 1.5F + par3) * 0.5F);
			blizzSticks[i].rotationPointX = MathHelper.cos(f6) * 5.0F;
			blizzSticks[i].rotationPointZ = MathHelper.sin(f6) * 5.0F;
			++f6;
		}
		blizzHead.rotateAngleY = par4 / (180F / (float) Math.PI);
		blizzHead.rotateAngleX = par5 / (180F / (float) Math.PI);
	}

}
