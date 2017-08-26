package cofh.thermalfoundation.util;

import cofh.core.util.crafting.FluidIngredientFactory.FluidIngredient;
import cofh.core.util.helpers.ItemHelper;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.init.TFProps;
import cofh.thermalfoundation.item.ItemMaterial;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

import static cofh.core.util.helpers.RecipeHelper.*;

public class TFCrafting {

	public static void loadRecipes() {

		/* STANDARD RECIPES */
		addSmelting(ItemMaterial.dustWoodCompressed, new ItemStack(Items.COAL, 1, 1), 0.15F);

		addStorageRecipe(ItemMaterial.dustWoodCompressed, "dustWood");

		addShapelessOreRecipe(new ItemStack(Items.GUNPOWDER), "dustCoal", "dustSulfur", "dustSaltpeter", "dustSaltpeter");
		addShapelessOreRecipe(new ItemStack(Items.GUNPOWDER), "dustCharcoal", "dustSulfur", "dustSaltpeter", "dustSaltpeter");

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
					addShapelessRecipe(ItemHelper.cloneStack(registeredGem.get(0), 2), oreName, "dustPetrotheum");
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

		/* CRYOTHEUM CRAFTING */
		addShapelessFluidRecipe(new ItemStack(Blocks.ICE), new FluidIngredient(FluidRegistry.WATER.getName()), "dustCryotheum");
		addShapelessRecipe(new ItemStack(Blocks.PACKED_ICE), new ItemStack(Blocks.ICE), "dustCryotheum");
		addShapelessRecipe(new ItemStack(Items.REDSTONE, 2), ItemMaterial.crystalRedstone, "dustCryotheum");
		addShapelessRecipe(new ItemStack(Items.GLOWSTONE_DUST), ItemMaterial.crystalGlowstone, "dustCryotheum");
		addShapelessRecipe(new ItemStack(Items.ENDER_PEARL), ItemMaterial.crystalEnder, "dustCryotheum");

		addShapelessFluidRecipe(new ItemStack(Items.REDSTONE, 10), new FluidIngredient("redstone"), "dustCryotheum");
		addShapelessFluidRecipe(new ItemStack(Items.GLOWSTONE_DUST, 4), new FluidIngredient("glowstone"), "dustCryotheum");
		addShapelessFluidRecipe(new ItemStack(Items.ENDER_PEARL, 4), new FluidIngredient("ender"), "dustCryotheum");

		/* VANILLA RECIPES */
		loadVanillaRecipes();
	}

	/* VANILLA RECIPES */
	public static void loadVanillaRecipes() {

		// @formatter:off

		/* HORSE ARMOR */
		if (TFProps.enableHorseArmorCrafting) {
			addShapedRecipe(new ItemStack(Items.IRON_HORSE_ARMOR, 1),
					"  H",
					"ICI",
					"III",
					'C', "blockWool",
					'H', Items.IRON_HELMET,
					'I', "ingotIron"
			);

			addShapedRecipe(new ItemStack(Items.GOLDEN_HORSE_ARMOR),
					"  H",
					"ICI",
					"III",
					'C', "blockWool",
					'H', Items.GOLDEN_HELMET,
					'I', "ingotGold"
			);

			addShapedRecipe(new ItemStack(Items.DIAMOND_HORSE_ARMOR),
					"  H",
					"ICI",
					"III",
					'C', "blockWool",
					'H', Items.DIAMOND_HELMET,
					'I', "gemDiamond"
			);
		}
		if (TFProps.enableSaddleCrafting) {
			addShapedRecipe(new ItemStack(Items.SADDLE),
					"LLL",
					"LIL",
					"I I",
					'I', "ingotIron",
					'L', Items.LEATHER
			);
		}

		// @formatter:on
	}

}
