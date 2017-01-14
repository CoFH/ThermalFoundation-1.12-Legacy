package cofh.thermalfoundation.core;

import cofh.api.core.IModelRegister;
import cofh.thermalfoundation.entity.monster.EntityBasalz;
import cofh.thermalfoundation.entity.monster.EntityBlitz;
import cofh.thermalfoundation.entity.monster.EntityBlizz;
import cofh.thermalfoundation.entity.projectile.EntityBasalzBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlitzBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlizzBolt;
import cofh.thermalfoundation.item.ItemMaterial;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Proxy {

	/* INIT */
	public void preInit(FMLPreInitializationEvent event) {

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

	/* EVENT HANDLING */
	@SubscribeEvent
	public void handleLivingDropsEvent(LivingDropsEvent event) {

		Entity entity = event.getEntity();
		if (entity.isImmuneToFire() && TFProps.dropSulfurFireImmune && event.getEntityLiving().worldObj.getGameRules().getBoolean("doMobLoot")) {
			boolean s = entity instanceof EntitySlime;
			if (event.getEntityLiving().getRNG().nextInt(6 + (s ? 16 : 0)) != 0) {
				return;
			}
			event.getDrops().add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, ItemMaterial.dustSulfur.copy()));
		}
	}

	/* HELPERS */
	public boolean addIModelRegister(IModelRegister modelRegister) {

		return false;
	}

}
