package cofh.thermalfoundation;

import cofh.CoFHCore;
import cofh.core.CoFHProps;
import cofh.core.util.ConfigHandler;
import cofh.core.util.CoreUtils;
import cofh.thermalfoundation.block.TFBlocks;
import cofh.thermalfoundation.core.Proxy;
import cofh.thermalfoundation.fluid.TFFluids;
import cofh.thermalfoundation.gui.CreativeTabTF;
import cofh.thermalfoundation.item.TFItems;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.CustomProperty;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ThermalFoundation.modId, name = ThermalFoundation.modName, version = ThermalFoundation.version, dependencies = ThermalFoundation.dependencies,
		guiFactory = ThermalFoundation.modGuiFactory, canBeDeactivated = false, customProperties = @CustomProperty(k = "cofhversion", v = "true"))
public class ThermalFoundation {

	public static final String modId = "thermalfoundation";
	public static final String modName = "Thermal Foundation";
	public static final String version = "1.10.2A0.0.1";
	public static final String version_max = "1.10.2A0.0.1";
	public static final String dependencies = CoFHCore.version_group;
	public static final String modGuiFactory = "cofh.thermalfoundation.gui.GuiConfigTFFactory";

	public static final String version_group = "required-after:" + modId + "@[" + version + "," + version_max + ");";
	public static final String releaseURL = "https://raw.github.com/CoFH/VERSION/master/" + modId;

	@Instance(modId)
	public static ThermalFoundation instance;

	@SidedProxy(clientSide = "cofh.thermalfoundation.core.ProxyClient", serverSide = "cofh.thermalfoundation.core.Proxy")
	public static Proxy proxy;

	public static final Logger LOG = LogManager.getLogger(modId);
	public static final ConfigHandler CONFIG = new ConfigHandler(version);
	public static final ConfigHandler CONFIG_CLIENT = new ConfigHandler(version);

	public static CreativeTabs tabCommon = new CreativeTabTF();
	public static CreativeTabs tabTools = CreativeTabs.TOOLS;
	public static CreativeTabs tabArmor = CreativeTabs.COMBAT;

	public static File worldGenOres;
	public static final String WORLD_GEN_PATH = "assets/thermalfoundation/world/";
	public static final String WORLD_GEN_FILE = "thermalfoundation_ores.json";

	static {

		FluidRegistry.enableUniversalBucket();
	}

	/* INIT SEQUENCE */
	public ThermalFoundation() {

		//super(log);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		CONFIG.setConfiguration(new Configuration(new File(CoFHProps.configDir, "/cofh/thermalfoundation/common.cfg"), true));
		CONFIG_CLIENT.setConfiguration(new Configuration(new File(CoFHProps.configDir, "cofh/thermalfoundation/client.cfg"), true));

		TFBlocks.preInit();
		TFItems.preInit();
		TFFluids.preInit();

		proxy.preInit(event);
	}

	@EventHandler
	public void initialize(FMLInitializationEvent event) {

		TFBlocks.initialize();
		TFItems.initialize();
		TFFluids.initialize();

		loadWorldGeneration();

		proxy.initialize(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		TFBlocks.postInit();
		TFItems.postInit();
		TFFluids.postInit();

		proxy.postInit(event);
	}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event) {

		CONFIG.cleanUp(false, true);
		CONFIG_CLIENT.cleanUp(false, true);

		LOG.info(modName + ": Load Complete.");
	}

	void loadWorldGeneration() {

		if (!CONFIG
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
				LOG.warn("Thermal Foundation was unable to convert existing world generation! This is really bad - your files are probably write protected and you need to handle it now!");
				failConvert = true;
			}
		}
		if (!worldGenOres.exists() && !failConvert) {
			try {
				worldGenOres.createNewFile();
				CoreUtils.copyFileUsingStream(WORLD_GEN_PATH + WORLD_GEN_FILE, worldGenOres);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {

	}

	@EventHandler
	public void handleIMC(IMCEvent event) {

	}

}
