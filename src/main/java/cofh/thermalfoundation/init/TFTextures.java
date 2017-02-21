package cofh.thermalfoundation.init;

import net.minecraft.client.renderer.texture.TextureMap;
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

		registerFluidTextures(map, TFFluids.fluidResin);
		registerFluidTextures(map, TFFluids.fluidTreeOil);
	}

	// Bouncer to make the class readable.
	private static void registerFluidTextures(TextureMap map, Fluid fluid) {

		map.registerSprite(fluid.getStill());
		map.registerSprite(fluid.getFlowing());
	}

}
