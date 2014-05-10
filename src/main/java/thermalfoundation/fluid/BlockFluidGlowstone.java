package thermalfoundation.fluid;

import cofh.fluid.BlockFluidCoFHBase;
import cofh.util.ServerHelper;
import cpw.mods.fml.common.registry.GameRegistry;

import java.util.Random;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;

import thermalfoundation.ThermalFoundation;

public class BlockFluidGlowstone extends BlockFluidCoFHBase {

	public static final int LEVELS = 6;
	public static final Material materialFluidGlowstone = new MaterialLiquid(MapColor.yellowColor);

	private static boolean effect = true;
	private static boolean enableSourceCondense = true;
	private static boolean enableSourceFloat = true;
	private static int maxGlowstoneHeight = 120;

	public BlockFluidGlowstone() {

		super("thermalfoundation", TFFluids.fluidGlowstone, materialFluidGlowstone, "glowstone");
		setDensity(-250);
		setQuantaPerBlock(LEVELS);
		setTickRate(20);

		setHardness(1F);
		setLightOpacity(0);
		setParticleColor(1.0F, 0.9F, 0.05F);
	}

	@Override
	public boolean preInit() {

		String category = "tweak";
		String comment = "Enable this for Fluid Glowstone to do...something.";
		effect = ThermalFoundation.config.get(category, "Fluid.Glowstone.Effect", true, comment);

		comment = "Enable this for Fluid Glowstone Source blocks to condense back into solid Glowstone above a given y-value.";
		enableSourceCondense = ThermalFoundation.config.get(category, "Fluid.Glowstone.Condense", enableSourceCondense, comment);

		comment = "Enable this for Fluid Glowstone Source blocks to gradually float upwards.";
		enableSourceFloat = ThermalFoundation.config.get(category, "Fluid.Glowstone.Float", enableSourceFloat, comment);

		int glowstoneHeight = maxGlowstoneHeight;
		comment = "This adjusts the y-value where Fluid Glowstone will *always* condense, if that is enabled. It will also condense above 80% of this value, if it cannot flow.";
		glowstoneHeight = ThermalFoundation.config.get(category, "Fluid.Glowstone.MaxHeight", maxGlowstoneHeight, comment);

		if (glowstoneHeight >= maxGlowstoneHeight) {
			maxGlowstoneHeight = glowstoneHeight;
		} else {
			ThermalFoundation.log.log(Level.INFO, "'Fluid.Glowstone.MaxHeight' config value is out of acceptable range. Using default: " + maxGlowstoneHeight
					+ ".");
		}
		GameRegistry.registerBlock(this, "FluidGlowstone");
		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {

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
	public int getLightValue(IBlockAccess world, int x, int y, int z) {

		return TFFluids.fluidGlowstone.getLuminosity();
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if (world.getBlockMetadata(x, y, z) == 0) {
			if (rand.nextInt(3) == 0) {
				if (shouldSourceBlockCondense(world, x, y, z)) {
					world.setBlock(x, y, z, Blocks.glowstone);
					return;
				}
				if (shouldSourceBlockFloat(world, x, y, z)) {
					world.setBlock(x, y + densityDir, z, this, 0, 3);
					world.setBlockToAir(x, y, z);
					return;
				}
			}
		} else if (y + densityDir > maxGlowstoneHeight) {

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

		return enableSourceCondense
				&& (y + densityDir > maxGlowstoneHeight || y + densityDir > world.getHeight() || y + densityDir > maxGlowstoneHeight * 0.8F
						&& !canDisplace(world, x, y + densityDir, z));
	}

	protected boolean shouldSourceBlockFloat(World world, int x, int y, int z) {

		return enableSourceFloat && (world.getBlock(x, y + densityDir, z) == this && world.getBlockMetadata(x, y + densityDir, z) != 0);
	}

}
