package cofh.thermalfoundation.item;

import cofh.api.core.IInitializer;
import cofh.core.item.ItemMulti;
import cofh.core.util.energy.FurnaceFuelHandler;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.init.TFProps;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
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

		/* Vanilla Derived */
		dustIron = addOreDictItem(0, "dustIron");
		dustGold = addOreDictItem(1, "dustGold");
		dustCoal = addOreDictItem(2, "dustCoal");
		dustCharcoal = addOreDictItem(3, "dustCharcoal");
		dustObsidian = addOreDictItem(4, "dustObsidian");

		dustSulfur = addOreDictItem(16, "dustSulfur");
		dustNiter = addItem(17, "dustNiter");
		OreDictionary.registerOre("dustSaltpeter", dustNiter);

		dustWood = addOreDictItem(20, "dustWood");
		dustWoodCompressed = addItem(21, "dustWoodCompressed");

		crystalSlag = addOreDictItem(24, "crystalSlag");
		crystalSlagRich = addOreDictItem(25, "crystalSlagRich");
		crystalCinnabar = addOreDictItem(26, "crystalCinnabar");

		/* Dusts */
		dustCopper = addOreDictItem(32, "dustCopper");
		dustTin = addOreDictItem(33, "dustTin");
		dustSilver = addOreDictItem(34, "dustSilver");
		dustLead = addOreDictItem(35, "dustLead");
		dustAluminum = addOreDictItem(36, "dustAluminum");
		dustNickel = addOreDictItem(37, "dustNickel");
		dustPlatinum = addOreDictItem(38, "dustPlatinum", EnumRarity.UNCOMMON);
		dustIridium = addOreDictItem(39, "dustIridium", EnumRarity.UNCOMMON);
		dustMithril = addOreDictItem(40, "dustMithril", EnumRarity.RARE);

		dustSteel = addOreDictItem(48, "dustSteel");
		dustElectrum = addOreDictItem(49, "dustElectrum");
		dustInvar = addOreDictItem(50, "dustInvar");
		dustBronze = addOreDictItem(51, "dustBronze");
		dustSignalum = addOreDictItem(52, "dustSignalum", EnumRarity.UNCOMMON);
		dustLumium = addOreDictItem(53, "dustLumium", EnumRarity.UNCOMMON);
		dustEnderium = addOreDictItem(54, "dustEnderium", EnumRarity.RARE);

		/* Ingots */
		ingotCopper = addOreDictItem(64, "ingotCopper");
		ingotTin = addOreDictItem(65, "ingotTin");
		ingotSilver = addOreDictItem(66, "ingotSilver");
		ingotLead = addOreDictItem(67, "ingotLead");
		ingotAluminum = addOreDictItem(68, "ingotAluminum");
		ingotNickel = addOreDictItem(69, "ingotNickel");
		ingotPlatinum = addOreDictItem(70, "ingotPlatinum", EnumRarity.UNCOMMON);
		ingotIridium = addOreDictItem(71, "ingotIridium", EnumRarity.UNCOMMON);
		ingotMithril = addOreDictItem(72, "ingotMithril", EnumRarity.RARE);

		ingotSteel = addOreDictItem(80, "ingotSteel");
		ingotElectrum = addOreDictItem(81, "ingotElectrum");
		ingotInvar = addOreDictItem(82, "ingotInvar");
		ingotBronze = addOreDictItem(83, "ingotBronze");
		ingotSignalum = addOreDictItem(84, "ingotSignalum", EnumRarity.UNCOMMON);
		ingotLumium = addOreDictItem(85, "ingotLumium", EnumRarity.UNCOMMON);
		ingotEnderium = addOreDictItem(86, "ingotEnderium", EnumRarity.RARE);

		/* Nuggets */
		nuggetDiamond = addOreDictItem(8, "nuggetDiamond");
		nuggetCopper = addOreDictItem(96, "nuggetCopper");
		nuggetTin = addOreDictItem(97, "nuggetTin");
		nuggetSilver = addOreDictItem(98, "nuggetSilver");
		nuggetLead = addOreDictItem(99, "nuggetLead");
		nuggetAluminum = addOreDictItem(100, "nuggetAluminum");
		nuggetNickel = addOreDictItem(101, "nuggetNickel");
		nuggetPlatinum = addOreDictItem(102, "nuggetPlatinum", EnumRarity.UNCOMMON);
		nuggetIridium = addOreDictItem(103, "nuggetIridium", EnumRarity.UNCOMMON);
		nuggetMithril = addOreDictItem(104, "nuggetMithril", EnumRarity.RARE);

		nuggetSteel = addOreDictItem(112, "nuggetSteel");
		nuggetElectrum = addOreDictItem(113, "nuggetElectrum");
		nuggetInvar = addOreDictItem(114, "nuggetInvar");
		nuggetBronze = addOreDictItem(115, "nuggetBronze");
		nuggetSignalum = addOreDictItem(116, "nuggetSignalum", EnumRarity.UNCOMMON);
		nuggetLumium = addOreDictItem(117, "nuggetLumium", EnumRarity.UNCOMMON);
		nuggetEnderium = addOreDictItem(118, "nuggetEnderium", EnumRarity.RARE);

		/* Gears */
		gearIron = addOreDictItem(12, "gearIron");
		gearGold = addOreDictItem(13, "gearGold");
		gearCopper = addOreDictItem(128, "gearCopper");
		gearTin = addOreDictItem(129, "gearTin");
		gearSilver = addOreDictItem(130, "gearSilver");
		gearLead = addOreDictItem(131, "gearLead");
		gearAluminum = addOreDictItem(132, "gearAluminum");
		gearNickel = addOreDictItem(133, "gearNickel");
		gearPlatinum = addOreDictItem(134, "gearPlatinum", EnumRarity.UNCOMMON);
		gearIridium = addOreDictItem(135, "gearIridium", EnumRarity.UNCOMMON);
		gearMithril = addOreDictItem(136, "gearMithril", EnumRarity.RARE);

		gearSteel = addOreDictItem(144, "gearSteel");
		gearElectrum = addOreDictItem(145, "gearElectrum");
		gearInvar = addOreDictItem(146, "gearInvar");
		gearBronze = addOreDictItem(147, "gearBronze");
		gearSignalum = addOreDictItem(148, "gearSignalum", EnumRarity.UNCOMMON);
		gearLumium = addOreDictItem(149, "gearLumium", EnumRarity.UNCOMMON);
		gearEnderium = addOreDictItem(150, "gearEnderium", EnumRarity.RARE);

		/* Parts */
		powerCoilGold = addItem(257, "powerCoilGold");
		powerCoilSilver = addItem(258, "powerCoilSilver");
		powerCoilElectrum = addItem(259, "powerCoilElectrum");

		/* Additional Items */
		dustPyrotheum = addOreDictItem(512, "dustPyrotheum", EnumRarity.RARE);
		dustCryotheum = addOreDictItem(513, "dustCryotheum", EnumRarity.RARE);
		dustAerotheum = addOreDictItem(514, "dustAerotheum", EnumRarity.RARE);
		dustPetrotheum = addOreDictItem(515, "dustPetrotheum", EnumRarity.RARE);
		dustMana = addOreDictItem(516, "dustMana", EnumRarity.EPIC);

		FurnaceFuelHandler.registerFuel(dustPyrotheum, TFProps.dustPyrotheumFuel);

		/* Mob Drops */
		rodBlizz = addOreDictItem(1024, "rodBlizz");
		dustBlizz = addOreDictItem(1025, "dustBlizz");
		rodBlitz = addOreDictItem(1026, "rodBlitz");
		dustBlitz = addOreDictItem(1027, "dustBlitz");
		rodBasalz = addOreDictItem(1028, "rodBasalz");
		dustBasalz = addOreDictItem(1029, "dustBasalz");

		return true;
	}

	@Override
	public boolean initialize() {

		nuggetIron = null; // TODO
		nuggetGold = new ItemStack(Items.GOLD_NUGGET);

		ingotIron = new ItemStack(Items.IRON_INGOT);
		ingotGold = new ItemStack(Items.GOLD_INGOT);
		gemDiamond = new ItemStack(Items.DIAMOND);

		return true;
	}

	@Override
	public boolean postInit() {

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

		// No Steel
		addSmelting(ingotElectrum, dustElectrum, 0.0F);
		addSmelting(ingotInvar, dustInvar, 0.0F);
		addSmelting(ingotBronze, dustBronze, 0.0F);
		addSmelting(ingotSignalum, dustSignalum, 0.0F);
		addSmelting(ingotLumium, dustLumium, 0.0F);
		// No Enderium

		/* Alloy Recipes */
		addRecipe(ShapelessRecipe(cloneStack(dustSteel, 1), "dustIron", "dustCoal", "dustCoal", "dustCoal", "dustCoal"));
		addRecipe(ShapelessRecipe(cloneStack(dustSteel, 1), "dustIron", "dustCharcoal", "dustCharcoal", "dustCharcoal", "dustCharcoal"));
		addRecipe(ShapelessRecipe(cloneStack(dustElectrum, 2), "dustGold", "dustSilver"));
		addRecipe(ShapelessRecipe(cloneStack(dustInvar, 3), "dustIron", "dustIron", "dustNickel"));
		addRecipe(ShapelessRecipe(cloneStack(dustBronze, 4), "dustCopper", "dustCopper", "dustCopper", "dustTin"));
		addRecipe(ShapelessRecipe(cloneStack(dustSignalum, 4), "dustCopper", "dustCopper", "dustSilver", "dustSilver", "bucketRedstone"));
		addRecipe(ShapelessRecipe(cloneStack(dustLumium, 4), "dustTin", "dustTin", "dustSilver", "dustSilver", "bucketGlowstone"));
		addRecipe(ShapelessRecipe(cloneStack(dustEnderium, 4), "dustTin", "dustTin", "dustSilver", "dustPlatinum", "bucketEnder"));

		/* Storage */
		addTwoWayStorageRecipe(gemDiamond, "gemDiamond", nuggetDiamond, "nuggetDiamond");

		addTwoWayStorageRecipe(ingotCopper, "ingotCopper", nuggetCopper, "nuggetCopper");
		addTwoWayStorageRecipe(ingotTin, "ingotTin", nuggetTin, "nuggetTin");
		addTwoWayStorageRecipe(ingotSilver, "ingotSilver", nuggetSilver, "nuggetSilver");
		addTwoWayStorageRecipe(ingotLead, "ingotLead", nuggetLead, "nuggetLead");
		addTwoWayStorageRecipe(ingotAluminum, "ingotAluminum", nuggetLead, "nuggetAluminum");
		addTwoWayStorageRecipe(ingotNickel, "ingotNickel", nuggetNickel, "nuggetNickel");
		addTwoWayStorageRecipe(ingotPlatinum, "ingotPlatinum", nuggetPlatinum, "nuggetPlatinum");
		addTwoWayStorageRecipe(ingotIridium, "ingotIridium", nuggetIridium, "nuggetIridium");
		addTwoWayStorageRecipe(ingotMithril, "ingotMithril", nuggetMithril, "nuggetMithril");

		addTwoWayStorageRecipe(ingotSteel, "ingotSteel", nuggetSteel, "nuggetSteel");
		addTwoWayStorageRecipe(ingotElectrum, "ingotElectrum", nuggetElectrum, "nuggetElectrum");
		addTwoWayStorageRecipe(ingotInvar, "ingotInvar", nuggetInvar, "nuggetInvar");
		addTwoWayStorageRecipe(ingotBronze, "ingotBronze", nuggetBronze, "nuggetBronze");
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
		addGearRecipe(gearSignalum, "ingotSignalum");
		addGearRecipe(gearLumium, "ingotLumium");
		addGearRecipe(gearEnderium, "ingotEnderium");

		/* Parts */
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

		/* Misc Recipes */
		addSmelting(dustWoodCompressed, new ItemStack(Items.COAL, 1, 1), 0.15F);

		addRecipe(ShapedRecipe(dustWoodCompressed, "###", "# #", "###", '#', "dustWood"));
		addRecipe(ShapelessRecipe(new ItemStack(Items.CLAY_BALL, 4), crystalSlag, crystalSlag, Blocks.DIRT, Items.WATER_BUCKET));

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
	public static ItemStack dustCoal;
	public static ItemStack dustCharcoal;
	public static ItemStack dustObsidian;
	public static ItemStack dustWood;
	public static ItemStack dustWoodCompressed;

	public static ItemStack nuggetDiamond;

	public static ItemStack gearIron;
	public static ItemStack gearGold;

	public static ItemStack dustSulfur;
	public static ItemStack dustNiter;

	public static ItemStack crystalSlag;
	public static ItemStack crystalSlagRich;
	public static ItemStack crystalCinnabar;

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
	public static ItemStack gearSignalum;
	public static ItemStack gearLumium;
	public static ItemStack gearEnderium;

	public static ItemStack pneumaticServo;
	public static ItemStack powerCoilGold;
	public static ItemStack powerCoilSilver;
	public static ItemStack powerCoilElectrum;

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

}
