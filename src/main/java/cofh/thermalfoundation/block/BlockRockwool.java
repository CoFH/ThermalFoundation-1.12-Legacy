package cofh.thermalfoundation.block;

import cofh.core.block.BlockCore;
import cofh.core.render.IModelRegister;
import cofh.core.util.core.IInitializer;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.item.ItemMaterial;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import static cofh.lib.util.helpers.ItemHelper.*;

public class BlockRockwool extends BlockCore implements IInitializer, IModelRegister {

	public static final PropertyEnum<BlockRockwool.Type> VARIANT = PropertyEnum.create("type", BlockRockwool.Type.class);

	public BlockRockwool() {

		super(Material.ROCK, "thermalfoundation");

		setUnlocalizedName("rockwool");
		setCreativeTab(ThermalFoundation.tabCommon);

		setHardness(0.8F);
		setResistance(10.0F);
		setSoundType(SoundType.CLOTH);
		setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, Type.GRAY));

		setHarvestLevel("pickaxe", 1);
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
	@SideOnly (Side.CLIENT)
	public void registerModels() {

		for (int i = 0; i < Type.values().length; i++) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(modName + ":" + name, "type=" + Type.byMetadata(i).getName()));
		}
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		this.setRegistryName("rockwool");
		ForgeRegistries.BLOCKS.register(this);

		ItemBlockRockwool itemBlock = new ItemBlockRockwool(this);
		itemBlock.setRegistryName(this.getRegistryName());
		ForgeRegistries.ITEMS.register(itemBlock);

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

		OreDictionary.registerOre("blockRockwool", new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE));

		ThermalFoundation.proxy.addIModelRegister(this);

		return true;
	}

	@Override
	public boolean initialize() {

		addSmelting(rockwoolSilver, ItemMaterial.crystalSlag, 0.0F);

		for (int i = 0; i < 16; i++) {
			addRecipe(ShapelessRecipe(new ItemStack(this, 1, i), new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.DYE, 1, i)));
		}
		return true;
	}

	@Override
	public boolean postInit() {

		return true;
	}

	/* TYPE */
	public enum Type implements IStringSerializable {

		// @formatter:off
		BLACK(0, "black"),
		RED(1, "red"),
		GREEN(2, "green"),
		BROWN(3, "brown"),
		BLUE(4, "blue"),
		PURPLE(5, "purple"),
		CYAN(6, "cyan"),
		SILVER(7, "silver"),
		GRAY(8, "gray"),
		PINK(9, "pink"),
		LIME(10, "lime"),
		YELLOW(11, "yellow"),
		LIGHT_BLUE(12, "light_blue"),
		MAGENTA(13, "magenta"),
		ORANGE(14, "orange"),
		WHITE(15, "white");
		// @formatter:on

		private static final BlockRockwool.Type[] METADATA_LOOKUP = new BlockRockwool.Type[values().length];
		private final int metadata;
		private final String name;

		Type(int metadata, String name) {

			this.metadata = metadata;
			this.name = name;
		}

		public int getMetadata() {

			return this.metadata;
		}

		@Override
		public String getName() {

			return this.name;
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
