package thermalfoundation.item;

import cofh.item.ItemBase;
import cofh.item.ItemBucket;
import cofh.util.ItemHelper;
import cofh.util.fluid.BucketHandler;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import thermalfoundation.ThermalFoundation;
import thermalfoundation.block.TFBlocks;
import thermalfoundation.fluid.TFFluids;

public class TFItems {

	public static void preInit() {

		itemBucket = (ItemBucket) new ItemBucket("thermalfoundation").setUnlocalizedName("bucket").setCreativeTab(ThermalFoundation.tab);
		itemMaterial = (ItemBase) new ItemBase("thermalfoundation").setUnlocalizedName("material").setCreativeTab(ThermalFoundation.tab);

		bucketRedstone = itemBucket.addItem(0, "bucketRedstone", 1);
		bucketGlowstone = itemBucket.addItem(1, "bucketGlowstone", 1);
		bucketEnder = itemBucket.addItem(2, "bucketEnder", 1);
		bucketPyrotheum = itemBucket.addItem(3, "bucketPyrotheum", 2);
		bucketCryotheum = itemBucket.addItem(4, "bucketCryotheum", 2);
		bucketMana = itemBucket.addItem(5, "bucketMana", 3);
		bucketCoal = itemBucket.addItem(6, "bucketCoal");

		/* Vanilla Derived */
		dustIron = itemMaterial.addOreDictItem(0, "dustIron");
		dustGold = itemMaterial.addOreDictItem(1, "dustGold");
		dustCoal = itemMaterial.addOreDictItem(2, "dustCoal");
		dustObsidian = itemMaterial.addOreDictItem(4, "dustObsidian");

		dustSulfur = itemMaterial.addOreDictItem(16, "dustSulfur");
		dustNiter = itemMaterial.addOreDictItem(17, "dustNiter");

		crystalCinnabar = itemMaterial.addOreDictItem(20, "crystalCinnabar");

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

		/* Gears */
		gearCopper = itemMaterial.addOreDictItem(128, "gearCopper");
		gearTin = itemMaterial.addOreDictItem(129, "gearTin");
		gearSilver = itemMaterial.addOreDictItem(130, "gearSilver");
		gearLead = itemMaterial.addOreDictItem(131, "gearLead");
		gearNickel = itemMaterial.addOreDictItem(132, "gearNickel");
		gearPlatinum = itemMaterial.addOreDictItem(133, "gearPlatinum");
		gearMithril = itemMaterial.addOreDictItem(134, "gearMithril");
		gearElectrum = itemMaterial.addOreDictItem(135, "gearElectrum");
		gearInvar = itemMaterial.addOreDictItem(136, "gearInvar");
		gearBronze = itemMaterial.addOreDictItem(137, "gearBronze");
		gearSignalum = itemMaterial.addOreDictItem(138, "gearSignalum", 1);
		gearLumium = itemMaterial.addOreDictItem(139, "gearLumium", 1);
		gearEnderium = itemMaterial.addOreDictItem(140, "gearEnderium", 2);

		/* Additional Items */
		dustPyrotheum = itemMaterial.addItem(512, "dustPyrotheum", 2);
		dustCryotheum = itemMaterial.addItem(513, "dustCryotheum", 2);
		dustMana = itemMaterial.addItem(514, "dustMana", 3);

		/* Mob Drops */
		rodBlizz = itemMaterial.addItem(1024, "rodBlizz");
		dustBlizz = itemMaterial.addItem(1025, "dustBlizz");
	}

	public static void initialize() {

		ingotIron = new ItemStack(Items.iron_ingot);
		ingotGold = new ItemStack(Items.gold_ingot);

		FurnaceRecipes.smelting().func_151394_a(dustIron, ingotIron, 0.0F);
		FurnaceRecipes.smelting().func_151394_a(dustGold, ingotGold, 0.0F);
		FurnaceRecipes.smelting().func_151394_a(dustCopper, ingotCopper, 0.0F);
		FurnaceRecipes.smelting().func_151394_a(dustTin, ingotTin, 0.0F);
		FurnaceRecipes.smelting().func_151394_a(dustSilver, ingotSilver, 0.0F);
		FurnaceRecipes.smelting().func_151394_a(dustLead, ingotLead, 0.0F);
		FurnaceRecipes.smelting().func_151394_a(dustNickel, ingotNickel, 0.0F);
		FurnaceRecipes.smelting().func_151394_a(dustPlatinum, ingotPlatinum, 0.0F);
		// No Mithril
		FurnaceRecipes.smelting().func_151394_a(dustElectrum, ingotElectrum, 0.0F);
		FurnaceRecipes.smelting().func_151394_a(dustInvar, ingotInvar, 0.0F);
		FurnaceRecipes.smelting().func_151394_a(dustBronze, ingotBronze, 0.0F);
		FurnaceRecipes.smelting().func_151394_a(dustSignalum, ingotSignalum, 0.0F);
		FurnaceRecipes.smelting().func_151394_a(dustLumium, ingotLumium, 0.0F);
		// No Enderium

		BucketHandler.registerBucket(TFBlocks.blockFluidRedstone, 0, bucketRedstone);
		BucketHandler.registerBucket(TFBlocks.blockFluidGlowstone, 0, bucketGlowstone);
		BucketHandler.registerBucket(TFBlocks.blockFluidEnder, 0, bucketEnder);
		BucketHandler.registerBucket(TFBlocks.blockFluidPyrotheum, 0, bucketPyrotheum);
		BucketHandler.registerBucket(TFBlocks.blockFluidCryotheum, 0, bucketCryotheum);
		BucketHandler.registerBucket(TFBlocks.blockFluidMana, 0, bucketMana);
		BucketHandler.registerBucket(TFBlocks.blockFluidCoal, 0, bucketCoal);

		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidRedstone, bucketRedstone, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidGlowstone, bucketGlowstone, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidEnder, bucketEnder, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidPyrotheum, bucketPyrotheum, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidCryotheum, bucketCryotheum, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidMana, bucketMana, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(TFFluids.fluidCoal, bucketCoal, FluidContainerRegistry.EMPTY_BUCKET);
	}

	public static void postInit() {

		/* Alloy Recipes */
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustElectrum, 2), new Object[] { "dustGold", "dustSilver" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustInvar, 3), new Object[] { "dustIron", "dustIron", "dustNickel" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustBronze, 4),
				new Object[] { "dustCopper", "dustCopper", "dustCopper", "dustTin" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustSignalum, 8), new Object[] { "dustCopper", "dustCopper", "dustCopper",
				"dustLead", bucketRedstone }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustLumium, 8), new Object[] { "dustTin", "dustTin", "dustTin", "dustSilver",
				bucketGlowstone }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustEnderium, 4), new Object[] { "dustTin", "dustTin", "dustTin", "dustPlatinum",
				bucketEnder }));

		/* Storage */
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

		ItemHelper.addReverseStorageRecipe(nuggetCopper, "ingotCopper");
		ItemHelper.addReverseStorageRecipe(nuggetTin, "ingotTin");
		ItemHelper.addReverseStorageRecipe(nuggetSilver, "ingotSilver");
		ItemHelper.addReverseStorageRecipe(nuggetLead, "ingotLead");
		ItemHelper.addReverseStorageRecipe(nuggetNickel, "ingotNickel");
		ItemHelper.addReverseStorageRecipe(nuggetPlatinum, "ingotPlatinum");
		ItemHelper.addReverseStorageRecipe(nuggetMithril, "ingotMithril");
		ItemHelper.addReverseStorageRecipe(nuggetElectrum, "ingotElectrum");
		ItemHelper.addReverseStorageRecipe(nuggetInvar, "ingotInvar");
		ItemHelper.addReverseStorageRecipe(nuggetBronze, "ingotBronze");
		ItemHelper.addReverseStorageRecipe(nuggetSignalum, "ingotSignalum");
		ItemHelper.addReverseStorageRecipe(nuggetLumium, "ingotLumium");
		ItemHelper.addReverseStorageRecipe(nuggetEnderium, "ingotEnderium");

		/* Gears */
		ItemHelper.addGearRecipe(gearCopper, "ingotCopper");
		ItemHelper.addGearRecipe(gearTin, "ingotTin");
		// ItemHelper.addGearRecipe(gearSilver, "ingotSilver");
		// ItemHelper.addGearRecipe(gearLead, "ingotLead");
		// ItemHelper.addGearRecipe(gearNickel, "ingotNickel");
		// ItemHelper.addGearRecipe(gearPlatinum, "ingotPlatinum");
		// ItemHelper.addGearRecipe(gearMithril, "ingotMithril");
		// ItemHelper.addGearRecipe(gearElectrum, "ingotElectrum");
		ItemHelper.addGearRecipe(gearInvar, "ingotInvar");
		ItemHelper.addGearRecipe(gearBronze, "ingotBronze");
		// ItemHelper.addGearRecipe(gearSignalum, "ingotSignalum");
		// ItemHelper.addGearRecipe(gearLumium, "ingotLumium");
		// ItemHelper.addGearRecipe(gearEnderium, "ingotEnderium");

	}

	public static ItemBucket itemBucket;
	public static ItemBase itemMaterial;

	public static ItemStack bucketRedstone;
	public static ItemStack bucketGlowstone;
	public static ItemStack bucketEnder;
	public static ItemStack bucketPyrotheum;
	public static ItemStack bucketCryotheum;
	public static ItemStack bucketMana;
	public static ItemStack bucketCoal;

	public static ItemStack ingotIron;
	public static ItemStack ingotGold;

	public static ItemStack dustIron;
	public static ItemStack dustGold;
	public static ItemStack dustCoal;
	public static ItemStack dustObsidian;

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
	public static ItemStack dustMana;

	public static ItemStack rodBlizz;
	public static ItemStack dustBlizz;

}
