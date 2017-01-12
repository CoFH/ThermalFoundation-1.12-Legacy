package cofh.thermalfoundation.item;

import cofh.api.core.IInitializer;
import cofh.core.item.ItemCoFHBase;
import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

import static cofh.lib.util.helpers.ItemHelper.ShapedRecipe;
import static cofh.lib.util.helpers.ItemHelper.addRecipe;

public class ItemSecurity extends ItemCoFHBase implements IInitializer {

	public ItemSecurity() {

		super("thermalfoundation");

		setUnlocalizedName("tool", "security");
		setCreativeTab(ThermalFoundation.tabCommon);

		setHasSubtypes(true);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {

	}

    public boolean isFull3D() {

        return true;
    }

	@Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        return player.canPlayerEdit(pos.offset(facing), facing, stack) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
    }

	@Override
    public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

		boolean ret = false;

		switch (Type.values()[ItemHelper.getItemDamage(stack)]) {
		case LOCK:
			ret = doLockUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ, hand);
			break;
		default:
			break;
		}
        return ret ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
	}

	private boolean doLockUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

		return false;
	}

	/* IModelRegister */
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerModels() {
//
//		StateMapper mapper = new StateMapper(modName, "tool", name);
//		ModelBakery.registerItemVariants(this);
//		ModelLoader.setCustomMeshDefinition(this, mapper);
//
//		for (Map.Entry<Integer, ItemEntry> entry : itemMap.entrySet()) {
//			ModelLoader.setCustomModelResourceLocation(this, entry.getKey(), new ModelResourceLocation(modName + ":" + "tool", entry.getValue().name));
//		}
//	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		lock = addItem(Type.LOCK.ordinal(), "lock");

		return true;
	}

	@Override
	public boolean initialize() {

		return true;
	}

	@Override
	public boolean postInit() {

		addRecipe(ShapedRecipe(lock, new Object[] { " S ", "SBS", "SSS", 'B', "ingotBronze", 'S', "nuggetSignalum" }));

		return true;
	}

	/* REFERENCES */
	public static ItemStack lock;

	/* TYPE */
	enum Type {
		LOCK;
	}

}
