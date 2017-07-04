package cofh.thermalfoundation.util.helpers;

import cofh.core.util.helpers.ItemHelper;
import cofh.core.util.helpers.RedstoneControlHelper;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.item.ItemDiagram;
import net.minecraft.item.ItemStack;

import java.util.List;

public class RedprintHelper {

	private RedprintHelper() {

	}

	public static void addInformation(ItemStack stack, List<String> tooltip) {

		if (!stack.hasTagCompound()) {
			tooltip.add(StringHelper.getActivationText("info.thermalfoundation.diagram.redprint.0"));
			tooltip.add(StringHelper.getInfoText("info.cofh.blank"));
			return;
		}
		if (StringHelper.displayShiftForDetail && !StringHelper.isShiftKeyDown()) {
			tooltip.add(StringHelper.shiftForDetails());
		}
		if (!StringHelper.isShiftKeyDown()) {
			return;
		}
		tooltip.add(StringHelper.getDeactivationText("info.thermalfoundation.diagram.erase"));
		tooltip.add(StringHelper.getActivationText("info.thermalfoundation.diagram.redprint.1"));
		RedstoneControlHelper.addRSControlInformation(stack, tooltip);
	}

	public static String getDisplayName(ItemStack stack) {

		if (!stack.hasTagCompound()) {
			return "";
		}
		if (stack.getTagCompound().hasKey("DisplayType")) {
			return ": " + StringHelper.localize(stack.getTagCompound().getString("Type")) + " (" + StringHelper.localize(stack.getTagCompound().getString("DisplayType")) + ")";
		}
		return ": " + StringHelper.localize(stack.getTagCompound().getString("Type"));
	}

	public static boolean isRedprint(ItemStack stack) {

		return ItemHelper.itemsEqualWithMetadata(stack, ItemDiagram.redprint);
	}

}
