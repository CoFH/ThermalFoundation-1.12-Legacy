package cofh.thermalfoundation.gui.container;

import cofh.core.util.oredict.OreDictionaryArbiter;
import cofh.lib.gui.container.ContainerInventoryItem;
import cofh.lib.gui.slot.SlotLocked;
import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalfoundation.network.PacketTFBase;
import cofh.thermalfoundation.util.LexiconManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ContainerLexiconStudy extends ContainerInventoryItem {

	public static final byte ORE_PREV = 0;
	public static final byte ORE_NEXT = 1;
	public static final byte SET_PREFERRED = 2;
	public static final byte CLEAR_PREFERRED = 3;
	public static final byte SELECT_ORE = 4;

	ArrayList<ItemStack> oreList;
	int oreSelection = -1;
	boolean hasPreferredStack = false;
	boolean syncClient = false;

	EntityPlayer player;

	public InventoryLexiconStudy lexiconInv = new InventoryLexiconStudy();

	public ContainerLexiconStudy(ItemStack stack, InventoryPlayer inventory) {

		super(stack, inventory);

		player = inventory.player;
		addSlotToContainer(new SlotLocked(lexiconInv, 0, 95, 33));
	}

	public boolean canSetPreferred() {

		ItemStack input = lexiconInv.getStackInSlot(0);

		if (input == null) {
			return false;
		}
		return LexiconManager.validOre(input);
	}

	public boolean doSetPreferred() {

		if (!canSetPreferred()) {
			return false;
		}
		LexiconManager.setPreferredStack(player, lexiconInv.getStackInSlot(0));
		hasPreferredStack = true;
		syncClient = true;

		return true;
	}

	public boolean doClearPreferred() {

		if (!hasPreferredOre()) {
			return false;
		}
		LexiconManager.clearPreferredStack(player, lexiconInv.getStackInSlot(0));
		hasPreferredStack = false;
		syncClient = true;

		return true;
	}

	public boolean hasMultipleOres() {

		return oreList != null && oreList.size() > 1;
	}

	public boolean hasPreferredOre() {

		return hasPreferredStack;
	}

	public void prevOre() {

		oreSelection += oreList.size() - 1;
		oreSelection %= oreList.size();
		lexiconInv.setInventorySlotContents(0, oreList.get(oreSelection));
	}

	public void nextOre() {

		oreSelection++;
		oreSelection %= oreList.size();
		lexiconInv.setInventorySlotContents(0, oreList.get(oreSelection));
	}

	public void onSelectionChanged(String oreName) {

		oreList = OreDictionaryArbiter.getOres(oreName);
		if (LexiconManager.hasPreferredStack(player, oreName)) {
			ItemStack ore = LexiconManager.getPreferredStack(player, oreName);
			lexiconInv.setInventorySlotContents(0, ore);
			for (int i = 0; i < oreList.size(); i++) {
				if (ItemHelper.itemsIdentical(oreList.get(i), ore)) {
					oreSelection = i;
					break;
				}
			}
		}
		PacketTFBase.sendLexiconStudySelectPacketToServer(ContainerLexiconStudy.SELECT_ORE, (oreName));
	}

	public void handlePacket(PacketTFBase payload) {

		switch (payload.getByte()) {
			case ORE_PREV:
				prevOre();
				return;
			case ORE_NEXT:
				nextOre();
				return;
			case SET_PREFERRED:
				doSetPreferred();
				return;
			case CLEAR_PREFERRED:
				doClearPreferred();
				return;
			case SELECT_ORE:
				String oreName = payload.getString();
				oreList = OreDictionaryArbiter.getOres(oreName);
				if (LexiconManager.hasPreferredStack(player, oreName)) {
					ItemStack ore = LexiconManager.getPreferredStack(player, oreName);
					lexiconInv.setInventorySlotContents(0, ore);
					for (int i = 0; i < oreList.size(); i++) {
						if (ItemHelper.itemsIdentical(oreList.get(i), ore)) {
							oreSelection = i;
							break;
						}
					}
					hasPreferredStack = true;
				} else {
					lexiconInv.setInventorySlotContents(0, OreDictionaryArbiter.getOres(oreName).get(0));
					oreSelection = 0;
					hasPreferredStack = false;
				}
				syncClient = true;
			default:

		}
	}

	@Override
	public void detectAndSendChanges() {

		super.detectAndSendChanges();

		for (int j = 0; j < this.listeners.size(); ++j) {
			if (syncClient) {
				this.listeners.get(j).sendProgressBarUpdate(this, 0, hasPreferredStack ? 1 : 0);
				syncClient = false;
			}
		}
	}

	@Override
	public void updateProgressBar(int i, int j) {

		if (j == 1) {
			hasPreferredStack = true;
		} else {
			hasPreferredStack = false;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {

		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {

		return null;
	}

	@Override
	protected int getPlayerInventoryVerticalOffset() {

		return 84;
	}

}
