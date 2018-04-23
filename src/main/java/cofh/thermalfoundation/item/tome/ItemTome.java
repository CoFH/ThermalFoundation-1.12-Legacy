package cofh.thermalfoundation.item.tome;

import cofh.api.item.IMultiModeItem;
import cofh.core.item.ItemCore;
import cofh.core.render.IModelRegister;
import cofh.core.util.StateMapper;
import cofh.core.util.core.IInitializer;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemTome extends ItemCore implements IInitializer, IModelRegister, IMultiModeItem {

	public ItemTome() {

		super("thermalfoundation");

		setMaxStackSize(1);
		setCreativeTab(ThermalFoundation.tabUtils);
	}

	protected boolean isEmpowered(ItemStack stack) {

		return getMode(stack) == 1;
	}

	@Override
	public boolean isFull3D() {

		return true;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {

		if (isEmpowered(stack)) {
			return EnumRarity.RARE;
		}
		return EnumRarity.UNCOMMON;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		return EnumActionResult.FAIL;
	}

	@Override
	public Item setUnlocalizedName(String name) {

		this.name = name;
		ForgeRegistries.ITEMS.register(setRegistryName("tome_" + this.name));
		return super.setUnlocalizedName(modName + ".tome." + name);
	}

	/* IMultiModeItem */
	@Override
	public void onModeChange(EntityPlayer player, ItemStack stack) {

		if (isEmpowered(stack)) {
			player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.PLAYERS, 0.4F, 1.0F);
		} else {
			player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.2F, 0.6F);
		}
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
