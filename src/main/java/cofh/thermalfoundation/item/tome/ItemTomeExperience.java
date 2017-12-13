package cofh.thermalfoundation.item.tome;

import cofh.api.fluid.IFluidContainerItem;
import cofh.core.init.CoreEnchantments;
import cofh.core.init.CoreProps;
import cofh.core.item.IEnchantableItem;
import cofh.core.key.KeyBindingItemMultiMode;
import cofh.core.util.CoreUtils;
import cofh.core.util.capabilities.FluidContainerItemWrapper;
import cofh.core.util.helpers.ItemHelper;
import cofh.core.util.helpers.MathHelper;
import cofh.core.util.helpers.ServerHelper;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.init.TFFluids;
import cofh.thermalfoundation.init.TFProps;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;

import static cofh.core.util.helpers.RecipeHelper.addShapedRecipe;

public class ItemTomeExperience extends ItemTome implements IFluidContainerItem, IEnchantableItem {

	public ItemTomeExperience() {

		super();

		setUnlocalizedName("experience");
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		if (StringHelper.displayShiftForDetail && !StringHelper.isShiftKeyDown()) {
			tooltip.add(StringHelper.shiftForDetails());
		}
		if (!StringHelper.isShiftKeyDown()) {
			return;
		}
		tooltip.add(StringHelper.getInfoText("info.thermalfoundation.tome.experience.0"));
		tooltip.add(StringHelper.localize("info.thermalfoundation.tome.experience.1"));
		tooltip.add(StringHelper.getNoticeText("info.thermalfoundation.tome.experience.2"));
		tooltip.add(StringHelper.localizeFormat("info.thermalfoundation.tome.experience.a." + (isEmpowered(stack) ? 1 : 0), StringHelper.getKeyName(KeyBindingItemMultiMode.INSTANCE.getKey())));
		tooltip.add(StringHelper.localize("info.cofh.experience") + ": " + StringHelper.formatNumber(getExperience(stack)) + " / " + StringHelper.formatNumber(getMaxExperience(stack)));
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isCurrentItem) {

		if (!isEmpowered(stack)) {
			return;
		}
		NBTTagCompound tag = entity.getEntityData();
		tag.setLong(TFProps.EXPERIENCE_TIMER, entity.world.getTotalWorldTime());
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {

		return true;
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {

		return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged) && (slotChanged || !ItemHelper.areItemStacksEqualIgnoreTags(oldStack, newStack, "Experience"));
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {

		return true;
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {

		return 10;
	}

	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {

		return CoreProps.RGB_DURABILITY_EXP;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {

		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		return 1D - ((double) getExperience(stack) / (double) getMaxExperience(stack));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

		ItemStack stack = player.getHeldItem(hand);
		if (CoreUtils.isFakePlayer(player) || hand != EnumHand.MAIN_HAND || ServerHelper.isClientWorld(world)) {
			return new ActionResult<>(EnumActionResult.FAIL, stack);
		}
		if (player.isSneaking()) {
			int exp = getExperience(stack);
			setPlayerExperience(player, player.experienceTotal + exp);
			modifyExperience(stack, -exp);
		} else {
			int exp = Math.min(player.experienceTotal, getSpace(stack));
			setPlayerExperience(player, player.experienceTotal - exp);
			modifyExperience(stack, exp);
		}
		return new ActionResult<>(EnumActionResult.FAIL, stack);
	}

	/* HELPERS */
	public static int getExperience(ItemStack stack) {

		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		return stack.getTagCompound().getInteger("Experience");
	}

	public static int getSpace(ItemStack stack) {

		return getMaxExperience(stack) - getExperience(stack);
	}

	public static int getMaxExperience(ItemStack stack) {

		return CAPACITY + CAPACITY * EnchantmentHelper.getEnchantmentLevel(CoreEnchantments.holding, stack) / 2;
	}

	public static int modifyExperience(ItemStack stack, int exp) {

		int storedExp = getExperience(stack) + exp;

		if (storedExp > getMaxExperience(stack)) {
			storedExp = getMaxExperience(stack);
		} else if (storedExp < 0) {
			storedExp = 0;
		}
		stack.getTagCompound().setInteger("Experience", storedExp);
		return storedExp;
	}

	public static void setPlayerExperience(EntityPlayer player, int exp) {

		player.experienceLevel = 0;
		player.experience = 0.0F;
		player.experienceTotal = 0;

		addExperienceToPlayer(player, exp);
	}

	public static void addExperienceToPlayer(EntityPlayer player, int exp) {

		int i = Integer.MAX_VALUE - player.experienceTotal;

		if (exp > i) {
			exp = i;
		}
		player.experience += (float) exp / (float) player.xpBarCap();
		for (player.experienceTotal += exp; player.experience >= 1.0F; player.experience /= (float) player.xpBarCap()) {
			player.experience = (player.experience - 1.0F) * (float) player.xpBarCap();
			addExperienceLevelToPlayer(player, 1);
		}
	}

	public static void addExperienceLevelToPlayer(EntityPlayer player, int levels) {

		player.experienceLevel += levels;

		if (player.experienceLevel < 0) {
			player.experienceLevel = 0;
			player.experience = 0.0F;
			player.experienceTotal = 0;
		}
	}

	public static int getTotalExpForLevel(int level) {

		return level >= 32 ? (9 * level * level - 325 * level + 4440) / 2 : level >= 17 ? (5 * level * level - 81 * level + 720) / 2 : (level * level + 6 * level);
	}

	public static boolean onXPPickup(PlayerPickupXpEvent event, ItemStack stack) {

		EntityXPOrb orb = event.getOrb();
		int toAdd = Math.min(getSpace(stack), orb.xpValue);

		if (toAdd > 0) {
			stack.setAnimationsToGo(5);
			EntityPlayer player = event.getEntityPlayer();
			player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.1F, (MathHelper.RANDOM.nextFloat() - MathHelper.RANDOM.nextFloat()) * 0.35F + 0.9F);

			ItemTomeExperience.modifyExperience(stack, toAdd);
			orb.xpValue -= toAdd;
			if (orb.xpValue <= 0) {
				orb.setDead();
			}
		}
		return orb.isDead;
	}

	/* IFluidContainerItem */
	@Override
	public FluidStack getFluid(ItemStack container) {

		int experience = getExperience(container);
		return experience > 0 ? new FluidStack(TFFluids.fluidExperience, experience * CoreProps.MB_PER_XP) : null;
	}

	@Override
	public int getCapacity(ItemStack container) {

		return getMaxExperience(container) * CoreProps.MB_PER_XP;
	}

	@Override
	public int fill(ItemStack container, FluidStack resource, boolean doFill) {

		if (resource == null || resource.getFluid() != TFFluids.fluidExperience) {
			return 0;
		}
		int experience = getExperience(container);
		int filled = Math.min(getMaxExperience(container) - experience, resource.amount / CoreProps.MB_PER_XP);

		if (doFill) {
			modifyExperience(container, filled);
		}
		return filled * CoreProps.MB_PER_XP;
	}

	@Override
	public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {

		int experience = getExperience(container);
		if (experience <= 0) {
			return null;
		}
		int drained = Math.min(experience, maxDrain / CoreProps.MB_PER_XP);

		if (doDrain) {
			modifyExperience(container, -drained);
		}
		return new FluidStack(TFFluids.fluidExperience, drained * CoreProps.MB_PER_XP);
	}

	/* IEnchantableItem */
	@Override
	public boolean canEnchant(ItemStack stack, Enchantment enchantment) {

		return enchantment == CoreEnchantments.holding;
	}

	/* CAPABILITIES */
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {

		return new FluidContainerItemWrapper(stack, this, false, true);
	}

	/* IInitializer */
	@Override
	public boolean initialize() {

		tomeExperience = new ItemStack(this);

		ThermalFoundation.proxy.addIModelRegister(this);

		return true;
	}

	@Override
	public boolean register() {

		addShapedRecipe(tomeExperience, " L ", "EBE", " L ", 'B', Items.BOOK, 'E', "gemEmerald", 'L', "gemLapis");

		return true;
	}

	public static final int CAPACITY = 10000;

	/* REFERENCES */
	public static ItemStack tomeExperience;

}
