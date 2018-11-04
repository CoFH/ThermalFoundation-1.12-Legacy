package cofh.thermalfoundation.item;

import cofh.core.energy.FurnaceFuelHandler;
import cofh.core.item.ItemMulti;
import cofh.core.util.core.IInitializer;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.init.TFProps;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

import static cofh.core.util.helpers.ItemHelper.cloneStack;
import static cofh.core.util.helpers.RecipeHelper.*;

public class ItemMaterial extends ItemMulti implements IInitializer {

	public ItemMaterial() {

		super("thermalfoundation");

		setUnlocalizedName("material");
		setCreativeTab(ThermalFoundation.tabItems);
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		ForgeRegistries.ITEMS.register(setRegistryName("material"));
		ThermalFoundation.proxy.addIModelRegister(this);

		dustIron = addOreDictItem(0, "dustIron");
		dustGold = addOreDictItem(1, "dustGold");
		// dustDiamond = addOreDictItem(2, "dustDiamond");
		nuggetDiamond = addOreDictItem(16, "nuggetDiamond");
		nuggetEmerald = addOreDictItem(17, "nuggetEmerald");

		gearWood = addOreDictItem(22, "gearWood");
		gearStone = addOreDictItem(23, "gearStone");

		gearIron = addOreDictItem(24, "gearIron");
		gearGold = addOreDictItem(25, "gearGold");

		gearDiamond = addOreDictItem(26, "gearDiamond");
		gearEmerald = addOreDictItem(27, "gearEmerald");

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

		/* Tool Parts */
		partToolCasing = addItem(640, "partToolCasing");
		partDrillHead = addItem(656, "partDrillHead");
		partSawBlade = addItem(657, "partSawBlade");

		/* Process Items */
		dustCoal = addOreDictItem(768, "dustCoal");
		dustCharcoal = addOreDictItem(769, "dustCharcoal");
		dustObsidian = addOreDictItem(770, "dustObsidian");
		dustSulfur = addOreDictItem(771, "dustSulfur");
		dustNiter = addItem(772, "dustNiter");

		dustWood = addOreDictItem(800, "dustWood");
		dustWoodCompressed = addItem(801, "dustWoodCompressed");
		fuelCoke = addOreDictItem(802, "fuelCoke");

		dustBiomass = addItem(816, "dustBiomass");
		dustBiomassRich = addItem(817, "dustBiomassRich");
		dustBioblend = addItem(818, "dustBioblend");
		dustBioblendRich = addItem(819, "dustBioblendRich");

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

		OreDictionary.registerOre("itemSawdust", dustWood);
		OreDictionary.registerOre("itemBiomass", dustBiomass);
		OreDictionary.registerOre("itemBioblend", dustBioblend);
		OreDictionary.registerOre("itemBiomassRich", dustBiomassRich);
		OreDictionary.registerOre("itemBioblendRich", dustBioblendRich);

		OreDictionary.registerOre("itemSlag", crystalSlag);
		OreDictionary.registerOre("itemSlagRich", crystalSlagRich);
		OreDictionary.registerOre("itemCinnabar", crystalCinnabar);

		OreDictionary.registerOre("clathrateOil", crystalCrudeOil);
		OreDictionary.registerOre("clathrateRedstone", crystalRedstone);
		OreDictionary.registerOre("clathrateGlowstone", crystalGlowstone);
		OreDictionary.registerOre("clathrateEnder", crystalEnder);

		/* Vanilla References */
		nuggetIron = new ItemStack(Items.IRON_NUGGET);
		nuggetGold = new ItemStack(Items.GOLD_NUGGET);

		ingotIron = new ItemStack(Items.IRON_INGOT);
		ingotGold = new ItemStack(Items.GOLD_INGOT);
		gemDiamond = new ItemStack(Items.DIAMOND);
		gemEmerald = new ItemStack(Items.EMERALD);

		return true;
	}

	@Override
	public boolean initialize() {

		/* Smelting */
		addSmelting(dustIron, ingotIron, 0.7F);
		addSmelting(dustGold, ingotGold, 1.0F);
		addSmelting(dustCopper, ingotCopper, 0.6F);
		addSmelting(dustTin, ingotTin, 0.7F);
		addSmelting(dustSilver, ingotSilver, 0.9F);
		addSmelting(dustLead, ingotLead, 0.8F);
		addSmelting(dustAluminum, ingotAluminum, 0.6F);
		addSmelting(dustNickel, ingotNickel, 1.0F);
		addSmelting(dustPlatinum, ingotPlatinum, 1.0F);
		addSmelting(dustIridium, ingotIridium, 1.2F);
		addSmelting(dustMithril, ingotMithril, 1.5F);

		// addSmelting(dustSteel, ingotSteel);
		addSmelting(dustElectrum, ingotElectrum);
		addSmelting(dustInvar, ingotInvar);
		addSmelting(dustBronze, ingotBronze);
		addSmelting(dustConstantan, ingotConstantan);
		// addSmelting(dustSignalum, ingotSignalum);
		// addSmelting(dustLumium, ingotLumium);
		// addSmelting(dustEnderium, ingotEnderium);

		if (!Loader.isModLoaded("thermalexpansion")) {
			addSmelting(Blocks.GRAVEL, crystalSlag);

			addSmelting(dustSteel, ingotSteel);
			addSmelting(dustSignalum, ingotSignalum);
			addSmelting(dustLumium, ingotLumium);
			addSmelting(dustEnderium, ingotEnderium);

			addSmelting(crystalRedstone, new ItemStack(Items.REDSTONE), 0.5F);
			addSmelting(crystalGlowstone, new ItemStack(Items.GLOWSTONE_DUST), 0.5F);
			addSmelting(crystalEnder, new ItemStack(Items.ENDER_PEARL), 0.5F);
		}

		/* Alloy Recipes */
		addShapelessRecipe(cloneStack(dustElectrum, 2), "dustGold", "dustSilver");
		addShapelessRecipe(cloneStack(dustInvar, 3), "dustIron", "dustIron", "dustNickel");
		addShapelessRecipe(cloneStack(dustBronze, 4), "dustCopper", "dustCopper", "dustCopper", "dustTin");
		addShapelessRecipe(cloneStack(dustConstantan, 2), "dustCopper", "dustNickel");
		addShapelessRecipe(cloneStack(dustBioblend, 4), "itemBiomass", "itemBiomass", "itemBiomass", "dustWood");

		/* Storage */
		addTwoWayStorageRecipe(gemDiamond, "gemDiamond", nuggetDiamond, "nuggetDiamond");
		addTwoWayStorageRecipe(gemEmerald, "gemEmerald", nuggetEmerald, "nuggetEmerald");

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

		addReverseStorageRecipe(new ItemStack(Items.COAL, 1, 1), "blockCharcoal");
		addReverseStorageRecipe(fuelCoke, "blockFuelCoke");

		if (!TFProps.disableBasicItems) {

			/* Gears */
			if (TFProps.enableBasicGears) {
				addGearRecipe(gearWood, "stickWood");
				addGearRecipe(gearStone, "stone", "gearWood");
			}
			addConfigurableGearRecipe(gearIron, "ingotIron");
			addConfigurableGearRecipe(gearGold, "ingotGold");
			addConfigurableGearRecipe(gearDiamond, "gemDiamond");
			addConfigurableGearRecipe(gearEmerald, "gemEmerald");

			addConfigurableGearRecipe(gearCopper, "ingotCopper");
			addConfigurableGearRecipe(gearTin, "ingotTin");
			addConfigurableGearRecipe(gearSilver, "ingotSilver");
			addConfigurableGearRecipe(gearLead, "ingotLead");
			addConfigurableGearRecipe(gearAluminum, "ingotAluminum");
			addConfigurableGearRecipe(gearNickel, "ingotNickel");
			addConfigurableGearRecipe(gearPlatinum, "ingotPlatinum");
			addConfigurableGearRecipe(gearIridium, "ingotIridium");
			addConfigurableGearRecipe(gearMithril, "ingotMithril");

			addConfigurableGearRecipe(gearSteel, "ingotSteel");
			addConfigurableGearRecipe(gearElectrum, "ingotElectrum");
			addConfigurableGearRecipe(gearInvar, "ingotInvar");
			addConfigurableGearRecipe(gearBronze, "ingotBronze");
			addConfigurableGearRecipe(gearConstantan, "ingotConstantan");
			addConfigurableGearRecipe(gearSignalum, "ingotSignalum");
			addConfigurableGearRecipe(gearLumium, "ingotLumium");
			addConfigurableGearRecipe(gearEnderium, "ingotEnderium");

			/* Parts */
			addShapedRecipe(redstoneServo, " R ", " I ", " R ", 'R', "dustRedstone", 'I', "ingotIron");
			addShapedRecipe(powerCoilGold, "  R", " G ", "R  ", 'R', "dustRedstone", 'G', "ingotGold");
			addShapedRecipe(powerCoilSilver, "  R", " G ", "R  ", 'R', "dustRedstone", 'G', "ingotSilver");
			addShapedRecipe(powerCoilElectrum, "R  ", " G ", "  R", 'R', "dustRedstone", 'G', "ingotElectrum");

			addShapedRecipe(partToolCasing, " R ", "GRG", "RIR", 'G', "ingotGold", 'R', "dustRedstone", 'I', "ingotIron");
			addShapedRecipe(partDrillHead, " I ", "ICI", "III", 'C', "ingotCopper", 'I', "ingotIron");
			addShapedRecipe(partSawBlade, "II ", "ICI", " II", 'C', "ingotCopper", 'I', "ingotIron");
		}
		/* Mob Drops */
		addShapelessRecipe(cloneStack(dustBlizz, 2), "rodBlizz");
		addShapelessRecipe(cloneStack(dustBlitz, 2), "rodBlitz");
		addShapelessRecipe(cloneStack(dustBasalz, 2), "rodBasalz");
		// Cold Gel
		// Air Gel
		// Rock Gel

		FurnaceFuelHandler.registerFuel(fuelCoke, TFProps.fuelCokeFuel);
		FurnaceFuelHandler.registerFuel(globRosin, TFProps.globRosinFuel);
		FurnaceFuelHandler.registerFuel(globTar, TFProps.globTarFuel);
		FurnaceFuelHandler.registerFuel(crystalCrudeOil, TFProps.fuelCokeFuel);
		FurnaceFuelHandler.registerFuel(dustPyrotheum, TFProps.dustPyrotheumFuel);

		return true;
	}

	public static void addConfigurableGearRecipe(ItemStack gear, String ingot) {

		if (TFProps.enableAlternateGears) {
			addGearRecipe(gear, ingot, "ingotIron");
		} else {
			addGearRecipe(gear, ingot);
		}
	}

	/* REFERENCES */
	public static ItemStack nuggetIron;
	public static ItemStack nuggetGold;

	public static ItemStack ingotIron;
	public static ItemStack ingotGold;
	public static ItemStack gemDiamond;
	public static ItemStack gemEmerald;

	public static ItemStack dustIron;
	public static ItemStack dustGold;

	public static ItemStack nuggetDiamond;
	public static ItemStack nuggetEmerald;

	public static ItemStack gearWood;
	public static ItemStack gearStone;
	public static ItemStack gearIron;
	public static ItemStack gearGold;
	public static ItemStack gearDiamond;
	public static ItemStack gearEmerald;

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

	public static ItemStack partToolCasing;
	public static ItemStack partDrillHead;
	public static ItemStack partSawBlade;

	public static ItemStack dustCoal;
	public static ItemStack dustCharcoal;
	public static ItemStack dustObsidian;
	public static ItemStack dustSulfur;
	public static ItemStack dustNiter;

	public static ItemStack dustWood;
	public static ItemStack dustWoodCompressed;
	public static ItemStack fuelCoke;

	public static ItemStack dustBiomass;
	public static ItemStack dustBiomassRich;
	public static ItemStack dustBioblend;
	public static ItemStack dustBioblendRich;

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
