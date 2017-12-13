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
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static cofh.core.util.helpers.ItemHelper.registerWithHandlers;
import static cofh.core.util.helpers.RecipeHelper.addSmelting;

public class BlockOre extends BlockCore implements IInitializer, IModelRegister {

	public static final PropertyEnum<Type> VARIANT = PropertyEnum.create("type", Type.class);

	public BlockOre() {

		super(Material.ROCK, "thermalfoundation");

		setUnlocalizedName("ore");
		setCreativeTab(ThermalFoundation.tabCommon);

		setHardness(3.0F);
		setResistance(5.0F);
		setSoundType(SoundType.STONE);
		setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, Type.COPPER));

		setHarvestLevel("pickaxe", 2);
		setHarvestLevel("pickaxe", 1, getStateFromMeta(Type.COPPER.getMetadata()));
		setHarvestLevel("pickaxe", 1, getStateFromMeta(Type.TIN.getMetadata()));
		setHarvestLevel("pickaxe", 1, getStateFromMeta(Type.ALUMINUM.getMetadata()));
		setHarvestLevel("pickaxe", 3, getStateFromMeta(Type.PLATINUM.getMetadata()));
		setHarvestLevel("pickaxe", 3, getStateFromMeta(Type.IRIDIUM.getMetadata()));
		setHarvestLevel("pickaxe", 3, getStateFromMeta(Type.MITHRIL.getMetadata()));
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
	public boolean initialize() {

		this.setRegistryName("ore");
		ForgeRegistries.BLOCKS.register(this);

		ItemBlockOre itemBlock = new ItemBlockOre(this);
		itemBlock.setRegistryName(this.getRegistryName());
		ForgeRegistries.ITEMS.register(itemBlock);

		oreCopper = new ItemStack(this, 1, Type.COPPER.getMetadata());
		oreTin = new ItemStack(this, 1, Type.TIN.getMetadata());
		oreSilver = new ItemStack(this, 1, Type.SILVER.getMetadata());
		oreLead = new ItemStack(this, 1, Type.LEAD.getMetadata());
		oreAluminum = new ItemStack(this, 1, Type.ALUMINUM.getMetadata());
		oreNickel = new ItemStack(this, 1, Type.NICKEL.getMetadata());
		orePlatinum = new ItemStack(this, 1, Type.PLATINUM.getMetadata());
		oreIridium = new ItemStack(this, 1, Type.IRIDIUM.getMetadata());
		oreMithril = new ItemStack(this, 1, Type.MITHRIL.getMetadata());

		registerWithHandlers("oreCopper", oreCopper);
		registerWithHandlers("oreTin", oreTin);
		registerWithHandlers("oreSilver", oreSilver);
		registerWithHandlers("oreLead", oreLead);
		registerWithHandlers("oreAluminum", oreAluminum);
		registerWithHandlers("oreNickel", oreNickel);
		registerWithHandlers("orePlatinum", orePlatinum);
		registerWithHandlers("oreIridium", oreIridium);
		registerWithHandlers("oreMithril", oreMithril);

		ThermalFoundation.proxy.addIModelRegister(this);

		return true;
	}

	@Override
	public boolean register() {

		addSmelting(oreCopper, ItemMaterial.ingotCopper, 0.6F);
		addSmelting(oreTin, ItemMaterial.ingotTin, 0.7F);
		addSmelting(oreSilver, ItemMaterial.ingotSilver, 0.9F);
		addSmelting(oreLead, ItemMaterial.ingotLead, 0.8F);
		addSmelting(oreAluminum, ItemMaterial.ingotAluminum, 0.6F);
		addSmelting(oreNickel, ItemMaterial.ingotNickel, 1.0F);
		addSmelting(orePlatinum, ItemMaterial.ingotPlatinum, 1.0F);
		addSmelting(oreIridium, ItemMaterial.ingotIridium, 1.2F);
		addSmelting(oreMithril, ItemMaterial.ingotMithril, 1.5F);

		return true;
	}

	/* TYPE */
	public enum Type implements IStringSerializable {

		// @formatter:off
		COPPER(0, "copper"),
		TIN(1, "tin"),
		SILVER(2, "silver", 4),
		LEAD(3, "lead"),
		ALUMINUM(4, "aluminum"),
		NICKEL(5, "nickel"),
		PLATINUM(6, "platinum", 4, EnumRarity.UNCOMMON),
		IRIDIUM(7, "iridium", 4, EnumRarity.UNCOMMON),
		MITHRIL(8, "mithril", 8, EnumRarity.RARE);
		// @formatter: on

		private static final Type[] METADATA_LOOKUP = new Type[values().length];
		private final int metadata;
		private final String name;
		private final int light;
		private final EnumRarity rarity;

		Type(int metadata, String name, int light, EnumRarity rarity) {

			this.metadata = metadata;
			this.name = name;
			this.light = light;
			this.rarity = rarity;
		}

		Type(int metadata, String name, int light) {

			this(metadata, name, light, EnumRarity.COMMON);
		}

		Type(int metadata, String name) {

			this(metadata, name, 0, EnumRarity.COMMON);
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
	public static ItemStack oreCopper;
	public static ItemStack oreTin;
	public static ItemStack oreSilver;
	public static ItemStack oreLead;
	public static ItemStack oreAluminum;
	public static ItemStack oreNickel;
	public static ItemStack orePlatinum;
	public static ItemStack oreIridium;
	public static ItemStack oreMithril;

}
