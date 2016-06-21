package cofh.thermalfoundation.fluid;

import cofh.core.fluid.BlockFluidCoFHBase;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;

import java.util.Random;

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

import org.apache.logging.log4j.Level;

public class BlockFluidGlowstone extends BlockFluidCoFHBase {

	public static final int LEVELS = 6;
	public static final Material materialFluidGlowstone = new MaterialLiquid(MapColor.yellowColor);

	private static boolean effect = true;
	private static boolean enableSourceCondense = true;
	private static boolean enableSourceFloat = true;
	private static int maxHeight = 120;

	public BlockFluidGlowstone(Fluid fluid) {

		super(fluid, materialFluidGlowstone, "thermalfoundation", "glowstone");
		setQuantaPerBlock(LEVELS);
		setTickRate(10);

		setHardness(1F);
		setLightOpacity(0);
		setParticleColor(1.0F, 0.9F, 0.05F);
	}

	protected boolean shouldSourceBlockCondense(World world, BlockPos pos) {

		int y = pos.getY();
		return enableSourceCondense
				&& (y + densityDir > maxHeight || y + densityDir > world.getHeight() || y + densityDir > maxHeight * 0.8F
						&& !canDisplace(world, pos.add(0, densityDir, 0)));
	}

	protected boolean shouldSourceBlockFloat(World world, BlockPos pos) {

		IBlockState state = world.getBlockState(pos.add(0, densityDir, 0));
		return enableSourceFloat && state.getBlock() == this && getMetaFromState(state) != 0;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {

		if (!effect) {
			return;
		}
		if (entity instanceof EntityLivingBase) {
			if (entity.motionY < -0.2) {
				entity.motionY *= 0.5;
				if (entity.fallDistance > 20) {
					entity.fallDistance = 20;
				} else {
					entity.fallDistance *= 0.95;
				}
			}
		}
		if (ServerHelper.isClientWorld(world)) {
			return;
		}
		if (world.getTotalWorldTime() % 8 == 0 && entity instanceof EntityLivingBase && !((EntityLivingBase) entity).isEntityUndead()) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 6 * 20, 0));
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.jump.id, 6 * 20, 0));
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {

		if (getMetaFromState(state) == 0) {
			if (rand.nextInt(3) == 0) {
				if (shouldSourceBlockCondense(world, pos)) {
					world.setBlockState(pos, Blocks.glowstone.getDefaultState(), 2);
					return;
				}
				if (shouldSourceBlockFloat(world, pos)) {
					world.setBlockState(pos.add(0, densityDir, 0), getDefaultState(), 2);
					world.setBlockToAir(pos);
					return;
				}
			}
		} else if (pos.getY() + densityDir > maxHeight) {

			int quantaRemaining = quantaPerBlock - state.getValue(LEVEL).intValue();
			int expQuanta = -101;

			//@formatter:off
			if (	world.getBlockState(pos.add( 0, -densityDir,  0)).getBlock() == this ||
					world.getBlockState(pos.add(-1, -densityDir,  0)).getBlock() == this ||
					world.getBlockState(pos.add( 1, -densityDir,  0)).getBlock() == this ||
					world.getBlockState(pos.add( 0, -densityDir, -1)).getBlock() == this ||
					world.getBlockState(pos.add( 0, -densityDir,  1)).getBlock() == this) {
				expQuanta = quantaPerBlock - 1;
				//@formatter:on
			} else {
				int maxQuanta = -100;
				maxQuanta = getLargerQuanta(world, pos.add(-1, 0, 0), maxQuanta);
				maxQuanta = getLargerQuanta(world, pos.add(1, 0, 0), maxQuanta);
				maxQuanta = getLargerQuanta(world, pos.add(0, 0, -1), maxQuanta);
				maxQuanta = getLargerQuanta(world, pos.add(0, 0, 1), maxQuanta);

				expQuanta = maxQuanta - 1;
			}
			// decay calculation
			if (expQuanta != quantaRemaining) {
				quantaRemaining = expQuanta;

				if (expQuanta <= 0) {
					world.setBlockToAir(pos);
				} else {
					world.setBlockState(pos, state.withProperty(LEVEL, quantaPerBlock - expQuanta), 2);
					world.scheduleUpdate(pos, this, tickRate);
					world.notifyNeighborsOfStateChange(pos, this);
				}
			}
			return;
		}
		super.updateTick(world, pos, state, rand);
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		GameRegistry.registerBlock(this, "FluidGlowstone");

		String category = "Fluid.Glowstone";
		String comment = "Enable this for Fluid Glowstone to do...something.";
		effect = ThermalFoundation.CONFIG.get(category, "Effect", true, comment);

		comment = "Enable this for Fluid Glowstone Source blocks to condense back into solid Glowstone above a given y-value.";
		enableSourceCondense = ThermalFoundation.CONFIG.get(category, "Condense", enableSourceCondense, comment);

		comment = "Enable this for Fluid Glowstone Source blocks to gradually float upwards.";
		enableSourceFloat = ThermalFoundation.CONFIG.get(category, "Float", enableSourceFloat, comment);

		int cfgHeight;
		comment = "This adjusts the y-value where Fluid Glowstone will *always* condense, if that is enabled. It will also condense above 80% of this value, if it cannot flow.";
		cfgHeight = ThermalFoundation.CONFIG.get(category, "MaxHeight", maxHeight, comment);

		if (cfgHeight >= maxHeight / 2) {
			maxHeight = cfgHeight;
		} else {
			ThermalFoundation.LOG.log(Level.INFO, "'Fluid.Glowstone.MaxHeight' config value is out of acceptable range. Using default: " + maxHeight + ".");
		}
		return true;
	}

}
