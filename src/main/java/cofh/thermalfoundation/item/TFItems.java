package cofh.thermalfoundation.item;

import cofh.api.core.IInitializer;
import cofh.thermalfoundation.ThermalFoundation;

import java.util.ArrayList;

public class TFItems {

	private TFItems() {

	}

	public static void preInit() {

		itemWrench = new ItemWrench();
		itemMeter = new ItemMeter();
		itemTome = new ItemTome();
		itemDiagram = new ItemDiagram();
		itemFertilizer = new ItemFertilizer();
		itemMaterial = new ItemMaterial();

		initList.add(itemWrench);
		initList.add(itemMeter);
		initList.add(itemTome);
		initList.add(itemDiagram);
		initList.add(itemFertilizer);
		initList.add(itemMaterial);

		ThermalFoundation.proxy.addIModelRegister(itemWrench);
		ThermalFoundation.proxy.addIModelRegister(itemMeter);
		ThermalFoundation.proxy.addIModelRegister(itemTome);
		ThermalFoundation.proxy.addIModelRegister(itemDiagram);
		ThermalFoundation.proxy.addIModelRegister(itemFertilizer);
		ThermalFoundation.proxy.addIModelRegister(itemMaterial);

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
	public static ItemWrench itemWrench;
	public static ItemMeter itemMeter;
	public static ItemTome itemTome;
	public static ItemDiagram itemDiagram;
	public static ItemFertilizer itemFertilizer;
	public static ItemMaterial itemMaterial;

}
