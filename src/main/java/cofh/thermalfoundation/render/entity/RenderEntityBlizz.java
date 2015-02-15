package cofh.thermalfoundation.render.entity;

import cofh.lib.util.helpers.HolidayHelper;
import cofh.thermalfoundation.entity.monster.EntityBlizz;
import cofh.thermalfoundation.render.model.ModelBlizz;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderEntityBlizz extends RenderLiving {

	public static final RenderEntityBlizz instance = new RenderEntityBlizz();

	static ResourceLocation texture;

	static {
		RenderingRegistry.registerEntityRenderingHandler(EntityBlizz.class, instance);
	}

	public static void initialize() {

		if (HolidayHelper.isChristmas()) {
			texture = new ResourceLocation("thermalfoundation:textures/entity/" + "xmas/Blizz.png");
			return;
		}
		texture = new ResourceLocation("thermalfoundation:textures/entity/" + "Blizz.png");
	}

	public RenderEntityBlizz() {

		super(ModelBlizz.instance, 0.5F);
	}

	@Override
	public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {

		doRenderBlizz((EntityBlizz) entity, d0, d1, d2, f, f1);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {

		return texture;
	}

	@Override
	public void doRender(EntityLivingBase entity, double d0, double d1, double d2, float f, float f1) {

		this.doRenderBlizz((EntityBlizz) entity, d0, d1, d2, f, f1);
	}

	protected void doRenderBlizz(EntityBlizz entity, double d0, double d1, double d2, float f, float f1) {

		super.doRender(entity, d0, d1, d2, f, f1);
	}

}
