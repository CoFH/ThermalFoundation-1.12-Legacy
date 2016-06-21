package cofh.thermalfoundation.item;

import cofh.api.core.IInitializer;
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

		initList.add(itemWrench);
		initList.add(itemMeter);
		initList.add(itemDiagram);
		initList.add(itemFertilizer);
		initList.add(itemMaterial);

		ProxyClient.modelList.add(itemWrench);
		ProxyClient.modelList.add(itemMeter);
		ProxyClient.modelList.add(itemDiagram);
		ProxyClient.modelList.add(itemFertilizer);
		ProxyClient.modelList.add(itemMaterial);

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

}
