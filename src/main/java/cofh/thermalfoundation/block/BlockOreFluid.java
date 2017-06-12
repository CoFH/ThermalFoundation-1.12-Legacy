package cofh.thermalfoundation.block;

import cofh.core.block.BlockCore;
import cofh.core.fluid.BlockFluidCore;
import cofh.core.render.IModelRegister;
import cofh.core.render.particle.EntityDropParticleFX;
import cofh.core.util.core.IInitializer;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.init.TFFluids;
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
		setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, Type.CRUDE_OIL));

		setHarvestLevel("pickaxe", 2);
		setHarvestLevel("pickaxe", 1, getStateFromMeta(Type.CRUDE_OIL.getMetadata()));
		setHarvestLevel("pickaxe", 3, getStateFromMeta(Type.ENDER.getMetadata()));
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

	private BlockFluidCore getFluidBlock(IBlockState state, IBlockAccess world, BlockPos pos) {

		return Type.byMetadata(state.getBlock().getMetaFromState(state)).fluid;
	}

	@Override
	public int quantityDropped(Random rand) {

		return 0;
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {

		return true;
	}

	@Override
	@SideOnly (Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {

		super.randomDisplayTick(state, world, pos, rand);

		double px = pos.getX() + rand.nextFloat();
		double py = pos.getY() - 0.05D;
		double pz = pos.getZ() + rand.nextFloat();

		BlockFluidCore fluid = getFluidBlock(state, world, pos);

		int density = fluid.getDensity();
		int densityDir = fluid.getDensityDir();

		if (density < 0) {
			py = pos.getY() + 2.10D;
		}
		if (rand.nextInt(20) == 0 && world.isSideSolid(pos.add(0, densityDir, 0), densityDir == -1 ? EnumFacing.UP : EnumFacing.DOWN) && !world.getBlockState(pos.add(0, 2 * densityDir, 0)).getMaterial().blocksMovement()) {
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

		oreFluidCrudeOil = new ItemStack(this, 1, Type.CRUDE_OIL.getMetadata());
		oreFluidRedstone = new ItemStack(this, 1, Type.REDSTONE.getMetadata());
		oreFluidGlowstone = new ItemStack(this, 1, Type.GLOWSTONE.getMetadata());
		oreFluidEnder = new ItemStack(this, 1, Type.ENDER.getMetadata());

		registerWithHandlers("oreFluidCrudeOil", oreFluidCrudeOil);
		registerWithHandlers("oreFluidRedstone", oreFluidRedstone);
		registerWithHandlers("oreFluidGlowstone", oreFluidGlowstone);
		registerWithHandlers("oreFluidEnder", oreFluidEnder);

		ThermalFoundation.proxy.addIModelRegister(this);

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
		CRUDE_OIL(0, "crude_oil", TFFluids.blockFluidCrudeOil),
		REDSTONE(1, "redstone", TFFluids.blockFluidRedstone, 7, EnumRarity.UNCOMMON),
		GLOWSTONE(2, "glowstone", TFFluids.blockFluidGlowstone, 15, EnumRarity.UNCOMMON),
		ENDER(3, "ender", TFFluids.blockFluidEnder, 3, EnumRarity.RARE);
		// @formatter: on

		private static final BlockOreFluid.Type[] METADATA_LOOKUP = new BlockOreFluid.Type[values().length];
		private final int metadata;
		private final String name;
		private final BlockFluidCore fluid;
		private final int light;
		private final EnumRarity rarity;

		Type(int metadata, String name, BlockFluidCore fluid, int light, EnumRarity rarity) {

			this.metadata = metadata;
			this.name = name;
			this.fluid = fluid;
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

		public BlockFluidCore getFluidBlock() {

			return this.fluid;
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
	public static ItemStack oreFluidCrudeOil;
	public static ItemStack oreFluidRedstone;
	public static ItemStack oreFluidGlowstone;
	public static ItemStack oreFluidEnder;

}
