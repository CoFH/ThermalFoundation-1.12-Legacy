package cofh.thermalfoundation.init;

import cofh.core.fluid.BlockFluidCore;
import cofh.core.fluid.FluidCore;
import cofh.core.util.core.IInitializer;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.fluid.*;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.EnumRarity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

public class TFFluids {

	public static final TFFluids INSTANCE = new TFFluids();

	private TFFluids() {

	}

	public static void preInit() {

		registerAllFluids();
		registerAllFluidBlocks();
		createBuckets();
		refreshReferences();

		for (IInitializer init : initList) {
			init.initialize();
		}
	}

	/* HELPERS */
	public static void registerAllFluids() {

		fluidSteam = new FluidCore("steam", "thermalfoundation").setDensity(-1000).setViscosity(200).setTemperature(750).setGaseous(true);

		fluidCreosote = new FluidCore("creosote", "thermalfoundation").setDensity(1100).setViscosity(2000);
		fluidCoal = new FluidCore("coal", "thermalfoundation").setDensity(900).setViscosity(2000);
		fluidCrudeOil = new FluidCore("crude_oil", "thermalfoundation").setDensity(900).setViscosity(2000);
		fluidRefinedOil = new FluidCore("refined_oil", "thermalfoundation").setDensity(800).setViscosity(1400);
		fluidFuel = new FluidCore("refined_fuel", "thermalfoundation").setDensity(700).setViscosity(800);

		fluidSap = new FluidCore("sap", "thermalfoundation").setDensity(1050).setViscosity(1500);
		fluidSyrup = new FluidCore("syrup", "thermalfoundation").setDensity(1400).setViscosity(2500);
		fluidResin = new FluidCore("resin", "thermalfoundation").setDensity(900).setViscosity(3000);
		fluidTreeOil = new FluidCore("tree_oil", "thermalfoundation").setDensity(900).setViscosity(1200);

		fluidMushroomStew = new FluidCore("mushroom_stew", "thermalfoundation").setDensity(2000).setViscosity(2000);
		fluidExperience = new FluidCore("experience", "thermalfoundation").setLuminosity(12).setDensity(-200).setViscosity(200).setGaseous(true).setRarity(EnumRarity.UNCOMMON);

		fluidPotion = new FluidPotion("potion", "thermalfoundation", "potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON);
		fluidPotionSplash = new FluidPotion("potion_splash", "thermalfoundation", "splash_potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON);
		fluidPotionLingering = new FluidPotion("potion_lingering", "thermalfoundation", "lingering_potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON);

		fluidRedstone = new FluidCore("redstone", "thermalfoundation").setLuminosity(7).setDensity(1200).setViscosity(1500).setRarity(EnumRarity.UNCOMMON);
		fluidGlowstone = new FluidCore("glowstone", "thermalfoundation").setLuminosity(15).setDensity(-500).setViscosity(100).setGaseous(true).setRarity(EnumRarity.UNCOMMON);
		fluidEnder = new FluidCore("ender", "thermalfoundation").setLuminosity(3).setDensity(4000).setViscosity(2500).setRarity(EnumRarity.UNCOMMON);
		fluidPyrotheum = new FluidCore("pyrotheum", "thermalfoundation").setLuminosity(15).setDensity(2000).setViscosity(1200).setTemperature(4000).setRarity(EnumRarity.RARE);
		fluidCryotheum = new FluidCore("cryotheum", "thermalfoundation").setDensity(4000).setViscosity(4000).setTemperature(50).setRarity(EnumRarity.RARE);
		fluidAerotheum = new FluidCore("aerotheum", "thermalfoundation").setDensity(-800).setViscosity(100).setGaseous(true).setRarity(EnumRarity.RARE);
		fluidPetrotheum = new FluidCore("petrotheum", "thermalfoundation").setDensity(4000).setViscosity(1500).setTemperature(350).setRarity(EnumRarity.RARE);
		fluidMana = new FluidCore("mana", "thermalfoundation").setLuminosity(15).setDensity(600).setViscosity(6000).setTemperature(350).setRarity(EnumRarity.EPIC);

		FluidRegistry.registerFluid(fluidSteam);

		FluidRegistry.registerFluid(fluidCreosote);
		FluidRegistry.registerFluid(fluidCoal);
		FluidRegistry.registerFluid(fluidCrudeOil);
		FluidRegistry.registerFluid(fluidRefinedOil);
		FluidRegistry.registerFluid(fluidFuel);

		FluidRegistry.registerFluid(fluidSap);
		FluidRegistry.registerFluid(fluidSyrup);
		FluidRegistry.registerFluid(fluidResin);
		FluidRegistry.registerFluid(fluidTreeOil);

		FluidRegistry.registerFluid(fluidMushroomStew);
		FluidRegistry.registerFluid(fluidExperience);

		FluidRegistry.registerFluid(fluidPotion);
		FluidRegistry.registerFluid(fluidPotionSplash);
		FluidRegistry.registerFluid(fluidPotionLingering);

		FluidRegistry.registerFluid(fluidRedstone);
		FluidRegistry.registerFluid(fluidGlowstone);
		FluidRegistry.registerFluid(fluidEnder);
		FluidRegistry.registerFluid(fluidPyrotheum);
		FluidRegistry.registerFluid(fluidCryotheum);
		FluidRegistry.registerFluid(fluidAerotheum);
		FluidRegistry.registerFluid(fluidPetrotheum);
		FluidRegistry.registerFluid(fluidMana);
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

		FluidRegistry.addBucketForFluid(fluidSap);
		FluidRegistry.addBucketForFluid(fluidSyrup);
		FluidRegistry.addBucketForFluid(fluidResin);
		FluidRegistry.addBucketForFluid(fluidTreeOil);

		FluidRegistry.addBucketForFluid(fluidMushroomStew);
		FluidRegistry.addBucketForFluid(fluidExperience);

		FluidRegistry.addBucketForFluid(fluidPotion);
		FluidRegistry.addBucketForFluid(fluidPotionSplash);
		FluidRegistry.addBucketForFluid(fluidPotionLingering);

		FluidRegistry.addBucketForFluid(fluidRedstone);
		FluidRegistry.addBucketForFluid(fluidGlowstone);
		FluidRegistry.addBucketForFluid(fluidEnder);
		FluidRegistry.addBucketForFluid(fluidPyrotheum);
		FluidRegistry.addBucketForFluid(fluidCryotheum);
		FluidRegistry.addBucketForFluid(fluidAerotheum);
		FluidRegistry.addBucketForFluid(fluidPetrotheum);
		FluidRegistry.addBucketForFluid(fluidMana);
	}

	public static void refreshReferences() {

		fluidSteam = FluidRegistry.getFluid("steam");

		fluidCreosote = FluidRegistry.getFluid("creosote");
		fluidCoal = FluidRegistry.getFluid("coal");
		fluidCrudeOil = FluidRegistry.getFluid("crude_oil");
		fluidRefinedOil = FluidRegistry.getFluid("refined_oil");
		fluidFuel = FluidRegistry.getFluid("refined_fuel");

		fluidSap = FluidRegistry.getFluid("sap");
		fluidSyrup = FluidRegistry.getFluid("syrup");
		fluidResin = FluidRegistry.getFluid("resin");
		fluidTreeOil = FluidRegistry.getFluid("tree_oil");

		fluidMushroomStew = FluidRegistry.getFluid("mushroom_stew");
		fluidExperience = FluidRegistry.getFluid("experience");

		fluidPotion = FluidRegistry.getFluid("potion");
		fluidPotionSplash = FluidRegistry.getFluid("potion_splash");
		fluidPotionLingering = FluidRegistry.getFluid("potion_lingering");

		fluidRedstone = FluidRegistry.getFluid("redstone");
		fluidGlowstone = FluidRegistry.getFluid("glowstone");
		fluidEnder = FluidRegistry.getFluid("ender");
		fluidPyrotheum = FluidRegistry.getFluid("pyrotheum");
		fluidCryotheum = FluidRegistry.getFluid("cryotheum");
		fluidAerotheum = FluidRegistry.getFluid("aerotheum");
		fluidPetrotheum = FluidRegistry.getFluid("petrotheum");
		fluidMana = FluidRegistry.getFluid("mana");
	}

	/* HELPERS */
	public static FluidStack getPotion(int amount, PotionType type) {

		if (type == PotionTypes.WATER) {
			return new FluidStack(FluidRegistry.WATER, amount);
		}
		return addPotionToFluidStack(new FluidStack(fluidPotion, amount), type);
	}

	public static FluidStack getSplashPotion(int amount, PotionType type) {

		return addPotionToFluidStack(new FluidStack(fluidPotionSplash, amount), type);
	}

	public static FluidStack getLingeringPotion(int amount, PotionType type) {

		return addPotionToFluidStack(new FluidStack(fluidPotionLingering, amount), type);
	}

	public static FluidStack addPotionToFluidStack(FluidStack stack, PotionType type) {

		ResourceLocation resourcelocation = PotionType.REGISTRY.getNameForObject(type);

		if (type == PotionTypes.EMPTY) {
			if (stack.tag != null) {
				stack.tag.removeTag("Potion");
				if (stack.tag.hasNoTags()) {
					stack.tag = null;
				}
			}
		} else {
			if (stack.tag == null) {
				stack.tag = new NBTTagCompound();
			}
			stack.tag.setString("Potion", resourcelocation.toString());
		}
		return stack;
	}

	private static ArrayList<IInitializer> initList = new ArrayList<>();

	/* REFERENCES */
	public static Fluid fluidSteam;

	public static Fluid fluidCreosote;
	public static Fluid fluidCoal;
	public static Fluid fluidCrudeOil;
	public static Fluid fluidRefinedOil;
	public static Fluid fluidFuel;

	public static Fluid fluidSap;
	public static Fluid fluidSyrup;
	public static Fluid fluidResin;
	public static Fluid fluidTreeOil;

	public static Fluid fluidMushroomStew;
	public static Fluid fluidExperience;

	public static Fluid fluidPotion;
	public static Fluid fluidPotionSplash;
	public static Fluid fluidPotionLingering;

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
