package cofh.thermalfoundation.util.helpers;

import cofh.core.inventory.InventoryCraftingFalse;
import cofh.core.util.helpers.ItemHelper;
import cofh.core.util.helpers.StringHelper;
import cofh.core.util.oredict.OreDictionaryArbiter;
import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SchematicHelper {

	private SchematicHelper() {

	}

	public static void addInformation(ItemStack stack, List<String> tooltip) {

		if (!stack.hasTagCompound()) {
			tooltip.add(StringHelper.getInfoText("info.cofh.blank"));
			return;
		}
		tooltip.add(StringHelper.getDeactivationText("info.thermalfoundation.diagram.erase"));

		boolean hasOre = false;
		TMap<String, Integer> aMap = new THashMap<>();
		String curName;

		for (int i = 0; i < 9; i++) {
			if (stack.getTagCompound().hasKey("Name" + i)) {
				if (stack.getTagCompound().hasKey("Ore" + i)) {
					hasOre = true;
					if (StringHelper.isShiftKeyDown()) {
						curName = stack.getTagCompound().getString("Ore" + i);
						if (aMap.containsKey(curName)) {
							aMap.put(curName, aMap.get(curName) + 1);
						} else {
							aMap.put(curName, 1);
						}
					}
				} else {
					curName = stack.getTagCompound().getString("Name" + i);
					if (aMap.containsKey(curName)) {
						aMap.put(curName, aMap.get(curName) + 1);
					} else {
						aMap.put(curName, 1);
					}
				}
			}
		}
		for (Map.Entry<String, Integer> entry : aMap.entrySet()) {
			tooltip.add(StringHelper.LIGHT_GRAY + entry.getValue() + "x " + entry.getKey());
		}
		if (hasOre && StringHelper.displayShiftForDetail && !StringHelper.isShiftKeyDown()) {
			tooltip.add(StringHelper.shiftForDetails());
		}
	}

	public static String getDisplayName(ItemStack stack) {

		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Output")) {
			return ": " + stack.getTagCompound().getString("Output");
		}
		return "";
	}

	public static boolean isSchematic(ItemStack stack) {

		return false;
		//return ItemHelper.itemsEqualWithMetadata(stack, ItemDiagram.schematic);
	}

	private static void copyInventory(IInventory src, IInventory dest) {

		for (int i = 0; i < src.getSizeInventory() && i < dest.getSizeInventory(); ++i) {
			dest.setInventorySlotContents(i, ItemHelper.cloneStack(src.getStackInSlot(i)));
		}
	}

	// TODO : Reimplement this in a sane manner.
	/* MUST BE PASSED AN INVENTORY WITH 9 SLOTS! */
	//	public static NBTTagCompound getNBTForSchematic(InventoryCrafting craftSlots, World world, ItemStack output) {
	//
	//		NBTTagCompound nbt = new NBTTagCompound();
	//		IRecipe recipe = null;
	//		InventoryCrafting workingSet = new InventoryCraftingFalse(3, 3);
	//		copyInventory(craftSlots, workingSet); // defensive copy
	//		{
	//			List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
	//			for (IRecipe irecipe : recipes) {
	//				if (irecipe.matches(workingSet, world)) {
	//					if (ItemHelper.itemsIdentical(output, irecipe.getCraftingResult(workingSet))) {
	//						recipe = irecipe;
	//						break;
	//					} else {
	//						copyInventory(craftSlots, workingSet); // defensive copy
	//					}
	//				}
	//			}
	//		}
	//		if (recipe == null) {
	//			// no recipe for the exact output? use the dumb search
	//			return getNBTForSchematic(craftSlots, output);
	//		}
	//		for (int i = 0; i < 9 && i < craftSlots.getSizeInventory(); i++) {
	//			if (craftSlots.getStackInSlot(i).isEmpty()) {
	//				nbt.removeTag("Slot" + i);
	//				nbt.removeTag("Name" + i);
	//				nbt.removeTag("Ore" + i);
	//			} else {
	//				NBTTagCompound itemTag = new NBTTagCompound();
	//				craftSlots.getStackInSlot(i).writeToNBT(itemTag);
	//				nbt.setTag("Slot" + i, itemTag);
	//				nbt.setString("Name" + i, craftSlots.getStackInSlot(i).getDisplayName());
	//
	//				ArrayList<String> oreNames = OreDictionaryArbiter.getAllOreNames(craftSlots.getStackInSlot(i));
	//				if (oreNames != null) {
	//					String validName = "";
	//					int validSize = 0;
	//					for (String oreName : oreNames) {
	//						if (oreName.equals(OreDictionaryArbiter.UNKNOWN)) {
	//							continue;
	//						}
	//						l:
	//						{
	//							copyInventory(craftSlots, workingSet);
	//							int size = 0;
	//							for (ItemStack stack : OreDictionaryArbiter.getOres(oreName)) {
	//								NBTTagCompound tag = stack.getTagCompound();
	//								int damage = Math.max(0, stack.getItemDamage());
	//								if (damage == OreDictionary.WILDCARD_VALUE) {
	//									damage = 0;
	//									// may or may not work. woo. can't iterate, may crash. could be 37k valid values. could have invalid values in the middle.
	//									// wish net.minecraft.item.Item.getSubItems worked on server
	//								}
	//								stack = new ItemStack(stack.getItem(), 1, damage);
	//								if (tag != null) {
	//									stack.setTagCompound(tag.copy());
	//								}
	//								workingSet.setInventorySlotContents(i, stack);
	//								if (!recipe.matches(workingSet, world)) {
	//									break l;
	//								}
	//								++size;
	//							}
	//							if (size > validSize) {
	//								validName = oreName;
	//								validSize = size;
	//							}
	//						}
	//					}
	//					if (validSize > 1) { // don't use ore names for single registrations. might break in the future.
	//						nbt.setString("Ore" + i, validName);
	//					}
	//				}
	//			}
	//		}
	//		nbt.setString("Output", output.getCount() + "x " + output.getDisplayName());
	//		return nbt;
	//	}

	/* MUST BE PASSED AN INVENTORY WITH 9 SLOTS! */
	public static NBTTagCompound getNBTForSchematic(IInventory craftSlots, ItemStack output) {

		NBTTagCompound nbt = new NBTTagCompound();
		for (int i = 0; i < 9 && i < craftSlots.getSizeInventory(); i++) {
			if (craftSlots.getStackInSlot(i).isEmpty()) {
				nbt.removeTag("Slot" + i);
				nbt.removeTag("Name" + i);
				nbt.removeTag("Ore" + i);
			} else {
				NBTTagCompound itemTag = new NBTTagCompound();
				craftSlots.getStackInSlot(i).writeToNBT(itemTag);
				nbt.setTag("Slot" + i, itemTag);
				nbt.setString("Name" + i, craftSlots.getStackInSlot(i).getDisplayName());

				ArrayList<String> oreNames = OreDictionaryArbiter.getAllOreNames(craftSlots.getStackInSlot(i));
				if (!oreNames.isEmpty()) {
					for (String oreName : oreNames) {
						if (!oreName.startsWith("list") && !oreName.equals(OreDictionaryArbiter.UNKNOWN) && !ItemHelper.isBlacklist(output)) {
							nbt.setString("Ore" + i, oreName);
						}
					}
				}

			}
		}
		nbt.setString("Output", output.getCount() + "x " + output.getDisplayName());
		return nbt;
	}

	public static ItemStack writeNBTToSchematic(ItemStack stack, NBTTagCompound nbt) {

		ItemStack returnStack = stack.copy();
		returnStack.setTagCompound(nbt);
		return returnStack;
	}

	public static ItemStack getOutput(ItemStack stack, World world) {

		InventoryCrafting tempCraft = new InventoryCraftingFalse(3, 3);
		for (int i = 0; i < 9; i++) {
			tempCraft.setInventorySlotContents(i, getSchematicSlot(stack, i));
		}
		return ItemHelper.getCraftingResult(tempCraft, world);
	}

	public static ItemStack getSchematicSlot(ItemStack stack, int slot) {

		if (stack.isEmpty()) {
			return ItemStack.EMPTY;
		}
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Slot" + slot)) {
			return new ItemStack(stack.getTagCompound().getCompoundTag("Slot" + slot));
		}
		return ItemStack.EMPTY;
	}

	public static String getSchematicOreSlot(ItemStack stack, int slot) {

		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Ore" + slot)) {
			return stack.getTagCompound().getString("Ore" + slot);
		}
		return null;
	}

}
