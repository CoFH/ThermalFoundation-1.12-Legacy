package cofh.thermalfoundation.render.entity;

import codechicken.lib.texture.TextureUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderEntityAsIcon extends Render<Entity> {

    private TextureAtlasSprite icon;
    private String iconName = "";

    public RenderEntityAsIcon(RenderManager renderManager) {
        super(renderManager);
    }

    public RenderEntityAsIcon(RenderManager renderManager, TextureAtlasSprite icon) {
        this(renderManager);
        this.icon = icon;
    }

    public RenderEntityAsIcon setIcon(String iconName) {
        this.iconName = iconName;
        return this;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
        if (icon == null) {
            icon = TextureUtils.getTexture(iconName);
        }

        if (icon != null) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(x, y, z);
            GlStateManager.enableRescaleNormal();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            this.bindEntityTexture(entity);
            this.renderIcon(icon);
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }

    private void renderIcon(TextureAtlasSprite p_77026_2_) {

        float minU = p_77026_2_.getMinU();
        float maxU = p_77026_2_.getMaxU();
        float minV = p_77026_2_.getMinV();
        float maxV = p_77026_2_.getMaxV();
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        VertexBuffer buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
        buffer.pos(0.0F - f5, 0.0F - f6, 0.0D).tex(minU, maxV).normal(0, 1, 0).endVertex();
        buffer.pos(f4 - f5, 0.0F - f6, 0.0D).tex(maxU, maxV).normal(0, 1, 0).endVertex();
        buffer.pos(f4 - f5, f4 - f6, 0.0D).tex(maxU, minV).normal(0, 1, 0).endVertex();
        buffer.pos(0.0F - f5, f4 - f6, 0.0D).tex(minU, minV).normal(0, 1, 0).endVertex();
        Tessellator.getInstance().draw();
    }

}
