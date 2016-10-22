package cofh.thermalfoundation.render.entity;

import cofh.thermalfoundation.entity.monster.EntityBlitz;
import cofh.thermalfoundation.render.model.ModelElemental;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEntityBlitz extends RenderLiving<EntityBlitz> {

	private static ResourceLocation texture;

	public static void initialize() {

		texture = new ResourceLocation("thermalfoundation:textures/entity/" + "blitz.png");
	}

	public RenderEntityBlitz(RenderManager manager) {

		super(manager, ModelElemental.instance, 0.5F);
	}

	@Override
	public void doRender(EntityBlitz entity, double d0, double d1, double d2, float f, float f1) {

		doRenderBlitz((EntityBlitz) entity, d0, d1, d2, f, f1);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBlitz par1Entity) {

		return texture;
	}


	protected void doRenderBlitz(EntityBlitz entity, double d0, double d1, double d2, float f, float f1) {

		super.doRender(entity, d0, d1, d2, f, f1);
	}

}
