package cofh.thermalfoundation.render.shader;

import cofh.core.init.CoreProps;
import cofh.core.render.ShaderHelper;
import cofh.core.util.helpers.HolidayHelper;
import cofh.thermalfoundation.init.TFProps;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderStarfield {

	public static final ResourceLocation starsTexture = new ResourceLocation((CoreProps.holidayAprilFools && HolidayHelper.isAprilFools()) || TFProps.renderStarfieldCage ? "thermalfoundation:textures/cage_starfield.png" : "textures/entity/end_portal.png");

	static {
		if (ShaderHelper.useShaders()) {
			starfieldShader = ShaderHelper.createProgram("/assets/thermalfoundation/shaders/starfield.vert", "/assets/thermalfoundation/shaders/starfield.frag");
		}
	}

	public static EnderCallBack callback = new EnderCallBack();
	public static int starfieldShader;

	public static float prevAlpha = -1;
	public static float alpha = 0;

	public static class EnderCallBack extends ShaderHelper.ShaderCallback {

		@Override
		public void call(int shader, boolean newFrame) {

			if (alpha != prevAlpha) {
				int alpha = ARBShaderObjects.glGetUniformLocationARB(shader, "alpha");
				ARBShaderObjects.glUniform1fARB(alpha, ShaderStarfield.alpha);
				prevAlpha = ShaderStarfield.alpha;
			}
			Minecraft mc = Minecraft.getMinecraft();

			int x = ARBShaderObjects.glGetUniformLocationARB(shader, "yaw");
			ARBShaderObjects.glUniform1fARB(x, (float) ((mc.player.rotationYaw * 2 * Math.PI) / 360.0));

			int z = ARBShaderObjects.glGetUniformLocationARB(shader, "pitch");
			ARBShaderObjects.glUniform1fARB(z, -(float) ((mc.player.rotationPitch * 2 * Math.PI) / 360.0));
		}
	}

}
