package cofh.thermalfoundation.entity.monster;

import cofh.core.init.CoreProps;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.entity.projectile.EntityBlizzBolt;
import cofh.thermalfoundation.init.TFSounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.ai.*;
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

public class EntityBlizz extends EntityElemental {

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

		lootTable = LootTableList.register(new ResourceLocation(ThermalFoundation.MOD_ID, "entities/blizz"));

		if (!enable) {
			return;
		}
		EntityRegistry.registerModEntity(new ResourceLocation("thermalfoundation:blizz"), EntityBlizz.class, "thermalfoundation.blizz", id, ThermalFoundation.instance, CoreProps.ENTITY_TRACKING_DISTANCE, 1, true, 0xE0FBFF, 0x6BE6FF);

		Set<Biome> validBiomes = new HashSet<>();

		validBiomes.addAll(BiomeDictionary.getBiomes(Type.COLD));
		validBiomes.addAll(BiomeDictionary.getBiomes(Type.SNOWY));
		validBiomes.removeAll(BiomeDictionary.getBiomes(Type.NETHER));
		validBiomes.removeAll(BiomeDictionary.getBiomes(Type.END));

		EntityRegistry.addSpawn(EntityBlizz.class, spawnWeight, spawnMin, spawnMax, EnumCreatureType.MONSTER, validBiomes.toArray(new Biome[validBiomes.size()]));
	}

	public static void config() {

		String category = "Mob.Blizz";
		String comment;

		comment = "If TRUE, Blizzes will spawn naturally.";
		enable = ThermalFoundation.CONFIG.getConfiguration().getBoolean("Enable", category, enable, comment);

		comment = "If TRUE, Blizzes will only spawn below a specified light level.";
		restrictLightLevel = ThermalFoundation.CONFIG.getConfiguration().getBoolean("LightLevelRestriction", category, restrictLightLevel, comment);

		comment = "This sets the maximum light level Blizzes can spawn at, if restricted.";
		spawnLightLevel = ThermalFoundation.CONFIG.getConfiguration().getInt("LightLevel", category, spawnLightLevel, 0, 15, comment);

		comment = "This sets the minimum number of Blizzes that spawn in a group.";
		spawnMin = ThermalFoundation.CONFIG.getConfiguration().getInt("MinGroupSize", category, spawnMin, 1, 10, comment);

		comment = "This sets the maximum number of Blizzes that spawn in a group.";
		spawnMax = ThermalFoundation.CONFIG.getConfiguration().getInt("MaxGroupSize", category, spawnMax, spawnMin, 24, comment);

		comment = "This sets the relative spawn weight for Blizzes.";
		spawnWeight = ThermalFoundation.CONFIG.getConfiguration().getInt("SpawnWeight", category, spawnWeight, 1, 20, comment);

		comment = "If TRUE, Blizz attacks will inflict Slowness.";
		effect = ThermalFoundation.CONFIG.getConfiguration().getBoolean("Effect", category, effect, comment);
	}

	public EntityBlizz(World world) {

		super(world);

		ambientParticle = EnumParticleTypes.SNOWBALL;
		ambientSound = TFSounds.blizzAmbient;
	}

	@Nullable
	protected ResourceLocation getLootTable() {

		return lootTable;
	}

	@Override
	protected void initEntityAI() {

		tasks.addTask(4, new EntityBlizz.AIBlizzballAttack(this));
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
	static class AIBlizzballAttack extends EntityAIBase {

		private final EntityBlizz blizz;
		private int attackStep;
		private int attackTime;

		public AIBlizzballAttack(EntityBlizz entity) {

			blizz = entity;
			setMutexBits(3);
		}

		@Override
		public boolean shouldExecute() {

			EntityLivingBase target = blizz.getAttackTarget();
			return target != null && target.isEntityAlive();
		}

		@Override
		public void startExecuting() {

			attackStep = 0;
		}

		@Override
		public void resetTask() {

			blizz.setInAttackMode(false);
		}

		@Override
		public void updateTask() {

			--attackTime;
			EntityLivingBase target = blizz.getAttackTarget();
			double d0 = blizz.getDistance(target);

			if (d0 < 4.0D) {
				if (attackTime <= 0) {
					attackTime = 20;
					blizz.attackEntityAsMob(target);
				}

				blizz.getMoveHelper().setMoveTo(target.posX, target.posY, target.posZ, 1.0D);
			} else if (d0 < 256.0D) {

				if (attackTime <= 0) {
					++attackStep;

					if (attackStep == 1) {
						attackTime = 60;
						blizz.setInAttackMode(true);
					} else if (attackStep <= 4) {
						attackTime = 6;
					} else {
						attackTime = 100;
						attackStep = 0;
						blizz.setInAttackMode(false);
					}

					if (attackStep > 1) {
						blizz.world.playEvent(null, 1009, new BlockPos((int) blizz.posX, (int) blizz.posY, (int) blizz.posZ), 0);

						for (int i = 0; i < 1; ++i) {
							EntityBlizzBolt bolt = new EntityBlizzBolt(blizz.world, blizz);
							bolt.posY = blizz.posY + blizz.height / 2.0F + 0.5D;
							bolt.shoot(target.posX - blizz.posX, target.posY - blizz.posY, target.posZ - blizz.posZ, 1.5F, 1.0F);
							blizz.playSound(TFSounds.blizzAttack, 2.0F, (blizz.rand.nextFloat() - blizz.rand.nextFloat()) * 0.2F + 1.0F);
							blizz.world.spawnEntity(bolt);
						}
					}
				}
				blizz.getLookHelper().setLookPositionWithEntity(target, 10.0F, 10.0F);
			} else {
				blizz.getNavigator().clearPath();
				blizz.getMoveHelper().setMoveTo(target.posX, target.posY, target.posZ, 1.0D);
			}
			super.updateTask();
		}
	}

}
