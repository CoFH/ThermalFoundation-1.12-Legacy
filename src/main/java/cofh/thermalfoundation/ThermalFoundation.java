package cofh.thermalfoundation;

import cofh.CoFHCore;
import cofh.core.CoFHProps;
import cofh.core.util.ConfigHandler;
import cofh.core.util.CoreUtils;
import cofh.mod.BaseMod;
import cofh.mod.updater.UpdateManager;
import cofh.thermalfoundation.block.TFBlocks;
import cofh.thermalfoundation.core.Proxy;
import cofh.thermalfoundation.core.TFProps;
import cofh.thermalfoundation.fluid.TFFluids;
import cofh.thermalfoundation.gui.GuiHandler;
import cofh.thermalfoundation.gui.TFCreativeTab;
import cofh.thermalfoundation.item.Equipment;
import cofh.thermalfoundation.item.TFItems;
import cofh.thermalfoundation.network.PacketTFBase;
import cofh.thermalfoundation.plugins.TFPlugins;
import cofh.thermalfoundation.util.EventHandlerLexicon;
import cofh.thermalfoundation.util.IMCHandler;
import cofh.thermalfoundation.util.LexiconManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.CustomProperty;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
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
	public static final String version = "1.7.10R1.2.6B1";
	public static final String version_max = "1.7.10R1.3.0";
	public static final String dependencies = CoFHCore.version_group;
	public static final String modGuiFactory = "cofh.thermalfoundation.gui.GuiConfigTFFactory";

	public static final String version_group = "required-after:" + modId + "@[" + version + "," + version_max + ");";
	public static final String releaseURL = "https://raw.github.com/CoFH/VERSION/master/" + modId;

	@Instance(modId)
	public static ThermalFoundation instance;

	@SidedProxy(clientSide = "cofh.thermalfoundation.core.ProxyClient", serverSide = "cofh.thermalfoundation.core.Proxy")
	public static Proxy proxy;

	public static final Logger log = LogManager.getLogger(modId);
	public static final ConfigHandler config = new ConfigHandler(version);
	public static final ConfigHandler configClient = new ConfigHandler(version);
	public static final GuiHandler guiHandler = new GuiHandler();

	public static CreativeTabs tabCommon;
	public static CreativeTabs tabTools = CreativeTabs.tabTools;
	public static CreativeTabs tabArmor = CreativeTabs.tabCombat;

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
		configClient.setConfiguration(new Configuration(new File(CoFHProps.configDir, "cofh/thermalfoundation/client.cfg"), true));

		tabCommon = new TFCreativeTab();

		cleanConfig(true);
		configOptions();

		TFFluids.preInit();
		TFItems.preInit();
		TFBlocks.preInit();
		TFPlugins.preInit();

		LexiconManager.preInit();
	}

	@EventHandler
	public void initialize(FMLInitializationEvent event) {

		TFFluids.initialize();
		TFItems.initialize();
		TFBlocks.initialize();
		TFPlugins.initialize();

		/* Init World Gen */
		loadWorldGeneration();

		/* Register Handlers */
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
		MinecraftForge.EVENT_BUS.register(proxy);
		EventHandlerLexicon.initialize();
		PacketTFBase.initialize();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		TFFluids.postInit();
		TFItems.postInit();
		TFBlocks.postInit();
		TFPlugins.postInit();

		proxy.registerEntities();
		proxy.registerRenderInformation();
	}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event) {

		IMCHandler.instance.handleIMC(FMLInterModComms.fetchRuntimeMessages(this));

		LexiconManager.loadComplete();

		TFPlugins.loadComplete();

		cleanConfig(false);
		config.cleanUp(false, true);
		configClient.cleanUp(false, true);

		log.info("Thermal Foundation: Load Complete.");
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {

		TFFluids.registerDispenserHandlers();
	}

	@EventHandler
	public void handleIMC(IMCEvent theIMC) {

		IMCHandler.instance.handleIMC(theIMC.getMessages());
	}

	/* LOADING FUNCTIONS */
	void configOptions() {

		String category;
		String comment;

		/* GENERAL */
		comment = "If TRUE, Fire-Immune mobs have a chance to drop Sulfur.";
		TFProps.dropSulfurFireImmune = ThermalFoundation.config.get("General", "FireImmuneDropSulfur", TFProps.dropSulfurFireImmune, comment);

		/* GRAPHICS */
		comment = "Set to FALSE to revert Blaze Powder to the default Minecraft icon.";
		TFProps.iconBlazePowder = ThermalFoundation.configClient.get("Icons", "BlazePowder", TFProps.iconBlazePowder, comment);

		comment = "Set to TRUE for Ender devices to be a bit more Cagey year-round.";
		TFProps.renderStarfieldCage = ThermalFoundation.configClient.get("Render", "CageyEnder", TFProps.renderStarfieldCage, comment);

		/* INTERFACE */
		category = "Interface.CreativeTab";
		boolean armorTab = false;
		boolean toolTab = false;

		comment = "Set to TRUE to put Thermal Foundation Armor under the general \"Thermal Foundation\" Creative Tab.";
		armorTab = configClient.get(category, "ArmorInCommonTab", armorTab);

		comment = "Set to TRUE to put Thermal Foundation Tools under the general \"Thermal Foundation\" Creative Tab.";
		toolTab = configClient.get(category, "ToolsInCommonTab", toolTab);

		/* EQUIPMENT */
		category = "Equipment";
		comment = "Set to TRUE to disable ALL armor sets.";
		TFProps.disableAllArmor = config.get(category, "DisableAllArmor", TFProps.disableAllArmor, comment);

		comment = "Set to TRUE to disable ALL tool sets.";
		TFProps.disableAllTools = config.get(category, "DisableAllTools", TFProps.disableAllTools, comment);

		comment = "Set to FALSE to hide all disabled equipment from the Creative Tabs and NEI.";
		TFProps.showDisabledEquipment = config.get(category, "ShowDisabledEquipment", TFProps.showDisabledEquipment, comment);

		if (armorTab) {
			tabArmor = tabCommon;
		} else {
			if (!TFProps.disableAllArmor || (TFProps.disableAllArmor && TFProps.showDisabledEquipment)) {
				tabArmor = new TFCreativeTab("Armor") {

					@Override
					protected ItemStack getStack() {

						return Equipment.Invar.armorPlate;
					}
				};
			}
		}
		if (toolTab) {
			tabTools = tabCommon;
		} else {
			if (!TFProps.disableAllTools || (TFProps.disableAllTools && TFProps.showDisabledEquipment)) {
				tabTools = new TFCreativeTab("Tools") {

					@Override
					protected ItemStack getStack() {

						return Equipment.Invar.toolPickaxe;
					}
				};
			}
		}

	}

	void cleanConfig(boolean preInit) {

		if (preInit) {

		}
		String prefix = "config.thermalfoundation.";
		String[] categoryNames = config.getCategoryNames().toArray(new String[config.getCategoryNames().size()]);
		for (int i = 0; i < categoryNames.length; i++) {
			config.getCategory(categoryNames[i]).setLanguageKey(prefix + categoryNames[i]).setRequiresMcRestart(true);
		}
		categoryNames = configClient.getCategoryNames().toArray(new String[configClient.getCategoryNames().size()]);
		for (int i = 0; i < categoryNames.length; i++) {
			configClient.getCategory(categoryNames[i]).setLanguageKey(prefix + categoryNames[i]).setRequiresMcRestart(true);
		}
	}

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
