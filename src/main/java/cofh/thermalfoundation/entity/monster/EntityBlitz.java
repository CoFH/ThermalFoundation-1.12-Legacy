package cofh.thermalfoundation.entity.monster;

import cofh.core.init.CoreProps;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.entity.projectile.EntityBlitzBolt;
import cofh.thermalfoundation.init.TFSounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class EntityBlitz extends EntityElemental {

	static boolean enable = true;
	static boolean restrictLightLevel = true;
	static ResourceLocation lootTable;

	static int spawnLightLevel = 8;

	static int spawnWeight = 10;
	static int spawnMin = 1;
	static int spawnMax = 4;

	public static boolean effect = true;

	public static void initialize(int id) {

		config();

		lootTable = LootTableList.register(new ResourceLocation(ThermalFoundation.MOD_ID, "entities/blitz"));

		EntityRegistry.registerModEntity(new ResourceLocation("thermalfoundation:blitz"), EntityBlitz.class, "thermalfoundation.blitz", id, ThermalFoundation.instance, CoreProps.ENTITY_TRACKING_DISTANCE, 1, true, 0xF0F8FF, 0xFFEFD5);

		if (!enable) {
			return;
		}
		Set<Biome> validBiomes = new HashSet<>();

		validBiomes.addAll(BiomeDictionary.getBiomes(Type.SANDY));
		validBiomes.addAll(BiomeDictionary.getBiomes(Type.SAVANNA));
		validBiomes.removeAll(BiomeDictionary.getBiomes(Type.NETHER));
		validBiomes.removeAll(BiomeDictionary.getBiomes(Type.END));

		EntityRegistry.addSpawn(EntityBlitz.class, spawnWeight, spawnMin, spawnMax, EnumCreatureType.MONSTER, validBiomes.toArray(new Biome[validBiomes.size()]));
	}

	public static void config() {

		String category = "Mob.Blitz";
		String comment;

		comment = "If TRUE, Blitzes will spawn naturally.";
		enable = ThermalFoundation.CONFIG.getConfiguration().getBoolean("Enable", category, enable, comment);

		comment = "If TRUE, Blitzes will only spawn below a specified light level.";
		restrictLightLevel = ThermalFoundation.CONFIG.getConfiguration().getBoolean("LightLevelRestriction", category, restrictLightLevel, comment);

		comment = "This sets the maximum light level Blitzes can spawn at, if restricted.";
		spawnLightLevel = ThermalFoundation.CONFIG.getConfiguration().getInt("LightLevel", category, spawnLightLevel, 0, 15, comment);

		comment = "This sets the minimum number of Blitzes that spawn in a group.";
		spawnMin = ThermalFoundation.CONFIG.getConfiguration().getInt("MinGroupSize", category, spawnMin, 1, 10, comment);

		comment = "This sets the maximum number of Blitzes that spawn in a group.";
		spawnMax = ThermalFoundation.CONFIG.getConfiguration().getInt("MaxGroupSize", category, spawnMax, spawnMin, 24, comment);

		comment = "This sets the relative spawn weight for Blitzes.";
		spawnWeight = ThermalFoundation.CONFIG.getConfiguration().getInt("SpawnWeight", category, spawnWeight, 1, 20, comment);

		comment = "If TRUE, Blitz attacks will inflict Blindness.";
		effect = ThermalFoundation.CONFIG.getConfiguration().getBoolean("Effect", category, effect, comment);
	}

	public EntityBlitz(World world) {

		super(world);

		ambientParticle = EnumParticleTypes.CLOUD;
		ambientSound = TFSounds.blitzAmbient;
	}

	@Nullable
	protected ResourceLocation getLootTable() {

		return lootTable;
	}

	@Override
	protected void initEntityAI() {

		tasks.addTask(4, new AIBlitzBoltAttack(this));
		tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(7, new EntityAIWander(this, 1.0D));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(8, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
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

			blitz = entity;
			setMutexBits(3);
		}

		@Override
		public boolean shouldExecute() {

			EntityLivingBase target = blitz.getAttackTarget();
			return target != null && target.isEntityAlive();
		}

		@Override
		public void startExecuting() {

			attackStep = 0;
		}

		@Override
		public void resetTask() {

			blitz.setInAttackMode(false);
		}

		@Override
		public void updateTask() {

			--attackTime;
			EntityLivingBase target = blitz.getAttackTarget();
			double d0 = blitz.getDistanceSq(target);

			if (d0 < 4.0D) {
				if (attackTime <= 0) {
					attackTime = 20;
					blitz.attackEntityAsMob(target);
				}

				blitz.getMoveHelper().setMoveTo(target.posX, target.posY, target.posZ, 1.0D);
			} else if (d0 < getFollowDistance() * getFollowDistance()) {

				if (attackTime <= 0) {
					++attackStep;

					if (attackStep == 1) {
						attackTime = 60;
						blitz.setInAttackMode(true);
					} else if (attackStep <= 4) {
						attackTime = 6;
					} else {
						attackTime = 100;
						attackStep = 0;
						blitz.setInAttackMode(false);
					}

					if (attackStep > 1) {
						blitz.world.playEvent(null, 1009, new BlockPos((int) blitz.posX, (int) blitz.posY, (int) blitz.posZ), 0);

						for (int i = 0; i < 1; ++i) {
							EntityBlitzBolt bolt = new EntityBlitzBolt(blitz.world, blitz);
							bolt.posY = blitz.posY + blitz.height / 2.0F + 0.5D;
							bolt.shoot(target.posX - blitz.posX, target.posY - blitz.posY, target.posZ - blitz.posZ, 1.5F, 1.0F);
							blitz.playSound(TFSounds.blitzAttack, 2.0F, (blitz.rand.nextFloat() - blitz.rand.nextFloat()) * 0.2F + 1.0F);
							blitz.world.spawnEntity(bolt);
						}
					}
				}
				blitz.getLookHelper().setLookPositionWithEntity(target, 10.0F, 10.0F);
			} else {
				blitz.getNavigator().clearPath();
				blitz.getMoveHelper().setMoveTo(target.posX, target.posY, target.posZ, 1.0D);
			}
			super.updateTask();
		}

		private double getFollowDistance() {

			IAttributeInstance attribute = this.blitz.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
			return attribute == null ? 16.0D : attribute.getAttributeValue();
		}
	}

}
