package cofh.thermalfoundation.init;

import cofh.api.core.IInitializer;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.block.*;

import java.util.ArrayList;

public class TFBlocks {

	private TFBlocks() {

	}

	public static void preInit() {

		blockOre = new BlockOre();
		blockStorage = new BlockStorage();
		blockStorageAlloy = new BlockStorageAlloy();
		blockGlass = new BlockGlass();
		blockGlassAlloy = new BlockGlassAlloy();
		blockRockwool = new BlockRockwool();

		initList.add(blockOre);
		initList.add(blockStorage);
		initList.add(blockStorageAlloy);
		initList.add(blockGlass);
		initList.add(blockGlassAlloy);
		initList.add(blockRockwool);

		ThermalFoundation.proxy.addIModelRegister(blockOre);
		ThermalFoundation.proxy.addIModelRegister(blockStorage);
		ThermalFoundation.proxy.addIModelRegister(blockStorageAlloy);
		ThermalFoundation.proxy.addIModelRegister(blockGlass);
		ThermalFoundation.proxy.addIModelRegister(blockGlassAlloy);
		ThermalFoundation.proxy.addIModelRegister(blockRockwool);

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
	public static BlockStorage blockStorage;
	public static BlockStorageAlloy blockStorageAlloy;
	public static BlockGlass blockGlass;
	public static BlockGlassAlloy blockGlassAlloy;
	public static BlockRockwool blockRockwool;

}
