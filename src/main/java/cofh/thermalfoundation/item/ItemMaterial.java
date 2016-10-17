package cofh.thermalfoundation.item;

import static cofh.lib.util.helpers.ItemHelper.*;

import cofh.api.core.IInitializer;
import cofh.api.core.IModelRegister;
import cofh.core.item.ItemCoFHBase;
import cofh.core.util.StateMapper;
import cofh.core.util.energy.FurnaceFuelHandler;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.core.TFProps;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Map;

public class ItemMaterial extends ItemCoFHBase implements IInitializer, IModelRegister {

	public ItemMaterial() {

		super("thermalfoundation");

		setUnlocalizedName("material");
		setCreativeTab(ThermalFoundation.tabCommon);
	}

	/* IModelRegister */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {

		for (Map.Entry<Integer, ItemEntry> entry : itemMap.entrySet()) {
			ModelLoader.setCustomModelResourceLocation(this, entry.getKey(), new ModelResourceLocation(modName + ":" + "material", entry.getValue().name));
		}
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		GameRegistry.register(this.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "material")));

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
		dustNickel = addOreDictItem(36, "dustNickel");
		dustPlatinum = addOreDictItem(37, "dustPlatinum", EnumRarity.UNCOMMON);
		dustMithril = addOreDictItem(38, "dustMithril", EnumRarity.RARE);
		dustElectrum = addOreDictItem(39, "dustElectrum");
		dustInvar = addOreDictItem(40, "dustInvar");
		dustBronze = addOreDictItem(41, "dustBronze");
		dustSignalum = addOreDictItem(42, "dustSignalum", EnumRarity.UNCOMMON);
		dustLumium = addOreDictItem(43, "dustLumium", EnumRarity.UNCOMMON);
		dustEnderium = addOreDictItem(44, "dustEnderium", EnumRarity.RARE);

		/* Ingots */
		ingotCopper = addOreDictItem(64, "ingotCopper");
		ingotTin = addOreDictItem(65, "ingotTin");
		ingotSilver = addOreDictItem(66, "ingotSilver");
		ingotLead = addOreDictItem(67, "ingotLead");
		ingotNickel = addOreDictItem(68, "ingotNickel");
		ingotPlatinum = addOreDictItem(69, "ingotPlatinum", EnumRarity.UNCOMMON);
		ingotMithril = addOreDictItem(70, "ingotMithril", EnumRarity.RARE);
		ingotElectrum = addOreDictItem(71, "ingotElectrum");
		ingotInvar = addOreDictItem(72, "ingotInvar");
		ingotBronze = addOreDictItem(73, "ingotBronze");
		ingotSignalum = addOreDictItem(74, "ingotSignalum", EnumRarity.UNCOMMON);
		ingotLumium = addOreDictItem(75, "ingotLumium", EnumRarity.UNCOMMON);
		ingotEnderium = addOreDictItem(76, "ingotEnderium", EnumRarity.RARE);

		/* Nuggets */
		nuggetIron = addOreDictItem(8, "nuggetIron");
		nuggetCopper = addOreDictItem(96, "nuggetCopper");
		nuggetTin = addOreDictItem(97, "nuggetTin");
		nuggetSilver = addOreDictItem(98, "nuggetSilver");
		nuggetLead = addOreDictItem(99, "nuggetLead");
		nuggetNickel = addOreDictItem(100, "nuggetNickel");
		nuggetPlatinum = addOreDictItem(101, "nuggetPlatinum", EnumRarity.UNCOMMON);
		nuggetMithril = addOreDictItem(102, "nuggetMithril", EnumRarity.RARE);
		nuggetElectrum = addOreDictItem(103, "nuggetElectrum");
		nuggetInvar = addOreDictItem(104, "nuggetInvar");
		nuggetBronze = addOreDictItem(105, "nuggetBronze");
		nuggetSignalum = addOreDictItem(106, "nuggetSignalum", EnumRarity.UNCOMMON);
		nuggetLumium = addOreDictItem(107, "nuggetLumium", EnumRarity.UNCOMMON);
		nuggetEnderium = addOreDictItem(108, "nuggetEnderium", EnumRarity.RARE);

		/* Gears */
		gearIron = addOreDictItem(12, "gearIron");
		gearGold = addOreDictItem(13, "gearGold");
		gearCopper = addOreDictItem(128, "gearCopper");
		gearTin = addOreDictItem(129, "gearTin");
		gearSilver = addOreDictItem(130, "gearSilver");
		gearLead = addOreDictItem(131, "gearLead");
		gearNickel = addOreDictItem(132, "gearNickel");
		gearPlatinum = addOreDictItem(133, "gearPlatinum", EnumRarity.UNCOMMON);
		gearMithril = addOreDictItem(134, "gearMithril", EnumRarity.RARE);
		gearElectrum = addOreDictItem(135, "gearElectrum");
		gearInvar = addOreDictItem(136, "gearInvar");
		gearBronze = addOreDictItem(137, "gearBronze");
		gearSignalum = addOreDictItem(138, "gearSignalum", EnumRarity.UNCOMMON);
		gearLumium = addOreDictItem(139, "gearLumium", EnumRarity.UNCOMMON);
		gearEnderium = addOreDictItem(140, "gearEnderium", EnumRarity.RARE);

		/* Parts */
		powerCoilGold = addItem(257, "powerCoilGold");
		powerCoilSilver = addItem(258, "powerCoilSilver");
		powerCoilElectrum = addItem(259, "powerCoilElectrum");

		/* Additional Items */
		dustPyrotheum = addOreDictItem(512, "dustPyrotheum", EnumRarity.RARE);
		dustCryotheum = addOreDictItem(513, "dustCryotheum", EnumRarity.RARE);
		dustAerotheum = addOreDictItem(514, "dustAerotheum", EnumRarity.RARE);
		dustPetrotheum = addOreDictItem(515, "dustPetrotheum", EnumRarity.RARE);
		dustMana = addItem(516, "dustMana", EnumRarity.EPIC);

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

		ingotIron = new ItemStack(Items.IRON_INGOT);
		ingotGold = new ItemStack(Items.GOLD_INGOT);
		nuggetGold = new ItemStack(Items.GOLD_NUGGET);

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
		addSmelting(ingotNickel, dustNickel, 0.0F);
		addSmelting(ingotPlatinum, dustPlatinum, 0.0F);
		addSmelting(ingotMithril, dustMithril, 0.0F);
		addSmelting(ingotElectrum, dustElectrum, 0.0F);
		addSmelting(ingotInvar, dustInvar, 0.0F);
		addSmelting(ingotBronze, dustBronze, 0.0F);
		addSmelting(ingotSignalum, dustSignalum, 0.0F);
		addSmelting(ingotLumium, dustLumium, 0.0F);
		// No Enderium

		/* Alloy Recipes */
		addRecipe(ShapelessRecipe(cloneStack(dustElectrum, 2), new Object[] { "dustGold", "dustSilver" }));
		addRecipe(ShapelessRecipe(cloneStack(dustInvar, 3), new Object[] { "dustIron", "dustIron", "dustNickel" }));
		addRecipe(ShapelessRecipe(cloneStack(dustBronze, 4), new Object[] { "dustCopper", "dustCopper", "dustCopper", "dustTin" }));
		addRecipe(ShapelessRecipe(cloneStack(dustSignalum, 4), new Object[] { "dustCopper", "dustCopper", "dustCopper", "dustSilver", "bucketRedstone" }));
		addRecipe(ShapelessRecipe(cloneStack(dustLumium, 4), new Object[] { "dustTin", "dustTin", "dustTin", "dustSilver", "bucketGlowstone" }));
		addRecipe(ShapelessRecipe(cloneStack(dustEnderium, 4), new Object[] { "dustTin", "dustTin", "dustSilver", "dustPlatinum", "bucketEnder" }));

		/* Storage */
		addTwoWayStorageRecipe(ingotIron, "ingotIron", nuggetIron, "nuggetIron");
		addTwoWayStorageRecipe(ingotCopper, "ingotCopper", nuggetCopper, "nuggetCopper");
		addTwoWayStorageRecipe(ingotTin, "ingotTin", nuggetTin, "nuggetTin");
		addTwoWayStorageRecipe(ingotSilver, "ingotSilver", nuggetSilver, "nuggetSilver");
		addTwoWayStorageRecipe(ingotLead, "ingotLead", nuggetLead, "nuggetLead");
		addTwoWayStorageRecipe(ingotNickel, "ingotNickel", nuggetNickel, "nuggetNickel");
		addTwoWayStorageRecipe(ingotPlatinum, "ingotPlatinum", nuggetPlatinum, "nuggetPlatinum");
		addTwoWayStorageRecipe(ingotMithril, "ingotMithril", nuggetMithril, "nuggetMithril");
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
		addReverseStorageRecipe(ingotNickel, "blockNickel");
		addReverseStorageRecipe(ingotPlatinum, "blockPlatinum");
		addReverseStorageRecipe(ingotMithril, "blockMithril");
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
		addGearRecipe(gearNickel, "ingotNickel");
		addGearRecipe(gearPlatinum, "ingotPlatinum");
		addGearRecipe(gearMithril, "ingotMithril");
		addGearRecipe(gearElectrum, "ingotElectrum");
		addGearRecipe(gearInvar, "ingotInvar");
		addGearRecipe(gearBronze, "ingotBronze");
		addGearRecipe(gearSignalum, "ingotSignalum");
		addGearRecipe(gearLumium, "ingotLumium");
		addGearRecipe(gearEnderium, "ingotEnderium");

		/* Parts */
		addRecipe(ShapedRecipe(powerCoilGold, new Object[] { "  R", " G ", "R  ", 'R', "dustRedstone", 'G', "ingotGold" }));
		addRecipe(ShapedRecipe(powerCoilSilver, new Object[] { "  R", " G ", "R  ", 'R', "dustRedstone", 'G', "ingotSilver" }));
		addRecipe(ShapedRecipe(powerCoilElectrum, new Object[] { "R  ", " G ", "  R", 'R', "dustRedstone", 'G', "ingotElectrum" }));

		/* Mob Drops */
		addRecipe(ShapelessRecipe(cloneStack(dustPyrotheum, 2), new Object[] { "dustCoal", "dustSulfur", "dustRedstone", Items.BLAZE_POWDER }));
		addRecipe(ShapelessRecipe(cloneStack(dustCryotheum, 2), new Object[] { Items.SNOWBALL, "dustSaltpeter", "dustRedstone", "dustBlizz" }));
		addRecipe(ShapelessRecipe(cloneStack(dustAerotheum, 2), new Object[] { "sand", "dustSaltpeter", "dustRedstone", "dustBlitz" }));
		addRecipe(ShapelessRecipe(cloneStack(dustPetrotheum, 2), new Object[] { Items.CLAY_BALL, "dustObsidian", "dustRedstone", "dustBasalz" }));
		addRecipe(ShapelessRecipe(cloneStack(dustBlizz, 2), "rodBlizz"));
		addRecipe(ShapelessRecipe(cloneStack(dustBlitz, 2), "rodBlitz"));
		addRecipe(ShapelessRecipe(cloneStack(dustBasalz, 2), "rodBasalz"));

		/* Misc Recipes */
		addSmelting(dustWoodCompressed, new ItemStack(Items.COAL, 1, 1), 0.15F);

		addRecipe(ShapedRecipe(dustWoodCompressed, new Object[] { "###", "# #", "###", '#', "dustWood" }));
		addRecipe(ShapelessRecipe(new ItemStack(Items.CLAY_BALL, 4), new Object[] { crystalSlag, crystalSlag, Blocks.DIRT, Items.WATER_BUCKET }));

		return true;
	}

	/* REFERENCES */
	public static ItemStack ingotIron;
	public static ItemStack ingotGold;
	public static ItemStack nuggetGold;

	public static ItemStack dustIron;
	public static ItemStack dustGold;
	public static ItemStack dustCoal;
	public static ItemStack dustCharcoal;
	public static ItemStack dustObsidian;
	public static ItemStack dustWood;
	public static ItemStack dustWoodCompressed;

	public static ItemStack nuggetIron;
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
