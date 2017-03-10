package cofh.thermalfoundation.block;

import cofh.core.util.core.IInitializer;
import cofh.core.render.IModelRegister;
import cofh.core.block.BlockCore;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static cofh.lib.util.helpers.ItemHelper.addStorageRecipe;
import static cofh.lib.util.helpers.ItemHelper.registerWithHandlers;

public class BlockStorage extends BlockCore implements IInitializer, IModelRegister {

	public static final PropertyEnum<BlockStorage.Type> VARIANT = PropertyEnum.<BlockStorage.Type>create("type", BlockStorage.Type.class);

	public BlockStorage() {

		super(Material.IRON, "thermalfoundation");

		setUnlocalizedName("storage");
		setCreativeTab(ThermalFoundation.tabCommon);

		setHardness(5.0F);
		setResistance(10.0F);
		setSoundType(SoundType.METAL);
		setDefaultState(getBlockState().getBaseState().withProperty(VARIANT, Type.COPPER));

		setHarvestLevel("pickaxe", 2);
		setHarvestLevel("pickaxe", 1, getStateFromMeta(Type.COPPER.getMetadata()));
		setHarvestLevel("pickaxe", 1, getStateFromMeta(Type.TIN.getMetadata()));
		setHarvestLevel("pickaxe", 3, getStateFromMeta(Type.PLATINUM.getMetadata()));
		setHarvestLevel("pickaxe", 3, getStateFromMeta(Type.IRIDIUM.getMetadata()));
		setHarvestLevel("pickaxe", 3, getStateFromMeta(Type.MITHRIL.getMetadata()));
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

		return this.getDefaultState().withProperty(VARIANT, BlockStorage.Type.byMetadata(meta));
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
	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, net.minecraft.entity.EntityLiving.SpawnPlacementType type) {

		return false;
	}

	@Override
	public boolean canProvidePower(IBlockState state) {

		return true;
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {

		return true;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {

		return true;
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {

		return Type.byMetadata(state.getBlock().getMetaFromState(state)).light;
	}

	@Override
	public float getBlockHardness(IBlockState state, World world, BlockPos pos) {

		return Type.byMetadata(state.getBlock().getMetaFromState(state)).hardness;
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {

		IBlockState state = world.getBlockState(pos);
		return Type.byMetadata(state.getBlock().getMetaFromState(state)).resistance;
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

		this.setRegistryName("storage");
		GameRegistry.register(this);

		ItemBlockStorage itemBlock = new ItemBlockStorage(this);
		itemBlock.setRegistryName(this.getRegistryName());
		GameRegistry.register(itemBlock);

		blockCopper = new ItemStack(this, 1, Type.COPPER.getMetadata());
		blockTin = new ItemStack(this, 1, Type.TIN.getMetadata());
		blockSilver = new ItemStack(this, 1, Type.SILVER.getMetadata());
		blockLead = new ItemStack(this, 1, Type.LEAD.getMetadata());
		blockAluminum = new ItemStack(this, 1, Type.ALUMINUM.getMetadata());
		blockNickel = new ItemStack(this, 1, Type.NICKEL.getMetadata());
		blockPlatinum = new ItemStack(this, 1, Type.PLATINUM.getMetadata());
		blockIridium = new ItemStack(this, 1, Type.IRIDIUM.getMetadata());
		blockMithril = new ItemStack(this, 1, Type.MITHRIL.getMetadata());

		registerWithHandlers("blockCopper", blockCopper);
		registerWithHandlers("blockTin", blockTin);
		registerWithHandlers("blockSilver", blockSilver);
		registerWithHandlers("blockLead", blockLead);
		registerWithHandlers("blockAluminum", blockAluminum);
		registerWithHandlers("blockNickel", blockNickel);
		registerWithHandlers("blockPlatinum", blockPlatinum);
		registerWithHandlers("blockIridium", blockIridium);
		registerWithHandlers("blockMithril", blockMithril);

		return true;
	}

	@Override
	public boolean initialize() {

		return true;
	}

	@Override
	public boolean postInit() {

		addStorageRecipe(blockCopper, "ingotCopper");
		addStorageRecipe(blockTin, "ingotTin");
		addStorageRecipe(blockSilver, "ingotSilver");
		addStorageRecipe(blockLead, "ingotLead");
		addStorageRecipe(blockNickel, "ingotNickel");
		addStorageRecipe(blockPlatinum, "ingotPlatinum");
		addStorageRecipe(blockMithril, "ingotMithril");

		return true;
	}

	/* TYPE */
	public enum Type implements IStringSerializable {

		// @formatter:off
		COPPER(0, "copper"),
		TIN(1, "tin"),
		SILVER(2, "silver", 4),
		LEAD(3, "lead", 4.0F, 12.0F),
		ALUMINUM(4, "aluminum", 4.0F, 6.0F),
		NICKEL(5, "nickel", 10.0F, 6.0F),
		PLATINUM(6, "platinum", 4, 5.0F, 10.0F, EnumRarity.UNCOMMON),
		IRIDIUM(7, "iridium", 4, 10.0F, 15.0F, EnumRarity.UNCOMMON),
		MITHRIL(8, "mithril", 8, 30.0F, 120.0F, EnumRarity.RARE);
		// @formatter: on

		private static final BlockStorage.Type[] METADATA_LOOKUP = new BlockStorage.Type[values().length];
		private final int metadata;
		private final String name;
		private final int light;
		private final float hardness;
		private final float resistance;
		private final EnumRarity rarity;

		Type(int metadata, String name, int light, float hardness, float resistance, EnumRarity rarity) {

			this.metadata = metadata;
			this.name = name;
			this.light = light;
			this.hardness = hardness;
			this.resistance = resistance;
			this.rarity = rarity;
		}

		Type(int metadata, String name, int light, float hardness, float resistance) {

			this(metadata, name, light, hardness, resistance, EnumRarity.COMMON);
		}

		Type(int metadata, String name, float hardness, float resistance) {
			this(metadata, name, 0, hardness, resistance, EnumRarity.COMMON);
		}

		Type(int metadata, String name, int light) {

			this(metadata, name, light, 5.0F, 6.0F, EnumRarity.COMMON);
		}

		Type(int metadata, String name) {

			this(metadata, name, 0, 5.0F, 6.0F, EnumRarity.COMMON);
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
	public static ItemStack blockCopper;
	public static ItemStack blockTin;
	public static ItemStack blockSilver;
	public static ItemStack blockLead;
	public static ItemStack blockAluminum;
	public static ItemStack blockNickel;
	public static ItemStack blockPlatinum;
	public static ItemStack blockIridium;
	public static ItemStack blockMithril;

}
