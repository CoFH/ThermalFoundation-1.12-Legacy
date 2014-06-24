package thermalfoundation.gui.container;

import cofh.gui.container.ContainerInventoryItem;
import cofh.gui.slot.ISlotValidator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class ContainerLexicon extends ContainerInventoryItem implements ISlotValidator {

	public ContainerLexicon(ItemStack stack, InventoryPlayer inventory) {

		super(stack, inventory);

		// TODO: Add Lexicon Slots
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {

		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {

		return null;
	}

	/* ISlotValidator */
	@Override
	public boolean isItemValid(ItemStack stack) {

		return containerWrapper.isItemValidForSlot(0, stack);
	}

}
