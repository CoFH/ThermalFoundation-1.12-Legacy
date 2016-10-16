package cofh.thermalfoundation.fluid;

import cofh.api.core.IInitializer;
import cofh.core.fluid.BlockFluidCoFHBase;
import cofh.core.fluid.FluidCoFHBase;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.core.ProxyClient;

import java.util.ArrayList;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.UniversalBucket;

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

		fluidSteam = new FluidCoFHBase("steam", "thermalfoundation").setLuminosity(0).setDensity(-1000).setViscosity(200).setTemperature(750).setGaseous(true);
		fluidCoal = new FluidCoFHBase("coal", "thermalfoundation").setLuminosity(0).setDensity(900).setViscosity(2000).setTemperature(300);
		fluidRedstone = new FluidCoFHBase("redstone", "thermalfoundation").setLuminosity(7).setDensity(1200).setViscosity(1500).setTemperature(300)
				.setRarity(EnumRarity.UNCOMMON);
		fluidGlowstone = new FluidCoFHBase("glowstone", "thermalfoundation").setLuminosity(15).setDensity(-500).setViscosity(100).setTemperature(300)
				.setGaseous(true).setRarity(EnumRarity.UNCOMMON);
		fluidEnder = new FluidCoFHBase("ender", "thermalfoundation").setLuminosity(3).setDensity(4000).setViscosity(3000).setTemperature(300)
				.setRarity(EnumRarity.UNCOMMON);
		fluidPyrotheum = new FluidCoFHBase("pyrotheum", "thermalfoundation").setLuminosity(15).setDensity(2000).setViscosity(1200).setTemperature(4000)
				.setRarity(EnumRarity.RARE);
		fluidCryotheum = new FluidCoFHBase("cryotheum", "thermalfoundation").setLuminosity(0).setDensity(4000).setViscosity(3000).setTemperature(50)
				.setRarity(EnumRarity.RARE);
		fluidAerotheum = new FluidCoFHBase("aerotheum", "thermalfoundation").setLuminosity(0).setDensity(-800).setViscosity(100).setTemperature(300)
				.setGaseous(true).setRarity(EnumRarity.RARE);
		fluidPetrotheum = new FluidCoFHBase("petrotheum", "thermalfoundation").setLuminosity(0).setDensity(4000).setViscosity(1500).setTemperature(400)
				.setRarity(EnumRarity.RARE);
		fluidMana = new FluidCoFHBase("mana", "thermalfoundation").setLuminosity(15).setDensity(600).setViscosity(6000).setTemperature(350)
				.setRarity(EnumRarity.EPIC);

		registerFluid(fluidSteam, "steam");
		registerFluid(fluidCoal, "coal");
		registerFluid(fluidRedstone, "redstone");
		registerFluid(fluidGlowstone, "glowstone");
		registerFluid(fluidEnder, "ender");
		registerFluid(fluidPyrotheum, "pyrotheum");
		registerFluid(fluidCryotheum, "cryotheum");
		registerFluid(fluidAerotheum, "aerotheum");
		registerFluid(fluidPetrotheum, "petrotheum");
		registerFluid(fluidMana, "mana");
	}

	public static void registerAllFluidBlocks() {

		registerFluidBlock(blockFluidSteam, new BlockFluidSteam(fluidSteam));
		registerFluidBlock(blockFluidCoal, new BlockFluidCoal(fluidCoal));
		registerFluidBlock(blockFluidRedstone, new BlockFluidRedstone(fluidRedstone));
		registerFluidBlock(blockFluidGlowstone, new BlockFluidGlowstone(fluidGlowstone));
		registerFluidBlock(blockFluidEnder, new BlockFluidEnder(fluidEnder));
		registerFluidBlock(blockFluidPyrotheum, new BlockFluidPyrotheum(fluidPyrotheum));
		registerFluidBlock(blockFluidCryotheum, new BlockFluidCryotheum(fluidCryotheum));
		registerFluidBlock(blockFluidAerotheum, new BlockFluidAerotheum(fluidAerotheum));
		registerFluidBlock(blockFluidPetrotheum, new BlockFluidPetrotheum(fluidPetrotheum));
		registerFluidBlock(blockFluidMana, new BlockFluidMana(fluidMana));
	}

	public static void registerFluid(Fluid fluid, String fluidName) {

		if (!FluidRegistry.isFluidRegistered(fluidName)) {
			FluidRegistry.registerFluid(fluid);
		}
		fluid = FluidRegistry.getFluid(fluidName);
	}

	public static void registerFluidBlock(BlockFluidCoFHBase block, BlockFluidCoFHBase block2) {

		block = block2;

		initList.add(block);

		ThermalFoundation.proxy.addModelRegister(block);
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

		bucketSteam = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, TFFluids.fluidSteam);
		bucketCoal = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, TFFluids.fluidCoal);
		bucketRedstone = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, TFFluids.fluidRedstone);
		bucketGlowstone = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, TFFluids.fluidGlowstone);
		bucketEnder = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, TFFluids.fluidEnder);
		bucketPyrotheum = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, TFFluids.fluidPyrotheum);
		bucketCryotheum = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, TFFluids.fluidCryotheum);
		bucketAerotheum = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, TFFluids.fluidAerotheum);
		bucketPetrotheum = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, TFFluids.fluidPetrotheum);
		bucketMana = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, TFFluids.fluidMana);
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

	public static BlockFluidCoFHBase blockFluidSteam;
	public static BlockFluidCoFHBase blockFluidCoal;
	public static BlockFluidCoFHBase blockFluidRedstone;
	public static BlockFluidCoFHBase blockFluidGlowstone;
	public static BlockFluidCoFHBase blockFluidEnder;
	public static BlockFluidCoFHBase blockFluidPyrotheum;
	public static BlockFluidCoFHBase blockFluidCryotheum;
	public static BlockFluidCoFHBase blockFluidAerotheum;
	public static BlockFluidCoFHBase blockFluidPetrotheum;
	public static BlockFluidCoFHBase blockFluidMana;

	public static ItemStack bucketSteam;
	public static ItemStack bucketCoal;
	public static ItemStack bucketRedstone;
	public static ItemStack bucketGlowstone;
	public static ItemStack bucketEnder;
	public static ItemStack bucketPyrotheum;
	public static ItemStack bucketCryotheum;
	public static ItemStack bucketAerotheum;
	public static ItemStack bucketPetrotheum;
	public static ItemStack bucketMana;

}
