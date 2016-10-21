package cofh.thermalfoundation.gui;

import cofh.thermalfoundation.block.BlockOre;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TFCreativeTab extends CreativeTabs {

	private final String label;

	public TFCreativeTab() {

		this("");
	}

	public TFCreativeTab(String label) {

		super("ThermalFoundation" + label);
		this.label = label;
	}

	protected ItemStack getStack() {

		return BlockOre.oreCopper;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack() {

		return getStack();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {

		return getIconItemStack().getItem();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTabLabel() {

		return "thermalfoundation.creativeTab" + label;
	}

}
