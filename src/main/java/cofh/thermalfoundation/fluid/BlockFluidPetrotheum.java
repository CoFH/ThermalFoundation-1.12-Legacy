package cofh.thermalfoundation.fluid;

import cofh.core.fluid.BlockFluidInteractive;
import cofh.lib.util.BlockWrapper;
import cofh.lib.util.helpers.BlockHelper;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cpw.mods.fml.common.registry.GameRegistry;

import java.util.Random;

import net.minecraft.block.Block;
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

public class BlockFluidPetrotheum extends BlockFluidInteractive {

	Random random = new Random();

	public static final int LEVELS = 6;
	public static final Material materialFluidPetrotheum = new MaterialLiquid(MapColor.stoneColor);

	private static boolean enableSourceFall = true;
	private static boolean effect = true;

	public BlockFluidPetrotheum() {

		super("thermalfoundation", TFFluids.fluidPetrotheum, materialFluidPetrotheum, "petrotheum");
		setQuantaPerBlock(LEVELS);
		setTickRate(10);

		setHardness(1000F);
		setLightOpacity(1);
		setParticleColor(0.4F, 0.3F, 0.2F);
	}

	@Override
	public boolean preInit() {

		GameRegistry.registerBlock(this, "FluidPetrotheum");

		// TODO: add interactions

		String category = "Fluid.Petrotheum";
		String comment = "Enable this for Fluid Petrotheum to make things more in tune with the earth.";
		effect = ThermalFoundation.config.get(category, "Effect", true, comment);

		comment = "Enable this for Fluid Petrotheum Source blocks to gradually fall downwards.";
		enableSourceFall = ThermalFoundation.config.get(category, "Fall", enableSourceFall, comment);

		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {

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
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.digSpeed.id, 30 * 20, 2));
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.nightVision.id, 30 * 20, 0));
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 1));
		}
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {

		return TFFluids.fluidPetrotheum.getLuminosity();
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if (effect) {
			checkForInteraction(world, x, y, z);
		}
		if (enableSourceFall && world.getBlockMetadata(x, y, z) == 0) {
			Block block = world.getBlock(x, y + densityDir, z);
			int bMeta = world.getBlockMetadata(x, y + densityDir, z);

			if (block == this && bMeta != 0) {
				world.setBlock(x, y + densityDir, z, this, 0, 3);
				world.setBlockToAir(x, y, z);
				return;
			}
		}
		super.updateTick(world, x, y, z, rand);
	}

	protected void checkForInteraction(World world, int x, int y, int z) {

		if (world.getBlock(x, y, z) != this) {
			return;
		}
		int x2 = x;
		int y2 = y;
		int z2 = z;

		for (int i = 2; i < 6; i++) {
			x2 = x + BlockHelper.SIDE_COORD_MOD[i][0];
			y2 = y + BlockHelper.SIDE_COORD_MOD[i][1];
			z2 = z + BlockHelper.SIDE_COORD_MOD[i][2];
			interactWithBlock(world, x2, y2, z2);
		}
	}

	protected void interactWithBlock(World world, int x, int y, int z) {

		Block block = world.getBlock(x, y, z);

		if (block == Blocks.air || block == this) {
			return;
		}
		int bMeta = world.getBlockMetadata(x, y, z);
		if (block.getMaterial() == Material.rock) {
			block.dropBlockAsItem(world, x, y, z, bMeta, 0);
			world.setBlockToAir(x, y, z);
			triggerInteractionEffects(world, x, y, z);
		} else if (hasInteraction(block, bMeta)) {
			BlockWrapper result = getInteraction(block, bMeta);
			world.setBlock(x, y, z, result.block, result.metadata, 3);
		}
	}

	protected void triggerInteractionEffects(World world, int x, int y, int z) {

		world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, "dig.stone", 0.5F, 0.9F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F);
	}

}
