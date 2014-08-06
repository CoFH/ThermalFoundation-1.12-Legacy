package thermalfoundation.util;

import cofh.lib.util.helpers.MathHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public class EventHandlerLexicon {

	public static EventHandlerLexicon instance = new EventHandlerLexicon();

	public static void initialize() {

		MinecraftForge.EVENT_BUS.register(instance);
	}

	@SubscribeEvent
	public void handleEntityItemPickupEvent(EntityItemPickupEvent event) {

		ItemStack stack = event.item.getEntityItem();
		if (stack == null) {
			return;
		}
		if (!LexiconManager.validOre(stack)) {
			return;
		}
		NBTTagCompound tag = event.entityPlayer.getEntityData(); // Cannot be null
		if (event.entityPlayer.worldObj.getTotalWorldTime() - tag.getLong("LexiconUpdate") > 20) {
			return;
		}
		event.setResult(Event.Result.DENY);
		ItemStack lexiconStack = LexiconManager.getPreferredStack(event.entityPlayer, stack);

		if (!event.entityPlayer.inventory.addItemStackToInventory(lexiconStack)) {
			stack.stackSize = lexiconStack.stackSize;
			event.item.setEntityItemStack(stack);
			return;
		}
		stack.stackSize = 0;

		if (stack.getItem() == Item.getItemFromBlock(Blocks.log)) {
			event.entityPlayer.triggerAchievement(AchievementList.mineWood);
		} else if (stack.getItem() == Item.getItemFromBlock(Blocks.log2)) {
			event.entityPlayer.triggerAchievement(AchievementList.mineWood);
		} else if (stack.getItem() == Items.leather) {
			event.entityPlayer.triggerAchievement(AchievementList.killCow);
		} else if (stack.getItem() == Items.diamond) {
			event.entityPlayer.triggerAchievement(AchievementList.diamonds);
		} else if (stack.getItem() == Items.blaze_rod) {
			event.entityPlayer.triggerAchievement(AchievementList.blazeRod);
		}
		FMLCommonHandler.instance().firePlayerItemPickupEvent(event.entityPlayer, event.item);

		event.item.playSound("note.harp", 0.5F, 1.0F);
		event.entityPlayer.onItemPickup(event.item, stack.stackSize);
		event.entityPlayer.worldObj.spawnParticle("enchantmenttable", event.item.posX, event.item.posY, event.item.posZ, MathHelper.RANDOM.nextFloat(),
				MathHelper.RANDOM.nextFloat(), MathHelper.RANDOM.nextFloat());

		if (stack.stackSize <= 0) {
			event.item.setDead();
		}
	}

}
