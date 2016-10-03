package cofh.thermalfoundation.block;

import static cofh.lib.util.helpers.ItemHelper.*;

import cofh.api.core.IInitializer;
import cofh.api.core.IModelRegister;
import cofh.core.block.BlockCoFHBase;
import cofh.core.util.RegistryHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.item.ItemMaterial;

import java.util.List;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOre extends BlockCoFHBase implements IInitializer, IModelRegister {

	public static final PropertyEnum<BlockOre.Type> VARIANT = PropertyEnum.<BlockOre.Type> create("type", BlockOre.Type.class);

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
		setHarvestLevel("pickaxe", 3, getStateFromMeta(Type.MITHRIL.getMetadata()));
	}

	@Override
	protected BlockStateContainer createBlockState() {

		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {

		for (int i = 0; i < Type.METADATA_LOOKUP.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {

		return this.getDefaultState().withProperty(VARIANT, BlockOre.Type.byMetadata(meta));
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
	public int getLightValue(IBlockState state) {
		return Type.byMetadata(state.getBlock().getMetaFromState(state)).light;
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

		RegistryHelper.registerBlockAndItem(this, new ResourceLocation(ThermalFoundation.modId, "ore"), ItemBlockOre::new);

		oreCopper = new ItemStack(this, 1, Type.COPPER.getMetadata());
		oreTin = new ItemStack(this, 1, Type.TIN.getMetadata());
		oreSilver = new ItemStack(this, 1, Type.SILVER.getMetadata());
		oreLead = new ItemStack(this, 1, Type.LEAD.getMetadata());
		oreNickel = new ItemStack(this, 1, Type.NICKEL.getMetadata());
		orePlatinum = new ItemStack(this, 1, Type.PLATINUM.getMetadata());
		oreMithril = new ItemStack(this, 1, Type.MITHRIL.getMetadata());

		registerWithHandlers("oreCopper", oreCopper);
		registerWithHandlers("oreTin", oreTin);
		registerWithHandlers("oreSilver", oreSilver);
		registerWithHandlers("oreLead", oreLead);
		registerWithHandlers("oreNickel", oreNickel);
		registerWithHandlers("orePlatinum", orePlatinum);
		registerWithHandlers("oreMithril", oreMithril);

		return true;
	}

	@Override
	public boolean initialize() {

		return true;
	}

	@Override
	public boolean postInit() {

		addSmelting(ItemMaterial.ingotCopper, oreCopper, 0.6F);
		addSmelting(ItemMaterial.ingotTin, oreTin, 0.7F);
		addSmelting(ItemMaterial.ingotSilver, oreSilver, 0.9F);
		addSmelting(ItemMaterial.ingotLead, oreLead, 0.8F);
		addSmelting(ItemMaterial.ingotNickel, oreNickel, 1.0F);
		addSmelting(ItemMaterial.ingotPlatinum, orePlatinum, 1.0F);
		addSmelting(ItemMaterial.ingotMithril, oreMithril, 1.5F);

		return true;
	}

	/* TYPE */
	public static enum Type implements IStringSerializable {

		// @formatter:off
		COPPER(0, "copper", oreCopper),
		TIN(1, "tin", oreTin),
		SILVER(2, "silver", oreSilver, 4),
		LEAD(3, "lead", oreLead),
		NICKEL(4, "nickel", oreNickel),
		PLATINUM(5, "platinum", orePlatinum, 4, EnumRarity.UNCOMMON),
		MITHRIL(6, "mithril", oreMithril, 8, EnumRarity.RARE);
		// @formatter: on

		private static final BlockOre.Type[] METADATA_LOOKUP = new BlockOre.Type[values().length];
		private final int metadata;
		private final String name;
		private final ItemStack stack;

		private final int light;
		private final EnumRarity rarity;

		private Type(int metadata, String name, ItemStack stack, int light, EnumRarity rarity) {

			this.metadata = metadata;
			this.name = name;
			this.stack = stack;

			this.light = light;
			this.rarity = rarity;
		}

		private Type(int metadata, String name, ItemStack stack, int light) {

			this(metadata, name, stack, light, EnumRarity.COMMON);
		}

		private Type(int metadata, String name, ItemStack stack) {

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
	public static ItemStack oreCopper;
	public static ItemStack oreTin;
	public static ItemStack oreSilver;
	public static ItemStack oreLead;
	public static ItemStack oreNickel;
	public static ItemStack orePlatinum;
	public static ItemStack oreMithril;

}
