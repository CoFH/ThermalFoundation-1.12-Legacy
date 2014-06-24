package thermalfoundation.util;

import cofh.util.ItemHelper;
import cofh.util.ItemWrapper;
import cofh.util.oredict.OreDictionaryArbiter;

import java.io.File;
import java.util.HashSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class LexiconManager {

	private static LexiconManager instance = new LexiconManager();

	private static HashSet<String> listNames = new HashSet();
	private static HashSet<ItemWrapper> listOres = new HashSet();

	public static boolean isWhitelist = true;
	public static boolean logEntries = false;
	public static boolean writeDefaultFile = true;

	static File theList;

	public static void initialize() {

	}

	public static boolean validOre(ItemStack stack) {

		return ItemHelper.hasOreName(stack) ? isWhitelist == (listOres.contains(new ItemWrapper(stack)) || listNames.contains(OreDictionaryArbiter
				.getOreName(stack))) : false;
	}

	public static ItemStack getPreferredStack(EntityPlayer player, ItemStack stack) {

		NBTTagCompound tag = player.getEntityData();
		if (tag.hasKey("Lexicon")) {
			NBTTagCompound lexicon = tag.getCompoundTag("Lexicon");
			String oreName = OreDictionaryArbiter.getOreName(stack);

			if (lexicon.hasKey(oreName)) {
				ItemStack retStack = ItemStack.loadItemStackFromNBT(lexicon.getCompoundTag(oreName));
				if (ItemHelper.isOreNameEqual(retStack, oreName)) {
					return ItemHelper.cloneStack(retStack, stack.stackSize);
				}
			}
		}
		return ItemHelper.cloneStack(OreDictionaryArbiter.getOres(stack).get(0), stack.stackSize);
	}

	public static void setPreferredStack(EntityPlayer player, ItemStack stack) {

		NBTTagCompound tag = player.getEntityData();

		NBTTagCompound lexicon = tag.getCompoundTag("Lexicon");
		String oreName = OreDictionaryArbiter.getOreName(stack);
		lexicon.setTag(oreName, stack.writeToNBT(new NBTTagCompound()));

		tag.setTag("Lexicon", lexicon);
	}

}
