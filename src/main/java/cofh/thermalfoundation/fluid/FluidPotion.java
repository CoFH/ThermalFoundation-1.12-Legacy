package cofh.thermalfoundation.fluid;

import cofh.core.util.helpers.StringHelper;
import net.minecraft.init.PotionTypes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public class FluidPotion extends Fluid {

	public static int DEFAULT_COLOR = 16253176;

	private final String prefix;

	public FluidPotion(String fluidName, String modName, String prefix) {

		super(fluidName, new ResourceLocation(modName, "blocks/fluid/potion_still"), new ResourceLocation(modName, "blocks/fluid/potion_flow"));

		this.prefix = prefix;
	}

	@Override
	public int getColor() {

		return 0xFF000000 | DEFAULT_COLOR;
	}

	@Override
	public int getColor(FluidStack stack) {

		return 0xFF000000 | getPotionColor(stack);
	}

	public String getLocalizedName(FluidStack stack) {

		PotionType type = PotionUtils.getPotionTypeFromNBT(stack.tag);

		if (type == PotionTypes.EMPTY || type == PotionTypes.WATER) {
			return super.getLocalizedName(stack);
		}
		return StringHelper.localize(type.getNamePrefixed(prefix));
	}

	public static int getPotionColor(FluidStack stack) {

		if (stack.tag != null && stack.tag.hasKey("CustomPotionColor", 99)) {
			return stack.tag.getInteger("CustomPotionColor");
		} else {
			return getPotionTypeFromNBT(stack.tag) == PotionTypes.EMPTY ? DEFAULT_COLOR : PotionUtils.getPotionColorFromEffectList(PotionUtils.getEffectsFromTag(stack.tag));
		}
	}

	public static PotionType getPotionTypeFromNBT(@Nullable NBTTagCompound tag) {

		return tag == null || !tag.hasKey("Potion") ? PotionTypes.EMPTY : PotionType.getPotionTypeForName(tag.getString("Potion"));
	}

}
