package cofh.thermalfoundation.fluid;

import cofh.core.fluid.BlockFluidInteractive;
import cofh.lib.util.BlockWrapper;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockFluidPetrotheum extends BlockFluidInteractive {

	public static final int LEVELS = 6;
	public static final Material materialFluidPetrotheum = new MaterialLiquid(MapColor.stoneColor);

	private static boolean effect = true;
	private static boolean enableSourceFall = true;
	private static boolean extreme = false;

	public BlockFluidPetrotheum(Fluid fluid) {

		super(fluid, materialFluidPetrotheum, "thermalfoundation", "petrotheum");
		setQuantaPerBlock(LEVELS);
		setTickRate(10);

		setHardness(1000F);
		setLightOpacity(1);
		setParticleColor(0.4F, 0.3F, 0.2F);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {

		if (!effect) {
			return;
		}
		if (ServerHelper.isClientWorld(world)) {
			return;
		}
		if (world.getTotalWorldTime() % 8 != 0) {
			return;
		}
		if (world.getTotalWorldTime() % 8 == 0 && entity instanceof EntityLivingBase && !((EntityLivingBase) entity).isEntityUndead()) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.digSpeed.id, 15 * 20, 2));
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.nightVision.id, 15 * 20, 0));
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.resistance.id, 15 * 20, 1));
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {

		if (effect) {
			checkForInteraction(world, pos, state);
		}
		if (enableSourceFall && getMetaFromState(state) == 0) {
			IBlockState stateDown = world.getBlockState(pos.add(0, densityDir, 0));
			Block block = stateDown.getBlock();

			if (block == this && getMetaFromState(stateDown) != 0) {
				world.setBlockState(pos.add(0, densityDir, 0), getDefaultState(), 2);
				world.setBlockToAir(pos);
				return;
			}
		}
		super.updateTick(world, pos, state, rand);
	}

	protected void checkForInteraction(World world, BlockPos pos, IBlockState state) {

		if (state.getBlock() != this) {
			return;
		}
		interactWithBlock(world, pos.add(-1, 0, 0));
		interactWithBlock(world, pos.add(1, 0, 0));
		interactWithBlock(world, pos.add(0, 0, -1));
		interactWithBlock(world, pos.add(0, 0, 1));
	}

	protected void interactWithBlock(World world, BlockPos pos) {

		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		if (block.isAir(world, pos) || block == this) {
			return;
		}
		int bMeta = block.getMetaFromState(state);
		BlockWrapper result;

		if (extreme && block.getMaterial() == Material.rock && block.getBlockHardness(world, pos) > 0) {
			block.dropBlockAsItem(world, pos, state, 0);
			world.setBlockToAir(pos);
			triggerInteractionEffects(world, pos);
		} else if (hasInteraction(block, bMeta)) {
			result = getInteraction(block, bMeta);
			world.setBlockState(pos, block.getStateFromMeta(bMeta), 2);
		}
	}

	protected void triggerInteractionEffects(World world, BlockPos pos) {

		world.playSoundEffect(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, "dig.stone", 0.5F,
				0.9F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F);
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		GameRegistry.registerBlock(this, "FluidPetrotheum");

		addInteraction(Blocks.stone, Blocks.gravel);
		addInteraction(Blocks.cobblestone, Blocks.gravel);
		addInteraction(Blocks.stonebrick, Blocks.gravel);
		addInteraction(Blocks.mossy_cobblestone, Blocks.gravel);

		String category = "Fluid.Petrotheum";
		String comment = "Enable this for Fluid Petrotheum to break apart stone blocks.";
		effect = ThermalFoundation.CONFIG.get(category, "Effect", effect, comment);

		comment = "Enable this for Fluid Petrotheum to have an EXTREME effect on stone blocks.";
		extreme = ThermalFoundation.CONFIG.get(category, "Effect.Extreme", extreme, comment);

		comment = "Enable this for Fluid Petrotheum Source blocks to gradually fall downwards.";
		enableSourceFall = ThermalFoundation.CONFIG.get(category, "Fall", enableSourceFall, comment);

		return true;
	}

}
