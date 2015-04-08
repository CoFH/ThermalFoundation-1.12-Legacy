package cofh.thermalfoundation.block;

import cofh.api.core.IInitializer;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStorage extends Block implements IInitializer {

	public BlockStorage() {

		super(Material.iron);
		setHardness(5.0F);
		setResistance(10.0F);
		setStepSound(soundTypeMetal);
		setCreativeTab(ThermalFoundation.tabCommon);
		setBlockName("thermalfoundation.storage");

		setHarvestLevel("pickaxe", 2);
		setHarvestLevel("pickaxe", 1, 0);
		setHarvestLevel("pickaxe", 1, 1);
		setHarvestLevel("pickaxe", 3, 6);
		setHarvestLevel("pickaxe", 3, 12);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {

		for (int i = 0; i < NAMES.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {

		return LIGHT[world.getBlockMetadata(x, y, z)];
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {

		return world.getBlockMetadata(x, y, z) == 10 ? 15 : 0;
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {

		return HARDNESS[world.getBlockMetadata(x, y, z)];
	}

	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {

		return RESISTANCE[world.getBlockMetadata(x, y, z)];
	}

	@Override
	public int damageDropped(int i) {

		return i;
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {

		return false;
	}

	@Override
	public boolean canProvidePower() {

		return true;
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {

		return true;
	}

	@Override
	public IIcon getIcon(int side, int metadata) {

		return TEXTURES[metadata];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) {

		for (int i = 0; i < NAMES.length; i++) {
			TEXTURES[i] = ir.registerIcon("thermalfoundation:storage/Block_" + StringHelper.titleCase(NAMES[i]));
		}
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		GameRegistry.registerBlock(this, ItemBlockStorage.class, "Storage");

		blockCopper = new ItemStack(this, 1, 0);
		blockTin = new ItemStack(this, 1, 1);
		blockSilver = new ItemStack(this, 1, 2);
		blockLead = new ItemStack(this, 1, 3);
		blockNickel = new ItemStack(this, 1, 4);
		blockPlatinum = new ItemStack(this, 1, 5);
		blockMithril = new ItemStack(this, 1, 6);
		blockElectrum = new ItemStack(this, 1, 7);
		blockInvar = new ItemStack(this, 1, 8);
		blockBronze = new ItemStack(this, 1, 9);
		blockSignalum = new ItemStack(this, 1, 10);
		blockLumium = new ItemStack(this, 1, 11);
		blockEnderium = new ItemStack(this, 1, 12);

		ItemHelper.registerWithHandlers("blockCopper", blockCopper);
		ItemHelper.registerWithHandlers("blockTin", blockTin);
		ItemHelper.registerWithHandlers("blockSilver", blockSilver);
		ItemHelper.registerWithHandlers("blockLead", blockLead);
		ItemHelper.registerWithHandlers("blockNickel", blockNickel);
		ItemHelper.registerWithHandlers("blockPlatinum", blockPlatinum);
		ItemHelper.registerWithHandlers("blockMithril", blockMithril);
		ItemHelper.registerWithHandlers("blockElectrum", blockElectrum);
		ItemHelper.registerWithHandlers("blockInvar", blockInvar);
		ItemHelper.registerWithHandlers("blockBronze", blockBronze);
		ItemHelper.registerWithHandlers("blockSignalum", blockSignalum);
		ItemHelper.registerWithHandlers("blockLumium", blockLumium);
		ItemHelper.registerWithHandlers("blockEnderium", blockEnderium);

		return true;
	}

	@Override
	public boolean initialize() {

		return true;
	}

	@Override
	public boolean postInit() {

		ItemHelper.addStorageRecipe(blockCopper, "ingotCopper");
		ItemHelper.addStorageRecipe(blockTin, "ingotTin");
		ItemHelper.addStorageRecipe(blockSilver, "ingotSilver");
		ItemHelper.addStorageRecipe(blockLead, "ingotLead");
		ItemHelper.addStorageRecipe(blockNickel, "ingotNickel");
		ItemHelper.addStorageRecipe(blockPlatinum, "ingotPlatinum");
		ItemHelper.addStorageRecipe(blockMithril, "ingotMithril");
		ItemHelper.addStorageRecipe(blockElectrum, "ingotElectrum");
		ItemHelper.addStorageRecipe(blockInvar, "ingotInvar");
		ItemHelper.addStorageRecipe(blockBronze, "ingotBronze");
		ItemHelper.addStorageRecipe(blockSignalum, "ingotSignalum");
		ItemHelper.addStorageRecipe(blockLumium, "ingotLumium");
		ItemHelper.addStorageRecipe(blockEnderium, "ingotEnderium");

		return true;
	}

	public static final String[] NAMES = { "copper", "tin", "silver", "lead", "nickel", "platinum", "mithril", "electrum", "invar", "bronze", "signalum",
			"lumium", "enderium" };
	public static final IIcon[] TEXTURES = new IIcon[NAMES.length];
	public static final int[] LIGHT = { 0, 0, 4, 0, 0, 4, 8, 0, 0, 0, 7, 15, 4 };
	public static final float[] HARDNESS = { 5, 5, 5, 4, 10, 5, 30, 4, 20, 5, 5, 5, 40 };
	public static final float[] RESISTANCE = { 6, 6, 6, 12, 6, 6, 120, 6, 12, 6, 9, 9, 120 };
	public static final int[] RARITY = { 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 1, 1, 2 };

	public static ItemStack blockCopper;
	public static ItemStack blockTin;
	public static ItemStack blockSilver;
	public static ItemStack blockLead;
	public static ItemStack blockNickel;
	public static ItemStack blockPlatinum;
	public static ItemStack blockMithril;
	public static ItemStack blockElectrum;
	public static ItemStack blockInvar;
	public static ItemStack blockBronze;
	public static ItemStack blockSignalum;
	public static ItemStack blockLumium;
	public static ItemStack blockEnderium;

}
