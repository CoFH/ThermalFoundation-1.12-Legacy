package cofh.thermalfoundation.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class TFSounds {

	private TFSounds() {

	}

	static {
		BLIZZ_AMBIENT = getRegisteredSoundEvent("thermalfoundation:mobBlizzAmbient");
		BLIZZ_ATTACK = getRegisteredSoundEvent("thermalfoundation:mobBlizzAttack");

		BLITZ_AMBIENT = getRegisteredSoundEvent("thermalfoundation:mobBlitzAmbient");
		BLITZ_ATTACK = getRegisteredSoundEvent("thermalfoundation:mobBlitzAttack");

		BASALZ_AMBIENT = getRegisteredSoundEvent("thermalfoundation:mobBasalzAmbient");
		BASALZ_ATTACK = getRegisteredSoundEvent("thermalfoundation:mobBasalzAttack");
	}

	private static SoundEvent getRegisteredSoundEvent(String id) {

		return new SoundEvent(new ResourceLocation(id));
	}

	public static final SoundEvent BLIZZ_AMBIENT;
	public static final SoundEvent BLIZZ_ATTACK;

	public static final SoundEvent BLITZ_AMBIENT;
	public static final SoundEvent BLITZ_ATTACK;

	public static final SoundEvent BASALZ_AMBIENT;
	public static final SoundEvent BASALZ_ATTACK;

}
