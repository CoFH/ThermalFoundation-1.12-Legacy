package cofh.thermalfoundation.item.diagram;

import cofh.core.util.core.IInitializer;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static cofh.core.util.helpers.RecipeHelper.addShapelessRecipe;

public class ItemDiagramChromaprint extends ItemDiagram implements IInitializer {

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
	public String getItemStackDisplayName(ItemStack stack) {

		String baseName = StringHelper.localize(getUnlocalizedName(stack) + ".name");
		// baseName += ChromaprintHelper.getDisplayName(stack);
		return baseName;
	}

	//	@Override
	//	public EnumRarity getRarity(ItemStack stack) {
	//
	//		return ChromaprintHelper.getDisplayName(stack).isEmpty() ? EnumRarity.COMMON : EnumRarity.UNCOMMON;
	//	}

	/* IInitializer */
	@Override
	public boolean initialize() {

		diagramChromaprint = new ItemStack(this);

		ThermalFoundation.proxy.addIModelRegister(this);

		return true;
	}

	@Override
	public boolean register() {

		addShapelessRecipe(diagramChromaprint, Items.PAPER, Items.PAPER, "dyeRed", "dyeGreen", "dyeBlue");
		addShapelessRecipe(diagramChromaprint, Items.PAPER, Items.PAPER, "dyeCyan", "dyeYellow", "dyeMagenta", "dyeBlack");

		return true;
	}

	/* REFERENCES */
	public static ItemStack diagramChromaprint;

}
