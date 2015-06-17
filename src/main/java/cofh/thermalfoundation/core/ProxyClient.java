package cofh.thermalfoundation.core;

import cofh.core.render.IconRegistry;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalfoundation.entity.projectile.EntityBasalzBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlitzBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlizzBall;
import cofh.thermalfoundation.entity.projectile.EntityBlizzBolt;
import cofh.thermalfoundation.fluid.TFFluids;
import cofh.thermalfoundation.render.entity.RenderEntityAsIcon;
import cofh.thermalfoundation.render.entity.RenderEntityBasalz;
import cofh.thermalfoundation.render.entity.RenderEntityBlitz;
import cofh.thermalfoundation.render.entity.RenderEntityBlizz;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.Fluid;

public class ProxyClient extends Proxy {

	static RenderEntityAsIcon renderBlizzBall = new RenderEntityAsIcon();
	static RenderEntityAsIcon renderBlizzBolt = new RenderEntityAsIcon();
	static RenderEntityAsIcon renderBlitzBolt = new RenderEntityAsIcon();
	static RenderEntityAsIcon renderBasalzBolt = new RenderEntityAsIcon();

	@Override
	public void registerRenderInformation() {

		RenderingRegistry.registerEntityRenderingHandler(EntityBlizzBall.class, renderBlizzBall);
		RenderingRegistry.registerEntityRenderingHandler(EntityBlizzBolt.class, renderBlizzBolt);
		RenderingRegistry.registerEntityRenderingHandler(EntityBlitzBolt.class, renderBlitzBolt);
		RenderingRegistry.registerEntityRenderingHandler(EntityBasalzBolt.class, renderBasalzBolt);
	}

	@SideOnly(Side.CLIENT)
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
		} else {
			// projectile icons go here
		}
	}

	@SideOnly(Side.CLIENT)
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
		RenderEntityBlitz.initialize();
		RenderEntityBasalz.initialize();

		renderBlizzBall.setIcon(Items.snowball.getIconFromDamage(0));
		renderBlizzBolt.setIcon(Items.snowball.getIconFromDamage(0));
		renderBlitzBolt.setIcon(Items.snowball.getIconFromDamage(0));
		renderBasalzBolt.setIcon(Items.snowball.getIconFromDamage(0));
	}

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
