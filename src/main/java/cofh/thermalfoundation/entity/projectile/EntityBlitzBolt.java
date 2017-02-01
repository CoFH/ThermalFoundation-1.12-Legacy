package cofh.thermalfoundation.entity.projectile;

import cofh.core.init.CoreProps;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.entity.monster.EntityBlitz;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBlitzBolt extends EntityThrowable {

	public static PotionEffect blitzEffect = new PotionEffectBlitz(5 * 20, 2);

	public static void initialize(int id) {

		EntityRegistry.registerModEntity(EntityBlitzBolt.class, "blitz_bolt", id, ThermalFoundation.instance, CoreProps.ENTITY_TRACKING_DISTANCE, 1, true);
	}

	/* REQUIRED CONSTRUCTOR */
	public EntityBlitzBolt(World world) {

		super(world);
	}

	public EntityBlitzBolt(World world, EntityLivingBase thrower) {

		super(world, thrower);
	}

	public EntityBlitzBolt(World world, double x, double y, double z) {

		super(world, x, y, z);
	}

	@Override
	protected float getGravityVelocity() {

		return 0.005F;
	}

	@Override
	protected void onImpact(RayTraceResult traceResult) {

		if (ServerHelper.isServerWorld(worldObj)) {
			if (traceResult.entityHit != null) {
				if (traceResult.entityHit instanceof EntityBlitz) {
					traceResult.entityHit.attackEntityFrom(DamageSourceBlitz.causeDamage(this, getThrower()), 0);
				} else {
					if (traceResult.entityHit.attackEntityFrom(DamageSourceBlitz.causeDamage(this, getThrower()), 5F) && traceResult.entityHit instanceof EntityLivingBase) {
						EntityLivingBase living = (EntityLivingBase) traceResult.entityHit;
						living.addPotionEffect(new PotionEffect(EntityBlitzBolt.blitzEffect));
					}
				}
			}
			for (int i = 0; i < 8; i++) {
				worldObj.spawnParticle(EnumParticleTypes.CLOUD, posX, posY, posZ, this.rand.nextDouble(), this.rand.nextDouble(), this.rand.nextDouble(), new int[0]);
			}
			setDead();
		}
	}

	@Override
	@SideOnly (Side.CLIENT)
	public int getBrightnessForRender(float f) {

		return 0xF000F0;
	}

	/* DAMAGE SOURCE */
	protected static class DamageSourceBlitz extends EntityDamageSource {

		public DamageSourceBlitz() {

			this(null);
		}

		public DamageSourceBlitz(Entity source) {

			super("blitz", source);
		}

		public static DamageSource causeDamage(EntityBlitzBolt entityProj, Entity entitySource) {

			return (new EntityDamageSourceIndirect("blitz", entityProj, entitySource == null ? entityProj : entitySource)).setProjectile();
		}
	}

	protected static class PotionEffectBlitz extends PotionEffect {

		public PotionEffectBlitz(Potion potion, int duration, int amplifier, boolean isAmbient) {

			super(potion, duration, amplifier, isAmbient, true);
			getCurativeItems().clear();
		}

		public PotionEffectBlitz(int duration, int amplifier) {

			this(MobEffects.NAUSEA, duration, amplifier, false);
		}

	}

}
