package cofh.thermalfoundation.entity.monster;

import cofh.core.CoFHProps;
import cofh.core.util.CoreUtils;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.MathHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.entity.projectile.EntityBlitzBolt;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;

public class EntityBlitz extends EntityElemental {

	static int entityId = -1;

	static boolean enable = true;
	static boolean restrictLightLevel = true;

	static int spawnLightLevel = 8;

	static int spawnWeight = 10;
	static int spawnMin = 1;
	static int spawnMax = 4;

	private static final ResourceLocation LOOT_TABLE_BLITZ = new ResourceLocation(ThermalFoundation.modId, "entities/blitz");
	private static final SoundEvent attackSound = CoreUtils.getSoundEvent(ThermalFoundation.modId, "mob_blitz_attack");
	private static final SoundEvent ambientSound0 = CoreUtils.getSoundEvent(ThermalFoundation.modId, "mob_blitz_breathe0");
	private static final SoundEvent ambientSound1 = CoreUtils.getSoundEvent(ThermalFoundation.modId, "mob_blitz_breathe1");
	private static final SoundEvent ambientSound2 = CoreUtils.getSoundEvent(ThermalFoundation.modId, "mob_blitz_breathe2");
	private static final SoundEvent specialAmbientSound = CoreUtils.getSoundEvent(ThermalFoundation.modId, "mob_blitz_ambient");
	private static final SoundEvent[] ambientSounds = new SoundEvent[] {ambientSound0, ambientSound1, ambientSound2};

	static {
		String category = "Mob.Blitz";
		String comment = "";

		comment = "Set this to false to disable Blitzes entirely. Jerk.";
		enable = ThermalFoundation.CONFIG.get(category, "Enable", enable, comment);

		category = "Mob.Blitz.Spawn";

		comment = "Set this to false for Blitzes to spawn at any light level.";
		restrictLightLevel = ThermalFoundation.CONFIG.get(category, "Light.Limit", restrictLightLevel, comment);

		comment = "This sets the maximum light level Blitzes can spawn at, if restricted.";
		spawnLightLevel = MathHelper.clamp(ThermalFoundation.CONFIG.get(category, "Light.Level", spawnLightLevel, comment), 0, 15);

		comment = "This sets the minimum number of Blitzes that spawn in a group.";
		spawnMin = MathHelper.clamp(ThermalFoundation.CONFIG.get(category, "MinGroupSize", spawnMin, comment), 1, 10);

		comment = "This sets the maximum light number of Blitzes that spawn in a group.";
		spawnMax = MathHelper.clamp(ThermalFoundation.CONFIG.get(category, "MaxGroupSize", spawnMax, comment), spawnMin, 24);

		comment = "This sets the relative spawn weight for Blitzes.";
		spawnWeight = ThermalFoundation.CONFIG.get(category, "SpawnWeight", spawnWeight, comment);
	}

	public static void initialize() {

		if (!enable) {
			return;
		}
		entityId = CoreUtils.getEntityId();
		EntityRegistry.registerModEntity(EntityBlitz.class, "blitz", entityId, ThermalFoundation.instance, CoFHProps.ENTITY_TRACKING_DISTANCE, 1, true,
				0xF0F8FF, 0xFFEFD5);

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

		LootTableList.register(LOOT_TABLE_BLITZ);

		GameRegistry.register(attackSound.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "mob_blitz_attack")));
		GameRegistry.register(ambientSound0.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "mob_blitz_breathe0")));
		GameRegistry.register(ambientSound1.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "mob_blitz_breathe1")));
		GameRegistry.register(ambientSound2.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "mob_blitz_breathe2")));
		GameRegistry.register(specialAmbientSound.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "mob_blitz_ambient")));
	}

	public EntityBlitz(World world) {

		super(world);
		this.tasks.addTask(4, new EntityBlitz.AIBlitzballAttack(this));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));

		ambientParticle = EnumParticleTypes.CLOUD;

	}

	@Override
	protected SoundEvent[] getAmbientSounds() {

		return ambientSounds;
	}

	@Override
	protected SoundEvent getSpecialAmbientSound() {

		return specialAmbientSound;
	}

	@Nullable
	@Override
	protected ResourceLocation getLootTable() {
		return LOOT_TABLE_BLITZ;
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
	static class AIBlitzballAttack extends EntityAIBase {

		private final EntityBlitz blitz;
		private int field_179467_b;
		private int field_179468_c;

		public AIBlitzballAttack(EntityBlitz entity) {

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

			this.field_179467_b = 0;
		}

		@Override
		public void resetTask() {

			this.blitz.setInAttackMode(false);
		}

		@Override
		public void updateTask() {

			--this.field_179468_c;
			EntityLivingBase entitylivingbase = this.blitz.getAttackTarget();
			double d0 = this.blitz.getDistanceSqToEntity(entitylivingbase);

			if (d0 < 4.0D) {
				if (this.field_179468_c <= 0) {
					this.field_179468_c = 20;
					this.blitz.attackEntityAsMob(entitylivingbase);
				}

				this.blitz.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
			} else if (d0 < 256.0D) {
				double d1 = entitylivingbase.posX - this.blitz.posX;
				double d2 = entitylivingbase.getEntityBoundingBox().minY + entitylivingbase.height / 2.0F - (this.blitz.posY + this.blitz.height / 2.0F);
				double d3 = entitylivingbase.posZ - this.blitz.posZ;

				if (this.field_179468_c <= 0) {
					++this.field_179467_b;

					if (this.field_179467_b == 1) {
						this.field_179468_c = 60;
						this.blitz.setInAttackMode(true);
					} else if (this.field_179467_b <= 4) {
						this.field_179468_c = 6;
					} else {
						this.field_179468_c = 100;
						this.field_179467_b = 0;
						this.blitz.setInAttackMode(false);
					}

					if (this.field_179467_b > 1) {
						double f = Math.sqrt(Math.sqrt(d0)) * 0.5F;
						this.blitz.worldObj.playEvent(null, 1009, new BlockPos((int) this.blitz.posX, (int) this.blitz.posY,
								(int) this.blitz.posZ), 0);

						for (int i = 0; i < 1; ++i) {
							EntityBlitzBolt bolt = new EntityBlitzBolt(this.blitz.worldObj, this.blitz);
							bolt.posY = this.blitz.posY + this.blitz.height / 2.0F + 0.5D;
							this.blitz.playSound(attackSound, 2.0F, (this.blitz.rand.nextFloat() - this.blitz.rand.nextFloat()) * 0.2F + 1.0F);
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
