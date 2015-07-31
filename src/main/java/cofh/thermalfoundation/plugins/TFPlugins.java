package cofh.thermalfoundation.plugins;

import cofh.core.CoFHProps;
import cofh.thermalfoundation.ThermalFoundation;

import java.util.ArrayList;

public class TFPlugins {

	static class Plugin {

		public Class<?> pluginClass = null;
		public String pluginPath;

		public Plugin(String pluginPath) {

			this.pluginPath = "cofh.thermalfoundation.plugins." + pluginPath;
		}

		public void preInit() {

			try {
				pluginClass = TFPlugins.class.getClassLoader().loadClass(pluginPath);
				pluginClass.getMethod("preInit", new Class[0]).invoke(null, new Object[0]);
			} catch (Throwable t) {
				if (CoFHProps.enableDebugOutput) {
					t.printStackTrace();
				}
			}
		}

		public void initialize() {

			try {
				if (pluginClass != null) {
					pluginClass.getMethod("initialize", new Class[0]).invoke(null, new Object[0]);
				}
			} catch (Throwable t) {
				if (CoFHProps.enableDebugOutput) {
					t.printStackTrace();
				}
			}
		}

		public void postInit() {

			try {
				if (pluginClass != null) {
					pluginClass.getMethod("postInit", new Class[0]).invoke(null, new Object[0]);
				}
			} catch (Throwable t) {
				if (CoFHProps.enableDebugOutput) {
					t.printStackTrace();
				}
			}
		}

		public void loadComplete() {

			try {
				if (pluginClass != null) {
					pluginClass.getMethod("loadComplete", new Class[0]).invoke(null, new Object[0]);
				}
			} catch (Throwable t) {
				if (CoFHProps.enableDebugOutput) {
					t.printStackTrace();
				}
			}
		}

		public void registerRenderInformation() {

			try {
				if (pluginClass != null) {
					pluginClass.getMethod("registerRenderInformation", new Class[0]).invoke(null, new Object[0]);
				}
			} catch (Throwable t) {
				if (CoFHProps.enableDebugOutput) {
					t.printStackTrace();
				}
			}
		}
	}

	public static ArrayList<Plugin> pluginList = new ArrayList<Plugin>();

	static {
		addPlugin("mfr.MFRPlugin", "MineFactoryReloaded");
		addPlugin("thaumcraft.ThaumcraftPlugin", "Thaumcraft");
		addPlugin("tconstruct.TConstructPlugin", "TConstruct");
		addPlugin("ee3.EE3Plugin", "EE3");
	}

	public static void preInit() {

		ThermalFoundation.log.info("Loading Plugins...");
		for (int i = 0; i < pluginList.size(); i++) {
			pluginList.get(i).preInit();
		}
		ThermalFoundation.log.info("Finished Loading Plugins.");
	}

	public static void initialize() {

		for (int i = 0; i < pluginList.size(); i++) {
			pluginList.get(i).initialize();
		}
	}

	public static void postInit() {

		for (int i = 0; i < pluginList.size(); i++) {
			pluginList.get(i).postInit();
		}
	}

	public static void loadComplete() {

		for (int i = 0; i < pluginList.size(); i++) {
			pluginList.get(i).loadComplete();
		}
	}

	public static void cleanUp() {

		pluginList.clear();
	}

	public static boolean addPlugin(String pluginPath, String pluginName) {

		boolean enable = ThermalFoundation.config.get("Plugins." + pluginName, "Enable", true);
		ThermalFoundation.config.save();

		if (enable) {
			pluginList.add(new Plugin(pluginPath));
			return true;
		}
		return false;
	}

}
