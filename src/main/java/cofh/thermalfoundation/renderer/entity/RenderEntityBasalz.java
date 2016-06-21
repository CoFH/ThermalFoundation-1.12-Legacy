package cofh.thermalfoundation.renderer.entity;

import cofh.thermalfoundation.entity.monster.EntityBasalz;
import cofh.thermalfoundation.model.ModelElemental;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEntityBasalz extends RenderLiving<EntityBasalz> {

	private static ResourceLocation texture;

	public static void initialize() {

		texture = new ResourceLocation("thermalfoundation:textures/entity/" + "basalz.png");

		RenderingRegistry.registerEntityRenderingHandler(EntityBasalz.class, new Factory());
	}

	public RenderEntityBasalz(RenderManager manager) {

		super(manager, ModelElemental.instance, 0.5F);
	}

	@Override
	public void doRender(EntityBasalz entity, double x, double y, double z, float entityYaw, float partialTicks) {

		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBasalz entity) {

		return texture;
	}

	/* FACTORY */
	public static class Factory implements IRenderFactory<EntityBasalz> {

		@Override
		public Render<? super EntityBasalz> createRenderFor(RenderManager manager) {

			return new RenderEntityBasalz(manager);
		}
	}

}
