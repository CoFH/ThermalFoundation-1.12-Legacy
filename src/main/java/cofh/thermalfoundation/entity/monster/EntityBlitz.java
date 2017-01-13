package cofh.thermalfoundation.entity.monster;

import cofh.core.CoFHProps;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.MathHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.entity.projectile.EntityBlitzBolt;
import cofh.thermalfoundation.init.ModSounds;
import cofh.thermalfoundation.item.ItemMaterial;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityBlitz extends EntityElemental {

	static boolean enable = true;
	static boolean restrictLightLevel = true;

	static int spawnLightLevel = 8;

	static int spawnWeight = 10;
	static int spawnMin = 1;
	static int spawnMax = 4;

	public static void initialize(int id) {

		config();

		if (!enable) {
			return;
		}
		EntityRegistry.registerModEntity(EntityBlitz.class, "blitz", id, ThermalFoundation.instance, CoFHProps.ENTITY_TRACKING_DISTANCE, 1, true, 0xF0F8FF, 0xFFEFD5);

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
		EntityRegistry.addSpawn(EntityBlitz.class, spawnWeight, spawnMin, spawnMax, EnumCreatureType.MONSTER, validBiomes.toArray(new Biome[validBiomes.size()]));
	}

	public static void config() {

		String category = "Mob.Blitz";
		String comment;

		comment = "Set this to false to disable Blitzes entirely. Jerk.";
		enable = ThermalFoundation.CONFIG.getConfiguration().get(category, "Enable", enable, comment).getBoolean(enable);

		category = "Mob.Blitz.Spawn";

		comment = "Set this to false for Blitzes to spawn at any light level.";
		restrictLightLevel = ThermalFoundation.CONFIG.getConfiguration().get(category, "Light.Limit", restrictLightLevel, comment).getBoolean(restrictLightLevel);

		comment = "This sets the maximum light level Blitzes can spawn at, if restricted.";
		spawnLightLevel = MathHelper.clamp(ThermalFoundation.CONFIG.getConfiguration().get(category, "Light.Level", spawnLightLevel, comment).getInt(spawnLightLevel), 0, 15);

		comment = "This sets the minimum number of Blitzes that spawn in a group.";
		spawnMin = MathHelper.clamp(ThermalFoundation.CONFIG.getConfiguration().get(category, "MinGroupSize", spawnMin, comment).getInt(spawnMin), 1, 10);

		comment = "This sets the maximum light number of Blitzes that spawn in a group.";
		spawnMax = MathHelper.clamp(ThermalFoundation.CONFIG.getConfiguration().get(category, "MaxGroupSize", spawnMax, comment).getInt(spawnMax), spawnMin, 24);

		comment = "This sets the relative spawn weight for Blitzes.";
		spawnWeight = ThermalFoundation.CONFIG.getConfiguration().get(category, "SpawnWeight", spawnWeight, comment).getInt(spawnWeight);
	}

	public EntityBlitz(World world) {

		super(world);
		ambientParticle = EnumParticleTypes.CLOUD;
	}

	@Override
	protected void initEntityAI() {

		this.tasks.addTask(4, new AIBlitzBoltAttack(this));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	protected void dropFewItems(boolean wasHitByPlayer, int looting) {

		if (wasHitByPlayer) {
			int items = this.rand.nextInt(2 + looting);
			for (int i = 0; i < items; i++) {
				this.entityDropItem(ItemHelper.cloneStack(ItemMaterial.dustNiter, 1), 0);
			}
			items = this.rand.nextInt(2 + looting);
			for (int i = 0; i < items; i++) {
				this.entityDropItem(ItemHelper.cloneStack(ItemMaterial.rodBlitz, 1), 0);
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
	static class AIBlitzBoltAttack extends EntityAIBase {

		private final EntityBlitz blitz;
		private int attackStep;
		private int attackTime;

		public AIBlitzBoltAttack(EntityBlitz entity) {

			this.blitz = entity;
			this.setMutexBits(3);
		}

		@Override
		public boolean shouldExecute() {

			EntityLivingBase entitylivingbase = this.blitz.getAttackTarget();
			return entitylivingbase != null && entitylivingbase.isEntityAlive();
		}

		@Override
		public void startExecuting() {

			this.attackStep = 0;
		}

		@Override
		public void resetTask() {

			this.blitz.setInAttackMode(false);
		}

		@Override
		public void updateTask() {

			--this.attackTime;
			EntityLivingBase entitylivingbase = this.blitz.getAttackTarget();
			double d0 = this.blitz.getDistanceSqToEntity(entitylivingbase);

			if (d0 < 4.0D) {
				if (this.attackTime <= 0) {
					this.attackTime = 20;
					this.blitz.attackEntityAsMob(entitylivingbase);
				}

				this.blitz.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
			} else if (d0 < 256.0D) {

				if (this.attackTime <= 0) {
					++this.attackStep;

					if (this.attackStep == 1) {
						this.attackTime = 60;
						this.blitz.setInAttackMode(true);
					} else if (this.attackStep <= 4) {
						this.attackTime = 6;
					} else {
						this.attackTime = 100;
						this.attackStep = 0;
						this.blitz.setInAttackMode(false);
					}

					if (this.attackStep > 1) {
						this.blitz.worldObj.playEvent(null, 1009, new BlockPos((int) this.blitz.posX, (int) this.blitz.posY, (int) this.blitz.posZ), 0);

						for (int i = 0; i < 1; ++i) {
							EntityBlitzBolt bolt = new EntityBlitzBolt(this.blitz.worldObj, this.blitz);
							bolt.posY = this.blitz.posY + this.blitz.height / 2.0F + 0.5D;
							this.blitz.playSound(ModSounds.BLITZ_ATTACK, 2.0F, (blitz.rand.nextFloat() - blitz.rand.nextFloat()) * 0.2F + 1.0F);
							this.blitz.worldObj.spawnEntityInWorld(bolt);
						}
					}
				}
				this.blitz.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
			} else {
				this.blitz.getNavigator().clearPathEntity();
				this.blitz.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
			}
			super.updateTask();
		}
	}

}
