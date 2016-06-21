package cofh.thermalfoundation.core;

import cofh.api.core.IModelRegister;
import cofh.thermalfoundation.renderer.entity.RenderEntityAsItem;
import cofh.thermalfoundation.renderer.entity.RenderEntityBasalz;
import cofh.thermalfoundation.renderer.entity.RenderEntityBlitz;
import cofh.thermalfoundation.renderer.entity.RenderEntityBlizz;

import java.util.ArrayList;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ProxyClient extends Proxy {

	/* INIT */
	@Override
	public void preInit(FMLPreInitializationEvent event) {

		super.preInit(event);

		for (int i = 0; i < modelList.size(); i++) {
			modelList.get(i).registerModels();
		}
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
	@Override
	public void registerEntities() {

		super.registerEntities();

		RenderEntityBlizz.initialize();
		RenderEntityBlitz.initialize();
		RenderEntityBasalz.initialize();
		RenderEntityAsItem.initialize();
	}

	/* HELPERS */

	public static ArrayList<IModelRegister> modelList = new ArrayList<IModelRegister>();

}
