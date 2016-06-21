package cofh.thermalfoundation.item;

import static cofh.lib.util.helpers.ItemHelper.*;

import cofh.api.block.IBlockConfigGui;
import cofh.api.block.IBlockInfo;
import cofh.api.core.IInitializer;
import cofh.api.tileentity.ITileInfo;
import cofh.core.chat.ChatHelper;
import cofh.core.item.ItemCoFHBase;
import cofh.core.util.StateMapper;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.ServerHelper;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMeter extends ItemCoFHBase implements IInitializer {

	public ItemMeter() {

		super("thermalfoundation");

		setUnlocalizedName("tool", "meter");
		setCreativeTab(ThermalFoundation.tabCommon);

		setHasSubtypes(true);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {

		if (StringHelper.displayShiftForDetail && !StringHelper.isShiftKeyDown()) {
			tooltip.add(StringHelper.shiftForDetails());
		}
		if (!StringHelper.isShiftKeyDown()) {
			return;
		}
		switch (Type.values()[ItemHelper.getItemDamage(stack)]) {
		case MULTIMETER:
			tooltip.add(StringHelper.getInfoText("info.thermalfoundation.tool.multimeter.0"));
			tooltip.add(StringHelper.getNoticeText("info.thermalfoundation.tool.multimeter.1"));
			break;
		default:
		}
	}

	@Override
	public boolean isFull3D() {

		return true;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {

		return false;
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {

		boolean ret = false;

		switch (Type.values()[ItemHelper.getItemDamage(stack)]) {
		case MULTIMETER:
			ret = doMultimeterUseFirst(stack, player, world, pos, side);
			break;
		default:
			break;
		}
		return ret;
	}

	private boolean doMultimeterUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side) {

		player.swingItem();

		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		ArrayList<IChatComponent> info = new ArrayList<IChatComponent>();

		if (ServerHelper.isClientWorld(world)) {
			if (block instanceof IBlockConfigGui || block instanceof IBlockInfo) {
				return true;
			}
			TileEntity tile = world.getTileEntity(pos);
			return tile instanceof ITileInfo;
		}
		if (player.isSneaking() && block instanceof IBlockConfigGui) {
			if (((IBlockConfigGui) block).openConfigGui(world, pos, side, player)) {
				return true;
			}
		}
		if (block instanceof IBlockInfo) {
			((IBlockInfo) (block)).getBlockInfo(info, world, pos, side, player, false);

			ChatHelper.sendIndexedChatMessagesToPlayer(player, info);
			info.clear();
			return true;
		} else {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof ITileInfo) {
				if (ServerHelper.isServerWorld(world)) {
					((ITileInfo) tile).getTileInfo(info, world, pos, side, player, false);
					ChatHelper.sendIndexedChatMessagesToPlayer(player, info);
				}
				info.clear();
				return true;
			}
		}
		info.clear();
		return false;

	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {

		switch (Type.values()[ItemHelper.getItemDamage(stack)]) {
		case MULTIMETER:
			return EnumRarity.COMMON;
		default:
			return EnumRarity.COMMON;
		}
	}

	/* IModelRegister */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {

		StateMapper mapper = new StateMapper(modName, "tool", name);
		ModelBakery.registerItemVariants(this);
		ModelLoader.setCustomMeshDefinition(this, mapper);

		for (Map.Entry<Integer, ItemEntry> entry : itemMap.entrySet()) {
			ModelLoader.setCustomModelResourceLocation(this, entry.getKey(), new ModelResourceLocation(modName + ":" + "tool", entry.getValue().name));
		}
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		multimeter = addItem(Type.MULTIMETER.ordinal(), "multimeter");

		return true;
	}

	@Override
	public boolean initialize() {

		return true;
	}

	@Override
	public boolean postInit() {

		addRecipe(ShapedRecipe(multimeter, new Object[] { "C C", "LPL", " G ", 'C', "ingotCopper", 'L', "ingotLead", 'P', ItemMaterial.powerCoilElectrum, 'G',
				"gearElectrum" }));

		return true;
	}

	/* REFERENCES */
	public static ItemStack multimeter;

	/* TYPE */
	enum Type {
		MULTIMETER, DEBUGGER;
	}

}
