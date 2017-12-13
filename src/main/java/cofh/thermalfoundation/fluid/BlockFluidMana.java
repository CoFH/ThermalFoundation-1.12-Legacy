package cofh.thermalfoundation.fluid;

import cofh.core.fluid.BlockFluidInteractive;
import cofh.core.util.CoreUtils;
import cofh.core.util.helpers.MathHelper;
import cofh.core.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.block.BlockOre;
import cofh.thermalfoundation.block.BlockStorage;
import cofh.thermalfoundation.init.TFBlocks;
import cofh.thermalfoundation.init.TFFluids;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirt.DirtType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Random;

public class BlockFluidMana extends BlockFluidInteractive {

	public static final int LEVELS = 6;
	public static final Material materialFluidMana = new MaterialLiquid(MapColor.PURPLE);

	private static boolean effect = true;
	private static boolean enableSourceFall = true;

	public BlockFluidMana(Fluid fluid) {

		super(fluid, materialFluidMana, "thermalfoundation", "mana");
		setQuantaPerBlock(LEVELS);
		setTickRate(10);

		setHardness(2000F);
		setLightOpacity(2);
		setParticleColor(0.2F, 0.0F, 0.4F);
	}

	public static void config() {

		String category = "Fluid.Mana";
		String comment;

		comment = "If TRUE, Fluid Mana will do...things.";
		effect = ThermalFoundation.CONFIG.getConfiguration().getBoolean("Effect", category, effect, comment);

		comment = "If TRUE, Fluid Mana Source blocks will gradually fall downwards.";
		enableSourceFall = ThermalFoundation.CONFIG.getConfiguration().getBoolean("Fall", category, enableSourceFall, comment);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {

		if (!effect || ServerHelper.isClientWorld(world)) {
			return;
		}
		if (world.getTotalWorldTime() % 4 == 0) {
			if (MathHelper.RANDOM.nextInt(100) != 0) {
				return;
			}
			BlockPos randPos = pos.add(8 + world.rand.nextInt(17), world.rand.nextInt(8), 8 + world.rand.nextInt(17));

			if (!world.getBlockState(randPos).getMaterial().isSolid()) {
				if (entity instanceof EntityLivingBase) {
					CoreUtils.teleportEntityTo(entity, randPos);
				} else {
					entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
					entity.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
				}
			}
		}
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {

		return effect ? 15 : 0;
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {

		return TFFluids.fluidMana.getLuminosity();
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {

		if (effect) {
			checkForInteraction(world, pos);
		}
		if (state.getBlock().getMetaFromState(state) == 0) {
			BlockPos offsetPos = pos.add(0, densityDir, 0);
			IBlockState offsetState = world.getBlockState(offsetPos);
			int bMeta = offsetState.getBlock().getMetaFromState(offsetState);

			if (offsetState.getBlock() == this && bMeta != 0) {
				world.setBlockState(offsetPos, getDefaultState(), 3);
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
		} else if (world.isSideSolid(pos, EnumFacing.UP) && world.isAirBlock(pos.offset(EnumFacing.UP))) {
			if (MathHelper.RANDOM.nextInt(2) == 0) {
				world.setBlockState(pos.offset(EnumFacing.UP), Blocks.SNOW_LAYER.getDefaultState(), 3);
			} else {
				world.setBlockState(pos.offset(EnumFacing.UP), Blocks.FIRE.getDefaultState(), 3);
			}
		}
	}

	protected void triggerInteractionEffects(World world, BlockPos pos) {

		if (MathHelper.RANDOM.nextInt(10) == 0) {
			world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
		}
		for (int i = 0; i < 8; i++) {
			world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, pos.getX() + Math.random() * 1.1, pos.getY() + 1.3D, pos.getZ() + Math.random() * 1.1, 0.0D, -0.5D, 0.0D);
		}
	}

	/* HELPERS */
	public void addInteractions() {

		addInteraction(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.DIRT), Blocks.GRASS.getDefaultState(), false);
		addInteraction(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.COARSE_DIRT), Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.PODZOL), false);
		addInteraction(Blocks.GLASS, Blocks.SAND);
		// addInteraction(Blocks.stained_glass, -1, Blocks.glass);
		// addInteraction(Blocks.diamond_ore, -1, TFBlocks.blockOre, 1);
		// addInteraction(Blocks.cauldron, -1, Blocks.carpet, 1);
		// addInteraction(Blocks.cactus, -1, Blocks.cake, 5);
		// addInteraction(Blocks.enchanting_table, -1, Blocks.brewing_stand, 0);
		// addInteraction(Blocks.bookshelf, 0, Blocks.chest, 0);
		// addInteraction(Blocks.ender_chest, -1, TFBlocks.blockFluidEnder, 1);
		// addInteraction(Blocks.dragon_egg, -1, Blocks.bedrock, 1);
		addInteraction(Blocks.REDSTONE_ORE.getDefaultState(), Blocks.LIT_REDSTONE_ORE.getDefaultState(), true);
		addInteraction(Blocks.LAPIS_ORE.getDefaultState(), Blocks.LAPIS_BLOCK.getDefaultState(), true);
		addInteraction(Blocks.FARMLAND.getDefaultState(), Blocks.MYCELIUM.getDefaultState(), true);
		//		for (int i = 8; i-- > 0; ) {
		//			addInteraction(Blocks.DOUBLE_STONE_SLAB.getStateFromMeta(i), Blocks.DOUBLE_STONE_SLAB.getStateFromMeta(i + 8), false);
		//		}
		addInteraction(TFBlocks.blockOre.getDefaultState().withProperty(BlockOre.VARIANT, BlockOre.Type.SILVER), TFBlocks.blockOre.getDefaultState().withProperty(BlockOre.VARIANT, BlockOre.Type.MITHRIL));
		addInteraction(TFBlocks.blockOre.getDefaultState().withProperty(BlockOre.VARIANT, BlockOre.Type.LEAD), Blocks.GOLD_ORE.getDefaultState());
		addInteraction(TFBlocks.blockStorage.getDefaultState().withProperty(BlockStorage.VARIANT, BlockStorage.Type.SILVER), TFBlocks.blockStorage.getDefaultState().withProperty(BlockStorage.VARIANT, BlockStorage.Type.MITHRIL));
		addInteraction(TFBlocks.blockStorage.getDefaultState().withProperty(BlockStorage.VARIANT, BlockStorage.Type.LEAD), Blocks.GOLD_BLOCK.getDefaultState());
	}

	/* IInitializer */
	@Override
	public boolean initialize() {

		this.setRegistryName("fluid_mana");
		ForgeRegistries.BLOCKS.register(this);
		ItemBlock itemBlock = new ItemBlock(this);
		itemBlock.setRegistryName(this.getRegistryName());
		ForgeRegistries.ITEMS.register(itemBlock);

		config();
		addInteractions();

		return true;
	}

}
