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
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBlitzBolt extends EntityThrowable {

	public static void initialize() {

		EntityRegistry.registerModEntity(EntityBlitzBolt.class, "blitzBolt", CoreUtils.getEntityId(), ThermalFoundation.instance,
				CoFHProps.ENTITY_TRACKING_DISTANCE, 1, true);
	}

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

		public PotionEffectBlitz(int id, int duration, int amplifier, boolean isAmbient) {

			super(id, duration, amplifier, isAmbient);
			getCurativeItems().clear();
		}

		public PotionEffectBlitz(int duration, int amplifier) {

			this(Potion.confusion.id, duration, amplifier, false);
		}

	}

	public static DamageSource blitzDamage = new DamageSourceBlitz();
	public static PotionEffect blitzEffect = new PotionEffectBlitz(5 * 20, 2);

	/* Required Constructor */
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
	protected void onImpact(MovingObjectPosition pos) {

		if (ServerHelper.isServerWorld(worldObj)) {
			if (pos.entityHit != null) {
				if (pos.entityHit instanceof EntityBlitz) {
					pos.entityHit.attackEntityFrom(DamageSourceBlitz.causeDamage(this, getThrower()), 0);
				} else {
					if (pos.entityHit.attackEntityFrom(DamageSourceBlitz.causeDamage(this, getThrower()), 5F) && pos.entityHit instanceof EntityLivingBase) {
						EntityLivingBase living = (EntityLivingBase) pos.entityHit;
						living.addPotionEffect(new PotionEffect(EntityBlitzBolt.blitzEffect));
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
