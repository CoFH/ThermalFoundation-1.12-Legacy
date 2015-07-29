package cofh.thermalfoundation.core;

import cofh.core.render.IconRegistry;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalfoundation.entity.projectile.EntityBasalzBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlazeBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlitzBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlizzBolt;
import cofh.thermalfoundation.fluid.TFFluids;
import cofh.thermalfoundation.item.TFItems;
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

	static RenderEntityAsIcon renderBlazeBolt = new RenderEntityAsIcon();
	static RenderEntityAsIcon renderBlizzBolt = new RenderEntityAsIcon();
	static RenderEntityAsIcon renderBlitzBolt = new RenderEntityAsIcon();
	static RenderEntityAsIcon renderBasalzBolt = new RenderEntityAsIcon();

	@Override
	public void registerRenderInformation() {

		RenderingRegistry.registerEntityRenderingHandler(EntityBlazeBolt.class, renderBlazeBolt);
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
			registerFluidIcons(TFFluids.fluidAerotheum, event.map);
			registerFluidIcons(TFFluids.fluidPetrotheum, event.map);
			registerFluidIcons(TFFluids.fluidMana, event.map);
			registerFluidIcons(TFFluids.fluidCoal, event.map);
			registerFluidIcons(TFFluids.fluidSteam, event.map);

		} else if (event.map.getTextureType() == 1) {
			IconRegistry.addIcon("DustBlaze", "thermalfoundation:material/DustBlaze", event.map);

			if (TFProps.iconBlazePowder) {
				Items.blaze_powder.setTextureName("thermalfoundation:material/DustBlaze");
				Items.blaze_powder.registerIcons(event.map);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void initializeIcons(TextureStitchEvent.Post event) {

		if (event.map.getTextureType() == 0) {
			setFluidIcons(TFFluids.fluidRedstone);
			setFluidIcons(TFFluids.fluidGlowstone);
			setFluidIcons(TFFluids.fluidEnder);
			setFluidIcons(TFFluids.fluidPyrotheum);
			setFluidIcons(TFFluids.fluidCryotheum);
			setFluidIcons(TFFluids.fluidAerotheum);
			setFluidIcons(TFFluids.fluidPetrotheum);
			setFluidIcons(TFFluids.fluidMana);
			setFluidIcons(TFFluids.fluidCoal);
			setFluidIcons(TFFluids.fluidSteam);
		} else if (event.map.getTextureType() == 1) {
			RenderEntityBlizz.initialize();
			RenderEntityBlitz.initialize();
			RenderEntityBasalz.initialize();

			renderBlazeBolt.setIcon(IconRegistry.getIcon("DustBlaze"));
			renderBlizzBolt.setIcon(TFItems.itemMaterial.getIconFromDamage(TFItems.dustBlizz.getItemDamage()));
			renderBlitzBolt.setIcon(TFItems.itemMaterial.getIconFromDamage(TFItems.dustBlitz.getItemDamage()));
			renderBasalzBolt.setIcon(TFItems.itemMaterial.getIconFromDamage(TFItems.dustBasalz.getItemDamage()));
		}
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
