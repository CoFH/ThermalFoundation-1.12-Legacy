package cofh.thermalfoundation.entity.projectile;

import cofh.core.CoFHProps;
import cofh.core.util.CoreUtils;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.entity.monster.EntityBlizz;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBlizzBolt extends EntityThrowable {

	public static void initialize() {

		EntityRegistry.registerModEntity(EntityBlizzBolt.class, "blizz_bolt", CoreUtils.getEntityId(), ThermalFoundation.instance,
				CoFHProps.ENTITY_TRACKING_DISTANCE, 1, true);
	}

	public static DamageSource blizzDamage = new DamageSourceBlizz();
	public static PotionEffect blizzEffect = new PotionEffectBlizz(5 * 20, 2);
	public static double accelMultiplier = 0.2D;

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
	protected void onImpact(MovingObjectPosition mop) {

		if (ServerHelper.isServerWorld(worldObj)) {
			if (mop.entityHit != null) {
				if (mop.entityHit instanceof EntityBlizz) {
					mop.entityHit.attackEntityFrom(DamageSourceBlizz.causeDamage(this, getThrower()), 0);
				} else {
					if (mop.entityHit.attackEntityFrom(DamageSourceBlizz.causeDamage(this, getThrower()), mop.entityHit.isImmuneToFire() ? 8F : 5F)
							&& mop.entityHit instanceof EntityLivingBase) {
						EntityLivingBase living = (EntityLivingBase) mop.entityHit;
						living.addPotionEffect(new PotionEffect(EntityBlizzBolt.blizzEffect));
					}
				}
			} else {
				BlockPos pos = mop.getBlockPos();
				pos.offset(mop.sideHit.getOpposite());

				if (worldObj.isAirBlock(pos)) {
					Block block = worldObj.getBlockState(pos.add(0, -1, 0)).getBlock();

					if (block != null && block.isSideSolid(worldObj, pos.add(0, -1, 0), EnumFacing.UP)) {
						worldObj.setBlockState(pos, Blocks.snow_layer.getDefaultState(), 2);
					}
				}
			}
			for (int i = 0; i < 8; i++) {
				worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, posX, posY, posZ, this.rand.nextDouble(), this.rand.nextDouble(), this.rand.nextDouble(),
						new int[0]);
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

		public PotionEffectBlizz(int id, int duration, int amplifier) {

			super(id, duration, amplifier, true, false);
			getCurativeItems().clear();
		}

		public PotionEffectBlizz(int duration, int amplifier) {

			this(Potion.moveSlowdown.id, duration, amplifier);
		}

	}
}
