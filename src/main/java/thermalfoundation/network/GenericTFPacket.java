package thermalfoundation.network;

import cofh.network.CoFHPacket;
import cofh.network.PacketHandler;

import net.minecraft.entity.player.EntityPlayer;

import thermalfoundation.ThermalFoundation;

public class GenericTFPacket extends CoFHPacket {

	public static void initialize() {

		PacketHandler.instance.registerPacket(GenericTFPacket.class);
	}

	public enum PacketTypes {

	}

	@Override
	public void handlePacket(EntityPlayer player, boolean isServer) {

		try {
			int type = getByte();

			switch (PacketTypes.values()[type]) {
			default:
				ThermalFoundation.log.error("Unknown Packet! Internal: TFPH, ID: " + type);
			}
		} catch (Exception e) {
			ThermalFoundation.log.error("Packet payload failure! Please check your config files!");
			e.printStackTrace();
		}
	}

	public static CoFHPacket getPacket(PacketTypes theType) {

		return new GenericTFPacket().addByte(theType.ordinal());
	}

}
