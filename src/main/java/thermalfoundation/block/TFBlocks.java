package thermalfoundation.block;

import cofh.fluid.BlockFluidCoFHBase;

import thermalfoundation.fluid.BlockFluidCoal;
import thermalfoundation.fluid.BlockFluidCryotheum;
import thermalfoundation.fluid.BlockFluidEnder;
import thermalfoundation.fluid.BlockFluidGlowstone;
import thermalfoundation.fluid.BlockFluidMana;
import thermalfoundation.fluid.BlockFluidPyrotheum;
import thermalfoundation.fluid.BlockFluidRedstone;
import thermalfoundation.fluid.BlockFluidSteam;

public class TFBlocks {

	public static void preInit() {

		blockOre = new BlockOre();
		blockStorage = new BlockStorage();

		blockFluidRedstone = new BlockFluidRedstone();
		blockFluidGlowstone = new BlockFluidGlowstone();
		blockFluidEnder = new BlockFluidEnder();
		blockFluidPyrotheum = new BlockFluidPyrotheum();
		blockFluidCryotheum = new BlockFluidCryotheum();
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
		blockFluidMana.preInit();
		blockFluidSteam.preInit();
		blockFluidCoal.preInit();
	}

	public static void initialize() {

	}

	public static void postInit() {

		blockOre.postInit();
	}

	public static BlockOre blockOre;
	public static BlockStorage blockStorage;

	public static BlockFluidCoFHBase blockFluidRedstone;
	public static BlockFluidCoFHBase blockFluidGlowstone;
	public static BlockFluidCoFHBase blockFluidEnder;
	public static BlockFluidCoFHBase blockFluidPyrotheum;
	public static BlockFluidCoFHBase blockFluidCryotheum;
	public static BlockFluidCoFHBase blockFluidMana;
	public static BlockFluidCoFHBase blockFluidSteam;
	public static BlockFluidCoFHBase blockFluidCoal;

}
