package thermalfoundation.fluid;

import cofh.fluid.BlockFluidInteractive;
import cofh.util.BlockHelper;
import cofh.util.BlockWrapper;
import cofh.util.CoreUtils;
import cofh.util.MathHelper;
import cpw.mods.fml.common.registry.GameRegistry;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import thermalfoundation.ThermalFoundation;
import thermalfoundation.block.TFBlocks;

public class BlockFluidMana extends BlockFluidInteractive {

	public static final int LEVELS = 6;
	public static final Material materialFluidMana = new MaterialLiquid(MapColor.purpleColor);

	private static boolean effect = true;

	public BlockFluidMana() {

		super("thermalfoundation", TFFluids.fluidMana, materialFluidMana, "mana");
		setQuantaPerBlock(LEVELS);
		setTickRate(20);

		setHardness(2000F);
		setLightOpacity(2);
		setParticleColor(0.2F, 0.0F, 0.4F);

		addInteraction(Blocks.dirt, 0, Blocks.grass);
		addInteraction(Blocks.dirt, 1, Blocks.dirt, 2);
		addInteraction(Blocks.glass, Blocks.sand);
		addInteraction(TFBlocks.blockOre, 2, TFBlocks.blockOre, 6);
		addInteraction(TFBlocks.blockOre, 3, Blocks.gold_ore);
		addInteraction(TFBlocks.blockStorage, 2, TFBlocks.blockStorage, 6);
		addInteraction(TFBlocks.blockStorage, 3, Blocks.gold_block);
	}

	@Override
	public boolean preInit() {

		String category = "tweak";
		String comment = "Enable this for Fluid Mana to do...things.";
		effect = ThermalFoundation.config.get(category, "Fluid.Mana.Effect", true, comment);

		GameRegistry.registerBlock(this, "FluidMana");
		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {

		if (!effect) {
			return;
		}
		if (MathHelper.RANDOM.nextInt(100) != 0) {
			return;
		}
		if (world.getTotalWorldTime() % 4 == 0) {
			int x2 = x - 8 + world.rand.nextInt(17);
			int y2 = y + world.rand.nextInt(8);
			int z2 = z - 8 + world.rand.nextInt(17);

			if (!world.getBlock(x2, y2, z2).getMaterial().isSolid()) {
				if (entity instanceof EntityLivingBase) {
					CoreUtils.teleportEntityTo((EntityLivingBase) entity, x2, y2, z2);
				} else {
					entity.setPosition(x2, y2, z2);
					entity.worldObj.playSoundEffect(x2, y2, z2, "mob.endermen.portal", 1.0F, 1.0F);
					entity.playSound("mob.endermen.portal", 1.0F, 1.0F);
				}
			}
		}
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {

		return effect ? MathHelper.RANDOM.nextInt(15) : 0;
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {

		return TFFluids.fluidMana.getLuminosity();
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if (effect) {
			checkForInteraction(world, x, y, z);
		}
		if (world.getBlockMetadata(x, y, z) == 0) {
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
		int result = -1;

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
		} else if (world.isSideSolid(x, y, z, ForgeDirection.UP) && world.isAirBlock(x, y + 1, z)) {
			// world.setBlock(x, y + 1, z, Blocks.snow_layer, 0, 3);
		}
	}

	protected void triggerInteractionEffects(World world, int x, int y, int z) {

		if (MathHelper.RANDOM.nextInt(10) == 0) {
			world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, "random.orb", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
		}
		for (int i = 0; i < 4; i++) {
			world.spawnParticle("enchantmenttable", x + Math.random(), y + 1.2D, z + Math.random(), 0.0D, 0.0D, 0.0D);
		}
	}

}
