package cofh.thermalfoundation.util;

import cofh.core.CoFHProps;
import cofh.core.util.oredict.OreDictionaryArbiter;
import cofh.lib.inventory.ComparableItemStackSafe;
import cofh.lib.util.ItemWrapper;
import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalfoundation.ThermalFoundation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class LexiconManager {

	private static LexiconManager instance = new LexiconManager();

	private static HashSet<String> listNames = new HashSet<String>();
	private static HashSet<ItemWrapper> listOres = new HashSet<ItemWrapper>();

	public static boolean isWhitelist = true;
	public static boolean logEntries = false;
	public static boolean writeDefaultFile = true;

	static File theList;

	public static void preInit() {

		String comment = "Set to true for a whitelist, FALSE for a blacklist";
		isWhitelist = ThermalFoundation.config.get("Lexicon", "UseWhiteList", isWhitelist, comment);

		comment = "This will generate a default list file depending on your list setting. This will ONLY generate if no list file already exists.";
		writeDefaultFile = ThermalFoundation.config.get("Lexicon", "GenerateDefaultList", writeDefaultFile, comment);

		comment = "This will echo all entries to the system log.";
		logEntries = ThermalFoundation.config.get("Lexicon", "LogEntries", logEntries, comment);
	}

	public static void generateList() {

		theList = isWhitelist ? new File(CoFHProps.configDir, "/cofh/thermalfoundation/lexicon-whitelist.cfg") : new File(CoFHProps.configDir,
				"/cofh/thermalfoundation/lexicon-blacklist.cfg");

		boolean writingDefaultFile = false;
		BufferedWriter out = null;
		ArrayList<String> defaultList = new ArrayList<String>();

		if ((writeDefaultFile) && (!theList.exists())) {
			try {
				writingDefaultFile = true;
				theList.createNewFile();
				out = new BufferedWriter(new FileWriter(theList));
			} catch (Throwable t) {
				ThermalFoundation.log.warn("There is an error in the " + theList.getName() + " file!");
				t.printStackTrace();
			}
		}
		if (writingDefaultFile) {
			String[] registeredOreNames = OreDictionary.getOreNames();
			for (int i = 0; i < registeredOreNames.length; i++) {
				if ((isWhitelist) && (ComparableItemStackSafe.safeOreType(registeredOreNames[i]))) {
					listNames.add(registeredOreNames[i]);
					defaultList.add(registeredOreNames[i]);
				} else if ((!isWhitelist) && (!ComparableItemStackSafe.safeOreType(registeredOreNames[i]))) {
					listNames.add(registeredOreNames[i]);
					defaultList.add(registeredOreNames[i]);
				}
			}
			Collections.sort(defaultList);
			if (isWhitelist) {
				ThermalFoundation.log.info("[Whitelist] Generating Default Whitelist.");
			} else {
				ThermalFoundation.log.info("[Blacklist] Generating Default Blacklist.");
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
				ThermalFoundation.log.info("[Whitelist] Reading established Whitelist from file.");
			} else {
				ThermalFoundation.log.info("[Blacklist] Reading established Blacklist from file.");
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
							ThermalFoundation.log.info("[Whitelist] The Forge Lexicon will allow conversions for ALL items of type '" + line[0] + "'.");
						} else {
							ThermalFoundation.log.info("[Blacklist] The Forge Lexicon will disable conversions for ALL items of type '" + line[0] + "'.");
						}
					}
				}
			}
			scan.close();
		} catch (Throwable t) {
			ThermalFoundation.log.warn("There is an error in the " + theList.getName() + " file!");
			t.printStackTrace();
		}
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
