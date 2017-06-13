package cofh.thermalfoundation.entity.monster;

import cofh.core.init.CoreProps;
import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.entity.projectile.EntityBasalzBolt;
import cofh.thermalfoundation.init.TFSounds;
import cofh.thermalfoundation.item.ItemMaterial;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class EntityBasalz extends EntityElemental {

	static boolean enable = true;
	static boolean restrictLightLevel = true;

	static int spawnLightLevel = 8;

	static int spawnWeight = 10;
	static int spawnMin = 1;
	static int spawnMax = 4;

	public static boolean effect = true;

	public static void initialize(int id) {

		config();

		if (!enable) {
			return;
		}
		EntityRegistry.registerModEntity(new ResourceLocation("thermalfoundation:basalz"), EntityBasalz.class, "basalz", id, ThermalFoundation.instance, CoreProps.ENTITY_TRACKING_DISTANCE, 1, true, 0x606060, 0xB3ABA3);

		// Add Basalz spawn to Mountain biomes
		Set<Biome> validBiomes = BiomeDictionary.getBiomes(Type.MOUNTAIN);

		// Add Basalz spawn to Wasteland biomes
		for (Biome biome : BiomeDictionary.getBiomes(Type.WASTELAND)) {
			if (!validBiomes.contains(biome)) {
				validBiomes.add(biome);
			}
		}
		// Remove Basalz spawn from End biomes
		for (Biome biome : BiomeDictionary.getBiomes(Type.END)) {
			if (validBiomes.contains(biome)) {
				validBiomes.remove(biome);
			}
		}
		EntityRegistry.addSpawn(EntityBasalz.class, spawnWeight, spawnMin, spawnMax, EnumCreatureType.MONSTER, validBiomes.toArray(new Biome[validBiomes.size()]));
	}

	public static void config() {

		String category = "Mob.Basalz";
		String comment;

		comment = "If TRUE, Basalzes will spawn naturally.";
		enable = ThermalFoundation.CONFIG.getConfiguration().getBoolean("Enable", category, enable, comment);

		comment = "If TRUE, Basalzes will only spawn below a specified light level.";
		restrictLightLevel = ThermalFoundation.CONFIG.getConfiguration().getBoolean("LightLevelRestriction", category, restrictLightLevel, comment);

		comment = "This sets the maximum light level Basalzes can spawn at, if restricted.";
		spawnLightLevel = ThermalFoundation.CONFIG.getConfiguration().getInt("LightLevel", category, spawnLightLevel, 0, 15, comment);

		comment = "This sets the minimum number of Basalzes that spawn in a group.";
		spawnMin = ThermalFoundation.CONFIG.getConfiguration().getInt("MinGroupSize", category, spawnMin, 1, 10, comment);

		comment = "This sets the maximum number of Basalzes that spawn in a group.";
		spawnMax = ThermalFoundation.CONFIG.getConfiguration().getInt("MaxGroupSize", category, spawnMax, spawnMin, 24, comment);

		comment = "This sets the relative spawn weight for Basalzes.";
		spawnWeight = ThermalFoundation.CONFIG.getConfiguration().getInt("SpawnWeight", category, spawnWeight, 1, 20, comment);

		comment = "If TRUE, Basalz attacks will inflict Weakness.";
		effect = ThermalFoundation.CONFIG.getConfiguration().getBoolean("Effect", category, effect, comment);
	}

	public EntityBasalz(World world) {

		super(world);

		ambientParticle = EnumParticleTypes.TOWN_AURA;
		ambientSound = TFSounds.BASALZ_AMBIENT;
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
	protected void initEntityAI() {

		tasks.addTask(4, new AIBasalzBoltAttack(this));
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
	static class AIBasalzBoltAttack extends EntityAIBase {

		private final EntityBasalz basalz;
		private int attackStep;
		private int attackTime;

		public AIBasalzBoltAttack(EntityBasalz entity) {

			basalz = entity;
			setMutexBits(3);
		}

		@Override
		public boolean shouldExecute() {

			EntityLivingBase target = basalz.getAttackTarget();
			return target != null && target.isEntityAlive();
		}

		@Override
		public void startExecuting() {

			this.attackStep = 0;
		}

		@Override
		public void resetTask() {

			this.basalz.setInAttackMode(false);
		}

		@Override
		public void updateTask() {

			--this.attackTime;
			EntityLivingBase target = this.basalz.getAttackTarget();
			double d0 = this.basalz.getDistanceSqToEntity(target);

			if (d0 < 4.0D) {
				if (attackTime <= 0) {
					attackTime = 20;
					basalz.attackEntityAsMob(target);
				}

				basalz.getMoveHelper().setMoveTo(target.posX, target.posY, target.posZ, 1.0D);
			} else if (d0 < 256.0D) {

				if (attackTime <= 0) {
					++attackStep;

					if (attackStep == 1) {
						attackTime = 60;
						basalz.setInAttackMode(true);
					} else if (attackStep <= 4) {
						attackTime = 6;
					} else {
						attackTime = 100;
						attackStep = 0;
						basalz.setInAttackMode(false);
					}

					if (attackStep > 1) {
						basalz.world.playEvent(null, 1009, new BlockPos((int) basalz.posX, (int) basalz.posY, (int) basalz.posZ), 0);

						for (int i = 0; i < 1; ++i) {
							EntityBasalzBolt bolt = new EntityBasalzBolt(basalz.world, basalz);
							bolt.setThrowableHeading(target.posX - basalz.posX, target.posY - basalz.posY, target.posZ - basalz.posZ, 1.5F, 1.0F);
							bolt.posY = basalz.posY + basalz.height / 2.0F + 0.5D;
							basalz.playSound(TFSounds.BASALZ_ATTACK, 2.0F, (basalz.rand.nextFloat() - basalz.rand.nextFloat()) * 0.2F + 1.0F);
							basalz.world.spawnEntity(bolt);
						}
					}
				}
				basalz.getLookHelper().setLookPositionWithEntity(target, 10.0F, 10.0F);
			} else {
				basalz.getNavigator().clearPathEntity();
				basalz.getMoveHelper().setMoveTo(target.posX, target.posY, target.posZ, 1.0D);
			}
			super.updateTask();
		}
	}

}
