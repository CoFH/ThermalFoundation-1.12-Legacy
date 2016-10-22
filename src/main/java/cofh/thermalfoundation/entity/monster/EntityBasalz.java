package cofh.thermalfoundation.entity.monster;

import codechicken.lib.math.MathHelper;
import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.entity.projectile.EntityBasalzBolt;
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

public class EntityBasalz extends EntityMob {
	private static final DataParameter<Boolean> ATTACK_MODE = EntityDataManager.<Boolean>createKey(EntityBlizz.class, DataSerializers.BOOLEAN);

	static boolean enable = true;
	static boolean restrictLightLevel = true;

	static int spawnLightLevel = 8;

	static int spawnWeight = 10;
	static int spawnMin = 1;
	static int spawnMax = 4;

	static {
		String category = "Mob.Basalz";
		String comment = "";

		comment = "Set this to false to disable Basalzes entirely. Jerk.";
		enable = ThermalFoundation.config.get(category, "Enable", enable, comment).getBoolean(enable);

		category = "Mob.Basalz.Spawn";

		comment = "Set this to false for Basalzes to spawn at any light level.";
		restrictLightLevel = ThermalFoundation.config.get(category, "Light.Limit", restrictLightLevel, comment).getBoolean(restrictLightLevel);

		comment = "This sets the maximum light level Basalzes can spawn at, if restricted.";
		spawnLightLevel = MathHelper.clip(ThermalFoundation.config.get(category, "Light.Level", spawnLightLevel, comment).getInt(spawnLightLevel), 0, 15);

		comment = "This sets the minimum number of Basalzes that spawn in a group.";
		spawnMin = MathHelper.clip(ThermalFoundation.config.get(category, "MinGroupSize", spawnMin, comment).getInt(spawnMin), 1, 10);

		comment = "This sets the maximum light number of Basalzes that spawn in a group.";
		spawnMax = MathHelper.clip(ThermalFoundation.config.get(category, "MaxGroupSize", spawnMax, comment).getInt(spawnMax), spawnMin, 24);

		comment = "This sets the relative spawn weight for Basalzes.";
		spawnWeight = ThermalFoundation.config.get(category, "SpawnWeight", spawnWeight, comment).getInt(spawnWeight);
	}

	public static void initialize(int id) {

		if (!enable) {
			return;
		}

		EntityRegistry.registerModEntity(EntityBasalz.class, "Basalz", id, ThermalFoundation.instance, 64, 1, true, 0x606060, 0xB3ABA3);

		// Add Basalz spawn to Mountain biomes
		List<Biome> validBiomes = new ArrayList<Biome>(Arrays.asList(BiomeDictionary.getBiomesForType(Type.MOUNTAIN)));

		// Add Basalz spawn to Wasteland biomes
		for (Biome biome : BiomeDictionary.getBiomesForType(Type.WASTELAND)) {
			if (!validBiomes.contains(biome)) {
				validBiomes.add(biome);
			}
		}
		// Remove Basalz spawn from End biomes
		for (Biome biome : BiomeDictionary.getBiomesForType(Type.END)) {
			if (validBiomes.contains(biome)) {
				validBiomes.remove(biome);
			}
		}
		EntityRegistry.addSpawn(EntityBasalz.class, spawnWeight, spawnMin, spawnMax, EnumCreatureType.MONSTER, validBiomes.toArray(new Biome[validBiomes.size()]));
	}

	/** Random offset used in floating behaviour */
	protected float heightOffset = 0.5F;

	/** ticks until heightOffset is randomized */
	protected int heightOffsetUpdateTime;
	protected int firingState;

	//public static final String SOUND_AMBIENT = CoreUtils.getSoundName(ThermalFoundation.modId, "mobBasalzAmbient");
	//public static final String SOUND_ATTACK = CoreUtils.getSoundName(ThermalFoundation.modId, "mobBasalzAttack");
	//public static final String SOUND_LIVING[] = { CoreUtils.getSoundName(ThermalFoundation.modId, "mobBasalzBreathe0"), CoreUtils.getSoundName(ThermalFoundation.modId, "mobBasalzBreathe1") };

	protected static final int SOUND_AMBIENT_FREQUENCY = 400; // How often it does ambient sound loop

	public EntityBasalz(World world) {

		super(world);
		this.experienceValue = 10;
	}

	protected void initEntityAI() {
        this.tasks.addTask(4, new EntityBasalz.AIFireballAttack(this));
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

	//@Override
	//protected String getLivingSound() {
		//return SOUND_LIVING[this.rand.nextInt(2)];
	//}

	//@Override
	//protected SoundEvent getHurtSound() {
	//	return "mob.basalz.hit";
	//}

	//@Override
	//protected SoundEvent getDeathSound() {
	//	return "mob.basalz.death";
	//}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float par1) {

		return 0xF000F0;
	}

	@Override
	public float getBrightness(float par1) {

		return 2.0F;
	}

//	@Override
//	public void onLivingUpdate() {
//
//		if (CommonUtils.isServerWorld(worldObj)) {
//			--this.heightOffsetUpdateTime;
//
//			if (this.heightOffsetUpdateTime <= 0) {
//				this.heightOffsetUpdateTime = 100;
//				this.heightOffset = 0.5F + (float) this.rand.nextGaussian() * 3.0F;
//			}
//			Entity target = this.getEntityToAttack();
//			if (target != null) {
//				if ((target.posY + target.getEyeHeight()) > (this.posY + this.getEyeHeight() + this.heightOffset)) {
//					this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
//				}
//			}
//		}
//		if (this.rand.nextInt(SOUND_AMBIENT_FREQUENCY) == 0) {
//			this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SOUND_AMBIENT, this.rand.nextFloat() * 0.2F + 0.1F,
//					this.rand.nextFloat() * 0.3F + 0.4F);
//		}
//		if (!this.onGround && this.motionY < 0.0D) {
//			this.motionY *= 0.6D;
//		}
//		for (int i = 0; i < 2; i++) {
//			this.worldObj.spawnParticle("townaura", this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble()
//					* (this.height * 0.2D), this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
//		}
//		super.onLivingUpdate();
//	}
//
//	/**
//	 * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking (Animals, Spiders at day, peaceful PigZombies).
//	 */
//	@Override
//	protected Entity findPlayerToAttack() {
//
//		EntityPlayer player = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);
//		if (player != null && this.canEntityBeSeen(player)) {
//			return player;
//		}
//		return getClosestVictim(16.0D);
//	}
//
//	public Entity getClosestVictim(double dist) {
//
//		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(this.posX - dist, this.posY - dist, this.posZ - dist, this.posX + dist, this.posY + dist, this.posZ
//				+ dist);
//		EntitySelectorInRangeByType entsel = new EntitySelectorInRangeByType(this, dist, EntityVillager.class);
//		// TODO: should this target INpc instead?
//		List<Entity> entities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, aabb, entsel);
//		if (entities.isEmpty()) {
//			return null;
//		}
//		Entity victim = null;
//		double closest = Double.MAX_VALUE;
//		for (Entity entity : entities) {
//			double distVsq = this.getDistanceSqToEntity(entity);
//			if (distVsq < closest) {
//				closest = distVsq;
//				victim = entity;
//			}
//		}
//		return victim;
//	}
//
//	@Override
//	protected void attackEntity(Entity target, float distance) {
//
//		// Melee distance
//		if (this.attackTime <= 0 && distance < 2.0F && target.boundingBox.maxY > this.boundingBox.minY && target.boundingBox.minY < this.boundingBox.maxY) {
//			this.attackTime = 20;
//			this.attackEntityAsMob(target);
//		}
//		// Within range (30)
//		else if (distance < 30.0F) {
//			double dX = target.posX - this.posX;
//			double dZ = target.posZ - this.posZ;
//
//			if (this.attackTime == 0) {
//				++this.firingState;
//
//				if (this.firingState == 1) {
//					this.attackTime = 60;
//					this.setInAttackMode(true); // Flary goodness :D
//				} else if (this.firingState <= 4) {
//					this.attackTime = 6;
//				} else {
//					this.attackTime = 80; // 100
//					this.firingState = 0;
//					this.setInAttackMode(false); // Unflary sadness :(
//				}
//				if (this.firingState > 1) {
//					EntityBasalzBolt bolt = new EntityBasalzBolt(this.worldObj, this);
//					bolt.posY = this.posY + this.height / 2.0F + 0.5D;
//					this.playSound(SOUND_ATTACK, 2.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
//					this.worldObj.spawnEntityInWorld(bolt);
//				}
//			}
//			this.rotationYaw = (float) (Math.atan2(dZ, dX) * 180.0D / Math.PI) - 90.0F;
//			this.hasAttacked = true;
//		}
//	}

	//TODO Update AI
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
				this.worldObj.spawnParticle(EnumParticleTypes.TOWN_AURA, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.0D, 0.0D, new int[0]);
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
	public void fall(float distance, float damageMultiplier) {}

	@Override
	protected void dropFewItems(boolean wasHitByPlayer, int looting) {

		if (wasHitByPlayer) {
			int items = this.rand.nextInt(2 + looting);
			for (int i = 0; i < items; i++) {
				this.entityDropItem(ItemHelper.cloneStack(TFItems.dustObsidian, 1), 0);
			}
			items = this.rand.nextInt(2 + looting);
			for (int i = 0; i < items; i++) {
				this.entityDropItem(ItemHelper.cloneStack(TFItems.rodBasalz, 1), 0);
			}
		}
	}

	public boolean isInAttackMode() {
		return dataManager.get(ATTACK_MODE);
	}

	public void setInAttackMode(boolean mode) {
		dataManager.set(ATTACK_MODE, mode);
	}

//	@Override
//	protected boolean isValidLightLevel() {
//
//		if (!restrictLightLevel) {
//			return true;
//		}
//		int i = MathHelper.floor(this.posX);
//		int j = MathHelper.floor(this.boundingBox.minY);
//		int k = MathHelper.floor(this.posZ);
//
//		if (this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, i, j, k) > this.rand.nextInt(32)) {
//			return false;
//		} else {
//			int l = this.worldObj.getBlockLightValue(i, j, k);
//
//			if (this.worldObj.isThundering()) {
//				int i1 = this.worldObj.skylightSubtracted;
//				this.worldObj.skylightSubtracted = 10;
//				l = this.worldObj.getBlockLightValue(i, j, k);
//				this.worldObj.skylightSubtracted = i1;
//			}
//			return l <= this.rand.nextInt(spawnLightLevel);
//		}
//	}

	//TODO Not sure if this is correct
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
		private final EntityBasalz basalz;
		private int attackStep;
		private int attackTime;

		public AIFireballAttack(EntityBasalz basalz)
		{
			this.basalz = basalz;
			this.setMutexBits(3);
		}

		public boolean shouldExecute()
		{
			EntityLivingBase entitylivingbase = this.basalz.getAttackTarget();
			return entitylivingbase != null && entitylivingbase.isEntityAlive();
		}

		public void startExecuting()
		{
			this.attackStep = 0;
		}

		public void resetTask()
		{

//			this.basalz.setOnFire(false);
		}

		public void updateTask()
		{
			--this.attackTime;
			EntityLivingBase entitylivingbase = this.basalz.getAttackTarget();
			double d0 = this.basalz.getDistanceSqToEntity(entitylivingbase);

			if (d0 < 4.0D)
			{
				if (this.attackTime <= 0)
				{
					this.attackTime = 20;
					this.basalz.attackEntityAsMob(entitylivingbase);
				}

				this.basalz.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
			}
			else if (d0 < 256.0D)
			{
				double d1 = entitylivingbase.posX - this.basalz.posX;
				double d2 = entitylivingbase.getEntityBoundingBox().minY + (double)(entitylivingbase.height / 2.0F) - (this.basalz.posY + (double)(this.basalz.height / 2.0F));
				double d3 = entitylivingbase.posZ - this.basalz.posZ;

				if (this.attackTime <= 0)
				{
					++this.attackStep;

					if (this.attackStep == 1)
					{
						this.attackTime = 60;
//						this.basalz.setOnFire(true);
					}
					else if (this.attackStep <= 4)
					{
						this.attackTime = 6;
					}
					else
					{
						this.attackTime = 100;
						this.attackStep = 0;
//						this.basalz.setOnFire(false);
					}

					if (this.attackStep > 1)
					{
						float f = net.minecraft.util.math.MathHelper.sqrt_float(net.minecraft.util.math.MathHelper.sqrt_double(d0)) * 0.5F;
						this.basalz.worldObj.playEvent((EntityPlayer)null, 1018, new BlockPos((int)this.basalz.posX, (int)this.basalz.posY, (int)this.basalz.posZ), 0);

						for (int i = 0; i < 1; ++i)
						{
							EntityBasalzBolt bolt = new EntityBasalzBolt(this.basalz.worldObj, this.basalz, d1 + this.basalz.getRNG().nextGaussian() * (double)f, d2, d3 + this.basalz.getRNG().nextGaussian() * (double)f);
							bolt.posY = this.basalz.posY + (double)(this.basalz.height / 2.0F) + 0.5D;
							bolt.posY = basalz.posY + basalz.height / 2.0F + 0.5D;
							basalz.playSound(ModSounds.BASALZ_ATTACK, 2.0F, (basalz.rand.nextFloat() - basalz.rand.nextFloat()) * 0.2F + 1.0F);
							this.basalz.worldObj.spawnEntityInWorld(bolt);
						}
					}
				}

				this.basalz.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
			}
			else
			{
				this.basalz.getNavigator().clearPathEntity();
				this.basalz.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
			}

			super.updateTask();
		}
	}
}
