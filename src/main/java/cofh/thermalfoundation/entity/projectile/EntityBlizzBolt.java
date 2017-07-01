package cofh.thermalfoundation.entity.projectile;

import cofh.core.init.CoreProps;
import cofh.core.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.entity.monster.EntityBlizz;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBlizzBolt extends EntityThrowable {

	public static PotionEffect blizzEffect = new PotionEffectBlizz(5 * 20, 2);

	public static void initialize(int id) {

		EntityRegistry.registerModEntity(new ResourceLocation("thermalfoundation:blizz_bolt"), EntityBlizzBolt.class, "thermalfoundation.blizz_bolt", id, ThermalFoundation.instance, CoreProps.ENTITY_TRACKING_DISTANCE, 1, true);
	}

	/* REQUIRED CONSTRUCTOR */
	public EntityBlizzBolt(World world) {

		super(world);
	}

	public EntityBlizzBolt(World world, EntityLivingBase thrower) {

		super(world, thrower);
	}

	public EntityBlizzBolt(World world, double x, double y, double z) {

		super(world, x, y, z);
	}

	@Override
	protected float getGravityVelocity() {

		return 0.005F;
	}

	@Override
	protected void onImpact(RayTraceResult traceResult) {

		if (ServerHelper.isServerWorld(world)) {
			if (traceResult.entityHit != null) {
				if (traceResult.entityHit instanceof EntityBlizz) {
					traceResult.entityHit.attackEntityFrom(DamageSourceBlizz.causeDamage(this, getThrower()), 0);
				} else {
					if (traceResult.entityHit.attackEntityFrom(DamageSourceBlizz.causeDamage(this, getThrower()), traceResult.entityHit.isImmuneToFire() ? 8F : 5F) && traceResult.entityHit instanceof EntityLivingBase) {
						EntityLivingBase living = (EntityLivingBase) traceResult.entityHit;

						if (EntityBlizz.effect) {
							living.addPotionEffect(new PotionEffect(EntityBlizzBolt.blizzEffect));
						}
					}
				}
			} else {
				BlockPos hitPosOffset = traceResult.getBlockPos().offset(traceResult.sideHit);

				if (world.isAirBlock(hitPosOffset)) {
					IBlockState state = world.getBlockState(hitPosOffset.offset(EnumFacing.DOWN));

					if (state.isSideSolid(world, hitPosOffset.offset(EnumFacing.DOWN), EnumFacing.UP)) {
						world.setBlockState(hitPosOffset, Blocks.SNOW_LAYER.getDefaultState());
					}
				}
			}
			for (int i = 0; i < 8; i++) {
				world.spawnParticle(EnumParticleTypes.SNOWBALL, posX, posY, posZ, this.rand.nextDouble(), this.rand.nextDouble(), this.rand.nextDouble());
			}
			setDead();
		}
	}

	@Override
	@SideOnly (Side.CLIENT)
	public int getBrightnessForRender() {

		return 0xF000F0;
	}

	/* DAMAGE SOURCE */
	protected static class DamageSourceBlizz extends EntityDamageSource {

		public DamageSourceBlizz() {

			this(null);
		}

		public DamageSourceBlizz(Entity source) {

			super("blizz", source);
		}

		public static DamageSource causeDamage(EntityBlizzBolt entityProj, Entity entitySource) {

			return (new EntityDamageSourceIndirect("blizz", entityProj, entitySource == null ? entityProj : entitySource)).setProjectile();
		}
	}

	protected static class PotionEffectBlizz extends PotionEffect {

		public PotionEffectBlizz(Potion potion, int duration, int amplifier, boolean isAmbient) {

			super(potion, duration, amplifier, isAmbient, true);
			getCurativeItems().clear();
		}

		public PotionEffectBlizz(int duration, int amplifier) {

			this(MobEffects.SLOWNESS, duration, amplifier, false);
		}

	}
}
