package cofh.thermalfoundation.init;

import cofh.api.core.IInitializer;
import cofh.core.fluid.BlockFluidCore;
import cofh.core.fluid.FluidCore;
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

		for (int i = 0; i < initList.size(); i++) {
			initList.get(i).preInit();
		}

	}

	public static void initialize() {

		for (int i = 0; i < initList.size(); i++) {
			initList.get(i).initialize();
		}
	}

	public static void postInit() {

		for (int i = 0; i < initList.size(); i++) {
			initList.get(i).postInit();
		}
	}

	/* HELPERS */
	public static void registerAllFluids() {

		FluidRegistry.registerFluid(new FluidCore("steam", "thermalfoundation").setLuminosity(0).setDensity(-1000).setViscosity(200).setTemperature(750).setGaseous(true));
		FluidRegistry.registerFluid(new FluidCore("coal", "thermalfoundation").setLuminosity(0).setDensity(900).setViscosity(2000).setTemperature(300));
		FluidRegistry.registerFluid(new FluidCore("redstone", "thermalfoundation").setLuminosity(7).setDensity(1200).setViscosity(1500).setTemperature(300).setRarity(EnumRarity.UNCOMMON));
		FluidRegistry.registerFluid(new FluidCore("glowstone", "thermalfoundation").setLuminosity(15).setDensity(-500).setViscosity(100).setTemperature(300).setGaseous(true).setRarity(EnumRarity.UNCOMMON));
		FluidRegistry.registerFluid(new FluidCore("ender", "thermalfoundation").setLuminosity(3).setDensity(4000).setViscosity(3000).setTemperature(300).setRarity(EnumRarity.UNCOMMON));
		FluidRegistry.registerFluid(new FluidCore("pyrotheum", "thermalfoundation").setLuminosity(15).setDensity(2000).setViscosity(1200).setTemperature(4000).setRarity(EnumRarity.RARE));
		FluidRegistry.registerFluid(new FluidCore("cryotheum", "thermalfoundation").setLuminosity(0).setDensity(4000).setViscosity(3000).setTemperature(50).setRarity(EnumRarity.RARE));
		FluidRegistry.registerFluid(new FluidCore("aerotheum", "thermalfoundation").setLuminosity(0).setDensity(-800).setViscosity(100).setTemperature(300).setGaseous(true).setRarity(EnumRarity.RARE));
		FluidRegistry.registerFluid(new FluidCore("petrotheum", "thermalfoundation").setLuminosity(0).setDensity(4000).setViscosity(1500).setTemperature(400).setRarity(EnumRarity.RARE));
		FluidRegistry.registerFluid(new FluidCore("mana", "thermalfoundation").setLuminosity(15).setDensity(600).setViscosity(6000).setTemperature(350).setRarity(EnumRarity.EPIC));

		fluidSteam = FluidRegistry.getFluid("steam");
		fluidCoal = FluidRegistry.getFluid("coal");
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

		blockFluidSteam = new BlockFluidSteam(fluidSteam);
		blockFluidCoal = new BlockFluidCoal(fluidCoal);
		blockFluidRedstone = new BlockFluidRedstone(fluidRedstone);
		blockFluidGlowstone = new BlockFluidGlowstone(fluidGlowstone);
		blockFluidEnder = new BlockFluidEnder(fluidEnder);
		blockFluidPyrotheum = new BlockFluidPyrotheum(fluidPyrotheum);
		blockFluidCryotheum = new BlockFluidCryotheum(fluidCryotheum);
		blockFluidAerotheum = new BlockFluidAerotheum(fluidAerotheum);
		blockFluidPetrotheum = new BlockFluidPetrotheum(fluidPetrotheum);
		blockFluidMana = new BlockFluidMana(fluidMana);

		initList.add(blockFluidSteam);
		initList.add(blockFluidCoal);
		initList.add(blockFluidRedstone);
		initList.add(blockFluidGlowstone);
		initList.add(blockFluidEnder);
		initList.add(blockFluidPyrotheum);
		initList.add(blockFluidCryotheum);
		initList.add(blockFluidAerotheum);
		initList.add(blockFluidPetrotheum);
		initList.add(blockFluidMana);

		ThermalFoundation.proxy.addIModelRegister(blockFluidSteam);
		ThermalFoundation.proxy.addIModelRegister(blockFluidCoal);
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
		FluidRegistry.addBucketForFluid(fluidCoal);
		FluidRegistry.addBucketForFluid(fluidRedstone);
		FluidRegistry.addBucketForFluid(fluidGlowstone);
		FluidRegistry.addBucketForFluid(fluidEnder);
		FluidRegistry.addBucketForFluid(fluidPyrotheum);
		FluidRegistry.addBucketForFluid(fluidCryotheum);
		FluidRegistry.addBucketForFluid(fluidAerotheum);
		FluidRegistry.addBucketForFluid(fluidPetrotheum);
		FluidRegistry.addBucketForFluid(fluidMana);
	}

	static ArrayList<IInitializer> initList = new ArrayList<IInitializer>();

	/* REFERENCES */
	public static Fluid fluidSteam;
	public static Fluid fluidCoal;
	public static Fluid fluidRedstone;
	public static Fluid fluidGlowstone;
	public static Fluid fluidEnder;
	public static Fluid fluidPyrotheum;
	public static Fluid fluidCryotheum;
	public static Fluid fluidAerotheum;
	public static Fluid fluidPetrotheum;
	public static Fluid fluidMana;

	public static BlockFluidCore blockFluidSteam;
	public static BlockFluidCore blockFluidCoal;
	public static BlockFluidCore blockFluidRedstone;
	public static BlockFluidCore blockFluidGlowstone;
	public static BlockFluidCore blockFluidEnder;
	public static BlockFluidCore blockFluidPyrotheum;
	public static BlockFluidCore blockFluidCryotheum;
	public static BlockFluidCore blockFluidAerotheum;
	public static BlockFluidCore blockFluidPetrotheum;
	public static BlockFluidCore blockFluidMana;

}

