package cofh.thermalfoundation.entity.projectile;

import codechicken.lib.util.CommonUtils;
import codechicken.lib.util.EntityUtils;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.entity.monster.EntityBlitz;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBlazeBolt extends EntityFireball {

    public static void initialize() {
        EntityRegistry.registerModEntity(EntityBlazeBolt.class, "blazeBolt", EntityUtils.nextEntityId(), ThermalFoundation.instance, 64, 1, true);
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

    public EntityBlazeBolt(World worldIn)
    {
        super(worldIn);
        this.setSize(0.3125F, 0.3125F);
    }

    public EntityBlazeBolt(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ)
    {
        super(worldIn, shooter, accelX, accelY, accelZ);
        this.setSize(0.3125F, 0.3125F);
    }

    public EntityBlazeBolt(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ)
    {
        super(worldIn, x, y, z, accelX, accelY, accelZ);
        this.setSize(0.3125F, 0.3125F);
    }

    @Override
    protected void onImpact(RayTraceResult pos) {

        if (CommonUtils.isServerWorld(worldObj)) {
            if (pos.entityHit != null) {
                if (pos.entityHit instanceof EntityBlitz) {
                    pos.entityHit.attackEntityFrom(DamageSourceBlaze.causeDamage(this, shootingEntity), 0);
                } else {
                    if (pos.entityHit.attackEntityFrom(DamageSourceBlaze.causeDamage(this, shootingEntity), 5F) && pos.entityHit instanceof EntityLivingBase) {
                        EntityLivingBase living = (EntityLivingBase) pos.entityHit;
                        living.setFire(5);
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
