package cofh.thermalfoundation.renderer.entity;

import cofh.thermalfoundation.entity.projectile.EntityBasalzBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlitzBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlizzBolt;
import cofh.thermalfoundation.item.ItemMaterial;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderEntityAsItem<T extends Entity> extends Render<T> {

	protected final ItemStack stack;
	private final RenderItem renderer;

	public static void initialize() {

		RenderingRegistry.registerEntityRenderingHandler(EntityBlizzBolt.class, new FactoryBlizzBolt());
		RenderingRegistry.registerEntityRenderingHandler(EntityBlitzBolt.class, new FactoryBlitzBolt());
		RenderingRegistry.registerEntityRenderingHandler(EntityBasalzBolt.class, new FactoryBasalzBolt());
	}

	public RenderEntityAsItem(RenderManager manager, ItemStack stack, RenderItem renderer) {

		super(manager);
		this.stack = stack;
		this.renderer = renderer;
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.enableRescaleNormal();
		GlStateManager.scale(0.5F, 0.5F, 0.5F);
		GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		this.bindTexture(TextureMap.locationBlocksTexture);
		this.renderer.renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {

		return TextureMap.locationBlocksTexture;
	}

	/* FACTORIES */
	public static class FactoryBlizzBolt implements IRenderFactory<EntityBlizzBolt> {

		@Override
		public Render<? super EntityBlizzBolt> createRenderFor(RenderManager manager) {

			return new RenderEntityAsItem(manager, ItemMaterial.dustBlizz, Minecraft.getMinecraft().getRenderItem());
		}
	}

	public static class FactoryBlitzBolt implements IRenderFactory<EntityBlitzBolt> {

		@Override
		public Render<? super EntityBlitzBolt> createRenderFor(RenderManager manager) {

			return new RenderEntityAsItem(manager, ItemMaterial.dustBlitz, Minecraft.getMinecraft().getRenderItem());
		}
	}

	public static class FactoryBasalzBolt implements IRenderFactory<EntityBasalzBolt> {

		@Override
		public Render<? super EntityBasalzBolt> createRenderFor(RenderManager manager) {

			return new RenderEntityAsItem(manager, ItemMaterial.dustBasalz, Minecraft.getMinecraft().getRenderItem());
		}
	}

}
