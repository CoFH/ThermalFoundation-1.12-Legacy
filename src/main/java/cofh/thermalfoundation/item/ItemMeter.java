package cofh.thermalfoundation.item;

import cofh.api.block.IBlockInfo;
import cofh.api.block.IConfigGui;
import cofh.api.tileentity.ITileInfo;
import cofh.core.item.ItemMulti;
import cofh.core.util.StateMapper;
import cofh.core.util.core.IInitializer;
import cofh.core.util.helpers.ChatHelper;
import cofh.core.util.helpers.ItemHelper;
import cofh.core.util.helpers.ServerHelper;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cofh.core.util.helpers.RecipeHelper.addShapedRecipe;

public class ItemMeter extends ItemMulti implements IInitializer {

	public ItemMeter() {

		super("thermalfoundation");

		setUnlocalizedName("util");
		setCreativeTab(ThermalFoundation.tabUtils);

		setHasSubtypes(true);
		setMaxStackSize(1);
	}

	private boolean doMultimeterUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

		player.swingArm(hand);
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		ArrayList<ITextComponent> info = new ArrayList<>();

		if (ServerHelper.isClientWorld(world)) {
			if (block instanceof IConfigGui || block instanceof IBlockInfo) {
				return true;
			}
			TileEntity tile = world.getTileEntity(pos);
			return tile instanceof ITileInfo;
		}
		if (player.isSneaking() && block instanceof IConfigGui) {
			if (((IConfigGui) block).openConfigGui(world, pos, side, player)) {
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
				((ITileInfo) tile).getTileInfo(info, side, player, false);
				ChatHelper.sendIndexedChatMessagesToPlayer(player, info);
				info.clear();
				return true;
			}
		}
		info.clear();
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		if (StringHelper.displayShiftForDetail && !StringHelper.isShiftKeyDown()) {
			tooltip.add(StringHelper.shiftForDetails());
		}
		if (!StringHelper.isShiftKeyDown()) {
			return;
		}
		switch (Type.values()[ItemHelper.getItemDamage(stack)]) {
			case MULTIMETER:
				tooltip.add(StringHelper.getInfoText("info.thermalfoundation.util.multimeter.0"));
				tooltip.add(StringHelper.getNoticeText("info.thermalfoundation.util.multimeter.1"));
				break;
			default:
		}
	}

	@Override
	public boolean isFull3D() {

		return true;
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

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		return EnumActionResult.FAIL;
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

		ItemStack stack = player.getHeldItem(hand);
		boolean ret = false;

		switch (Type.values()[ItemHelper.getItemDamage(stack)]) {
			case MULTIMETER:
				ret = doMultimeterUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ, hand);
				break;
			default:
				break;
		}
		return ret ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
	}

	/* IModelRegister */
	@Override
	@SideOnly (Side.CLIENT)
	public void registerModels() {

		StateMapper mapper = new StateMapper(modName, "util", name);
		ModelBakery.registerItemVariants(this);
		ModelLoader.setCustomMeshDefinition(this, mapper);

		for (Map.Entry<Integer, ItemEntry> entry : itemMap.entrySet()) {
			ModelLoader.setCustomModelResourceLocation(this, entry.getKey(), new ModelResourceLocation(modName + ":" + "util", "type=" + entry.getValue().name));
		}
	}

	/* IInitializer */
	@Override
	public boolean initialize() {

		ForgeRegistries.ITEMS.register(setRegistryName("meter"));
		ThermalFoundation.proxy.addIModelRegister(this);

		multimeter = addItem(Type.MULTIMETER.ordinal(), "multimeter");

		return true;
	}

	@Override
	public boolean register() {

		addShapedRecipe(multimeter, "C C", "LPL", " G ", 'C', "ingotCopper", 'L', "ingotLead", 'P', ItemMaterial.powerCoilElectrum, 'G', "gearGold");

		return true;
	}

	/* REFERENCES */
	public static ItemStack multimeter;

	/* TYPE */
	enum Type {
		MULTIMETER, DEBUGGER
	}

}
