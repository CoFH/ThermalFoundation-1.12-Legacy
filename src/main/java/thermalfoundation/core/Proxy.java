package thermalfoundation.core;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerIcons(TextureStitchEvent.Pre event) {

	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void initializeIcons(TextureStitchEvent.Post event) {

	}

}
