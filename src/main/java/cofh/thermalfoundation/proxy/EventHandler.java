package cofh.thermalfoundation.proxy;

import cofh.thermalfoundation.init.TFProps;
import cofh.thermalfoundation.item.ItemMaterial;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {

	public static final EventHandler INSTANCE = new EventHandler();

	@SubscribeEvent
	public void handleLivingDropsEvent(LivingDropsEvent event) {

		Entity entity = event.getEntity();

		if (entity.isImmuneToFire() && TFProps.dropSulfurFireImmuneMobs && event.getEntityLiving().world.getGameRules().getBoolean("doMobLoot")) {
			boolean s = entity instanceof EntitySlime;
			if (event.getEntityLiving().getRNG().nextInt(6 + (s ? 16 : 0)) != 0) {
				return;
			}
			event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, ItemMaterial.dustSulfur.copy()));
		}
	}

}
