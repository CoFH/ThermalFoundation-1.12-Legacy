package cofh.thermalfoundation.render.entity;

import cofh.lib.util.helpers.HolidayHelper;
import cofh.thermalfoundation.entity.monster.EntityBlizz;
import cofh.thermalfoundation.render.model.ModelElemental;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEntityBlizz extends RenderLiving<EntityBlizz> {

    private static ResourceLocation texture;

    public static void initialize() {

        if (HolidayHelper.isChristmas()) {
            texture = new ResourceLocation("thermalfoundation:textures/entity/" + "xmas/blizz.png");
            return;
        }
        texture = new ResourceLocation("thermalfoundation:textures/entity/" + "blizz.png");
    }

    public RenderEntityBlizz(RenderManager renderManager) {
        super(renderManager, ModelElemental.instance, 0.5F);
    }

    @Override
    public void doRender(EntityBlizz entity, double d0, double d1, double d2, float f, float f1) {
        doRenderBlizz((EntityBlizz) entity, d0, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBlizz par1Entity) {
        return texture;
    }

    protected void doRenderBlizz(EntityBlizz entity, double d0, double d1, double d2, float f, float f1) {
        super.doRender(entity, d0, d1, d2, f, f1);
    }

}
