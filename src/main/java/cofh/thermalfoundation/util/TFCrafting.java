package cofh.thermalfoundation.util;

import cofh.core.util.helpers.ItemHelper;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.init.TFProps;
import cofh.thermalfoundation.item.ItemMaterial;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

import static cofh.core.util.helpers.RecipeHelper.*;

public class TFCrafting {

	public static void loadRecipes() {

		/* STANDARD RECIPES */
		addSmelting(ItemMaterial.dustWoodCompressed, new ItemStack(Items.COAL, 1, 1), 0.15F);

		addStorageRecipe(ItemMaterial.dustWoodCompressed, "dustWood");

		addShapedRecipe(new ItemStack(Blocks.TORCH, 4), "X", "#", 'X', ItemMaterial.globRosin, '#', "string");
		addShapedRecipe(new ItemStack(Blocks.STICKY_PISTON, 1), "S", "P", 'S', ItemMaterial.globRosin, 'P', Blocks.PISTON);
		addShapedRecipe(new ItemStack(Items.LEAD, 2), "~~ ", "~O ", "  ~", '~', "string", 'O', ItemMaterial.globRosin);

		addShapedRecipe(new ItemStack(Blocks.TORCH, 4), "X", "#", 'X', ItemMaterial.globTar, '#', "string");
		addShapedRecipe(new ItemStack(Blocks.STICKY_PISTON, 1), "S", "P", 'S', ItemMaterial.globTar, 'P', Blocks.PISTON);
		addShapedRecipe(new ItemStack(Items.LEAD, 2), "~~ ", "~O ", "  ~", '~', "string", 'O', ItemMaterial.globTar);

		/* PYROTHEUM / PETROTHEUM CRAFTING */
		String[] oreNameList = OreDictionary.getOreNames();

		for (String oreName : oreNameList) {
			if (oreName.length() > 3 && oreName.startsWith("ore")) {

				String dustName = "dust" + StringHelper.titleCase(oreName.substring(3, oreName.length()));
				String ingotName = "ingot" + StringHelper.titleCase(oreName.substring(3, oreName.length()));
				String gemName = "gem" + StringHelper.titleCase(oreName.substring(3, oreName.length()));

				List<ItemStack> registeredOre = OreDictionary.getOres(oreName, false);
				List<ItemStack> registeredDust = OreDictionary.getOres(dustName, false);
				List<ItemStack> registeredIngot = OreDictionary.getOres(ingotName, false);
				List<ItemStack> registeredGem = OreDictionary.getOres(gemName, false);

				if (registeredOre.isEmpty()) {
					continue;
				}
				if (TFProps.enablePetrotheumCrafting && !registeredDust.isEmpty()) {
					addShapelessRecipe(ItemHelper.cloneStack(registeredDust.get(0), 2), oreName, "dustPetrotheum");

					if (!registeredIngot.isEmpty()) {
						addShapelessRecipe(ItemHelper.cloneStack(registeredDust.get(0), 1), ingotName, "dustPetrotheum");
					}
				}
				if (TFProps.enablePetrotheumCrafting && !registeredGem.isEmpty()) {
					addShapelessRecipe(ItemHelper.cloneStack(registeredGem.get(0), 1), oreName, "dustPetrotheum");
				}
				if (TFProps.enablePyrotheumCrafting && !registeredIngot.isEmpty()) {
					addShapelessRecipe(ItemHelper.cloneStack(registeredIngot.get(0), 1), oreName, "dustPyrotheum");

					if (!registeredDust.isEmpty()) {
						addShapelessRecipe(ItemHelper.cloneStack(registeredIngot.get(0), 1), dustName, "dustPyrotheum");
					}
				}
				if (TFProps.enablePetrotheumCrafting && TFProps.enablePyrotheumCrafting && !registeredDust.isEmpty() && !registeredIngot.isEmpty()) {
					addShapelessRecipe(ItemHelper.cloneStack(registeredIngot.get(0), 2), oreName, "dustPetrotheum", "dustPyrotheum");
				}
			}
		}
	}

}
