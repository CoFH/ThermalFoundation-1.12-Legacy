package cofh.thermalfoundation.item;

import static cofh.lib.util.helpers.ItemHelper.*;

import cofh.api.core.IInitializer;
import cofh.api.core.IModelRegister;
import cofh.api.tileentity.IPortableData;
import cofh.core.item.ItemCoFHBase;
import cofh.core.util.StateMapper;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.ServerHelper;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.util.PatternHelper;
import cofh.thermalfoundation.util.RedprintHelper;
import cofh.thermalfoundation.util.SchematicHelper;

import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDiagram extends ItemCoFHBase implements IInitializer, IModelRegister {

	public ItemDiagram() {

		super("thermalfoundation");

		setUnlocalizedName("diagram");
		setCreativeTab(ThermalFoundation.tabCommon);

		setHasSubtypes(true);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {

		switch (Type.values()[ItemHelper.getItemDamage(stack)]) {
		case SCHEMATIC:
			SchematicHelper.addInformation(stack, tooltip);
			break;
		case PATTERN:
			PatternHelper.addInformation(stack, tooltip);
			break;
		case REDPRINT:
			RedprintHelper.addInformation(stack, tooltip);
			break;
		default:
		}
	}

	@Override
	public boolean isFull3D() {

		return true;
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

		return player.canPlayerEdit(pos.offset(side), side, stack) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
	}

	@Override
	public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

		if (player.isSneaking()) {
			if (stack.hasTagCompound()) {
				world.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.NEUTRAL, 0.5F, 0.3F);
			}
			stack.setTagCompound(null);
			return EnumActionResult.SUCCESS;
		}
		switch (Type.values()[ItemHelper.getItemDamage(stack)]) {
		case REDPRINT:
			doRedprintUseFirst(stack, player, world, pos, side);
			break;
		default:
			return EnumActionResult.SUCCESS;
		}
		ServerHelper.sendItemUsePacket(stack, player, world, pos, side, hitX, hitY, hitZ);
		return EnumActionResult.SUCCESS;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {

		if (player.isSneaking()) {
			if (stack.hasTagCompound()) {
				world.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.NEUTRAL, 0.5F, 0.3F);
			}
			stack.setTagCompound(null);
		}
		player.swingArm(hand);

		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}

	private void doRedprintUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side) {

		if (ServerHelper.isClientWorld(world)) {
			return;
		}
		TileEntity tile = world.getTileEntity(pos);

		if (tile instanceof IPortableData) {
			if (!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
				((IPortableData) tile).writePortableData(player, stack.getTagCompound());
				if (stack.getTagCompound().hasNoTags()) {
					stack.setTagCompound(null);
				} else {
					stack.getTagCompound().setString("Type", ((IPortableData) tile).getDataType());
					world.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.NEUTRAL, 0.5F, 0.7F);
				}
			} else {
				if (stack.getTagCompound().getString("Type").equals(((IPortableData) tile).getDataType())) {
					((IPortableData) tile).readPortableData(player, stack.getTagCompound());
				}
			}
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {

		String baseName = StringHelper.localize(getUnlocalizedName(stack) + ".name");

		switch (Type.values()[ItemHelper.getItemDamage(stack)]) {
		case SCHEMATIC:
			baseName += SchematicHelper.getDisplayName(stack);
			break;
		case PATTERN:
			//baseName += PatternHelper.getDisplayName(stack);
			break;
		case REDPRINT:
			baseName += RedprintHelper.getDisplayName(stack);
			break;
		default:
		}
		return baseName;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {

		return RedprintHelper.getDisplayName(stack).isEmpty() ? EnumRarity.COMMON : EnumRarity.UNCOMMON;
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

		schematic = addItem(Type.SCHEMATIC.ordinal(), "schematic");
		redprint = addItem(Type.REDPRINT.ordinal(), "redprint");

		GameRegistry.register(this.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "diagram")));

		return true;
	}

	@Override
	public boolean initialize() {

		return true;
	}

	@Override
	public boolean postInit() {

		addRecipe(ShapelessRecipe(schematic, new Object[] { Items.PAPER, Items.PAPER, "dyeBlue" }));
		addRecipe(ShapelessRecipe(redprint, new Object[] { Items.PAPER, Items.PAPER, "dustRedstone" }));

		return true;
	}

	/* REFERENCES */
	public static ItemStack schematic;
	public static ItemStack pattern;
	public static ItemStack redprint;
	public static ItemStack enderprint;

	/* TYPE */
	public enum Type {
		SCHEMATIC, PATTERN, REDPRINT, ENDERPRINT;
	}

}
