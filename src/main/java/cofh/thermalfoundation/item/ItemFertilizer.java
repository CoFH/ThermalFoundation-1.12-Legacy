package cofh.thermalfoundation.item;

import cofh.core.item.ItemMulti;
import cofh.core.util.core.IInitializer;
import cofh.core.util.helpers.ItemHelper;
import cofh.core.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.Loader;

import static cofh.core.util.helpers.RecipeHelper.addShapelessOreRecipe;
import static cofh.core.util.helpers.RecipeHelper.addShapelessRecipe;

public class ItemFertilizer extends ItemMulti implements IInitializer {

	public ItemFertilizer() {

		super("thermalfoundation");

		setUnlocalizedName("fertilizer");
		setCreativeTab(ThermalFoundation.tabUtils);
	}

	private boolean growBlock(World world, BlockPos pos, IBlockState state) {

		if (state.getBlock() instanceof IGrowable) {
			IGrowable growable = (IGrowable) state.getBlock();

			if (growable.canGrow(world, pos, state, world.isRemote)) {
				if (ServerHelper.isServerWorld(world)) {
					if (growable.canUseBonemeal(world, world.rand, pos, state)) {
						growable.grow(world, world.rand, pos, state);
						world.playEvent(2005, pos, 0);
					}
				}
				return true;
			}
		}
		return false;
	}

	private boolean onApplyBonemeal(ItemStack stack, World world, BlockPos pos, EntityPlayer player, EnumHand hand, int radius) {

		IBlockState state = world.getBlockState(pos);

		int hook = ForgeEventFactory.onApplyBonemeal(player, world, pos, state, stack, hand);
		if (hook != 0) {
			return hook > 0;
		}
		boolean used = false;
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		BlockPos pos2;

		for (int i = x - radius; i <= x + radius; i++) {
			for (int k = z - radius; k <= z + radius; k++) {
				pos2 = new BlockPos(i, y, k);
				used |= growBlock(world, pos2, world.getBlockState(pos2));
			}
		}
		if (used) {
			stack.shrink(1);
		}
		return used;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		ItemStack stack = player.getHeldItem(hand);
		if (!player.canPlayerEdit(pos.offset(facing), facing, stack)) {
			return EnumActionResult.PASS;
		}
		int radius = 1 + ItemHelper.getItemDamage(stack);

		if (onApplyBonemeal(stack, world, pos, player, hand, radius)) {
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

	/* IInitializer */
	@Override
	public boolean initialize() {

		fertilizerBasic = addItem(0, "fertilizerBasic");
		fertilizerRich = addItem(1, "fertilizerRich");
		fertilizerFlux = addItem(2, "fertilizerFlux", EnumRarity.UNCOMMON);

		ThermalFoundation.proxy.addIModelRegister(this);

		return true;
	}

	@Override
	public boolean register() {

		addShapelessRecipe(ItemHelper.cloneStack(fertilizerBasic, 4), "dustWood", "dustWood", "dustSaltpeter", "crystalSlag");
		addShapelessRecipe(ItemHelper.cloneStack(fertilizerBasic, 12), "dustCharcoal", "dustSaltpeter", "crystalSlag");

		addShapelessRecipe(ItemHelper.cloneStack(fertilizerRich, 4), "dustWood", "dustWood", "dustSaltpeter", "crystalSlagRich");
		addShapelessRecipe(ItemHelper.cloneStack(fertilizerRich, 12), "dustCharcoal", "dustSaltpeter", "crystalSlagRich");

		if (!Loader.isModLoaded("thermalexpansion")) {
			addShapelessRecipe(fertilizerRich, fertilizerBasic, new ItemStack(Items.DYE, 1, EnumDyeColor.WHITE.getDyeDamage()));
			addShapelessOreRecipe(fertilizerFlux, fertilizerRich, "dustRedstone");
		}

		return true;
	}

	/* REFERENCES */
	public static ItemStack fertilizerBasic;
	public static ItemStack fertilizerRich;
	public static ItemStack fertilizerFlux;

}
