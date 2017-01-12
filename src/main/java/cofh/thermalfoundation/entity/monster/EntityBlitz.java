package cofh.thermalfoundation.entity.monster;

import cofh.core.CoFHProps;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.MathHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.entity.projectile.EntityBlitzBolt;
import cofh.thermalfoundation.init.ModSounds;
import cofh.thermalfoundation.item.TFItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityBlitz extends EntityMob {

    private static final DataParameter<Boolean> ATTACK_MODE = EntityDataManager.<Boolean>createKey(EntityBlizz.class, DataSerializers.BOOLEAN);

    static boolean enable = true;
    static boolean restrictLightLevel = true;

    static int spawnLightLevel = 8;

    static int spawnWeight = 10;
    static int spawnMin = 1;
    static int spawnMax = 4;

    static {
        String category = "Mob.Blitz";
        String comment = "";

        comment = "Set this to false to disable Blitzes entirely. Jerk.";
        enable = ThermalFoundation.config.get(category, "Enable", enable, comment).getBoolean(enable);

        category = "Mob.Blitz.Spawn";

        comment = "Set this to false for Blitzes to spawn at any light level.";
        restrictLightLevel = ThermalFoundation.config.get(category, "Light.Limit", restrictLightLevel, comment).getBoolean(restrictLightLevel);

        comment = "This sets the maximum light level Blitzes can spawn at, if restricted.";
        spawnLightLevel = MathHelper.clamp(ThermalFoundation.config.get(category, "Light.Level", spawnLightLevel, comment).getInt(spawnLightLevel), 0, 15);

        comment = "This sets the minimum number of Blitzes that spawn in a group.";
        spawnMin = MathHelper.clamp(ThermalFoundation.config.get(category, "MinGroupSize", spawnMin, comment).getInt(spawnMin), 1, 10);

        comment = "This sets the maximum light number of Blitzes that spawn in a group.";
        spawnMax = MathHelper.clamp(ThermalFoundation.config.get(category, "MaxGroupSize", spawnMax, comment).getInt(spawnMax), spawnMin, 24);

        comment = "This sets the relative spawn weight for Blitzes.";
        spawnWeight = ThermalFoundation.config.get(category, "SpawnWeight", spawnWeight, comment).getInt(spawnWeight);
    }

    public static void initialize(int id) {

        if (!enable) {
            return;
        }

        EntityRegistry.registerModEntity(EntityBlitz.class, "Blitz", id, ThermalFoundation.instance, CoFHProps.ENTITY_TRACKING_DISTANCE, 1, true, 0xF0F8FF, 0xFFEFD5);

        // Add Blitz spawn to Plains biomes
        List<Biome> validBiomes = new ArrayList<Biome>(Arrays.asList(BiomeDictionary.getBiomesForType(Type.PLAINS)));

        // Add Blitz spawn to Sandy biomes
        for (Biome biome : BiomeDictionary.getBiomesForType(Type.SANDY)) {
            if (!validBiomes.contains(biome)) {
                validBiomes.add(biome);
            }
        }
        // Remove Blitz spawn from End biomes
        for (Biome biome : BiomeDictionary.getBiomesForType(Type.END)) {
            if (validBiomes.contains(biome)) {
                validBiomes.remove(biome);
            }
        }
        EntityRegistry.addSpawn(EntityBlitz.class, spawnWeight, spawnMin, spawnMax, EnumCreatureType.MONSTER, validBiomes.toArray(new Biome[0]));
    }

    /**
     * Random offset used in floating behaviour
     */
    protected float heightOffset = 0.5F;

    /**
     * ticks until heightOffset is randomized
     */
    protected int heightOffsetUpdateTime;
    protected int firingState;

    protected static final int SOUND_AMBIENT_FREQUENCY = 400; // How often it does ambient sound loop

    public EntityBlitz(World world) {

        super(world);
        this.experienceValue = 10;
    }

    protected void initEntityAI() {
        this.tasks.addTask(4, new EntityBlitz.AIFireballAttack(this));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
    }

    @Override
    protected void entityInit() {

        super.entityInit();
        dataManager.register(ATTACK_MODE, false);
    }

    @Override
    protected SoundEvent getHurtSound() {
        return SoundEvents.ENTITY_BLAZE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BLAZE_DEATH;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par1) {

        return 0xF000F0;
    }

    @Override
    public float getBrightness(float par1) {

        return 2.0F;
    }

    @Override
    public void onLivingUpdate() {
        if (!this.onGround && this.motionY < 0.0D) {
            this.motionY *= 0.6D;
        }

        if (this.worldObj.isRemote) {
            if (this.rand.nextInt(24) == 0 && !this.isSilent()) {
                this.worldObj.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, ModSounds.BLIZZ_AMBIENT, this.getSoundCategory(), this.rand.nextFloat() * 0.2F + 0.1F, this.rand.nextFloat() * 0.3F + 0.4F, false);
            }

            for (int i = 0; i < 2; ++i) {
                this.worldObj.spawnParticle(EnumParticleTypes.CLOUD, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.0D, 0.0D, new int[0]);
            }
        }

        super.onLivingUpdate();
    }

    @Override
    protected void updateAITasks() {
        --this.heightOffsetUpdateTime;

        if (this.heightOffsetUpdateTime <= 0) {
            this.heightOffsetUpdateTime = 100;
            this.heightOffset = 0.5F + (float) this.rand.nextGaussian() * 3.0F;
        }

        EntityLivingBase entitylivingbase = this.getAttackTarget();

        if (entitylivingbase != null && entitylivingbase.posY + (double) entitylivingbase.getEyeHeight() > this.posY + (double) this.getEyeHeight() + (double) this.heightOffset) {
            this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
            this.isAirBorne = true;
        }

        super.updateAITasks();
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    protected void dropFewItems(boolean wasHitByPlayer, int looting) {

        if (wasHitByPlayer) {
            int items = this.rand.nextInt(2 + looting);
            for (int i = 0; i < items; i++) {
                this.entityDropItem(ItemHelper.cloneStack(TFItems.dustNiter, 1), 0);
            }
            items = this.rand.nextInt(2 + looting);
            for (int i = 0; i < items; i++) {
                this.entityDropItem(ItemHelper.cloneStack(TFItems.rodBlitz, 1), 0);
            }
        }
    }

    public boolean isInAttackMode() {
        return dataManager.get(ATTACK_MODE);
    }

    public void setInAttackMode(boolean mode) {
        dataManager.set(ATTACK_MODE, mode);
    }

    @Override
    protected boolean isValidLightLevel() {
        if (!restrictLightLevel) {
            return true;
        }

        int i = cofh.lib.util.helpers.MathHelper.floor(this.posX);
        int j = cofh.lib.util.helpers.MathHelper.floor(this.getEntityBoundingBox().minY);
        int k = cofh.lib.util.helpers.MathHelper.floor(this.posZ);
        BlockPos pos = new BlockPos(i, j, k);

        if (this.worldObj.getLightFor(EnumSkyBlock.SKY, pos) > this.rand.nextInt(32)) {
            return false;
        } else {
            int l = this.worldObj.getLight(pos);

            if (this.worldObj.isThundering()) {
                int i1 = this.worldObj.getSkylightSubtracted();
                this.worldObj.setSkylightSubtracted(10);
                l = this.worldObj.getLight(pos);
                this.worldObj.setSkylightSubtracted(i1);
            }
            return l <= this.rand.nextInt(spawnLightLevel);
        }
    }

    static class AIFireballAttack extends EntityAIBase
    {
        private final EntityBlitz blitz;
        private int attackStep;
        private int attackTime;

        public AIFireballAttack(EntityBlitz blitz)
        {
            this.blitz = blitz;
            this.setMutexBits(3);
        }

        public boolean shouldExecute()
        {
            EntityLivingBase entitylivingbase = this.blitz.getAttackTarget();
            return entitylivingbase != null && entitylivingbase.isEntityAlive();
        }

        public void startExecuting()
        {
            this.attackStep = 0;
        }

        public void resetTask()
        {

//			this.blitz.setOnFire(false);
        }

        public void updateTask()
        {
            --this.attackTime;
            EntityLivingBase entitylivingbase = this.blitz.getAttackTarget();
            double d0 = this.blitz.getDistanceSqToEntity(entitylivingbase);

            if (d0 < 4.0D)
            {
                if (this.attackTime <= 0)
                {
                    this.attackTime = 20;
                    this.blitz.attackEntityAsMob(entitylivingbase);
                }

                this.blitz.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
            }
            else if (d0 < 256.0D)
            {
                double d1 = entitylivingbase.posX - this.blitz.posX;
                double d2 = entitylivingbase.getEntityBoundingBox().minY + (double)(entitylivingbase.height / 2.0F) - (this.blitz.posY + (double)(this.blitz.height / 2.0F));
                double d3 = entitylivingbase.posZ - this.blitz.posZ;

                if (this.attackTime <= 0)
                {
                    ++this.attackStep;

                    if (this.attackStep == 1)
                    {
                        this.attackTime = 60;
//						this.blitz.setOnFire(true);
                    }
                    else if (this.attackStep <= 4)
                    {
                        this.attackTime = 6;
                    }
                    else
                    {
                        this.attackTime = 100;
                        this.attackStep = 0;
//						this.blitz.setOnFire(false);
                    }

                    if (this.attackStep > 1)
                    {
                        float f = net.minecraft.util.math.MathHelper.sqrt_float(net.minecraft.util.math.MathHelper.sqrt_double(d0)) * 0.5F;
                        this.blitz.worldObj.playEvent((EntityPlayer)null, 1018, new BlockPos((int)this.blitz.posX, (int)this.blitz.posY, (int)this.blitz.posZ), 0);

                        for (int i = 0; i < 1; ++i)
                        {
                            EntityBlitzBolt bolt = new EntityBlitzBolt(this.blitz.worldObj, this.blitz, d1 + this.blitz.getRNG().nextGaussian() * (double)f, d2, d3 + this.blitz.getRNG().nextGaussian() * (double)f);
                            bolt.posY = this.blitz.posY + (double)(this.blitz.height / 2.0F) + 0.5D;
                            bolt.posY = blitz.posY + blitz.height / 2.0F + 0.5D;
                            blitz.playSound(ModSounds.BLITZ_ATTACK, 2.0F, (blitz.rand.nextFloat() - blitz.rand.nextFloat()) * 0.2F + 1.0F);
                            this.blitz.worldObj.spawnEntityInWorld(bolt);
                        }
                    }
                }

                this.blitz.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
            }
            else
            {
                this.blitz.getNavigator().clearPathEntity();
                this.blitz.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
            }

            super.updateTask();
        }
    }

}
