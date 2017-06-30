package cofh.thermalfoundation.util;

import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalfoundation.init.TFProps;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

import static cofh.lib.util.helpers.RecipeHelper.addShapelessRecipe;

public class TFCraftingManager {

	public static void loadRecipes() {

		String[] oreNameList = OreDictionary.getOreNames();

		for (String oreName : oreNameList) {
			if (oreName.length() > 3 && oreName.startsWith("ore")) {

				String dustName = "dust" + StringHelper.titleCase(oreName.substring(3, oreName.length()));
				String ingotName = "ingot" + StringHelper.titleCase(oreName.substring(3, oreName.length()));

				List<ItemStack> registeredOre = OreDictionary.getOres(oreName, false);
				List<ItemStack> registeredDust = OreDictionary.getOres(dustName, false);
				List<ItemStack> registeredIngot = OreDictionary.getOres(ingotName, false);

				if (registeredOre.isEmpty()) {
					continue;
				}
				if (TFProps.enablePetrotheumCrafting && !registeredDust.isEmpty()) {
					addShapelessRecipe(ItemHelper.cloneStack(registeredDust.get(0), 2), oreName, "dustPetrotheum");

					if (!registeredIngot.isEmpty()) {
						addShapelessRecipe(ItemHelper.cloneStack(registeredDust.get(0), 1), ingotName, "dustPetrotheum");
					}
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
