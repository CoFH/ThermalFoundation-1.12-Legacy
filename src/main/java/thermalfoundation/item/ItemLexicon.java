package thermalfoundation.item;

import cofh.api.item.IEmpowerableItem;
import cofh.api.item.IInventoryContainerItem;
import cofh.core.item.ItemBase;
import cofh.core.util.CoreUtils;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.ServerHelper;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import thermalfoundation.ThermalFoundation;
import thermalfoundation.gui.GuiHandler;

public class ItemLexicon extends ItemBase implements IInventoryContainerItem, IEmpowerableItem {

	public ItemLexicon() {

		super("thermalfoundation");
		setMaxStackSize(1);
		setCreativeTab(ThermalFoundation.tab);
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {

		list.add(new ItemStack(item, 1, 0));
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {

		if (isEmpowered(stack)) {
			return EnumRarity.rare;
		}
		return EnumRarity.uncommon;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {

		if (isEmpowered(stack)) {
			return "item.thermalfoundation.tome.lexiconEmpowered";
		}
		return "item.thermalfoundation.tome.lexicon";
	}

	@Override
	public boolean isFull3D() {

		return true;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean check) {

		list.add("UNIMPLEMENTED. This is a beta. ~KL");

		// if (StringHelper.displayShiftForDetail && !StringHelper.isShiftKeyDown()) {
		// list.add(StringHelper.shiftForDetails());
		// }
		// if (!StringHelper.isShiftKeyDown()) {
		// return;
		// }
		// list.add(StringHelper.getInfoText("info.thermalfoundation.tome.lexicon.1"));
		//
		// if (isEmpowered(stack)) {
		// list.add(StringHelper.localize("info.thermalfoundation.tome.lexicon.4") + StringHelper.END);
		// list.add(StringHelper.YELLOW + StringHelper.ITALIC + StringHelper.localize("info.cofh.press") + " "
		// + Keyboard.getKeyName(KeyBindingEmpower.instance.getKey()) + " " + StringHelper.localize("info.thermalfoundation.tome.lexicon.5")
		// + StringHelper.END);
		// } else {
		// list.add(StringHelper.localize("info.thermalfoundation.tome.lexicon.2") + StringHelper.END);
		// list.add(StringHelper.BRIGHT_BLUE + StringHelper.ITALIC + StringHelper.localize("info.cofh.press") + " "
		// + Keyboard.getKeyName(KeyBindingEmpower.instance.getKey()) + " " + StringHelper.localize("info.thermalfoundation.tome.lexicon.3")
		// + StringHelper.END);
		// }
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		if (CoreUtils.isFakePlayer(player)) {
			return stack;
		}
		if (ServerHelper.isServerWorld(world)) {
			if (isEmpowered(stack)) {
				player.openGui(ThermalFoundation.instance, GuiHandler.LEXICON_EMP_ID, world, 0, 0, 0);
			} else {
				player.openGui(ThermalFoundation.instance, GuiHandler.LEXICON_ID, world, 0, 0, 0);
			}
		}
		return stack;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isCurrentItem) {

		if (ItemHelper.getItemDamage(stack) == 0) {
			return;
		}
		NBTTagCompound tag = entity.getEntityData();
		tag.setLong("LexiconUpdate", entity.worldObj.getTotalWorldTime());
	}

	/* IInventoryContainerItem */
	@Override
	public int getSizeInventory(ItemStack container) {

		return 3;
	}

	/* IEmpowerableItem */
	@Override
	public boolean isEmpowered(ItemStack stack) {

		return stack.stackTagCompound == null ? false : stack.stackTagCompound.getBoolean("Empowered");
	}

	@Override
	public boolean setEmpoweredState(ItemStack stack, boolean state) {

		if (stack.stackTagCompound == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.stackTagCompound.setBoolean("Empowered", state);
		return true;
	}

	@Override
	public void onStateChange(EntityPlayer player, ItemStack stack) {

		if (isEmpowered(stack)) {
			player.worldObj.playSoundAtEntity(player, "ambient.weather.thunder", 0.4F, 1.0F);
		} else {
			player.worldObj.playSoundAtEntity(player, "random.orb", 0.2F, 0.6F);
		}
	}

}
