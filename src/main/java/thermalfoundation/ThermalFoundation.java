package thermalfoundation;

import cofh.CoFHCore;
import cofh.core.CoFHProps;
import cofh.mod.BaseMod;
import cofh.updater.UpdateManager;
import cofh.util.ConfigHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import thermalfoundation.block.TFBlocks;
import thermalfoundation.core.Proxy;
import thermalfoundation.fluid.TFFluids;
import thermalfoundation.gui.GuiHandler;
import thermalfoundation.gui.TFCreativeTab;
import thermalfoundation.item.TFItems;
import thermalfoundation.util.LexiconManager;

@Mod(modid = ThermalFoundation.modId, name = ThermalFoundation.modName, version = ThermalFoundation.version, dependencies = ThermalFoundation.dependencies,
		canBeDeactivated = false)
public class ThermalFoundation extends BaseMod {

	public static final String modId = "ThermalFoundation";
	public static final String modName = "Thermal Foundation";
	public static final String version = "1.7.10R1.0.0B1";
	public static final String dependencies = "required-after:CoFHCore@[" + CoFHCore.version + ",)";
	public static final String releaseURL = "https://github.com/CoFH/ThermalFoundation/VERSION";

	@Instance(modId)
	public static ThermalFoundation instance;

	@SidedProxy(clientSide = "thermalfoundation.core.ProxyClient", serverSide = "thermalfoundation.core.Proxy")
	public static Proxy proxy;

	public static final Logger log = LogManager.getLogger(modId);

	public static final ConfigHandler config = new ConfigHandler(version);
	public static final GuiHandler guiHandler = new GuiHandler();

	public static final CreativeTabs tab = new TFCreativeTab();

	/* INIT SEQUENCE */
	public ThermalFoundation() {

		super(log);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		// loadLang();

		UpdateManager.registerUpdater(new UpdateManager(this, releaseURL));

		config.setConfiguration(new Configuration(new File(CoFHProps.configDir, "/cofh/ThermalFoundation.cfg")));

		TFFluids.preInit();
		TFItems.preInit();
		TFBlocks.preInit();

		LexiconManager.preInit();

		config.save();
	}

	@EventHandler
	public void initialize(FMLInitializationEvent event) {

		TFFluids.initialize();
		TFItems.initialize();
		TFBlocks.initialize();

		/* Register Handlers */
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
		MinecraftForge.EVENT_BUS.register(proxy);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		TFFluids.postInit();
		TFItems.postInit();
		TFBlocks.postInit();

		proxy.registerEntities();
		proxy.registerRenderInformation();

		config.cleanUp(false, true);
	}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event) {

		LexiconManager.generateList();
		LexiconManager.addAllListedOres();

		cleanConfig(false);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {

		TFFluids.registerDispenserHandlers();
	}

	void cleanConfig(boolean preInit) {

		if (preInit) {

		}
		String prefix = "config.thermalfoundation.";
		String[] categoryNames = config.getCategoryNames().toArray(new String[config.getCategoryNames().size()]);
		for (int i = 0; i < categoryNames.length; i++) {
			config.getCategory(categoryNames[i]).setLanguageKey(prefix + categoryNames[i]).setRequiresMcRestart(true);
		}
	}

	/* BaseMod */
	@Override
	public String getModId() {

		return modId;
	}

	@Override
	public String getModName() {

		return modName;
	}

	@Override
	public String getModVersion() {

		return version;
	}

}
