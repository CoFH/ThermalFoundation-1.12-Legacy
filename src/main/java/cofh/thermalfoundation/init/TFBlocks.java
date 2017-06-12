package cofh.thermalfoundation.init;

import cofh.core.util.core.IInitializer;
import cofh.thermalfoundation.block.*;

import java.util.ArrayList;

public class TFBlocks {

	private TFBlocks() {

	}

	public static void preInit() {

		blockOre = new BlockOre();
		blockOreFluid = new BlockOreFluid();
		blockStorage = new BlockStorage();
		blockStorageAlloy = new BlockStorageAlloy();
		blockGlass = new BlockGlass();
		blockGlassAlloy = new BlockGlassAlloy();
		blockRockwool = new BlockRockwool();

		initList.add(blockOre);
		initList.add(blockOreFluid);
		initList.add(blockStorage);
		initList.add(blockStorageAlloy);
		initList.add(blockGlass);
		initList.add(blockGlassAlloy);
		initList.add(blockRockwool);

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

	private static ArrayList<IInitializer> initList = new ArrayList<>();

	/* REFERENCES */
	public static BlockOre blockOre;
	public static BlockOreFluid blockOreFluid;
	public static BlockStorage blockStorage;
	public static BlockStorageAlloy blockStorageAlloy;
	public static BlockGlass blockGlass;
	public static BlockGlassAlloy blockGlassAlloy;
	public static BlockRockwool blockRockwool;

}
