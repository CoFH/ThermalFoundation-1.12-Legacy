package cofh.thermalfoundation.plugins.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.core.TFProps;
import cofh.thermalfoundation.item.Equipment;
import cofh.thermalfoundation.item.VanillaEquipment;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class NEIThermalFoundationConfig implements IConfigureNEI {

	/* IConfigureNEI */
	@Override
	public void loadConfig() {

		if (TFProps.showDisabledEquipment) {
			return;
		}
		for (Equipment e : Equipment.values()) {
			if (!e.enableArmor) {
				API.hideItem(new ItemStack(e.armorHelmet.getItem(), 1, OreDictionary.WILDCARD_VALUE));
				API.hideItem(new ItemStack(e.armorPlate.getItem(), 1, OreDictionary.WILDCARD_VALUE));
				API.hideItem(new ItemStack(e.armorLegs.getItem(), 1, OreDictionary.WILDCARD_VALUE));
				API.hideItem(new ItemStack(e.armorBoots.getItem(), 1, OreDictionary.WILDCARD_VALUE));
			}
			if (!e.enableTools[0]) {
				API.hideItem(new ItemStack(e.toolSword.getItem(), 1, OreDictionary.WILDCARD_VALUE));
			}
			if (!e.enableTools[1]) {
				API.hideItem(new ItemStack(e.toolShovel.getItem(), 1, OreDictionary.WILDCARD_VALUE));
			}
			if (!e.enableTools[2]) {
				API.hideItem(new ItemStack(e.toolPickaxe.getItem(), 1, OreDictionary.WILDCARD_VALUE));
			}
			if (!e.enableTools[3]) {
				API.hideItem(new ItemStack(e.toolAxe.getItem(), 1, OreDictionary.WILDCARD_VALUE));
			}
			if (!e.enableTools[4]) {
				API.hideItem(new ItemStack(e.toolHoe.getItem(), 1, OreDictionary.WILDCARD_VALUE));
			}
			if (!e.enableTools[5]) {
				API.hideItem(new ItemStack(e.toolShears.getItem(), 1, OreDictionary.WILDCARD_VALUE));
			}
			if (!e.enableTools[6]) {
				API.hideItem(new ItemStack(e.toolFishingRod.getItem(), 1, OreDictionary.WILDCARD_VALUE));
			}
			if (!e.enableTools[7]) {
				API.hideItem(new ItemStack(e.toolSickle.getItem(), 1, OreDictionary.WILDCARD_VALUE));
			}
			if (!e.enableTools[8]) {
				API.hideItem(new ItemStack(e.toolBow.getItem(), 1, OreDictionary.WILDCARD_VALUE));
			}
		}
		for (VanillaEquipment e : VanillaEquipment.values()) {
			if (!e.enableTools[0]) {
				API.hideItem(new ItemStack(e.toolShears.getItem(), 1, OreDictionary.WILDCARD_VALUE));
			}
			if (!e.enableTools[1]) {
				API.hideItem(new ItemStack(e.toolFishingRod.getItem(), 1, OreDictionary.WILDCARD_VALUE));
			}
			if (!e.enableTools[2]) {
				API.hideItem(new ItemStack(e.toolSickle.getItem(), 1, OreDictionary.WILDCARD_VALUE));
			}
			if (!e.enableTools[3]) {
				API.hideItem(new ItemStack(e.toolBow.getItem(), 1, OreDictionary.WILDCARD_VALUE));
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
