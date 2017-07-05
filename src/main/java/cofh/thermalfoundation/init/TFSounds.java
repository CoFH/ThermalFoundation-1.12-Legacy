package cofh.thermalfoundation.init;

import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class TFSounds {

	public static final TFSounds INSTANCE = new TFSounds();

	private TFSounds() {

	}

	public static void preInit() {

		MinecraftForge.EVENT_BUS.register(INSTANCE);
	}

	/* EVENT HANDLING */
	@SubscribeEvent
	public void registerSounds(RegistryEvent.Register<SoundEvent> event) {

		blizzAmbient = registerSoundEvent("mob_blizz_ambient");
		blizzAttack = registerSoundEvent("mob_blizz_attack");

		blitzAmbient = registerSoundEvent("mob_blitz_ambient");
		blitzAttack = registerSoundEvent("mob_blitz_attack");

		basalzAmbient = registerSoundEvent("mob_basalz_ambient");
		basalzAttack = registerSoundEvent("mob_basalz_attack");
	}

	/* HELPERS */
	private static SoundEvent registerSoundEvent(String id) {

		SoundEvent sound = new SoundEvent(new ResourceLocation(ThermalFoundation.MOD_ID + ":" + id));
		sound.setRegistryName(id);
		ForgeRegistries.SOUND_EVENTS.register(sound);
		return sound;
	}

	public static SoundEvent blizzAmbient;
	public static SoundEvent blizzAttack;

	public static SoundEvent blitzAmbient;
	public static SoundEvent blitzAttack;

	public static SoundEvent basalzAmbient;
	public static SoundEvent basalzAttack;

}
