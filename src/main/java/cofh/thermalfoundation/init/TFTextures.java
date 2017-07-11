package cofh.thermalfoundation.init;

import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
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

		registerFluidTextures(fluid.getName());
	}

	private static void registerFluidTextures(String fluidName) {

		textureMap.registerSprite(new ResourceLocation(ThermalFoundation.MOD_ID, "blocks/fluid/" + fluidName + "_still"));
		textureMap.registerSprite(new ResourceLocation(ThermalFoundation.MOD_ID, "blocks/fluid/" + fluidName + "_flow"));
	}

	private static TextureMap textureMap;

}
