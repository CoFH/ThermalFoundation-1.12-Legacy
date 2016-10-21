package cofh.thermalfoundation.block;

import codechicken.lib.block.property.PropertyString;
import codechicken.lib.util.ArrayUtils;
import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.item.TFItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockOre extends Block {

    public static final String[] NAMES = { "copper", "tin", "silver", "lead", "nickel", "platinum", "mithril" };
    public static final PropertyString TYPE = new PropertyString("type", NAMES);
    //public static final IIcon[] TEXTURES = new IIcon[NAMES.length];
    public static final int[] LIGHT = { 0, 0, 4, 0, 0, 4, 8 };
    public static final int[] RARITY = { 0, 0, 0, 0, 0, 1, 2 };

    public static ItemStack oreCopper;
    public static ItemStack oreTin;
    public static ItemStack oreSilver;
    public static ItemStack oreLead;
    public static ItemStack oreNickel;
    public static ItemStack orePlatinum;
    public static ItemStack oreMithril;

    public BlockOre() {

        super(Material.ROCK);
        setHardness(3.0F);
        setResistance(5.0F);
        setSoundType(SoundType.STONE);
        setCreativeTab(ThermalFoundation.tabCommon);
        setUnlocalizedName("thermalfoundation.ore");

        setHarvestLevel("pickaxe", 2);
        setHarvestLevel("pickaxe", 1, getDefaultState().withProperty(TYPE, NAMES[0]));
        setHarvestLevel("pickaxe", 1, getDefaultState().withProperty(TYPE, NAMES[1]));
        setHarvestLevel("pickaxe", 3, getDefaultState().withProperty(TYPE, NAMES[6]));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getBlockState().getBaseState().withProperty(TYPE, NAMES[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ArrayUtils.indexOf(NAMES, state.getValue(TYPE));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < NAMES.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return LIGHT[getMetaFromState(state)];
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    /* IInitializer */
    //@Override
    public boolean preInit() {
        this.setRegistryName("ore");

        GameRegistry.register(this);
        ItemBlockOre itemBlock = new ItemBlockOre(this);
        itemBlock.setRegistryName(this.getRegistryName());
        GameRegistry.register(itemBlock);

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

    //@Override
    public boolean initialize() {

        return true;
    }

    //@Override
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

    @SideOnly(Side.CLIENT)
    public void registerModels() {
        for (int i = 0; i < NAMES.length; i++) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(this.getRegistryName(), "type=" + NAMES[i]));
        }
    }

}
