package cofh.thermalfoundation.proxy;

import cofh.core.util.helpers.ItemHelper;
import cofh.core.util.helpers.MathHelper;
import cofh.thermalfoundation.init.TFProps;
import cofh.thermalfoundation.item.ItemMaterial;
import cofh.thermalfoundation.item.tome.ItemTomeExperience;
import cofh.thermalfoundation.util.LexiconManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
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

		ItemStack stack = event.getItem().getItem();
		if (stack.isEmpty() || !LexiconManager.validOre(stack)) {
			return;
		}
		EntityPlayer player = event.getEntityPlayer();
		NBTTagCompound tag = player.getEntityData(); // Cannot be null
		if (player.world.getTotalWorldTime() - tag.getLong(TFProps.LEXICON_TIMER) > 20) {
			return;
		}
		ItemStack lexiconStack = LexiconManager.getPreferredStack(player, stack);
		if (!player.inventory.addItemStackToInventory(lexiconStack)) {
			stack.setCount(lexiconStack.getCount());
			event.getItem().setItem(stack);
			return;
		}
		stack.setCount(0);
		FMLCommonHandler.instance().firePlayerItemPickupEvent(player, event.getItem());
		if (stack.getCount() <= 0) {
			event.getItem().setDead();
		}
		event.setCanceled(true);
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
		ItemStack tome = findExpTome(player);
		if (tome.isEmpty()) {
			return;
		}
		EntityXPOrb orb = event.getOrb();
		ItemTomeExperience.modifyExperience(tome, orb.xpValue);
		player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.1F, (MathHelper.RANDOM.nextFloat() - MathHelper.RANDOM.nextFloat()) * 0.35F + 0.9F);
		orb.setDead();

		event.setCanceled(true);
	}

	/* HELPERS */
	public ItemStack findExpTome(EntityPlayer player) {

		ItemStack offHand = player.getHeldItemOffhand();
		ItemStack mainHand = player.getHeldItemMainhand();
		if (ItemHelper.itemsEqualWithMetadata(offHand, ItemTomeExperience.tomeExperience) && ItemTomeExperience.getExperience(offHand) < ItemTomeExperience.getMaxExperience(offHand)) {
			return offHand;
		} else if (ItemHelper.itemsEqualWithMetadata(mainHand, ItemTomeExperience.tomeExperience) && ItemTomeExperience.getExperience(mainHand) < ItemTomeExperience.getMaxExperience(mainHand)) {
			return mainHand;
		}
		for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack stack = player.inventory.getStackInSlot(i);
			if (ItemHelper.itemsEqualWithMetadata(stack, ItemTomeExperience.tomeExperience) && ItemTomeExperience.getExperience(stack) < ItemTomeExperience.getMaxExperience(stack)) {
				return stack;
			}
		}
		return ItemStack.EMPTY;
	}

}
