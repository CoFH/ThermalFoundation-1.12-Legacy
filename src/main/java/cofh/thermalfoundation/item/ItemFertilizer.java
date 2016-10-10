package cofh.thermalfoundation.item;

import static cofh.lib.util.helpers.ItemHelper.*;

import cofh.api.core.IInitializer;
import cofh.api.core.IModelRegister;
import cofh.core.item.ItemCoFHBase;
import cofh.core.util.StateMapper;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;

import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

public class ItemFertilizer extends ItemCoFHBase implements IInitializer, IModelRegister {

	public ItemFertilizer() {

		super("thermalfoundation");

		setUnlocalizedName("fertilizer");
		setCreativeTab(ThermalFoundation.tabCommon);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

		if (!player.canPlayerEdit(pos.offset(side), side, stack)) {
			return EnumActionResult.FAIL;
		}
		int radius = 1 + ItemHelper.getItemDamage(stack);
		int potency = 2 + ItemHelper.getItemDamage(stack);

		if (onApplyBonemeal(stack, world, pos, player, radius, potency)) {
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

	public static boolean onApplyBonemeal(ItemStack stack, World world, BlockPos pos, EntityPlayer player, int radius, int potency) {

		IBlockState state = world.getBlockState(pos);

		int hook = ForgeEventFactory.onApplyBonemeal(player, world, pos, state, stack);
		if (hook != 0) {
			return hook > 0;
		}
		boolean used = false;
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		BlockPos pos2;

		for (int i = x - radius; i <= x + radius; i++) {
			for (int k = z - radius; k <= z + radius; k++) {
				pos2 = new BlockPos(i, y, k);
				used |= growBlock(world, pos2, world.getBlockState(pos2), potency);
			}
		}
		if (used) {
			--stack.stackSize;
		}
		return used;
	}

	public static boolean growBlock(World world, BlockPos pos, IBlockState state, int potency) {

		if (state.getBlock() instanceof IGrowable) {
			IGrowable growable = (IGrowable) state.getBlock();

			if (growable.canGrow(world, pos, state, world.isRemote)) {
				if (ServerHelper.isServerWorld(world)) {
					if (growable.canUseBonemeal(world, world.rand, pos, state)) {
						for (int i = 0; i < potency; i++) {
							growable.grow(world, world.rand, pos, state);
						}
						world.playEvent(2005, pos, 0);
					}
				}
				return true;
			}
		}
		return false;
	}

	/* IModelRegister */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {

		StateMapper mapper = new StateMapper(modName, "fertilizer", name);
		ModelBakery.registerItemVariants(this);
		ModelLoader.setCustomMeshDefinition(this, mapper);

		for (Map.Entry<Integer, ItemEntry> entry : itemMap.entrySet()) {
			ModelLoader.setCustomModelResourceLocation(this, entry.getKey(), new ModelResourceLocation(modName + ":" + "fertilizer", entry.getValue().name));
		}
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		fertilizerBasic = addItem(0, "fertilizerBasic");
		fertilizerRich = addItem(1, "fertilizerRich");
		//fertilizerFlux = addItem(2, "fertilizerFlux"); TODO add flux fertilizer

		GameRegistry.register(this.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "fertilizer")));

		return true;
	}

	@Override
	public boolean initialize() {

		return true;
	}

	@Override
	public boolean postInit() {

		addRecipe(ShapelessRecipe(ItemHelper.cloneStack(fertilizerBasic, 8), new Object[] { "dustWood", "dustWood", "dustSaltpeter", "crystalSlag" }));
		addRecipe(ShapelessRecipe(ItemHelper.cloneStack(fertilizerBasic, 32), new Object[] { "dustCharcoal", "dustSaltpeter", "crystalSlag" }));
		addRecipe(ShapelessRecipe(ItemHelper.cloneStack(fertilizerRich, 8), new Object[] { "dustWood", "dustWood", "dustSaltpeter", "crystalSlagRich" }));
		addRecipe(ShapelessRecipe(ItemHelper.cloneStack(fertilizerRich, 32), new Object[] { "dustCharcoal", "dustSaltpeter", "crystalSlagRich" }));

		return true;
	}

	/* REFERENCES */
	public static ItemStack fertilizerBasic;
	public static ItemStack fertilizerRich;
	public static ItemStack fertilizerFlux;

}
