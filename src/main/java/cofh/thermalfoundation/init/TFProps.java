package cofh.thermalfoundation.init;

import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.gui.CreativeTabTF;
import cofh.thermalfoundation.util.LexiconManager;
import net.minecraft.item.ItemStack;

public class TFProps {

	private TFProps() {

	}

	public static void preInit() {

		String category;
		String comment;

		/* GENERAL */
		comment = "If TRUE, Fire-Immune mobs have a chance to drop Sulfur.";
		dropSulfurFireImmuneMobs = ThermalFoundation.CONFIG.get("General", "FireImmuneMobsDropSulfur", dropSulfurFireImmuneMobs, comment);

		/* GRAPHICS */
		comment = "Set to FALSE to revert Blaze Powder to the default Minecraft icon.";
		iconBlazePowder = ThermalFoundation.CONFIG_CLIENT.get("Icons", "BlazePowder", iconBlazePowder, comment);

		comment = "Set to TRUE for Ender devices to be a bit more Cagey year-round.";
		renderStarfieldCage = ThermalFoundation.CONFIG_CLIENT.get("Render", "CageyEnder", renderStarfieldCage, comment);

		/* INTERFACE */
		category = "Interface";
		boolean armorTab = false;
		boolean toolTab = false;

		comment = "Set to TRUE to put Thermal Foundation Armor under the general \"Thermal Foundation\" Creative Tab.";
		armorTab = ThermalFoundation.CONFIG_CLIENT.get(category, "ArmorInCommonTab", armorTab, comment);

		comment = "Set to TRUE to put Thermal Foundation Tools under the general \"Thermal Foundation\" Creative Tab.";
		toolTab = ThermalFoundation.CONFIG_CLIENT.get(category, "ToolsInCommonTab", toolTab, comment);

		/* EQUIPMENT */
		category = "Equipment";
		comment = "Set to TRUE to disable ALL armor sets.";
		disableAllArmor = ThermalFoundation.CONFIG.get(category, "DisableAllArmor", disableAllArmor, comment);

		comment = "Set to TRUE to disable ALL tool sets.";
		disableAllTools = ThermalFoundation.CONFIG.get(category, "DisableAllTools", disableAllTools, comment);

		comment = "Set to FALSE to hide all disabled equipment from the Creative Tabs.";
		showDisabledEquipment = ThermalFoundation.CONFIG.get(category, "ShowDisabledEquipment", showDisabledEquipment, comment);

		if (armorTab) {
			ThermalFoundation.tabArmor = ThermalFoundation.tabCommon;
		} else {
			if (!disableAllArmor || (disableAllArmor && showDisabledEquipment)) {
				ThermalFoundation.tabArmor = new CreativeTabTF("Armor") {

					@Override
					protected ItemStack getStack() {

						return TFEquipment.ArmorSet.INVAR.armorPlate;
					}
				};
			}
		}
		if (toolTab) {
			ThermalFoundation.tabTools = ThermalFoundation.tabCommon;
		} else {
			if (!disableAllTools || (disableAllTools && showDisabledEquipment)) {
				ThermalFoundation.tabTools = new CreativeTabTF("Tools") {

					@Override
					protected ItemStack getStack() {

						return TFEquipment.ToolSet.INVAR.toolPickaxe;
					}
				};
			}
		}

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

	/* INTERFACE */
	public static boolean disableAllTools = false;
	public static boolean disableAllArmor = false;
	public static boolean showDisabledEquipment = true;

	/* GENERAL */
	public static boolean dropSulfurFireImmuneMobs = true;
	public static int dustPyrotheumFuel = 24000;

	/* RENDER */
	public static boolean iconBlazePowder = true;
	public static boolean renderStarfieldCage = false;

}
