package cofh.thermalfoundation.gui.container;

import cofh.core.util.oredict.OreDictionaryArbiter;
import cofh.lib.gui.slot.ISlotValidator;
import cofh.lib.gui.slot.SlotLocked;
import cofh.lib.gui.slot.SlotRemoveOnly;
import cofh.lib.gui.slot.SlotValidated;
import cofh.lib.gui.slot.SlotViewOnly;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.ServerHelper;
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

	boolean sendClientUpdate = true;

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

	private void reset() {

		oreList = null;
		nameList = null;
		oreName = OreDictionaryArbiter.UNKNOWN;
		oreSelection = -1;
		nameSelection = -1;
		oreStack = null;
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

	@Override
	public void onContainerClosed(EntityPlayer player) {

		super.onContainerClosed(player);

		ItemStack stack = this.lexicon.getStackInSlotOnClosing(0);
		if (stack != null && !mergeItemStack(stack, 0, 36, true)) {
			if (ServerHelper.isServerWorld(player.worldObj)) {
				player.dropPlayerItemWithRandomChoice(stack, false);
			}
		}
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {

		ItemStack input = inventory.getStackInSlot(0);

		if (input != null && (oreStack == null || !equivalentOres(input, oreStack))) {
			// Probably shouldn't have to worry with the error checks due to slot validation, but whatever
			nameList = OreDictionaryArbiter.getAllOreNames(input);

			if (nameList == null) {
				return;
			}
			oreName = nameList.get(0);
			oreList = OreDictionaryArbiter.getOres(oreName);

			nameSelection = 0;
			oreSelection = 0;
			oreStack = ItemHelper.cloneStack(input, 1);

			inventoryItemStacks.set(2, oreList.get(0));
			inventory.setInventorySlotContents(2, oreList.get(0));

		} else if (input != null && this.oreSelection == -1 && equivalentOres(input, oreStack)) {
			// Lots of extra checks in here - in theory if we reach here, it's not going to fail
			nameList = OreDictionaryArbiter.getAllOreNames(input);

			if (nameList == null) {
				return;
			}
			oreName = getCommonOreName(input, oreStack);
			oreList = OreDictionaryArbiter.getOres(oreName);

			oreSelection = 0;
			nameSelection = 0;

			for (int i = 0; i < nameList.size(); i++) {
				if (oreName.equals(nameList.get(i))) {
					nameSelection = i;
					break;
				}
			}
			for (int i = 0; i < oreList.size(); i++) {
				if (oreStack.isItemEqual(oreList.get(i))) {
					inventory.setInventorySlotContents(2, oreList.get(i));
					oreSelection = i;
					break;
				}
			}
		} else if (input == null && oreStack != null && OreDictionaryArbiter.getAllOreNames(oreStack) == null) {
			inventory.setInventorySlotContents(2, null);
			reset();
		}
		super.onCraftMatrixChanged(inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {

		return lexicon.isUseableByPlayer(player);
	}

	@Override
	public void addCraftingToCrafters(ICrafting player) {

		super.addCraftingToCrafters(player);
		player.sendProgressBarUpdate(this, 0, nameSelection);
	}

	@Override
	public void detectAndSendChanges() {

		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++) {
			ICrafting player = (ICrafting) crafters.get(i);

			if (sendClientUpdate) {
				player.sendProgressBarUpdate(this, 0, nameSelection);
			}
		}
		sendClientUpdate = false;
	}

	@Override
	public void updateProgressBar(int i, int j) {

		ItemStack input = lexicon.getStackInSlot(0);

		if (oreStack != null) {
			nameSelection = j;
			if (input != null && LexiconManager.validOre(input)) {
				nameList = OreDictionaryArbiter.getAllOreNames(input);
				oreStack = ItemHelper.cloneStack(input, 1);
			} else {
				nameList = OreDictionaryArbiter.getAllOreNames(oreStack);
			}
			oreName = nameList.get(nameSelection);
			oreList = OreDictionaryArbiter.getOres(oreName);
		} else {
			reset();
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {

		ItemStack stack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		int invPlayer = 27;
		int invFull = invPlayer + 9;
		int invTile = invFull + lexicon.getSizeInventory();

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

	public void doTransmute() {

		if (!canTransmute()) {
			return;
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

	public String getOreName() {

		return oreName;
	}

	public boolean multipleOres() {

		return oreList != null && oreList.size() > 1;
	}

	public boolean multipleNames() {

		return nameList != null && nameList.size() > 1;
	}

	public void handlePacket(byte command) {

		switch (command) {
		case ORE_PREV:
			if (oreList != null) {
				oreSelection += oreList.size() - 1;
				oreSelection %= oreList.size();
				oreStack = oreList.get(oreSelection);
				lexicon.setInventorySlotContents(2, oreStack);

				sendClientUpdate = true;
			}
			return;
		case ORE_NEXT:
			if (oreList != null) {
				oreSelection++;
				oreSelection %= oreList.size();
				oreStack = oreList.get(oreSelection);
				lexicon.setInventorySlotContents(2, oreStack);

				sendClientUpdate = true;
			}
			return;
		case NAME_PREV:
			if (nameList != null) {
				nameSelection += nameList.size() - 1;
				nameSelection %= nameList.size();
				oreName = nameList.get(nameSelection);
				oreList = OreDictionaryArbiter.getOres(oreName);
				oreSelection %= oreList.size();
				oreStack = oreList.get(oreSelection);
				lexicon.setInventorySlotContents(2, oreStack);

				sendClientUpdate = true;
			}
			return;
		case NAME_NEXT:
			if (nameList != null) {
				nameSelection++;
				nameSelection %= nameList.size();
				oreName = nameList.get(nameSelection);
				oreList = OreDictionaryArbiter.getOres(oreName);
				oreSelection %= oreList.size();
				oreStack = oreList.get(oreSelection);
				lexicon.setInventorySlotContents(2, oreStack);

				sendClientUpdate = true;
			}
			return;
		case TRANSMUTE:
			if (canTransmute()) {
				doTransmute();
			}
			return;
		default:
		}
	}

	/* ISlotValidator */

	@Override
	public boolean isItemValid(ItemStack stack) {

		return ItemHelper.hasOreName(stack);
	}

}
