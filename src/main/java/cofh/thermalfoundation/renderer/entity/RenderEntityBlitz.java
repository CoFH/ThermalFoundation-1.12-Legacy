package cofh.thermalfoundation.renderer.entity;

import cofh.thermalfoundation.entity.monster.EntityBlitz;
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
public class RenderEntityBlitz extends RenderLiving<EntityBlitz> {

	static ResourceLocation texture;

	public static void initialize() {

		texture = new ResourceLocation("thermalfoundation:textures/entity/" + "blitz.png");

		RenderingRegistry.registerEntityRenderingHandler(EntityBlitz.class, new Factory());
	}

	public RenderEntityBlitz(RenderManager manager) {

		super(manager, ModelElemental.instance, 0.5F);
	}

	@Override
	public void doRender(EntityBlitz entity, double x, double y, double z, float entityYaw, float partialTicks) {

		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBlitz entity) {

		return texture;
	}

	/* FACTORY */
	public static class Factory implements IRenderFactory<EntityBlitz> {

		@Override
		public Render<? super EntityBlitz> createRenderFor(RenderManager manager) {

			return new RenderEntityBlitz(manager);
		}
	}

}
