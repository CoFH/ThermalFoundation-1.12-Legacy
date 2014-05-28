package thermalfoundation.fluid;

import cofh.util.fluid.DispenserEmptyBucketHandler;
import cofh.util.fluid.DispenserFilledBucketHandler;

import net.minecraft.block.BlockDispenser;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import thermalfoundation.item.TFItems;

public class TFFluids {

	public static void preInit() {

		fluidRedstone = new Fluid("redstone").setLuminosity(7).setDensity(1200).setViscosity(1500).setTemperature(350).setRarity(EnumRarity.uncommon);
		fluidGlowstone = new Fluid("glowstone").setLuminosity(15).setDensity(-500).setViscosity(100).setTemperature(350).setGaseous(true)
				.setRarity(EnumRarity.uncommon);
		fluidEnder = new Fluid("ender").setLuminosity(3).setDensity(4000).setViscosity(3000).setTemperature(300).setRarity(EnumRarity.uncommon);
		fluidPyrotheum = new Fluid("pyrotheum").setLuminosity(15).setDensity(2000).setViscosity(1200).setTemperature(4000).setRarity(EnumRarity.rare);
		fluidCryotheum = new Fluid("cryotheum").setLuminosity(0).setDensity(4000).setViscosity(3000).setTemperature(50).setRarity(EnumRarity.rare);
		fluidMana = new Fluid("mana").setLuminosity(15).setDensity(600).setViscosity(6000).setTemperature(350).setRarity(EnumRarity.epic);
		fluidSteam = new Fluid("steam").setLuminosity(0).setDensity(-1000).setViscosity(200).setTemperature(1100).setGaseous(true);
		fluidCoal = new Fluid("coal").setLuminosity(0).setDensity(900).setViscosity(2000).setTemperature(300);

		FluidRegistry.registerFluid(fluidRedstone);
		FluidRegistry.registerFluid(fluidGlowstone);
		FluidRegistry.registerFluid(fluidEnder);
		FluidRegistry.registerFluid(fluidPyrotheum);
		FluidRegistry.registerFluid(fluidCryotheum);
		FluidRegistry.registerFluid(fluidMana);
		FluidRegistry.registerFluid(fluidSteam);
		FluidRegistry.registerFluid(fluidCoal);

	}

	public static void initialize() {

	}

	public static void postInit() {

	}

	public static void registerDispenserHandlers() {

		BlockDispenser.dispenseBehaviorRegistry.putObject(TFItems.itemBucket, new DispenserFilledBucketHandler());
		BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bucket, new DispenserEmptyBucketHandler());
	}

	public static Fluid fluidRedstone;
	public static Fluid fluidGlowstone;
	public static Fluid fluidEnder;
	public static Fluid fluidPyrotheum;
	public static Fluid fluidCryotheum;
	public static Fluid fluidMana;
	public static Fluid fluidSteam;
	public static Fluid fluidCoal;

}
