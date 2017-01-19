package cofh.thermalfoundation.init;

import cofh.api.core.IInitializer;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.block.BlockGlass;
import cofh.thermalfoundation.block.BlockOre;
import cofh.thermalfoundation.block.BlockRockwool;
import cofh.thermalfoundation.block.BlockStorage;

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

		ThermalFoundation.proxy.addIModelRegister(blockOre);
		ThermalFoundation.proxy.addIModelRegister(blockStorage);
		ThermalFoundation.proxy.addIModelRegister(blockGlass);
		ThermalFoundation.proxy.addIModelRegister(blockRockwool);

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
		initList.clear();
	}

	static ArrayList<IInitializer> initList = new ArrayList<IInitializer>();

	/* REFERENCES */
	public static BlockOre blockOre;
	public static BlockStorage blockStorage;
	public static BlockGlass blockGlass;
	public static BlockRockwool blockRockwool;

}
