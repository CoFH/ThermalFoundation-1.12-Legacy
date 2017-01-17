package cofh.thermalfoundation.init;

import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class TFSounds {

	private TFSounds() {

	}

	static {
		BLIZZ_AMBIENT = getRegisteredSoundEvent("mobBlizzAmbient");
		BLIZZ_ATTACK = getRegisteredSoundEvent("mobBlizzAttack");

		BLITZ_AMBIENT = getRegisteredSoundEvent("mobBlitzAmbient");
		BLITZ_ATTACK = getRegisteredSoundEvent("mobBlitzAttack");

		BASALZ_AMBIENT = getRegisteredSoundEvent("mobBasalzAmbient");
		BASALZ_ATTACK = getRegisteredSoundEvent("mobBasalzAttack");
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
