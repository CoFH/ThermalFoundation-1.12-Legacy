package cofh.thermalfoundation.item.diagram;

import cofh.api.core.IPortableData;
import cofh.api.core.ISecurable;
import cofh.core.util.core.IInitializer;
import cofh.core.util.helpers.ServerHelper;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.util.helpers.RedprintHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static cofh.core.util.helpers.RecipeHelper.addShapelessRecipe;

public class ItemDiagramRedprint extends ItemDiagram implements IInitializer {

	public ItemDiagramRedprint() {

		super();

		setUnlocalizedName("redprint");
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		RedprintHelper.addInformation(stack, tooltip);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isCurrentItem) {

		if (stack.getItemDamage() > 0) {
			stack.setItemDamage(0);
		}
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

		ItemStack stack = player.getHeldItem(hand);

		if (player.isSneaking()) {
			if (stack.getTagCompound() != null) {
				player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5F, 0.3F);
				stack.setTagCompound(null);
			}
			return EnumActionResult.SUCCESS;
		}
		IBlockState state = world.getBlockState(pos);
		Block block = world.getBlockState(pos).getBlock();

		if (!block.hasTileEntity(state)) {
			return EnumActionResult.PASS;
		}
		TileEntity tile = world.getTileEntity(pos);

		if (tile instanceof ISecurable && !((ISecurable) tile).canPlayerAccess(player)) {
			return EnumActionResult.PASS;
		}
		if (tile instanceof IPortableData) {
			if (ServerHelper.isServerWorld(world)) {
				if (!stack.hasTagCompound()) {
					stack.setTagCompound(new NBTTagCompound());
					((IPortableData) tile).writePortableData(player, stack.getTagCompound());
					if (stack.getTagCompound() == null || stack.getTagCompound().hasNoTags()) {
						stack.setTagCompound(null);
					} else {
						stack.getTagCompound().setString("Type", ((IPortableData) tile).getDataType());
						player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5F, 0.7F);
					}
				} else {
					if (stack.getTagCompound().getString("Type").equals(((IPortableData) tile).getDataType())) {
						((IPortableData) tile).readPortableData(player, stack.getTagCompound());
						player.world.playSound(null, player.getPosition(), SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 0.5F, 0.8F);
					}
				}
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {

		String baseName = StringHelper.localize(getUnlocalizedName(stack) + ".name");
		baseName += RedprintHelper.getDisplayName(stack);
		return baseName;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {

		return RedprintHelper.getDisplayName(stack).isEmpty() ? EnumRarity.COMMON : EnumRarity.UNCOMMON;
	}

	/* IInitializer */
	@Override
	public boolean initialize() {

		diagramRedprint = new ItemStack(this);

		ThermalFoundation.proxy.addIModelRegister(this);

		return true;
	}

	@Override
	public boolean register() {

		addShapelessRecipe(diagramRedprint, Items.PAPER, Items.PAPER, "dustRedstone");

		return true;
	}

	/* REFERENCES */
	public static ItemStack diagramRedprint;

}
