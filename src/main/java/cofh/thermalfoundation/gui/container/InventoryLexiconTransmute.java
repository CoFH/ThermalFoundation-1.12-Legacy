package cofh.thermalfoundation.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public class InventoryLexiconTransmute implements IInventory {

	public static final byte INPUT = 0;

	protected ItemStack[] stackList = new ItemStack[3];
	ContainerLexiconTransmute eventHandler;

	public InventoryLexiconTransmute(ContainerLexiconTransmute container) {

		this.eventHandler = container;
	}

	@Override
	public int getSizeInventory() {

		return stackList.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {

		return stackList[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {

		if (this.stackList[slot] != null) {
			ItemStack stack;

			if (this.stackList[slot].stackSize <= amount) {
				stack = this.stackList[slot];
				this.stackList[slot] = null;
				this.eventHandler.onCraftMatrixChanged(this);
				return stack;
			} else {
				stack = this.stackList[slot].splitStack(amount);

				if (this.stackList[slot].stackSize == 0) {
					this.stackList[slot] = null;
				}
				this.eventHandler.onCraftMatrixChanged(this);
				return stack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int slot) {

		if (slot == 0 && this.stackList[slot] != null) {
			ItemStack stack = this.stackList[slot];
			this.stackList[slot] = null;
			return stack;
		} else {
			return null;
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
	public String getName() {

		return null;
	}

	@Override
	public boolean hasCustomName() {

		return true;
	}

	@Override
	public ITextComponent getDisplayName() {
		return null;
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
	public boolean isUseableByPlayer(EntityPlayer player) {

		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

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

}
