package cofh.thermalfoundation.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryLexiconStudy implements IInventory {

	protected ItemStack[] stackList = new ItemStack[1];

	public InventoryLexiconStudy() {

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

		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {

		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {

		this.stackList[slot] = stack;
	}

	@Override
	public String getInventoryName() {

		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {

		return true;
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
	}

	@Override
	public void markDirty() {

	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {

		return true;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {

		return true;
	}

}
