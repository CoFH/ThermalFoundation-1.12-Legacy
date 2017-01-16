package cofh.thermalfoundation.util.helpers;

import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalfoundation.item.ItemDiagram;
import net.minecraft.item.ItemStack;

import java.util.List;

public class PatternHelper {

	private PatternHelper() {

	}

	public static void addInformation(ItemStack stack, List<String> list) {

		if (!stack.hasTagCompound()) {
			list.add(StringHelper.getInfoText("info.cofh.blank"));
			return;
		}
		list.add(StringHelper.getDeactivationText("info.thermalfoundation.diagram.erase"));
	}

	public static String getDisplayName(ItemStack stack) {

		if (!stack.hasTagCompound()) {
			return "";
		}
		return ": " + "";
	}

	public static boolean isPattern(ItemStack stack) {

		return ItemHelper.itemsEqualWithMetadata(stack, ItemDiagram.pattern);
	}

	// TODO: this should be a 2x2 or 3x3 or something of that nature
}
