package cofh.thermalfoundation.item;

import cofh.core.item.ItemMulti;
import cofh.core.util.core.IInitializer;
import cofh.core.util.helpers.ItemHelper;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

import static cofh.core.util.helpers.RecipeHelper.addShapelessOreRecipe;
import static cofh.core.util.helpers.RecipeHelper.addShapelessRecipe;

public class ItemBait extends ItemMulti implements IInitializer {

	public ItemBait() {

		super("thermalfoundation");

		setUnlocalizedName("bait");
		setCreativeTab(ThermalFoundation.tabUtils);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		//		ItemStack stack = player.getHeldItem(hand);
		//		if (!player.canPlayerEdit(pos.offset(facing), facing, stack)) {
		//			return EnumActionResult.PASS;
		//		}
		//		int radius = 1 + ItemHelper.getItemDamage(stack);
		//		int potency = 2 + ItemHelper.getItemDamage(stack);
		//
		//		if (onApplyBonemeal(stack, world, pos, player, radius, potency)) {
		//			return EnumActionResult.SUCCESS;
		//		}
		return EnumActionResult.PASS;
	}

	/* IInitializer */
	@Override
	public boolean initialize() {

		baitBasic = addItem(0, "baitBasic");
		baitRich = addItem(1, "baitRich");
		baitFlux = addItem(2, "baitFlux", EnumRarity.UNCOMMON);

		ThermalFoundation.proxy.addIModelRegister(this);

		return true;
	}

	@Override
	public boolean register() {

		addShapelessRecipe(ItemHelper.cloneStack(baitBasic, 4), "dustWood", "slimeball", Items.BREAD);
		addShapelessRecipe(ItemHelper.cloneStack(baitBasic, 4), "dustWood", ItemMaterial.globRosin, Items.BREAD);

		addShapelessRecipe(ItemHelper.cloneStack(baitRich, 4), "dustWood", "slimeball", Items.BREAD, Items.NETHER_WART);
		addShapelessRecipe(ItemHelper.cloneStack(baitRich, 4), "dustWood", ItemMaterial.globRosin, Items.BREAD, Items.NETHER_WART);

		if (!Loader.isModLoaded("thermalexpansion")) {
			addShapelessOreRecipe(baitFlux, baitRich, "dustRedstone");
		}
		return true;
	}

	/* REFERENCES */
	public static ItemStack baitBasic;
	public static ItemStack baitRich;
	public static ItemStack baitFlux;

}
