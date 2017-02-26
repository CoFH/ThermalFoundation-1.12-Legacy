package cofh.thermalfoundation.entity.monster;

import cofh.core.init.CoreProps;
import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.entity.projectile.EntityBlizzBolt;
import cofh.thermalfoundation.init.TFSounds;
import cofh.thermalfoundation.item.ItemMaterial;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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

public class EntityBlizz extends EntityElemental {

	static boolean enable = true;
	static boolean restrictLightLevel = true;

	static int spawnLightLevel = 8;

	static int spawnWeight = 10;
	static int spawnMin = 1;
	static int spawnMax = 4;

	public static void initialize(int id) {

		config();
		EntityRegistry.registerModEntity(EntityBlizz.class, "blizz", id, ThermalFoundation.instance, CoreProps.ENTITY_TRACKING_DISTANCE, 1, true, 0xE0FBFF, 0x6BE6FF);

		// Add Blizz spawn to Cold biomes
		List<Biome> validBiomes = new ArrayList<>(Arrays.asList(BiomeDictionary.getBiomesForType(Type.COLD)));

		// Add Blizz spawn to Snowy biomes (in vanilla, all snowy are also cold)
		for (Biome biome : BiomeDictionary.getBiomesForType(Type.SNOWY)) {
			if (!validBiomes.contains(biome)) {
				validBiomes.add(biome);
			}
		}
		// Remove Blizz spawn from End biomes
		for (Biome biome : BiomeDictionary.getBiomesForType(Type.END)) {
			if (validBiomes.contains(biome)) {
				validBiomes.remove(biome);
			}
		}
		if (!enable) {
			return;
		}
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
	}

	public EntityBlizz(World world) {

		super(world);

		ambientParticle = EnumParticleTypes.SNOWBALL;
		ambientSound = TFSounds.BLIZZ_AMBIENT;
	}

	@Override
	protected void dropFewItems(boolean wasHitByPlayer, int looting) {

		if (wasHitByPlayer) {
			int items = this.rand.nextInt(4 + looting);
			for (int i = 0; i < items; i++) {
				this.entityDropItem(new ItemStack(Items.SNOWBALL), 0);
			}
			items = this.rand.nextInt(2 + looting);
			for (int i = 0; i < items; i++) {
				this.entityDropItem(ItemHelper.cloneStack(ItemMaterial.rodBlizz, 1), 0);
			}
		}
	}

	@Override
	protected void initEntityAI() {

		this.tasks.addTask(4, new EntityBlizz.AIBlizzballAttack(this));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
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

			this.blizz = entity;
			this.setMutexBits(3);
		}

		@Override
		public boolean shouldExecute() {

			EntityLivingBase entitylivingbase = this.blizz.getAttackTarget();
			return entitylivingbase != null && entitylivingbase.isEntityAlive();
		}

		@Override
		public void startExecuting() {

			this.attackStep = 0;
		}

		@Override
		public void resetTask() {

			this.blizz.setInAttackMode(false);
		}

		@Override
		public void updateTask() {

			--this.attackTime;
			EntityLivingBase entitylivingbase = this.blizz.getAttackTarget();
			double d0 = this.blizz.getDistanceSqToEntity(entitylivingbase);

			if (d0 < 4.0D) {
				if (this.attackTime <= 0) {
					this.attackTime = 20;
					this.blizz.attackEntityAsMob(entitylivingbase);
				}

				this.blizz.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
			} else if (d0 < 256.0D) {

				if (this.attackTime <= 0) {
					++this.attackStep;

					if (this.attackStep == 1) {
						this.attackTime = 60;
						this.blizz.setInAttackMode(true);
					} else if (this.attackStep <= 4) {
						this.attackTime = 6;
					} else {
						this.attackTime = 100;
						this.attackStep = 0;
						this.blizz.setInAttackMode(false);
					}

					if (this.attackStep > 1) {
						this.blizz.worldObj.playEvent(null, 1009, new BlockPos((int) this.blizz.posX, (int) this.blizz.posY, (int) this.blizz.posZ), 0);

						for (int i = 0; i < 1; ++i) {
							EntityBlizzBolt bolt = new EntityBlizzBolt(this.blizz.worldObj, this.blizz);
							bolt.posY = this.blizz.posY + this.blizz.height / 2.0F + 0.5D;
							this.blizz.playSound(TFSounds.BLIZZ_ATTACK, 2.0F, (blizz.rand.nextFloat() - blizz.rand.nextFloat()) * 0.2F + 1.0F);
							this.blizz.worldObj.spawnEntityInWorld(bolt);
						}
					}
				}
				this.blizz.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
			} else {
				this.blizz.getNavigator().clearPathEntity();
				this.blizz.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
			}
			super.updateTask();
		}
	}

}
