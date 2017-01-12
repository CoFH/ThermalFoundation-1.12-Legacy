package cofh.thermalfoundation.item;

import codechicken.lib.item.ItemMultiType;
import cofh.core.item.ItemBucket;
import cofh.core.util.energy.FurnaceFuelHandler;
import cofh.core.util.fluid.BucketHandler;
import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.core.TFProps;
import cofh.thermalfoundation.fluid.TFFluids;
import cofh.thermalfoundation.item.tool.Equipment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import static net.minecraft.item.EnumRarity.*;

public class TFItems {

	public static ItemBucket itemBucket;
	public static ItemLexicon itemLexicon;
	public static ItemMultiType itemMaterial;

	public static ItemStack bucketRedstone;
	public static ItemStack bucketGlowstone;
	public static ItemStack bucketEnder;
	public static ItemStack bucketPyrotheum;
	public static ItemStack bucketCryotheum;
	public static ItemStack bucketAerotheum;
	public static ItemStack bucketPetrotheum;
	public static ItemStack bucketMana;
	public static ItemStack bucketCoal;

	public static ItemStack lexicon;

	public static ItemStack ingotIron;
	public static ItemStack ingotGold;
	public static ItemStack nuggetGold;

	public static ItemStack dustIron;
	public static ItemStack dustGold;
	public static ItemStack dustCoal;
	public static ItemStack dustCharcoal;
	public static ItemStack dustObsidian;

	public static ItemStack nuggetIron;
	public static ItemStack gearIron;
	public static ItemStack gearGold;

	public static ItemStack dustSulfur;
	public static ItemStack dustNiter;

	public static ItemStack crystalCinnabar;

	public static ItemStack ingotCopper;
	public static ItemStack ingotTin;
	public static ItemStack ingotSilver;
	public static ItemStack ingotLead;
	public static ItemStack ingotNickel;
	public static ItemStack ingotPlatinum;
	public static ItemStack ingotMithril;
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
	public static ItemStack dustNickel;
	public static ItemStack dustPlatinum;
	public static ItemStack dustMithril;
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
	public static ItemStack nuggetNickel;
	public static ItemStack nuggetPlatinum;
	public static ItemStack nuggetMithril;
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
	public static ItemStack gearPlatinum;
	public static ItemStack gearMithril;
	public static ItemStack gearElectrum;
	public static ItemStack gearInvar;
	public static ItemStack gearBronze;
	public static ItemStack gearSignalum;
	public static ItemStack gearLumium;
	public static ItemStack gearEnderium;

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

	public static void preInit() {

		itemLexicon = (ItemLexicon) new ItemLexicon("lexicon").setUnlocalizedName("tome", "lexicon");
		itemBucket = (ItemBucket) new ItemBucket("thermalfoundation").setUnlocalizedName("bucket").setCreativeTab(ThermalFoundation.tabCommon);

		itemMaterial = new ItemMultiType(ThermalFoundation.tabCommon, "material").setUnlocalizedName("thermalfoundation.material.").setUseStackRegistry();
		GameRegistry.register(itemMaterial);

		bucketRedstone = itemBucket.addOreDictItem(0, "bucketRedstone", 1);
		bucketGlowstone = itemBucket.addOreDictItem(1, "bucketGlowstone", 1);
		bucketEnder = itemBucket.addOreDictItem(2, "bucketEnder", 1);
		bucketPyrotheum = itemBucket.addOreDictItem(3, "bucketPyrotheum", 2);
		bucketCryotheum = itemBucket.addOreDictItem(4, "bucketCryotheum", 2);
		bucketAerotheum = itemBucket.addOreDictItem(7, "bucketAerotheum", 2);
		bucketPetrotheum = itemBucket.addOreDictItem(8, "bucketPetrotheum", 2);
		bucketMana = itemBucket.addItem(5, "bucketMana", 3);
		bucketCoal = itemBucket.addOreDictItem(6, "bucketCoal");

		lexicon = new ItemStack(itemLexicon);
		itemLexicon.setEmpoweredState(lexicon, false);

		/* Vanilla Derived */
		dustCoal = itemMaterial.registerSubItemOreDict(2, "dustCoal");
		OreDictionary.registerOre("dyeBlack", dustCoal.copy());
		dustCharcoal = itemMaterial.registerSubItemOreDict(3, "dustCharcoal");
		OreDictionary.registerOre("dyeBlack", dustCharcoal.copy());
		dustObsidian = itemMaterial.registerSubItemOreDict(4, "dustObsidian");

		dustSulfur = itemMaterial.registerSubItemOreDict(16, "dustSulfur");
		OreDictionary.registerOre("dyeYellow", dustSulfur.copy());
		dustNiter = itemMaterial.registerSubItemOreDict(17, "dustNiter");
		OreDictionary.registerOre("dustSaltpeter", dustNiter);

		crystalCinnabar = itemMaterial.registerSubItemOreDict(20, "crystalCinnabar");

		dustIron = itemMaterial.registerSubItemOreDict(0, "dustIron");
		dustGold = itemMaterial.registerSubItemOreDict(1, "dustGold");
		/* Dusts */
		dustCopper = itemMaterial.registerSubItemOreDict(32, "dustCopper");
		dustTin = itemMaterial.registerSubItemOreDict(33, "dustTin");
		dustSilver = itemMaterial.registerSubItemOreDict(34, "dustSilver");
		dustLead = itemMaterial.registerSubItemOreDict(35, "dustLead");
		dustNickel = itemMaterial.registerSubItemOreDict(36, "dustNickel");
		dustPlatinum = itemMaterial.registerSubItemOreDict(37, "dustPlatinum", UNCOMMON);
		dustMithril = itemMaterial.registerSubItemOreDict(38, "dustMithril", RARE);
		dustElectrum = itemMaterial.registerSubItemOreDict(39, "dustElectrum");
		dustInvar = itemMaterial.registerSubItemOreDict(40, "dustInvar");
		dustBronze = itemMaterial.registerSubItemOreDict(41, "dustBronze");
		dustSignalum = itemMaterial.registerSubItemOreDict(42, "dustSignalum", UNCOMMON);
		dustLumium = itemMaterial.registerSubItemOreDict(43, "dustLumium", UNCOMMON);
		dustEnderium = itemMaterial.registerSubItemOreDict(44, "dustEnderium", RARE);

		/* Ingots */
		ingotCopper = itemMaterial.registerSubItemOreDict(64, "ingotCopper");
		ingotTin = itemMaterial.registerSubItemOreDict(65, "ingotTin");
		ingotSilver = itemMaterial.registerSubItemOreDict(66, "ingotSilver");
		ingotLead = itemMaterial.registerSubItemOreDict(67, "ingotLead");
		ingotNickel = itemMaterial.registerSubItemOreDict(68, "ingotNickel");
		ingotPlatinum = itemMaterial.registerSubItemOreDict(69, "ingotPlatinum", UNCOMMON);
		ingotMithril = itemMaterial.registerSubItemOreDict(70, "ingotMithril", RARE);
		ingotElectrum = itemMaterial.registerSubItemOreDict(71, "ingotElectrum");
		ingotInvar = itemMaterial.registerSubItemOreDict(72, "ingotInvar");
		ingotBronze = itemMaterial.registerSubItemOreDict(73, "ingotBronze");
		ingotSignalum = itemMaterial.registerSubItemOreDict(74, "ingotSignalum", UNCOMMON);
		ingotLumium = itemMaterial.registerSubItemOreDict(75, "ingotLumium", UNCOMMON);
		ingotEnderium = itemMaterial.registerSubItemOreDict(76, "ingotEnderium", RARE);

		nuggetIron = itemMaterial.registerSubItemOreDict(8, "nuggetIron");
		/* Nuggets */
		nuggetCopper = itemMaterial.registerSubItemOreDict(96, "nuggetCopper");
		nuggetTin = itemMaterial.registerSubItemOreDict(97, "nuggetTin");
		nuggetSilver = itemMaterial.registerSubItemOreDict(98, "nuggetSilver");
		nuggetLead = itemMaterial.registerSubItemOreDict(99, "nuggetLead");
		nuggetNickel = itemMaterial.registerSubItemOreDict(100, "nuggetNickel");
		nuggetPlatinum = itemMaterial.registerSubItemOreDict(101, "nuggetPlatinum", UNCOMMON);
		nuggetMithril = itemMaterial.registerSubItemOreDict(102, "nuggetMithril", RARE);
		nuggetElectrum = itemMaterial.registerSubItemOreDict(103, "nuggetElectrum");
		nuggetInvar = itemMaterial.registerSubItemOreDict(104, "nuggetInvar");
		nuggetBronze = itemMaterial.registerSubItemOreDict(105, "nuggetBronze");
		nuggetSignalum = itemMaterial.registerSubItemOreDict(106, "nuggetSignalum", UNCOMMON);
		nuggetLumium = itemMaterial.registerSubItemOreDict(107, "nuggetLumium", UNCOMMON);
		nuggetEnderium = itemMaterial.registerSubItemOreDict(108, "nuggetEnderium", RARE);

		gearIron = itemMaterial.registerSubItemOreDict(12, "gearIron");
		gearGold = itemMaterial.registerSubItemOreDict(13, "gearGold");
		/* Gears */
		gearCopper = itemMaterial.registerSubItemOreDict(128, "gearCopper");
		gearTin = itemMaterial.registerSubItemOreDict(129, "gearTin");
		gearSilver = itemMaterial.registerSubItemOreDict(130, "gearSilver");
		gearLead = itemMaterial.registerSubItemOreDict(131, "gearLead");
		gearNickel = itemMaterial.registerSubItemOreDict(132, "gearNickel");
		gearPlatinum = itemMaterial.registerSubItemOreDict(133, "gearPlatinum", UNCOMMON);
		gearMithril = itemMaterial.registerSubItemOreDict(134, "gearMithril", RARE);
		gearElectrum = itemMaterial.registerSubItemOreDict(135, "gearElectrum");
		gearInvar = itemMaterial.registerSubItemOreDict(136, "gearInvar");
		gearBronze = itemMaterial.registerSubItemOreDict(137, "gearBronze");
		gearSignalum = itemMaterial.registerSubItemOreDict(138, "gearSignalum", UNCOMMON);
		gearLumium = itemMaterial.registerSubItemOreDict(139, "gearLumium", UNCOMMON);
		gearEnderium = itemMaterial.registerSubItemOreDict(140, "gearEnderium", RARE);

		/* Additional Items */
		dustPyrotheum = itemMaterial.registerSubItemOreDict(512, "dustPyrotheum", RARE);
		dustCryotheum = itemMaterial.registerSubItemOreDict(513, "dustCryotheum", RARE);
		dustAerotheum = itemMaterial.registerSubItemOreDict(514, "dustAerotheum", RARE);
		dustPetrotheum = itemMaterial.registerSubItemOreDict(515, "dustPetrotheum", RARE);
		dustMana = itemMaterial.registerSubItem(516, "dustMana", EPIC);

		FurnaceFuelHandler.registerFuel(dustPyrotheum, TFProps.dustPyrotheumFuel);

		/* Mob Drops */
		rodBlizz = itemMaterial.registerSubItemOreDict(1024, "rodBlizz");
		dustBlizz = itemMaterial.registerSubItemOreDict(1025, "dustBlizz");
		rodBlitz = itemMaterial.registerSubItemOreDict(1026, "rodBlitz");
		dustBlitz = itemMaterial.registerSubItemOreDict(1027, "dustBlitz");
		rodBasalz = itemMaterial.registerSubItemOreDict(1028, "rodBasalz");
		dustBasalz = itemMaterial.registerSubItemOreDict(1029, "dustBasalz");

		/* Equipment */
		Equipment.preInit();
	}

	public static void initialize() {

		ingotIron = new ItemStack(Items.IRON_INGOT);
		ingotGold = new ItemStack(Items.GOLD_INGOT);
		nuggetGold = new ItemStack(Items.GOLD_NUGGET);

		BucketHandler.registerBucket(TFFluids.blockFluidRedstone, 0, bucketRedstone);
		BucketHandler.registerBucket(TFFluids.blockFluidGlowstone, 0, bucketGlowstone);
		BucketHandler.registerBucket(TFFluids.blockFluidEnder, 0, bucketEnder);
		BucketHandler.registerBucket(TFFluids.blockFluidPyrotheum, 0, bucketPyrotheum);
		BucketHandler.registerBucket(TFFluids.blockFluidCryotheum, 0, bucketCryotheum);
		BucketHandler.registerBucket(TFFluids.blockFluidAerotheum, 0, bucketAerotheum);
		BucketHandler.registerBucket(TFFluids.blockFluidPetrotheum, 0, bucketPetrotheum);
		BucketHandler.registerBucket(TFFluids.blockFluidMana, 0, bucketMana);
		BucketHandler.registerBucket(TFFluids.blockFluidCoal, 0, bucketCoal);

		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidRedstone, bucketRedstone, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidGlowstone, bucketGlowstone, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidEnder, bucketEnder, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidPyrotheum, bucketPyrotheum, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidCryotheum, bucketCryotheum, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidAerotheum, bucketAerotheum, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidPetrotheum, bucketPetrotheum, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidMana, bucketMana, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidCoal, bucketCoal, FluidContainerRegistry.EMPTY_BUCKET);

		/* Equipment */
		Equipment.initialize();
	}

	public static void postInit() {

		GameRegistry.addRecipe(new ShapedOreRecipe(lexicon, " D ", "GBI", " R ", 'D', Items.DIAMOND, 'G', "ingotGold", 'B', Items.BOOK, 'I', "ingotIron", 'R', "dustRedstone"));

		// @formatter: off
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustPyrotheum, 2), "dustCoal", "dustSulfur", "dustRedstone", Items.BLAZE_POWDER));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustCryotheum, 2), Items.SNOWBALL, "dustSaltpeter", "dustRedstone", "dustBlizz"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustAerotheum, 2), "sand", "dustSaltpeter", "dustRedstone", "dustBlitz"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustPetrotheum, 2), Items.CLAY_BALL, "dustObsidian", "dustRedstone", "dustBasalz"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustBlizz, 2), "rodBlizz"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustBlitz, 2), "rodBlitz"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustBasalz, 2), "rodBasalz"));
		// @formatter: on

		/* Smelting */
		GameRegistry.addSmelting(dustIron, ingotIron, 0.0F);
		GameRegistry.addSmelting(dustGold, ingotGold, 0.0F);
		GameRegistry.addSmelting(dustCopper, ingotCopper, 0.0F);
		GameRegistry.addSmelting(dustTin, ingotTin, 0.0F);
		GameRegistry.addSmelting(dustSilver, ingotSilver, 0.0F);
		GameRegistry.addSmelting(dustLead, ingotLead, 0.0F);
		GameRegistry.addSmelting(dustNickel, ingotNickel, 0.0F);
		GameRegistry.addSmelting(dustPlatinum, ingotPlatinum, 0.0F);
		GameRegistry.addSmelting(dustMithril, ingotMithril, 0.0F);
		GameRegistry.addSmelting(dustElectrum, ingotElectrum, 0.0F);
		GameRegistry.addSmelting(dustInvar, ingotInvar, 0.0F);
		GameRegistry.addSmelting(dustBronze, ingotBronze, 0.0F);
		GameRegistry.addSmelting(dustSignalum, ingotSignalum, 0.0F);
		GameRegistry.addSmelting(dustLumium, ingotLumium, 0.0F);
		// No Enderium

		/* Alloy Recipes */
		// @formatter: off
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustElectrum, 2), "dustGold", "dustSilver"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustInvar, 3), "dustIron", "dustIron", "dustNickel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustBronze, 4), "dustCopper", "dustCopper", "dustCopper", "dustTin"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustSignalum, 4), "dustCopper", "dustCopper", "dustCopper", "dustSilver", "bucketRedstone"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustLumium, 4), "dustTin", "dustTin", "dustTin", "dustSilver", "bucketGlowstone"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustEnderium, 4), "dustTin", "dustTin", "dustSilver", "dustPlatinum", "bucketEnder"));
		// @formatter: on

		/* Storage */
		ItemHelper.addTwoWayStorageRecipe(ingotIron, "ingotIron", nuggetIron, "nuggetIron");
		ItemHelper.addTwoWayStorageRecipe(ingotCopper, "ingotCopper", nuggetCopper, "nuggetCopper");
		ItemHelper.addTwoWayStorageRecipe(ingotTin, "ingotTin", nuggetTin, "nuggetTin");
		ItemHelper.addTwoWayStorageRecipe(ingotSilver, "ingotSilver", nuggetSilver, "nuggetSilver");
		ItemHelper.addTwoWayStorageRecipe(ingotLead, "ingotLead", nuggetLead, "nuggetLead");
		ItemHelper.addTwoWayStorageRecipe(ingotNickel, "ingotNickel", nuggetNickel, "nuggetNickel");
		ItemHelper.addTwoWayStorageRecipe(ingotPlatinum, "ingotPlatinum", nuggetPlatinum, "nuggetPlatinum");
		ItemHelper.addTwoWayStorageRecipe(ingotMithril, "ingotMithril", nuggetMithril, "nuggetMithril");
		ItemHelper.addTwoWayStorageRecipe(ingotElectrum, "ingotElectrum", nuggetElectrum, "nuggetElectrum");
		ItemHelper.addTwoWayStorageRecipe(ingotInvar, "ingotInvar", nuggetInvar, "nuggetInvar");
		ItemHelper.addTwoWayStorageRecipe(ingotBronze, "ingotBronze", nuggetBronze, "nuggetBronze");
		ItemHelper.addTwoWayStorageRecipe(ingotSignalum, "ingotSignalum", nuggetSignalum, "nuggetSignalum");
		ItemHelper.addTwoWayStorageRecipe(ingotLumium, "ingotLumium", nuggetLumium, "nuggetLumium");
		ItemHelper.addTwoWayStorageRecipe(ingotEnderium, "ingotEnderium", nuggetEnderium, "nuggetEnderium");

		ItemHelper.addReverseStorageRecipe(ingotCopper, "blockCopper");
		ItemHelper.addReverseStorageRecipe(ingotTin, "blockTin");
		ItemHelper.addReverseStorageRecipe(ingotSilver, "blockSilver");
		ItemHelper.addReverseStorageRecipe(ingotLead, "blockLead");
		ItemHelper.addReverseStorageRecipe(ingotNickel, "blockNickel");
		ItemHelper.addReverseStorageRecipe(ingotPlatinum, "blockPlatinum");
		ItemHelper.addReverseStorageRecipe(ingotMithril, "blockMithril");
		ItemHelper.addReverseStorageRecipe(ingotElectrum, "blockElectrum");
		ItemHelper.addReverseStorageRecipe(ingotInvar, "blockInvar");
		ItemHelper.addReverseStorageRecipe(ingotBronze, "blockBronze");
		ItemHelper.addReverseStorageRecipe(ingotSignalum, "blockSignalum");
		ItemHelper.addReverseStorageRecipe(ingotLumium, "blockLumium");
		ItemHelper.addReverseStorageRecipe(ingotEnderium, "blockEnderium");

		/* Gears */
		ItemHelper.addGearRecipe(gearIron, "ingotIron", "ingotIron");
		ItemHelper.addGearRecipe(gearGold, "ingotGold", "ingotIron");
		ItemHelper.addGearRecipe(gearCopper, "ingotCopper", "ingotIron");
		ItemHelper.addGearRecipe(gearTin, "ingotTin", "ingotIron");
		ItemHelper.addGearRecipe(gearSilver, "ingotSilver", "ingotIron");
		ItemHelper.addGearRecipe(gearLead, "ingotLead", "ingotIron");
		ItemHelper.addGearRecipe(gearNickel, "ingotNickel", "ingotIron");
		ItemHelper.addGearRecipe(gearPlatinum, "ingotPlatinum", "ingotIron");
		ItemHelper.addGearRecipe(gearMithril, "ingotMithril", "ingotIron");
		ItemHelper.addGearRecipe(gearElectrum, "ingotElectrum", "ingotIron");
		ItemHelper.addGearRecipe(gearInvar, "ingotInvar", "ingotIron");
		ItemHelper.addGearRecipe(gearBronze, "ingotBronze", "ingotIron");
		ItemHelper.addGearRecipe(gearSignalum, "ingotSignalum", "ingotIron");
		ItemHelper.addGearRecipe(gearLumium, "ingotLumium", "ingotIron");
		ItemHelper.addGearRecipe(gearEnderium, "ingotEnderium", "ingotIron");

		/* Equipment */
		Equipment.postInit();
	}
}
