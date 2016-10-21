package cofh.thermalfoundation.gui;

import cofh.thermalfoundation.ThermalFoundation;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;

public class GuiConfigTF extends GuiConfig {

	public GuiConfigTF(GuiScreen parentScreen) {

		super(parentScreen, getConfigElements(parentScreen), ThermalFoundation.modId, false, false, ThermalFoundation.modName);
	}

	public static final String[] CATEGORIES = { "Equipment", "Fluid", "Lexicon" };

	@SuppressWarnings("rawtypes")
	private static List<IConfigElement> getConfigElements(GuiScreen parent) {

		List<IConfigElement> list = new ArrayList<IConfigElement>();

		for (int i = 0; i < CATEGORIES.length; i++) {
			list.add(new ConfigElement<ConfigCategory>(ThermalFoundation.config.getCategory(CATEGORIES[i])));
		}
		return list;
	}

}
