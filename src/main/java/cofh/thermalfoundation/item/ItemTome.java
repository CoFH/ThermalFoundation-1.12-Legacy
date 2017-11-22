package cofh.thermalfoundation.item;

import cofh.api.item.IInventoryContainerItem;
import cofh.api.item.IMultiModeItem;
import cofh.core.item.ItemMulti;
import cofh.core.key.KeyBindingItemMultiMode;
import cofh.core.util.CoreUtils;
import cofh.core.util.StateMapper;
import cofh.core.util.core.IInitializer;
import cofh.core.util.helpers.ItemHelper;
import cofh.core.util.helpers.ServerHelper;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.gui.GuiHandler;
import cofh.thermalfoundation.init.TFProps;
import cofh.thermalfoundation.util.LexiconManager;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

import static cofh.core.util.helpers.RecipeHelper.addShapedRecipe;

public class ItemTome extends ItemMulti implements IInitializer, IInventoryContainerItem, IMultiModeItem {

	public ItemTome() {

		super("thermalfoundation");

		setMaxStackSize(1);
		setUnlocalizedName("tome");
		setCreativeTab(ThermalFoundation.tabUtils);
	}

	protected boolean isEmpowered(ItemStack stack) {

		return getMode(stack) == 1;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		if (StringHelper.displayShiftForDetail && !StringHelper.isShiftKeyDown()) {
			tooltip.add(StringHelper.shiftForDetails());
		}
		if (!StringHelper.isShiftKeyDown()) {
			return;
		}
		switch (Type.values()[ItemHelper.getItemDamage(stack)]) {
			case LEXICON:
				tooltip.add(StringHelper.getInfoText("info.thermalfoundation.tome.lexicon.a.0"));

				if (isEmpowered(stack)) {
					tooltip.add(StringHelper.localize("info.thermalfoundation.tome.lexicon.c.0") + StringHelper.END);
					tooltip.add(StringHelper.localizeFormat("info.thermalfoundation.tome.lexicon.c.1", StringHelper.getKeyName(KeyBindingItemMultiMode.INSTANCE.getKey())));
				} else {
					tooltip.add(StringHelper.localize("info.thermalfoundation.tome.lexicon.b.0") + StringHelper.END);
					tooltip.add(StringHelper.localizeFormat("info.thermalfoundation.tome.lexicon.b.1", StringHelper.getKeyName(KeyBindingItemMultiMode.INSTANCE.getKey())));
				}
				break;
			case EXPERIENCE:
				tooltip.add(StringHelper.getInfoText("info.thermalfoundation.tome.experience.0"));
				tooltip.add(StringHelper.formatNumber(getExperience(stack)));
			default:
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isCurrentItem) {

		if (!isEmpowered(stack)) {
			return;
		}
		switch (Type.values()[ItemHelper.getItemDamage(stack)]) {
			case LEXICON:
				NBTTagCompound tag = entity.getEntityData();
				tag.setLong(TFProps.LEXICON_TIMER, entity.world.getTotalWorldTime());
				break;
			default:
		}
	}

	@Override
	public boolean isFull3D() {

		return true;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {

		if (isEmpowered(stack)) {
			return EnumRarity.RARE;
		}
		return EnumRarity.UNCOMMON;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

		ItemStack stack = player.getHeldItem(hand);
		if (CoreUtils.isFakePlayer(player) || hand != EnumHand.MAIN_HAND) {
			return new ActionResult<>(EnumActionResult.FAIL, stack);
		}
		switch (Type.values()[ItemHelper.getItemDamage(stack)]) {
			case LEXICON:
				if (ServerHelper.isServerWorld(world) && LexiconManager.getSortedOreNames().size() > 0) {
					if (isEmpowered(stack)) {
						player.openGui(ThermalFoundation.instance, GuiHandler.LEXICON_TRANSMUTE_ID, world, 0, 0, 0);
					} else {
						player.openGui(ThermalFoundation.instance, GuiHandler.LEXICON_STUDY_ID, world, 0, 0, 0);
					}
				}
				break;
			case EXPERIENCE:
				if (player.experienceLevel > 0 && getExperience(stack) < TFProps.MAX_EXP) {
					player.experienceLevel -= 1;
					modifyExperience(stack, player.xpBarCap());
				}
			default:
		}
		return new ActionResult<>(EnumActionResult.FAIL, stack);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		return EnumActionResult.FAIL;
	}

	private int modifyExperience(ItemStack stack, int exp) {

		int storedExp = getExperience(stack) + exp;

		if (storedExp > TFProps.MAX_EXP) {
			storedExp = TFProps.MAX_EXP;
		} else if (storedExp < 0) {
			storedExp = 0;
		}
		stack.getTagCompound().setInteger("Experience", storedExp);
		return storedExp;
	}

	private int getExperience(ItemStack stack) {

		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		return stack.getTagCompound().getInteger("Experience");
	}

	/* IModelRegister */
	@Override
	@SideOnly (Side.CLIENT)
	public void registerModels() {

		StateMapper mapper = new StateMapper(modName, "util", name);
		ModelBakery.registerItemVariants(this);
		ModelLoader.setCustomMeshDefinition(this, mapper);

		for (Map.Entry<Integer, ItemEntry> entry : itemMap.entrySet()) {
			ModelLoader.setCustomModelResourceLocation(this, entry.getKey(), new ModelResourceLocation(modName + ":" + "util", "type=" + entry.getValue().name));
		}
	}

	/* IInventoryContainerItem */
	@Override
	public int getSizeInventory(ItemStack container) {

		switch (Type.values()[ItemHelper.getItemDamage(container)]) {
			case LEXICON:
				return 3;
			default:
				return 0;
		}
	}

	/* IMultiModeItem */
	@Override
	public int getMode(ItemStack stack) {

		return !stack.hasTagCompound() ? 0 : stack.getTagCompound().getInteger("Mode");
	}

	@Override
	public boolean setMode(ItemStack stack, int mode) {

		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setInteger("Mode", mode);
		return false;
	}

	@Override
	public boolean incrMode(ItemStack stack) {

		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		int curMode = getMode(stack);
		curMode++;
		if (curMode >= getNumModes(stack)) {
			curMode = 0;
		}
		stack.getTagCompound().setInteger("Mode", curMode);
		return true;
	}

	@Override
	public boolean decrMode(ItemStack stack) {

		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		int curMode = getMode(stack);
		curMode--;
		if (curMode <= 0) {
			curMode = getNumModes(stack) - 1;
		}
		stack.getTagCompound().setInteger("Mode", curMode);
		return true;
	}

	@Override
	public int getNumModes(ItemStack stack) {

		return 2;
	}

	@Override
	public void onModeChange(EntityPlayer player, ItemStack stack) {

		if (isEmpowered(stack)) {
			player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.PLAYERS, 0.4F, 1.0F);
		} else {
			player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.2F, 0.6F);
		}
	}

	/* IInitializer */
	@Override
	public boolean initialize() {

		tomeLexicon = addItem(0, "lexicon");
		tomeExperience = addItem(1, "experience");

		ThermalFoundation.proxy.addIModelRegister(this);

		return true;
	}

	@Override
	public boolean register() {

		addShapedRecipe(tomeLexicon, " L ", "GBI", " R ", 'B', Items.BOOK, 'G', "ingotGold", 'I', "ingotIron", 'L', "gemLapis", 'R', "dustRedstone");
		addShapedRecipe(tomeExperience, " L ", "EBE", " L ", 'B', Items.BOOK, 'E', "gemEmerald", 'L', "gemLapis");

		return true;
	}

	/* REFERENCES */
	public static ItemStack tomeLexicon;
	public static ItemStack tomeExperience;

	/* TYPE */
	enum Type {
		LEXICON, EXPERIENCE
	}

}
