package cofh.thermalfoundation.item.diagram;

import cofh.api.item.IToolDye;
import cofh.core.util.core.IInitializer;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
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

		diagramChromaprint = new ItemStack(this);

		OreDictionary.registerOre("dye", diagramChromaprint);

		ThermalFoundation.proxy.addIModelRegister(this);

		return true;
	}

	@Override
	public boolean register() {

		addShapelessRecipe(diagramChromaprint, "paper", "paper", "dyeRed", "dyeGreen", "dyeBlue");
		addShapelessRecipe(diagramChromaprint, "paper", "paper", "dyeCyan", "dyeYellow", "dyeMagenta", "dyeBlack");

		return true;
	}

	/* REFERENCES */
	public static ItemStack diagramChromaprint;

}
