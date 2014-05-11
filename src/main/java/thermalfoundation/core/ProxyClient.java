package thermalfoundation.core;

import cofh.render.IconRegistry;
import cofh.util.StringHelper;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.Fluid;

import thermalfoundation.entity.projectile.EntityBlizzBall;
import thermalfoundation.entity.projectile.EntityBlizzSlowball;
import thermalfoundation.fluid.TFFluids;
import thermalfoundation.render.entity.RenderEntityBlizz;

public class ProxyClient extends Proxy {

	@Override
	public void registerRenderInformation() {

		RenderingRegistry.registerEntityRenderingHandler(EntityBlizzBall.class, new RenderSnowball(Items.snowball));
		RenderingRegistry.registerEntityRenderingHandler(EntityBlizzSlowball.class, new RenderSnowball(Items.snowball));
	}

	@Override
	@SubscribeEvent
	public void registerIcons(TextureStitchEvent.Pre event) {

		if (event.map.getTextureType() == 0) {
			registerFluidIcons(TFFluids.fluidRedstone, event.map);
			registerFluidIcons(TFFluids.fluidGlowstone, event.map);
			registerFluidIcons(TFFluids.fluidEnder, event.map);
			registerFluidIcons(TFFluids.fluidPyrotheum, event.map);
			registerFluidIcons(TFFluids.fluidCryotheum, event.map);
			registerFluidIcons(TFFluids.fluidMana, event.map);
			registerFluidIcons(TFFluids.fluidCoal, event.map);
			registerFluidIcons(TFFluids.fluidSteam, event.map);
		}
	}

	@Override
	@SubscribeEvent
	public void initializeIcons(TextureStitchEvent.Post event) {

		setFluidIcons(TFFluids.fluidRedstone);
		setFluidIcons(TFFluids.fluidGlowstone);
		setFluidIcons(TFFluids.fluidEnder);
		setFluidIcons(TFFluids.fluidPyrotheum);
		setFluidIcons(TFFluids.fluidCryotheum);
		setFluidIcons(TFFluids.fluidMana);
		setFluidIcons(TFFluids.fluidCoal);
		setFluidIcons(TFFluids.fluidSteam);

		RenderEntityBlizz.initialize();
	}

	// @SubscribeEvent
	// public void initializeSounds(SoundLoadEvent event) {
	//
	// String folder = "mob/blizz/";
	// String[] files = { "ambient", "breathe1", "breathe2", "breathe3", "spike1" };
	//
	// for (String sound : files) {
	// try {
	// event.manager.addSound(CoreUtils.getSoundName("thermalfoundation", folder + sound, true));
	// thermalfoundation.log.info("Loaded sound: " + sound);
	// } catch (Exception e) {
	// thermalfoundation.log(Level.SEVERE, String.format("Unable to load sound: %s [[%s]]", folder + sound, e.toString()));
	// }
	// }
	// }

	public static void registerFluidIcons(Fluid fluid, IIconRegister ir) {

		String name = StringHelper.titleCase(fluid.getName());
		IconRegistry.addIcon("Fluid" + name, "thermalfoundation:fluid/Fluid_" + name + "_Still", ir);
		IconRegistry.addIcon("Fluid" + name + 1, "thermalfoundation:fluid/Fluid_" + name + "_Flow", ir);
	}

	public static void setFluidIcons(Fluid fluid) {

		String name = StringHelper.titleCase(fluid.getName());
		fluid.setIcons(IconRegistry.getIcon("Fluid" + name), IconRegistry.getIcon("Fluid" + name, 1));
	}

}
