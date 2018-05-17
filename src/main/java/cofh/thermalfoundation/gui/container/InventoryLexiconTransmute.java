package cofh.thermalfoundation.gui.container;

import cofh.core.util.helpers.InventoryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import java.util.Arrays;

public class InventoryLexiconTransmute implements IInventory {

	public static final byte INPUT = 0;

	protected ItemStack[] stackList;
	ContainerLexiconTransmute eventHandler;

	public InventoryLexiconTransmute(ContainerLexiconTransmute container) {

		this.eventHandler = container;
		stackList = new ItemStack[3];
		Arrays.fill(stackList, ItemStack.EMPTY);
	}

	/* IInventory */
	@Override
	public int getSizeInventory() {

		return stackList.length;
	}

	@Override
	public boolean isEmpty() {

		return InventoryHelper.isEmpty(stackList);
	}

	@Override
	public ItemStack getStackInSlot(int slot) {

		return stackList[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {

		if (!this.stackList[slot].isEmpty()) {
			ItemStack stack;

			if (this.stackList[slot].getCount() <= amount) {
				stack = this.stackList[slot];
				this.stackList[slot] = ItemStack.EMPTY;
				this.eventHandler.onCraftMatrixChanged(this);
				return stack;
			} else {
				stack = this.stackList[slot].splitStack(amount);

				if (this.stackList[slot].getCount() == 0) {
					this.stackList[slot] = ItemStack.EMPTY;
				}
				this.eventHandler.onCraftMatrixChanged(this);
				return stack;
			}
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int slot) {

		if (slot == 0 && !this.stackList[slot].isEmpty()) {
			ItemStack stack = this.stackList[slot];
			this.stackList[slot] = ItemStack.EMPTY;
			return stack;
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {

		this.stackList[slot] = stack;
		if (slot == INPUT) {
			this.eventHandler.onCraftMatrixChanged(this);
		}
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
	}

	@Override
	public void markDirty() {

		eventHandler.onCraftMatrixChanged(this);
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {

		return true;
	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {

		return true;
	}

	@Override
	public int getField(int id) {

		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {

		return 0;
	}

	@Override
	public void clear() {

	}

	/* IWorldNameable */
	@Override
	public String getName() {

		return null;
	}

	@Override
	public boolean hasCustomName() {

		return false;
	}

	@Override
	public ITextComponent getDisplayName() {

		return null;
	}

}
