package cofh.thermalfoundation.fluid;

import cofh.api.core.IInitializer;
import cofh.core.fluid.BlockFluidCoFHBase;
import cofh.core.fluid.FluidCoFHBase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class TFFluids {

	private TFFluids() {

	}

	public static void preInit() {

		registerAllFluids();
		registerAllFluidBlocks();

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
		fluidRedstone = new FluidCoFHBase("redstone", "thermalfoundation").setLuminosity(7).setDensity(1200).setViscosity(1500).setTemperature(300).setRarity(EnumRarity.UNCOMMON);
		fluidGlowstone = new FluidCoFHBase("glowstone", "thermalfoundation").setLuminosity(15).setDensity(-500).setViscosity(100).setTemperature(300).setGaseous(true).setRarity(EnumRarity.UNCOMMON);
		fluidEnder = new FluidCoFHBase("ender", "thermalfoundation").setLuminosity(3).setDensity(4000).setViscosity(3000).setTemperature(300).setRarity(EnumRarity.UNCOMMON);
		fluidPyrotheum = new FluidCoFHBase("pyrotheum", "thermalfoundation").setLuminosity(15).setDensity(2000).setViscosity(1200).setTemperature(4000).setRarity(EnumRarity.RARE);
		fluidCryotheum = new FluidCoFHBase("cryotheum", "thermalfoundation").setLuminosity(0).setDensity(4000).setViscosity(3000).setTemperature(50).setRarity(EnumRarity.RARE);
		fluidAerotheum = new FluidCoFHBase("aerotheum", "thermalfoundation").setLuminosity(0).setDensity(-800).setViscosity(100).setTemperature(300).setGaseous(true).setRarity(EnumRarity.RARE);
		fluidPetrotheum = new FluidCoFHBase("petrotheum", "thermalfoundation").setLuminosity(0).setDensity(4000).setViscosity(1500).setTemperature(400).setRarity(EnumRarity.RARE);
		fluidMana = new FluidCoFHBase("mana", "thermalfoundation").setLuminosity(15).setDensity(600).setViscosity(6000).setTemperature(350).setRarity(EnumRarity.EPIC);

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

	@SideOnly (Side.CLIENT)
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

	@SideOnly (Side.CLIENT)
	public static void registerFluidModels(Fluid fluid) {

		if (fluid == null) {
			return;
		}

		Block block = fluid.getBlock();
		if (block != null) {
			Item item = Item.getItemFromBlock(block);
			FluidStateMapper mapper = new FluidStateMapper(fluid);

			if (item != null) {
				ModelLoader.registerItemVariants(item);
				ModelLoader.setCustomMeshDefinition(item, mapper);
			}
			ModelLoader.setCustomStateMapper(block, mapper);
		}
	}

	@SideOnly (Side.CLIENT)
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

}

