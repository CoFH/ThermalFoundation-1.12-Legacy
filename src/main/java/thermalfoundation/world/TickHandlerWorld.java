package thermalfoundation.world;

import cofh.util.ChunkCoord;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;

import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.world.World;

public class TickHandlerWorld {

	public static TickHandlerWorld instance = new TickHandlerWorld();

	public static TMap<Integer, ArrayList<ChunkCoord>> chunksToGen = new THashMap<Integer, ArrayList<ChunkCoord>>();

	@SubscribeEvent
	public void tickEnd(WorldTickEvent event) {

		if (event.phase != Phase.END | event.side != Side.SERVER) {
			return;
		}
		World world = event.world;
		int dim = world.provider.dimensionId;
		ArrayList<ChunkCoord> chunks = chunksToGen.get(Integer.valueOf(dim));

		if (chunks != null && chunks.size() > 0) {
			ChunkCoord c = chunks.get(0);
			long worldSeed = world.getSeed();
			Random rand = new Random(worldSeed);
			long xSeed = rand.nextLong() >> 2 + 1L;
			long zSeed = rand.nextLong() >> 2 + 1L;
			rand.setSeed(xSeed * c.chunkX + zSeed * c.chunkZ ^ worldSeed);
			WorldHandler.instance.generateWorld(rand, c.chunkX, c.chunkZ, world, false);
			chunks.remove(0);
			chunksToGen.put(Integer.valueOf(dim), chunks);
		}
	}

}
