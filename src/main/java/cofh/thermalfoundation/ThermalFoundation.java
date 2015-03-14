package cofh.thermalfoundation;

import cofh.CoFHCore;
import cofh.core.CoFHProps;
import cofh.core.util.ConfigHandler;
import cofh.core.util.CoreUtils;
import cofh.mod.BaseMod;
import cofh.mod.updater.UpdateManager;
import cofh.thermalfoundation.block.TFBlocks;
import cofh.thermalfoundation.core.Proxy;
import cofh.thermalfoundation.fluid.TFFluids;
import cofh.thermalfoundation.gui.GuiHandler;
import cofh.thermalfoundation.gui.TFCreativeTab;
import cofh.thermalfoundation.item.Equipment;
import cofh.thermalfoundation.item.TFItems;
import cofh.thermalfoundation.network.PacketTFBase;
import cofh.thermalfoundation.util.LexiconManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.CustomProperty;
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
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ThermalFoundation.modId, name = ThermalFoundation.modName, version = ThermalFoundation.version, dependencies = ThermalFoundation.dependencies,
guiFactory = ThermalFoundation.modGuiFactory, canBeDeactivated = false, customProperties = @CustomProperty(k = "cofhversion", v = "true"))
public class ThermalFoundation extends BaseMod {

	public static final String modId = "ThermalFoundation";
	public static final String modName = "Thermal Foundation";
	public static final String version = "1.7.10R1.0.0RC7";
	public static final String dependencies = "required-after:CoFHCore@[" + CoFHCore.version + ",)";
	public static final String releaseURL = "https://raw.github.com/CoFH/ThermalFoundation/master/VERSION";
	public static final String modGuiFactory = "cofh.thermalfoundation.gui.GuiConfigTFFactory";

	@Instance(modId)
	public static ThermalFoundation instance;

	@SidedProxy(clientSide = "cofh.thermalfoundation.core.ProxyClient", serverSide = "cofh.thermalfoundation.core.Proxy")
	public static Proxy proxy;

	public static final Logger log = LogManager.getLogger(modId);

	public static final ConfigHandler config = new ConfigHandler(version);
	public static final GuiHandler guiHandler = new GuiHandler();

	public static final CreativeTabs tabItems = new TFCreativeTab();
	public static final CreativeTabs tabTools = new TFCreativeTab("Tools") {

		@Override
		protected ItemStack getStack() {

			return Equipment.Invar.toolPickaxe;
		}
	};
	public static final CreativeTabs tabArmor = new TFCreativeTab("Armor") {

		@Override
		protected ItemStack getStack() {

			return Equipment.Invar.armorPlate;
		}
	};

	public static File worldGenOres;
	public static final String worldGenInternalOres = "assets/thermalfoundation/world/ThermalFoundation-Ores.json";

	/* INIT SEQUENCE */
	public ThermalFoundation() {

		super(log);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		UpdateManager.registerUpdater(new UpdateManager(this, releaseURL, CoFHProps.DOWNLOAD_URL));

		config.setConfiguration(new Configuration(new File(CoFHProps.configDir, "/cofh/thermalfoundation/common.cfg"), true));

		TFFluids.preInit();
		TFItems.preInit();
		TFBlocks.preInit();
		Equipment.preInit();

		LexiconManager.preInit();

		config.save();
	}

	@EventHandler
	public void initialize(FMLInitializationEvent event) {

		TFFluids.initialize();
		TFItems.initialize();
		TFBlocks.initialize();
		Equipment.initialize();

		/* Init World Gen */
		loadWorldGeneration();

		/* Register Handlers */
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
		MinecraftForge.EVENT_BUS.register(proxy);
		PacketTFBase.initialize();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		TFFluids.postInit();
		TFItems.postInit();
		TFBlocks.postInit();
		Equipment.postInit();

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

	/* LOADING FUNCTIONS */
	void loadWorldGeneration() {

		if (!config
				.get("World",
						"GenerateDefaultFiles",
						true,
						"If enabled, Thermal Foundation will create default world generation files - if it cannot find existing ones. Only disable this if you know what you are doing.")) {
			return;
		}
		worldGenOres = new File(CoFHProps.configDir, "/cofh/world/ThermalFoundation-Ores.json");
		boolean failConvert = false;

		File oldGen = new File(CoFHProps.configDir, "/cofh/world/ThermalExpansion-Ores.json");

		if (oldGen.exists()) {
			if (oldGen.renameTo(worldGenOres)) {
				log.warn("Thermal Foundation was unable to convert existing world generation! This is really bad - your files are probably write protected and you need to handle it now!");
				failConvert = true;
			}
		}
		if (!worldGenOres.exists() && !failConvert) {
			try {
				worldGenOres.createNewFile();
				CoreUtils.copyFileUsingStream(worldGenInternalOres, worldGenOres);
			} catch (Throwable t) {
				t.printStackTrace();
			}
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
