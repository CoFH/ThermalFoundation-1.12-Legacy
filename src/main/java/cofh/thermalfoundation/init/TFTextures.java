package cofh.thermalfoundation.init;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.fluids.Fluid;

public class TFTextures {

	private TFTextures() {

	}

	public static void registerTextures(TextureMap map) {

		textureMap = map;

		registerFluidTextures(TFFluids.fluidSteam);

		registerFluidTextures(TFFluids.fluidCreosote);
		registerFluidTextures(TFFluids.fluidCoal);
		registerFluidTextures(TFFluids.fluidRefinedOil);
		registerFluidTextures(TFFluids.fluidFuel);

		registerFluidTextures(TFFluids.fluidSap);
		registerFluidTextures(TFFluids.fluidSyrup);
		registerFluidTextures(TFFluids.fluidResin);
		registerFluidTextures(TFFluids.fluidTreeOil);
	}

	/* HELPERS */
	private static void registerFluidTextures(Fluid fluid) {

		textureMap.registerSprite(fluid.getStill());
		textureMap.registerSprite(fluid.getFlowing());
	}

	private static TextureMap textureMap;

}
