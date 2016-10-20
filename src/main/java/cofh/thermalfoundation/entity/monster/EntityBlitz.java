package cofh.thermalfoundation.entity.monster;

import cofh.core.CoFHProps;
import cofh.core.util.CoreUtils;
import cofh.lib.util.helpers.MathHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.entity.projectile.EntityBlitzBolt;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	static class AIBlitzballAttack extends AIElementalboltAttack {

		public AIBlitzballAttack(EntityElemental entity) {

			super(entity);
		}

		@Override
		protected EntityThrowable getBolt(World world, EntityElemental elemental) {

			return new EntityBlitzBolt(world, elemental);
		}

		@Override
		protected SoundEvent getAttackSound() {

			return attackSound;
		}
	}
}
