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
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFluidPyrotheum extends BlockFluidInteractive {

	Random random = new Random();

	public static final int LEVELS = 5;
	public static final Material materialFluidPyrotheum = new MaterialLiquid(MapColor.tntColor);

	private static boolean effect = true;
	private static boolean enableSourceFall = true;

	public BlockFluidPyrotheum() {

		super("thermalfoundation", TFFluids.fluidPyrotheum, Material.lava, "pyrotheum");
		setQuantaPerBlock(LEVELS);
		setTickRate(10);

		setHardness(1000F);
		setLightOpacity(1);
		setParticleColor(1.0F, 0.7F, 0.15F);
	}

	@Override
	public boolean preInit() {

		GameRegistry.registerBlock(this, "FluidPyrotheum");

		addInteraction(Blocks.cobblestone, Blocks.stone);
		addInteraction(Blocks.grass, Blocks.dirt);
		addInteraction(Blocks.sand, Blocks.glass);
		addInteraction(Blocks.water, Blocks.stone);
		addInteraction(Blocks.flowing_water, Blocks.stone);
		addInteraction(Blocks.clay, Blocks.hardened_clay);
		addInteraction(Blocks.ice, Blocks.stone);
		addInteraction(Blocks.snow, Blocks.air);
		addInteraction(Blocks.snow_layer, Blocks.air);

		for (int i = 0; i < 8; i++) {
			addInteraction(Blocks.stone_stairs, i, Blocks.stone_brick_stairs, i);
		}

		String category = "Fluid.Pyrotheum";
		String comment = "Enable this for Fluid Pyrotheum to be worse than lava.";
		effect = ThermalFoundation.config.get(category, "Effect", true, comment);

		comment = "Enable this for Fluid Pyrotheum Source blocks to gradually fall downwards.";
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
		if (entity instanceof EntityPlayer) {

		} else if (entity instanceof EntityCreeper) {
			world.createExplosion(entity, entity.posX, entity.posY, entity.posZ, 6.0F, entity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
			entity.setDead();
		}
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {

		return TFFluids.fluidPyrotheum.getLuminosity();
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {

		return effect ? 800 : 0;
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {

		return 0;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {

		return effect && face.ordinal() > ForgeDirection.UP.ordinal() && world.getBlock(x, y - 1, z) != this;
	}

	@Override
	public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side) {

		return effect;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if (effect) {
			checkForInteraction(world, x, y, z);
		}
		if (world.getBlockMetadata(x, y, z) == 0) {
			Block block = world.getBlock(x, y + densityDir, z);
			int bMeta = world.getBlockMetadata(x, y + densityDir, z);

			if (block == this && bMeta != 0 || block.isFlammable(world, x, y + densityDir, z, ForgeDirection.UP)) {
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
		} else if (block.isFlammable(world, x, y, z, ForgeDirection.UP)) {
			world.setBlock(x, y, z, Blocks.fire);
		} else if (world.isSideSolid(x, y, z, ForgeDirection.UP) && world.isAirBlock(x, y + 1, z)) {
			world.setBlock(x, y + 1, z, Blocks.fire, 0, 3);
		}
	}

	protected void triggerInteractionEffects(World world, int x, int y, int z) {

		if (random.nextInt(20) == 0) {
			world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
		}
		for (int i = 0; i < 4; i++) {
			world.spawnParticle("largesmoke", x + random.nextDouble(), y + 1.2D, z + random.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}

}
