package cofh.thermalfoundation.item;

import cofh.api.core.ISecurable;
import cofh.api.core.ISecurable.AccessMode;
import cofh.api.item.IPlacementUtilItem;
import cofh.core.item.ItemMulti;
import cofh.core.util.StateMapper;
import cofh.core.util.core.IInitializer;
import cofh.core.util.helpers.ChatHelper;
import cofh.core.util.helpers.ServerHelper;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

import static cofh.core.util.helpers.RecipeHelper.addShapedRecipe;

public class ItemSecurity extends ItemMulti implements IInitializer, IPlacementUtilItem {

	public ItemSecurity() {

		super("thermalfoundation");

		setUnlocalizedName("util");
		setCreativeTab(ThermalFoundation.tabUtils);

		setHasSubtypes(true);
	}

	private boolean doLockUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos) {

		if (ServerHelper.isClientWorld(world)) {
			return false;
		}
		TileEntity tile = world.getTileEntity(pos);

		if (tile instanceof ISecurable) {
			if (((ISecurable) tile).setOwner(player.getGameProfile())) {
				((ISecurable) tile).setAccess(AccessMode.PUBLIC);

				if (!player.capabilities.isCreativeMode) {
					stack.shrink(1);
				}
				player.world.playSound(null, player.getPosition(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 0.5F, 0.8F);
				ChatHelper.sendIndexedChatMessageToPlayer(player, new TextComponentTranslation("chat.cofh.secure.block.success"));
			}
			return true;
		}
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
		tooltip.add(StringHelper.getInfoText("info.thermalfoundation.security.lock"));
		tooltip.add(StringHelper.getNoticeText("info.thermalfoundation.offhandUse"));
	}

	public boolean isFull3D() {

		return true;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		return player.canPlayerEdit(pos.offset(facing), facing, player.getHeldItem(hand)) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

		return doLockUse(player.getHeldItem(hand), player, world, pos) ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
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

	/* IPlacementUtilItem */
	@Override
	public boolean onBlockPlacement(ItemStack stack, World world, BlockPos pos, IBlockState state, EntityPlayer player) {

		return doLockUse(stack, player, world, pos);
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		ForgeRegistries.ITEMS.register(setRegistryName("security"));
		ThermalFoundation.proxy.addIModelRegister(this);

		lock = addItem(0, "lock");

		return true;
	}

	@Override
	public boolean initialize() {

		addShapedRecipe(lock, " S ", "SBS", "SSS", 'B', "ingotBronze", 'S', "nuggetSignalum");

		return true;
	}

	/* REFERENCES */
	public static ItemStack lock;

}
