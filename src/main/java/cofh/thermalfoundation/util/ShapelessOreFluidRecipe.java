package cofh.thermalfoundation.util;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;

public class ShapelessOreFluidRecipe extends ShapelessOreRecipe {

	public ShapelessOreFluidRecipe(Block result, Object... recipe) {

		super(result, replaceFluidWithUniversalBucket(recipe));
	}

	public ShapelessOreFluidRecipe(Item result, Object... recipe) {

		super(result, replaceFluidWithUniversalBucket(recipe));
	}

	public ShapelessOreFluidRecipe(ItemStack result, Object... recipe) {

		super(result, replaceFluidWithUniversalBucket(recipe));
	}

	public static Object[] replaceFluidWithUniversalBucket(Object[] array) {

		for (int i = 0; i < array.length; i++) {
			Object obj = array[i];
			if (obj instanceof Fluid) {
				ItemStack filledBucket = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, (Fluid) obj);
				array[i] = filledBucket;
			} else if (obj instanceof FluidStack) {
				ItemStack bucket = ForgeModContainer.getInstance().universalBucket.getEmpty().copy();
				IFluidHandler handler = Validate.notNull(FluidUtil.getFluidHandler(bucket));
				handler.fill((FluidStack) obj, true);
				array[i] = bucket;
			}
		}
		return array;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {

		ItemStack[] ret = new ItemStack[inv.getSizeInventory()];
		for (int i = 0; i < ret.length; i++) {
			ItemStack stackInSlot = inv.getStackInSlot(i);
			IFluidHandler fluidHandler = FluidUtil.getFluidHandler(stackInSlot);
			if (fluidHandler == null) {
				ret[i] = ForgeHooks.getContainerItem(stackInSlot);
			} else {
				ItemStack copy = stackInSlot.copy();
				copy.stackSize = 1;
				Validate.notNull(FluidUtil.getFluidHandler(copy)).drain(Fluid.BUCKET_VOLUME, true);
				if (copy.getItem() != null && copy.stackSize > 0 && (!copy.isItemStackDamageable() || copy.getMetadata() <= copy.getMaxDamage())) {
					ret[i] = copy;
				} else {
					ret[i] = null;
				}
			}
		}
		return ret;
	}

	@Override
	public boolean matches(InventoryCrafting inventoryCrafting, World world) {

		ArrayList<Object> required = new ArrayList<Object>(input);

		for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
			ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);

			if (stackInSlot != null) {
				boolean inRecipe = false;

				for (Object aRequired : required) {
					boolean match = false;

					if (aRequired instanceof ItemStack) {
						ItemStack requiredStack = (ItemStack) aRequired;
						if (requiredStack.getItem() == ForgeModContainer.getInstance().universalBucket) {
							FluidStack fluidStack = Validate.notNull(FluidUtil.getFluidContained(requiredStack));
							IFluidHandler fluidHandler = FluidUtil.getFluidHandler(stackInSlot);
							if (fluidHandler != null) {
								if (fluidStack.isFluidStackIdentical(fluidHandler.drain(Fluid.BUCKET_VOLUME, false))) {
									match = true;
								}
							}
						} else {
							IFluidHandler fluidHandler = FluidUtil.getFluidHandler(stackInSlot);
							if (fluidHandler == null) {
								match = OreDictionary.itemMatches(requiredStack, stackInSlot, false);
							}
						}
					} else if (aRequired instanceof List) {
						for (ItemStack stack : ((List<ItemStack>) aRequired)) {
							if (OreDictionary.itemMatches(stack, stackInSlot, false)) {
								match = true;
								break;
							}
						}
					}

					if (match) {
						inRecipe = true;
						required.remove(aRequired);
						break;
					}
				}

				if (!inRecipe) {
					return false;
				}
			}
		}

		return required.isEmpty();
	}
}
