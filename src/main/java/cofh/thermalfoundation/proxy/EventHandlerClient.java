package cofh.thermalfoundation.proxy;

import cofh.thermalfoundation.init.TFTextures;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerClient {

	public static final EventHandlerClient INSTANCE = new EventHandlerClient();

	@SubscribeEvent
	public void handleTextureStitchPreEvent(TextureStitchEvent.Pre event) {

		TFTextures.registerTextures(event.getMap());
	}

}
