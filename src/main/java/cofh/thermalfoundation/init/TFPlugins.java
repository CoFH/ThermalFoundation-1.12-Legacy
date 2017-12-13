package cofh.thermalfoundation.init;

import cofh.core.util.core.IInitializer;
import cofh.thermalfoundation.plugins.PluginTConstruct;

import java.util.ArrayList;

public class TFPlugins {

	private TFPlugins() {

	}

	public static void preInit() {

		pluginTConstruct = new PluginTConstruct();

		initList.add(pluginTConstruct);

		for (IInitializer init : initList) {
			init.initialize();
		}
	}

	public static void postInit() {

		for (IInitializer init : initList) {
			init.register();
		}
	}

	private static ArrayList<IInitializer> initList = new ArrayList<>();

	/* REFERENCES */
	public static PluginTConstruct pluginTConstruct;

}
