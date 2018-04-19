package cofh.thermalfoundation.item.diagram;

import cofh.core.util.core.IInitializer;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDiagramChromaprint extends ItemDiagram implements IInitializer {

	public ItemDiagramChromaprint() {

		super();

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

		// addShapelessRecipe(diagramChromaprint, Items.PAPER, Items.PAPER, "dustRedstone");

		return true;
	}

	/* REFERENCES */
	public static ItemStack diagramChromaprint;

}
