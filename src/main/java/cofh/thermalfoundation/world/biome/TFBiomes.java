package cofh.thermalfoundation.world.biome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

public class TFBiomes {

	public static final TFBiomes INSTANCE = new TFBiomes();

	private TFBiomes() {

	}

	/* INIT */
	public static void preInit() {

		biomeFluxed = new BiomeFluxed(new BiomeProperties("Fluxed").setTemperature(0.8F).setRainfall(0.7F).setBaseHeight(0.1F).setHeightVariation(0.0F));

		MinecraftForge.EVENT_BUS.register(INSTANCE);
	}

	public static void postInit() {

		for (WrappedBiomeEntry wrapped : biomeList) {
			wrapped.initialize();
		}
	}

	/* EVENT HANDLING */
	@SubscribeEvent
	public void registerBiomes(RegistryEvent.Register<Biome> event) {

		IForgeRegistry<Biome> registry = event.getRegistry();

		register(registry, biomeFluxed, "meadow", BiomeType.COOL, 7000000, true, true, true, PLAINS, LUSH, WET);

	}

	public static void register(IForgeRegistry<Biome> registry, Biome biome, String name, BiomeType type, int weight, boolean enable, boolean spawn, boolean village, BiomeDictionary.Type... biomeDictTypes) {

		if (!enable) {
			return;
		}
		biome.setRegistryName(name);
		registry.register(biome);

		for (BiomeDictionary.Type biomeDictType : biomeDictTypes) {
			BiomeDictionary.addTypes(biome, biomeDictType);
		}
		biomeList.add(new WrappedBiomeEntry(biome, type, weight, spawn, village));
	}

	private static ArrayList<WrappedBiomeEntry> biomeList = new ArrayList<>();

	/* REFERENCES */
	public static BiomeFluxed biomeFluxed;

	/* BIOME DATA CLASS */
	public static class WrappedBiomeEntry {

		public final Biome biome;
		public final BiomeType type;
		public final boolean spawn;
		public final boolean village;
		public final BiomeManager.BiomeEntry entry;

		public WrappedBiomeEntry(Biome biome, BiomeType type, int weight, boolean spawn, boolean village) {

			this.biome = biome;
			this.type = type;
			this.spawn = spawn;
			this.village = village;
			this.entry = new BiomeManager.BiomeEntry(biome, weight);
		}

		public void initialize() {

			BiomeManager.addBiome(type, entry);

			if (village) {
				BiomeManager.addVillageBiome(biome, true);
			}
			if (spawn) {
				BiomeManager.addSpawnBiome(biome);
			}
		}
	}

}
