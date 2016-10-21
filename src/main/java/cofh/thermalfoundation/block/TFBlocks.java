package cofh.thermalfoundation.block;


import cofh.thermalfoundation.fluid.*;

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

	public static BlockFluidBase blockFluidRedstone;
	public static BlockFluidBase blockFluidGlowstone;
	public static BlockFluidBase blockFluidEnder;
	public static BlockFluidBase blockFluidPyrotheum;
	public static BlockFluidBase blockFluidCryotheum;
	public static BlockFluidBase blockFluidAerotheum;
	public static BlockFluidBase blockFluidPetrotheum;
	public static BlockFluidBase blockFluidMana;
	public static BlockFluidBase blockFluidSteam;
	public static BlockFluidBase blockFluidCoal;

}
