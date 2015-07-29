package cofh.thermalfoundation.util;

import cofh.thermalfoundation.ThermalFoundation;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

public class IMCHandler {

	public static IMCHandler instance = new IMCHandler();

	public void handleIMC(List<IMCMessage> messages) {

		NBTTagCompound theNBT;
		for (IMCMessage theMessage : messages) {
			try {
				if (theMessage.isNBTMessage()) {
					theNBT = theMessage.getNBTValue();

					// if (theMessage.key.equalsIgnoreCase("FurnaceRecipe")) {
					// FurnaceManager.addRecipe(theNBT.getInteger("energy"), ItemStack.loadItemStackFromNBT(theNBT.getCompoundTag("input")), ItemStack
					// .loadItemStackFromNBT(theNBT.getCompoundTag("output")), theNBT.hasKey("overwrite") ? theNBT.getBoolean("overwrite") : false);
					// continue;
					// }
					//
					// else if (theMessage.key.equalsIgnoreCase("RemoveFurnaceRecipe")) {
					// FurnaceManager.removeRecipe(ItemStack.loadItemStackFromNBT(theNBT.getCompoundTag("input")));
					// continue;
					// }
					ThermalFoundation.log.warn("Thermal Foundation received an invalid IMC from " + theMessage.getSender() + "! Key was " + theMessage.key);
				}
			} catch (Exception e) {
				ThermalFoundation.log.warn("Thermal Foundation received a broken IMC from " + theMessage.getSender() + "!");
				e.printStackTrace();
			}
		}
	}

}
