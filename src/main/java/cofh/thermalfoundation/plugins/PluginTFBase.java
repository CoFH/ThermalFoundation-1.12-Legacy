package cofh.thermalfoundation.plugins;

import cofh.core.util.PluginCore;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionType;
import net.minecraftforge.fml.common.Loader;

public abstract class PluginTFBase extends PluginCore {

	public PluginTFBase(String modId, String modName) {

		super(modId, modName);
	}

	/* IInitializer */
	@Override
	public boolean initialize() {

		String category = "Plugins";
		String comment = "If TRUE, support for " + modName + " is enabled.";
		enable = ThermalFoundation.CONFIG.getConfiguration().getBoolean(modName, category, true, comment) && Loader.isModLoaded(modId);

		if (!enable) {
			return false;
		}
		initializeDelegate();
		return !error;
	}

	public boolean register() {

		if (!enable) {
			return false;
		}
		try {
			registerDelegate();
		} catch (Throwable t) {
			ThermalFoundation.LOG.error("Thermal Foundation: " + modName + " Plugin encountered an error:", t);
			error = true;
		}
		if (!error) {
			ThermalFoundation.LOG.info("Thermal Foundation: " + modName + " Plugin Enabled.");
		}
		return !error;
	}

	public void initializeDelegate() {

	}

	public void registerDelegate() {

	}

	/* HELPERS */
	public PotionType getPotionType(String baseName, String qualifier) {

		if (qualifier.isEmpty()) {
			return PotionType.getPotionTypeForName(modId + ":" + baseName);
		}
		PotionType ret = PotionType.getPotionTypeForName(modId + ":" + baseName + "_" + qualifier);

		if (ret == PotionTypes.EMPTY) {
			ret = PotionType.getPotionTypeForName(modId + ":" + qualifier + "_" + baseName);
		}
		return ret;
	}

}
