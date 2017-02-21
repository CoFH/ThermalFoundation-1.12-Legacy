package cofh.thermalfoundation.init;

import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.gui.CreativeTabTF;
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
		disableAllArmorRecipes = ThermalFoundation.CONFIG.getConfiguration().getBoolean("DisableAllArmorRecipes", category, disableAllArmorRecipes, comment);

		comment = "If TRUE, recipes for all Tools are disabled.";
		disableAllToolRecipes = ThermalFoundation.CONFIG.getConfiguration().getBoolean("DisableAllToolRecipes", category, disableAllToolRecipes, comment);
	}

	private static void configClient() {

		String category;
		String comment;

		/* GRAPHICS */
		category = "Render";

		comment = "If TRUE, Blaze Powder uses a custom icon.";
		iconBlazePowder = ThermalFoundation.CONFIG_CLIENT.getConfiguration().getBoolean("BlazePowder", category, iconBlazePowder, comment);

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

					return TFEquipment.ArmorSet.INVAR.armorChestplate;
				}
			};
		}
		if (toolTabCommon) {
			ThermalFoundation.tabTools = ThermalFoundation.tabCommon;
		} else {
			ThermalFoundation.tabTools = new CreativeTabTF("Tools") {

				@Override
				protected ItemStack getStack() {

					return TFEquipment.ToolSet.INVAR.toolPickaxe;
				}
			};
		}
	}

	/* INTERFACE */
	public static boolean disableAllToolRecipes = false;
	public static boolean disableAllArmorRecipes = false;

	/* GENERAL */
	public static boolean dropSulfurFireImmuneMobs = true;

	public static int gemCokeFuel = 3200;
	public static int dustPyrotheumFuel = 24000;

	/* RENDER */
	public static boolean iconBlazePowder = true;
	public static boolean renderStarfieldCage = false;

}
