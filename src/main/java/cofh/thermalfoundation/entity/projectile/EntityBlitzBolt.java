package cofh.thermalfoundation.entity.projectile;

import codechicken.lib.util.CommonUtils;
import cofh.thermalfoundation.entity.monster.EntityBlitz;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBlitzBolt extends EntityFireball {

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

    public static DamageSource blitzDamage = new DamageSourceBlitz();
    public static PotionEffect blitzEffect = new PotionEffectBlitz(5 * 20, 2);

    public EntityBlitzBolt(World worldIn)
    {
        super(worldIn);
        this.setSize(0.3125F, 0.3125F);
    }

    public EntityBlitzBolt(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ)
    {
        super(worldIn, shooter, accelX, accelY, accelZ);
        this.setSize(0.3125F, 0.3125F);
    }

    public EntityBlitzBolt(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ)
    {
        super(worldIn, x, y, z, accelX, accelY, accelZ);
        this.setSize(0.3125F, 0.3125F);
    }

    @Override
    protected void onImpact(RayTraceResult pos) {
        if (CommonUtils.isServerWorld(worldObj)) {
            if (pos.entityHit != null) {
                if (pos.entityHit instanceof EntityBlitz) {
                    pos.entityHit.attackEntityFrom(DamageSourceBlitz.causeDamage(this, shootingEntity), 0);
                } else {
                    if (pos.entityHit.attackEntityFrom(DamageSourceBlitz.causeDamage(this, shootingEntity), 5F) && pos.entityHit instanceof EntityLivingBase) {
                        EntityLivingBase living = (EntityLivingBase) pos.entityHit;
                        living.addPotionEffect(new PotionEffect(EntityBlitzBolt.blitzEffect));
                    }
                }
            }
            for (int i = 0; i < 8; i++) {
                worldObj.spawnParticle(EnumParticleTypes.CLOUD, posX, posY, posZ, this.rand.nextDouble(), this.rand.nextDouble(), this.rand.nextDouble());
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
