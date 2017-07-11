package cofh.thermalfoundation.proxy;

import cofh.core.render.IModelRegister;
import cofh.thermalfoundation.entity.monster.EntityBasalz;
import cofh.thermalfoundation.entity.monster.EntityBlitz;
import cofh.thermalfoundation.entity.monster.EntityBlizz;
import cofh.thermalfoundation.entity.projectile.EntityBasalzBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlitzBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlizzBolt;
import cofh.thermalfoundation.init.TFTextures;
import cofh.thermalfoundation.render.entity.RenderEntityAsIcon;
import cofh.thermalfoundation.render.entity.RenderEntityBasalz;
import cofh.thermalfoundation.render.entity.RenderEntityBlitz;
import cofh.thermalfoundation.render.entity.RenderEntityBlizz;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class ProxyClient extends Proxy {

	/* INIT */
	@Override
	public void preInit(FMLPreInitializationEvent event) {

		super.preInit(event);

		for (IModelRegister register : modelList) {
			register.registerModels();
		}
		registerRenderInformation();
	}

	@Override
	public void initialize(FMLInitializationEvent event) {

		super.initialize(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {

		super.postInit(event);
	}

	/* REGISTRATION */
	public void registerRenderInformation() {

		RenderingRegistry.registerEntityRenderingHandler(EntityBasalz.class, RenderEntityBasalz::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBlitz.class, RenderEntityBlitz::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBlizz.class, RenderEntityBlizz::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityBlizzBolt.class, manager -> new RenderEntityAsIcon(manager).setIcon("thermalfoundation:items/material/dust_blizz"));
		RenderingRegistry.registerEntityRenderingHandler(EntityBlitzBolt.class, manager -> new RenderEntityAsIcon(manager).setIcon("thermalfoundation:items/material/dust_blitz"));
		RenderingRegistry.registerEntityRenderingHandler(EntityBasalzBolt.class, manager -> new RenderEntityAsIcon(manager).setIcon("thermalfoundation:items/material/dust_basalz"));
	}

	/* EVENT HANDLERS */
	@SubscribeEvent
	public void registerIcons(TextureStitchEvent.Pre event) {

		TFTextures.registerTextures(event.getMap());
	}

	/* HELPERS */
	public boolean addIModelRegister(IModelRegister modelRegister) {

		return modelList.add(modelRegister);
	}

	private static ArrayList<IModelRegister> modelList = new ArrayList<>();

}
