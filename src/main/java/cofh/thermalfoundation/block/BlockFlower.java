package cofh.thermalfoundation.block;

import cofh.core.block.BlockCore;
import cofh.core.render.IModelRegister;
import cofh.core.util.core.IInitializer;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockFlower extends BlockCore implements IInitializer, IModelRegister, IPlantable {

	protected static final AxisAlignedBB FLOWER_AABB = new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 0.6000000238418579D, 0.699999988079071D);
	public static final PropertyEnum<BlockFlower.Type> VARIANT = PropertyEnum.create("type", BlockFlower.Type.class);

	public BlockFlower() {

		super(Material.PLANTS, "thermalfoundation");

		setUnlocalizedName("flower");
		setCreativeTab(ThermalFoundation.tabCommon);

		setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, Type.COAL));
		setTickRandomly(true);
	}

	@Override
	protected BlockStateContainer createBlockState() {

		return new BlockStateContainer(this, VARIANT);
	}

	@Override
	@SideOnly (Side.CLIENT)
	public void getSubBlocks(@Nonnull Item item, CreativeTabs tab, List<ItemStack> list) {

		for (int i = 0; i < BlockFlower.Type.METADATA_LOOKUP.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	/* TYPE METHODS */
	@Override
	public IBlockState getStateFromMeta(int meta) {

		return this.getDefaultState().withProperty(VARIANT, BlockFlower.Type.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {

		return state.getValue(VARIANT).getMetadata();
	}

	@Override
	public int damageDropped(IBlockState state) {

		return state.getValue(VARIANT).getMetadata();
	}

	/* BLOCK METHODS */
	protected void checkAndDropBlock(World world, BlockPos pos, IBlockState state) {

		if (!canBlockStay(world, pos, state)) {
			dropBlockAsItem(world, pos, state, 0);
			world.setBlockToAir(pos);
		}
	}

	protected boolean canSustain(Block ground) {

		return ground == Blocks.GRASS || ground == Blocks.DIRT || ground == Blocks.FARMLAND;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block) {

		super.neighborChanged(state, world, pos, block);
		this.checkAndDropBlock(world, pos, state);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {

		checkAndDropBlock(world, pos, state);
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {

		return BlockFlower.Type.byMetadata(state.getBlock().getMetaFromState(state)).light;
	}

	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {

		if (state.getBlock() == this) //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
		{
			IBlockState soil = worldIn.getBlockState(pos.down());
			return soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this);
		}
		return this.canSustain(worldIn.getBlockState(pos.down()).getBlock());
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {

		IBlockState soil = worldIn.getBlockState(pos.down());
		return super.canPlaceBlockAt(worldIn, pos) && soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this);
	}

	@Override
	public boolean isFullCube(IBlockState state) {

		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {

		return false;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {

		return FLOWER_AABB;
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {

		return NULL_AABB;
	}

	@Override
	@SideOnly (Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {

		return BlockRenderLayer.CUTOUT;
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

		for (int i = 0; i < Type.values().length; i++) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(modName + ":" + name, "type=" + Type.byMetadata(i).getName()));
		}
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		this.setRegistryName("flower");
		GameRegistry.register(this);

		ItemBlockFlower itemBlock = new ItemBlockFlower(this);
		itemBlock.setRegistryName(this.getRegistryName());
		GameRegistry.register(itemBlock);

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
	public enum Type implements IStringSerializable {

		// @formatter:off
		COAL(0, "coal"),
		REDSTONE(1, "redstone"),
		GLOWSTONE(2, "glowstone"),
		ENDER(3, "ender"),
		BLAZE(4, "blaze"),
		BLIZZ(5, "blizz"),
		BLITZ(6, "blitz"),
		BASALZ(7, "basalz"),
		MANA(8, "mana");
		// @formatter:on

		private static final BlockFlower.Type[] METADATA_LOOKUP = new BlockFlower.Type[values().length];
		private final int metadata;
		private final String name;
		private final int light;
		private final EnumRarity rarity;

		Type(int metadata, String name, int light, EnumRarity rarity) {

			this.metadata = metadata;
			this.name = name;
			this.light = light;
			this.rarity = rarity;
		}

		Type(int metadata, String name, int light) {

			this(metadata, name, light, EnumRarity.COMMON);
		}

		Type(int metadata, String name) {

			this(metadata, name, 0, EnumRarity.COMMON);
		}

		public int getMetadata() {

			return this.metadata;
		}

		@Override
		public String getName() {

			return this.name;
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
