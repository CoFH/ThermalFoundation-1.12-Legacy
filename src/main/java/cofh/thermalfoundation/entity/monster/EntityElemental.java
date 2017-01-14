package cofh.thermalfoundation.entity.monster;

import cofh.lib.util.helpers.ServerHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityElemental extends EntityMob {

	private static final DataParameter<Boolean> ATTACK_MODE = EntityDataManager.<Boolean>createKey(EntityBlizz.class, DataSerializers.BOOLEAN);

	protected static final int SOUND_AMBIENT_FREQUENCY = 400; // How often it does ambient sound loop

	protected float heightOffset = 0.5F;
	protected int heightOffsetUpdateTime;

	protected EnumParticleTypes ambientParticle;

	public EntityElemental(World world) {

		super(world);
		this.experienceValue = 10;
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
	protected void updateAITasks() {

		--this.heightOffsetUpdateTime;

		if (this.heightOffsetUpdateTime <= 0) {
			this.heightOffsetUpdateTime = 100;
			this.heightOffset = 0.5F + (float) this.rand.nextGaussian() * 3.0F;
		}
		EntityLivingBase target = this.getAttackTarget();

		if (target != null) {
			if ((target.posY + target.getEyeHeight()) > (this.posY + this.getEyeHeight() + this.heightOffset)) {
				this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
			}
		}
		super.updateAITasks();
	}

	@Override
	protected boolean isValidLightLevel() {

		if (!restrictLightLevel()) {
			return true;
		}
		BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);

		if (this.worldObj.getLightFor(EnumSkyBlock.SKY, blockpos) > this.rand.nextInt(32)) {
			return false;
		} else {
			int i = this.worldObj.getLightFromNeighbors(blockpos);

			if (this.worldObj.isThundering()) {
				int j = this.worldObj.getSkylightSubtracted();
				this.worldObj.setSkylightSubtracted(10);
				i = this.worldObj.getLightFromNeighbors(blockpos);
				this.worldObj.setSkylightSubtracted(j);
			}
			return i <= this.rand.nextInt(getSpawnLightLevel());
		}
	}

	@Override
	protected SoundEvent getHurtSound() {

		return SoundEvents.ENTITY_BLAZE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {

		return SoundEvents.ENTITY_BLAZE_DEATH;
	}

	protected abstract boolean restrictLightLevel();

	protected abstract int getSpawnLightLevel();

	@Override
	public void fall(float distance, float damageMultiplier) {

	}

	@Override
	public void onLivingUpdate() {

		if (!this.onGround && this.motionY < 0.0D) {
			this.motionY *= 0.6D;
		}
		if (ServerHelper.isClientWorld(worldObj)) {
			//            if (this.rand.nextInt(SOUND_AMBIENT_FREQUENCY) == 0) {
			//                this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, soundAmbient, this.rand.nextFloat() * 0.2F + 0.1F,
			//                        this.rand.nextFloat() * 0.3F + 0.4F);
			//            }
			for (int i = 0; i < 2; i++) {
				this.worldObj.spawnParticle(ambientParticle, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
		super.onLivingUpdate();
	}

	public void setInAttackMode(boolean mode) {

		dataManager.set(ATTACK_MODE, mode);
	}

	@Override
	@SideOnly (Side.CLIENT)
	public int getBrightnessForRender(float partialTicks) {

		return 0xF000F0;
	}

	@Override
	public float getBrightness(float partialTicks) {

		return 1.0F;
	}

}
