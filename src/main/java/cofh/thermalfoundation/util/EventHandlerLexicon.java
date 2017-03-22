package cofh.thermalfoundation.util;

import cofh.thermalfoundation.init.TFProps;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerLexicon {

	public static EventHandlerLexicon instance = new EventHandlerLexicon();

	public static void initialize() {

		MinecraftForge.EVENT_BUS.register(instance);
	}

	@SubscribeEvent (priority = EventPriority.HIGHEST)
	public void handleEntityItemPickupEvent(EntityItemPickupEvent event) {

		ItemStack stack = event.getItem().getEntityItem();
		if (stack == null || !LexiconManager.validOre(stack)) {
			return;
		}
		NBTTagCompound tag = event.getEntityPlayer().getEntityData(); // Cannot be null
		if (event.getEntityPlayer().worldObj.getTotalWorldTime() - tag.getLong(TFProps.LEXICON_TIMER) > 20) {
			return;
		}
		event.setResult(Event.Result.DENY);
		ItemStack lexiconStack = LexiconManager.getPreferredStack(event.getEntityPlayer(), stack);

		if (!event.getEntityPlayer().inventory.addItemStackToInventory(lexiconStack)) {
			stack.stackSize = lexiconStack.stackSize;
			event.getItem().setEntityItemStack(stack);
			return;
		}
		stack.stackSize = 0;

		if (stack.getItem() == Item.getItemFromBlock(Blocks.LOG)) {
			event.getEntityPlayer().addStat(AchievementList.MINE_WOOD);
		} else if (stack.getItem() == Item.getItemFromBlock(Blocks.LOG2)) {
			event.getEntityPlayer().addStat(AchievementList.MINE_WOOD);
		} else if (stack.getItem() == Items.LEATHER) {
			event.getEntityPlayer().addStat(AchievementList.KILL_COW);
		} else if (stack.getItem() == Items.DIAMOND) {
			event.getEntityPlayer().addStat(AchievementList.DIAMONDS);
		} else if (stack.getItem() == Items.BLAZE_ROD) {
			event.getEntityPlayer().addStat(AchievementList.BLAZE_ROD);
		}
		FMLCommonHandler.instance().firePlayerItemPickupEvent(event.getEntityPlayer(), event.getItem());

		if (stack.stackSize <= 0) {
			event.getItem().setDead();
		}
	}

}
