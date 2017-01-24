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
	public static BlockStorageAlloy blockStorageAlloy;
	public static BlockGlass blockGlass;
	public static BlockGlassAlloy blockGlassAlloy;
	public static BlockRockwool blockRockwool;

}
