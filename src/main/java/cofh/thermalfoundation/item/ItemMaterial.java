package cofh.thermalfoundation.item;

import cofh.core.energy.FurnaceFuelHandler;
import cofh.core.item.ItemMulti;
import cofh.core.util.core.IInitializer;
import cofh.core.util.crafting.RecipeShapelessOreFluid;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.init.TFFluids;
import cofh.thermalfoundation.init.TFProps;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

import static cofh.lib.util.helpers.ItemHelper.*;

public class ItemMaterial extends ItemMulti implements IInitializer {

	public ItemMaterial() {

		super("thermalfoundation");

		setUnlocalizedName("material");
		setCreativeTab(ThermalFoundation.tabCommon);
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		dustIron = addOreDictItem(0, "dustIron");
		dustGold = addOreDictItem(1, "dustGold");
		// dustDiamond = addOreDictItem(2, "dustDiamond");
		nuggetDiamond = addOreDictItem(16, "nuggetDiamond");
		gearIron = addOreDictItem(24, "gearIron");
		gearGold = addOreDictItem(25, "gearGold");
		plateIron = addOreDictItem(32, "plateIron");
		plateGold = addOreDictItem(33, "plateGold");

		/* Dusts */
		dustCopper = addOreDictItem(64, "dustCopper");
		dustTin = addOreDictItem(65, "dustTin");
		dustSilver = addOreDictItem(66, "dustSilver");
		dustLead = addOreDictItem(67, "dustLead");
		dustAluminum = addOreDictItem(68, "dustAluminum");
		dustNickel = addOreDictItem(69, "dustNickel");
		dustPlatinum = addOreDictItem(70, "dustPlatinum", EnumRarity.UNCOMMON);
		dustIridium = addOreDictItem(71, "dustIridium", EnumRarity.UNCOMMON);
		dustMithril = addOreDictItem(72, "dustMithril", EnumRarity.RARE);

		dustSteel = addOreDictItem(96, "dustSteel");
		dustElectrum = addOreDictItem(97, "dustElectrum");
		dustInvar = addOreDictItem(98, "dustInvar");
		dustBronze = addOreDictItem(99, "dustBronze");
		dustConstantan = addOreDictItem(100, "dustConstantan");
		dustSignalum = addOreDictItem(101, "dustSignalum", EnumRarity.UNCOMMON);
		dustLumium = addOreDictItem(102, "dustLumium", EnumRarity.UNCOMMON);
		dustEnderium = addOreDictItem(103, "dustEnderium", EnumRarity.RARE);

		/* Ingots */
		ingotCopper = addOreDictItem(128, "ingotCopper");
		ingotTin = addOreDictItem(129, "ingotTin");
		ingotSilver = addOreDictItem(130, "ingotSilver");
		ingotLead = addOreDictItem(131, "ingotLead");
		ingotAluminum = addOreDictItem(132, "ingotAluminum");
		ingotNickel = addOreDictItem(133, "ingotNickel");
		ingotPlatinum = addOreDictItem(134, "ingotPlatinum", EnumRarity.UNCOMMON);
		ingotIridium = addOreDictItem(135, "ingotIridium", EnumRarity.UNCOMMON);
		ingotMithril = addOreDictItem(136, "ingotMithril", EnumRarity.RARE);

		ingotSteel = addOreDictItem(160, "ingotSteel");
		ingotElectrum = addOreDictItem(161, "ingotElectrum");
		ingotInvar = addOreDictItem(162, "ingotInvar");
		ingotBronze = addOreDictItem(163, "ingotBronze");
		ingotConstantan = addOreDictItem(164, "ingotConstantan");
		ingotSignalum = addOreDictItem(165, "ingotSignalum", EnumRarity.UNCOMMON);
		ingotLumium = addOreDictItem(166, "ingotLumium", EnumRarity.UNCOMMON);
		ingotEnderium = addOreDictItem(167, "ingotEnderium", EnumRarity.RARE);

		/* Nuggets */
		nuggetCopper = addOreDictItem(192, "nuggetCopper");
		nuggetTin = addOreDictItem(193, "nuggetTin");
		nuggetSilver = addOreDictItem(194, "nuggetSilver");
		nuggetLead = addOreDictItem(195, "nuggetLead");
		nuggetAluminum = addOreDictItem(196, "nuggetAluminum");
		nuggetNickel = addOreDictItem(197, "nuggetNickel");
		nuggetPlatinum = addOreDictItem(198, "nuggetPlatinum", EnumRarity.UNCOMMON);
		nuggetIridium = addOreDictItem(199, "nuggetIridium", EnumRarity.UNCOMMON);
		nuggetMithril = addOreDictItem(200, "nuggetMithril", EnumRarity.RARE);

		nuggetSteel = addOreDictItem(224, "nuggetSteel");
		nuggetElectrum = addOreDictItem(225, "nuggetElectrum");
		nuggetInvar = addOreDictItem(226, "nuggetInvar");
		nuggetBronze = addOreDictItem(227, "nuggetBronze");
		nuggetConstantan = addOreDictItem(228, "nuggetConstantan");
		nuggetSignalum = addOreDictItem(229, "nuggetSignalum", EnumRarity.UNCOMMON);
		nuggetLumium = addOreDictItem(230, "nuggetLumium", EnumRarity.UNCOMMON);
		nuggetEnderium = addOreDictItem(231, "nuggetEnderium", EnumRarity.RARE);

		/* Gears */
		gearCopper = addOreDictItem(256, "gearCopper");
		gearTin = addOreDictItem(257, "gearTin");
		gearSilver = addOreDictItem(258, "gearSilver");
		gearLead = addOreDictItem(259, "gearLead");
		gearAluminum = addOreDictItem(260, "gearAluminum");
		gearNickel = addOreDictItem(261, "gearNickel");
		gearPlatinum = addOreDictItem(262, "gearPlatinum", EnumRarity.UNCOMMON);
		gearIridium = addOreDictItem(263, "gearIridium", EnumRarity.UNCOMMON);
		gearMithril = addOreDictItem(264, "gearMithril", EnumRarity.RARE);

		gearSteel = addOreDictItem(288, "gearSteel");
		gearElectrum = addOreDictItem(289, "gearElectrum");
		gearInvar = addOreDictItem(290, "gearInvar");
		gearBronze = addOreDictItem(291, "gearBronze");
		gearConstantan = addOreDictItem(292, "gearConstantan");
		gearSignalum = addOreDictItem(293, "gearSignalum", EnumRarity.UNCOMMON);
		gearLumium = addOreDictItem(294, "gearLumium", EnumRarity.UNCOMMON);
		gearEnderium = addOreDictItem(295, "gearEnderium", EnumRarity.RARE);

		/* Plates */
		plateCopper = addOreDictItem(320, "plateCopper");
		plateTin = addOreDictItem(321, "plateTin");
		plateSilver = addOreDictItem(322, "plateSilver");
		plateLead = addOreDictItem(323, "plateLead");
		plateAluminum = addOreDictItem(324, "plateAluminum");
		plateNickel = addOreDictItem(325, "plateNickel");
		platePlatinum = addOreDictItem(326, "platePlatinum", EnumRarity.UNCOMMON);
		plateIridium = addOreDictItem(327, "plateIridium", EnumRarity.UNCOMMON);
		plateMithril = addOreDictItem(328, "plateMithril", EnumRarity.RARE);

		plateSteel = addOreDictItem(352, "plateSteel");
		plateElectrum = addOreDictItem(353, "plateElectrum");
		plateInvar = addOreDictItem(354, "plateInvar");
		plateBronze = addOreDictItem(355, "plateBronze");
		plateConstantan = addOreDictItem(356, "plateConstantan");
		plateSignalum = addOreDictItem(357, "plateSignalum", EnumRarity.UNCOMMON);
		plateLumium = addOreDictItem(358, "plateLumium", EnumRarity.UNCOMMON);
		plateEnderium = addOreDictItem(359, "plateEnderium", EnumRarity.RARE);

		/* Parts */
		redstoneServo = addItem(512, "redstoneServo");
		powerCoilGold = addItem(513, "powerCoilGold");
		powerCoilSilver = addItem(514, "powerCoilSilver");
		powerCoilElectrum = addItem(515, "powerCoilElectrum");

		/* Process Items */
		dustCoal = addOreDictItem(768, "dustCoal");
		dustCharcoal = addOreDictItem(769, "dustCharcoal");
		dustObsidian = addOreDictItem(770, "dustObsidian");
		dustSulfur = addOreDictItem(771, "dustSulfur");
		dustNiter = addItem(772, "dustNiter");

		dustWood = addOreDictItem(800, "dustWood");
		dustWoodCompressed = addItem(801, "dustWoodCompressed");
		gemCoke = addItem(802, "gemCoke");

		globRosin = addItem(832, "globRosin");
		globTar = addItem(833, "globTar");

		crystalSlag = addOreDictItem(864, "crystalSlag");
		crystalSlagRich = addOreDictItem(865, "crystalSlagRich");
		crystalCinnabar = addOreDictItem(866, "crystalCinnabar");

		crystalCrudeOil = addOreDictItem(892, "crystalCrudeOil");
		crystalRedstone = addOreDictItem(893, "crystalRedstone", EnumRarity.UNCOMMON);
		crystalGlowstone = addOreDictItem(894, "crystalGlowstone", EnumRarity.UNCOMMON);
		crystalEnder = addOreDictItem(895, "crystalEnder", EnumRarity.RARE);

		/* Additional Items */
		dustPyrotheum = addOreDictItem(1024, "dustPyrotheum", EnumRarity.RARE);
		dustCryotheum = addOreDictItem(1025, "dustCryotheum", EnumRarity.RARE);
		dustAerotheum = addOreDictItem(1026, "dustAerotheum", EnumRarity.RARE);
		dustPetrotheum = addOreDictItem(1027, "dustPetrotheum", EnumRarity.RARE);
		dustMana = addOreDictItem(1028, "dustMana", EnumRarity.EPIC);

		/* Mob Drops */
		rodBlizz = addOreDictItem(2048, "rodBlizz");
		dustBlizz = addOreDictItem(2049, "dustBlizz");
		rodBlitz = addOreDictItem(2050, "rodBlitz");
		dustBlitz = addOreDictItem(2051, "dustBlitz");
		rodBasalz = addOreDictItem(2052, "rodBasalz");
		dustBasalz = addOreDictItem(2053, "dustBasalz");
		// Cold Gel
		// Air Gel
		// Rock Gel

		OreDictionary.registerOre("dustSaltpeter", dustNiter);
		OreDictionary.registerOre("fuelCoke", gemCoke);

		OreDictionary.registerOre("itemSlag", crystalSlag);
		OreDictionary.registerOre("itemSlagRich", crystalSlagRich);
		OreDictionary.registerOre("itemCinnabar", crystalCinnabar);

		/* Vanilla References */
		nuggetIron = new ItemStack(Items.field_191525_da);
		nuggetGold = new ItemStack(Items.GOLD_NUGGET);

		ingotIron = new ItemStack(Items.IRON_INGOT);
		ingotGold = new ItemStack(Items.GOLD_INGOT);
		gemDiamond = new ItemStack(Items.DIAMOND);

		ThermalFoundation.proxy.addIModelRegister(this);

		return true;
	}

	@Override
	public boolean initialize() {

		/* Smelting */
		addSmelting(ingotIron, dustIron, 0.0F);
		addSmelting(ingotGold, dustGold, 0.0F);
		addSmelting(ingotCopper, dustCopper, 0.0F);
		addSmelting(ingotTin, dustTin, 0.0F);
		addSmelting(ingotSilver, dustSilver, 0.0F);
		addSmelting(ingotLead, dustLead, 0.0F);
		addSmelting(ingotAluminum, dustAluminum, 0.0F);
		addSmelting(ingotNickel, dustNickel, 0.0F);
		addSmelting(ingotPlatinum, dustPlatinum, 0.0F);
		addSmelting(ingotIridium, dustIridium, 0.0F);
		addSmelting(ingotMithril, dustMithril, 0.0F);

		addSmelting(ingotSteel, dustSteel, 0.0F);
		addSmelting(ingotElectrum, dustElectrum, 0.0F);
		addSmelting(ingotInvar, dustInvar, 0.0F);
		addSmelting(ingotBronze, dustBronze, 0.0F);
		addSmelting(ingotConstantan, dustConstantan, 0.0F);
		// No Signalum
		// No Lumium
		// No Enderium

		if (!Loader.isModLoaded("thermalexpansion")) {
			addSmelting(ingotEnderium, dustEnderium, 0.0F);

			addSmelting(new ItemStack(Items.REDSTONE), crystalRedstone, 0.0F);
			addSmelting(new ItemStack(Items.GLOWSTONE_DUST), crystalGlowstone, 0.0F);
			addSmelting(new ItemStack(Items.ENDER_PEARL), crystalEnder, 0.0F);
		}

		/* Alloy Recipes */
		addRecipe(ShapelessRecipe(cloneStack(dustElectrum, 2), "dustGold", "dustSilver"));
		addRecipe(ShapelessRecipe(cloneStack(dustInvar, 3), "dustIron", "dustIron", "dustNickel"));
		addRecipe(ShapelessRecipe(cloneStack(dustBronze, 4), "dustCopper", "dustCopper", "dustCopper", "dustTin"));
		addRecipe(ShapelessRecipe(cloneStack(dustConstantan, 2), "dustCopper", "dustNickel"));
		addRecipe(new RecipeShapelessOreFluid(cloneStack(dustSignalum, 4), "dustCopper", "dustCopper", "dustCopper", "dustSilver", TFFluids.fluidRedstone));
		addRecipe(new RecipeShapelessOreFluid(cloneStack(dustLumium, 4), "dustTin", "dustTin", "dustTin", "dustSilver", TFFluids.fluidGlowstone));
		addRecipe(new RecipeShapelessOreFluid(cloneStack(dustEnderium, 4), "dustTin", "dustTin", "dustSilver", "dustPlatinum", TFFluids.fluidEnder));

		/* Storage */
		addTwoWayStorageRecipe(gemDiamond, "gemDiamond", nuggetDiamond, "nuggetDiamond");

		addTwoWayStorageRecipe(ingotCopper, "ingotCopper", nuggetCopper, "nuggetCopper");
		addTwoWayStorageRecipe(ingotTin, "ingotTin", nuggetTin, "nuggetTin");
		addTwoWayStorageRecipe(ingotSilver, "ingotSilver", nuggetSilver, "nuggetSilver");
		addTwoWayStorageRecipe(ingotLead, "ingotLead", nuggetLead, "nuggetLead");
		addTwoWayStorageRecipe(ingotAluminum, "ingotAluminum", nuggetAluminum, "nuggetAluminum");
		addTwoWayStorageRecipe(ingotNickel, "ingotNickel", nuggetNickel, "nuggetNickel");
		addTwoWayStorageRecipe(ingotPlatinum, "ingotPlatinum", nuggetPlatinum, "nuggetPlatinum");
		addTwoWayStorageRecipe(ingotIridium, "ingotIridium", nuggetIridium, "nuggetIridium");
		addTwoWayStorageRecipe(ingotMithril, "ingotMithril", nuggetMithril, "nuggetMithril");

		addTwoWayStorageRecipe(ingotSteel, "ingotSteel", nuggetSteel, "nuggetSteel");
		addTwoWayStorageRecipe(ingotElectrum, "ingotElectrum", nuggetElectrum, "nuggetElectrum");
		addTwoWayStorageRecipe(ingotInvar, "ingotInvar", nuggetInvar, "nuggetInvar");
		addTwoWayStorageRecipe(ingotBronze, "ingotBronze", nuggetBronze, "nuggetBronze");
		addTwoWayStorageRecipe(ingotConstantan, "ingotConstantan", nuggetConstantan, "nuggetConstantan");
		addTwoWayStorageRecipe(ingotSignalum, "ingotSignalum", nuggetSignalum, "nuggetSignalum");
		addTwoWayStorageRecipe(ingotLumium, "ingotLumium", nuggetLumium, "nuggetLumium");
		addTwoWayStorageRecipe(ingotEnderium, "ingotEnderium", nuggetEnderium, "nuggetEnderium");

		addReverseStorageRecipe(ingotCopper, "blockCopper");
		addReverseStorageRecipe(ingotTin, "blockTin");
		addReverseStorageRecipe(ingotSilver, "blockSilver");
		addReverseStorageRecipe(ingotLead, "blockLead");
		addReverseStorageRecipe(ingotAluminum, "blockAluminum");
		addReverseStorageRecipe(ingotNickel, "blockNickel");
		addReverseStorageRecipe(ingotPlatinum, "blockPlatinum");
		addReverseStorageRecipe(ingotIridium, "blockIridium");
		addReverseStorageRecipe(ingotMithril, "blockMithril");

		addReverseStorageRecipe(ingotSteel, "blockSteel");
		addReverseStorageRecipe(ingotElectrum, "blockElectrum");
		addReverseStorageRecipe(ingotInvar, "blockInvar");
		addReverseStorageRecipe(ingotBronze, "blockBronze");
		addReverseStorageRecipe(ingotConstantan, "blockConstantan");
		addReverseStorageRecipe(ingotSignalum, "blockSignalum");
		addReverseStorageRecipe(ingotLumium, "blockLumium");
		addReverseStorageRecipe(ingotEnderium, "blockEnderium");

		/* Gears */
		addGearRecipe(gearIron, "ingotIron");
		addGearRecipe(gearGold, "ingotGold");
		addGearRecipe(gearCopper, "ingotCopper");
		addGearRecipe(gearTin, "ingotTin");
		addGearRecipe(gearSilver, "ingotSilver");
		addGearRecipe(gearLead, "ingotLead");
		addGearRecipe(gearAluminum, "ingotAluminum");
		addGearRecipe(gearNickel, "ingotNickel");
		addGearRecipe(gearPlatinum, "ingotPlatinum");
		addGearRecipe(gearIridium, "ingotIridium");
		addGearRecipe(gearMithril, "ingotMithril");

		addGearRecipe(gearSteel, "ingotSteel");
		addGearRecipe(gearElectrum, "ingotElectrum");
		addGearRecipe(gearInvar, "ingotInvar");
		addGearRecipe(gearBronze, "ingotBronze");
		addGearRecipe(gearConstantan, "ingotConstantan");
		addGearRecipe(gearSignalum, "ingotSignalum");
		addGearRecipe(gearLumium, "ingotLumium");
		addGearRecipe(gearEnderium, "ingotEnderium");

		/* Parts */
		addRecipe(ShapedRecipe(redstoneServo, "R R", " I ", "R R", 'R', "dustRedstone", 'I', "ingotIron"));
		addRecipe(ShapedRecipe(powerCoilGold, "  R", " G ", "R  ", 'R', "dustRedstone", 'G', "ingotGold"));
		addRecipe(ShapedRecipe(powerCoilSilver, "  R", " G ", "R  ", 'R', "dustRedstone", 'G', "ingotSilver"));
		addRecipe(ShapedRecipe(powerCoilElectrum, "R  ", " G ", "  R", 'R', "dustRedstone", 'G', "ingotElectrum"));

		/* Mob Drops */
		addRecipe(ShapelessRecipe(cloneStack(dustPyrotheum, 2), "dustCoal", "dustSulfur", "dustRedstone", Items.BLAZE_POWDER));
		addRecipe(ShapelessRecipe(cloneStack(dustCryotheum, 2), Items.SNOWBALL, "dustSaltpeter", "dustRedstone", "dustBlizz"));
		addRecipe(ShapelessRecipe(cloneStack(dustAerotheum, 2), "sand", "dustSaltpeter", "dustRedstone", "dustBlitz"));
		addRecipe(ShapelessRecipe(cloneStack(dustPetrotheum, 2), Items.CLAY_BALL, "dustObsidian", "dustRedstone", "dustBasalz"));
		addRecipe(ShapelessRecipe(cloneStack(dustBlizz, 2), "rodBlizz"));
		addRecipe(ShapelessRecipe(cloneStack(dustBlitz, 2), "rodBlitz"));
		addRecipe(ShapelessRecipe(cloneStack(dustBasalz, 2), "rodBasalz"));
		// Cold Gel
		// Air Gel
		// Rock Gel

		return true;
	}

	@Override
	public boolean postInit() {

		FurnaceFuelHandler.registerFuel(gemCoke, TFProps.gemCokeFuel);
		FurnaceFuelHandler.registerFuel(globRosin, TFProps.globRosinFuel);
		FurnaceFuelHandler.registerFuel(globTar, TFProps.globTarFuel);
		FurnaceFuelHandler.registerFuel(crystalCrudeOil, TFProps.gemCokeFuel);
		FurnaceFuelHandler.registerFuel(dustPyrotheum, TFProps.dustPyrotheumFuel);

		return true;
	}

	/* REFERENCES */
	public static ItemStack nuggetIron;
	public static ItemStack nuggetGold;

	public static ItemStack ingotIron;
	public static ItemStack ingotGold;
	public static ItemStack gemDiamond;

	public static ItemStack dustIron;
	public static ItemStack dustGold;

	public static ItemStack nuggetDiamond;

	public static ItemStack gearIron;
	public static ItemStack gearGold;

	public static ItemStack plateIron;
	public static ItemStack plateGold;

	public static ItemStack ingotCopper;
	public static ItemStack ingotTin;
	public static ItemStack ingotSilver;
	public static ItemStack ingotLead;
	public static ItemStack ingotAluminum;
	public static ItemStack ingotNickel;
	public static ItemStack ingotPlatinum;
	public static ItemStack ingotIridium;
	public static ItemStack ingotMithril;

	public static ItemStack ingotSteel;
	public static ItemStack ingotElectrum;
	public static ItemStack ingotInvar;
	public static ItemStack ingotBronze;
	public static ItemStack ingotConstantan;
	public static ItemStack ingotSignalum;
	public static ItemStack ingotLumium;
	public static ItemStack ingotEnderium;

	public static ItemStack dustCopper;
	public static ItemStack dustTin;
	public static ItemStack dustSilver;
	public static ItemStack dustLead;
	public static ItemStack dustAluminum;
	public static ItemStack dustNickel;
	public static ItemStack dustPlatinum;
	public static ItemStack dustIridium;
	public static ItemStack dustMithril;

	public static ItemStack dustSteel;
	public static ItemStack dustElectrum;
	public static ItemStack dustInvar;
	public static ItemStack dustBronze;
	public static ItemStack dustConstantan;
	public static ItemStack dustSignalum;
	public static ItemStack dustLumium;
	public static ItemStack dustEnderium;

	public static ItemStack nuggetCopper;
	public static ItemStack nuggetTin;
	public static ItemStack nuggetSilver;
	public static ItemStack nuggetLead;
	public static ItemStack nuggetAluminum;
	public static ItemStack nuggetNickel;
	public static ItemStack nuggetPlatinum;
	public static ItemStack nuggetIridium;
	public static ItemStack nuggetMithril;

	public static ItemStack nuggetSteel;
	public static ItemStack nuggetElectrum;
	public static ItemStack nuggetInvar;
	public static ItemStack nuggetBronze;
	public static ItemStack nuggetConstantan;
	public static ItemStack nuggetSignalum;
	public static ItemStack nuggetLumium;
	public static ItemStack nuggetEnderium;

	public static ItemStack gearCopper;
	public static ItemStack gearTin;
	public static ItemStack gearSilver;
	public static ItemStack gearLead;
	public static ItemStack gearNickel;
	public static ItemStack gearAluminum;
	public static ItemStack gearPlatinum;
	public static ItemStack gearIridium;
	public static ItemStack gearMithril;

	public static ItemStack gearSteel;
	public static ItemStack gearElectrum;
	public static ItemStack gearInvar;
	public static ItemStack gearBronze;
	public static ItemStack gearConstantan;
	public static ItemStack gearSignalum;
	public static ItemStack gearLumium;
	public static ItemStack gearEnderium;

	public static ItemStack plateCopper;
	public static ItemStack plateTin;
	public static ItemStack plateSilver;
	public static ItemStack plateLead;
	public static ItemStack plateAluminum;
	public static ItemStack plateNickel;
	public static ItemStack platePlatinum;
	public static ItemStack plateIridium;
	public static ItemStack plateMithril;

	public static ItemStack plateSteel;
	public static ItemStack plateElectrum;
	public static ItemStack plateInvar;
	public static ItemStack plateBronze;
	public static ItemStack plateConstantan;
	public static ItemStack plateSignalum;
	public static ItemStack plateLumium;
	public static ItemStack plateEnderium;

	public static ItemStack redstoneServo;
	public static ItemStack powerCoilGold;
	public static ItemStack powerCoilSilver;
	public static ItemStack powerCoilElectrum;

	public static ItemStack dustCoal;
	public static ItemStack dustCharcoal;
	public static ItemStack dustObsidian;
	public static ItemStack dustSulfur;
	public static ItemStack dustNiter;

	public static ItemStack dustWood;
	public static ItemStack dustWoodCompressed;
	public static ItemStack gemCoke;

	public static ItemStack globRosin;
	public static ItemStack globTar;

	public static ItemStack crystalSlag;
	public static ItemStack crystalSlagRich;
	public static ItemStack crystalCinnabar;

	public static ItemStack crystalCrudeOil;
	public static ItemStack crystalRedstone;
	public static ItemStack crystalGlowstone;
	public static ItemStack crystalEnder;

	public static ItemStack dustPyrotheum;
	public static ItemStack dustCryotheum;
	public static ItemStack dustAerotheum;
	public static ItemStack dustPetrotheum;
	public static ItemStack dustMana;

	public static ItemStack rodBlizz;
	public static ItemStack dustBlizz;
	public static ItemStack rodBlitz;
	public static ItemStack dustBlitz;
	public static ItemStack rodBasalz;
	public static ItemStack dustBasalz;
	// Cold Gel
	// Air Gel
	// Rock Gel

}
