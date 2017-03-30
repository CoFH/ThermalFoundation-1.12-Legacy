package cofh.thermalfoundation.init;

import cofh.core.util.core.IInitializer;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.item.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

import static cofh.lib.util.helpers.ItemHelper.*;

public class TFItems {

	private TFItems() {

	}

	public static void preInit() {

		itemWrench = new ItemWrench();
		itemMeter = new ItemMeter();
		itemTome = new ItemTome();
		itemSecurity = new ItemSecurity();
		itemDiagram = new ItemDiagram();
		itemCoin = new ItemCoin();
		itemFertilizer = new ItemFertilizer();
		itemMaterial = new ItemMaterial();
		itemGeode = new ItemGeode();

		initList.add(itemWrench);
		initList.add(itemMeter);
		initList.add(itemTome);
		initList.add(itemSecurity);
		initList.add(itemDiagram);
		initList.add(itemCoin);
		initList.add(itemFertilizer);
		initList.add(itemMaterial);
		initList.add(itemGeode);

		ThermalFoundation.proxy.addIModelRegister(itemWrench);
		ThermalFoundation.proxy.addIModelRegister(itemMeter);
		ThermalFoundation.proxy.addIModelRegister(itemTome);
		ThermalFoundation.proxy.addIModelRegister(itemSecurity);
		ThermalFoundation.proxy.addIModelRegister(itemDiagram);
		ThermalFoundation.proxy.addIModelRegister(itemCoin);
		ThermalFoundation.proxy.addIModelRegister(itemFertilizer);
		ThermalFoundation.proxy.addIModelRegister(itemMaterial);
		ThermalFoundation.proxy.addIModelRegister(itemGeode);

		for (IInitializer init : initList) {
			init.preInit();
		}
	}

	public static void initialize() {

		for (IInitializer init : initList) {
			init.initialize();
		}
	}

	public static void postInit() {

		for (IInitializer init : initList) {
			init.postInit();
		}
		initList.clear();

		addRecipes();
	}

	/* HELPERS */
	public static void addRecipes() {

		addSmelting(new ItemStack(Items.COAL, 1, 1), ItemMaterial.dustWoodCompressed, 0.15F);

		addStorageRecipe(ItemMaterial.dustWoodCompressed, "dustWood");

		addRecipe(ShapedRecipe(new ItemStack(Blocks.TORCH, 4), "X", "#", 'X', ItemMaterial.globRosin, '#', "string"));
		addRecipe(ShapedRecipe(new ItemStack(Blocks.STICKY_PISTON, 1), "S", "P", 'S', ItemMaterial.globRosin, 'P', Blocks.PISTON));
		addRecipe(ShapedRecipe(new ItemStack(Items.LEAD, 2), "~~ ", "~O ", "  ~", '~', "string", 'O', ItemMaterial.globRosin));

		addRecipe(ShapedRecipe(new ItemStack(Blocks.TORCH, 4), "X", "#", 'X', ItemMaterial.globTar, '#', "string"));
		addRecipe(ShapedRecipe(new ItemStack(Blocks.STICKY_PISTON, 1), "S", "P", 'S', ItemMaterial.globTar, 'P', Blocks.PISTON));
		addRecipe(ShapedRecipe(new ItemStack(Items.LEAD, 2), "~~ ", "~O ", "  ~", '~', "string", 'O', ItemMaterial.globTar));

		addGearRecipe(new ItemStack(Items.PAPER, 2), "dustWood", new ItemStack(Items.WATER_BUCKET));
		addRecipe(ShapelessRecipe(new ItemStack(Items.CLAY_BALL, 4), ItemMaterial.crystalSlag, ItemMaterial.crystalSlag, Blocks.DIRT, Items.WATER_BUCKET));

		addRecipe(ShapelessRecipe(new ItemStack(Blocks.MYCELIUM, 1), Blocks.DIRT, ItemFertilizer.fertilizerRich, Items.WATER_BUCKET, Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM));
		addRecipe(ShapelessRecipe(new ItemStack(Blocks.DIRT, 1, 2), new ItemStack(Blocks.DIRT, 1, 1), ItemFertilizer.fertilizerRich, Items.WATER_BUCKET, "treeLeaves", "treeLeaves"));
	}

	private static ArrayList<IInitializer> initList = new ArrayList<>();

	/* REFERENCES */
	public static ItemWrench itemWrench;
	public static ItemMeter itemMeter;
	public static ItemTome itemTome;
	public static ItemSecurity itemSecurity;
	public static ItemDiagram itemDiagram;
	public static ItemFertilizer itemFertilizer;
	public static ItemMaterial itemMaterial;
	public static ItemCoin itemCoin;
	public static ItemGeode itemGeode;

}
