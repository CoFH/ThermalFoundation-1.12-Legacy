package cofh.thermalfoundation.init;

import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class TFSounds {

	private TFSounds() {

	}

	public static void initialize() {

		BLIZZ_AMBIENT = getRegisteredSoundEvent("mob_blizz_ambient");
		BLIZZ_ATTACK = getRegisteredSoundEvent("mob_blizz_attack");

		BLITZ_AMBIENT = getRegisteredSoundEvent("mob_blitz_ambient");
		BLITZ_ATTACK = getRegisteredSoundEvent("mob_blitz_attack");

		BASALZ_AMBIENT = getRegisteredSoundEvent("mob_basalz_ambient");
		BASALZ_ATTACK = getRegisteredSoundEvent("mob_basalz_attack");
	}

	private static SoundEvent getRegisteredSoundEvent(String id) {

		SoundEvent sound = new SoundEvent(new ResourceLocation(ThermalFoundation.MOD_ID + ":" + id));
		sound.setRegistryName(id);
		ForgeRegistries.SOUND_EVENTS.register(sound);
		return sound;
	}

	public static SoundEvent BLIZZ_AMBIENT;
	public static SoundEvent BLIZZ_ATTACK;

	public static SoundEvent BLITZ_AMBIENT;
	public static SoundEvent BLITZ_ATTACK;

	public static SoundEvent BASALZ_AMBIENT;
	public static SoundEvent BASALZ_ATTACK;

}
