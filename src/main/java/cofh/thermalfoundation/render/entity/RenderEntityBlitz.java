package cofh.thermalfoundation.render.entity;

import cofh.thermalfoundation.entity.monster.EntityBlitz;
import cofh.thermalfoundation.render.model.ModelElemental;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderEntityBlitz extends RenderLiving {

	public static final RenderEntityBlitz instance = new RenderEntityBlitz();

	static ResourceLocation texture;

	static {
		RenderingRegistry.registerEntityRenderingHandler(EntityBlitz.class, instance);
	}

	public static void initialize() {

		texture = new ResourceLocation("thermalfoundation:textures/entity/" + "Blitz.png");
	}

	public RenderEntityBlitz() {

		super(ModelElemental.instance, 0.5F);
	}

	@Override
	public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {

		doRenderBlitz((EntityBlitz) entity, d0, d1, d2, f, f1);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {

		return texture;
	}

	@Override
	public void doRender(EntityLivingBase entity, double d0, double d1, double d2, float f, float f1) {

		this.doRenderBlitz((EntityBlitz) entity, d0, d1, d2, f, f1);
	}

	protected void doRenderBlitz(EntityBlitz entity, double d0, double d1, double d2, float f, float f1) {

		super.doRender(entity, d0, d1, d2, f, f1);
	}

}
