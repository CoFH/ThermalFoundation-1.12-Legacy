package cofh.thermalfoundation.init;

import cofh.core.fluid.BlockFluidCore;
import cofh.core.fluid.FluidCore;
import cofh.core.util.core.IInitializer;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.fluid.*;
import net.minecraft.item.EnumRarity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.ArrayList;

public class TFFluids {

	private TFFluids() {

	}

	public static void preInit() {

		registerAllFluids();
		registerAllFluidBlocks();
		createBuckets();

		for (IInitializer init : initList) {
			init.preInit();
		}
	}

	public static void initialize() {

		for (IInitializer init : initList) {
			init.initialize();
		}
	}

	public static void postInit() {

		for (IInitializer init : initList) {
			init.postInit();
		}
		initList.clear();
	}

	/* HELPERS */
	public static void registerAllFluids() {

		FluidRegistry.registerFluid(new FluidCore("steam", "thermalfoundation").setLuminosity(0).setDensity(-1000).setViscosity(200).setTemperature(750).setGaseous(true));

		FluidRegistry.registerFluid(new FluidCore("creosote", "thermalfoundation").setLuminosity(0).setDensity(1100).setViscosity(2000).setTemperature(300));
		FluidRegistry.registerFluid(new FluidCore("coal", "thermalfoundation").setLuminosity(0).setDensity(900).setViscosity(2000).setTemperature(300));
		FluidRegistry.registerFluid(new FluidCore("crude_oil", "thermalfoundation").setLuminosity(0).setDensity(900).setViscosity(2000).setTemperature(300));
		FluidRegistry.registerFluid(new FluidCore("refined_oil", "thermalfoundation").setLuminosity(0).setDensity(800).setViscosity(1400).setTemperature(300));
		FluidRegistry.registerFluid(new FluidCore("fuel", "thermalfoundation").setLuminosity(0).setDensity(700).setViscosity(800).setTemperature(300));

		FluidRegistry.registerFluid(new FluidCore("resin", "thermalfoundation").setLuminosity(0).setDensity(900).setViscosity(3000).setTemperature(300));
		FluidRegistry.registerFluid(new FluidCore("tree_oil", "thermalfoundation").setLuminosity(0).setDensity(900).setViscosity(1200).setTemperature(300));

		FluidRegistry.registerFluid(new FluidCore("redstone", "thermalfoundation").setLuminosity(7).setDensity(1200).setViscosity(1500).setTemperature(300).setRarity(EnumRarity.UNCOMMON));
		FluidRegistry.registerFluid(new FluidCore("glowstone", "thermalfoundation").setLuminosity(15).setDensity(-500).setViscosity(100).setTemperature(300).setGaseous(true).setRarity(EnumRarity.UNCOMMON));
		FluidRegistry.registerFluid(new FluidCore("ender", "thermalfoundation").setLuminosity(3).setDensity(4000).setViscosity(2500).setTemperature(300).setRarity(EnumRarity.UNCOMMON));
		FluidRegistry.registerFluid(new FluidCore("pyrotheum", "thermalfoundation").setLuminosity(15).setDensity(2000).setViscosity(1200).setTemperature(4000).setRarity(EnumRarity.RARE));
		FluidRegistry.registerFluid(new FluidCore("cryotheum", "thermalfoundation").setLuminosity(0).setDensity(4000).setViscosity(4000).setTemperature(50).setRarity(EnumRarity.RARE));
		FluidRegistry.registerFluid(new FluidCore("aerotheum", "thermalfoundation").setLuminosity(0).setDensity(-800).setViscosity(100).setTemperature(300).setGaseous(true).setRarity(EnumRarity.RARE));
		FluidRegistry.registerFluid(new FluidCore("petrotheum", "thermalfoundation").setLuminosity(0).setDensity(4000).setViscosity(1500).setTemperature(400).setRarity(EnumRarity.RARE));
		FluidRegistry.registerFluid(new FluidCore("mana", "thermalfoundation").setLuminosity(15).setDensity(600).setViscosity(6000).setTemperature(350).setRarity(EnumRarity.EPIC));

		fluidSteam = FluidRegistry.getFluid("steam");

		fluidCreosote = FluidRegistry.getFluid("creosote");
		fluidCoal = FluidRegistry.getFluid("coal");
		fluidCrudeOil = FluidRegistry.getFluid("crude_oil");
		fluidRefinedOil = FluidRegistry.getFluid("refined_oil");
		fluidFuel = FluidRegistry.getFluid("fuel");

		fluidResin = FluidRegistry.getFluid("resin");
		fluidTreeOil = FluidRegistry.getFluid("tree_oil");

		fluidRedstone = FluidRegistry.getFluid("redstone");
		fluidGlowstone = FluidRegistry.getFluid("glowstone");
		fluidEnder = FluidRegistry.getFluid("ender");
		fluidPyrotheum = FluidRegistry.getFluid("pyrotheum");
		fluidCryotheum = FluidRegistry.getFluid("cryotheum");
		fluidAerotheum = FluidRegistry.getFluid("aerotheum");
		fluidPetrotheum = FluidRegistry.getFluid("petrotheum");
		fluidMana = FluidRegistry.getFluid("mana");
	}

	public static void registerAllFluidBlocks() {

		blockFluidCrudeOil = new BlockFluidCrudeOil(fluidCrudeOil);
		blockFluidRedstone = new BlockFluidRedstone(fluidRedstone);
		blockFluidGlowstone = new BlockFluidGlowstone(fluidGlowstone);
		blockFluidEnder = new BlockFluidEnder(fluidEnder);
		blockFluidPyrotheum = new BlockFluidPyrotheum(fluidPyrotheum);
		blockFluidCryotheum = new BlockFluidCryotheum(fluidCryotheum);
		blockFluidAerotheum = new BlockFluidAerotheum(fluidAerotheum);
		blockFluidPetrotheum = new BlockFluidPetrotheum(fluidPetrotheum);
		blockFluidMana = new BlockFluidMana(fluidMana);

		initList.add(blockFluidCrudeOil);
		initList.add(blockFluidRedstone);
		initList.add(blockFluidGlowstone);
		initList.add(blockFluidEnder);
		initList.add(blockFluidPyrotheum);
		initList.add(blockFluidCryotheum);
		initList.add(blockFluidAerotheum);
		initList.add(blockFluidPetrotheum);
		initList.add(blockFluidMana);

		ThermalFoundation.proxy.addIModelRegister(blockFluidCrudeOil);
		ThermalFoundation.proxy.addIModelRegister(blockFluidRedstone);
		ThermalFoundation.proxy.addIModelRegister(blockFluidGlowstone);
		ThermalFoundation.proxy.addIModelRegister(blockFluidEnder);
		ThermalFoundation.proxy.addIModelRegister(blockFluidPyrotheum);
		ThermalFoundation.proxy.addIModelRegister(blockFluidCryotheum);
		ThermalFoundation.proxy.addIModelRegister(blockFluidAerotheum);
		ThermalFoundation.proxy.addIModelRegister(blockFluidPetrotheum);
		ThermalFoundation.proxy.addIModelRegister(blockFluidMana);
	}

	public static void createBuckets() {

		FluidRegistry.addBucketForFluid(fluidSteam);

		FluidRegistry.addBucketForFluid(fluidCreosote);
		FluidRegistry.addBucketForFluid(fluidCoal);
		FluidRegistry.addBucketForFluid(fluidCrudeOil);
		FluidRegistry.addBucketForFluid(fluidRefinedOil);
		FluidRegistry.addBucketForFluid(fluidFuel);
		FluidRegistry.addBucketForFluid(fluidResin);
		FluidRegistry.addBucketForFluid(fluidTreeOil);

		FluidRegistry.addBucketForFluid(fluidRedstone);
		FluidRegistry.addBucketForFluid(fluidGlowstone);
		FluidRegistry.addBucketForFluid(fluidEnder);
		FluidRegistry.addBucketForFluid(fluidPyrotheum);
		FluidRegistry.addBucketForFluid(fluidCryotheum);
		FluidRegistry.addBucketForFluid(fluidAerotheum);
		FluidRegistry.addBucketForFluid(fluidPetrotheum);
		FluidRegistry.addBucketForFluid(fluidMana);
	}

	private static ArrayList<IInitializer> initList = new ArrayList<>();

	/* REFERENCES */
	public static Fluid fluidSteam;

	public static Fluid fluidCreosote;
	public static Fluid fluidCoal;
	public static Fluid fluidCrudeOil;
	public static Fluid fluidRefinedOil;
	public static Fluid fluidFuel;

	public static Fluid fluidResin;
	public static Fluid fluidTreeOil;

	public static Fluid fluidRedstone;
	public static Fluid fluidGlowstone;
	public static Fluid fluidEnder;
	public static Fluid fluidPyrotheum;
	public static Fluid fluidCryotheum;
	public static Fluid fluidAerotheum;
	public static Fluid fluidPetrotheum;
	public static Fluid fluidMana;

	public static BlockFluidCore blockFluidCrudeOil;
	public static BlockFluidCore blockFluidRedstone;
	public static BlockFluidCore blockFluidGlowstone;
	public static BlockFluidCore blockFluidEnder;
	public static BlockFluidCore blockFluidPyrotheum;
	public static BlockFluidCore blockFluidCryotheum;
	public static BlockFluidCore blockFluidAerotheum;
	public static BlockFluidCore blockFluidPetrotheum;
	public static BlockFluidCore blockFluidMana;

}

