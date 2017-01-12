package cofh.thermalfoundation.util;

import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.RedstoneControlHelper;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalfoundation.item.ItemDiagram;

import java.util.List;

import net.minecraft.item.ItemStack;

public class RedprintHelper {

	private RedprintHelper() {

	}

	public static void addInformation(ItemStack stack, List<String> list) {

		if (!stack.hasTagCompound()) {
			list.add(StringHelper.getActivationText("info.thermalfoundation.diagram.redprint.0"));
			list.add(StringHelper.getInfoText("info.cofh.blank"));
			return;
		}
		if (StringHelper.displayShiftForDetail && !StringHelper.isShiftKeyDown()) {
			list.add(StringHelper.shiftForDetails());
		}
		if (!StringHelper.isShiftKeyDown()) {
			return;
		}
		list.add(StringHelper.getDeactivationText("info.thermalfoundation.diagram.erase"));
		list.add(StringHelper.getActivationText("info.thermalfoundation.diagram.redprint.1"));
		RedstoneControlHelper.addRSControlInformation(stack, list);
	}

	public static String getDisplayName(ItemStack stack) {

		if (!stack.hasTagCompound()) {
			return "";
		}
		if (stack.getTagCompound().hasKey("DisplayType")) {
			return ": " + StringHelper.localize(stack.getTagCompound().getString("Type")) + " ("
					+ StringHelper.localize(stack.getTagCompound().getString("DisplayType")) + ")";
		}
		return ": " + StringHelper.localize(stack.getTagCompound().getString("Type"));
	}

	public static boolean isRedprint(ItemStack stack) {

		return ItemHelper.itemsEqualWithMetadata(stack, ItemDiagram.redprint);
	}

}
