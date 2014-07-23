package thermalfoundation.core;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraftforge.client.event.TextureStitchEvent;

import thermalfoundation.entity.monster.EntityBlizz;
import thermalfoundation.entity.projectile.EntityBlizzBall;
import thermalfoundation.entity.projectile.EntityBlizzSlowball;

public class Proxy {

	public void registerEntities() {

		EntityBlizz.initialize();
		EntityBlizzBall.initialize();
		EntityBlizzSlowball.initialize();
	}

	public void registerRenderInformation() {

	}

	@SubscribeEvent
	public void registerIcons(TextureStitchEvent.Pre event) {

	}

	@SubscribeEvent
	public void initializeIcons(TextureStitchEvent.Post event) {

	}

}
