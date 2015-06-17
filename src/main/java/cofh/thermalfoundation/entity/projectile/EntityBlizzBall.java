package cofh.thermalfoundation.entity.projectile;

import cofh.core.CoFHProps;
import cofh.core.util.CoreUtils;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cpw.mods.fml.common.registry.EntityRegistry;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityBlizzBall extends EntityThrowable {

	public static void initialize() {

		EntityRegistry.registerModEntity(EntityBlizzBall.class, "blizzBall", CoreUtils.getEntityId(), ThermalFoundation.instance,
				CoFHProps.ENTITY_TRACKING_DISTANCE, 1, true);
	}

	/* Required Constructor */
	public EntityBlizzBall(World world) {

		super(world);
	}

	public EntityBlizzBall(World world, EntityLivingBase thrower) {

		super(world, thrower);
	}

	public EntityBlizzBall(World world, double x, double y, double z) {

		super(world, x, y, z);
	}

	@Override
	protected void onImpact(MovingObjectPosition pos) {

		if (ServerHelper.isServerWorld(worldObj)) {
			if (pos.entityHit != null) {
				pos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), pos.entityHit instanceof EntityBlaze ? 3 : 0);
			} else {
				ForgeDirection dir = ForgeDirection.getOrientation(pos.sideHit);
				int x = pos.blockX + dir.offsetX;
				int y = pos.blockY + dir.offsetY;
				int z = pos.blockZ + dir.offsetZ;

				if (worldObj.isAirBlock(x, y, z)) {
					Block block = worldObj.getBlock(x, y - 1, z);

					if (block != null && block.isSideSolid(worldObj, x, y - 1, z, ForgeDirection.UP)) {
						worldObj.setBlock(x, y, z, Blocks.snow_layer);
					}
				}
			}
			for (int i = 0; i < 8; i++) {
				worldObj.spawnParticle("snowballpoof", posX, posY, posZ, this.rand.nextDouble(), this.rand.nextDouble(), this.rand.nextDouble());
			}
			setDead();
		}
	}

}
