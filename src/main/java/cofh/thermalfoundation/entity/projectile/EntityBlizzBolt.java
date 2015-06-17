package cofh.thermalfoundation.entity.projectile;

import cofh.core.CoFHProps;
import cofh.core.util.CoreUtils;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.entity.monster.EntityBlizz;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityBlizzBolt extends EntityThrowable {

	public static void initialize() {

		EntityRegistry.registerModEntity(EntityBlizzBolt.class, "blizzBolt", CoreUtils.getEntityId(), ThermalFoundation.instance,
				CoFHProps.ENTITY_TRACKING_DISTANCE, 1, true);
	}

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

		public PotionEffectBlizz(int id, int duration, int amplifier, boolean isAmbient) {

			super(id, duration, amplifier, isAmbient);
			getCurativeItems().clear();
		}

		public PotionEffectBlizz(int duration, int amplifier) {

			this(Potion.moveSlowdown.id, duration, amplifier, false);
		}

	}

	public static DamageSource blizzDamage = new DamageSourceBlizz();
	public static PotionEffect blizzEffect = new PotionEffectBlizz(5 * 20, 2);
	public static double accelMultiplier = 0.2D;

	/* Required Constructor */
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
	protected void onImpact(MovingObjectPosition pos) {

		if (ServerHelper.isServerWorld(worldObj)) {
			if (pos.entityHit != null) {
				if (pos.entityHit instanceof EntityBlizz) {
					pos.entityHit.attackEntityFrom(DamageSourceBlizz.causeDamage(this, getThrower()), 0);
				} else {
					if (pos.entityHit.attackEntityFrom(DamageSourceBlizz.causeDamage(this, getThrower()), pos.entityHit.isImmuneToFire() ? 8F : 5F)
							&& pos.entityHit instanceof EntityLivingBase) {
						EntityLivingBase living = (EntityLivingBase) pos.entityHit;
						living.addPotionEffect(new PotionEffect(EntityBlizzBolt.blizzEffect));
					}
				}
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

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float f) {

		return 0xF000F0;
	}

}
