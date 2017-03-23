package cofh.thermalfoundation.init;

import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.gui.CreativeTabTF;
import cofh.thermalfoundation.init.TFEquipment.ArmorSet;
import cofh.thermalfoundation.init.TFEquipment.ToolSet;
import cofh.thermalfoundation.util.LexiconManager;
import net.minecraft.item.ItemStack;

public class TFProps {

	private TFProps() {

	}

	public static void preInit() {

		configCommon();
		configClient();

		LexiconManager.initialize();
	}

	public static void loadComplete() {

		LexiconManager.loadComplete();

		String prefix = "config.thermalfoundation.";
		String[] categoryNames = ThermalFoundation.CONFIG.getCategoryNames().toArray(new String[ThermalFoundation.CONFIG.getCategoryNames().size()]);
		for (int i = 0; i < categoryNames.length; i++) {
			ThermalFoundation.CONFIG.getCategory(categoryNames[i]).setLanguageKey(prefix + categoryNames[i]).setRequiresMcRestart(true);
		}
		categoryNames = ThermalFoundation.CONFIG_CLIENT.getCategoryNames().toArray(new String[ThermalFoundation.CONFIG_CLIENT.getCategoryNames().size()]);
		for (int i = 0; i < categoryNames.length; i++) {
			ThermalFoundation.CONFIG_CLIENT.getCategory(categoryNames[i]).setLanguageKey(prefix + categoryNames[i]).setRequiresMcRestart(true);
		}
	}

	/* HELPERS */
	private static void configCommon() {

		String category;
		String comment;

		/* GENERAL */
		category = "General";

		comment = "If TRUE, Fire-Immune mobs have a chance to drop Sulfur.";
		dropSulfurFireImmuneMobs = ThermalFoundation.CONFIG.getConfiguration().getBoolean("FireImmuneMobsDropSulfur", category, dropSulfurFireImmuneMobs, comment);

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
		ThermalFoundation.tabCommon = new CreativeTabTF();

		if (armorTabCommon) {
			ThermalFoundation.tabArmor = ThermalFoundation.tabCommon;
		} else {
			ThermalFoundation.tabArmor = new CreativeTabTF("Armor") {

				@Override
				protected ItemStack getStack() {

					return ArmorSet.INVAR.armorChestplate;
				}
			};
		}
		if (toolTabCommon) {
			ThermalFoundation.tabTools = ThermalFoundation.tabCommon;
		} else {
			ThermalFoundation.tabTools = new CreativeTabTF("Tools") {

				@Override
				protected ItemStack getStack() {

					return ToolSet.INVAR.toolPickaxe;
				}
			};
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
	public static final String LEXICON_TIMER = "thermalexpansion.lexicon_timer";
	public static final String LEXICON_DATA = "thermalexpansion.lexicon_data";

	public static boolean dropSulfurFireImmuneMobs = true;

	public static int gemCokeFuel = 3200;
	public static int globRosinFuel = 800;
	public static int globTarFuel = 800;
	public static int dustPyrotheumFuel = 24000;

	/* RENDER */
	public static boolean iconBlazePowder = true;
	public static boolean renderStarfieldCage = false;

}
