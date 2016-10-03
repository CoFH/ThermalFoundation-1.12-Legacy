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
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockFluidPetrotheum extends BlockFluidInteractive {

	public static final int LEVELS = 6;
	public static final Material materialFluidPetrotheum = new MaterialLiquid(MapColor.STONE);

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
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.HASTE, 15 * 20, 2));
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 15 * 20, 0));
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 15 * 20, 1));
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

		if (block.isAir(state, world, pos) || block == this) {
			return;
		}
		int bMeta = block.getMetaFromState(state);
		BlockWrapper result;

		if (extreme && state.getMaterial() == Material.ROCK && block.getBlockHardness(state, world, pos) > 0) {
			block.dropBlockAsItem(world, pos, state, 0);
			world.setBlockToAir(pos);
			triggerInteractionEffects(world, pos);
		} else if (hasInteraction(block, bMeta)) {
			result = getInteraction(block, bMeta);
			world.setBlockState(pos, block.getStateFromMeta(bMeta), 2);
		}
	}

	protected void triggerInteractionEffects(World world, BlockPos pos) {

		world.playSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 0.5F,
				0.9F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F, false);
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		GameRegistry.register(this, new ResourceLocation(ThermalFoundation.modId, "FluidPetrotheum"));

		addInteraction(Blocks.STONE, Blocks.GRAVEL);
		addInteraction(Blocks.COBBLESTONE, Blocks.GRAVEL);
		addInteraction(Blocks.STONEBRICK, Blocks.GRAVEL);
		addInteraction(Blocks.MOSSY_COBBLESTONE, Blocks.GRAVEL);

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
