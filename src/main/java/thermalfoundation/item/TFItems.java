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
import net.minecraftforge.oredict.OreDictionary;
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

		/* Vanilla derived */
		dustIron = itemMaterial.addItem(0, "dustIron");
		dustGold = itemMaterial.addItem(1, "dustGold");
		dustCoal = itemMaterial.addItem(2, "dustCoal");
		dustObsidian = itemMaterial.addItem(4, "dustObsidian");

		dustSulfur = itemMaterial.addItem(16, "dustSulfur");
		dustNiter = itemMaterial.addItem(17, "dustNiter");

		/* Dusts */
		dustCopper = itemMaterial.addItem(32, "dustCopper");
		dustTin = itemMaterial.addItem(33, "dustTin");
		dustSilver = itemMaterial.addItem(34, "dustSilver");
		dustLead = itemMaterial.addItem(35, "dustLead");
		dustNickel = itemMaterial.addItem(36, "dustNickel");
		dustPlatinum = itemMaterial.addItem(37, "dustPlatinum", 1);
		dustMithril = itemMaterial.addItem(38, "dustMithril", 2);
		dustElectrum = itemMaterial.addItem(39, "dustElectrum");
		dustInvar = itemMaterial.addItem(40, "dustInvar");
		dustBronze = itemMaterial.addItem(41, "dustBronze");
		dustSignalum = itemMaterial.addItem(42, "dustSignalum", 1);
		dustLumium = itemMaterial.addItem(43, "dustLumium", 1);
		dustEnderium = itemMaterial.addItem(44, "dustEnderium", 2);

		/* Ingots */
		ingotCopper = itemMaterial.addItem(64, "ingotCopper");
		ingotTin = itemMaterial.addItem(65, "ingotTin");
		ingotSilver = itemMaterial.addItem(66, "ingotSilver");
		ingotLead = itemMaterial.addItem(67, "ingotLead");
		ingotNickel = itemMaterial.addItem(68, "ingotNickel");
		ingotPlatinum = itemMaterial.addItem(69, "ingotPlatinum", 1);
		ingotMithril = itemMaterial.addItem(70, "ingotMithril", 2);
		ingotElectrum = itemMaterial.addItem(71, "ingotElectrum");
		ingotInvar = itemMaterial.addItem(72, "ingotInvar");
		ingotBronze = itemMaterial.addItem(73, "ingotBronze");
		ingotSignalum = itemMaterial.addItem(74, "ingotSignalum", 1);
		ingotLumium = itemMaterial.addItem(75, "ingotLumium", 1);
		ingotEnderium = itemMaterial.addItem(76, "ingotEnderium", 2);

		/* Nuggets */
		nuggetCopper = itemMaterial.addItem(96, "nuggetCopper");
		nuggetTin = itemMaterial.addItem(97, "nuggetTin");
		nuggetSilver = itemMaterial.addItem(98, "nuggetSilver");
		nuggetLead = itemMaterial.addItem(99, "nuggetLead");
		nuggetNickel = itemMaterial.addItem(100, "nuggetNickel");
		nuggetPlatinum = itemMaterial.addItem(101, "nuggetPlatinum");
		nuggetMithril = itemMaterial.addItem(102, "nuggetMithril");
		nuggetElectrum = itemMaterial.addItem(103, "nuggetElectrum");
		nuggetInvar = itemMaterial.addItem(104, "nuggetInvar");
		nuggetBronze = itemMaterial.addItem(105, "nuggetBronze");
		nuggetSignalum = itemMaterial.addItem(106, "nuggetSignalum", 1);
		nuggetLumium = itemMaterial.addItem(107, "nuggetLumium", 1);
		nuggetEnderium = itemMaterial.addItem(108, "nuggetEnderium", 2);

		/* Gears */
		gearCopper = itemMaterial.addItem(128, "gearCopper");
		gearTin = itemMaterial.addItem(129, "gearTin");
		// gearSilver = itemMaterial.addItem(130, "gearSilver");
		// gearLead = itemMaterial.addItem(131, "gearLead");
		// gearNickel = itemMaterial.addItem(132, "gearNickel");
		// gearPlatinum = itemMaterial.addItem(133, "gearPlatinum");
		// gearMithril = itemMaterial.addItem(134, "gearMithril");
		gearElectrum = itemMaterial.addItem(135, "gearElectrum");
		gearInvar = itemMaterial.addItem(136, "gearInvar");
		gearBronze = itemMaterial.addItem(137, "gearBronze");
		// gearSignalum = itemMaterial.addItem(138, "gearSignalum");
		// gearLumium = itemMaterial.addItem(139, "gearLumium");
		// gearEnderium = itemMaterial.addItem(140, "gearEnderium");

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

		/* Additional Items */
		dustPyrotheum = itemMaterial.addItem(512, "dustPyrotheum", 2);
		dustCryotheum = itemMaterial.addItem(513, "dustCryotheum", 2);
		dustMana = itemMaterial.addItem(514, "dustMana", 3);

		/* Mob Drops */
		rodBlizz = itemMaterial.addItem(1024, "rodBlizz");
		dustBlizz = itemMaterial.addItem(1025, "dustBlizz");

		OreDictionary.registerOre("dustSulfur", dustSulfur);
		OreDictionary.registerOre("dustSaltpeter", dustNiter);

		OreDictionary.registerOre("dustIron", dustIron);
		OreDictionary.registerOre("dustGold", dustGold);
		OreDictionary.registerOre("dustCoal", dustCoal);
		OreDictionary.registerOre("dustObsidian", dustObsidian);

		OreDictionary.registerOre("dustCopper", dustCopper);
		OreDictionary.registerOre("dustTin", dustTin);
		OreDictionary.registerOre("dustSilver", dustSilver);
		OreDictionary.registerOre("dustLead", dustLead);
		OreDictionary.registerOre("dustNickel", dustNickel);
		OreDictionary.registerOre("dustPlatinum", dustPlatinum);
		OreDictionary.registerOre("dustMithril", dustMithril);
		OreDictionary.registerOre("dustElectrum", dustElectrum);
		OreDictionary.registerOre("dustInvar", dustInvar);
		OreDictionary.registerOre("dustBronze", dustBronze);
		OreDictionary.registerOre("dustSignalum", dustSignalum);
		OreDictionary.registerOre("dustLumium", dustLumium);
		OreDictionary.registerOre("dustEnderium", dustEnderium);

		OreDictionary.registerOre("ingotCopper", ingotCopper);
		OreDictionary.registerOre("ingotTin", ingotTin);
		OreDictionary.registerOre("ingotSilver", ingotSilver);
		OreDictionary.registerOre("ingotLead", ingotLead);
		OreDictionary.registerOre("ingotNickel", ingotNickel);
		OreDictionary.registerOre("ingotPlatinum", ingotPlatinum);
		OreDictionary.registerOre("ingotMithril", ingotMithril);
		OreDictionary.registerOre("ingotElectrum", ingotElectrum);
		OreDictionary.registerOre("ingotInvar", ingotInvar);
		OreDictionary.registerOre("ingotBronze", ingotBronze);
		OreDictionary.registerOre("ingotSignalum", ingotSignalum);
		OreDictionary.registerOre("ingotLumium", ingotLumium);
		OreDictionary.registerOre("ingotEnderium", ingotEnderium);

		OreDictionary.registerOre("nuggetCopper", nuggetCopper);
		OreDictionary.registerOre("nuggetTin", nuggetTin);
		OreDictionary.registerOre("nuggetSilver", nuggetSilver);
		OreDictionary.registerOre("nuggetLead", nuggetLead);
		OreDictionary.registerOre("nuggetNickel", nuggetNickel);
		OreDictionary.registerOre("nuggetPlatinum", nuggetPlatinum);
		OreDictionary.registerOre("nuggetMithril", nuggetMithril);
		OreDictionary.registerOre("nuggetElectrum", nuggetElectrum);
		OreDictionary.registerOre("nuggetInvar", nuggetInvar);
		OreDictionary.registerOre("nuggetBronze", nuggetBronze);
		OreDictionary.registerOre("nuggetSignalum", nuggetSignalum);
		OreDictionary.registerOre("nuggetLumium", nuggetLumium);
		OreDictionary.registerOre("nuggetEnderium", nuggetEnderium);

		OreDictionary.registerOre("gearCopper", gearCopper);
		OreDictionary.registerOre("gearTin", gearTin);
		// OreDictionary.registerOre("gearSilver", gearSilver);
		// OreDictionary.registerOre("gearLead", gearLead);
		// OreDictionary.registerOre("gearNickel", gearNickel);
		// OreDictionary.registerOre("gearPlatinum", gearPlatinum);
		// OreDictionary.registerOre("gearMithril", gearMithril);
		OreDictionary.registerOre("gearElectrum", gearElectrum);
		OreDictionary.registerOre("gearInvar", gearInvar);
		OreDictionary.registerOre("gearBronze", gearBronze);
		// OreDictionary.registerOre("gearSignalum", gearSignalum);
		// OreDictionary.registerOre("gearLumium", gearLumium);
		// OreDictionary.registerOre("gearEnderium", gearEnderium);
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
