package cofh.thermalfoundation.block;

import cofh.api.core.IInitializer;
import cofh.api.core.IModelRegister;
import cofh.core.block.BlockCoFHBase;
import cofh.thermalfoundation.ThermalFoundation;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFlower extends BlockCoFHBase implements IInitializer, IModelRegister, IPlantable {

	public static final PropertyEnum<BlockFlower.Type> VARIANT = PropertyEnum.<BlockFlower.Type> create("type", BlockFlower.Type.class);

	public BlockFlower() {

		super(Material.plants, "thermalfoundation");

		setUnlocalizedName("flower");
		setCreativeTab(ThermalFoundation.tabCommon);

		setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, Type.COAL));

		setTickRandomly(true);
		float f = 0.2F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);

	}

	@Override
	protected BlockState createBlockState() {

		return new BlockState(this, new IProperty[] { VARIANT });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {

		for (int i = 0; i < BlockFlower.Type.METADATA_LOOKUP.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public int getDamageValue(World world, BlockPos pos) {

		IBlockState state = world.getBlockState(pos);
		return state.getBlock() != this ? 0 : state.getValue(VARIANT).getMetadata();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {

		return this.getDefaultState().withProperty(VARIANT, BlockFlower.Type.byMetadata(meta));
	}

	protected void checkAndDropBlock(World world, BlockPos pos, IBlockState state) {

		if (!canBlockStay(world, pos, state)) {
			dropBlockAsItem(world, pos, state, 0);
			world.setBlockToAir(pos);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighbor) {

		super.onNeighborBlockChange(world, pos, state, neighbor);
		checkAndDropBlock(world, pos, state);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {

		checkAndDropBlock(world, pos, state);
	}

	@Override
	public int getMetaFromState(IBlockState state) {

		return state.getValue(VARIANT).getMetadata();
	}

	@Override
	public int damageDropped(IBlockState state) {

		return state.getValue(VARIANT).getMetadata();
	}

	@Override
	public int getLightValue(IBlockAccess world, BlockPos pos) {

		IBlockState state = world.getBlockState(pos);
		return BlockFlower.Type.byMetadata(state.getBlock().getMetaFromState(state)).light;
	}

	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {

		BlockPos down = pos.down();
		Block soil = worldIn.getBlockState(down).getBlock();
		if (state.getBlock() != this) {
			return this.canPlaceBlockOn(soil);
		}
		return soil.canSustainPlant(worldIn, down, net.minecraft.util.EnumFacing.UP, this);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {

		return super.canPlaceBlockAt(worldIn, pos)
				&& worldIn.getBlockState(pos.down()).getBlock().canSustainPlant(worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this);
	}

	protected boolean canPlaceBlockOn(Block ground) {

		return ground == Blocks.grass || ground == Blocks.dirt || ground == Blocks.farmland;
	}

	@Override
	public boolean isFullCube() {

		return false;
	}

	@Override
	public boolean isOpaqueCube() {

		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {

		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {

		return EnumWorldBlockLayer.CUTOUT;
	}

	/* IPlantable */
	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {

		return null;
	}

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos) {

		return null;
	}

	/* IModelRegister */
	@Override
	public void registerModels() {

	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		GameRegistry.registerBlock(this, ItemBlockFlower.class, "flower");

		return true;
	}

	@Override
	public boolean initialize() {

		return true;
	}

	@Override
	public boolean postInit() {

		return true;
	}

	/* TYPE */
	public static enum Type implements IStringSerializable {

		// @formatter:off
		COAL(0, "coal", flowerCoal),
		REDSTONE(1, "redstone", flowerRedstone),
		GLOWSTONE(1, "glowstone", flowerGlowstone),
		ENDER(1, "ender", flowerEnder),
		BLAZE(1, "blaze", flowerBlaze),
		BLIZZ(1, "blizz", flowerBlizz),
		BLITZ(1, "blitz", flowerBlitz),
		BASALZ(1, "basalz", flowerBasalz),
		MANA(1, "mana", flowerMana);
		// @formatter:on

		private static final BlockFlower.Type[] METADATA_LOOKUP = new BlockFlower.Type[values().length];
		private final int metadata;
		private final String name;
		private final ItemStack stack;

		private final int light;
		private final EnumRarity rarity;

		private Type(int metadata, String name, ItemStack stack, int light, EnumRarity rarity) {

			this.metadata = metadata;
			this.name = name;
			this.stack = stack;

			this.light = light;
			this.rarity = rarity;
		}

		private Type(int metadata, String name, ItemStack stack, int light) {

			this(metadata, name, stack, light, EnumRarity.COMMON);
		}

		private Type(int metadata, String name, ItemStack stack) {

			this(metadata, name, stack, 0, EnumRarity.COMMON);
		}

		public int getMetadata() {

			return this.metadata;
		}

		@Override
		public String getName() {

			return this.name;
		}

		public ItemStack getStack() {

			return this.stack;
		}

		public int getLight() {

			return this.light;
		}

		public EnumRarity getRarity() {

			return this.rarity;
		}

		public static Type byMetadata(int metadata) {

			if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
				metadata = 0;
			}
			return METADATA_LOOKUP[metadata];
		}

		static {
			for (Type type : values()) {
				METADATA_LOOKUP[type.getMetadata()] = type;
			}
		}
	}

	/* REFERENCES */
	public static ItemStack flowerCoal;
	public static ItemStack flowerRedstone;
	public static ItemStack flowerGlowstone;
	public static ItemStack flowerEnder;
	public static ItemStack flowerBlaze;
	public static ItemStack flowerBlizz;
	public static ItemStack flowerBlitz;
	public static ItemStack flowerBasalz;
	public static ItemStack flowerMana;

}
