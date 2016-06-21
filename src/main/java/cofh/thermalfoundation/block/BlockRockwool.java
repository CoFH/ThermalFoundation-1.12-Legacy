package cofh.thermalfoundation.block;

import static cofh.lib.util.helpers.ItemHelper.addSmelting;

import cofh.api.core.IInitializer;
import cofh.api.core.IModelRegister;
import cofh.core.block.BlockCoFHBase;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.item.ItemMaterial;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRockwool extends BlockCoFHBase implements IInitializer, IModelRegister {

	public static final PropertyEnum<BlockRockwool.Type> VARIANT = PropertyEnum.<BlockRockwool.Type> create("type", BlockRockwool.Type.class);

	public BlockRockwool() {

		super(Material.rock, "thermalfoundation");

		setUnlocalizedName("rockwool");
		setCreativeTab(ThermalFoundation.tabCommon);

		setHardness(0.8F);
		setResistance(10.0F);
		setStepSound(soundTypeCloth);
		setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, Type.GRAY));

		setHarvestLevel("pickaxe", 1);
	}

	@Override
	protected BlockState createBlockState() {

		return new BlockState(this, new IProperty[] { VARIANT });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {

		for (int i = 0; i < Type.METADATA_LOOKUP.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public int getDamageValue(World world, BlockPos pos) {

		IBlockState state = world.getBlockState(pos);
		return state.getBlock() != this ? 0 : state.getValue(VARIANT).getMetadata();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {

		return this.getDefaultState().withProperty(VARIANT, BlockRockwool.Type.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {

		return state.getValue(VARIANT).getMetadata();
	}

	@Override
	public int damageDropped(IBlockState state) {

		return state.getValue(VARIANT).getMetadata();
	}

	/* IModelRegister */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {

		for (int i = 0; i < Type.values().length; i++) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(modName + ":" + name, "type="
					+ Type.byMetadata(i).getName()));
		}
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		GameRegistry.registerBlock(this, ItemBlockRockwool.class, "rockwool");

		rockwoolBlack = new ItemStack(this, 1, Type.BLACK.getMetadata());
		rockwoolRed = new ItemStack(this, 1, Type.RED.getMetadata());
		rockwoolGreen = new ItemStack(this, 1, Type.GREEN.getMetadata());
		rockwoolBrown = new ItemStack(this, 1, Type.BROWN.getMetadata());
		rockwoolBlue = new ItemStack(this, 1, Type.BLUE.getMetadata());
		rockwoolPurple = new ItemStack(this, 1, Type.PURPLE.getMetadata());
		rockwoolCyan = new ItemStack(this, 1, Type.CYAN.getMetadata());
		rockwoolSilver = new ItemStack(this, 1, Type.SILVER.getMetadata());
		rockwoolGray = new ItemStack(this, 1, Type.GRAY.getMetadata());
		rockwoolPink = new ItemStack(this, 1, Type.PINK.getMetadata());
		rockwoolLime = new ItemStack(this, 1, Type.LIME.getMetadata());
		rockwoolYellow = new ItemStack(this, 1, Type.YELLOW.getMetadata());
		rockwoolLightBlue = new ItemStack(this, 1, Type.LIGHT_BLUE.getMetadata());
		rockwoolMagenta = new ItemStack(this, 1, Type.MAGENTA.getMetadata());
		rockwoolOrange = new ItemStack(this, 1, Type.ORANGE.getMetadata());
		rockwoolWhite = new ItemStack(this, 1, Type.WHITE.getMetadata());

		return true;
	}

	@Override
	public boolean initialize() {

		return true;
	}

	@Override
	public boolean postInit() {

		addSmelting(ItemMaterial.crystalSlag, rockwoolSilver, 0.0F);

		return true;
	}

	/* TYPE */
	public static enum Type implements IStringSerializable {

		// @formatter:off
		BLACK(0, "black", rockwoolBlack),
		RED(1, "red", rockwoolRed),
		GREEN(2, "green", rockwoolGreen),
		BROWN(3, "brown", rockwoolBrown),
		BLUE(4, "blue", rockwoolBlue),
		PURPLE(5, "purple", rockwoolPurple),
		CYAN(6, "cyan", rockwoolCyan),
		SILVER(7, "silver", rockwoolSilver),
		GRAY(8, "gray", rockwoolGray),
		PINK(9, "pink", rockwoolPink),
		LIME(10, "lime", rockwoolLime),
		YELLOW(11, "yellow", rockwoolYellow),
		LIGHT_BLUE(12, "light_blue", rockwoolLightBlue),
		MAGENTA(13, "magenta", rockwoolMagenta),
		ORANGE(14, "orange", rockwoolOrange),
		WHITE(15, "white", rockwoolWhite);
		// @formatter:on

		private static final BlockRockwool.Type[] METADATA_LOOKUP = new BlockRockwool.Type[values().length];
		private final int metadata;
		private final String name;
		private final ItemStack stack;

		private Type(int metadata, String name, ItemStack stack) {

			this.metadata = metadata;
			this.name = name;
			this.stack = stack;
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
	public static ItemStack rockwoolBlack;
	public static ItemStack rockwoolRed;
	public static ItemStack rockwoolGreen;
	public static ItemStack rockwoolBrown;
	public static ItemStack rockwoolBlue;
	public static ItemStack rockwoolPurple;
	public static ItemStack rockwoolCyan;
	public static ItemStack rockwoolSilver;
	public static ItemStack rockwoolGray;
	public static ItemStack rockwoolPink;
	public static ItemStack rockwoolLime;
	public static ItemStack rockwoolYellow;
	public static ItemStack rockwoolLightBlue;
	public static ItemStack rockwoolMagenta;
	public static ItemStack rockwoolOrange;
	public static ItemStack rockwoolWhite;

}
