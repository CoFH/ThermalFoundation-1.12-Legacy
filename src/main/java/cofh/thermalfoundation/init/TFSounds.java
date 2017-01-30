package cofh.thermalfoundation.init;

import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class TFSounds {

	private TFSounds() {

	}

	static {
		BLIZZ_AMBIENT = getRegisteredSoundEvent("mob_blizz_ambient");
		BLIZZ_ATTACK = getRegisteredSoundEvent("mob_blizz_attack");

		BLITZ_AMBIENT = getRegisteredSoundEvent("mob_blitz_ambient");
		BLITZ_ATTACK = getRegisteredSoundEvent("mob_blitz_attack");

		BASALZ_AMBIENT = getRegisteredSoundEvent("mob_basalz_ambient");
		BASALZ_ATTACK = getRegisteredSoundEvent("mob_basalz_attack");
	}

	private static SoundEvent getRegisteredSoundEvent(String id) {

		return new SoundEvent(new ResourceLocation(ThermalFoundation.MOD_ID + ":" + id));
	}

	public static final SoundEvent BLIZZ_AMBIENT;
	public static final SoundEvent BLIZZ_ATTACK;

	public static final SoundEvent BLITZ_AMBIENT;
	public static final SoundEvent BLITZ_ATTACK;

	public static final SoundEvent BASALZ_AMBIENT;
	public static final SoundEvent BASALZ_ATTACK;

}
