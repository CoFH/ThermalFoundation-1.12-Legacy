package cofh.thermalfoundation.item;

import cofh.api.core.IInitializer;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.core.ProxyClient;

import java.util.ArrayList;

public class TFItems {

	private TFItems() {

	}

	public static void preInit() {

		itemWrench = new ItemWrench();
		itemMeter = new ItemMeter();
		itemDiagram = new ItemDiagram();
		itemFertilizer = new ItemFertilizer();
		itemMaterial = new ItemMaterial();
		itemSecurity = new ItemSecurity();

		Equipment.preInit();

		initList.add(itemWrench);
		initList.add(itemMeter);
		initList.add(itemDiagram);
		initList.add(itemFertilizer);
		initList.add(itemMaterial);
		initList.add(itemSecurity);

		ThermalFoundation.proxy.addModelRegister(itemWrench);
		ThermalFoundation.proxy.addModelRegister(itemMeter);
		ThermalFoundation.proxy.addModelRegister(itemDiagram);
		ThermalFoundation.proxy.addModelRegister(itemFertilizer);
		ThermalFoundation.proxy.addModelRegister(itemMaterial);
		ThermalFoundation.proxy.addModelRegister(itemSecurity);

		for (int i = 0; i < initList.size(); i++) {
			initList.get(i).preInit();
		}
	}

	public static void initialize() {

		Equipment.initialize();

		for (int i = 0; i < initList.size(); i++) {
			initList.get(i).initialize();
		}
	}

	public static void postInit() {

		Equipment.postInit();

		for (int i = 0; i < initList.size(); i++) {
			initList.get(i).postInit();
		}
	}

	// TODO: FIX
	//	public static ItemBucket itemBucket;
	//	public static ItemLexicon itemLexicon;

	static ArrayList<IInitializer> initList = new ArrayList<IInitializer>();

	/* REFERENCES */
	public static ItemWrench itemWrench;
	public static ItemMeter itemMeter;
	public static ItemDiagram itemDiagram;
	public static ItemFertilizer itemFertilizer;
	public static ItemMaterial itemMaterial;
	public static ItemSecurity itemSecurity;

}
