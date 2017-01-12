package cofh.thermalfoundation.fluid;

import cofh.thermalfoundation.block.TFBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class TFFluids {

    public static Fluid fluidRedstone;
    public static Fluid fluidGlowstone;
    public static Fluid fluidEnder;
    public static Fluid fluidPyrotheum;
    public static Fluid fluidCryotheum;
    public static Fluid fluidAerotheum;
    public static Fluid fluidPetrotheum;
    public static Fluid fluidMana;
    public static Fluid fluidSteam;
    public static Fluid fluidCoal;

    public static void preInit() {
        ResourceLocation flowTexture = new ResourceLocation("thermalfoundation", "blocks/fluid/redstone_flow");
        ResourceLocation stillTexture = new ResourceLocation("thermalfoundation", "blocks/fluid/redstone_still");
        fluidRedstone = new Fluid("redstone", stillTexture, flowTexture).setLuminosity(7).setDensity(1200).setViscosity(1500).setTemperature(300).setRarity(EnumRarity.UNCOMMON);

        flowTexture = new ResourceLocation("thermalfoundation", "blocks/fluid/glowstone_flow");
        stillTexture = new ResourceLocation("thermalfoundation", "blocks/fluid/glowstone_still");
        fluidGlowstone = new Fluid("glowstone", stillTexture, flowTexture).setLuminosity(15).setDensity(-500).setViscosity(100).setTemperature(300).setGaseous(true).setRarity(EnumRarity.UNCOMMON);

        flowTexture = new ResourceLocation("thermalfoundation", "blocks/fluid/ender_flow");
        stillTexture = new ResourceLocation("thermalfoundation", "blocks/fluid/ender_still");
        fluidEnder = new Fluid("ender", stillTexture, flowTexture).setLuminosity(3).setDensity(4000).setViscosity(3000).setTemperature(300).setRarity(EnumRarity.UNCOMMON);

        flowTexture = new ResourceLocation("thermalfoundation", "blocks/fluid/pyrotheum_flow");
        stillTexture = new ResourceLocation("thermalfoundation", "blocks/fluid/pyrotheum_still");
        fluidPyrotheum = new Fluid("pyrotheum", stillTexture, flowTexture).setLuminosity(15).setDensity(2000).setViscosity(1200).setTemperature(4000).setRarity(EnumRarity.RARE);

        flowTexture = new ResourceLocation("thermalfoundation", "blocks/fluid/cryotheum_flow");
        stillTexture = new ResourceLocation("thermalfoundation", "blocks/fluid/cryotheum_still");
        fluidCryotheum = new Fluid("cryotheum", stillTexture, flowTexture).setBlock(TFBlocks.blockFluidCryotheum).setLuminosity(0).setDensity(4000).setViscosity(3000).setTemperature(50).setRarity(EnumRarity.RARE);

        flowTexture = new ResourceLocation("thermalfoundation", "blocks/fluid/aerotheum_flow");
        stillTexture = new ResourceLocation("thermalfoundation", "blocks/fluid/aerotheum_still");
        fluidAerotheum = new Fluid("aerotheum", stillTexture, flowTexture).setLuminosity(0).setDensity(-800).setViscosity(100).setTemperature(300).setGaseous(true).setRarity(EnumRarity.RARE);

        flowTexture = new ResourceLocation("thermalfoundation", "blocks/fluid/petrotheum_flow");
        stillTexture = new ResourceLocation("thermalfoundation", "blocks/fluid/petrotheum_still");
        fluidPetrotheum = new Fluid("petrotheum", stillTexture, flowTexture).setLuminosity(0).setDensity(4000).setViscosity(1500).setTemperature(400).setRarity(EnumRarity.RARE);

        flowTexture = new ResourceLocation("thermalfoundation", "blocks/fluid/mana_flow");
        stillTexture = new ResourceLocation("thermalfoundation", "blocks/fluid/mana_still");
        fluidMana = new Fluid("mana", stillTexture, flowTexture).setLuminosity(15).setDensity(600).setViscosity(6000).setTemperature(350).setRarity(EnumRarity.EPIC);

        flowTexture = new ResourceLocation("thermalfoundation", "blocks/fluid/steam_flow");
        stillTexture = new ResourceLocation("thermalfoundation", "blocks/fluid/steam_still");
        fluidSteam = new Fluid("steam", stillTexture, flowTexture).setLuminosity(0).setDensity(-1000).setViscosity(200).setTemperature(750).setGaseous(true);

        flowTexture = new ResourceLocation("thermalfoundation", "blocks/fluid/coal_flow");
        stillTexture = new ResourceLocation("thermalfoundation", "blocks/fluid/coal_still");
        fluidCoal = new Fluid("coal", stillTexture, flowTexture).setLuminosity(0).setDensity(900).setViscosity(2000).setTemperature(300);

        registerFluid(fluidRedstone, "redstone");
        registerFluid(fluidGlowstone, "glowstone");
        registerFluid(fluidEnder, "ender");
        registerFluid(fluidPyrotheum, "pyrotheum");
        registerFluid(fluidCryotheum, "cryotheum");
        registerFluid(fluidAerotheum, "aerotheum");
        registerFluid(fluidPetrotheum, "petrotheum");
        registerFluid(fluidMana, "mana");
        registerFluid(fluidSteam, "steam");
        registerFluid(fluidCoal, "coal");
    }

    public static void initialize() {}

    public static void postInit() {}

    public static void registerFluid(Fluid fluid, String fluidName) {
        if (!FluidRegistry.isFluidRegistered(fluidName)) {
            FluidRegistry.registerFluid(fluid);
        }
    }

    public static void registerDispenserHandlers() {
        //BlockDispenser.dispenseBehaviorRegistry.putObject(TFItems.itemBucket, new DispenserFilledBucketHandler());
        //BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bucket, new DispenserEmptyBucketHandler());
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        registerFluidModels(fluidRedstone);
        registerFluidModels(fluidGlowstone);
        registerFluidModels(fluidEnder);
        registerFluidModels(fluidPyrotheum);
        registerFluidModels(fluidCryotheum);
        registerFluidModels(fluidAerotheum);
        registerFluidModels(fluidPetrotheum);
        registerFluidModels(fluidMana);
        registerFluidModels(fluidSteam);
        registerFluidModels(fluidCoal);
    }

    @SideOnly(Side.CLIENT)
    public static void registerFluidModels(Fluid fluid) {
        if(fluid == null) {
            return;
        }

        Block block = fluid.getBlock();
        if(block != null) {
            Item item = Item.getItemFromBlock(block);
            FluidStateMapper mapper = new FluidStateMapper(fluid);

            if(item != null) {
                ModelLoader.registerItemVariants(item);
                ModelLoader.setCustomMeshDefinition(item, mapper);
            }

            ModelLoader.setCustomStateMapper(block, mapper);
        }
    }

    @SideOnly(Side.CLIENT)
    public static class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {

        public final Fluid fluid;
        public final ModelResourceLocation location;

        public FluidStateMapper(Fluid fluid) {
            this.fluid = fluid;
            this.location = new ModelResourceLocation("thermalfoundation:fluid", fluid.getName());
        }

        @Nonnull
        @Override
        protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
            return location;
        }

        @Nonnull
        @Override
        public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
            return location;
        }
    }
}

