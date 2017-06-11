package cofh.thermalfoundation.block;

import cofh.core.block.ItemBlockCore;
import cofh.lib.util.helpers.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemBlockStorageAlloy extends ItemBlockCore {

	public ItemBlockStorageAlloy(Block block) {

		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {

		return "tile.thermalfoundation.storage." + BlockStorageAlloy.Type.byMetadata(ItemHelper.getItemDamage(stack)).getName() + ".name";
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {

		return BlockStorageAlloy.Type.byMetadata(ItemHelper.getItemDamage(stack)).getRarity();
	}

}
