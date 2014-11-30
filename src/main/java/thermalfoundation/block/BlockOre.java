package thermalfoundation.block;

import cofh.api.core.IInitializer;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import thermalfoundation.ThermalFoundation;
import thermalfoundation.item.TFItems;

public class BlockOre extends Block implements IInitializer {

	public BlockOre() {

		super(Material.rock);
		setHardness(3.0F);
		setResistance(5.0F);
		setStepSound(soundTypeStone);
		setCreativeTab(ThermalFoundation.tab);
		setBlockName("thermalfoundation.ore");

		setHarvestLevel("pickaxe", 2);
		setHarvestLevel("pickaxe", 1, 0);
		setHarvestLevel("pickaxe", 1, 1);
		setHarvestLevel("pickaxe", 3, 6);
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
	public int damageDropped(int i) {

		return i;
	}

	@Override
	public IIcon getIcon(int side, int metadata) {

		return TEXTURES[metadata];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) {

		for (int i = 0; i < NAMES.length; i++) {
			TEXTURES[i] = ir.registerIcon("thermalfoundation:ore/Ore_" + StringHelper.titleCase(NAMES[i]));
		}
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		GameRegistry.registerBlock(this, ItemBlockOre.class, "Ore");

		oreCopper = new ItemStack(this, 1, 0);
		oreTin = new ItemStack(this, 1, 1);
		oreSilver = new ItemStack(this, 1, 2);
		oreLead = new ItemStack(this, 1, 3);
		oreNickel = new ItemStack(this, 1, 4);
		orePlatinum = new ItemStack(this, 1, 5);
		oreMithril = new ItemStack(this, 1, 6);

		ItemHelper.registerWithHandlers("oreCopper", oreCopper);
		ItemHelper.registerWithHandlers("oreTin", oreTin);
		ItemHelper.registerWithHandlers("oreSilver", oreSilver);
		ItemHelper.registerWithHandlers("oreLead", oreLead);
		ItemHelper.registerWithHandlers("oreNickel", oreNickel);
		ItemHelper.registerWithHandlers("orePlatinum", orePlatinum);
		ItemHelper.registerWithHandlers("oreMithril", oreMithril);

		return true;
	}

	@Override
	public boolean initialize() {

		return true;
	}

	@Override
	public boolean postInit() {

		ItemHelper.addSmelting(TFItems.ingotCopper, oreCopper, 0.6F);
		ItemHelper.addSmelting(TFItems.ingotTin, oreTin, 0.7F);
		ItemHelper.addSmelting(TFItems.ingotSilver, oreSilver, 0.9F);
		ItemHelper.addSmelting(TFItems.ingotLead, oreLead, 0.8F);
		ItemHelper.addSmelting(TFItems.ingotNickel, oreNickel, 1.0F);
		ItemHelper.addSmelting(TFItems.ingotPlatinum, orePlatinum, 1.0F);
		ItemHelper.addSmelting(TFItems.ingotMithril, oreMithril, 1.5F);

		return true;
	}

	public static final String[] NAMES = { "copper", "tin", "silver", "lead", "nickel", "platinum", "mithril" };
	public static final IIcon[] TEXTURES = new IIcon[NAMES.length];
	public static final int[] LIGHT = { 0, 0, 4, 0, 0, 4, 8 };
	public static final int[] RARITY = { 0, 0, 0, 0, 0, 1, 2 };

	public static ItemStack oreCopper;
	public static ItemStack oreTin;
	public static ItemStack oreSilver;
	public static ItemStack oreLead;
	public static ItemStack oreNickel;
	public static ItemStack orePlatinum;
	public static ItemStack oreMithril;

}
