package cofh.thermalfoundation.item;

import static cofh.lib.util.helpers.ItemHelper.ShapelessRecipe;

import cofh.core.item.ItemBase;
import cofh.core.item.ItemBucket;
import cofh.core.util.fluid.BucketHandler;
import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.block.TFBlocks;
import cofh.thermalfoundation.fluid.TFFluids;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class TFItems {

	public static void preInit() {

		itemLexicon = (ItemLexicon) new ItemLexicon("lexicon").setUnlocalizedName("tome", "lexicon");
		itemBucket = (ItemBucket) new ItemBucket("thermalfoundation").setUnlocalizedName("bucket").setCreativeTab(ThermalFoundation.tabCommon);
		itemMaterial = (ItemBase) new ItemBase("thermalfoundation").setUnlocalizedName("material").setCreativeTab(ThermalFoundation.tabCommon);

		bucketRedstone = itemBucket.addOreDictItem(0, "bucketRedstone", 1);
		bucketGlowstone = itemBucket.addOreDictItem(1, "bucketGlowstone", 1);
		bucketEnder = itemBucket.addOreDictItem(2, "bucketEnder", 1);
		bucketPyrotheum = itemBucket.addOreDictItem(3, "bucketPyrotheum", 2);
		bucketCryotheum = itemBucket.addOreDictItem(4, "bucketCryotheum", 2);
		// bucketAerotheum = itemBucket.addOreDictItem(7, "bucketAerotheum", 2);
		// bucketPetrotheum = itemBucket.addOreDictItem(8, "bucketPetrotheum", 2);
		bucketMana = itemBucket.addItem(5, "bucketMana", 3);
		bucketCoal = itemBucket.addOreDictItem(6, "bucketCoal");

		lexicon = new ItemStack(itemLexicon);

		/* Vanilla Derived */
		dustCoal = itemMaterial.addOreDictItem(2, "dustCoal");
		dustCharcoal = itemMaterial.addOreDictItem(3, "dustCharcoal");
		dustObsidian = itemMaterial.addOreDictItem(4, "dustObsidian");

		dustSulfur = itemMaterial.addOreDictItem(16, "dustSulfur");
		dustNiter = itemMaterial.addItem(17, "dustNiter");
		OreDictionary.registerOre("dustSaltpeter", dustNiter);

		crystalCinnabar = itemMaterial.addOreDictItem(20, "crystalCinnabar");

		dustIron = itemMaterial.addOreDictItem(0, "dustIron");
		dustGold = itemMaterial.addOreDictItem(1, "dustGold");
		/* Dusts */
		dustCopper = itemMaterial.addOreDictItem(32, "dustCopper");
		dustTin = itemMaterial.addOreDictItem(33, "dustTin");
		dustSilver = itemMaterial.addOreDictItem(34, "dustSilver");
		dustLead = itemMaterial.addOreDictItem(35, "dustLead");
		dustNickel = itemMaterial.addOreDictItem(36, "dustNickel");
		dustPlatinum = itemMaterial.addOreDictItem(37, "dustPlatinum", 1);
		dustMithril = itemMaterial.addOreDictItem(38, "dustMithril", 2);
		dustElectrum = itemMaterial.addOreDictItem(39, "dustElectrum");
		dustInvar = itemMaterial.addOreDictItem(40, "dustInvar");
		dustBronze = itemMaterial.addOreDictItem(41, "dustBronze");
		dustSignalum = itemMaterial.addOreDictItem(42, "dustSignalum", 1);
		dustLumium = itemMaterial.addOreDictItem(43, "dustLumium", 1);
		dustEnderium = itemMaterial.addOreDictItem(44, "dustEnderium", 2);

		/* Ingots */
		ingotCopper = itemMaterial.addOreDictItem(64, "ingotCopper");
		ingotTin = itemMaterial.addOreDictItem(65, "ingotTin");
		ingotSilver = itemMaterial.addOreDictItem(66, "ingotSilver");
		ingotLead = itemMaterial.addOreDictItem(67, "ingotLead");
		ingotNickel = itemMaterial.addOreDictItem(68, "ingotNickel");
		ingotPlatinum = itemMaterial.addOreDictItem(69, "ingotPlatinum", 1);
		ingotMithril = itemMaterial.addOreDictItem(70, "ingotMithril", 2);
		ingotElectrum = itemMaterial.addOreDictItem(71, "ingotElectrum");
		ingotInvar = itemMaterial.addOreDictItem(72, "ingotInvar");
		ingotBronze = itemMaterial.addOreDictItem(73, "ingotBronze");
		ingotSignalum = itemMaterial.addOreDictItem(74, "ingotSignalum", 1);
		ingotLumium = itemMaterial.addOreDictItem(75, "ingotLumium", 1);
		ingotEnderium = itemMaterial.addOreDictItem(76, "ingotEnderium", 2);

		nuggetIron = itemMaterial.addOreDictItem(8, "nuggetIron");
		/* Nuggets */
		nuggetCopper = itemMaterial.addOreDictItem(96, "nuggetCopper");
		nuggetTin = itemMaterial.addOreDictItem(97, "nuggetTin");
		nuggetSilver = itemMaterial.addOreDictItem(98, "nuggetSilver");
		nuggetLead = itemMaterial.addOreDictItem(99, "nuggetLead");
		nuggetNickel = itemMaterial.addOreDictItem(100, "nuggetNickel");
		nuggetPlatinum = itemMaterial.addOreDictItem(101, "nuggetPlatinum", 1);
		nuggetMithril = itemMaterial.addOreDictItem(102, "nuggetMithril", 2);
		nuggetElectrum = itemMaterial.addOreDictItem(103, "nuggetElectrum");
		nuggetInvar = itemMaterial.addOreDictItem(104, "nuggetInvar");
		nuggetBronze = itemMaterial.addOreDictItem(105, "nuggetBronze");
		nuggetSignalum = itemMaterial.addOreDictItem(106, "nuggetSignalum", 1);
		nuggetLumium = itemMaterial.addOreDictItem(107, "nuggetLumium", 1);
		nuggetEnderium = itemMaterial.addOreDictItem(108, "nuggetEnderium", 2);

		gearIron = itemMaterial.addOreDictItem(12, "gearIron");
		gearGold = itemMaterial.addOreDictItem(13, "gearGold");
		/* Gears */
		gearCopper = itemMaterial.addOreDictItem(128, "gearCopper");
		gearTin = itemMaterial.addOreDictItem(129, "gearTin");
		gearSilver = itemMaterial.addOreDictItem(130, "gearSilver");
		gearLead = itemMaterial.addOreDictItem(131, "gearLead");
		gearNickel = itemMaterial.addOreDictItem(132, "gearNickel");
		gearPlatinum = itemMaterial.addOreDictItem(133, "gearPlatinum", 1);
		gearMithril = itemMaterial.addOreDictItem(134, "gearMithril", 2);
		gearElectrum = itemMaterial.addOreDictItem(135, "gearElectrum");
		gearInvar = itemMaterial.addOreDictItem(136, "gearInvar");
		gearBronze = itemMaterial.addOreDictItem(137, "gearBronze");
		gearSignalum = itemMaterial.addOreDictItem(138, "gearSignalum", 1);
		gearLumium = itemMaterial.addOreDictItem(139, "gearLumium", 1);
		gearEnderium = itemMaterial.addOreDictItem(140, "gearEnderium", 2);

		/* Additional Items */
		dustPyrotheum = itemMaterial.addOreDictItem(512, "dustPyrotheum", 2);
		dustCryotheum = itemMaterial.addOreDictItem(513, "dustCryotheum", 2);
		dustAerotheum = itemMaterial.addOreDictItem(514, "dustAerotheum", 2);
		dustPetrotheum = itemMaterial.addOreDictItem(515, "dustPetrotheum", 2);
		dustMana = itemMaterial.addItem(516, "dustMana", 3);

		/* Mob Drops */
		rodBlizz = itemMaterial.addOreDictItem(1024, "rodBlizz");
		dustBlizz = itemMaterial.addOreDictItem(1025, "dustBlizz");
		// rodBlitz = itemMaterial.addOreDictItem(1026, "rodBlitz");
		// dustBlitz = itemMaterial.addOreDictItem(1027, "dustBlitz");
		// rodBasalz = itemMaterial.addOreDictItem(1028, "rodBasalz");
		// dustBasalz = itemMaterial.addOreDictItem(1029, "dustBasalz");

		/* Equipment */
		Equipment.preInit();
	}

	public static void initialize() {

		ingotIron = new ItemStack(Items.iron_ingot);
		ingotGold = new ItemStack(Items.gold_ingot);

		BucketHandler.registerBucket(TFBlocks.blockFluidRedstone, 0, bucketRedstone);
		BucketHandler.registerBucket(TFBlocks.blockFluidGlowstone, 0, bucketGlowstone);
		BucketHandler.registerBucket(TFBlocks.blockFluidEnder, 0, bucketEnder);
		BucketHandler.registerBucket(TFBlocks.blockFluidPyrotheum, 0, bucketPyrotheum);
		BucketHandler.registerBucket(TFBlocks.blockFluidCryotheum, 0, bucketCryotheum);
		// BucketHandler.registerBucket(TFBlocks.blockFluidAerotheum, 0, bucketAerotheum);
		// BucketHandler.registerBucket(TFBlocks.blockFluidPetrotheum, 0, bucketPetrotheum);
		BucketHandler.registerBucket(TFBlocks.blockFluidMana, 0, bucketMana);
		BucketHandler.registerBucket(TFBlocks.blockFluidCoal, 0, bucketCoal);

		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidRedstone, bucketRedstone, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidGlowstone, bucketGlowstone, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidEnder, bucketEnder, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidPyrotheum, bucketPyrotheum, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidCryotheum, bucketCryotheum, FluidContainerRegistry.EMPTY_BUCKET);
		// FluidContainerRegistry.registerFluidContainer(TFFluids.fluidAerotheum, bucketAerotheum, FluidContainerRegistry.EMPTY_BUCKET);
		// FluidContainerRegistry.registerFluidContainer(TFFluids.fluidPetrotheum, bucketPetrotheum, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidMana, bucketMana, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidCoal, bucketCoal, FluidContainerRegistry.EMPTY_BUCKET);

		/* Equipment */
		Equipment.initialize();
	}

	public static void postInit() {

		ItemHelper.addRecipe(ShapelessRecipe(itemLexicon, new Object[] { Items.book, "ingotIron", "ingotGold" }));

		// @formatter: off
		ItemHelper.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(dustPyrotheum, 2), new Object[] { "dustCoal", "dustSulfur", "dustRedstone",
				Items.blaze_powder }));
		ItemHelper.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(dustCryotheum, 2), new Object[] { Items.snowball, "dustSaltpeter", "dustRedstone",
				"dustBlizz" }));
		// ItemHelper.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(dustAerotheum, 2), new Object[] { "sand", "dustSaltpeter", "dustRedstone", "dustBlitz"
		// }));
		// ItemHelper.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(dustPetrotheum, 2), new Object[] { Items.clay_ball, "dustObsidian", "dustRedstone",
		// "dustBasalz" }));
		ItemHelper.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(dustBlizz, 2), "rodBlizz"));
		// ItemHelper.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(dustBlitz, 2), "rodBlitz"));
		// ItemHelper.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(dustBasalz, 2), "rodBasalz"));
		// @formatter: on

		/* Smelting */
		ItemHelper.addSmelting(ingotIron, dustIron, 0.0F);
		ItemHelper.addSmelting(ingotGold, dustGold, 0.0F);
		ItemHelper.addSmelting(ingotCopper, dustCopper, 0.0F);
		ItemHelper.addSmelting(ingotTin, dustTin, 0.0F);
		ItemHelper.addSmelting(ingotSilver, dustSilver, 0.0F);
		ItemHelper.addSmelting(ingotLead, dustLead, 0.0F);
		ItemHelper.addSmelting(ingotNickel, dustNickel, 0.0F);
		ItemHelper.addSmelting(ingotPlatinum, dustPlatinum, 0.0F);
		ItemHelper.addSmelting(ingotMithril, dustMithril, 0.0F);
		ItemHelper.addSmelting(ingotElectrum, dustElectrum, 0.0F);
		ItemHelper.addSmelting(ingotInvar, dustInvar, 0.0F);
		ItemHelper.addSmelting(ingotBronze, dustBronze, 0.0F);
		ItemHelper.addSmelting(ingotSignalum, dustSignalum, 0.0F);
		ItemHelper.addSmelting(ingotLumium, dustLumium, 0.0F);
		// No Enderium

		/* Alloy Recipes */
		// @formatter: off
		ItemHelper.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(dustElectrum, 2), new Object[] { "dustGold", "dustSilver" }));
		ItemHelper.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(dustInvar, 3), new Object[] { "dustIron", "dustIron", "dustNickel" }));
		ItemHelper.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(dustBronze, 4), new Object[] { "dustCopper", "dustCopper", "dustCopper", "dustTin" }));
		ItemHelper.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(dustSignalum, 4), new Object[] { "dustCopper", "dustCopper", "dustCopper", "dustSilver",
				"bucketRedstone" }));
		ItemHelper.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(dustLumium, 4), new Object[] { "dustTin", "dustTin", "dustTin", "dustSilver",
				"bucketGlowstone" }));
		ItemHelper.addRecipe(ShapelessRecipe(ItemHelper.cloneStack(dustEnderium, 4), new Object[] { "dustTin", "dustTin", "dustSilver", "dustPlatinum",
				"bucketEnder" }));
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
		ItemHelper.addGearRecipe(gearIron, "ingotIron");
		ItemHelper.addGearRecipe(gearGold, "ingotGold");
		ItemHelper.addGearRecipe(gearCopper, "ingotCopper");
		ItemHelper.addGearRecipe(gearTin, "ingotTin");
		ItemHelper.addGearRecipe(gearSilver, "ingotSilver");
		ItemHelper.addGearRecipe(gearLead, "ingotLead");
		ItemHelper.addGearRecipe(gearNickel, "ingotNickel");
		ItemHelper.addGearRecipe(gearPlatinum, "ingotPlatinum");
		ItemHelper.addGearRecipe(gearMithril, "ingotMithril");
		ItemHelper.addGearRecipe(gearElectrum, "ingotElectrum");
		ItemHelper.addGearRecipe(gearInvar, "ingotInvar");
		ItemHelper.addGearRecipe(gearBronze, "ingotBronze");
		ItemHelper.addGearRecipe(gearSignalum, "ingotSignalum");
		ItemHelper.addGearRecipe(gearLumium, "ingotLumium");
		ItemHelper.addGearRecipe(gearEnderium, "ingotEnderium");

		/* Equipment */
		Equipment.postInit();
	}

	public static ItemBucket itemBucket;
	public static ItemLexicon itemLexicon;
	public static ItemBase itemMaterial;

	public static ItemStack bucketRedstone;
	public static ItemStack bucketGlowstone;
	public static ItemStack bucketEnder;
	public static ItemStack bucketPyrotheum;
	public static ItemStack bucketCryotheum;
	// public static ItemStack bucketAerotheum;
	// public static ItemStack bucketPetrotheum;
	public static ItemStack bucketMana;
	public static ItemStack bucketCoal;

	public static ItemStack lexicon;

	public static ItemStack ingotIron;
	public static ItemStack ingotGold;

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

}
