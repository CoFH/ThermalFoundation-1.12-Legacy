package cofh.thermalfoundation.util;

import cofh.core.CoFHProps;
import cofh.core.util.oredict.OreDictionaryArbiter;
import cofh.lib.inventory.ComparableItemStackSafe;
import cofh.lib.util.ItemWrapper;
import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class LexiconManager {

	@SuppressWarnings ("unused")
	private static LexiconManager instance = new LexiconManager();

	private static HashSet<String> listNames = new HashSet<String>();
	private static HashSet<ItemWrapper> blacklistStacks = new HashSet<ItemWrapper>();
	private static List<String> sortedNames = new ArrayList<String>();

	public static boolean isWhitelist = true;
	public static boolean logEntries = false;
	public static boolean writeDefaultFile = true;
	public static boolean alwaysWriteFile = false;

	static File theList;

	public static void preInit() {

		String comment = "Set to true for a whitelist, FALSE for a blacklist";
		isWhitelist = ThermalFoundation.CONFIG.getConfiguration().get("Lexicon", "UseWhiteList", isWhitelist, comment).getBoolean(isWhitelist);

		comment = "This will generate a default list file depending on your list setting. This will ONLY generate if no list file already exists OR you have also enabled list regeneration.";
		writeDefaultFile = ThermalFoundation.CONFIG.getConfiguration().get("Lexicon", "GenerateDefaultList", writeDefaultFile, comment).getBoolean(writeDefaultFile);

		comment = "This option will generate a fresh blacklist or whitelist EVERY time. This is not recommended, but is provided here as an option if you are satisfied with the defaults.";
		alwaysWriteFile = ThermalFoundation.CONFIG.getConfiguration().get("Lexicon", "AlwaysGenerateList", alwaysWriteFile, comment).getBoolean(alwaysWriteFile);

		comment = "This will echo all entries to the system LOG.";
		logEntries = ThermalFoundation.CONFIG.getConfiguration().get("Lexicon", "LogEntries", logEntries, comment).getBoolean(logEntries);
	}

	public static void loadComplete() {

		generateList();
		addAllListedOres();
		sortOreNames();
	}

	@SuppressWarnings ("resource")
	public static void generateList() {

		theList = isWhitelist ? new File(CoFHProps.configDir, "/cofh/thermalfoundation/lexicon-whitelist.cfg") : new File(CoFHProps.configDir, "/cofh/thermalfoundation/lexicon-blacklist.cfg");

		boolean writingDefaultFile = false;
		BufferedWriter out = null;
		ArrayList<String> defaultList = new ArrayList<String>();

		if (writeDefaultFile && alwaysWriteFile && theList.exists()) {
			theList.delete();
		}
		if (writeDefaultFile && !theList.exists()) {
			try {
				writingDefaultFile = true;
				theList.createNewFile();
				out = new BufferedWriter(new FileWriter(theList));
			} catch (Throwable t) {
				ThermalFoundation.LOG.warn("There is an error in the " + theList.getName() + " file!");
				t.printStackTrace();
			}
		}
		if (writingDefaultFile) {
			String[] registeredOreNames = OreDictionary.getOreNames();
			for (int i = 0; i < registeredOreNames.length; i++) {
				if (isWhitelist && ComparableItemStackSafe.safeOreType(registeredOreNames[i])) {
					if (registeredOreNames[i].contains("blockCloth") || registeredOreNames[i].contains("blockGlass")) {
						// ignore Cloth and Glass
					} else {
						listNames.add(registeredOreNames[i]);
						defaultList.add(registeredOreNames[i]);
					}
				} else if (!isWhitelist && !ComparableItemStackSafe.safeOreType(registeredOreNames[i]) || registeredOreNames[i].contains("blockCloth") || registeredOreNames[i].contains("blockGlass")) {
					listNames.add(registeredOreNames[i]);
					defaultList.add(registeredOreNames[i]);
				}
			}
			Collections.sort(defaultList);
			if (isWhitelist) {
				ThermalFoundation.LOG.info("[Whitelist] Generating Default Whitelist.");
			} else {
				ThermalFoundation.LOG.info("[Blacklist] Generating Default Blacklist.");
			}
			try {
				for (int i = 0; i < defaultList.size(); i++) {
					out.write(defaultList.get(i) + "\n");
				}
				out.close();
				defaultList.clear();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	public static void addAllListedOres() {

		try {
			if (!theList.exists()) {
				return;
			}
			if (isWhitelist) {
				ThermalFoundation.LOG.info("[Whitelist] Reading established Whitelist from file.");
			} else {
				ThermalFoundation.LOG.info("[Blacklist] Reading established Blacklist from file.");
			}
			Scanner scan = new Scanner(theList);
			String[] line = null;
			String[] tokens = null;
			while (scan.hasNext()) {
				line = scan.next().split("\\n");
				tokens = line[0].split(":");

				if (tokens.length == 1) {
					listNames.add(line[0]);
					if (logEntries) {
						if (isWhitelist) {
							ThermalFoundation.LOG.info("[Whitelist] The Forge Lexicon will allow conversions for ALL items of type '" + line[0] + "'.");
						} else {
							ThermalFoundation.LOG.info("[Blacklist] The Forge Lexicon will disable conversions for ALL items of type '" + line[0] + "'.");
						}
					}
				}
			}
			scan.close();
		} catch (Throwable t) {
			ThermalFoundation.LOG.warn("There is an error in the " + theList.getName() + " file!");
			t.printStackTrace();
		}
	}

	public static void sortOreNames() {

		String[] ores = OreDictionary.getOreNames();

		for (int i = 0; i < ores.length; i++) {
			if (validType(ores[i]) && OreDictionaryArbiter.getOres(ores[i]) != null) {
				sortedNames.add(ores[i]);
			}
		}
		Collections.sort(sortedNames);
	}

	public static List<String> getSortedOreNames() {

		return sortedNames;
	}

	public static boolean validOre(ItemStack stack) {

		if (blacklistStacks.contains(new ItemWrapper(stack)) || ItemHelper.getItemDamage(stack) == OreDictionary.WILDCARD_VALUE) {
			return false;
		}
		return ItemHelper.hasOreName(stack) ? isWhitelist == listNames.contains(OreDictionaryArbiter.getOreName(stack)) : false;
	}

	public static boolean validType(String oreName) {

		return isWhitelist == listNames.contains(oreName);
	}

	/* Player Interaction */
	public static ItemStack getPreferredStack(EntityPlayer player, ItemStack stack) {

		NBTTagCompound tag = player.getEntityData();
		if (tag.hasKey("cofh.Lexicon")) {
			NBTTagCompound lexicon = tag.getCompoundTag("cofh.Lexicon");
			String oreName = OreDictionaryArbiter.getOreName(stack);

			if (lexicon.hasKey(oreName)) {
				ItemStack retStack = ItemStack.loadItemStackFromNBT(lexicon.getCompoundTag(oreName));
				if (ItemHelper.isOreNameEqual(retStack, oreName)) {
					return ItemHelper.cloneStack(retStack, stack.stackSize);
				}
			}
		}
		ItemStack defaultStack = OreDictionaryArbiter.getOres(stack).get(0);

		if (ItemHelper.getItemDamage(defaultStack) == OreDictionary.WILDCARD_VALUE) {
			return stack;
		}
		return ItemHelper.cloneStack(defaultStack, stack.stackSize);
	}

	public static ItemStack getPreferredStack(EntityPlayer player, String oreName) {

		NBTTagCompound tag = player.getEntityData();
		if (tag.hasKey("cofh.Lexicon")) {
			NBTTagCompound lexicon = tag.getCompoundTag("cofh.Lexicon");

			if (lexicon.hasKey(oreName)) {
				ItemStack retStack = ItemStack.loadItemStackFromNBT(lexicon.getCompoundTag(oreName));
				if (ItemHelper.isOreNameEqual(retStack, oreName)) {
					return ItemHelper.cloneStack(retStack, 1);
				}
			}
		}
		return ItemHelper.cloneStack(OreDictionaryArbiter.getOres(oreName).get(0), 1);
	}

	public static void setPreferredStack(EntityPlayer player, ItemStack stack) {

		NBTTagCompound tag = player.getEntityData();

		NBTTagCompound lexicon = tag.getCompoundTag("cofh.Lexicon");
		String oreName = OreDictionaryArbiter.getOreName(stack);
		lexicon.setTag(oreName, stack.writeToNBT(new NBTTagCompound()));

		tag.setTag("cofh.Lexicon", lexicon);
	}

	public static void clearPreferredStack(EntityPlayer player, ItemStack stack) {

		NBTTagCompound tag = player.getEntityData();

		NBTTagCompound lexicon = tag.getCompoundTag("cofh.Lexicon");
		String oreName = OreDictionaryArbiter.getOreName(stack);
		lexicon.removeTag(oreName);

		tag.setTag("cofh.Lexicon", lexicon);
	}

	public static boolean hasPreferredStack(EntityPlayer player, String oreName) {

		NBTTagCompound tag = player.getEntityData();
		NBTTagCompound lexicon = tag.getCompoundTag("cofh.Lexicon");

		return lexicon.hasKey(oreName);
	}

	/* Entry Management */
	public static boolean addBlacklistEntry(ItemStack stack) {

		if (stack == null) {
			return false;
		}
		return blacklistStacks.add(new ItemWrapper(stack));
	}

	public static boolean removeBlacklistEntry(ItemStack stack) {

		if (stack == null) {
			return false;
		}
		return blacklistStacks.remove(new ItemWrapper(stack));
	}
}
