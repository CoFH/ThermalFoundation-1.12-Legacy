package thermalfoundation.network;

import cofh.network.PacketCoFHBase;
import cofh.network.PacketHandler;

import net.minecraft.entity.player.EntityPlayer;

import thermalfoundation.ThermalFoundation;

public class PacketTFBase extends PacketCoFHBase {

	public static void initialize() {

		PacketHandler.instance.registerPacket(PacketTFBase.class);
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

	public static PacketCoFHBase getPacket(PacketTypes theType) {

		return new PacketTFBase().addByte(theType.ordinal());
	}

}
