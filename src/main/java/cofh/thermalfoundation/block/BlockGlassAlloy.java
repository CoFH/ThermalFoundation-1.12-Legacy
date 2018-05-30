package cofh.thermalfoundation.block;

import cofh.api.block.IDismantleable;
import cofh.core.block.BlockCore;
import cofh.core.block.ItemBlockCore;
import cofh.core.render.IModelRegister;
import cofh.core.util.CoreUtils;
import cofh.core.util.RayTracer;
import cofh.core.util.core.IInitializer;
import cofh.core.util.helpers.ItemHelper;
import cofh.core.util.helpers.ServerHelper;
import cofh.core.util.helpers.WrenchHelper;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Random;

public class BlockGlassAlloy extends BlockCore implements IDismantleable, IInitializer, IModelRegister {

	public static final PropertyEnum<Type> VARIANT = PropertyEnum.create("type", Type.class);

	public BlockGlassAlloy() {

		super(Material.GLASS, "thermalfoundation");

		setUnlocalizedName("glass");
		setCreativeTab(ThermalFoundation.tabCommon);

		setHardness(3.0F);
		setResistance(200.0F);
		setSoundType(SoundType.GLASS);
		setDefaultState(getBlockState().getBaseState().withProperty(VARIANT, Type.STEEL));
	}

	@Override
	protected BlockStateContainer createBlockState() {

		return new BlockStateContainer(this, VARIANT);
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {

		for (int i = 0; i < Type.METADATA_LOOKUP.length; i++) {
			items.add(new ItemStack(this, 1, i));
		}
	}

	/* TYPE METHODS */
	@Override
	public String getUnlocalizedName(ItemStack stack) {

		return "tile.thermalfoundation.glass." + Type.byMetadata(ItemHelper.getItemDamage(stack)).getName() + ".name";
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {

		return Type.byMetadata(ItemHelper.getItemDamage(stack)).getRarity();
	}

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

		return state.getValue(VARIANT).getLight();
	}

	@Override
	public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {

		return getMetaFromState(state) == Type.SIGNALUM.getMetadata() ? 15 : 0;
	}

	@Override
	public int quantityDropped(Random rand) {

		return 0;
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, net.minecraft.entity.EntityLiving.SpawnPlacementType type) {

		return false;
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {

		return false;
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {

		return true;
	}

	@Override
	public boolean canProvidePower(IBlockState state) {

		return getMetaFromState(state) == Type.SIGNALUM.getMetadata();
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {

		return true;
	}

	@Override
	public boolean isFullCube(IBlockState state) {

		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {

		return false;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

		if (player.isSneaking()) {
			RayTraceResult traceResult = RayTracer.retrace(player);

			if (traceResult == null) {
				return false;
			}
			if (WrenchHelper.isHoldingUsableWrench(player, traceResult)) {
				if (ServerHelper.isServerWorld(world)) {
					dismantleBlock(world, pos, state, player, false);
					WrenchHelper.usedWrench(player, traceResult);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {

		IBlockState offset = blockAccess.getBlockState(pos.offset(side));
		return offset.getBlock() != this && super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@Override
	@SideOnly (Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {

		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public float[] getBeaconColorMultiplier(IBlockState state, World world, BlockPos pos, BlockPos beaconPos) {

		return state.getValue(VARIANT).beaconMult;
	}

	/* IDismantleable */
	@Override
	public ArrayList<ItemStack> dismantleBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, boolean returnDrops) {

		int metadata = getMetaFromState(world.getBlockState(pos));
		ItemStack dropBlock = new ItemStack(this, 1, damageDropped(state));
		world.setBlockToAir(pos);

		if (!returnDrops) {
			float f = 0.3F;
			double x2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double y2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double z2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			EntityItem dropEntity = new EntityItem(world, pos.getX() + x2, pos.getY() + y2, pos.getZ() + z2, dropBlock);
			dropEntity.setPickupDelay(10);
			world.spawnEntity(dropEntity);

			CoreUtils.dismantleLog(player.getName(), this, metadata, pos);
		}
		ArrayList<ItemStack> ret = new ArrayList<>();
		ret.add(dropBlock);
		return ret;
	}

	@Override
	public boolean canDismantle(World world, BlockPos pos, IBlockState state, EntityPlayer player) {

		return true;
	}

	/* IModelRegister */
	@Override
	@SideOnly (Side.CLIENT)
	public void registerModels() {

		for (int i = 0; i < Type.values().length; i++) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(modName + ":" + name + "_alloy", "type=" + Type.byMetadata(i).getName()));
		}
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		this.setRegistryName("glass_alloy");
		ForgeRegistries.BLOCKS.register(this);

		ItemBlockCore itemBlock = new ItemBlockCore(this);
		itemBlock.setRegistryName(this.getRegistryName());
		ForgeRegistries.ITEMS.register(itemBlock);

		glassSteel = new ItemStack(this, 1, Type.STEEL.getMetadata());
		glassElectrum = new ItemStack(this, 1, Type.ELECTRUM.getMetadata());
		glassInvar = new ItemStack(this, 1, Type.INVAR.getMetadata());
		glassBronze = new ItemStack(this, 1, Type.BRONZE.getMetadata());
		glassConstantan = new ItemStack(this, 1, Type.CONSTANTAN.getMetadata());
		glassSignalum = new ItemStack(this, 1, Type.SIGNALUM.getMetadata());
		glassLumium = new ItemStack(this, 1, Type.LUMIUM.getMetadata());
		glassEnderium = new ItemStack(this, 1, Type.ENDERIUM.getMetadata());

		OreDictionary.registerOre("blockGlassHardened", new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE));

		ThermalFoundation.proxy.addIModelRegister(this);

		return true;
	}

	@Override
	public boolean initialize() {

		return true;
	}

	/* TYPE */
	public enum Type implements IStringSerializable {

		// @formatter:off
		STEEL(0, "steel", new float[] { 0.478F, 0.478F, 0.478F }),
		ELECTRUM(1, "electrum", new float[] { 0.820F, 0.761F, 0.365F }),
		INVAR(2, "invar", new float[] { 0.580F, 0.616F, 0.600F }),
		BRONZE(3, "bronze", new float[] { 0.808F, 0.557F, 0.267F }),
		CONSTANTAN(4, "constantan", new float[] { 0.804F, 0.635F, 0.373F }),
		SIGNALUM(5, "signalum", 7, new float[] { 0.788F, 0.345F, 0.133F }, EnumRarity.UNCOMMON),
		LUMIUM(6, "lumium", 15, new float[] { 0.918F, 0.898F, 0.620F }, EnumRarity.UNCOMMON),
		ENDERIUM(7, "enderium", 4, new float[] { 0.165F, 0.459F, 0.459F }, EnumRarity.RARE);
		// @formatter:on

		private static final Type[] METADATA_LOOKUP = new Type[values().length];
		private final int metadata;
		private final String name;
		private final int light;
		private final float[] beaconMult;
		private final EnumRarity rarity;

		Type(int metadata, String name, int light, float[] beaconMult, EnumRarity rarity) {

			this.metadata = metadata;
			this.name = name;
			this.light = light;
			this.beaconMult = beaconMult;
			this.rarity = rarity;
		}

		Type(int metadata, String name, int light, float[] beaconMult) {

			this(metadata, name, light, beaconMult, EnumRarity.COMMON);
		}

		Type(int metadata, String name, float[] beaconMult) {

			this(metadata, name, 0, beaconMult, EnumRarity.COMMON);
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
	public static ItemStack glassSteel;
	public static ItemStack glassElectrum;
	public static ItemStack glassInvar;
	public static ItemStack glassBronze;
	public static ItemStack glassConstantan;
	public static ItemStack glassSignalum;
	public static ItemStack glassLumium;
	public static ItemStack glassEnderium;

}
