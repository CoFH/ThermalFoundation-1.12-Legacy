package cofh.thermalfoundation.init;

import cofh.core.util.core.IInitializer;
import cofh.thermalfoundation.block.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class TFBlocks {

	public static final TFBlocks INSTANCE = new TFBlocks();

	private TFBlocks() {

	}

	public static void preInit() {

		blockOre = new BlockOre();
		blockOreFluid = new BlockOreFluid();
		blockStorage = new BlockStorage();
		blockStorageAlloy = new BlockStorageAlloy();
		blockStorageResource = new BlockStorageResource();
		blockGlass = new BlockGlass();
		blockGlassAlloy = new BlockGlassAlloy();
		blockRockwool = new BlockRockwool();

		initList.add(blockOre);
		initList.add(blockOreFluid);
		initList.add(blockStorage);
		initList.add(blockStorageAlloy);
		initList.add(blockStorageResource);
		initList.add(blockGlass);
		initList.add(blockGlassAlloy);
		initList.add(blockRockwool);

		for (IInitializer init : initList) {
			init.preInit();
		}
		MinecraftForge.EVENT_BUS.register(INSTANCE);
	}

	/* EVENT HANDLING */
	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event) {

		for (IInitializer init : initList) {
			init.initialize();
		}
	}

	private static ArrayList<IInitializer> initList = new ArrayList<>();

	/* REFERENCES */
	public static BlockOre blockOre;
	public static BlockOreFluid blockOreFluid;
	public static BlockStorage blockStorage;
	public static BlockStorageAlloy blockStorageAlloy;
	public static BlockStorageResource blockStorageResource;
	public static BlockGlass blockGlass;
	public static BlockGlassAlloy blockGlassAlloy;
	public static BlockRockwool blockRockwool;

}
