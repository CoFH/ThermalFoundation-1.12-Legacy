package cofh.thermalfoundation.fluid;

import cofh.core.fluid.BlockFluidInteractive;
import cofh.lib.util.BlockWrapper;
import cofh.lib.util.helpers.DamageHelper;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.entity.monster.EntityBlizz;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockFluidCryotheum extends BlockFluidInteractive {

	public static final int LEVELS = 5;
	public static final Material materialFluidCryotheum = new MaterialLiquid(MapColor.iceColor);

	private static boolean effect = true;
	private static boolean enableSourceFall = true;

	public BlockFluidCryotheum(Fluid fluid) {

		super(fluid, materialFluidCryotheum, "thermalfoundation", "cryotheum");
		setQuantaPerBlock(LEVELS);
		setTickRate(15);

		setHardness(1000F);
		setLightOpacity(1);
		setParticleColor(0.15F, 0.7F, 1.0F);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {

		entity.extinguish();

		if (!effect) {
			return;
		}
		if (entity.motionY < -0.05 || entity.motionY > 0.05) {
			entity.motionY *= 0.05;
		}
		if (entity.motionZ < -0.05 || entity.motionZ > 0.05) {
			entity.motionZ *= 0.05;
		}
		if (entity.motionX < -0.05 || entity.motionX > 0.05) {
			entity.motionX *= 0.05;
		}
		if (ServerHelper.isClientWorld(world)) {
			return;
		}
		if (world.getTotalWorldTime() % 8 != 0) {
			return;
		}
		if (entity instanceof EntityZombie || entity instanceof EntityCreeper) {
			EntitySnowman snowman = new EntitySnowman(world);
			snowman.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
			world.spawnEntityInWorld(snowman);
			entity.setDead();
		} else if (entity instanceof EntityBlizz || entity instanceof EntitySnowman) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 6 * 20, 0));
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.regeneration.id, 6 * 20, 0));
		} else if (entity instanceof EntityBlaze) {
			entity.attackEntityFrom(DamageHelper.cryotheum, 10.0F);
		} else {
			boolean t = entity.velocityChanged;
			entity.attackEntityFrom(DamageHelper.cryotheum, 2.0F);
			entity.velocityChanged = t;
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
		interactWithBlock(world, pos.add(0, -1, 0));
		interactWithBlock(world, pos.add(0, 1, 0));
		interactWithBlock(world, pos.add(-1, 0, 0));
		interactWithBlock(world, pos.add(1, 0, 0));
		interactWithBlock(world, pos.add(0, 0, -1));
		interactWithBlock(world, pos.add(0, 0, 1));

		interactWithBlock(world, pos.add(-1, 0, -1));
		interactWithBlock(world, pos.add(-1, 0, 1));
		interactWithBlock(world, pos.add(1, 0, -1));
		interactWithBlock(world, pos.add(1, 0, 1));
	}

	protected void interactWithBlock(World world, BlockPos pos) {

		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		if (block.isAir(world, pos) || block == this) {
			return;
		}
		int bMeta = block.getMetaFromState(state);
		BlockWrapper result;

		if (hasInteraction(block, bMeta)) {
			result = getInteraction(block, bMeta);
			world.setBlockState(pos, block.getStateFromMeta(bMeta), 2);
			triggerInteractionEffects(world, pos);
		} else if (world.isSideSolid(pos, EnumFacing.UP) && world.isAirBlock(pos.add(0, 1, 0))) {
			world.setBlockState(pos.add(0, 1, 0), Blocks.snow_layer.getDefaultState(), 2);
		}
	}

	protected void triggerInteractionEffects(World world, BlockPos pos) {

	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		GameRegistry.registerBlock(this, "FluidCryotheum");

		addInteraction(Blocks.grass, Blocks.dirt);
		addInteraction(Blocks.water, 0, Blocks.ice);
		addInteraction(Blocks.water, Blocks.snow);
		addInteraction(Blocks.flowing_water, 0, Blocks.ice);
		addInteraction(Blocks.flowing_water, Blocks.snow);
		addInteraction(Blocks.lava, 0, Blocks.obsidian);
		addInteraction(Blocks.lava, Blocks.stone);
		addInteraction(Blocks.flowing_lava, 0, Blocks.obsidian);
		addInteraction(Blocks.flowing_lava, Blocks.stone);
		addInteraction(Blocks.leaves, Blocks.air);
		addInteraction(Blocks.tallgrass, Blocks.air);
		addInteraction(Blocks.fire, Blocks.air);
		addInteraction(TFFluids.blockFluidGlowstone, 0, Blocks.glowstone);

		String category = "Fluid.Cryotheum";
		String comment = "Enable this for Fluid Cryotheum to be worse than lava, except cold.";
		effect = ThermalFoundation.CONFIG.get(category, "Effect", true, comment);

		comment = "Enable this for Fluid Cryotheum Source blocks to gradually fall downwards.";
		enableSourceFall = ThermalFoundation.CONFIG.get(category, "Fall", enableSourceFall, comment);

		return true;
	}

}
