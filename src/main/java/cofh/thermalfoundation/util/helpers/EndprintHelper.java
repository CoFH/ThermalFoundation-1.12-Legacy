package cofh.thermalfoundation.util.helpers;

import cofh.core.util.helpers.StringHelper;
import net.minecraft.item.ItemStack;

import java.util.List;

public class EndprintHelper {

	private EndprintHelper() {

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

	public static boolean isEndprint(ItemStack stack) {

		return false;
		// return ItemHelper.itemsEqual(stack.getItem(), TFItems.itemDiagramEndprint);
	}

}
