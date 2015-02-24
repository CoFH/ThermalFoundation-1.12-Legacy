package cofh.thermalfoundation.fluid;

import cofh.core.fluid.BlockFluidInteractive;
import cofh.lib.util.BlockWrapper;
import cofh.lib.util.helpers.BlockHelper;
import cofh.lib.util.helpers.DamageHelper;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.block.TFBlocks;
import cofh.thermalfoundation.entity.monster.EntityBlizz;
import cpw.mods.fml.common.registry.GameRegistry;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFluidCryotheum extends BlockFluidInteractive {

	Random random = new Random();

	public static final int LEVELS = 5;
	public static final Material materialFluidCryotheum = new MaterialLiquid(MapColor.iceColor);

	private static boolean enableSourceFall = true;
	private static boolean effect = true;

	public BlockFluidCryotheum() {

		super("thermalfoundation", TFFluids.fluidCryotheum, materialFluidCryotheum, "cryotheum");
		setQuantaPerBlock(LEVELS);
		setTickRate(30);

		setHardness(1000F);
		setLightOpacity(1);
		setParticleColor(0.15F, 0.7F, 1.0F);
	}

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
		addInteraction(TFBlocks.blockFluidGlowstone, 0, Blocks.glowstone);

		String category = "Fluid.Cryotheum";
		String comment = "Enable this for Fluid Cryotheum to be worse than lava, except cold.";
		effect = ThermalFoundation.config.get(category, "Effect", true, comment);

		comment = "Enable this for Fluid Cryotheum Source blocks to gradually fall downwards.";
		enableSourceFall = ThermalFoundation.config.get(category, "Fall", enableSourceFall, comment);

		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {

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
			entity.setDead();

			EntitySnowman snowman = new EntitySnowman(world);
			snowman.setLocationAndAngles(x + 0.5D, y + 1.0D, z + 0.5D, 0.0F, 0.0F);
			world.spawnEntityInWorld(snowman);
		} else if (entity instanceof EntityBlizz) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 6 * 20, 0));
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.regeneration.id, 6 * 20, 0));
		} else if (entity instanceof EntityBlaze) {
			entity.attackEntityFrom(DamageHelper.cryotheum, 10F);
		} else {
			entity.attackEntityFrom(DamageHelper.cryotheum, 2.0F);
		}
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {

		return TFFluids.fluidCryotheum.getLuminosity();
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

		for (int i = 0; i < 6; i++) {
			x2 = x + BlockHelper.SIDE_COORD_MOD[i][0];
			y2 = y + BlockHelper.SIDE_COORD_MOD[i][1];
			z2 = z + BlockHelper.SIDE_COORD_MOD[i][2];

			interactWithBlock(world, x2, y2, z2);

			x2 += BlockHelper.SIDE_COORD_MOD[i][0];
			z2 += BlockHelper.SIDE_COORD_MOD[i][2];

			interactWithBlock(world, x2, y2, z2);
		}
		interactWithBlock(world, x - 1, y, z - 1);
		interactWithBlock(world, x - 1, y, z + 1);
		interactWithBlock(world, x + 1, y, z - 1);
		interactWithBlock(world, x + 1, y, z + 1);
	}

	protected void interactWithBlock(World world, int x, int y, int z) {

		Block block = world.getBlock(x, y, z);

		if (block == Blocks.air || block == this) {
			return;
		}
		int bMeta = world.getBlockMetadata(x, y, z);
		BlockWrapper result;

		if (hasInteraction(block, bMeta)) {
			result = getInteraction(block, bMeta);
			world.setBlock(x, y, z, result.block, result.metadata, 3);
			triggerInteractionEffects(world, x, y, z);
		} else if (world.isSideSolid(x, y, z, ForgeDirection.UP) && world.isAirBlock(x, y + 1, z)) {
			world.setBlock(x, y + 1, z, Blocks.snow_layer, 0, 3);
		}
	}

	protected void triggerInteractionEffects(World world, int x, int y, int z) {

		// if (random.nextInt(20) == 0) {
		// world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
		// }
		for (int i = 0; i < 4; i++) {
			world.spawnParticle("snowballpoof", x + random.nextDouble(), y + 1.2D, z + random.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}

}
