package cofh.thermalfoundation.gui.container;

import cofh.core.gui.slot.ISlotValidator;
import cofh.core.gui.slot.SlotLocked;
import cofh.core.gui.slot.SlotRemoveOnly;
import cofh.core.gui.slot.SlotValidated;
import cofh.core.util.helpers.ItemHelper;
import cofh.core.util.oredict.OreDictionaryArbiter;
import cofh.thermalfoundation.network.PacketTFBase;
import cofh.thermalfoundation.util.LexiconManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

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
	ItemStack oreStack = ItemStack.EMPTY;

	boolean syncClient = false;

	public InventoryLexiconTransmute lexiconInv = new InventoryLexiconTransmute(this);

	public ContainerLexiconTransmute(InventoryPlayer inventory) {

		bindPlayerInventory(inventory);

		addSlotToContainer(new SlotValidated(this, lexiconInv, 0, 59, 61));
		addSlotToContainer(new SlotRemoveOnly(lexiconInv, 1, 131, 61));
		addSlotToContainer(new SlotLocked(lexiconInv, 2, 95, 33));

		onCraftMatrixChanged(lexiconInv);
	}

	protected void bindPlayerInventory(InventoryPlayer inventory) {

		int xOffset = 23;
		int yOffset = 114;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, xOffset + j * 18, yOffset + i * 18));
			}
		}
		for (int i = 0; i < 9; i++) {
			if (i == inventory.currentItem) {
				addSlotToContainer(new SlotLocked(inventory, i, xOffset + i * 18, yOffset + 58));
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

		if (ore1.isEmpty() || ore2.isEmpty()) {
			return false;
		}
		ArrayList<String> nameList1 = OreDictionaryArbiter.getAllOreNames(ore1);
		ArrayList<String> nameList2 = OreDictionaryArbiter.getAllOreNames(ore2);

		if (nameList1.isEmpty() || nameList2.isEmpty()) {
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

		ItemStack input = lexiconInv.getStackInSlot(0);

		if (input.isEmpty() || !LexiconManager.validOre(input)) {
			return false;
		}
		ItemStack entry = lexiconInv.getStackInSlot(2);

		if (entry.isEmpty() || !LexiconManager.validOre(entry)) {
			return false;
		}
		if (input.isItemEqual(entry)) {
			return false;
		}
		if (!equivalentOres(input, entry)) {
			return false;
		}
		ItemStack output = lexiconInv.getStackInSlot(1);

		return output.isEmpty() || (output.equals(entry) && output.getCount() < output.getMaxStackSize());
	}

	public boolean doTransmute() {

		if (!canTransmute()) {
			return false;
		}
		ItemStack input = lexiconInv.getStackInSlot(0);
		ItemStack output = lexiconInv.getStackInSlot(1);
		ItemStack entry = lexiconInv.getStackInSlot(2);

		oreStack = ItemHelper.cloneStack(entry, 1);

		if (output.isEmpty()) {
			output = ItemHelper.cloneStack(entry, input.getCount());
			input = ItemStack.EMPTY;
		} else if (output.getCount() + input.getCount() > output.getMaxStackSize()) {
			int diff = output.getMaxStackSize() - output.getCount();
			output.setCount(output.getMaxStackSize());
			input.shrink(diff);
		} else {
			output.grow(input.getCount());
			input = ItemStack.EMPTY;
		}
		lexiconInv.setInventorySlotContents(1, output);
		lexiconInv.setInventorySlotContents(0, input);
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
		lexiconInv.setInventorySlotContents(2, oreList.get(oreSelection));
	}

	public void nextOre() {

		oreSelection++;
		oreSelection %= oreList.size();
		lexiconInv.setInventorySlotContents(2, oreList.get(oreSelection));
	}

	public void prevName() {

		nameSelection += nameList.size() - 1;
		nameSelection %= nameList.size();
		oreName = nameList.get(nameSelection);
		oreList = OreDictionaryArbiter.getOres(oreName);
		oreSelection %= oreList.size();
		lexiconInv.setInventorySlotContents(2, oreList.get(oreSelection));

		syncClient = true;
	}

	public void nextName() {

		nameSelection++;
		nameSelection %= nameList.size();
		oreName = nameList.get(nameSelection);
		oreList = OreDictionaryArbiter.getOres(oreName);
		oreSelection %= oreList.size();
		lexiconInv.setInventorySlotContents(2, oreList.get(oreSelection));

		syncClient = true;
	}

	public void handlePacket(PacketTFBase payload) {

		switch (payload.getByte()) {
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

		for (IContainerListener listener : this.listeners) {
			if (syncClient) {
				listener.sendWindowProperty(this, 0, nameSelection);
				listener.sendWindowProperty(this, 1, oreSelection);
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

		return lexiconInv.isUsableByPlayer(player);
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {

		super.onContainerClosed(player);

		ItemStack stack = this.lexiconInv.removeStackFromSlot(0);
		if (!stack.isEmpty() && !mergeItemStack(stack, 0, 36, false)) {
			player.dropItem(stack, false);
		}
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {

		super.onCraftMatrixChanged(inventory);

		ItemStack input = inventory.getStackInSlot(0);

		if (!input.isEmpty() && ItemHelper.hasOreName(input)) {
			// if there is an input and no prior transmute or the input has no common types with last transmute
			if (!equivalentOres(input, oreStack)) {
				nameList = OreDictionaryArbiter.getAllOreNames(input);

				if (nameList.isEmpty()) {
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

				if (nameList.isEmpty()) {
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

		ItemStack stack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(slotIndex);

		int invPlayer = 27;
		int invFull = invPlayer + 9;
		int invTile = invFull + 1;

		if (slot != null && slot.getHasStack()) {
			ItemStack stackInSlot = slot.getStack();
			stack = stackInSlot.copy();

			if (slotIndex < invFull) {
				if (!this.mergeItemStack(stackInSlot, invFull, invTile, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(stackInSlot, 0, invFull, true)) {
				return ItemStack.EMPTY;
			}
			if (stackInSlot.getCount() <= 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			if (stackInSlot.getCount() == stack.getCount()) {
				return ItemStack.EMPTY;
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
			while (stack.getCount() > 0 && (!ascending && k < slotMax || ascending && k >= slotMin)) {
				slot = this.inventorySlots.get(k);
				stackInSlot = slot.getStack();

				if (slot.isItemValid(stack) && ItemHelper.itemsEqualWithMetadata(stack, stackInSlot, true)) {
					int l = stackInSlot.getCount() + stack.getCount();
					int slotLimit = Math.min(stack.getMaxStackSize(), slot.getSlotStackLimit());

					if (l <= slotLimit) {
						stack.setCount(0);
						stackInSlot.setCount(l);
						slot.onSlotChanged();
						slotFound = true;
					} else if (stackInSlot.getCount() < slotLimit) {
						stack.shrink(slotLimit - stackInSlot.getCount());
						stackInSlot.setCount(slotLimit);
						slot.onSlotChanged();
						slotFound = true;
					}
				}
				k += ascending ? -1 : 1;
			}
		}
		if (stack.getCount() > 0) {
			k = ascending ? slotMax - 1 : slotMin;

			while (!ascending && k < slotMax || ascending && k >= slotMin) {
				slot = this.inventorySlots.get(k);
				stackInSlot = slot.getStack();

				if (slot.isItemValid(stack) && stackInSlot.isEmpty()) {
					slot.putStack(ItemHelper.cloneStack(stack, Math.min(stack.getCount(), slot.getSlotStackLimit())));
					slot.onSlotChanged();

					if (!slot.getStack().isEmpty()) {
						stack.shrink(slot.getStack().getCount());
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
