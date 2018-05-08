package cofh.thermalfoundation.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public abstract class EntityElementalCube extends EntitySlime {

	public EntityElementalCube(World world) {

		super(world);
	}

	@Override
	protected void applyEntityAttributes() {

		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
	}

	@Override
	protected void alterSquishAmount() {

		this.squishAmount *= 0.9F;
	}

	@Override
	protected void jump() {

		this.motionY = (double) (0.42F + (float) this.getSlimeSize() * 0.1F);
		this.isAirBorne = true;
		net.minecraftforge.common.ForgeHooks.onLivingJump(this);
	}

	@Override
	protected void setSlimeSize(int size, boolean resetHealth) {

		super.setSlimeSize(size, resetHealth);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue((double) (size * 3));
	}

	@Override
	protected boolean canDamagePlayer() {

		return true;
	}

	@Override
	protected int getAttackStrength() {

		return super.getAttackStrength() + 2;
	}

	@Override
	protected int getJumpDelay() {

		return super.getJumpDelay() * 4;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {

		return this.isSmallSlime() ? SoundEvents.ENTITY_SMALL_MAGMACUBE_HURT : SoundEvents.ENTITY_MAGMACUBE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {

		return this.isSmallSlime() ? SoundEvents.ENTITY_SMALL_MAGMACUBE_DEATH : SoundEvents.ENTITY_MAGMACUBE_DEATH;
	}

	@Override
	protected SoundEvent getSquishSound() {

		return this.isSmallSlime() ? SoundEvents.ENTITY_SMALL_MAGMACUBE_SQUISH : SoundEvents.ENTITY_MAGMACUBE_SQUISH;
	}

	@Override
	protected SoundEvent getJumpSound() {

		return SoundEvents.ENTITY_MAGMACUBE_JUMP;
	}

	@Override
	public void fall(float distance, float damageMultiplier) {

	}

	@Override
	public boolean getCanSpawnHere() {

		return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
	}

	@Override
	public boolean isNotColliding() {

		return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(this.getEntityBoundingBox());
	}

}
