package cofh.thermalfoundation.render.entity;

import cofh.lib.util.helpers.HolidayHelper;
import cofh.thermalfoundation.entity.monster.EntityBlizz;
import cofh.thermalfoundation.render.model.ModelElemental;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly (Side.CLIENT)
public class RenderEntityBlizz extends RenderLiving<EntityBlizz> {

	private static ResourceLocation texture;
	private static ResourceLocation textureXmas;

	static {
		texture = new ResourceLocation("thermalfoundation:textures/entity/" + "blizz.png");
		textureXmas = new ResourceLocation("thermalfoundation:textures/entity/" + "blizz_xmas.png");
	}

	public RenderEntityBlizz(RenderManager renderManager) {

		super(renderManager, ModelElemental.instance, 0.5F);
	}

	@Override
	public void doRender(EntityBlizz entity, double x, double y, double z, float entityYaw, float partialTicks) {

		doRenderBlizz(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBlizz par1Entity) {

		return HolidayHelper.isChristmas() ? textureXmas : texture;
	}

	protected void doRenderBlizz(EntityBlizz entity, double x, double y, double z, float entityYaw, float partialTicks) {

		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

}
