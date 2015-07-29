package cofh.thermalfoundation.entity.projectile;

import cofh.core.CoFHProps;
import cofh.core.util.CoreUtils;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.entity.monster.EntityBlitz;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBlazeBolt extends EntityThrowable {

	public static void initialize() {

		EntityRegistry.registerModEntity(EntityBlazeBolt.class, "blazeBolt", CoreUtils.getEntityId(), ThermalFoundation.instance,
				CoFHProps.ENTITY_TRACKING_DISTANCE, 1, true);
	}

	protected static class DamageSourceBlaze extends EntityDamageSource {

		public DamageSourceBlaze() {

			this(null);
		}

		public DamageSourceBlaze(Entity source) {

			super("blaze", source);
		}

		public static DamageSource causeDamage(EntityBlazeBolt entityProj, Entity entitySource) {

			return (new EntityDamageSourceIndirect("blaze", entityProj, entitySource == null ? entityProj : entitySource)).setProjectile();
		}
	}

	public static DamageSource blazeDamage = new DamageSourceBlaze();

	/* Required Constructor */
	public EntityBlazeBolt(World world) {

		super(world);
	}

	public EntityBlazeBolt(World world, EntityLivingBase thrower) {

		super(world, thrower);
	}

	public EntityBlazeBolt(World world, double x, double y, double z) {

		super(world, x, y, z);
	}

	@Override
	protected float getGravityVelocity() {

		return 0.005F;
	}

	@Override
	protected void onImpact(MovingObjectPosition pos) {

		if (ServerHelper.isServerWorld(worldObj)) {
			if (pos.entityHit != null) {
				if (pos.entityHit instanceof EntityBlitz) {
					pos.entityHit.attackEntityFrom(DamageSourceBlaze.causeDamage(this, getThrower()), 0);
				} else {
					if (pos.entityHit.attackEntityFrom(DamageSourceBlaze.causeDamage(this, getThrower()), 5F) && pos.entityHit instanceof EntityLivingBase) {
						EntityLivingBase living = (EntityLivingBase) pos.entityHit;
						living.setFire(5);
					}
				}
			}
			for (int i = 0; i < 8; i++) {
				worldObj.spawnParticle("cloud", posX, posY, posZ, this.rand.nextDouble(), this.rand.nextDouble(), this.rand.nextDouble());
			}
			setDead();
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float f) {

		return 0xF000F0;
	}

}
