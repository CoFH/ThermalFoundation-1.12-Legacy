package cofh.thermalfoundation.util;

import cofh.core.init.CoreProps;
import cofh.core.inventory.ComparableItemStack;
import cofh.core.util.ItemWrapper;
import cofh.core.util.helpers.ItemHelper;
import cofh.core.util.oredict.OreDictionaryArbiter;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.init.TFProps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class LexiconManager {

	private LexiconManager() {

	}

	public static void initialize() {

		config();
	}

	public static void config() {

		String category = "Lexicon";
		String comment;

		comment = "If TRUE, a WHITELIST is used, if FALSE, a BLACKLIST is used.";
		isWhitelist = ThermalFoundation.CONFIG.getConfiguration().getBoolean("UseWhiteList", category, isWhitelist, comment);

		comment = "If TRUE, a default list will be generated depending on your list setting. This will ONLY generate if no list file already exists OR the Always Generate option is enabled.";
		writeDefaultFile = ThermalFoundation.CONFIG.getConfiguration().getBoolean("GenerateDefaultList", category, writeDefaultFile, comment);

		comment = "If TRUE, a default list will generate EVERY time. Enable this if you are satisfied with the default filtering and are adding/removing mods.";
		alwaysWriteFile = ThermalFoundation.CONFIG.getConfiguration().getBoolean("AlwaysGenerateList", category, alwaysWriteFile, comment);

		comment = "If TRUE, all entries will be echoed to the system LOG.";
		logEntries = ThermalFoundation.CONFIG.getConfiguration().getBoolean("LogEntries", category, logEntries, comment);
	}

	public static void loadComplete() {

		generateList();
		addAllListedOres();
		sortOreNames();
	}

	private static void generateList() {

		filterList = isWhitelist ? new File(CoreProps.configDir, "/cofh/thermalfoundation/lexicon-whitelist.cfg") : new File(CoreProps.configDir, "/cofh/thermalfoundation/lexicon-blacklist.cfg");

		boolean writingDefaultFile = false;
		BufferedWriter out = null;
		ArrayList<String> defaultList = new ArrayList<>();

		if (writeDefaultFile && alwaysWriteFile && filterList.exists()) {
			filterList.delete();
		}
		if (writeDefaultFile && !filterList.exists()) {
			try {
				writingDefaultFile = true;
				filterList.createNewFile();
				out = new BufferedWriter(new FileWriter(filterList));
			} catch (Throwable t) {
				ThermalFoundation.LOG.warn("There is an error in the " + filterList.getName() + " file!");
				t.printStackTrace();
			}
		}
		if (writingDefaultFile) {
			String[] registeredOreNames = OreDictionary.getOreNames();
			for (String oreName : registeredOreNames) {
				if (isWhitelist && ComparableItemStack.DEFAULT_VALIDATOR.validate(oreName)) {
					if (oreName.contains("blockWool") || oreName.contains("blockGlass")) {
						// ignore Wool and Glass
					} else {
						listNames.add(oreName);
						defaultList.add(oreName);
					}
				} else if (!isWhitelist && !ComparableItemStack.DEFAULT_VALIDATOR.validate(oreName) || oreName.contains("blockWool") || oreName.contains("blockGlass")) {
					listNames.add(oreName);
					defaultList.add(oreName);
				}
			}
			Collections.sort(defaultList);
			if (isWhitelist) {
				ThermalFoundation.LOG.info("[Whitelist] Generating Default Whitelist.");
			} else {
				ThermalFoundation.LOG.info("[Blacklist] Generating Default Blacklist.");
			}
			try {
				for (String listEntry : defaultList) {
					out.write(listEntry + "\n");
				}
				out.close();
				defaultList.clear();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	private static void addAllListedOres() {

		try {
			if (!filterList.exists()) {
				return;
			}
			if (isWhitelist) {
				ThermalFoundation.LOG.info("[Whitelist] Reading established Whitelist from file.");
			} else {
				ThermalFoundation.LOG.info("[Blacklist] Reading established Blacklist from file.");
			}
			Scanner scan = new Scanner(filterList);
			String[] line;
			String[] tokens;
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
			ThermalFoundation.LOG.warn("There is an error in the " + filterList.getName() + " file!");
			t.printStackTrace();
		}
	}

	private static void sortOreNames() {

		String[] ores = OreDictionary.getOreNames();

		for (String ore : ores) {
			if (validType(ore) && OreDictionaryArbiter.getOres(ore) != null) {
				sortedNames.add(ore);
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
		return ItemHelper.hasOreName(stack) && isWhitelist == listNames.contains(OreDictionaryArbiter.getOreName(stack));
	}

	private static boolean validType(String oreName) {

		return DEBUG || isWhitelist == listNames.contains(oreName);
	}

	/* PLAYER INTERACTION */
	public static ItemStack getPreferredStack(EntityPlayer player, ItemStack stack) {

		NBTTagCompound tag = player.getEntityData();

		if (tag.hasKey(TFProps.LEXICON_DATA)) {
			NBTTagCompound lexicon = tag.getCompoundTag(TFProps.LEXICON_DATA);
			String oreName = OreDictionaryArbiter.getOreName(stack);

			if (lexicon.hasKey(oreName)) {
				ItemStack retStack = new ItemStack(lexicon.getCompoundTag(oreName));
				if (ItemHelper.isOreNameEqual(retStack, oreName)) {
					return ItemHelper.cloneStack(retStack, stack.getCount());
				}
			}
		}
		ItemStack defaultStack = OreDictionaryArbiter.getOres(stack).get(0);

		if (ItemHelper.getItemDamage(defaultStack) == OreDictionary.WILDCARD_VALUE) {
			return stack;
		}
		return ItemHelper.cloneStack(defaultStack, stack.getCount());
	}

	public static ItemStack getPreferredStack(EntityPlayer player, String oreName) {

		NBTTagCompound tag = player.getEntityData();

		if (tag.hasKey(TFProps.LEXICON_DATA)) {
			NBTTagCompound lexicon = tag.getCompoundTag(TFProps.LEXICON_DATA);

			if (lexicon.hasKey(oreName)) {
				ItemStack retStack = new ItemStack(lexicon.getCompoundTag(oreName));
				if (ItemHelper.isOreNameEqual(retStack, oreName)) {
					return ItemHelper.cloneStack(retStack, 1);
				}
			}
		}
		return ItemHelper.cloneStack(OreDictionaryArbiter.getOres(oreName).get(0), 1);
	}

	public static void setPreferredStack(EntityPlayer player, ItemStack stack) {

		NBTTagCompound tag = player.getEntityData();

		NBTTagCompound lexicon = tag.getCompoundTag(TFProps.LEXICON_DATA);
		String oreName = OreDictionaryArbiter.getOreName(stack);
		lexicon.setTag(oreName, stack.writeToNBT(new NBTTagCompound()));

		tag.setTag(TFProps.LEXICON_DATA, lexicon);
	}

	public static void clearPreferredStack(EntityPlayer player, ItemStack stack) {

		NBTTagCompound tag = player.getEntityData();

		NBTTagCompound lexicon = tag.getCompoundTag(TFProps.LEXICON_DATA);
		String oreName = OreDictionaryArbiter.getOreName(stack);
		lexicon.removeTag(oreName);

		tag.setTag(TFProps.LEXICON_DATA, lexicon);
	}

	public static boolean hasPreferredStack(EntityPlayer player, String oreName) {

		NBTTagCompound tag = player.getEntityData();
		NBTTagCompound lexicon = tag.getCompoundTag(TFProps.LEXICON_DATA);

		return lexicon.hasKey(oreName);
	}

	/* ENTRY MANAGEMENT */
	public static boolean addBlacklistEntry(ItemStack stack) {

		return !stack.isEmpty() && blacklistStacks.add(new ItemWrapper(stack));
	}

	public static boolean removeBlacklistEntry(ItemStack stack) {

		return !stack.isEmpty() && blacklistStacks.remove(new ItemWrapper(stack));
	}

	private static HashSet<String> listNames = new HashSet<>();
	private static HashSet<ItemWrapper> blacklistStacks = new HashSet<>();
	private static List<String> sortedNames = new ArrayList<>();

	private static boolean isWhitelist = true;
	private static boolean logEntries = false;
	private static boolean writeDefaultFile = true;
	private static boolean alwaysWriteFile = false;

	private static File filterList;

	private static final boolean DEBUG = false;

}
