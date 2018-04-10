package cofh.thermalfoundation.util;

import cofh.core.init.CorePotions;
import cofh.core.util.crafting.FluidIngredientFactory.FluidIngredient;
import cofh.core.util.helpers.ItemHelper;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.init.TFProps;
import cofh.thermalfoundation.item.ItemMaterial;
import cofh.thermalfoundation.util.crafting.ShapelessPotionFillRecipeFactory.ShapelessPotionFillRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.GameData;

import java.util.List;

import static cofh.core.util.helpers.RecipeHelper.*;

public class TFCrafting {

	public static void loadRecipes() {

		/* STANDARD RECIPES */
		addSmelting(ItemMaterial.dustWoodCompressed, new ItemStack(Items.COAL, 1, 1), 0.15F);

		addStorageRecipe(ItemMaterial.dustWoodCompressed, "dustWood");

		addShapelessOreRecipe(ItemHelper.cloneStack(Items.GUNPOWDER), "dustCoal", "dustSulfur", "dustSaltpeter", "dustSaltpeter");
		addShapelessOreRecipe(ItemHelper.cloneStack(Items.GUNPOWDER), "dustCharcoal", "dustSulfur", "dustSaltpeter", "dustSaltpeter");

		addShapedRecipe(ItemHelper.cloneStack(Blocks.TORCH, 4), "X", "#", 'X', ItemMaterial.globRosin, '#', "string");
		addShapedRecipe(ItemHelper.cloneStack(Blocks.STICKY_PISTON), "S", "P", 'S', ItemMaterial.globRosin, 'P', Blocks.PISTON);
		addShapedRecipe(ItemHelper.cloneStack(Items.LEAD, 2), "~~ ", "~O ", "  ~", '~', "string", 'O', ItemMaterial.globRosin);

		addShapedRecipe(ItemHelper.cloneStack(Blocks.TORCH, 4), "X", "#", 'X', ItemMaterial.globTar, '#', "string");
		addShapedRecipe(ItemHelper.cloneStack(Blocks.STICKY_PISTON), "S", "P", 'S', ItemMaterial.globTar, 'P', Blocks.PISTON);
		addShapedRecipe(ItemHelper.cloneStack(Items.LEAD, 2), "~~ ", "~O ", "  ~", '~', "string", 'O', ItemMaterial.globTar);

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
		addShapelessFluidRecipe(ItemHelper.cloneStack(Blocks.ICE), new FluidIngredient(FluidRegistry.WATER.getName()), "dustCryotheum");
		addShapelessRecipe(ItemHelper.cloneStack(Blocks.PACKED_ICE), ItemHelper.cloneStack(Blocks.ICE), "dustCryotheum");
		addShapelessRecipe(ItemHelper.cloneStack(Items.REDSTONE, 2), ItemMaterial.crystalRedstone, "dustCryotheum");
		addShapelessRecipe(ItemHelper.cloneStack(Items.GLOWSTONE_DUST), ItemMaterial.crystalGlowstone, "dustCryotheum");
		addShapelessRecipe(ItemHelper.cloneStack(Items.ENDER_PEARL), ItemMaterial.crystalEnder, "dustCryotheum");

		addShapelessFluidRecipe(ItemHelper.cloneStack(Items.REDSTONE, 10), new FluidIngredient("redstone"), "dustCryotheum");
		addShapelessFluidRecipe(ItemHelper.cloneStack(Items.GLOWSTONE_DUST, 4), new FluidIngredient("glowstone"), "dustCryotheum");
		addShapelessFluidRecipe(ItemHelper.cloneStack(Items.ENDER_PEARL, 4), new FluidIngredient("ender"), "dustCryotheum");

		/* VANILLA RECIPES */
		loadVanillaRecipes();

		/* POTIONS */
		loadPotions();
	}

	public static void addPotionFillRecipe(ItemStack output, Object... input) {

		ResourceLocation location = getNameForRecipe(output);
		ShapelessPotionFillRecipe recipe = new ShapelessPotionFillRecipe(location, output, input);
		recipe.setRegistryName(location);
		GameData.register_impl(recipe);
	}

	/* VANILLA RECIPES */
	public static void loadVanillaRecipes() {

		// @formatter:off
		/* HORSE ARMOR */
		if (TFProps.enableHorseArmorCrafting) {
			addShapedRecipe(ItemHelper.cloneStack(Items.IRON_HORSE_ARMOR),
					"I I",
					"LCL",
					"I I",
					'C', "blockWool",
					'L', Items.LEATHER,
					'I', "ingotIron"
			);

			addShapedRecipe(ItemHelper.cloneStack(Items.GOLDEN_HORSE_ARMOR),
					"I I",
					"LCL",
					"I I",
					'C', "blockWool",
					'L', Items.LEATHER,
					'I', "ingotGold"
			);

			addShapedRecipe(ItemHelper.cloneStack(Items.DIAMOND_HORSE_ARMOR),
					"I I",
					"LCL",
					"I I",
					'C', "blockWool",
					'L', Items.LEATHER,
					'I', "gemDiamond"
			);
		}
		if (TFProps.enableSaddleCrafting) {
			addShapedRecipe(ItemHelper.cloneStack(Items.SADDLE),
					"LLL",
					"LIL",
					"I I",
					'I', "ingotIron",
					'L', Items.LEATHER
			);
		}
		// @formatter:on
	}

	public static void loadPotions() {

		PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromStacks(ItemMaterial.dustBasalz), CorePotions.haste);
		PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromStacks(ItemMaterial.dustObsidian), CorePotions.resistance);
		PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromStacks(ItemMaterial.dustBlitz), CorePotions.levitation);
		PotionHelper.addMix(PotionTypes.AWKWARD, Ingredient.fromStacks(ItemMaterial.dustBlizz), CorePotions.absorption);
		PotionHelper.addMix(PotionTypes.AWKWARD, Items.EMERALD, CorePotions.luck);
		PotionHelper.addMix(CorePotions.luck, Items.FERMENTED_SPIDER_EYE, CorePotions.unluck);
		PotionHelper.addMix(PotionTypes.REGENERATION, Items.FERMENTED_SPIDER_EYE, CorePotions.wither);

		PotionHelper.addMix(CorePotions.haste, Items.REDSTONE, CorePotions.hasteLong);
		PotionHelper.addMix(CorePotions.resistance, Items.REDSTONE, CorePotions.resistanceLong);
		PotionHelper.addMix(CorePotions.levitation, Items.REDSTONE, CorePotions.levitationLong);
		PotionHelper.addMix(CorePotions.absorption, Items.REDSTONE, CorePotions.absorptionLong);
		PotionHelper.addMix(CorePotions.luck, Items.REDSTONE, CorePotions.luckLong);
		PotionHelper.addMix(CorePotions.unluck, Items.REDSTONE, CorePotions.unluckLong);
		PotionHelper.addMix(CorePotions.wither, Items.REDSTONE, CorePotions.witherLong);

		PotionHelper.addMix(CorePotions.haste, Items.GLOWSTONE_DUST, CorePotions.hasteStrong);
		PotionHelper.addMix(CorePotions.resistance, Items.GLOWSTONE_DUST, CorePotions.resistanceStrong);
		PotionHelper.addMix(CorePotions.absorption, Items.GLOWSTONE_DUST, CorePotions.absorptionStrong);
		PotionHelper.addMix(CorePotions.luck, Items.GLOWSTONE_DUST, CorePotions.luckStrong);
		PotionHelper.addMix(CorePotions.unluck, Items.GLOWSTONE_DUST, CorePotions.unluckStrong);
		PotionHelper.addMix(CorePotions.wither, Items.GLOWSTONE_DUST, CorePotions.witherStrong);
	}

}
