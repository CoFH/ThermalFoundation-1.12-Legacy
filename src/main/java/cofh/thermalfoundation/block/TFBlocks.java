package cofh.thermalfoundation.block;

import cofh.api.core.IInitializer;

import java.util.ArrayList;

public class TFBlocks {

	private TFBlocks() {

	}

	public static void preInit() {

		blockOre = new BlockOre();
		blockStorage = new BlockStorage();
		blockGlass = new BlockGlass();
		blockRockwool = new BlockRockwool();

		initList.add(blockOre);
		initList.add(blockStorage);
		initList.add(blockGlass);
		initList.add(blockRockwool);

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

	static ArrayList<IInitializer> initList = new ArrayList<IInitializer>();

	/* REFERENCES */
	public static BlockOre blockOre;
	public static BlockStorage blockStorage;
	public static BlockGlass blockGlass;
	public static BlockRockwool blockRockwool;

}
