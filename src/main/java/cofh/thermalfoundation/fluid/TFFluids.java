package cofh.thermalfoundation.fluid;

import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

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
        ResourceLocation flowTexture = new ResourceLocation("ThermalFoundation", "blocks/fluid/Fluid_Redstone_Flow");
        ResourceLocation stillTexture = new ResourceLocation("ThermalFoundation", "blocks/fluid/Fluid_Redstone_Still");
        fluidRedstone = new Fluid("redstone", stillTexture, flowTexture).setLuminosity(7).setDensity(1200).setViscosity(1500).setTemperature(300).setRarity(EnumRarity.UNCOMMON);

        flowTexture = new ResourceLocation("ThermalFoundation", "blocks/fluid/Fluid_Glowstone_Flow");
        stillTexture = new ResourceLocation("ThermalFoundation", "blocks/fluid/Fluid_Glowstone_Still");
        fluidGlowstone = new Fluid("glowstone", stillTexture, flowTexture).setLuminosity(15).setDensity(-500).setViscosity(100).setTemperature(300).setGaseous(true).setRarity(EnumRarity.UNCOMMON);

        flowTexture = new ResourceLocation("ThermalFoundation", "blocks/fluid/Fluid_Ender_Flow");
        stillTexture = new ResourceLocation("ThermalFoundation", "blocks/fluid/Fluid_Ender_Still");
        fluidEnder = new Fluid("ender", stillTexture, flowTexture).setLuminosity(3).setDensity(4000).setViscosity(3000).setTemperature(300).setRarity(EnumRarity.UNCOMMON);

        flowTexture = new ResourceLocation("ThermalFoundation", "blocks/fluid/Fluid_Pyrotheum_Flow");
        stillTexture = new ResourceLocation("ThermalFoundation", "blocks/fluid/Fluid_Pyrotheum_Still");
        fluidPyrotheum = new Fluid("pyrotheum", stillTexture, flowTexture).setLuminosity(15).setDensity(2000).setViscosity(1200).setTemperature(4000).setRarity(EnumRarity.RARE);

        flowTexture = new ResourceLocation("ThermalFoundation", "blocks/fluid/Fluid_Cryotheum_Flow");
        stillTexture = new ResourceLocation("ThermalFoundation", "blocks/fluid/Fluid_Cryotheum_Still");
        fluidCryotheum = new Fluid("cryotheum", stillTexture, flowTexture).setLuminosity(0).setDensity(4000).setViscosity(3000).setTemperature(50).setRarity(EnumRarity.RARE);

        flowTexture = new ResourceLocation("ThermalFoundation", "blocks/fluid/Fluid_Aerotheum_Flow");
        stillTexture = new ResourceLocation("ThermalFoundation", "blocks/fluid/Fluid_Aerotheum_Still");
        fluidAerotheum = new Fluid("aerotheum", stillTexture, flowTexture).setLuminosity(0).setDensity(-800).setViscosity(100).setTemperature(300).setGaseous(true).setRarity(EnumRarity.RARE);

        flowTexture = new ResourceLocation("ThermalFoundation", "blocks/fluid/Fluid_Petrotheum_Flow");
        stillTexture = new ResourceLocation("ThermalFoundation", "blocks/fluid/Fluid_Petrotheum_Still");
        fluidPetrotheum = new Fluid("petrotheum", stillTexture, flowTexture).setLuminosity(0).setDensity(4000).setViscosity(1500).setTemperature(400).setRarity(EnumRarity.RARE);

        flowTexture = new ResourceLocation("ThermalFoundation", "blocks/fluid/Fluid_Mana_Flow");
        stillTexture = new ResourceLocation("ThermalFoundation", "blocks/fluid/Fluid_Mana_Still");
        fluidMana = new Fluid("mana", stillTexture, flowTexture).setLuminosity(15).setDensity(600).setViscosity(6000).setTemperature(350).setRarity(EnumRarity.EPIC);

        flowTexture = new ResourceLocation("ThermalFoundation", "blocks/fluid/Fluid_Steam_Flow");
        stillTexture = new ResourceLocation("ThermalFoundation", "blocks/fluid/Fluid_Steam_Still");
        fluidSteam = new Fluid("steam", stillTexture, flowTexture).setLuminosity(0).setDensity(-1000).setViscosity(200).setTemperature(750).setGaseous(true);

        flowTexture = new ResourceLocation("ThermalFoundation", "blocks/fluid/Fluid_Coal_Flow");
        stillTexture = new ResourceLocation("ThermalFoundation", "blocks/fluid/Fluid_Coal_Still");
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

    public static void initialize() {

    }

    public static void postInit() {

    }

    public static void registerFluid(Fluid fluid, String fluidName) {

        if (!FluidRegistry.isFluidRegistered(fluidName)) {
            FluidRegistry.registerFluid(fluid);
        }
    }

    public static void registerDispenserHandlers() {

        //BlockDispenser.dispenseBehaviorRegistry.putObject(TFItems.itemBucket, new DispenserFilledBucketHandler());
        //BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bucket, new DispenserEmptyBucketHandler());
    }

}
