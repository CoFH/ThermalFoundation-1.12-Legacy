package cofh.thermalfoundation.init;

import cofh.core.util.core.IInitializer;
import cofh.thermalfoundation.item.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

import static cofh.core.util.helpers.RecipeHelper.*;

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

		addSmelting(ItemMaterial.dustWoodCompressed, new ItemStack(Items.COAL, 1, 1), 0.15F);

		addStorageRecipe(ItemMaterial.dustWoodCompressed, "dustWood");

		addShapedRecipe(new ItemStack(Blocks.TORCH, 4), "X", "#", 'X', ItemMaterial.globRosin, '#', "string");
		addShapedRecipe(new ItemStack(Blocks.STICKY_PISTON, 1), "S", "P", 'S', ItemMaterial.globRosin, 'P', Blocks.PISTON);
		addShapedRecipe(new ItemStack(Items.LEAD, 2), "~~ ", "~O ", "  ~", '~', "string", 'O', ItemMaterial.globRosin);

		addShapedRecipe(new ItemStack(Blocks.TORCH, 4), "X", "#", 'X', ItemMaterial.globTar, '#', "string");
		addShapedRecipe(new ItemStack(Blocks.STICKY_PISTON, 1), "S", "P", 'S', ItemMaterial.globTar, 'P', Blocks.PISTON);
		addShapedRecipe(new ItemStack(Items.LEAD, 2), "~~ ", "~O ", "  ~", '~', "string", 'O', ItemMaterial.globTar);
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
