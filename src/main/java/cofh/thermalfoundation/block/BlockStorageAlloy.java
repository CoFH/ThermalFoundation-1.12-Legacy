package cofh.thermalfoundation.block;

import cofh.core.block.BlockCore;
import cofh.core.block.ItemBlockCore;
import cofh.core.render.IModelRegister;
import cofh.core.util.core.IInitializer;
import cofh.core.util.helpers.ItemHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.init.TFProps;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static cofh.core.util.helpers.ItemHelper.registerWithHandlers;
import static cofh.core.util.helpers.RecipeHelper.addStorageRecipe;

public class BlockStorageAlloy extends BlockCore implements IInitializer, IModelRegister {

	public static final PropertyEnum<Type> VARIANT = PropertyEnum.create("type", Type.class);

	public BlockStorageAlloy() {

		super(Material.IRON, "thermalfoundation");

		setUnlocalizedName("storage");
		setCreativeTab(ThermalFoundation.tabCommon);

		setHardness(5.0F);
		setResistance(10.0F);
		setSoundType(SoundType.METAL);
		setDefaultState(getBlockState().getBaseState().withProperty(VARIANT, Type.STEEL));

		setHarvestLevel("pickaxe", 2);
		setHarvestLevel("pickaxe", 3, getStateFromMeta(Type.ENDERIUM.getMetadata()));
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

		return "tile.thermalfoundation.storage." + Type.byMetadata(ItemHelper.getItemDamage(stack)).getName() + ".name";
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

	/* BLOCK METHODS */
	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, net.minecraft.entity.EntityLiving.SpawnPlacementType type) {

		return false;
	}

	@Override
	public boolean canProvidePower(IBlockState state) {

		return getMetaFromState(state) == Type.SIGNALUM.getMetadata();
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

		return state.getValue(VARIANT).getLight();
	}

	@Override
	public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {

		return getMetaFromState(state) == Type.SIGNALUM.getMetadata() ? 15 : 0;
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

		this.setRegistryName("storage_alloy");
		ForgeRegistries.BLOCKS.register(this);

		ItemBlockCore itemBlock = new ItemBlockCore(this);
		itemBlock.setRegistryName(this.getRegistryName());
		ForgeRegistries.ITEMS.register(itemBlock);

		blockSteel = new ItemStack(this, 1, Type.STEEL.getMetadata());
		blockElectrum = new ItemStack(this, 1, Type.ELECTRUM.getMetadata());
		blockInvar = new ItemStack(this, 1, Type.INVAR.getMetadata());
		blockBronze = new ItemStack(this, 1, Type.BRONZE.getMetadata());
		blockConstantan = new ItemStack(this, 1, Type.CONSTANTAN.getMetadata());
		blockSignalum = new ItemStack(this, 1, Type.SIGNALUM.getMetadata());
		blockLumium = new ItemStack(this, 1, Type.LUMIUM.getMetadata());
		blockEnderium = new ItemStack(this, 1, Type.ENDERIUM.getMetadata());

		registerWithHandlers("blockSteel", blockSteel);
		registerWithHandlers("blockElectrum", blockElectrum);
		registerWithHandlers("blockInvar", blockInvar);
		registerWithHandlers("blockBronze", blockBronze);
		registerWithHandlers("blockConstantan", blockConstantan);
		registerWithHandlers("blockSignalum", blockSignalum);
		registerWithHandlers("blockLumium", blockLumium);
		registerWithHandlers("blockEnderium", blockEnderium);

		ThermalFoundation.proxy.addIModelRegister(this);

		for (int i = 0; i < Type.METADATA_LOOKUP.length; i++) {
			TFProps.blockList.add(new ItemStack(this, 1, i));
		}
		return true;
	}

	@Override
	public boolean initialize() {

		addStorageRecipe(blockSteel, "ingotSteel");
		addStorageRecipe(blockElectrum, "ingotElectrum");
		addStorageRecipe(blockInvar, "ingotInvar");
		addStorageRecipe(blockBronze, "ingotBronze");
		addStorageRecipe(blockConstantan, "ingotConstantan");
		addStorageRecipe(blockSignalum, "ingotSignalum");
		addStorageRecipe(blockLumium, "ingotLumium");
		addStorageRecipe(blockEnderium, "ingotEnderium");

		return true;
	}

	/* TYPE */
	public enum Type implements IStringSerializable {

		// @formatter:off
		STEEL(0, "steel"),
		ELECTRUM(1, "electrum", 4.0F, 6.0F),
		INVAR(2, "invar", 20.0F, 12.0F),
		BRONZE(3, "bronze"),
		CONSTANTAN(4, "constantan"),
		SIGNALUM(5, "signalum", 7, 5.0F, 6.0F, EnumRarity.UNCOMMON),
		LUMIUM(6, "lumium", 15, 5.0F, 9.0F, EnumRarity.UNCOMMON),
		ENDERIUM(7, "enderium", 4, 40.0F, 120.0F, EnumRarity.RARE);
		// @formatter:on

		private static final Type[] METADATA_LOOKUP = new Type[values().length];
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
	public static ItemStack blockSteel;
	public static ItemStack blockElectrum;
	public static ItemStack blockInvar;
	public static ItemStack blockBronze;
	public static ItemStack blockConstantan;
	public static ItemStack blockSignalum;
	public static ItemStack blockLumium;
	public static ItemStack blockEnderium;

}
