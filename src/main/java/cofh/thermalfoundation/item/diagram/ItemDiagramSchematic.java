package cofh.thermalfoundation.item.diagram;

import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static cofh.core.util.helpers.RecipeHelper.addShapelessRecipe;

public class ItemDiagramSchematic extends ItemDiagram {

	public ItemDiagramSchematic() {

		super();

		setUnlocalizedName("schematic");
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		// RedprintHelper.addInformation(stack, tooltip);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {

		String baseName = StringHelper.localize(getUnlocalizedName(stack) + ".name");
		// baseName += RedprintHelper.getDisplayName(stack);
		return baseName;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {

		return EnumRarity.COMMON;
		//return RedprintHelper.getDisplayName(stack).isEmpty() ? EnumRarity.COMMON : EnumRarity.UNCOMMON;
	}

	/* IInitializer */
	@Override
	public boolean initialize() {

		diagramSchematic = new ItemStack(this);

		ThermalFoundation.proxy.addIModelRegister(this);

		return true;
	}

	@Override
	public boolean register() {

		addShapelessRecipe(diagramSchematic, Items.PAPER, Items.PAPER, "dyeBlue");

		return true;
	}

	/* REFERENCES */
	public static ItemStack diagramSchematic;

}
