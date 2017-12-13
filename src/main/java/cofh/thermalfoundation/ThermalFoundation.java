package cofh.thermalfoundation;

import cofh.CoFHCore;
import cofh.cofhworld.CoFHWorld;
import cofh.core.init.CoreProps;
import cofh.core.util.ConfigHandler;
import cofh.thermalfoundation.gui.GuiHandler;
import cofh.thermalfoundation.init.*;
import cofh.thermalfoundation.network.PacketTFBase;
import cofh.thermalfoundation.proxy.Proxy;
import cofh.thermalfoundation.util.IMCHandler;
import cofh.thermalfoundation.util.LexiconManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod (modid = ThermalFoundation.MOD_ID, name = ThermalFoundation.MOD_NAME, version = ThermalFoundation.VERSION, dependencies = ThermalFoundation.DEPENDENCIES, updateJSON = ThermalFoundation.UPDATE_URL)
public class ThermalFoundation {

	public static final String MOD_ID = "thermalfoundation";
	public static final String MOD_NAME = "Thermal Foundation";

	public static final String VERSION = "2.3.7";
	public static final String VERSION_MAX = "2.4.0";
	public static final String VERSION_GROUP = "required-after:" + MOD_ID + "@[" + VERSION + "," + VERSION_MAX + ");";
	public static final String UPDATE_URL = "https://raw.github.com/cofh/version/master/" + MOD_ID + "_update.json";

	public static final String DEPENDENCIES = CoFHCore.VERSION_GROUP + CoFHWorld.VERSION_GROUP;
	public static final String MOD_GUI_FACTORY = "cofh.thermalfoundation.gui.GuiConfigTFFactory";

	@Instance (MOD_ID)
	public static ThermalFoundation instance;

	@SidedProxy (clientSide = "cofh.thermalfoundation.proxy.ProxyClient", serverSide = "cofh.thermalfoundation.proxy.Proxy")
	public static Proxy proxy;

	public static final Logger LOG = LogManager.getLogger(MOD_ID);
	public static final ConfigHandler CONFIG = new ConfigHandler(VERSION);
	public static final ConfigHandler CONFIG_CLIENT = new ConfigHandler(VERSION);
	public static final GuiHandler GUI_HANDLER = new GuiHandler();

	public static CreativeTabs tabCommon;
	public static CreativeTabs tabUtils;
	public static CreativeTabs tabTools;
	public static CreativeTabs tabArmor;

	static {
		FluidRegistry.enableUniversalBucket();
	}

	public ThermalFoundation() {

		super();
	}

	/* INIT */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		CONFIG.setConfiguration(new Configuration(new File(CoreProps.configDir, "/cofh/" + MOD_ID + "/common.cfg"), true));
		CONFIG_CLIENT.setConfiguration(new Configuration(new File(CoreProps.configDir, "/cofh/" + MOD_ID + "/client.cfg"), true));

		TFProps.preInit();

		TFBlocks.preInit();
		TFItems.preInit();
		TFEquipment.preInit();
		TFFluids.preInit();
		TFSounds.preInit();
		TFPlugins.preInit();

		/* Register Handlers */
		registerHandlers();

		proxy.preInit(event);
	}

	@EventHandler
	public void initialize(FMLInitializationEvent event) {

		proxy.initialize(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		TFPlugins.postInit();

		proxy.postInit(event);
	}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event) {

		IMCHandler.INSTANCE.handleIMC(FMLInterModComms.fetchRuntimeMessages(this));

		LexiconManager.loadComplete();

		CONFIG.cleanUp(false, true);
		CONFIG_CLIENT.cleanUp(false, true);

		LOG.info(MOD_NAME + ": Load Complete.");
	}

	@EventHandler
	public void handleIdMappingEvent(FMLModIdMappingEvent event) {

		TFFluids.refreshReferences();
	}

	@EventHandler
	public void handleIMC(IMCEvent event) {

		IMCHandler.INSTANCE.handleIMC(event.getMessages());
	}

	/* HELPERS */
	private void registerHandlers() {

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, GUI_HANDLER);

		LexiconManager.initialize();
		PacketTFBase.initialize();
	}

}
