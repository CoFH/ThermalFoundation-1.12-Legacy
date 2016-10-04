package cofh.thermalfoundation.entity.monster;

import cofh.core.CoFHProps;
import cofh.core.util.CoreUtils;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.MathHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.entity.projectile.EntityBasalzBolt;
import cofh.thermalfoundation.item.ItemMaterial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class EntityBasalz extends EntityElemental {

	static int entityId = -1;

	static boolean enable = true;
	static boolean restrictLightLevel = true;

	static int spawnLightLevel = 8;

	static int spawnWeight = 10;
	static int spawnMin = 1;
	static int spawnMax = 4;

	private static final SoundEvent attackSound = CoreUtils.getSoundEvent(ThermalFoundation.modId, "mob_basalz_attack");
	private static final SoundEvent ambientSound0 = CoreUtils.getSoundEvent(ThermalFoundation.modId, "mob_basalz_breathe0");
	private static final SoundEvent ambientSound1 = CoreUtils.getSoundEvent(ThermalFoundation.modId, "mob_basalz_breathe1");
	private static final SoundEvent specialAmbientSound = CoreUtils.getSoundEvent(ThermalFoundation.modId, "mob_basalz_ambient");
	private static final SoundEvent[] ambientSounds = new SoundEvent[] {ambientSound0, ambientSound1};

	static {
		String category = "Mob.Basalz";
		String comment = "";

		comment = "Set this to false to disable Basalzes entirely. Jerk.";
		enable = ThermalFoundation.CONFIG.get(category, "Enable", enable, comment);

		category = "Mob.Basalz.Spawn";

		comment = "Set this to false for Basalzes to spawn at any light level.";
		restrictLightLevel = ThermalFoundation.CONFIG.get(category, "Light.Limit", restrictLightLevel, comment);

		comment = "This sets the maximum light level Basalzes can spawn at, if restricted.";
		spawnLightLevel = MathHelper.clamp(ThermalFoundation.CONFIG.get(category, "Light.Level", spawnLightLevel, comment), 0, 15);

		comment = "This sets the minimum number of Basalzes that spawn in a group.";
		spawnMin = MathHelper.clamp(ThermalFoundation.CONFIG.get(category, "MinGroupSize", spawnMin, comment), 1, 10);

		comment = "This sets the maximum light number of Basalzes that spawn in a group.";
		spawnMax = MathHelper.clamp(ThermalFoundation.CONFIG.get(category, "MaxGroupSize", spawnMax, comment), spawnMin, 24);

		comment = "This sets the relative spawn weight for Basalzes.";
		spawnWeight = ThermalFoundation.CONFIG.get(category, "SpawnWeight", spawnWeight, comment);
	}

	public static void initialize() {

		if (!enable) {
			return;
		}
		entityId = CoreUtils.getEntityId();
		EntityRegistry.registerModEntity(EntityBasalz.class, "basalz", entityId, ThermalFoundation.instance, CoFHProps.ENTITY_TRACKING_DISTANCE, 1, true,
				0x606060, 0xB3ABA3);

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
		EntityRegistry.addSpawn(EntityBasalz.class, spawnWeight, spawnMin, spawnMax, EnumCreatureType.MONSTER, validBiomes.toArray(new Biome[0]));

		GameRegistry.register(attackSound.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "mob_basalz_attack")));
		GameRegistry.register(ambientSound0.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "mob_basalz_breathe0")));
		GameRegistry.register(ambientSound1.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "mob_basalz_breathe1")));
		GameRegistry.register(specialAmbientSound.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "mob_basalz_ambient")));
	}

	public EntityBasalz(World world) {

		super(world);
		this.tasks.addTask(4, new EntityBasalz.AIBasalzballAttack(this));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));

		ambientParticle = EnumParticleTypes.TOWN_AURA;
	}

	@Override
	protected SoundEvent[] getAmbientSounds() {

		return ambientSounds;
	}

	@Override
	protected SoundEvent getSpecialAmbientSound() {

		return specialAmbientSound;
	}

	@Override
	protected void dropFewItems(boolean wasHitByPlayer, int looting) {

		if (wasHitByPlayer) {
			int items = this.rand.nextInt(2 + looting);
			for (int i = 0; i < items; i++) {
				this.entityDropItem(ItemHelper.cloneStack(ItemMaterial.dustObsidian, 1), 0);
			}
			items = this.rand.nextInt(2 + looting);
			for (int i = 0; i < items; i++) {
				this.entityDropItem(ItemHelper.cloneStack(ItemMaterial.rodBasalz, 1), 0);
			}
		}
	}

	@Override
	protected boolean restrictLightLevel() {

		return restrictLightLevel;
	}

	@Override
	protected int getSpawnLightLevel() {

		return spawnLightLevel;
	}

	/* ATTACK */
	static class AIBasalzballAttack extends EntityAIBase {

		private final EntityBasalz basalz;
		private int field_179467_b;
		private int field_179468_c;

		public AIBasalzballAttack(EntityBasalz entity) {

			this.basalz = entity;
			this.setMutexBits(3);
		}

		@Override
		public boolean shouldExecute() {

			EntityLivingBase entitylivingbase = this.basalz.getAttackTarget();
			return entitylivingbase != null && entitylivingbase.isEntityAlive();
		}

		@Override
		public void startExecuting() {

			this.field_179467_b = 0;
		}

		@Override
		public void resetTask() {

			this.basalz.setInAttackMode(false);
		}

		@Override
		public void updateTask() {

			--this.field_179468_c;
			EntityLivingBase entitylivingbase = this.basalz.getAttackTarget();
			double d0 = this.basalz.getDistanceSqToEntity(entitylivingbase);

			if (d0 < 4.0D) {
				if (this.field_179468_c <= 0) {
					this.field_179468_c = 20;
					this.basalz.attackEntityAsMob(entitylivingbase);
				}

				this.basalz.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
			} else if (d0 < 256.0D) {
				double d1 = entitylivingbase.posX - this.basalz.posX;
				double d2 = entitylivingbase.getEntityBoundingBox().minY + entitylivingbase.height / 2.0F - (this.basalz.posY + this.basalz.height / 2.0F);
				double d3 = entitylivingbase.posZ - this.basalz.posZ;

				if (this.field_179468_c <= 0) {
					++this.field_179467_b;

					if (this.field_179467_b == 1) {
						this.field_179468_c = 60;
						this.basalz.setInAttackMode(true);
					} else if (this.field_179467_b <= 4) {
						this.field_179468_c = 6;
					} else {
						this.field_179468_c = 100;
						this.field_179467_b = 0;
						this.basalz.setInAttackMode(false);
					}

					if (this.field_179467_b > 1) {
						double f = Math.sqrt(Math.sqrt(d0)) * 0.5F;
						this.basalz.worldObj.playEvent(null, 1009, new BlockPos((int) this.basalz.posX, (int) this.basalz.posY,
								(int) this.basalz.posZ), 0);

						for (int i = 0; i < 1; ++i) {
							EntityBasalzBolt bolt = new EntityBasalzBolt(this.basalz.worldObj, this.basalz);
							bolt.posY = this.basalz.posY + this.basalz.height / 2.0F + 0.5D;
							this.basalz.playSound(attackSound, 2.0F, (this.basalz.rand.nextFloat() - this.basalz.rand.nextFloat()) * 0.2F + 1.0F);
							this.basalz.worldObj.spawnEntityInWorld(bolt);
						}
					}
				}
				this.basalz.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
			} else {
				this.basalz.getNavigator().clearPathEntity();
				this.basalz.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
			}
			super.updateTask();
		}
	}
}
