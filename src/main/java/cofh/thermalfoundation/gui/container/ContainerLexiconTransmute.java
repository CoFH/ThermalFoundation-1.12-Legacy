package cofh.thermalfoundation.gui.container;

import cofh.core.util.oredict.OreDictionaryArbiter;
import cofh.lib.gui.slot.ISlotValidator;
import cofh.lib.gui.slot.SlotLocked;
import cofh.lib.gui.slot.SlotRemoveOnly;
import cofh.lib.gui.slot.SlotValidated;
import cofh.lib.gui.slot.SlotViewOnly;
import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalfoundation.util.LexiconManager;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLexiconTransmute extends Container implements ISlotValidator {

	public static final byte ORE_PREV = 0;
	public static final byte ORE_NEXT = 1;
	public static final byte NAME_PREV = 2;
	public static final byte NAME_NEXT = 3;
	public static final byte TRANSMUTE = 4;

	ArrayList<ItemStack> oreList;
	ArrayList<String> nameList;
	int oreSelection = -1;
	int nameSelection = -1;
	String oreName = OreDictionaryArbiter.UNKNOWN;
	ItemStack oreStack;

	boolean syncClient = false;

	public InventoryLexiconTransmute lexicon = new InventoryLexiconTransmute(this);

	public ContainerLexiconTransmute(InventoryPlayer inventory) {

		addPlayerSlotsToContainer(inventory, 23, 114);

		addSlotToContainer(new SlotValidated(this, lexicon, 0, 59, 61));
		addSlotToContainer(new SlotRemoveOnly(lexicon, 1, 131, 61));
		addSlotToContainer(new SlotLocked(lexicon, 2, 95, 33));

		onCraftMatrixChanged(lexicon);
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

	private String getCommonOreName(ItemStack ore1, ItemStack ore2) {

		ArrayList<String> nameList1 = OreDictionaryArbiter.getAllOreNames(ore1);
		ArrayList<String> nameList2 = OreDictionaryArbiter.getAllOreNames(ore2);

		for (String name1 : nameList1) {
			for (String name2 : nameList2) {
				if (name1.equals(name2)) {
					return name1;
				}
			}
		}
		return OreDictionaryArbiter.UNKNOWN;
	}

	private boolean equivalentOres(ItemStack ore1, ItemStack ore2) {

		if (ore1 == null || ore2 == null) {
			return false;
		}
		ArrayList<String> nameList1 = OreDictionaryArbiter.getAllOreNames(ore1);
		ArrayList<String> nameList2 = OreDictionaryArbiter.getAllOreNames(ore2);

		if (nameList1 == null || nameList2 == null) {
			return false;
		}
		for (String name1 : nameList1) {
			for (String name2 : nameList2) {
				if (name1.equals(name2)) {
					return true;
				}
			}
		}
		return false;
	}

	public String getOreName() {

		return oreName;
	}

	public boolean canTransmute() {

		ItemStack input = lexicon.getStackInSlot(0);

		if (!LexiconManager.validOre(input)) {
			return false;
		}
		ItemStack entry = lexicon.getStackInSlot(2);

		if (!LexiconManager.validOre(entry)) {
			return false;
		}
		if (input.isItemEqual(entry)) {
			return false;
		}
		if (!equivalentOres(input, entry)) {
			return false;
		}
		ItemStack output = lexicon.getStackInSlot(1);

		return output == null || (output.equals(entry) && output.stackSize < output.getMaxStackSize());
	}

	public boolean doTransmute() {

		if (!canTransmute()) {
			return false;
		}
		ItemStack input = lexicon.getStackInSlot(0);
		ItemStack output = lexicon.getStackInSlot(1);
		ItemStack entry = lexicon.getStackInSlot(2);

		oreStack = ItemHelper.cloneStack(entry, 1);

		if (output == null) {
			output = ItemHelper.cloneStack(entry, input.stackSize);
			input = null;
		} else if (output.stackSize + input.stackSize > output.getMaxStackSize()) {
			int diff = output.getMaxStackSize() - output.stackSize;
			output.stackSize = output.getMaxStackSize();
			input.stackSize -= diff;
		} else {
			output.stackSize += input.stackSize;
			input = null;
		}
		lexicon.setInventorySlotContents(1, output);
		lexicon.setInventorySlotContents(0, input);
		return true;
	}

	public boolean hasMultipleOres() {

		return oreList != null && oreList.size() > 1;
	}

	public boolean hasMultipleNames() {

		return nameList != null && nameList.size() > 1;
	}

	public void prevOre() {

		oreSelection += oreList.size() - 1;
		oreSelection %= oreList.size();
		lexicon.setInventorySlotContents(2, oreList.get(oreSelection));
	}

	public void nextOre() {

		oreSelection++;
		oreSelection %= oreList.size();
		lexicon.setInventorySlotContents(2, oreList.get(oreSelection));
	}

	public void prevName() {

		nameSelection += nameList.size() - 1;
		nameSelection %= nameList.size();
		oreName = nameList.get(nameSelection);
		oreList = OreDictionaryArbiter.getOres(oreName);
		oreSelection %= oreList.size();
		lexicon.setInventorySlotContents(2, oreList.get(oreSelection));

		syncClient = true;
	}

	public void nextName() {

		nameSelection++;
		nameSelection %= nameList.size();
		oreName = nameList.get(nameSelection);
		oreList = OreDictionaryArbiter.getOres(oreName);
		oreSelection %= oreList.size();
		lexicon.setInventorySlotContents(2, oreList.get(oreSelection));

		syncClient = true;
	}

	public void handlePacket(byte command) {

		switch (command) {
		case ORE_PREV:
			prevOre();
			return;
		case ORE_NEXT:
			nextOre();
			return;
		case NAME_PREV:
			prevName();
			return;
		case NAME_NEXT:
			nextName();
			return;
		case TRANSMUTE:
			doTransmute();
			return;
		default:

		}
	}

	@Override
	public void detectAndSendChanges() {

		super.detectAndSendChanges();

		for (int j = 0; j < this.crafters.size(); ++j) {
			if (syncClient) {
				((ICrafting) this.crafters.get(j)).sendProgressBarUpdate(this, 0, nameSelection);
				((ICrafting) this.crafters.get(j)).sendProgressBarUpdate(this, 1, oreSelection);
				syncClient = false;
			}
		}
	}

	@Override
	public void updateProgressBar(int i, int j) {

		if (i == 0) {
			nameSelection = j;
			oreName = nameList.get(nameSelection);
			oreList = OreDictionaryArbiter.getOres(oreName);
		} else if (i == 1) {
			oreSelection = j;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {

		return lexicon.isUseableByPlayer(player);
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {

		super.onContainerClosed(player);

		ItemStack stack = this.lexicon.getStackInSlotOnClosing(0);
		if (stack != null && !mergeItemStack(stack, 0, 36, false)) {
			player.dropPlayerItemWithRandomChoice(stack, false);
		}
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {

		super.onCraftMatrixChanged(inventory);

		ItemStack input = inventory.getStackInSlot(0);

		if (input == null || !ItemHelper.hasOreName(input)) {
			// do nothing
		} else {
			// if there is an input and no prior transmute or the input has no common types with last transmute
			if (!equivalentOres(input, oreStack)) {
				nameList = OreDictionaryArbiter.getAllOreNames(input);

				if (nameList == null) {
					return;
				}
				// no existing/common types - start at 0
				oreName = nameList.get(0);
				oreList = OreDictionaryArbiter.getOres(oreName);

				nameSelection = 0;
				oreSelection = 0;
				oreStack = ItemHelper.cloneStack(oreList.get(oreSelection), 1);

				inventory.setInventorySlotContents(2, oreList.get(oreSelection));
				// if the input DOES have a common type with the existing ore
			} else {
				nameList = OreDictionaryArbiter.getAllOreNames(input);

				if (nameList == null) {
					return;
				}
				// get the first common type
				oreName = getCommonOreName(input, oreStack);
				oreList = OreDictionaryArbiter.getOres(oreName);

				nameSelection = 0;
				oreSelection %= oreList.size();

				for (int i = 0; i < nameList.size(); i++) {
					if (oreName.equals(nameList.get(i))) {
						nameSelection = i;
						break;
					}
				}
			}
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {

		ItemStack stack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		int invPlayer = 27;
		int invFull = invPlayer + 9;
		int invTile = invFull + 1;

		if (slot != null && slot.getHasStack()) {
			ItemStack stackInSlot = slot.getStack();
			stack = stackInSlot.copy();

			if (slotIndex < invFull) {
				if (!this.mergeItemStack(stackInSlot, invFull, invTile, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(stackInSlot, 0, invFull, true)) {
				return null;
			}
			if (stackInSlot.stackSize <= 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
			if (stackInSlot.stackSize == stack.stackSize) {
				return null;
			}
		}
		return stack;
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int slotMin, int slotMax, boolean ascending) {

		boolean slotFound = false;
		int k = ascending ? slotMax - 1 : slotMin;

		Slot slot;
		ItemStack stackInSlot;

		if (stack.isStackable()) {
			while (stack.stackSize > 0 && (!ascending && k < slotMax || ascending && k >= slotMin)) {
				slot = (Slot) this.inventorySlots.get(k);
				stackInSlot = slot.getStack();

				if (slot.isItemValid(stack) && ItemHelper.itemsEqualWithMetadata(stack, stackInSlot, true)) {
					int l = stackInSlot.stackSize + stack.stackSize;
					int slotLimit = Math.min(stack.getMaxStackSize(), slot.getSlotStackLimit());

					if (l <= slotLimit) {
						stack.stackSize = 0;
						stackInSlot.stackSize = l;
						slot.onSlotChanged();
						slotFound = true;
					} else if (stackInSlot.stackSize < slotLimit) {
						stack.stackSize -= slotLimit - stackInSlot.stackSize;
						stackInSlot.stackSize = slotLimit;
						slot.onSlotChanged();
						slotFound = true;
					}
				}
				k += ascending ? -1 : 1;
			}
		}
		if (stack.stackSize > 0) {
			k = ascending ? slotMax - 1 : slotMin;

			while (!ascending && k < slotMax || ascending && k >= slotMin) {
				slot = (Slot) this.inventorySlots.get(k);
				stackInSlot = slot.getStack();

				if (slot.isItemValid(stack) && stackInSlot == null) {
					slot.putStack(ItemHelper.cloneStack(stack, Math.min(stack.stackSize, slot.getSlotStackLimit())));
					slot.onSlotChanged();

					if (slot.getStack() != null) {
						stack.stackSize -= slot.getStack().stackSize;
						slotFound = true;
					}
					break;
				}
				k += ascending ? -1 : 1;
			}
		}
		return slotFound;
	}

	/* ISlotValidator */
	@Override
	public boolean isItemValid(ItemStack stack) {

		return ItemHelper.hasOreName(stack);
	}

}
