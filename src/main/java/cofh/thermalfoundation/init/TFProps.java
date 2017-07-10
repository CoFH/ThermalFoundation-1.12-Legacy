package cofh.thermalfoundation.init;

import cofh.core.gui.CreativeTabCore;
import cofh.core.init.CoreProps;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.block.BlockOre;
import cofh.thermalfoundation.init.TFEquipment.ArmorSet;
import cofh.thermalfoundation.init.TFEquipment.ToolSet;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.core.util.Loader;

import java.io.File;

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
		comment = "If TRUE, Pyrotheum Dust can be used to smelt Ores into Ingots.";
		enablePyrotheumCrafting = ThermalFoundation.CONFIG.getConfiguration().getBoolean("EnablePyrotheumSmelting", category, enablePyrotheumCrafting, comment);

		comment = "If TRUE, Petrotheum Dust can be used to break Ores into Dusts and Gems.";
		enablePetrotheumCrafting = ThermalFoundation.CONFIG.getConfiguration().getBoolean("EnablePetrotheumSmashing", category, enablePetrotheumCrafting, comment);

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

		category = "Interface";
		boolean armorTabCommon = false;
		boolean toolTabCommon = false;

		comment = "If TRUE, Thermal Foundation Armor Sets appear under the general \"Thermal Foundation\" Creative Tab.";
		armorTabCommon = ThermalFoundation.CONFIG_CLIENT.getConfiguration().getBoolean("ArmorInCommonTab", category, armorTabCommon, comment);

		comment = "If TRUE, Thermal Foundation Tools appear under the general \"Thermal Foundation\" Creative Tab.";
		toolTabCommon = ThermalFoundation.CONFIG_CLIENT.getConfiguration().getBoolean("ToolsInCommonTab", category, toolTabCommon, comment);

		/* CREATIVE TABS */
		ThermalFoundation.tabCommon = new CreativeTabCore("thermalfoundation") {

			@Override
			@SideOnly (Side.CLIENT)
			public ItemStack getIconItemStack() {

				return BlockOre.oreCopper;
			}

		};

		if (armorTabCommon) {
			ThermalFoundation.tabArmor = ThermalFoundation.tabCommon;
		} else {
			ThermalFoundation.tabArmor = new CreativeTabCore("thermalfoundation", "Armor") {

				@Override
				@SideOnly (Side.CLIENT)
				public ItemStack getIconItemStack() {

					return ArmorSet.COPPER.armorChestplate;
				}

			};
		}
		if (toolTabCommon) {
			ThermalFoundation.tabTools = ThermalFoundation.tabCommon;
		} else {
			ThermalFoundation.tabTools = new CreativeTabCore("thermalfoundation", "Tools") {

				@Override
				@SideOnly (Side.CLIENT)
				public ItemStack getIconItemStack() {

					return ToolSet.COPPER.toolPickaxe;
				}

			};
		}
	}

	private static void addWorldGeneration() {

		File worldGenFile;
		String worldGenPath = "assets/" + ThermalFoundation.MOD_ID + "/world/";

		String worldGenOre = "01_thermalfoundation_ores.json";
		String worldGenOil = "02_thermalfoundation_oil.json";
		String worldGenClathrates = "03_thermalfoundation_clathrates.json";

		if (!ThermalFoundation.CONFIG.getConfiguration().getBoolean("GenerateDefaultFiles", "World", true, "If TRUE, Thermal Foundation will create default world generation files if it cannot find existing ones. Only disable if you know what you are doing.")) {
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

	/* INTERFACE */
	public static boolean disableAllTools = false;
	public static boolean disableAllArmor = false;
	public static boolean disableVanillaTools = false;

	public static boolean disableAllBows = true;
	public static boolean disableAllFishingRods = true;
	public static boolean disableAllShears = true;
	public static boolean disableAllShields = true;

	public static boolean showDisabledEquipment = false;

	/* GENERAL */
	public static final String LEXICON_TIMER = "thermalfoundation.lexicon_timer";
	public static final String LEXICON_DATA = "thermalfoundation.lexicon_data";

	public static boolean dropSulfurFireImmuneMobs = true;

	/* CRAFTING */
	public static boolean enablePyrotheumCrafting = true;
	public static boolean enablePetrotheumCrafting = true;

	public static int gemCokeFuel = 3200;
	public static int globRosinFuel = 800;
	public static int globTarFuel = 800;
	public static int dustPyrotheumFuel = 24000;

	/* RENDER */
	public static boolean iconBlazePowder = true;
	public static boolean renderStarfieldCage = false;

}
