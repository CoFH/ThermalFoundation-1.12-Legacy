package cofh.thermalfoundation.block;

import codechicken.lib.raytracer.RayTracer;
import cofh.api.block.IDismantleable;
import cofh.api.core.IInitializer;
import cofh.api.core.IModelRegister;
import cofh.core.block.BlockCoFHBase;
import cofh.core.util.CoreUtils;
import cofh.lib.util.helpers.ServerHelper;
import cofh.lib.util.helpers.WrenchHelper;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
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
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockGlass extends BlockCoFHBase implements IDismantleable, IInitializer, IModelRegister {

	public static final PropertyEnum<BlockGlass.Type> VARIANT = PropertyEnum.<BlockGlass.Type>create("type", BlockGlass.Type.class);

	public BlockGlass() {

		super(Material.GLASS, "thermalfoundation");

		setUnlocalizedName("glass");
		setCreativeTab(ThermalFoundation.tabCommon);

		setHardness(3.0F);
		setResistance(200.0F);
		setSoundType(SoundType.GLASS);
	}

	@Override
	protected BlockStateContainer createBlockState() {

		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

	@Override
	@SideOnly (Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {

		for (int i = 0; i < Type.METADATA_LOOKUP.length; i++) {
			list.add(new ItemStack(item, 1, i));
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
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {

		return true;
	}

	@Override
	public boolean canProvidePower(IBlockState state) {

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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {

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
			world.spawnEntityInWorld(dropEntity);

			CoreUtils.dismantleLog(player.getName(), this, metadata, pos);
		}
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
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

		GameRegistry.registerBlock(this, ItemBlockGlass.class, "glass");

		glassCopper = new ItemStack(this, 1, Type.COPPER.getMetadata());
		glassTin = new ItemStack(this, 1, Type.TIN.getMetadata());
		glassSilver = new ItemStack(this, 1, Type.SILVER.getMetadata());
		glassLead = new ItemStack(this, 1, Type.LEAD.getMetadata());
		glassNickel = new ItemStack(this, 1, Type.NICKEL.getMetadata());
		glassPlatinum = new ItemStack(this, 1, Type.PLATINUM.getMetadata());
		glassMithril = new ItemStack(this, 1, Type.MITHRIL.getMetadata());
		glassElectrum = new ItemStack(this, 1, Type.ELECTRUM.getMetadata());
		glassInvar = new ItemStack(this, 1, Type.INVAR.getMetadata());
		glassBronze = new ItemStack(this, 1, Type.BRONZE.getMetadata());
		glassSignalum = new ItemStack(this, 1, Type.SIGNALUM.getMetadata());
		glassLumium = new ItemStack(this, 1, Type.LUMIUM.getMetadata());
		glassEnderium = new ItemStack(this, 1, Type.ENDERIUM.getMetadata());

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
		COPPER(0, "copper", glassCopper),
		TIN(1, "tin", glassTin),
		SILVER(2, "silver", glassSilver, 4),
		LEAD(3, "lead", glassLead),
		NICKEL(4, "nickel", glassNickel),
		PLATINUM(5, "platinum", glassPlatinum, 4, EnumRarity.UNCOMMON),
		MITHRIL(6, "mithril", glassMithril, 8, EnumRarity.RARE),
		ELECTRUM(7, "electrum", glassElectrum),
		INVAR(8, "invar", glassInvar),
		BRONZE(9, "bronze", glassBronze),
		SIGNALUM(10, "signalum", glassSignalum, 7, EnumRarity.UNCOMMON),
		LUMIUM(11, "lumium", glassLumium, 15, EnumRarity.UNCOMMON),
		ENDERIUM(12, "enderium", glassEnderium, 4, EnumRarity.RARE);
		// @formatter: on

		private static final BlockGlass.Type[] METADATA_LOOKUP = new BlockGlass.Type[values().length];
		private final int metadata;
		private final String name;
		private final ItemStack stack;

		private final int light;
		private final EnumRarity rarity;

		Type(int metadata, String name, ItemStack stack, int light, EnumRarity rarity) {

			this.metadata = metadata;
			this.name = name;
			this.stack = stack;

			this.light = light;
			this.rarity = rarity;
		}

		Type(int metadata, String name, ItemStack stack, int light) {

			this(metadata, name, stack, light, EnumRarity.COMMON);
		}


		Type(int metadata, String name, ItemStack stack) {

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
	public static ItemStack glassCopper;
	public static ItemStack glassTin;
	public static ItemStack glassSilver;
	public static ItemStack glassLead;
	public static ItemStack glassNickel;
	public static ItemStack glassPlatinum;
	public static ItemStack glassMithril;
	public static ItemStack glassElectrum;
	public static ItemStack glassInvar;
	public static ItemStack glassBronze;
	public static ItemStack glassSignalum;
	public static ItemStack glassLumium;
	public static ItemStack glassEnderium;

}
