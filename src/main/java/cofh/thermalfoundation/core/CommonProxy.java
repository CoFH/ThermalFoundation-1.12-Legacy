package cofh.thermalfoundation.core;

import cofh.core.CoFHProps;
import cofh.thermalfoundation.ThermalFoundation;
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
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class CommonProxy {

	public void preInit() {}

	public void init() {}

	public void post() {}

	public void registerEntities() {

		EntityBlizz.initialize(0);
		EntityBlitz.initialize(1);
		EntityBasalz.initialize(2);

		EntityRegistry.registerModEntity(EntityBlizzBolt.class, "blizzBolt", 3, ThermalFoundation.instance, CoFHProps.ENTITY_TRACKING_DISTANCE, 1, true);
		EntityRegistry.registerModEntity(EntityBlitzBolt.class, "blitzBolt", 4, ThermalFoundation.instance, CoFHProps.ENTITY_TRACKING_DISTANCE, 1, true);
		EntityRegistry.registerModEntity(EntityBasalzBolt.class, "basalzBolt", 5, ThermalFoundation.instance, CoFHProps.ENTITY_TRACKING_DISTANCE, 1, true);
	}

	public void registerRenderInformation() {

	}

	@SubscribeEvent
	public void livingDrops(LivingDropsEvent evt) {

		Entity entity = evt.getEntity();
		if (entity.isImmuneToFire() && TFProps.dropSulfurFireImmune) {
			boolean s = entity instanceof EntitySlime;
			if (evt.getEntityLiving().getRNG().nextInt(6 + (s ? 16 : 0)) != 0) {
				return;
			}
			evt.getDrops().add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, TFItems.dustSulfur.copy()));
		}
	}

}
