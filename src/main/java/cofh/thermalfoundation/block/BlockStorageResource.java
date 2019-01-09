package cofh.thermalfoundation.block;

import cofh.core.block.BlockCore;
import cofh.core.block.ItemBlockCore;
import cofh.core.energy.FurnaceFuelHandler;
import cofh.core.render.IModelRegister;
import cofh.core.util.core.IInitializer;
import cofh.core.util.helpers.ItemHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.init.TFProps;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
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

public class BlockStorageResource extends BlockCore implements IInitializer, IModelRegister {

	public static final PropertyEnum<Type> VARIANT = PropertyEnum.create("type", Type.class);

	public BlockStorageResource() {

		super(Material.ROCK, MapColor.BLACK, "thermalfoundation");

		setUnlocalizedName("storage");
		setCreativeTab(ThermalFoundation.tabCommon);

		setHardness(5.0F);
		setResistance(10.0F);
		setSoundType(SoundType.STONE);
		setDefaultState(getBlockState().getBaseState().withProperty(VARIANT, Type.CHARCOAL));

		setHarvestLevel("pickaxe", 0);
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

		return "tile.thermalfoundation.storage." + Type.values()[ItemHelper.getItemDamage(stack)].getName() + ".name";
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

	/* BLOCK METHODS */
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {

		return true;
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {

		return state.getValue(VARIANT).getLight();
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
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return 5;
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return 5;
	}

	/* IModelRegister */
	@Override
	@SideOnly (Side.CLIENT)
	public void registerModels() {

		for (int i = 0; i < Type.values().length; i++) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(modName + ":" + name + "_resource", "type=" + Type.values()[i].getName()));
		}
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		this.setRegistryName("storage_resource");
		ForgeRegistries.BLOCKS.register(this);

		ItemBlockCore itemBlock = new ItemBlockCore(this);
		itemBlock.setRegistryName(this.getRegistryName());
		ForgeRegistries.ITEMS.register(itemBlock);

		blockCharcoal = new ItemStack(this, 1, Type.CHARCOAL.getMetadata());
		blockCoke = new ItemStack(this, 1, Type.COKE.getMetadata());

		registerWithHandlers("blockCharcoal", blockCharcoal);
		registerWithHandlers("blockFuelCoke", blockCoke);

		ThermalFoundation.proxy.addIModelRegister(this);

		return true;
	}

	@Override
	public boolean initialize() {

		addStorageRecipe(blockCharcoal, new ItemStack(Items.COAL, 1, 1));
		addStorageRecipe(blockCoke, "fuelCoke");

		FurnaceFuelHandler.registerFuel(blockCharcoal, 1600 * 10);
		FurnaceFuelHandler.registerFuel(blockCoke, TFProps.fuelCokeFuel * 10);

		return true;
	}

	/* TYPE */
	public enum Type implements IStringSerializable {

		// @formatter:off
		CHARCOAL(0, "charcoal"),
		COKE(1, "coke");
		// @formatter:on

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
	}

	/* REFERENCES */
	public static ItemStack blockCharcoal;
	public static ItemStack blockCoke;
	// TODO: Rosin/Tar?
	// TODO: Slags?

}
