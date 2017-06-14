package cofh.thermalfoundation.item;

import cofh.api.core.IPortableData;
import cofh.core.item.ItemMulti;
import cofh.core.util.StateMapper;
import cofh.core.util.core.IInitializer;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.ServerHelper;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.util.helpers.RedprintHelper;
import cofh.thermalfoundation.util.helpers.SchematicHelper;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

import static cofh.lib.util.helpers.ItemHelper.ShapelessRecipe;
import static cofh.lib.util.helpers.ItemHelper.addRecipe;

public class ItemDiagram extends ItemMulti implements IInitializer {

	public ItemDiagram() {

		super("thermalfoundation");

		setUnlocalizedName("diagram");
		setCreativeTab(ThermalFoundation.tabCommon);

		setHasSubtypes(true);
	}

	private void doRedprintUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

		if (ServerHelper.isClientWorld(world)) {
			return;
		}
		TileEntity tile = world.getTileEntity(pos);

		if (tile instanceof IPortableData) {
			if (!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
				((IPortableData) tile).writePortableData(player, stack.getTagCompound());
				if (stack.getTagCompound() == null || stack.getTagCompound().hasNoTags()) {
					stack.setTagCompound(null);
				} else {
					stack.getTagCompound().setString("Type", ((IPortableData) tile).getDataType());
					player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 0.7F);
				}
			} else {
				if (stack.getTagCompound().getString("Type").equals(((IPortableData) tile).getDataType())) {
					((IPortableData) tile).readPortableData(player, stack.getTagCompound());
				}
			}
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {

		switch (Type.values()[ItemHelper.getItemDamage(stack)]) {
			case SCHEMATIC:
				SchematicHelper.addInformation(stack, tooltip);
				break;
			case FORMULA:
				break;
			case SCROLL:
				break;
			case REDPRINT:
				RedprintHelper.addInformation(stack, tooltip);
				break;
			case ENDERPRINT:
				break;
			default:
		}
	}

	@Override
	public boolean isFull3D() {

		return true;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {

		String baseName = StringHelper.localize(getUnlocalizedName(stack) + ".name");

		switch (Type.values()[ItemHelper.getItemDamage(stack)]) {
			case SCHEMATIC:
				baseName += SchematicHelper.getDisplayName(stack);
				break;
			case FORMULA:
				break;
			case SCROLL:
				break;
			case REDPRINT:
				baseName += RedprintHelper.getDisplayName(stack);
				break;
			case ENDERPRINT:
				break;
			default:
		}
		return baseName;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {

		return RedprintHelper.getDisplayName(stack).isEmpty() ? EnumRarity.COMMON : EnumRarity.UNCOMMON;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

		ItemStack stack = player.getHeldItem(hand);
		if (player.isSneaking()) {
			if (stack.getTagCompound() != null) {
				player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.3F);
			}
			stack.setTagCompound(null);
		}
		player.swingArm(hand);
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		return player.canPlayerEdit(pos.offset(facing), facing, player.getHeldItem(hand)) ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

		ItemStack stack = player.getHeldItem(hand);
		if (player.isSneaking()) {
			if (stack.getTagCompound() != null) {
				player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.3F);
			}
			stack.setTagCompound(null);
		}
		switch (Type.values()[ItemHelper.getItemDamage(stack)]) {
			case REDPRINT:
				doRedprintUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ, hand);
				break;
			default:
				return EnumActionResult.PASS;
		}
		ServerHelper.sendItemUsePacket(world, pos, side, hand, hitX, hitY, hitZ);
		return EnumActionResult.SUCCESS;
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
	public boolean preInit() {

		schematic = addItem(Type.SCHEMATIC.ordinal(), "schematic");
		redprint = addItem(Type.REDPRINT.ordinal(), "redprint");

		ThermalFoundation.proxy.addIModelRegister(this);

		return true;
	}

	@Override
	public boolean initialize() {

		// addRecipe(ShapelessRecipe(schematic, Items.PAPER, Items.PAPER, "dyeBlue"));
		addRecipe(ShapelessRecipe(redprint, Items.PAPER, Items.PAPER, "dustRedstone"));

		return true;
	}

	@Override
	public boolean postInit() {

		return true;
	}

	/* REFERENCES */
	public static ItemStack schematic;
	public static ItemStack formula;
	public static ItemStack scroll;
	public static ItemStack redprint;
	public static ItemStack enderprint;

	/* TYPE */
	public enum Type {
		SCHEMATIC, FORMULA, SCROLL, REDPRINT, ENDERPRINT
	}

}
