package cofh.thermalfoundation.entity.projectile;

import cofh.core.CoFHProps;
import cofh.core.util.CoreUtils;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.entity.monster.EntityBasalz;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBasalzBolt extends EntityThrowable {

	public static void initialize() {

		EntityRegistry.registerModEntity(EntityBasalzBolt.class, "basalzBolt", CoreUtils.getEntityId(), ThermalFoundation.instance,
				CoFHProps.ENTITY_TRACKING_DISTANCE, 1, true);
	}

	public static DamageSource basalzDamage = new DamageSourceBasalz();
	public static PotionEffect basalzEffect = new PotionEffectBasalz(5 * 20, 2);

	/* REQUIRED CONSTRUCTOR */
	public EntityBasalzBolt(World world) {

		super(world);
	}

	public EntityBasalzBolt(World world, EntityLivingBase thrower) {

		super(world, thrower);
	}

	public EntityBasalzBolt(World world, double x, double y, double z) {

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
				if (pos.entityHit instanceof EntityBasalz) {
					pos.entityHit.attackEntityFrom(DamageSourceBasalz.causeDamage(this, getThrower()), 0);
				} else {
					if (pos.entityHit.attackEntityFrom(DamageSourceBasalz.causeDamage(this, getThrower()), 5F) && pos.entityHit instanceof EntityLivingBase) {
						EntityLivingBase living = (EntityLivingBase) pos.entityHit;
						living.addPotionEffect(new PotionEffect(EntityBasalzBolt.basalzEffect));
					}
				}
			}
			for (int i = 0; i < 8; i++) {
				worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, posX, posY, posZ, this.rand.nextDouble(), this.rand.nextDouble(),
						this.rand.nextDouble(), new int[0]);
			}
			setDead();
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float f) {

		return 0xF000F0;
	}

	/* DAMAGE SOURCE */
	protected static class DamageSourceBasalz extends EntityDamageSource {

		public DamageSourceBasalz() {

			this(null);
		}

		public DamageSourceBasalz(Entity source) {

			super("basalz", source);
		}

		public static DamageSource causeDamage(EntityBasalzBolt entityProj, Entity entitySource) {

			return (new EntityDamageSourceIndirect("basalz", entityProj, entitySource == null ? entityProj : entitySource)).setProjectile();
		}
	}

	protected static class PotionEffectBasalz extends PotionEffect {

		public PotionEffectBasalz(int id, int duration, int amplifier) {

			super(id, duration, amplifier, true, false);
			getCurativeItems().clear();
		}

		public PotionEffectBasalz(int duration, int amplifier) {

			this(Potion.weakness.id, duration, amplifier);
		}

	}

}
