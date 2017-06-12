package cofh.thermalfoundation.block;

import cofh.core.block.ItemBlockCore;
import cofh.lib.util.helpers.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemBlockOreFluid extends ItemBlockCore {

	public ItemBlockOreFluid(Block block) {

		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {

		return "tile.thermalfoundation.ore." + BlockOreFluid.Type.byMetadata(ItemHelper.getItemDamage(stack)).getName() + ".name";
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {

		return BlockOreFluid.Type.byMetadata(ItemHelper.getItemDamage(stack)).getRarity();
	}

}
