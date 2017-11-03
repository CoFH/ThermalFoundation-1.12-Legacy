package cofh.thermalfoundation.init;

import cofh.core.util.core.IInitializer;
import cofh.thermalfoundation.item.*;
import cofh.thermalfoundation.util.TFCrafting;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class TFItems {

	public static final TFItems INSTANCE = new TFItems();

	private TFItems() {

	}

	public static void preInit() {

		itemWrench = new ItemWrench();
		itemMeter = new ItemMeter();
		itemTome = new ItemTome();
		itemSecurity = new ItemSecurity();
		itemDiagram = new ItemDiagram();
		itemCoin = new ItemCoin();
		itemFertilizer = new ItemFertilizer();
		itemBait = new ItemBait();
		itemMaterial = new ItemMaterial();
		itemGeode = new ItemGeode();

		initList.add(itemWrench);
		initList.add(itemMeter);
		initList.add(itemTome);
		initList.add(itemSecurity);
		initList.add(itemDiagram);
		initList.add(itemCoin);
		initList.add(itemFertilizer);
		initList.add(itemBait);
		initList.add(itemMaterial);
		initList.add(itemGeode);

		for (IInitializer init : initList) {
			init.initialize();
		}
		MinecraftForge.EVENT_BUS.register(INSTANCE);
	}

	/* EVENT HANDLING */
	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event) {

		for (IInitializer init : initList) {
			init.register();
		}
		TFCrafting.loadRecipes();
	}

	private static ArrayList<IInitializer> initList = new ArrayList<>();

	/* REFERENCES */
	public static ItemWrench itemWrench;
	public static ItemMeter itemMeter;
	public static ItemTome itemTome;
	public static ItemSecurity itemSecurity;
	public static ItemDiagram itemDiagram;
	public static ItemCoin itemCoin;
	public static ItemBait itemBait;
	public static ItemFertilizer itemFertilizer;
	public static ItemMaterial itemMaterial;
	public static ItemGeode itemGeode;

}
