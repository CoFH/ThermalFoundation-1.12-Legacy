package cofh.thermalfoundation.item.diagram;

import cofh.api.item.IToolDye;
import cofh.core.util.core.IInitializer;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.item.ItemDye;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.List;

import static cofh.core.util.helpers.RecipeHelper.addShapelessRecipe;

public class ItemDiagramChromaprint extends ItemDiagram implements IInitializer, IToolDye {

	public ItemDiagramChromaprint() {

		super();
		setMaxDamage(8);

		setUnlocalizedName("chromaprint");
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		// ChromaprintHelper.addInformation(stack, tooltip);
	}

	@Override
	public ItemStack getContainerItem(ItemStack stack) {

		ItemStack ret = stack.copy();
		ret.setItemDamage(stack.getItemDamage() + 1);

		if (ret.getItemDamage() > ret.getMaxDamage()) {
			ret = ItemStack.EMPTY;
		}
		return ret;
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {

		return true;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {

		String baseName = StringHelper.localize(getUnlocalizedName(stack) + ".name");
		// baseName += ChromaprintHelper.getDisplayName(stack);
		return baseName;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {

		return hasColor(stack) ? EnumRarity.UNCOMMON : EnumRarity.COMMON;
	}

	/* IToolDye */
	@Override
	public boolean hasColor(ItemStack item) {

		return true;
	}

	@Override
	public int getColor(ItemStack item) {

		return 0xFFFFFF;
	}

	/* IInitializer */
	@Override
	public boolean initialize() {

		ForgeRegistries.ITEMS.register(setRegistryName("diagram_chromaprint"));
		ThermalFoundation.proxy.addIModelRegister(this);

		diagramChromaprint = new ItemStack(this);

		OreDictionary.registerOre("dye", diagramChromaprint);

		return true;
	}

	@Override
	public boolean register() {

		addShapelessRecipe(diagramChromaprint, "paper", "paper", "dyeRed", "dyeGreen", "dyeBlue");
		addShapelessRecipe(diagramChromaprint, "paper", "paper", "dyeCyan", "dyeYellow", "dyeMagenta", "dyeBlack");

		addShapelessRecipe(ItemDye.dyeBlack, diagramChromaprint, "dyeBlack");
		addShapelessRecipe(ItemDye.dyeRed, diagramChromaprint, "dyeRed");
		addShapelessRecipe(ItemDye.dyeGreen, diagramChromaprint, "dyeGreen");
		addShapelessRecipe(ItemDye.dyeBrown, diagramChromaprint, "dyeBrown");
		addShapelessRecipe(ItemDye.dyeBlue, diagramChromaprint, "dyeBlue");
		addShapelessRecipe(ItemDye.dyePurple, diagramChromaprint, "dyePurple");
		addShapelessRecipe(ItemDye.dyeCyan, diagramChromaprint, "dyeCyan");
		addShapelessRecipe(ItemDye.dyeLightGray, diagramChromaprint, "dyeLightGray");
		addShapelessRecipe(ItemDye.dyeGray, diagramChromaprint, "dyeGray");
		addShapelessRecipe(ItemDye.dyePink, diagramChromaprint, "dyePink");
		addShapelessRecipe(ItemDye.dyeLime, diagramChromaprint, "dyeLime");
		addShapelessRecipe(ItemDye.dyeYellow, diagramChromaprint, "dyeYellow");
		addShapelessRecipe(ItemDye.dyeLightBlue, diagramChromaprint, "dyeLightBlue");
		addShapelessRecipe(ItemDye.dyeMagenta, diagramChromaprint, "dyeMagenta");
		addShapelessRecipe(ItemDye.dyeOrange, diagramChromaprint, "dyeOrange");
		addShapelessRecipe(ItemDye.dyeWhite, diagramChromaprint, "dyeWhite");

		return true;
	}

	/* REFERENCES */
	public static ItemStack diagramChromaprint;

}
