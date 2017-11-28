package cofh.thermalfoundation.item.diagram;

import cofh.core.item.ItemCore;
import cofh.core.render.IModelRegister;
import cofh.core.util.StateMapper;
import cofh.core.util.core.IInitializer;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemDiagram extends ItemCore implements IInitializer, IModelRegister {

	public ItemDiagram() {

		super("thermalfoundation");

		setMaxStackSize(1);
		setCreativeTab(ThermalFoundation.tabUtils);
	}

	@Override
	public boolean isFull3D() {

		return true;
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
	public Item setUnlocalizedName(String name) {

		this.name = name;
		ForgeRegistries.ITEMS.register(setRegistryName("diagram_" + this.name));
		return super.setUnlocalizedName(modName + ".diagram." + name);
	}

	/* IModelRegister */
	@Override
	@SideOnly (Side.CLIENT)
	public void registerModels() {

		StateMapper mapper = new StateMapper(modName, "util", name);
		ModelLoader.setCustomMeshDefinition(this, mapper);
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(modName + ":" + "util", "type=" + name));
	}

}
