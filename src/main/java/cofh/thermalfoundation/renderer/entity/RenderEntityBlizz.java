package cofh.thermalfoundation.renderer.entity;

import cofh.thermalfoundation.entity.monster.EntityBlizz;
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
public class RenderEntityBlizz extends RenderLiving<EntityBlizz> {

	static ResourceLocation texture;

	public static void initialize() {

		// TODO: Xmas textures :)
		texture = new ResourceLocation("thermalfoundation:textures/entity/" + "blizz.png");

		RenderingRegistry.registerEntityRenderingHandler(EntityBlizz.class, new Factory());
	}

	public RenderEntityBlizz(RenderManager manager) {

		super(manager, ModelElemental.instance, 0.5F);
	}

	@Override
	public void doRender(EntityBlizz entity, double x, double y, double z, float entityYaw, float partialTicks) {

		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBlizz entity) {

		return texture;
	}

	/* FACTORY */
	public static class Factory implements IRenderFactory<EntityBlizz> {

		@Override
		public Render<? super EntityBlizz> createRenderFor(RenderManager manager) {

			return new RenderEntityBlizz(manager);
		}
	}

}
