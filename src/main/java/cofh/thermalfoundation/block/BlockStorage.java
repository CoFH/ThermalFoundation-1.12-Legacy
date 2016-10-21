package cofh.thermalfoundation.block;

import codechicken.lib.block.property.PropertyString;
import codechicken.lib.util.ArrayUtils;
import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockStorage extends Block {

    public static final String[] NAMES = { "copper", "tin", "silver", "lead", "nickel", "platinum", "mithril", "electrum", "invar", "bronze", "signalum", "lumium", "enderium" };
    public static final PropertyString TYPE = new PropertyString("type", NAMES);
    //public static final IIcon[] TEXTURES = new IIcon[NAMES.length];
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

    public BlockStorage() {

        super(Material.IRON);
        setHardness(5.0F);
        setResistance(10.0F);
        setSoundType(SoundType.METAL);
        setCreativeTab(ThermalFoundation.tabCommon);
        setUnlocalizedName("thermalfoundation.storage");

        setHarvestLevel("pickaxe", 2);
        setHarvestLevel("pickaxe", 1, getDefaultState().withProperty(TYPE, NAMES[0]));
        setHarvestLevel("pickaxe", 1, getDefaultState().withProperty(TYPE, NAMES[1]));
        setHarvestLevel("pickaxe", 3, getDefaultState().withProperty(TYPE, NAMES[6]));
        setHarvestLevel("pickaxe", 3, getDefaultState().withProperty(TYPE, NAMES[12]));
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
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {

        return getMetaFromState(blockState) == 10 ? 15 : 0;
    }

    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {

        return HARDNESS[getMetaFromState(blockState)];
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {

        return RESISTANCE[getMetaFromState(world.getBlockState(pos))];
    }

    @Override
    public int damageDropped(IBlockState state) {

        return getMetaFromState(state);
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type) {

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

    //@Override
    //public IIcon getIcon(int side, int metadata) {
    //    return TEXTURES[metadata];
    //}

    //@Override
    //@SideOnly(Side.CLIENT)
    //public void registerBlockIcons(IIconRegister ir) {
    //    for (int i = 0; i < NAMES.length; i++) {
    //        TEXTURES[i] = ir.registerIcon("thermalfoundation:storage/Block_" + StringHelper.titleCase(NAMES[i]));
    //    }
    //}

    /* IInitializer */
    //@Override
    public boolean preInit() {

        this.setRegistryName("storage");

        GameRegistry.register(this);
        ItemBlockStorage itemBlock = new ItemBlockStorage(this);
        itemBlock.setRegistryName(this.getRegistryName());
        GameRegistry.register(itemBlock);

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

    //@Override
    public boolean initialize() {

        return true;
    }

    //@Override
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

    @SideOnly(Side.CLIENT)
    public void registerModels() {
        for (int i = 0; i < NAMES.length; i++) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(this.getRegistryName(), "type=" + NAMES[i]));
        }
    }

}
