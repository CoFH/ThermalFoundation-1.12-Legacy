package cofh.thermalfoundation.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

/**
 * Created by brandon3055 on 21/10/2016.
 */
public class ModSounds {

	public static final SoundEvent BLIZZ_AMBIENT;
	public static final SoundEvent BLIZZ_ATTACK;
	public static final SoundEvent BLIZZ_BREATHE;
	public static final SoundEvent BLITZ_AMBIENT;
	public static final SoundEvent BLITZ_ATTACK;
	public static final SoundEvent BLITZ_BREATHE;
	public static final SoundEvent BASALZ_AMBIENT;
	public static final SoundEvent BASALZ_ATTACK;
	public static final SoundEvent BASALZ_BREATHE;

	static {
		BLIZZ_AMBIENT = getRegisteredSoundEvent("thermalfoundation:mobBlizzAmbient");
		BLIZZ_ATTACK = getRegisteredSoundEvent("thermalfoundation:mobBlizzAttack");
		BLIZZ_BREATHE = getRegisteredSoundEvent("thermalfoundation:mobBlizzBreathe");
		BLITZ_AMBIENT = getRegisteredSoundEvent("thermalfoundation:mobBlitzAmbient");
		BLITZ_ATTACK = getRegisteredSoundEvent("thermalfoundation:mobBlitzAttack");
		BLITZ_BREATHE = getRegisteredSoundEvent("thermalfoundation:mobBlitzBreathe");
		BASALZ_AMBIENT = getRegisteredSoundEvent("thermalfoundation:mobBasalzAmbient");
		BASALZ_ATTACK = getRegisteredSoundEvent("thermalfoundation:mobBasalzAttack");
		BASALZ_BREATHE = getRegisteredSoundEvent("thermalfoundation:mobBasalzBreathe");
	}

	private static SoundEvent getRegisteredSoundEvent(String id) {

		return new SoundEvent(new ResourceLocation(id));
	}

}
