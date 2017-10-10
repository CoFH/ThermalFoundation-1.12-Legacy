package cofh.thermalfoundation.item;

import cofh.core.item.ItemMulti;
import cofh.core.util.core.IInitializer;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemDye extends ItemMulti implements IInitializer {

	public ItemDye() {

		super("thermalfoundation");

		setUnlocalizedName("dye");
		setCreativeTab(ThermalFoundation.tabCommon);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		// These are gonna have some kind of "on use" action. Probably converting dyeable blocks in world. :)

		return EnumActionResult.PASS;
	}

	/* IInitializer */
	@Override
	public boolean initialize() {

		dyeBlack = addOreDictItem(0, "dyeBlack");
		dyeRed = addOreDictItem(1, "dyeRed");
		dyeGreen = addOreDictItem(2, "dyeGreen");
		dyeBrown = addOreDictItem(3, "dyeBrown");
		dyeBlue = addOreDictItem(4, "dyeBlue");
		dyePurple = addOreDictItem(5, "dyePurple");
		dyeCyan = addOreDictItem(6, "dyeCyan");
		dyeLightGray = addOreDictItem(7, "dyeLightGray");
		dyeGray = addOreDictItem(8, "dyeGray");
		dyePink = addOreDictItem(9, "dyePink");
		dyeLime = addOreDictItem(10, "dyeLime");
		dyeYellow = addOreDictItem(11, "dyeYellow");
		dyeLightBlue = addOreDictItem(12, "dyeLightBlue");
		dyeMagenta = addOreDictItem(13, "dyeMagenta");
		dyeOrange = addOreDictItem(14, "dyeOrange");
		dyeWhite = addOreDictItem(15, "dyeWhite");

		return true;
	}

	@Override
	public boolean register() {

		return true;
	}

	/* REFERENCES */
	public static ItemStack dyeBlack;
	public static ItemStack dyeRed;
	public static ItemStack dyeGreen;
	public static ItemStack dyeBrown;
	public static ItemStack dyeBlue;
	public static ItemStack dyePurple;
	public static ItemStack dyeCyan;
	public static ItemStack dyeLightGray;
	public static ItemStack dyeGray;
	public static ItemStack dyePink;
	public static ItemStack dyeLime;
	public static ItemStack dyeYellow;
	public static ItemStack dyeLightBlue;
	public static ItemStack dyeMagenta;
	public static ItemStack dyeOrange;
	public static ItemStack dyeWhite;

}
