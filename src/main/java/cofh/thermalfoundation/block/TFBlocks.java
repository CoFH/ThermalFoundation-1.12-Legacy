package cofh.thermalfoundation.block;

import cofh.core.fluid.BlockFluidCoFHBase;
import cofh.thermalfoundation.fluid.BlockFluidAerotheum;
import cofh.thermalfoundation.fluid.BlockFluidCoal;
import cofh.thermalfoundation.fluid.BlockFluidCryotheum;
import cofh.thermalfoundation.fluid.BlockFluidEnder;
import cofh.thermalfoundation.fluid.BlockFluidGlowstone;
import cofh.thermalfoundation.fluid.BlockFluidMana;
import cofh.thermalfoundation.fluid.BlockFluidPetrotheum;
import cofh.thermalfoundation.fluid.BlockFluidPyrotheum;
import cofh.thermalfoundation.fluid.BlockFluidRedstone;
import cofh.thermalfoundation.fluid.BlockFluidSteam;

public class TFBlocks {

	public static void preInit() {

		blockOre = new BlockOre();
		blockStorage = new BlockStorage();

		blockFluidRedstone = new BlockFluidRedstone();
		blockFluidGlowstone = new BlockFluidGlowstone();
		blockFluidEnder = new BlockFluidEnder();
		blockFluidPyrotheum = new BlockFluidPyrotheum();
		blockFluidCryotheum = new BlockFluidCryotheum();
		blockFluidAerotheum = new BlockFluidAerotheum();
		blockFluidPetrotheum = new BlockFluidPetrotheum();
		blockFluidMana = new BlockFluidMana();
		blockFluidSteam = new BlockFluidSteam();
		blockFluidCoal = new BlockFluidCoal();

		blockOre.preInit();
		blockStorage.preInit();

		blockFluidRedstone.preInit();
		blockFluidGlowstone.preInit();
		blockFluidEnder.preInit();
		blockFluidPyrotheum.preInit();
		blockFluidCryotheum.preInit();
		blockFluidAerotheum.preInit();
		blockFluidPetrotheum.preInit();
		blockFluidMana.preInit();
		blockFluidSteam.preInit();
		blockFluidCoal.preInit();
	}

	public static void initialize() {

	}

	public static void postInit() {

		blockOre.postInit();
		blockStorage.postInit();
	}

	public static BlockOre blockOre;
	public static BlockStorage blockStorage;

	public static BlockFluidCoFHBase blockFluidRedstone;
	public static BlockFluidCoFHBase blockFluidGlowstone;
	public static BlockFluidCoFHBase blockFluidEnder;
	public static BlockFluidCoFHBase blockFluidPyrotheum;
	public static BlockFluidCoFHBase blockFluidCryotheum;
	public static BlockFluidCoFHBase blockFluidAerotheum;
	public static BlockFluidCoFHBase blockFluidPetrotheum;
	public static BlockFluidCoFHBase blockFluidMana;
	public static BlockFluidCoFHBase blockFluidSteam;
	public static BlockFluidCoFHBase blockFluidCoal;

}
