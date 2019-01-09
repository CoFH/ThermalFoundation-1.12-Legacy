package cofh.thermalfoundation.block;

import cofh.core.block.BlockCore;
import cofh.core.block.ItemBlockCore;
import cofh.core.fluid.BlockFluidCore;
import cofh.core.render.IModelRegister;
import cofh.core.render.particle.EntityDropParticleFX;
import cofh.core.util.core.IInitializer;
import cofh.core.util.helpers.ItemHelper;
import cofh.core.util.helpers.ServerHelper;
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
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

import static cofh.core.util.helpers.ItemHelper.registerWithHandlers;

public class BlockOreFluid extends BlockCore implements IInitializer, IModelRegister {

	public static final PropertyEnum<Type> VARIANT = PropertyEnum.create("type", Type.class);

	public BlockOreFluid() {

		super(Material.ROCK, "thermalfoundation");

		setUnlocalizedName("ore");
		setCreativeTab(ThermalFoundation.tabCommon);

		setHardness(3.0F);
		setResistance(5.0F);
		setSoundType(SoundType.STONE);
		setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, Type.CRUDE_OIL_SAND));

		setHarvestLevel("pickaxe", 1);
		setHarvestLevel("shovel", 1, getDefaultState().withProperty(VARIANT, Type.CRUDE_OIL_SAND));
		setHarvestLevel("shovel", 1, getDefaultState().withProperty(VARIANT, Type.CRUDE_OIL_RED_SAND));
		setHarvestLevel("shovel", 1, getDefaultState().withProperty(VARIANT, Type.CRUDE_OIL_GRAVEL));
	}

	@Override
	protected BlockStateContainer createBlockState() {

		return new BlockStateContainer(this, VARIANT);
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {

		for (int i = 0; i < Type.values().length; i++) {
			items.add(new ItemStack(this, 1, i));
		}
	}

	/* TYPE METHODS */
	@Override
	public String getUnlocalizedName(ItemStack stack) {

		return "tile.thermalfoundation.ore." + Type.values()[ItemHelper.getItemDamage(stack)].getName() + ".name";
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {

		return Type.values()[ItemHelper.getItemDamage(stack)].getRarity();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {

		return this.getDefaultState().withProperty(VARIANT, Type.values()[meta]);
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

		return state.getValue(VARIANT).getLight();
	}

	/* BLOCK METHODS */
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {

		if (!enableBreakFluid) {
			return;
		}
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

		ItemStack stack = player.getHeldItemMainhand();

		if (!enableBreakFluid || player.capabilities.isCreativeMode || willHarvest && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0) {
			return world.setBlockState(pos, net.minecraft.init.Blocks.AIR.getDefaultState(), 3);
		}
		if (ServerHelper.isServerWorld(world)) {
			this.onBlockHarvested(world, pos, state, player);
		}
		return world.setBlockState(new BlockPos(pos), fluidBlocks[state.getValue(VARIANT).getMetadata()].getDefaultState().withProperty(BlockFluidCore.LEVEL, 1), 3);
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

		return world.getBlockState(pos).getValue(VARIANT).getFlammability();
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
			case CRUDE_OIL_RED_SAND:
			case CRUDE_OIL_GRAVEL:
				return MathHelper.getInt(rand, 0, 2);
			case REDSTONE:
				return MathHelper.getInt(rand, 1, 5);
			case GLOWSTONE:
				return MathHelper.getInt(rand, 2, 5);
			case ENDER:
				return MathHelper.getInt(rand, 3, 7);
			default:
				return 0;
		}
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return world.getBlockState(pos).getValue(VARIANT).getFlammability() ? 15 : 0;
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return world.getBlockState(pos).getValue(VARIANT).getFlammability() ? 1 : 0;
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
	public float getBlockHardness(IBlockState state, World world, BlockPos pos) {

		return state.getValue(VARIANT).getHardness();
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {

		IBlockState state = world.getBlockState(pos);
		return state.getValue(VARIANT).getResistance();
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

		drops.add(ItemHelper.cloneStack(this.drops[damageDropped(state)], quantityDropped(state, fortune, world instanceof World ? ((World) world).rand : RANDOM)));
	}

	@Override
	@SideOnly (Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {

		double px = pos.getX() + rand.nextFloat();
		double py = pos.getY() - 0.05D;
		double pz = pos.getZ() + rand.nextFloat();

		BlockFluidCore fluid = fluidBlocks[state.getValue(VARIANT).getMetadata()];

		int density = fluid.getDensitySafe();
		int densityDir = fluid.getDensityDirSafe();

		if (density < 0) {
			py = pos.getY() + 1.10D;
		}
		if (rand.nextInt(20) == 0 && !world.isSideSolid(pos.add(0, densityDir, 0), densityDir == -1 ? EnumFacing.UP : EnumFacing.DOWN) && !world.getBlockState(pos.add(0, densityDir, 0)).getMaterial().blocksMovement()) {
			Particle fx = new EntityDropParticleFX(world, px, py, pz, fluid.getParticleRed(), fluid.getParticleGreen(), fluid.getParticleBlue(), densityDir);
			FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
		}
	}

	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {

		switch (state.getValue(VARIANT).getMetadata()) {
			case 0:
			case 5:
				return SoundType.SAND;
			case 1:
				return SoundType.GROUND;
			default:
				return SoundType.STONE;
		}
	}

	/* IModelRegister */
	@Override
	@SideOnly (Side.CLIENT)
	public void registerModels() {

		for (int i = 0; i < Type.values().length; i++) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(modName + ":" + name + "_fluid", "type=" + Type.values()[i].getName()));
		}
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		this.setRegistryName("ore_fluid");
		ForgeRegistries.BLOCKS.register(this);

		ItemBlockCore itemBlock = new ItemBlockCore(this);
		itemBlock.setRegistryName(this.getRegistryName());
		ForgeRegistries.ITEMS.register(itemBlock);

		config();

		oreClathrateOilSand = new ItemStack(this, 1, Type.CRUDE_OIL_SAND.getMetadata());
		oreClathrateOilShale = new ItemStack(this, 1, Type.CRUDE_OIL_GRAVEL.getMetadata());
		oreClathrateRedstone = new ItemStack(this, 1, Type.REDSTONE.getMetadata());
		oreClathrateGlowstone = new ItemStack(this, 1, Type.GLOWSTONE.getMetadata());
		oreClathrateEnder = new ItemStack(this, 1, Type.ENDER.getMetadata());
		oreClathrateOilRedSand = new ItemStack(this, 1, Type.CRUDE_OIL_RED_SAND.getMetadata());

		registerWithHandlers("oreClathrateOilSand", oreClathrateOilSand);
		registerWithHandlers("oreClathrateOilShale", oreClathrateOilShale);
		registerWithHandlers("oreClathrateRedstone", oreClathrateRedstone);
		registerWithHandlers("oreClathrateGlowstone", oreClathrateGlowstone);
		registerWithHandlers("oreClathrateEnder", oreClathrateEnder);
		registerWithHandlers("oreClathrateOilSand", oreClathrateOilRedSand);

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
		fluidBlocks[Type.CRUDE_OIL_RED_SAND.getMetadata()] = TFFluids.blockFluidCrudeOil;

		drops[Type.CRUDE_OIL_SAND.getMetadata()] = ItemMaterial.crystalCrudeOil;
		drops[Type.CRUDE_OIL_GRAVEL.getMetadata()] = ItemMaterial.crystalCrudeOil;
		drops[Type.REDSTONE.getMetadata()] = ItemMaterial.crystalRedstone;
		drops[Type.GLOWSTONE.getMetadata()] = ItemMaterial.crystalGlowstone;
		drops[Type.ENDER.getMetadata()] = ItemMaterial.crystalEnder;
		drops[Type.CRUDE_OIL_RED_SAND.getMetadata()] = ItemMaterial.crystalCrudeOil;

		return true;
	}

	public void config() {

		String category = "Block.OreFluid";
		String comment = "If TRUE, Clathrates will create fluid when broken.";
		enableBreakFluid = ThermalFoundation.CONFIG.get(category, "FluidOnBreaking", enableBreakFluid, comment);
	}

	/* TYPE */
	public enum Type implements IStringSerializable {

		// @formatter:off
		CRUDE_OIL_SAND(0, "crude_oil_sand", 0, 0.5F, 2.0F, true, EnumRarity.COMMON),
		CRUDE_OIL_GRAVEL(1, "crude_oil_gravel", 0, 0.6F, 2.5F, true, EnumRarity.COMMON),
		REDSTONE(2, "redstone", 7, 5.0F, 3.0F, false, EnumRarity.UNCOMMON),
		GLOWSTONE(3, "glowstone", 15, 0.4F, 2.0F, false, EnumRarity.UNCOMMON),
		ENDER(4, "ender", 3, 3.0F, 9.0F, false, EnumRarity.RARE),
		CRUDE_OIL_RED_SAND(5, "crude_oil_red_sand", 0, 0.5F, 2.0F, true, EnumRarity.COMMON);
		// @formatter:on

		private final int metadata;
		private final String name;
		private final int light;
		private final float hardness;
		private final float resistance;
		private final boolean flammable;
		private final EnumRarity rarity;

		Type(int metadata, String name, int light, float hardness, float resistance, boolean flammable, EnumRarity rarity) {

			this.metadata = metadata;
			this.name = name;
			this.light = light;
			this.hardness = hardness;
			this.resistance = resistance;
			this.flammable = flammable;
			this.rarity = rarity;
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

		public float getHardness() {

			return this.hardness;
		}

		public float getResistance() {

			return this.resistance;
		}

		public boolean getFlammability() {

			return this.flammable;
		}

		public EnumRarity getRarity() {

			return this.rarity;
		}
	}

	private static boolean enableBreakFluid = true;
	private static BlockFluidCore fluidBlocks[] = new BlockFluidCore[Type.values().length];
	private static ItemStack drops[] = new ItemStack[Type.values().length];

	/* REFERENCES */
	public static ItemStack oreClathrateOilSand;
	public static ItemStack oreClathrateOilRedSand;
	public static ItemStack oreClathrateOilShale;
	public static ItemStack oreClathrateRedstone;
	public static ItemStack oreClathrateGlowstone;
	public static ItemStack oreClathrateEnder;

}
