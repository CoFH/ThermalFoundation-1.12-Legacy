package thermalfoundation.world;

import cofh.api.world.IFeatureGenerator;
import cofh.api.world.IFeatureHandler;
import cofh.util.ChunkCoord;
import cofh.util.MathHelper;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType;
import net.minecraftforge.event.world.ChunkDataEvent;

import thermalfoundation.ThermalFoundation;

public class WorldHandler implements IWorldGenerator, IFeatureHandler {

	private static List<IFeatureGenerator> features = new ArrayList<IFeatureGenerator>();
	private static HashSet<String> featureNames = new HashSet<String>();
	private static HashSet<EventType> vanillaGenEvents = new HashSet<EventType>();
	private static HashSet<Integer> dimensionBlacklist = new HashSet<Integer>();

	private static long genHash = 0;

	public static final int MAX_BEDROCK_LAYERS = 5;

	public static boolean allowCustomGen = false;
	public static boolean genFlatBedrock = false;
	public static boolean genReplaceVanilla = false;
	public static boolean retroFlatBedrock = false;
	public static boolean retroOreGeneration = false;
	public static boolean forceFullRegeneration = false;

	public static ArrayList<String> registeredFeatureNames = new ArrayList<String>();

	public static int layersBedrock = 1;

	static {
		vanillaGenEvents.add(OreGenEvent.GenerateMinable.EventType.COAL);
		vanillaGenEvents.add(OreGenEvent.GenerateMinable.EventType.DIAMOND);
		vanillaGenEvents.add(OreGenEvent.GenerateMinable.EventType.DIRT);
		vanillaGenEvents.add(OreGenEvent.GenerateMinable.EventType.GOLD);
		vanillaGenEvents.add(OreGenEvent.GenerateMinable.EventType.GRAVEL);
		vanillaGenEvents.add(OreGenEvent.GenerateMinable.EventType.IRON);
		vanillaGenEvents.add(OreGenEvent.GenerateMinable.EventType.LAPIS);
		vanillaGenEvents.add(OreGenEvent.GenerateMinable.EventType.REDSTONE);
	}

	public static void initialize() {

		String category = "feature";
		String comment = null;

		comment = "This allows for custom generation to be specified in the WorldCustomGen.txt file.";
		allowCustomGen = ThermalFoundation.config.get(category, "AllowCustomGeneration", false, comment);

		comment = "This allows for vanilla ore generation to be REPLACED. Configure in the 'world.vanilla' section of the CoFHWorld-Generation.cfg; vanilla defaults have been provided.";
		genReplaceVanilla = ThermalFoundation.config.get(category, "ReplaceVanillaGeneration", false, comment);

		comment = "This will flatten the bedrock layer.";
		genFlatBedrock = ThermalFoundation.config.get(category, "FlatBedrock", false, comment);

		comment = "The number of layers of bedrock to flatten to. (Max: " + MAX_BEDROCK_LAYERS + ")";
		layersBedrock = ThermalFoundation.config.get(category, "FlatBedrockLayers", 1, comment);
		layersBedrock = MathHelper.clampI(layersBedrock, 1, MAX_BEDROCK_LAYERS);

		comment = "If FlatBedrock is enabled, this will enforce it in previously generated chunks.";
		retroFlatBedrock = ThermalFoundation.config.get(category, "RetroactiveFlatBedrock", false, comment);

		comment = "This will retroactively generate ores in previously generated chunks.";
		retroOreGeneration = ThermalFoundation.config.get(category, "RetroactiveOreGeneration", false, comment);

		GameRegistry.registerWorldGenerator(instance, 0);
		MinecraftForge.EVENT_BUS.register(instance);
		MinecraftForge.ORE_GEN_BUS.register(instance);

		if (genFlatBedrock && retroFlatBedrock || retroOreGeneration) {
			MinecraftForge.EVENT_BUS.register(TickHandlerWorld.instance);
		}
	}

	public static WorldHandler instance = new WorldHandler();

	@SubscribeEvent
	public void handleChunkSaveEvent(ChunkDataEvent.Save event) {

		NBTTagCompound tag = new NBTTagCompound();

		if (retroFlatBedrock && genFlatBedrock) {
			tag.setBoolean("Bedrock", true);
		}
		if (retroOreGeneration) {
			tag.setLong("Features", genHash);
		}
		event.getData().setTag("CoFHWorld", tag);
	}

	@SubscribeEvent
	public void handleChunkLoadEvent(ChunkDataEvent.Load event) {

		int dim = event.world.provider.dimensionId;

		if (dimensionBlacklist.contains(dim)) {
			return;
		}
		boolean bedrock = false;
		boolean features = false;
		boolean regen = false;
		NBTTagCompound tag = (NBTTagCompound) event.getData().getTag("CoFHWorld");

		if (tag != null) {
			bedrock = !tag.hasKey("Bedrock") && retroFlatBedrock && genFlatBedrock;
			features = tag.getLong("Features") != genHash && retroOreGeneration;
		}
		ChunkCoord cCoord = new ChunkCoord(event.getChunk());

		if (tag == null && (retroFlatBedrock && genFlatBedrock || retroOreGeneration)) {
			regen = true;
		}
		if (bedrock) {
			ThermalFoundation.log.info("Retroactively flattening bedrock for the chunk at " + cCoord.toString() + ".");
			regen = true;
		}
		if (features) {
			ThermalFoundation.log.info("Retroactively generating features for the chunk at " + cCoord.toString() + ".");
			regen = true;
		}
		if (regen) {
			ArrayList<ChunkCoord> chunks = TickHandlerWorld.chunksToGen.get(Integer.valueOf(dim));

			if (chunks == null) {
				TickHandlerWorld.chunksToGen.put(Integer.valueOf(dim), new ArrayList<ChunkCoord>());
				chunks = TickHandlerWorld.chunksToGen.get(Integer.valueOf(dim));
			}
			if (chunks != null) {
				chunks.add(cCoord);
				TickHandlerWorld.chunksToGen.put(Integer.valueOf(dim), chunks);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void handleOreGenEvent(OreGenEvent.GenerateMinable event) {

		if (!genReplaceVanilla) {
			return;
		}
		if (vanillaGenEvents.contains(event.type)) {
			event.setResult(Result.DENY);
		}
	}

	/* IWorldGenerator */
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

		generateWorld(random, chunkX, chunkZ, world, true);
	}

	/* IFeatureHandler */
	@Override
	public boolean registerFeature(IFeatureGenerator feature) {

		if (featureNames.contains(feature.getFeatureName())) {
			return false;
		}
		featureNames.add(feature.getFeatureName());
		features.add(feature);
		genHash += feature.getFeatureName().hashCode();

		return true;
	}

	/* HELPER FUNCTIONS */
	public static boolean addFeature(IFeatureGenerator feature) {

		return instance.registerFeature(feature);
	}

	public void generateWorld(Random random, int chunkX, int chunkZ, World world, boolean newGen) {

		replaceBedrock(random, chunkX, chunkZ, world, newGen);

		if (!newGen && !retroOreGeneration) {
			return;
		}
		if (world.provider.dimensionId == 1 || world.provider.dimensionId == -1) {
			return;
		}
		for (IFeatureGenerator feature : features) {
			feature.generateFeature(random, chunkX, chunkZ, world, newGen);
		}
		if (!newGen) {
			world.getChunkFromChunkCoords(chunkX, chunkZ).setChunkModified();
		}
	}

	public void replaceBedrock(Random random, int chunkX, int chunkZ, World world, boolean newGen) {

		if (!genFlatBedrock || !newGen && !retroFlatBedrock) {
			return;
		}
		Block filler = world.getBiomeGenForCoords(chunkX, chunkZ).fillerBlock;

		for (int blockX = 0; blockX < 16; blockX++) {
			for (int blockZ = 0; blockZ < 16; blockZ++) {
				for (int blockY = MAX_BEDROCK_LAYERS; blockY > -1 + layersBedrock; blockY--) {
					if (world.getBlock(chunkX * 16 + blockX, blockY, chunkZ * 16 + blockZ) == Blocks.bedrock) {
						world.setBlock(chunkX * 16 + blockX, blockY, chunkZ * 16 + blockZ, filler, 0, 2);
					}
				}
				for (int blockY = layersBedrock - 1; blockY > 0; blockY--) {
					if (world.getBlock(chunkX * 16 + blockX, blockY, chunkZ * 16 + blockZ) != Blocks.air) {
						world.setBlock(chunkX * 16 + blockX, blockY, chunkZ * 16 + blockZ, Blocks.bedrock, 0, 2);
					}
				}
			}
		}
	}

}
