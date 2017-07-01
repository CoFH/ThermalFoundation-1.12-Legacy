package cofh.thermalfoundation.block;

import cofh.api.block.IDismantleable;
import cofh.core.block.BlockCore;
import cofh.core.render.IModelRegister;
import cofh.core.util.CoreUtils;
import cofh.core.util.core.IInitializer;
import cofh.core.util.RayTracer;
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

public class BlockGlass extends BlockCore implements IDismantleable, IInitializer, IModelRegister {

	public static final PropertyEnum<BlockGlass.Type> VARIANT = PropertyEnum.create("type", BlockGlass.Type.class);

	public BlockGlass() {

		super(Material.GLASS, "thermalfoundation");

		setUnlocalizedName("glass");
		setCreativeTab(ThermalFoundation.tabCommon);

		setHardness(3.0F);
		setResistance(200.0F);
		setSoundType(SoundType.GLASS);
		setDefaultState(getBlockState().getBaseState().withProperty(VARIANT, Type.COPPER));
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

	@Override
	public IBlockState getStateFromMeta(int meta) {

		return this.getDefaultState().withProperty(VARIANT, BlockGlass.Type.byMetadata(meta));
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

	@Override
	public int quantityDropped(Random rand) {

		return 0;
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, net.minecraft.entity.EntityLiving.SpawnPlacementType type) {

		return false;
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {

		return true;
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

		return Type.byMetadata(state.getBlock().getMetaFromState(state)).beaconMult;
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
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(modName + ":" + name, "type=" + Type.byMetadata(i).getName()));
		}
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		this.setRegistryName("glass");
		ForgeRegistries.BLOCKS.register(this);

		ItemBlockGlass itemBlock = new ItemBlockGlass(this);
		itemBlock.setRegistryName(this.getRegistryName());
		ForgeRegistries.ITEMS.register(itemBlock);

		glassCopper = new ItemStack(this, 1, Type.COPPER.getMetadata());
		glassTin = new ItemStack(this, 1, Type.TIN.getMetadata());
		glassSilver = new ItemStack(this, 1, Type.SILVER.getMetadata());
		glassLead = new ItemStack(this, 1, Type.LEAD.getMetadata());
		glassAluminum = new ItemStack(this, 1, Type.ALUMINUM.getMetadata());
		glassNickel = new ItemStack(this, 1, Type.NICKEL.getMetadata());
		glassPlatinum = new ItemStack(this, 1, Type.PLATINUM.getMetadata());
		glassIridium = new ItemStack(this, 1, Type.IRIDIUM.getMetadata());
		glassMithril = new ItemStack(this, 1, Type.MITHRIL.getMetadata());

		OreDictionary.registerOre("blockGlassHardened", new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE));

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
		COPPER(0, "copper", new float[] { 0.792F, 0.478F, 0.137F }),
		TIN(1, "tin", new float[] { 0.565F, 0.639F, 0.686F }),
		SILVER(2, "silver", 4, new float[] { 0.663F, 0.761F, 0.784F }),
		LEAD(3, "lead", new float[] { 0.427F, 0.471F, 0.604F }),
		ALUMINUM(4, "aluminum", new float[] { 0.694F, 0.702F, 0.741F }),
		NICKEL(5, "nickel", new float[] { 0.816F, 0.792F, 0.608F }),
		PLATINUM(6, "platinum", 4, new float[] { 0.537F, 0.808F, 0.910F }, EnumRarity.UNCOMMON),
		IRIDIUM(7, "iridium", 4, new float[] { 0.800F, 0.804F, 0.890F }, EnumRarity.UNCOMMON),
		MITHRIL(8, "mithril", 8, new float[] { 0.416F, 0.612F, 0.722F }, EnumRarity.RARE);
		// @formatter: on

		private static final BlockGlass.Type[] METADATA_LOOKUP = new BlockGlass.Type[values().length];
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
	public static ItemStack glassCopper;
	public static ItemStack glassTin;
	public static ItemStack glassSilver;
	public static ItemStack glassLead;
	public static ItemStack glassAluminum;
	public static ItemStack glassNickel;
	public static ItemStack glassPlatinum;
	public static ItemStack glassIridium;
	public static ItemStack glassMithril;

}
