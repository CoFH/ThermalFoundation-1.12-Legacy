package cofh.thermalfoundation.plugins.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.item.Equipment;
import cofh.thermalfoundation.item.TFItems;

import net.minecraft.item.ItemStack;

public class NEIThermalFoundationConfig implements IConfigureNEI {

	/* IConfigureNEI */
	@Override
	public void loadConfig() {

		// TODO: Temp until Lexicon is finished
		API.hideItem(new ItemStack(TFItems.itemLexicon));

		if (ThermalFoundation.showDisabledEquipment) {
			return;
		}
		for (Equipment e : Equipment.values()) {
			if (!e.enableArmor) {
				API.hideItem(e.armorHelmet);
				API.hideItem(e.armorPlate);
				API.hideItem(e.armorLegs);
				API.hideItem(e.armorBoots);
			}
			if (!e.enableTools[0]) {
				API.hideItem(e.toolSword);
			}
			if (!e.enableTools[1]) {
				API.hideItem(e.toolShovel);
			}
			if (!e.enableTools[2]) {
				API.hideItem(e.toolPickaxe);
			}
			if (!e.enableTools[3]) {
				API.hideItem(e.toolAxe);
			}
			if (!e.enableTools[4]) {
				API.hideItem(e.toolHoe);
			}
			if (!e.enableTools[5]) {
				API.hideItem(e.toolShears);
			}
			if (!e.enableTools[6]) {
				API.hideItem(e.toolFishingRod);
			}
			if (!e.enableTools[7]) {
				API.hideItem(e.toolSickle);
			}
			if (!e.enableTools[8]) {
				API.hideItem(e.toolBow);
			}
		}
	}

	@Override
	public String getName() {

		return ThermalFoundation.modName;
	}

	@Override
	public String getVersion() {

		return ThermalFoundation.version;
	}

}
