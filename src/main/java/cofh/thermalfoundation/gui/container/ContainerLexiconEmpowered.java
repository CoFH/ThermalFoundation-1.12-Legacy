package cofh.thermalfoundation.gui.container;

import cofh.lib.gui.container.ContainerInventoryItem;
import cofh.lib.gui.slot.ISlotValidator;
import cofh.lib.gui.slot.SlotRemoveOnly;
import cofh.lib.gui.slot.SlotValidated;
import cofh.lib.gui.slot.SlotViewOnly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLexiconEmpowered extends ContainerInventoryItem implements ISlotValidator {

	public ContainerLexiconEmpowered(ItemStack stack, InventoryPlayer inventory) {

		super(stack, inventory);

		addPlayerSlotsToContainer(inventory, 23, 114);

		addSlotToContainer(new SlotValidated(this, containerWrapper, 0, 59, 61));
		addSlotToContainer(new SlotRemoveOnly(containerWrapper, 1, 131, 61));
		addSlotToContainer(new SlotViewOnly(containerWrapper, 2, 95, 33));
	}

	private void addPlayerSlotsToContainer(InventoryPlayer inventory, int xOffset, int yOffset) {

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, xOffset + j * 18, yOffset + i * 18));
			}
		}
		for (int i = 0; i < 9; i++) {
			if (i == inventory.currentItem) {
				addSlotToContainer(new SlotViewOnly(inventory, i, xOffset + i * 18, yOffset + 58));
			} else {
				addSlotToContainer(new Slot(inventory, i, xOffset + i * 18, yOffset + 58));
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {

		return true;
	}

	/* ISlotValidator */
	@Override
	public boolean isItemValid(ItemStack stack) {

		return containerWrapper.isItemValidForSlot(0, stack);
	}

}
