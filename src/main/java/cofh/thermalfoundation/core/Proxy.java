package cofh.thermalfoundation.core;

import cofh.thermalfoundation.entity.monster.EntityBasalz;
import cofh.thermalfoundation.entity.monster.EntityBlitz;
import cofh.thermalfoundation.entity.monster.EntityBlizz;
import cofh.thermalfoundation.entity.projectile.EntityBasalzBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlitzBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlizzBolt;
import cofh.thermalfoundation.item.TFItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Proxy {

	public void preInit() {

	}

	public void init() {

	}

	public void post() {

	}

	public void registerEntities() {

		EntityBlizz.initialize(0);
		EntityBlitz.initialize(1);
		EntityBasalz.initialize(2);
		EntityBlizzBolt.initialize(3);
		EntityBlitzBolt.initialize(4);
		EntityBasalzBolt.initialize(5);
	}

	public void registerRenderInformation() {

	}

	@SubscribeEvent
	public void handleLivingDropsEvent(LivingDropsEvent event) {

		Entity entity = event.getEntity();
		if (entity.isImmuneToFire() && TFProps.dropSulfurFireImmune && event.getEntityLiving().worldObj.getGameRules().getBoolean("doMobLoot")) {
			boolean s = entity instanceof EntitySlime;
			if (event.getEntityLiving().getRNG().nextInt(6 + (s ? 16 : 0)) != 0) {
				return;
			}
			event.getDrops().add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, TFItems.dustSulfur.copy()));
		}
	}

}
