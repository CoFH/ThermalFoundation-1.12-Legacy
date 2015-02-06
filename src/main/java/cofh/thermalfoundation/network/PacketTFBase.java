package cofh.thermalfoundation.network;

import cofh.core.network.PacketCoFHBase;
import cofh.core.network.PacketHandler;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.entity.player.EntityPlayer;

public class PacketTFBase extends PacketCoFHBase {

	public static void initialize() {

		PacketHandler.instance.registerPacket(PacketTFBase.class);
	}

	public enum PacketTypes {
		LEXICON_PREV_ORE, LEXICON_NEXT_ORE, LEXICON_PREV_NAME, LEXICON_NEXT_NAME
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
