package cofh.thermalfoundation.core;

import cofh.thermalfoundation.entity.monster.EntityBasalz;
import cofh.thermalfoundation.entity.monster.EntityBlitz;
import cofh.thermalfoundation.entity.monster.EntityBlizz;
import cofh.thermalfoundation.entity.projectile.EntityBasalzBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlitzBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlizzBolt;
import cofh.thermalfoundation.item.TFItems;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class Proxy {

	public void registerEntities() {

		EntityBlizz.initialize();
		EntityBlitz.initialize();
		EntityBasalz.initialize();
		EntityBlizzBolt.initialize();
		EntityBlitzBolt.initialize();
		EntityBasalzBolt.initialize();
	}

	public void registerRenderInformation() {

	}

	@SubscribeEvent
	public void livingDrops(LivingDropsEvent evt) {

		Entity entity = evt.entity;
		if (entity.isImmuneToFire() && TFProps.dropSulfurFireImmune) {
			boolean s = entity instanceof EntitySlime;
			if (evt.entityLiving.getRNG().nextInt(6 + (s ? 16 : 0)) != 0) {
				return;
			}
			evt.drops.add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, TFItems.dustSulfur.copy()));
		}
	}

}
