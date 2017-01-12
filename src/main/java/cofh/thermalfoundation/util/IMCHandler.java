package cofh.thermalfoundation.util;

import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCMessage;

import java.util.List;

public class IMCHandler {

	public static IMCHandler instance = new IMCHandler();

	public void handleIMC(List<IMCMessage> messages) {

		NBTTagCompound theNBT;
		for (IMCMessage theMessage : messages) {
			try {
				if (theMessage.isNBTMessage()) {
					theNBT = theMessage.getNBTValue();

					if (theMessage.key.equalsIgnoreCase("AddLexiconBlacklistEntry")) {
						LexiconManager.addBlacklistEntry(ItemStack.loadItemStackFromNBT(theNBT.getCompoundTag("entry")));
						continue;
					} else if (theMessage.key.equalsIgnoreCase("RemoveLexiconBlacklistEntry")) {
						LexiconManager.removeBlacklistEntry(ItemStack.loadItemStackFromNBT(theNBT.getCompoundTag("entry")));
						continue;
					}
					ThermalFoundation.LOG.warn("Thermal Foundation received an invalid IMC from " + theMessage.getSender() + "! Key was " + theMessage.key);
				}
			} catch (Exception e) {
				ThermalFoundation.LOG.warn("Thermal Foundation received a broken IMC from " + theMessage.getSender() + "!");
				e.printStackTrace();
			}
		}
	}

}
