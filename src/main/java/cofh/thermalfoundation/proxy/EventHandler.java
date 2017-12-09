package cofh.thermalfoundation.proxy;

import cofh.core.util.helpers.ItemHelper;
import cofh.thermalfoundation.init.TFProps;
import cofh.thermalfoundation.item.ItemMaterial;
import cofh.thermalfoundation.item.tome.ItemTomeExperience;
import cofh.thermalfoundation.util.LexiconManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {

	public static final EventHandler INSTANCE = new EventHandler();

	/* MOB DROPS */
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

	/* FORGE LEXICON */
	@SubscribeEvent (priority = EventPriority.HIGHEST)
	public void handleEntityItemPickupEvent(EntityItemPickupEvent event) {

		if (event.isCanceled()) {
			return;
		}
		EntityItem item = event.getItem();
		ItemStack stack = item.getItem();
		if (stack.isEmpty() || !LexiconManager.validOre(stack)) {
			return;
		}
		EntityPlayer player = event.getEntityPlayer();
		NBTTagCompound tag = player.getEntityData(); // Cannot be null
		if (player.world.getTotalWorldTime() - tag.getLong(TFProps.LEXICON_TIMER) > 20) {
			return;
		}
		ItemStack lexiconStack = LexiconManager.getPreferredStack(player, stack);
		if (ItemHelper.itemsIdentical(stack, lexiconStack)) {
			return;
		}
		item.setItem(lexiconStack.copy());
		event.setCanceled(true);
		item.onCollideWithPlayer(player);
	}

	@SubscribeEvent (priority = EventPriority.HIGHEST)
	public void handlePlayerCloneEvent(PlayerEvent.Clone event) {

		NBTTagCompound newTag = event.getEntityPlayer().getEntityData();
		NBTTagCompound oldTag = event.getOriginal().getEntityData();
		if (oldTag.hasKey(TFProps.LEXICON_DATA)) {
			newTag.setTag(TFProps.LEXICON_DATA, oldTag.getCompoundTag(TFProps.LEXICON_DATA));
		}
	}

	/* TOME OF KNOWLEDGE */
	@SubscribeEvent (priority = EventPriority.HIGH)
	public void handlePlayerPickupXpEvent(PlayerPickupXpEvent event) {

		if (event.isCanceled()) {
			return;
		}
		EntityPlayer player = event.getEntityPlayer();
		NBTTagCompound tag = player.getEntityData(); // Cannot be null
		if (player.world.getTotalWorldTime() - tag.getLong(TFProps.EXPERIENCE_TIMER) > 20) {
			return;
		}
		InventoryPlayer inventory = player.inventory;
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if (stack.getItem() instanceof ItemTomeExperience && ItemTomeExperience.onXPPickup(event, stack)) {
				event.setCanceled(true);
				return;
			}
		}
	}

}
