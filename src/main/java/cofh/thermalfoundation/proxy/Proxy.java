package cofh.thermalfoundation.proxy;

import cofh.core.render.IModelRegister;
import cofh.thermalfoundation.entity.monster.EntityBasalz;
import cofh.thermalfoundation.entity.monster.EntityBlitz;
import cofh.thermalfoundation.entity.monster.EntityBlizz;
import cofh.thermalfoundation.entity.projectile.EntityBasalzBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlitzBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlizzBolt;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Proxy {

	/* INIT */
	public void preInit(FMLPreInitializationEvent event) {

		MinecraftForge.EVENT_BUS.register(EventHandler.INSTANCE);

		registerEntities();
	}

	public void initialize(FMLInitializationEvent event) {

	}

	public void postInit(FMLPostInitializationEvent event) {

	}

	/* REGISTRATION */
	public void registerEntities() {

		EntityBlizz.initialize(0);
		EntityBlitz.initialize(1);
		EntityBasalz.initialize(2);

		EntityBlizzBolt.initialize(3);
		EntityBlitzBolt.initialize(4);
		EntityBasalzBolt.initialize(5);
	}

	/* HELPERS */
	public boolean addIModelRegister(IModelRegister modelRegister) {

		return false;
	}

}
