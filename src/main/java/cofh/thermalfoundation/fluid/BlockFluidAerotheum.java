package cofh.thermalfoundation.fluid;

import cofh.core.fluid.BlockFluidCoFHBase;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cpw.mods.fml.common.registry.GameRegistry;

import java.util.Random;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;

public class BlockFluidAerotheum extends BlockFluidCoFHBase {

	Random random = new Random();

	public static final int LEVELS = 6;
	public static final Material materialFluidAerotheum = new MaterialLiquid(MapColor.airColor);

	private static boolean effect = true;
	private static boolean enableSourceDissipate = true;
	private static boolean enableSourceFloat = true;
	private static int maxHeight = 120;

	public BlockFluidAerotheum() {

		super("thermalfoundation", TFFluids.fluidAerotheum, materialFluidAerotheum, "aerotheum");
		setQuantaPerBlock(LEVELS);
		setTickRate(8);

		setHardness(1F);
		setLightOpacity(0);
		setParticleColor(0.9F, 0.9F, 0.9F);
	}

	@Override
	public boolean preInit() {

		GameRegistry.registerBlock(this, "FluidAerotheum");

		String category = "Fluid.Aerotheum";
		String comment = "Enable this for Fluid Aerotheum to do...things.";
		effect = ThermalFoundation.config.get(category, "Effect", true, comment);

		comment = "Enable this for Fluid Aerotheum Source blocks to dissipate back into air above a given y-value.";
		enableSourceDissipate = ThermalFoundation.config.get(category, "Dissipate", enableSourceDissipate, comment);

		comment = "Enable this for Fluid Aerotheum Source blocks to gradually float upwards.";
		enableSourceFloat = ThermalFoundation.config.get(category, "Float", enableSourceFloat, comment);

		int cfgHeight;
		comment = "This adjusts the y-value where Fluid Aerotheum will *always* dissipate, if that is enabled.";
		cfgHeight = ThermalFoundation.config.get(category, "MaxHeight", maxHeight, comment);

		if (cfgHeight >= maxHeight / 2) {
			maxHeight = cfgHeight;
		} else {
			ThermalFoundation.log.log(Level.INFO, "'Fluid.Aerotheum.MaxHeight' config value is out of acceptable range. Using default: " + maxHeight + ".");
		}
		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {

		if (!effect) {
			return;
		}
		if (entity instanceof EntityLivingBase) {
			if (entity.motionX > 0.1) {
				entity.motionX = 0.1;
			}
			if (entity.motionZ > 0.1) {
				entity.motionZ = 0.1;
			}
			if (entity.motionY < -0.2) {
				entity.motionY *= 0.5;
				if (entity.fallDistance > 20) {
					entity.fallDistance = 20;
				} else {
					entity.fallDistance *= 0.5;
				}
			}
		} else if (entity instanceof IProjectile) {
			entity.motionX *= random.nextGaussian() * 1.5;
			entity.motionZ *= random.nextGaussian() * 1.5;
		}
		if (ServerHelper.isClientWorld(world)) {
			return;
		}
		if (world.getTotalWorldTime() % 8 == 0 && entity instanceof EntityLivingBase && !((EntityLivingBase) entity).isEntityUndead()) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.invisibility.id, 3 * 20, 0));
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 30 * 20, 0));
		}
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {

		return TFFluids.fluidAerotheum.getLuminosity();
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if (world.getBlockMetadata(x, y, z) == 0) {
			if (rand.nextInt(3) == 0) {
				if (shouldSourceBlockCondense(world, x, y, z)) {
					world.setBlockToAir(x, y, z);
					return;
				}
				if (shouldSourceBlockFloat(world, x, y, z)) {
					world.setBlock(x, y + densityDir, z, this, 0, 3);
					world.setBlockToAir(x, y, z);
					return;
				}
			}
		} else if (y + densityDir > maxHeight) {

			int quantaRemaining = quantaPerBlock - world.getBlockMetadata(x, y, z);
			int expQuanta = -101;
			int y2 = y - densityDir;

			if (world.getBlock(x, y2, z) == this || world.getBlock(x - 1, y2, z) == this || world.getBlock(x + 1, y2, z) == this
					|| world.getBlock(x, y2, z - 1) == this || world.getBlock(x, y2, z + 1) == this) {
				expQuanta = quantaPerBlock - 1;

			} else {
				int maxQuanta = -100;
				maxQuanta = getLargerQuanta(world, x - 1, y, z, maxQuanta);
				maxQuanta = getLargerQuanta(world, x + 1, y, z, maxQuanta);
				maxQuanta = getLargerQuanta(world, x, y, z - 1, maxQuanta);
				maxQuanta = getLargerQuanta(world, x, y, z + 1, maxQuanta);

				expQuanta = maxQuanta - 1;
			}
			// decay calculation
			if (expQuanta != quantaRemaining) {
				quantaRemaining = expQuanta;
				if (expQuanta <= 0) {
					world.setBlockToAir(x, y, z);
				} else {
					world.setBlockMetadataWithNotify(x, y, z, quantaPerBlock - expQuanta, 3);
					world.scheduleBlockUpdate(x, y, z, this, tickRate);
					world.notifyBlocksOfNeighborChange(x, y, z, this);
				}
			}
			return;
		}
		super.updateTick(world, x, y, z, rand);
	}

	protected boolean shouldSourceBlockCondense(World world, int x, int y, int z) {

		return enableSourceDissipate && (y + densityDir > maxHeight || y + densityDir > world.getHeight());
	}

	protected boolean shouldSourceBlockFloat(World world, int x, int y, int z) {

		return enableSourceFloat && (world.getBlock(x, y + densityDir, z) == this && world.getBlockMetadata(x, y + densityDir, z) != 0);
	}

}
