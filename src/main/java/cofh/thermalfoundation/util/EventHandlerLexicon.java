package cofh.thermalfoundation.util;

import cofh.thermalfoundation.init.TFProps;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerLexicon {

	public static EventHandlerLexicon INSTANCE = new EventHandlerLexicon();

	public static void initialize() {

		MinecraftForge.EVENT_BUS.register(INSTANCE);
	}

	@SubscribeEvent (priority = EventPriority.HIGHEST)
	public void handleEntityItemPickupEvent(EntityItemPickupEvent event) {

		ItemStack stack = event.getItem().getItem();
		if (stack.isEmpty() || !LexiconManager.validOre(stack)) {
			return;
		}
		NBTTagCompound tag = event.getEntityPlayer().getEntityData(); // Cannot be null
		if (event.getEntityPlayer().world.getTotalWorldTime() - tag.getLong(TFProps.LEXICON_TIMER) > 20) {
			return;
		}
		event.setResult(Event.Result.DENY);
		ItemStack lexiconStack = LexiconManager.getPreferredStack(event.getEntityPlayer(), stack);

		if (!event.getEntityPlayer().inventory.addItemStackToInventory(lexiconStack)) {
			stack.setCount(lexiconStack.getCount());
			event.getItem().setItem(stack);
			return;
		}
		stack.setCount(0);
		FMLCommonHandler.instance().firePlayerItemPickupEvent(event.getEntityPlayer(), event.getItem());

		if (stack.getCount() <= 0) {
			event.getItem().setDead();
		}
	}

	@SubscribeEvent (priority = EventPriority.HIGHEST)
	public void handlePlayerCloneEvent(PlayerEvent.Clone event) {

		NBTTagCompound newTag = event.getEntityPlayer().getEntityData();
		NBTTagCompound oldTag = event.getOriginal().getEntityData();

		if (oldTag.hasKey(TFProps.LEXICON_DATA)) {
			newTag.setTag(TFProps.LEXICON_DATA, oldTag.getCompoundTag(TFProps.LEXICON_DATA));
		}
	}

}
