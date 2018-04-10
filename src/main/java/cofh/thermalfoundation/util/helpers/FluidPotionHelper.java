package cofh.thermalfoundation.util.helpers;

import cofh.thermalfoundation.init.TFFluids;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidPotionHelper {

	public static FluidStack getFluidPotion(int amount, ItemStack stack) {

		Item item = stack.getItem();

		if (item.equals(Items.POTIONITEM)) {
			return getPotion(amount, PotionUtils.getPotionFromItem(stack));
		} else if (item.equals(Items.SPLASH_POTION)) {
			return getSplashPotion(amount, PotionUtils.getPotionFromItem(stack));
		} else if (item.equals(Items.LINGERING_POTION)) {
			return getLingeringPotion(amount, PotionUtils.getPotionFromItem(stack));
		}
		return null;
	}

	public static FluidStack getPotion(int amount, PotionType type) {

		if (type == PotionTypes.WATER) {
			return new FluidStack(FluidRegistry.WATER, amount);
		}
		return addPotionToFluidStack(new FluidStack(TFFluids.fluidPotion, amount), type);
	}

	public static FluidStack getSplashPotion(int amount, PotionType type) {

		return addPotionToFluidStack(new FluidStack(TFFluids.fluidPotionSplash, amount), type);
	}

	public static FluidStack getLingeringPotion(int amount, PotionType type) {

		return addPotionToFluidStack(new FluidStack(TFFluids.fluidPotionLingering, amount), type);
	}

	public static FluidStack addPotionToFluidStack(FluidStack stack, PotionType type) {

		ResourceLocation resourcelocation = PotionType.REGISTRY.getNameForObject(type);

		if (type == PotionTypes.EMPTY) {
			if (stack.tag != null) {
				stack.tag.removeTag("Potion");
				if (stack.tag.hasNoTags()) {
					stack.tag = null;
				}
			}
		} else {
			if (stack.tag == null) {
				stack.tag = new NBTTagCompound();
			}
			stack.tag.setString("Potion", resourcelocation.toString());
		}
		return stack;
	}

}
