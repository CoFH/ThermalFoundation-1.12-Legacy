package cofh.thermalfoundation.gui.container;

import cofh.core.util.helpers.InventoryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import java.util.Arrays;

public class InventoryLexiconStudy implements IInventory {

	protected ItemStack[] stackList;

	public InventoryLexiconStudy() {

		stackList = new ItemStack[1];
		Arrays.fill(stackList, ItemStack.EMPTY);
	}

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

		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStackFromSlot(int slot) {

		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {

		this.stackList[slot] = stack;
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
	}

	@Override
	public void markDirty() {

	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {

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
