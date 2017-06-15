package cofh.thermalfoundation.block;

import cofh.core.block.BlockCore;
import cofh.core.fluid.BlockFluidCore;
import cofh.core.render.IModelRegister;
import cofh.core.render.particle.EntityDropParticleFX;
import cofh.core.util.core.IInitializer;
import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.init.TFFluids;
import cofh.thermalfoundation.item.ItemMaterial;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

import static cofh.lib.util.helpers.ItemHelper.registerWithHandlers;

public class BlockOreFluid extends BlockCore implements IInitializer, IModelRegister {

	public static final PropertyEnum<BlockOreFluid.Type> VARIANT = PropertyEnum.create("type", BlockOreFluid.Type.class);

	public BlockOreFluid() {

		super(Material.ROCK, "thermalfoundation");

		setUnlocalizedName("ore");
		setCreativeTab(ThermalFoundation.tabCommon);

		setHardness(3.0F);
		setResistance(5.0F);
		setSoundType(SoundType.STONE);
		setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, Type.CRUDE_OIL_SAND));

		setHarvestLevel("pickaxe", 1);
	}

	@Override
	protected BlockStateContainer createBlockState() {

		return new BlockStateContainer(this, VARIANT);
	}

	@Override
	@SideOnly (Side.CLIENT)
	public void getSubBlocks(@Nonnull Item item, CreativeTabs tab, List<ItemStack> list) {

		for (int i = 0; i < Type.METADATA_LOOKUP.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	/* TYPE METHODS */
	@Override
	public IBlockState getStateFromMeta(int meta) {

		return this.getDefaultState().withProperty(VARIANT, Type.byMetadata(meta));
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
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {

		return Type.byMetadata(state.getBlock().getMetaFromState(state)).light;
	}

	/* BLOCK METHODS */
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {

		BlockPos north = pos.add(0, 0, -1);
		BlockPos south = pos.add(0, 0, 1);
		BlockPos west = pos.add(-1, 0, 0);
		BlockPos east = pos.add(1, 0, 0);

		if (world.isAirBlock(north)) {
			world.setBlockState(north, fluidBlocks[state.getValue(VARIANT).getMetadata()].getDefaultState().withProperty(BlockFluidCore.LEVEL, 1), 3);
		}
		if (world.isAirBlock(south)) {
			world.setBlockState(south, fluidBlocks[state.getValue(VARIANT).getMetadata()].getDefaultState().withProperty(BlockFluidCore.LEVEL, 1), 3);
		}
		if (world.isAirBlock(west)) {
			world.setBlockState(west, fluidBlocks[state.getValue(VARIANT).getMetadata()].getDefaultState().withProperty(BlockFluidCore.LEVEL, 1), 3);
		}
		if (world.isAirBlock(east)) {
			world.setBlockState(east, fluidBlocks[state.getValue(VARIANT).getMetadata()].getDefaultState().withProperty(BlockFluidCore.LEVEL, 1), 3);
		}
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {

		this.onBlockHarvested(world, pos, state, player);
		return world.setBlockState(pos, fluidBlocks[state.getValue(VARIANT).getMetadata()].getDefaultState().withProperty(BlockFluidCore.LEVEL, 1), world.isRemote ? 11 : 3);
	}

	@Override
	public boolean canProvidePower(IBlockState state) {

		return getMetaFromState(state) == Type.REDSTONE.getMetadata();
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {

		return true;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return getMetaFromState(world.getBlockState(pos)) <= Type.CRUDE_OIL_GRAVEL.getMetadata();
	}

	@Override
	public boolean isFireSource(World world, BlockPos pos, EnumFacing face) {

		return getMetaFromState(world.getBlockState(pos)) <= Type.CRUDE_OIL_GRAVEL.getMetadata();
	}

	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {

		int metadata = getMetaFromState(state);

		if (metadata >= Type.values().length) {
			return 0;
		}
		Random rand = world instanceof World ? ((World) world).rand : new Random();

		switch (Type.values()[metadata]) {
			case CRUDE_OIL_SAND:
				return MathHelper.getRandomIntegerInRange(rand, 0, 2);
			case CRUDE_OIL_GRAVEL:
				return MathHelper.getRandomIntegerInRange(rand, 0, 2);
			case REDSTONE:
				return MathHelper.getRandomIntegerInRange(rand, 1, 5);
			case GLOWSTONE:
				return MathHelper.getRandomIntegerInRange(rand, 2, 5);
			case ENDER:
				return MathHelper.getRandomIntegerInRange(rand, 3, 7);
			default:
				return 0;
		}
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return getMetaFromState(world.getBlockState(pos)) <= Type.CRUDE_OIL_GRAVEL.getMetadata() ? 15 : 0;
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return getMetaFromState(world.getBlockState(pos)) <= Type.CRUDE_OIL_GRAVEL.getMetadata() ? 1 : 0;
	}

	@Override
	public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {

		return getMetaFromState(state) == Type.REDSTONE.getMetadata() ? 7 : 0;
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random rand) {

		return 1 + rand.nextInt(fortune + 1);
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

		List<ItemStack> ret = new java.util.ArrayList<>();
		ret.add(ItemHelper.cloneStack(drops[damageDropped(state)], quantityDropped(state, fortune, world instanceof World ? ((World) world).rand : RANDOM)));

		return ret;
	}

	@Override
	@SideOnly (Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {

		super.randomDisplayTick(state, world, pos, rand);

		double px = pos.getX() + rand.nextFloat();
		double py = pos.getY() - 0.05D;
		double pz = pos.getZ() + rand.nextFloat();

		BlockFluidCore fluid = fluidBlocks[state.getValue(VARIANT).getMetadata()];

		int density = fluid.getDensity();
		int densityDir = fluid.getDensityDir();

		if (density < 0) {
			py = pos.getY() + 1.10D;
		}
		if (rand.nextInt(20) == 0 && !world.isSideSolid(pos.add(0, densityDir, 0), densityDir == -1 ? EnumFacing.UP : EnumFacing.DOWN) && !world.getBlockState(pos.add(0, densityDir, 0)).getMaterial().blocksMovement()) {
			Particle fx = new EntityDropParticleFX(world, px, py, pz, fluid.getParticleRed(), fluid.getParticleGreen(), fluid.getParticleBlue(), densityDir);
			FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
		}
	}

	/* IModelRegister */
	@Override
	@SideOnly (Side.CLIENT)
	public void registerModels() {

		for (int i = 0; i < Type.values().length; i++) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(modName + ":" + name + "_fluid", "type=" + Type.byMetadata(i).getName()));
		}
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		this.setRegistryName("ore_fluid");
		GameRegistry.register(this);

		ItemBlockOreFluid itemBlock = new ItemBlockOreFluid(this);
		itemBlock.setRegistryName(this.getRegistryName());
		GameRegistry.register(itemBlock);

		oreFluidCrudeOilSand = new ItemStack(this, 1, Type.CRUDE_OIL_SAND.getMetadata());
		oreFluidCrudeOilGravel = new ItemStack(this, 1, Type.CRUDE_OIL_GRAVEL.getMetadata());
		oreFluidRedstone = new ItemStack(this, 1, Type.REDSTONE.getMetadata());
		oreFluidGlowstone = new ItemStack(this, 1, Type.GLOWSTONE.getMetadata());
		oreFluidEnder = new ItemStack(this, 1, Type.ENDER.getMetadata());

		registerWithHandlers("oreFluidCrudeOilSand", oreFluidCrudeOilSand);
		registerWithHandlers("oreFluidCrudeOilShale", oreFluidCrudeOilGravel);
		registerWithHandlers("oreFluidRedstone", oreFluidRedstone);
		registerWithHandlers("oreFluidGlowstone", oreFluidGlowstone);
		registerWithHandlers("oreFluidEnder", oreFluidEnder);

		ThermalFoundation.proxy.addIModelRegister(this);

		return true;
	}

	@Override
	public boolean initialize() {

		fluidBlocks[Type.CRUDE_OIL_SAND.getMetadata()] = TFFluids.blockFluidCrudeOil;
		fluidBlocks[Type.CRUDE_OIL_GRAVEL.getMetadata()] = TFFluids.blockFluidCrudeOil;
		fluidBlocks[Type.REDSTONE.getMetadata()] = TFFluids.blockFluidRedstone;
		fluidBlocks[Type.GLOWSTONE.getMetadata()] = TFFluids.blockFluidGlowstone;
		fluidBlocks[Type.ENDER.getMetadata()] = TFFluids.blockFluidEnder;

		drops[Type.CRUDE_OIL_SAND.getMetadata()] = ItemMaterial.crystalCrudeOil;
		drops[Type.CRUDE_OIL_GRAVEL.getMetadata()] = ItemMaterial.crystalCrudeOil;
		drops[Type.REDSTONE.getMetadata()] = ItemMaterial.crystalRedstone;
		drops[Type.GLOWSTONE.getMetadata()] = ItemMaterial.crystalGlowstone;
		drops[Type.ENDER.getMetadata()] = ItemMaterial.crystalEnder;

		return true;
	}

	@Override
	public boolean postInit() {

		return true;
	}

	/* TYPE */
	public enum Type implements IStringSerializable {

		// @formatter:off
		CRUDE_OIL_SAND(0, "crude_oil_sand", TFFluids.blockFluidCrudeOil),
		CRUDE_OIL_GRAVEL(1, "crude_oil_gravel", TFFluids.blockFluidCrudeOil),
		REDSTONE(2, "redstone", TFFluids.blockFluidRedstone, 7, EnumRarity.UNCOMMON),
		GLOWSTONE(3, "glowstone", TFFluids.blockFluidGlowstone, 15, EnumRarity.UNCOMMON),
		ENDER(4, "ender", TFFluids.blockFluidEnder, 3, EnumRarity.RARE);
		// @formatter: on

		private static final BlockOreFluid.Type[] METADATA_LOOKUP = new BlockOreFluid.Type[values().length];
		private final int metadata;
		private final String name;
		private final int light;
		private final EnumRarity rarity;

		Type(int metadata, String name, BlockFluidCore fluid, int light, EnumRarity rarity) {

			this.metadata = metadata;
			this.name = name;
			this.light = light;
			this.rarity = rarity;
		}

		Type(int metadata, String name, BlockFluidCore fluid, int light) {

			this(metadata, name, fluid, light, EnumRarity.COMMON);
		}

		Type(int metadata, String name, BlockFluidCore fluid) {

			this(metadata, name, fluid, 0, EnumRarity.COMMON);
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

	private BlockFluidCore fluidBlocks[] = new BlockFluidCore[Type.values().length];
	private ItemStack drops[] = new ItemStack[Type.values().length];

	/* REFERENCES */
	public static ItemStack oreFluidCrudeOilSand;
	public static ItemStack oreFluidCrudeOilGravel;
	public static ItemStack oreFluidRedstone;
	public static ItemStack oreFluidGlowstone;
	public static ItemStack oreFluidEnder;

}
