package cofh.thermalfoundation.init;

import cofh.CoFHCore;
import cofh.core.gui.CreativeTabCore;
import cofh.core.init.CoreProps;
import cofh.core.network.PacketBase;
import cofh.core.util.CoreUtils;
import cofh.core.util.TimeTracker;
import cofh.core.util.helpers.MathHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.block.BlockOre;
import cofh.thermalfoundation.init.TFEquipment.ArmorSet;
import cofh.thermalfoundation.init.TFEquipment.ToolSet;
import cofh.thermalfoundation.item.tome.ItemTomeLexicon;
import cofh.thermalfoundation.network.PacketTFBase;
import cofh.thermalfoundation.network.PacketTFBase.PacketTypes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.core.util.Loader;

import java.io.File;
import java.util.ArrayList;

public class TFProps {

	private TFProps() {

	}

	public static void preInit() {

		configCommon();
		configClient();

		addWorldGeneration();
	}

	/* HELPERS */
	private static void configCommon() {

		String category;
		String comment;

		/* GENERAL */
		category = "General";

		comment = "If TRUE, Fire-Immune mobs have a chance to drop Sulfur.";
		dropSulfurFireImmuneMobs = ThermalFoundation.CONFIG.getConfiguration().getBoolean("FireImmuneMobsDropSulfur", category, dropSulfurFireImmuneMobs, comment);

		/* CRAFTING */
		comment = "If TRUE, recipes for Basic Craftable Items (Gears, Parts) are disabled. Only enable this option if you understand the consequences.";
		disableBasicItems = ThermalFoundation.CONFIG.getConfiguration().getBoolean("DisableBasicItemRecipes", category, disableBasicItems, comment);

		comment = "If TRUE, recipes for Upgrade Items (Kits) are disabled. Only enable this option if you understand the consequences.";
		disableUpgradeItems = ThermalFoundation.CONFIG.getConfiguration().getBoolean("DisableUpgradeItemRecipes", category, disableUpgradeItems, comment);

		comment = "If TRUE, non-Basic Gears will require an additional Iron Ingot to craft";
		enableAlternateGears = ThermalFoundation.CONFIG.getConfiguration().getBoolean("AlternateGears", category, enableAlternateGears, comment);

		comment = "If TRUE, Basic (Wood and Stone) Gears will be craftable.";
		enableBasicGears = ThermalFoundation.CONFIG.getConfiguration().getBoolean("BasicGears", category, enableBasicGears, comment);

		comment = "If TRUE, Pyrotheum Dust can be used to smelt Ores into Ingots.";
		enablePyrotheumCrafting = ThermalFoundation.CONFIG.getConfiguration().getBoolean("EnablePyrotheumCrafting", category, enablePyrotheumCrafting, comment);

		comment = "If TRUE, Petrotheum Dust can be used to break Ores into Dusts and Gems.";
		enablePetrotheumCrafting = ThermalFoundation.CONFIG.getConfiguration().getBoolean("EnablePetrotheumCrafting", category, enablePetrotheumCrafting, comment);

		comment = "If TRUE, Cryotheum Dust can be used to create Ice and solidify Clathrates.";
		enableCryotheumCrafting = ThermalFoundation.CONFIG.getConfiguration().getBoolean("EnableCryotheumCrafting", category, enableCryotheumCrafting, comment);

		comment = "If TRUE, Horse Armor will be craftable.";
		enableHorseArmorCrafting = ThermalFoundation.CONFIG.getConfiguration().getBoolean("EnableHorseArmorCrafting", category, enableHorseArmorCrafting, comment);

		comment = "If TRUE, Saddles will be craftable.";
		enableSaddleCrafting = ThermalFoundation.CONFIG.getConfiguration().getBoolean("EnableSaddleCrafting", category, enableSaddleCrafting, comment);

		/* EQUIPMENT */
		category = "Equipment";
		comment = "If TRUE, recipes for all Armor Sets are disabled.";
		disableAllArmor = ThermalFoundation.CONFIG.getConfiguration().getBoolean("DisableAllArmorRecipes", category, disableAllArmor, comment);

		comment = "If TRUE, recipes for all Tools are disabled.";
		disableAllTools = ThermalFoundation.CONFIG.getConfiguration().getBoolean("DisableAllToolRecipes", category, disableAllTools, comment);

		comment = "If TRUE, recipes for new Vanilla material (Wood, Stone, Iron, Gold, Diamond) Tools are disabled.";
		disableVanillaTools = ThermalFoundation.CONFIG.getConfiguration().getBoolean("DisableVanillaToolRecipes", category, disableVanillaTools, comment);

		comment = "If TRUE, recipes for all new Bows will be disabled, leaving only the Vanilla Bow";
		disableAllBows = ThermalFoundation.CONFIG.getConfiguration().getBoolean("DisableAllBows", category, disableAllBows, comment);

		comment = "If TRUE, recipes for all new Shears will be disabled, leaving only the Vanilla (Iron) Shears.";
		disableAllShears = ThermalFoundation.CONFIG.getConfiguration().getBoolean("DisableAllShears", category, disableAllShears, comment);

		comment = "If TRUE, recipes for all new Fishing Rods will be disabled, leaving only the Vanilla (Wood) Fishing Rod";
		disableAllFishingRods = ThermalFoundation.CONFIG.getConfiguration().getBoolean("DisableAllFishingRods", category, disableAllFishingRods, comment);

		comment = "If TRUE, recipes for all new Shields will be disabled, leaving only the Vanilla (Wood) Shield";
		disableAllShields = ThermalFoundation.CONFIG.getConfiguration().getBoolean("DisableAllShields", category, disableAllShields, comment);

		comment = "If TRUE, items which have had their recipes disabled will show in the Creative Tab and JEI.";
		showDisabledEquipment = ThermalFoundation.CONFIG.getConfiguration().getBoolean("ShowDisabledEquipment", category, showDisabledEquipment, comment);
	}

	private static void configClient() {

		String category;
		String comment;

		/* GRAPHICS */
		category = "Render";

		//		comment = "If TRUE, Blaze Powder uses a custom icon.";
		//		iconBlazePowder = ThermalFoundation.CONFIG_CLIENT.getConfiguration().getBoolean("BlazePowder", category, iconBlazePowder, comment);

		comment = "If TRUE, Ender devices will be a bit more Cagey year-round.";
		renderStarfieldCage = ThermalFoundation.CONFIG_CLIENT.getConfiguration().getBoolean("CageyEnder", category, renderStarfieldCage, comment);

		boolean utilTabCommon = false;
		boolean toolTabCommon = false;
		boolean armorTabCommon = false;

		category = "Global";

		comment = "If TRUE, Creative versions of Items will show in Creative Tabs.";
		showCreativeItems = ThermalFoundation.CONFIG_CLIENT.getConfiguration().getBoolean("ShowCreativeItems", category, showCreativeItems, comment);

		comment = "If TRUE, Empty versions of Items which contain a specific resource (such as RF or Water) will show in Creative Tabs.";
		showEmptyItems = ThermalFoundation.CONFIG_CLIENT.getConfiguration().getBoolean("ShowEmptyItems", category, showEmptyItems, comment);

		comment = "If TRUE, Full versions of Items which contain a specific resource (such as RF or Water) will show in Creative Tabs.";
		showFullItems = ThermalFoundation.CONFIG_CLIENT.getConfiguration().getBoolean("ShowFullItems", category, showFullItems, comment);

		comment = "If TRUE, all Thermal Series mods will be share common pre-configured \"Thermal Series\" Creative Tabs. Basic Armor and Basic Tools will go to appropriate vanilla Creative Tabs.";
		useUnifiedTabs = ThermalFoundation.CONFIG_CLIENT.getConfiguration().getBoolean("ThermalSeriesTabs", category, useUnifiedTabs, comment);

		category = "Interface";

		comment = "If TRUE, Thermal Foundation Utility Items appear under the general \"Thermal Foundation\" Creative Tab. Does not work if \"Thermal Series\" Creative Tabs are in use.";
		utilTabCommon = ThermalFoundation.CONFIG_CLIENT.getConfiguration().getBoolean("UtilsInCommonTab", category, utilTabCommon, comment);

		comment = "If TRUE, Thermal Foundation Basic Tools appear under the general \"Thermal Foundation\" Creative Tab. Does not work if \"Thermal Series\" Creative Tabs are in use.";
		toolTabCommon = ThermalFoundation.CONFIG_CLIENT.getConfiguration().getBoolean("ToolsInCommonTab", category, toolTabCommon, comment);

		comment = "If TRUE, Thermal Foundation Basic Armor Sets appear under the general \"Thermal Foundation\" Creative Tab. Does not work if \"Thermal Series\" Creative Tabs are in use.";
		armorTabCommon = ThermalFoundation.CONFIG_CLIENT.getConfiguration().getBoolean("ArmorInCommonTab", category, armorTabCommon, comment);

		/* CREATIVE TABS */
		if (useUnifiedTabs) {
			initCommonTab();
			initItemTab();
			initUtilTab();

			ThermalFoundation.tabBasicTools = CreativeTabs.TOOLS;
			ThermalFoundation.tabBasicCombat = CreativeTabs.COMBAT;
			ThermalFoundation.tabBasicArmor = CreativeTabs.COMBAT;
		} else {
			ThermalFoundation.tabCommon = new CreativeTabCore("thermalfoundation") {

				@Override
				@SideOnly (Side.CLIENT)
				public ItemStack getTabIconItem() {

					return BlockOre.oreNickel;
				}
			};
			if (utilTabCommon) {
				ThermalFoundation.tabUtils = ThermalFoundation.tabCommon;
			} else {
				ThermalFoundation.tabUtils = new CreativeTabCore("thermalfoundation", "Utils") {

					@Override
					@SideOnly (Side.CLIENT)
					public ItemStack getTabIconItem() {

						return ItemTomeLexicon.tomeLexicon;
					}
				};
				if (toolTabCommon) {
					ThermalFoundation.tabBasicTools = ThermalFoundation.tabCommon;
				} else {
					ThermalFoundation.tabBasicTools = new CreativeTabCore("thermalfoundation", "Tools") {

						@Override
						@SideOnly (Side.CLIENT)
						public ItemStack getTabIconItem() {

							return ToolSet.INVAR.toolPickaxe;
						}
					};
				}
				if (armorTabCommon) {
					ThermalFoundation.tabBasicArmor = ThermalFoundation.tabCommon;
				} else {
					ThermalFoundation.tabBasicArmor = new CreativeTabCore("thermalfoundation", "Armor") {

						@Override
						@SideOnly (Side.CLIENT)
						public ItemStack getTabIconItem() {

							return ArmorSet.INVAR.armorChestplate;
						}
					};
				}
				ThermalFoundation.tabItems = ThermalFoundation.tabCommon;
				ThermalFoundation.tabMisc = ThermalFoundation.tabCommon;
				ThermalFoundation.tabBasicCombat = ThermalFoundation.tabBasicTools;
			}
		}
	}

	public static void initCommonTab() {

		if (!useUnifiedTabs || ThermalFoundation.tabCommon != null) {
			return;
		}
		ThermalFoundation.tabCommon = new CreativeTabCore("thermalseries", "Blocks") {

			int iconIndex = 0;
			TimeTracker iconTracker = new TimeTracker();

			public void updateIcon() {

				World world = CoFHCore.proxy.getClientWorld();
				if (CoreUtils.isClient() && iconTracker.hasDelayPassed(world, 80)) {
					iconIndex = MathHelper.RANDOM.nextInt(blockList.size());
					iconTracker.markTime(world);
				}
			}

			@Override
			@SideOnly (Side.CLIENT)
			public ItemStack getTabIconItem() {

				if (blockList.isEmpty()) {
					return ItemStack.EMPTY;
				}
				updateIcon();
				return blockList.get(iconIndex);
			}
		};
	}

	public static void initItemTab() {

		if (!useUnifiedTabs || ThermalFoundation.tabItems != null) {
			return;
		}
		ThermalFoundation.tabItems = new CreativeTabCore("thermalseries", "Items") {

			int iconIndex = 0;
			TimeTracker iconTracker = new TimeTracker();

			public void updateIcon() {

				World world = CoFHCore.proxy.getClientWorld();
				if (CoreUtils.isClient() && iconTracker.hasDelayPassed(world, 80)) {
					iconIndex = MathHelper.RANDOM.nextInt(itemList.size());
					iconTracker.markTime(world);
				}
			}

			@Override
			@SideOnly (Side.CLIENT)
			public ItemStack getTabIconItem() {

				if (itemList.isEmpty()) {
					return ItemStack.EMPTY;
				}
				updateIcon();
				return itemList.get(iconIndex);
			}
		};
	}

	public static void initUtilTab() {

		if (!useUnifiedTabs || ThermalFoundation.tabUtils != null) {
			return;
		}
		ThermalFoundation.tabUtils = new CreativeTabCore("thermalseries", "Utils") {

			int iconIndex = 0;
			TimeTracker iconTracker = new TimeTracker();

			public void updateIcon() {

				World world = CoFHCore.proxy.getClientWorld();
				if (CoreUtils.isClient() && iconTracker.hasDelayPassed(world, 80)) {
					iconIndex = MathHelper.RANDOM.nextInt(utilList.size());
					iconTracker.markTime(world);
				}
			}

			@Override
			@SideOnly (Side.CLIENT)
			public ItemStack getTabIconItem() {

				if (utilList.isEmpty()) {
					return ItemStack.EMPTY;
				}
				updateIcon();
				return utilList.get(iconIndex);
			}
		};
	}

	public static void initToolTab() {

		if (!useUnifiedTabs || ThermalFoundation.tabTools != null) {
			return;
		}
		ThermalFoundation.tabTools = new CreativeTabCore("thermalseries", "Tools") {

			int iconIndex = 0;
			TimeTracker iconTracker = new TimeTracker();

			public void updateIcon() {

				World world = CoFHCore.proxy.getClientWorld();
				if (CoreUtils.isClient() && iconTracker.hasDelayPassed(world, 80)) {
					iconIndex = MathHelper.RANDOM.nextInt(toolList.size());
					iconTracker.markTime(world);
				}
			}

			@Override
			@SideOnly (Side.CLIENT)
			public ItemStack getTabIconItem() {

				if (toolList.isEmpty()) {
					return ItemStack.EMPTY;
				}
				updateIcon();
				return toolList.get(iconIndex);
			}
		};
	}

	public static void initMiscTab() {

		if (!useUnifiedTabs || ThermalFoundation.tabMisc != null) {
			return;
		}
		ThermalFoundation.tabMisc = new CreativeTabCore("thermalseries", "Misc") {

			int iconIndex = 0;
			TimeTracker iconTracker = new TimeTracker();

			public void updateIcon() {

				World world = CoFHCore.proxy.getClientWorld();
				if (CoreUtils.isClient() && iconTracker.hasDelayPassed(world, 80)) {
					iconIndex = MathHelper.RANDOM.nextInt(miscList.size());
					iconTracker.markTime(world);
				}
			}

			@Override
			@SideOnly (Side.CLIENT)
			public ItemStack getTabIconItem() {

				if (miscList.isEmpty()) {
					return ItemStack.EMPTY;
				}
				updateIcon();
				return miscList.get(iconIndex);
			}
		};
	}

	private static void addWorldGeneration() {

		File worldGenFile;
		String worldGenPath = "assets/" + ThermalFoundation.MOD_ID + "/world/";

		String worldGenOre = "01_thermalfoundation_ores.json";
		String worldGenOil = "02_thermalfoundation_oil.json";
		String worldGenClathrates = "03_thermalfoundation_clathrates.json";

		String category = "World";
		String comment = "If TRUE, Thermal Foundation will create default world generation files if it cannot find existing ones. These files will only work if CoFH World is installed! Only disable this if you know what you are doing.";

		boolean generateDefaultFiles = ThermalFoundation.CONFIG.getConfiguration().getBoolean("GenerateDefaultFiles", category, true, comment);

		if (!generateDefaultFiles) {
			return;
		}
		worldGenFile = new File(CoreProps.configDir, "/cofh/world/" + worldGenOre);
		if (!worldGenFile.exists()) {
			try {
				worldGenFile.createNewFile();
				FileUtils.copyInputStreamToFile(Loader.getResource(worldGenPath + worldGenOre, null).openStream(), worldGenFile);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}

		worldGenFile = new File(CoreProps.configDir, "/cofh/world/" + worldGenOil);
		if (!worldGenFile.exists()) {
			try {
				worldGenFile.createNewFile();
				FileUtils.copyInputStreamToFile(Loader.getResource(worldGenPath + worldGenOil, null).openStream(), worldGenFile);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}

		worldGenFile = new File(CoreProps.configDir, "/cofh/world/" + worldGenClathrates);
		if (!worldGenFile.exists()) {
			try {
				worldGenFile.createNewFile();
				FileUtils.copyInputStreamToFile(Loader.getResource(worldGenPath + worldGenClathrates, null).openStream(), worldGenFile);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	public static PacketBase getConfigSync() {

		PacketBase payload = PacketTFBase.getPacket(PacketTypes.CONFIG_SYNC);

		return payload;
	}

	public static void handleConfigSync(PacketBase payload) {

	}

	/* GENERAL */
	public static final String EXPERIENCE_TIMER = "thermalfoundation.experience_timer";

	public static final String LEXICON_TIMER = "thermalfoundation.lexicon_timer";
	public static final String LEXICON_DATA = "thermalfoundation.lexicon_data";

	//	public static final int MAX_EXP_LEVEL = 100;
	//	public static final int MAX_EXP = (9 * MAX_EXP_LEVEL * MAX_EXP_LEVEL - 325 * MAX_EXP_LEVEL + 4440) / 2;

	public static boolean dropSulfurFireImmuneMobs = true;

	/* CRAFTING */
	public static boolean enablePyrotheumCrafting = true;
	public static boolean enablePetrotheumCrafting = true;
	public static boolean enableCryotheumCrafting = true;

	public static boolean enableAlternateGears = false;
	public static boolean enableBasicGears = true;

	public static boolean enableHorseArmorCrafting = true;
	public static boolean enableSaddleCrafting = true;

	public static int fuelCokeFuel = 3200;
	public static int globRosinFuel = 800;
	public static int globTarFuel = 800;
	public static int dustPyrotheumFuel = 24000;

	/* INTERFACE */
	public static boolean disableBasicItems = false;
	public static boolean disableUpgradeItems = false;

	public static boolean disableAllTools = false;
	public static boolean disableAllArmor = false;
	public static boolean disableVanillaTools = false;

	public static boolean disableAllBows = false;
	public static boolean disableAllFishingRods = false;
	public static boolean disableAllShears = false;
	public static boolean disableAllShields = false;

	public static boolean showDisabledEquipment = false;
	public static boolean showCreativeItems = true;
	public static boolean showEmptyItems = false;
	public static boolean showFullItems = true;
	public static boolean useUnifiedTabs = true;

	public static ArrayList<ItemStack> blockList = new ArrayList<>();
	public static ArrayList<ItemStack> itemList = new ArrayList<>();
	public static ArrayList<ItemStack> utilList = new ArrayList<>();
	public static ArrayList<ItemStack> toolList = new ArrayList<>();
	public static ArrayList<ItemStack> miscList = new ArrayList<>();

	/* RENDER */
	public static boolean renderStarfieldCage = false;

}
