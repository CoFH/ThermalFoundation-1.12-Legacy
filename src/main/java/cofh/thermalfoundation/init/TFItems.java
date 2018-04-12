package cofh.thermalfoundation.init;

import cofh.core.util.core.IInitializer;
import cofh.thermalfoundation.item.*;
import cofh.thermalfoundation.item.diagram.ItemDiagram;
import cofh.thermalfoundation.item.diagram.ItemDiagramRedprint;
import cofh.thermalfoundation.item.tome.ItemTome;
import cofh.thermalfoundation.item.tome.ItemTomeExperience;
import cofh.thermalfoundation.item.tome.ItemTomeLexicon;
import cofh.thermalfoundation.util.TFCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.MissingMappings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;

public class TFItems {

	public static final TFItems INSTANCE = new TFItems();

	private TFItems() {

	}

	public static void preInit() {

		itemWrench = new ItemWrench();
		itemMeter = new ItemMeter();
		itemUpgrade = new ItemUpgrade();
		itemSecurity = new ItemSecurity();
		itemDiagramRedprint = new ItemDiagramRedprint();
		itemTomeLexicon = new ItemTomeLexicon();
		itemTomeExperience = new ItemTomeExperience();
		itemCoin = new ItemCoin();
		itemFertilizer = new ItemFertilizer();
		itemBait = new ItemBait();
		itemMaterial = new ItemMaterial();
		itemGeode = new ItemGeode();

		initList.add(itemWrench);
		initList.add(itemMeter);
		initList.add(itemUpgrade);
		initList.add(itemSecurity);
		initList.add(itemDiagramRedprint);
		initList.add(itemTomeLexicon);
		initList.add(itemTomeExperience);
		initList.add(itemCoin);
		initList.add(itemFertilizer);
		initList.add(itemBait);
		initList.add(itemMaterial);
		initList.add(itemGeode);

		for (IInitializer init : initList) {
			init.initialize();
		}
		TFProps.itemList.addAll(itemMaterial.getAllItems());
		TFProps.utilList.add(ItemWrench.wrenchBasic);
		TFProps.utilList.add(ItemDiagramRedprint.diagramRedprint);
		TFProps.utilList.add(ItemTomeLexicon.tomeLexicon);

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

	@SubscribeEvent
	public void missingMapping(RegistryEvent.MissingMappings<Item> event) {

		for (MissingMappings.Mapping<Item> entry : event.getAllMappings()) {
			if (entry.key.toString().equals("thermalexpansion:upgrade")) {
				entry.remap(ForgeRegistries.ITEMS.getValue(new ResourceLocation("thermalfoundation:upgrade")));
			} else if (entry.key.toString().equals("thermalfoundation:diagram")) {
				entry.remap(ForgeRegistries.ITEMS.getValue(new ResourceLocation("thermalfoundation:diagram_redprint")));
			} else if (entry.key.toString().equals("thermalfoundation:tome")) {
				entry.remap(ForgeRegistries.ITEMS.getValue(new ResourceLocation("thermalfoundation:tome_lexicon")));
			}
		}
	}

	private static ArrayList<IInitializer> initList = new ArrayList<>();

	/* REFERENCES */
	public static ItemWrench itemWrench;
	public static ItemMeter itemMeter;
	public static ItemUpgrade itemUpgrade;
	public static ItemSecurity itemSecurity;
	public static ItemDiagram itemDiagramRedprint;
	public static ItemTome itemTomeLexicon;
	public static ItemTome itemTomeExperience;
	public static ItemCoin itemCoin;
	public static ItemBait itemBait;
	public static ItemFertilizer itemFertilizer;
	public static ItemMaterial itemMaterial;
	public static ItemGeode itemGeode;

}
