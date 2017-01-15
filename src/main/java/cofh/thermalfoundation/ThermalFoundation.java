package cofh.thermalfoundation;

import cofh.CoFHCore;
import cofh.core.CoFHProps;
import cofh.core.util.ConfigHandler;
import cofh.thermalfoundation.block.TFBlocks;
import cofh.thermalfoundation.fluid.TFFluids;
import cofh.thermalfoundation.gui.CreativeTabTF;
import cofh.thermalfoundation.gui.GuiHandler;
import cofh.thermalfoundation.item.TFEquipment;
import cofh.thermalfoundation.item.TFItems;
import cofh.thermalfoundation.network.PacketTFBase;
import cofh.thermalfoundation.proxy.Proxy;
import cofh.thermalfoundation.util.EventHandlerLexicon;
import cofh.thermalfoundation.util.IMCHandler;
import cofh.thermalfoundation.util.LexiconManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.helpers.Loader;

import java.io.File;

@Mod (modid = ThermalFoundation.MOD_ID, name = ThermalFoundation.MOD_NAME, version = ThermalFoundation.VERSION, dependencies = ThermalFoundation.DEPENDENCIES, guiFactory = ThermalFoundation.MOD_GUI_FACTORY)
public class ThermalFoundation {

	public static final String MOD_ID = "thermalfoundation";
	public static final String MOD_NAME = "Thermal Foundation";

	public static final String VERSION = "1.3.0";
	public static final String VERSION_MAX = "1.4.0";
	public static final String VERSION_GROUP = "required-after:" + MOD_ID + "@[" + VERSION + "," + VERSION_MAX + ");";

	public static final String DEPENDENCIES = CoFHCore.VERSION_GROUP;
	public static final String MOD_GUI_FACTORY = "cofh.thermalfoundation.gui.GuiConfigTFFactory";

	@Instance (MOD_ID)
	public static ThermalFoundation instance;

	@SidedProxy (clientSide = "cofh.thermalfoundation.proxy.ProxyClient", serverSide = "cofh.thermalfoundation.proxy.Proxy")
	public static Proxy proxy;

	public static final Logger LOG = LogManager.getLogger(MOD_ID);
	public static final ConfigHandler CONFIG = new ConfigHandler(VERSION);
	public static final ConfigHandler CONFIG_CLIENT = new ConfigHandler(VERSION);
	public static final GuiHandler GUI_HANDLER = new GuiHandler();

	public static CreativeTabs tabCommon = new CreativeTabTF();
	public static CreativeTabs tabTools = CreativeTabs.TOOLS;
	public static CreativeTabs tabArmor = CreativeTabs.COMBAT;

	public ThermalFoundation() {

		super();
	}

	/* INIT */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		CONFIG.setConfiguration(new Configuration(new File(CoFHProps.configDir, "/cofh/" + MOD_ID + "/common.cfg"), true));
		CONFIG_CLIENT.setConfiguration(new Configuration(new File(CoFHProps.configDir, "/cofh/" + MOD_ID + "/client.cfg"), true));

		TFBlocks.preInit();
		TFItems.preInit();
		TFEquipment.preInit();
		TFFluids.preInit();

		LexiconManager.preInit();

		proxy.preInit(event);
	}

	@EventHandler
	public void initialize(FMLInitializationEvent event) {

		TFBlocks.initialize();
		TFItems.initialize();
		TFEquipment.initialize();
		TFFluids.initialize();

		/* Register Handlers */
		registerHandlers();

		/* Add World Generation */
		addWorldGeneration();

		proxy.initialize(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		TFBlocks.postInit();
		TFItems.postInit();
		TFEquipment.postInit();
		TFFluids.postInit();

		proxy.postInit(event);
	}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event) {

		IMCHandler.instance.handleIMC(FMLInterModComms.fetchRuntimeMessages(this));

		LexiconManager.loadComplete();

		CONFIG.cleanUp(false, true);
		CONFIG_CLIENT.cleanUp(false, true);

		LOG.info(MOD_NAME + ": Load Complete.");
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {

	}

	@EventHandler
	public void handleIMC(IMCEvent theIMC) {

		IMCHandler.instance.handleIMC(theIMC.getMessages());
	}

	/* HELPERS */
	private void registerHandlers() {

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, GUI_HANDLER);
		MinecraftForge.EVENT_BUS.register(proxy);

		EventHandlerLexicon.initialize();
		PacketTFBase.initialize();
	}

	private void addWorldGeneration() {

		File worldGenOres;
		String worldGenPath = "assets/thermalfoundation/world/";
		String worldGenOre = "thermalfoundation_ores.json";

		if (!CONFIG.getConfiguration().getBoolean("World", "GenerateDefaultFiles", true, "If enabled, Thermal Foundation will create default world generation files - if it cannot find existing ones. Only disable this if you know what you are doing.")) {
			return;
		}
		worldGenOres = new File(CoFHProps.configDir, "/cofh/world/" + worldGenOre);

		if (!worldGenOres.exists()) {
			try {
				worldGenOres.createNewFile();
				FileUtils.copyInputStreamToFile(Loader.getResource(worldGenPath + worldGenOre, null).openStream(), worldGenOres);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

}
