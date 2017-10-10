package cofh.thermalfoundation.fluid;

import cofh.core.fluid.BlockFluidInteractive;
import cofh.core.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.init.TFFluids;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Random;

public class BlockFluidPyrotheum extends BlockFluidInteractive {

	public static final int LEVELS = 5;
	public static final Material materialFluidPyrotheum = new MaterialLiquid(MapColor.TNT);

	private static boolean effect = true;
	private static boolean enableSourceFall = true;

	public BlockFluidPyrotheum(Fluid fluid) {

		super(fluid, Material.LAVA, "thermalfoundation", "pyrotheum");
		setQuantaPerBlock(LEVELS);
		setTickRate(10);

		setHardness(1000F);
		setLightOpacity(1);
		setParticleColor(1.0F, 0.7F, 0.15F);
	}

	public static void config() {

		String category = "Fluid.Pyrotheum";
		String comment;

		comment = "If TRUE, Fluid Pyrotheum will be worse than lava.";
		effect = ThermalFoundation.CONFIG.getConfiguration().getBoolean("Effect", category, effect, comment);

		comment = "If TRUE, Fluid Pyrotheum Source blocks will gradually fall downwards.";
		enableSourceFall = ThermalFoundation.CONFIG.getConfiguration().getBoolean("Fall", category, enableSourceFall, comment);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {

		if (!effect) {
			return;
		}
		if (ServerHelper.isClientWorld(world)) {
			return;
		}
		if (entity instanceof EntityCreeper) {
			world.createExplosion(entity, entity.posX, entity.posY, entity.posZ, 6.0F, entity.world.getGameRules().getBoolean("mobGriefing"));
			entity.setDead();
		}
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {

		return TFFluids.fluidPyrotheum.getLuminosity();
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return effect ? 800 : 0;
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return 0;
	}

	@Override
	public Boolean isEntityInsideMaterial(IBlockAccess world, BlockPos blockpos, IBlockState iblockstate, Entity entity, double yToTest, Material materialIn, boolean testingHead) {

		if (materialIn != this.blockMaterial) {
			return null;
		}
		return super.isEntityInsideMaterial(world, blockpos, iblockstate, entity, yToTest, materialIn, testingHead);
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return effect && face.ordinal() > EnumFacing.UP.ordinal() && world.getBlockState(pos.add(0, -1, 0)).getBlock() != this;
	}

	@Override
	public boolean isFireSource(World world, BlockPos pos, EnumFacing face) {

		return effect;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {

		if (effect) {
			checkForInteraction(world, pos);
		}
		if (enableSourceFall && state.getBlock().getMetaFromState(state) == 0) {
			BlockPos offsetPos = pos.add(0, densityDir, 0);
			IBlockState offsetState = world.getBlockState(offsetPos);
			int bMeta = offsetState.getBlock().getMetaFromState(offsetState);

			if (offsetState.getBlock() == this && bMeta != 0 || offsetState.getBlock().isFlammable(world, offsetPos, EnumFacing.UP)) {
				world.setBlockState(offsetPos, this.getDefaultState(), 3);
				world.setBlockToAir(pos);
				return;
			}
		}
		super.updateTick(world, pos, state, rand);
	}

	protected void interactWithBlock(World world, BlockPos pos) {

		IBlockState state = world.getBlockState(pos);

		if (state.getBlock().isAir(state, world, pos) || state.getBlock() == this) {
			return;
		}
		if (hasInteraction(state)) {
			IBlockState result = getInteraction(state);
			world.setBlockState(pos, result, 3);
			triggerInteractionEffects(world, pos);
		} else if (state.getBlock().isFlammable(world, pos, EnumFacing.UP)) {
			world.setBlockState(pos, Blocks.FIRE.getDefaultState());
		} else if (state.isSideSolid(world, pos, EnumFacing.UP) && world.isAirBlock(pos.offset(EnumFacing.UP))) {
			world.setBlockState(pos.offset(EnumFacing.UP), Blocks.FIRE.getDefaultState(), 3);
		}
	}

	protected void triggerInteractionEffects(World world, BlockPos pos) {

		if (world.rand.nextInt(16) == 0) {
			world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_GENERIC_BURN, SoundCategory.BLOCKS, 0.5F, 2.2F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F, false);
		}
	}

	/* HELPERS */
	public void addInteractions() {

		addInteraction(Blocks.COBBLESTONE, Blocks.STONE);
		addInteraction(Blocks.GRASS, Blocks.DIRT);
		addInteraction(Blocks.SAND, Blocks.GLASS);
		addInteraction(Blocks.WATER, Blocks.STONE);
		addInteraction(Blocks.FLOWING_WATER, Blocks.STONE);
		addInteraction(Blocks.CLAY, Blocks.HARDENED_CLAY);
		addInteraction(Blocks.ICE, Blocks.STONE);
		addInteraction(Blocks.SNOW, Blocks.AIR);
		addInteraction(Blocks.SNOW_LAYER, Blocks.AIR);

		for (int i = 0; i < 8; i++) {
			addInteraction(Blocks.STONE_STAIRS.getStateFromMeta(i), Blocks.STONE_BRICK_STAIRS.getStateFromMeta(i), false);
		}
	}

	/* IInitializer */
	@Override
	public boolean initialize() {

		this.setRegistryName("fluid_pyrotheum");
		ForgeRegistries.BLOCKS.register(this);
		ItemBlock itemBlock = new ItemBlock(this);
		itemBlock.setRegistryName(this.getRegistryName());
		ForgeRegistries.ITEMS.register(itemBlock);

		config();
		addInteractions();

		return true;
	}
}
