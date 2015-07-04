package cofh.thermalfoundation.plugins.mfr;

import cofh.asm.relauncher.Strippable;
import cofh.lib.util.helpers.MathHelper;
import cofh.thermalfoundation.ThermalFoundation;

import powercrystals.minefactoryreloaded.api.FactoryRegistry;
import powercrystals.minefactoryreloaded.api.ValuedItem;

public class MFRPlugin {

	public static void preInit() {

		String comment;
		String category = "Plugins.MineFactoryReloaded.Straw";

		strawRedstone = ThermalFoundation.config.get(category, "Redstone", true);
		strawGlowstone = ThermalFoundation.config.get(category, "Glowstone", true);
		strawEnder = ThermalFoundation.config.get(category, "Ender", true);
		strawPyrotheum = ThermalFoundation.config.get(category, "Pyrotheum", true);
		strawCryotheum = ThermalFoundation.config.get(category, "Cryotheum", true);
		strawAerotheum = ThermalFoundation.config.get(category, "Aerotheum", true);
		strawPetrotheum = ThermalFoundation.config.get(category, "Petrotheum", true);
		strawCoal = ThermalFoundation.config.get(category, "Coal", true);

		comment = "This controls the maximum distance (in blocks) a player will teleport from drinking Ender. (Min: 8, Max: 65536)";
		strawEnderRange = ThermalFoundation.config.get(category, "Ender.Range", strawEnderRange, comment);
		strawEnderRange = MathHelper.clampI(strawEnderRange, 8, 65536);
	}

	public static void initialize() {

	}

	@Strippable("mod:MineFactoryReloaded")
	public static void postInit() {

		if (strawRedstone) {
			FactoryRegistry.sendMessage("registerLiquidDrinkHandler", new ValuedItem("redstone", DrinkHandlerRedstone.instance));
		}
		if (strawGlowstone) {
			FactoryRegistry.sendMessage("registerLiquidDrinkHandler", new ValuedItem("glowstone", DrinkHandlerGlowstone.instance));
		}
		if (strawEnder) {
			FactoryRegistry.sendMessage("registerLiquidDrinkHandler", new ValuedItem("ender", DrinkHandlerEnder.instance));
		}
		if (strawPyrotheum) {
			FactoryRegistry.sendMessage("registerLiquidDrinkHandler", new ValuedItem("pyrotheum", DrinkHandlerPyrotheum.instance));
		}
		if (strawCryotheum) {
			FactoryRegistry.sendMessage("registerLiquidDrinkHandler", new ValuedItem("cryotheum", DrinkHandlerCryotheum.instance));
		}
		if (strawAerotheum) {
			FactoryRegistry.sendMessage("registerLiquidDrinkHandler", new ValuedItem("aerotheum", DrinkHandlerAerotheum.instance));
		}
		if (strawPetrotheum) {
			FactoryRegistry.sendMessage("registerLiquidDrinkHandler", new ValuedItem("petrotheum", DrinkHandlerPetrotheum.instance));
		}
		if (strawCoal) {
			FactoryRegistry.sendMessage("registerLiquidDrinkHandler", new ValuedItem("coal", DrinkHandlerCoal.instance));
		}
	}

	@Strippable("mod:MineFactoryReloaded")
	public static void loadComplete() {

		ThermalFoundation.log.info("Thermal Foundation: MineFactoryReloaded Plugin Enabled.");
	}

	public static boolean strawRedstone = true;
	public static boolean strawGlowstone = true;
	public static boolean strawEnder = true;
	public static boolean strawPyrotheum = true;
	public static boolean strawCryotheum = true;
	public static boolean strawAerotheum = true;
	public static boolean strawPetrotheum = true;
	public static boolean strawCoal = true;

	public static int strawEnderRange = 16384;

}
