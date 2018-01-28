package cofh.thermalfoundation.item;

import cofh.core.item.ItemMultiRF;
import net.minecraft.item.ItemStack;

public class ItemBattery extends ItemMultiRF {

	public ItemBattery(String modName) {

		super(modName);
	}

	@Override
	protected int getCapacity(ItemStack stack) {

		return 0;
	}

	@Override
	protected int getReceive(ItemStack stack) {

		return 0;
	}

	/* IMultiModeItem */
	@Override
	public int getNumModes(ItemStack stack) {

		return 1;
	}

}
