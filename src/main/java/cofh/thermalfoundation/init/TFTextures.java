package cofh.thermalfoundation.init;

import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.Fluid;

public class TFTextures {

	private TFTextures() {

	}

	public static void registerIcons(TextureStitchEvent.Pre event) {

		TextureMap map = event.getMap();

		registerFluidTextures(map, TFFluids.fluidSteam);

		registerFluidTextures(map, TFFluids.fluidCreosote);
		registerFluidTextures(map, TFFluids.fluidCoal);
		registerFluidTextures(map, TFFluids.fluidRefinedOil);
		registerFluidTextures(map, TFFluids.fluidFuel);

		registerFluidTextures(map, TFFluids.fluidSap);
		registerFluidTextures(map, TFFluids.fluidSyrup);
		registerFluidTextures(map, TFFluids.fluidResin);
		registerFluidTextures(map, TFFluids.fluidTreeOil);
	}

	/* HELPERS */
	private static void registerFluidTextures(TextureMap map, Fluid fluid) {

		registerFluidTextures(map, fluid.getName());
	}

	private static void registerFluidTextures(TextureMap map, String fluidName) {

		map.registerSprite(new ResourceLocation(ThermalFoundation.MOD_ID, "blocks/fluid/" + fluidName + "_still"));
		map.registerSprite(new ResourceLocation(ThermalFoundation.MOD_ID, "blocks/fluid/" + fluidName + "_flow"));
	}

}
