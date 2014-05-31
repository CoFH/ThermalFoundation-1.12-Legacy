package thermalfoundation;

import cofh.CoFHCore;
import cofh.core.CoFHProps;
import cofh.mod.BaseMod;
import cofh.updater.UpdateManager;
import cofh.util.ConfigHandler;
import cofh.world.FeatureParser;
import cofh.world.WorldHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import thermalfoundation.block.TFBlocks;
import thermalfoundation.core.Proxy;
import thermalfoundation.fluid.TFFluids;
import thermalfoundation.gui.TFCreativeTab;
import thermalfoundation.item.TFItems;

@Mod(modid = ThermalFoundation.modId, name = ThermalFoundation.modName, version = ThermalFoundation.version, dependencies = ThermalFoundation.dependencies,
		canBeDeactivated = false)
public class ThermalFoundation extends BaseMod {

	public static final String modId = "ThermalFoundation";
	public static final String modName = "Thermal Foundation";
	public static final String version = "1.7.2R1.0.0B1";
	public static final String dependencies = "required-after:CoFHCore@[" + CoFHCore.version + ",)";
	public static final String releaseURL = "http://teamcofh.com/thermalfoundation/version/version.txt";

	@Instance(modId)
	public static ThermalFoundation instance;

	@SidedProxy(clientSide = "thermalfoundation.core.ProxyClient", serverSide = "thermalfoundation.core.Proxy")
	public static Proxy proxy;

	public static final ConfigHandler config = new ConfigHandler(version);
	public static final Logger log = LogManager.getLogger(modId);

	public static final CreativeTabs tab = new TFCreativeTab();

	/* INIT SEQUENCE */
	public ThermalFoundation() {

		super(log);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		UpdateManager.registerUpdater(new UpdateManager(this, releaseURL));

		config.setConfiguration(new Configuration(new File(CoFHProps.configDir, "/cofh/ThermalFoundation.cfg")));
		WorldHandler.initialize();

		TFFluids.preInit();
		TFItems.preInit();
		TFBlocks.preInit();

		config.save();
	}

	@EventHandler
	public void initialize(FMLInitializationEvent event) {

		TFFluids.initialize();
		TFItems.initialize();
		TFBlocks.initialize();

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

		try {
			FeatureParser.parseGenerationFile();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {

		TFFluids.registerDispenserHandlers();
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

	// void loadWorldGeneration() {
	//
	// String category = "world.vanilla";
	// ConfigCategory cat = configGeneration.getCategory(category);
	// cat.setComment("This section controls generation specifically related to vanilla Minecraft ores. THESE VALUES ARE USED INSTEAD OF STANDARD GENERATION IF REPLACEMENT IS ENABLED.");
	//
	// addFeature("world.vanilla", new ItemStack(Blocks.dirt), "dirt", 32, 20, 0, 128, ORE_UNIFORM, true, genReplaceVanilla);
	// addFeature("world.vanilla", new ItemStack(Blocks.gravel), "gravel", 32, 10, 0, 128, ORE_UNIFORM, true, genReplaceVanilla);
	// addFeature("world.vanilla", new ItemStack(Blocks.coal_ore), "coal", 16, 20, 0, 128, ORE_UNIFORM, true, genReplaceVanilla);
	// addFeature("world.vanilla", new ItemStack(Blocks.iron_ore), "iron", 8, 20, 0, 64, ORE_UNIFORM, true, genReplaceVanilla);
	// addFeature("world.vanilla", new ItemStack(Blocks.gold_ore), "gold", 8, 2, 0, 32, ORE_UNIFORM, true, genReplaceVanilla);
	// addFeature("world.vanilla", new ItemStack(Blocks.redstone_ore), "redstone", 7, 8, 0, 16, ORE_UNIFORM, true, genReplaceVanilla);
	// addFeature("world.vanilla", new ItemStack(Blocks.diamond_ore), "diamond", 7, 1, 0, 16, ORE_UNIFORM, true, genReplaceVanilla);
	// addFeature("world.vanilla", new ItemStack(Blocks.lapis_ore), "lapis", 6, 1, 16, 16, ORE_NORMAL, true, genReplaceVanilla);
	//
	// configGeneration.save();
	// }
	//
	// void parseCustomGeneration() {
	//
	// customGen = new File(CoFHProps.configDir, "/cofh/WorldCustomGen.txt");
	// try {
	// if (!customGen.exists()) {
	// customGen.createNewFile();
	// Files.write((CUSTOM_GEN_TEMPLATE + CUSTOM_GEN_FORMAT + CUSTOM_GEN_UNIFORM + CUSTOM_GEN_NORMAL).getBytes(), customGen);
	// }
	// List<String> lines = Files.readLines(customGen, Charsets.UTF_8);
	//
	// String[] tokens;
	// String genType;
	// @SuppressWarnings("unused")
	// String name;
	// int oreId;
	// int oreMeta;
	// int clusterSize;
	// int numClusters;
	// int minY;
	// int maxY;
	// boolean regen;
	// int type;
	//
	// for (String line : lines) {
	// if (line.startsWith("#")) {
	// continue;
	// }
	// tokens = line.split("\t");
	//
	// if (tokens.length != 9) {
	// continue;
	// }
	// try {
	// genType = tokens[0].toLowerCase(Locale.ENGLISH);
	// name = tokens[1].toLowerCase(Locale.ENGLISH);
	// oreId = Integer.valueOf(tokens[2]);
	// oreMeta = Integer.valueOf(tokens[3]);
	// clusterSize = Integer.valueOf(tokens[4]);
	// numClusters = Integer.valueOf(tokens[5]);
	// minY = Integer.valueOf(tokens[6]);
	// maxY = Integer.valueOf(tokens[7]);
	// regen = Boolean.valueOf(tokens[8]);
	//
	// if (genType.equals("uniform")) {
	// type = ORE_UNIFORM;
	// } else if (genType.equals("normal")) {
	// type = ORE_NORMAL;
	// } else {
	// log.log(Level.ERROR, "The WorldCustomGen.txt file has an invalid entry: '" + tokens[1] + "'.");
	// continue;
	// }
	// if (oreId >= 4096) {
	// log.log(Level.ERROR, "The WorldCustomGen.txt file has an invalid entry: '" + tokens[1] + "'. The ID is too high!");
	// } else {
	// addCustomFeature(oreId, oreMeta, tokens[1], clusterSize, numClusters, minY, maxY, type, regen, true);
	// }
	// } catch (NumberFormatException e) {
	// log.log(Level.ERROR, "The WorldCustomGen.txt file has an invalid entry: '" + tokens[1] + "'.");
	// }
	// }
	// } catch (IOException e) {
	// log.log(Level.ERROR, "The WorldCustomGen.txt file could not be read! Skipping custom generation.");
	// }
	// }
	//
	// @Deprecated
	// // until converted to strings so i know where it's used
	// public static void addCustomFeature(int oreId, int oreMeta, String featureName, int clusterSize, int numClusters, int minY, int maxY, int feature,
	// boolean regen, boolean enable) { // TODO: convert this to strings
	//
	// if (registeredFeatureNames.contains(featureName)) {
	// log.log(Level.ERROR, "There is a duplicate feature entry name: '" + featureName + "' - only the first one will be used.");
	// return;
	// }
	// if (true) {// Block.blocksList[oreId] == null) {
	// log.log(Level.ERROR, "The entry for custom ore '" + featureName + "' is invalid - the block is null.");
	// return;
	// }
	// @SuppressWarnings("unused")
	// String category = "world.custom." + featureName.toLowerCase(Locale.ENGLISH);
	//
	// int bId = configGeneration.get(category, "BlockId", oreId);
	// int bMeta = configGeneration.get(category, "BlockMeta", oreMeta);
	//
	// // addFeature("world.custom", new ItemStack(bId, 1, bMeta), featureName, clusterSize, numClusters, minY, maxY, feature, regen, enable);
	// }
	//
	// public static void addFeature(String category, ItemStack stack, String featureName, int clusterSize, int numClusters, int minY, int maxY, int feature,
	// boolean regen, boolean enable) {
	//
	// List<WeightedRandomBlock> resList = new ArrayList<WeightedRandomBlock>();
	// resList.add(new WeightedRandomBlock(stack));
	// addFeature(category, resList, featureName, clusterSize, numClusters, minY, maxY, feature, regen, enable);
	// }
	//
	// public static void addFeature(String category, List<WeightedRandomBlock> resList, String featureName, int clusterSize, int numClusters, int minY, int
	// maxY,
	// int feature, boolean regen, boolean enable) {
	//
	// category += "." + featureName.toLowerCase(Locale.ENGLISH);
	// ConfigCategory cat = configGeneration.getCategory(category);
	//
	// String featureType = "<UNIFORM>";
	// String strMin = "MinY";
	// String strMax = "MaxY";
	//
	// if (feature == ORE_NORMAL) {
	// featureType = "<NORMAL>";
	// strMin = "MeanY";
	// strMax = "MaxVar";
	// }
	// cat.setComment(featureType + " Generation settings for " + StringHelper.titleCase(featureName) + "; Defaults: ClusterSize = " + clusterSize
	// + ", NumClusters = " + numClusters + ", " + strMin + " = " + minY + ", " + strMax + " = " + maxY);
	//
	// clusterSize = configGeneration.get(category, "ClusterSize", clusterSize);
	// numClusters = configGeneration.get(category, "NumClusters", numClusters);
	// minY = configGeneration.get(category, strMin, minY);
	// maxY = configGeneration.get(category, strMax, maxY);
	// regen = configGeneration.get(category, "RetroGen", regen);
	//
	// configGeneration.save();
	//
	// if (!enable) {
	// return;
	// }
	// if (feature == ORE_UNIFORM) {
	// WorldHandler.addFeature(new FeatureOreGenUniform(featureName, new WorldGenMinableCluster(resList, clusterSize), numClusters, minY, maxY, regen));
	// } else if (feature == ORE_NORMAL) {
	// WorldHandler.addFeature(new FeatureOreGenNormal(featureName, new WorldGenMinableCluster(resList, clusterSize), numClusters, minY, maxY, regen));
	// }
	// }
	//
	// public static void convertLegacyConfig(boolean stage) {
	//
	// if (!stage) {
	// File oldFile = new File(CoFHProps.configDir, "/cofh/World.cfg");
	// if (oldFile.exists()) {
	// try {
	// CoreUtils.copyFileUsingChannel(oldFile, new File(CoFHProps.configDir, "/cofh/CoFHWorld-Generation.cfg"));
	// } catch (IOException e) {
	//
	// }
	// oldFile.renameTo(new File(CoFHProps.configDir, "/cofh/CoFHWorld.cfg"));
	// }
	// return;
	// }
	// config.getCategory("world.feature").remove("Vanilla.Augment");
	// config.removeCategory("world.feature");
	// config.removeCategory("world.tweak");
	// config.removeCategory("world");
	//
	// configGeneration.removeCategory("feature");
	// }

}
