package cofh.thermalfoundation.block;

import cofh.core.item.ItemBlockCoFHBase;
import cofh.lib.util.helpers.ItemHelper;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemBlockStorage extends ItemBlockCoFHBase {

	public ItemBlockStorage(Block block) {

		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {

		return "tile.thermalfoundation.storage." + BlockStorage.Type.byMetadata(ItemHelper.getItemDamage(stack)).getName() + ".name";
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {

		return BlockStorage.Type.byMetadata(ItemHelper.getItemDamage(stack)).getRarity();
	}

}
