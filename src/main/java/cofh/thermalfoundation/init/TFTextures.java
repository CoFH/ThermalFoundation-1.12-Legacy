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
		registerFluidTextures(TFFluids.fluidCrudeOil);
		registerFluidTextures(TFFluids.fluidRefinedOil);
		registerFluidTextures(TFFluids.fluidFuel);

		registerFluidTextures(TFFluids.fluidSap);
		registerFluidTextures(TFFluids.fluidSyrup);
		registerFluidTextures(TFFluids.fluidResin);
		registerFluidTextures(TFFluids.fluidTreeOil);

		registerFluidTextures(TFFluids.fluidMushroomStew);
		registerFluidTextures(TFFluids.fluidExperience);

		registerFluidTextures(TFFluids.fluidPotion);

		registerFluidTextures(TFFluids.fluidRedstone);
		registerFluidTextures(TFFluids.expFluid);
		registerFluidTextures(TFFluids.fluidEnder);
		registerFluidTextures(TFFluids.fluidPyrotheum);
		registerFluidTextures(TFFluids.fluidCryotheum);
		registerFluidTextures(TFFluids.fluidAerotheum);
		registerFluidTextures(TFFluids.fluidPetrotheum);
		registerFluidTextures(TFFluids.fluidMana);
	}

	/* HELPERS */
	private static TextureMap textureMap;

	private static void registerFluidTextures(Fluid fluid) {

		registerFluidTextures(fluid.getName());
	}

	private static void registerFluidTextures(String fluidName) {

		textureMap.registerSprite(new ResourceLocation(ThermalFoundation.MOD_ID, "blocks/fluid/" + fluidName + "_still"));
		textureMap.registerSprite(new ResourceLocation(ThermalFoundation.MOD_ID, "blocks/fluid/" + fluidName + "_flow"));
	}

}
