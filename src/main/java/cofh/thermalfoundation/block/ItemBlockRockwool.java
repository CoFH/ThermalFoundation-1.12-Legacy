package cofh.thermalfoundation.block;

import cofh.core.block.ItemBlockCore;
import cofh.core.util.helpers.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockRockwool extends ItemBlockCore {

	public ItemBlockRockwool(Block block) {

		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {

		return "tile.thermalfoundation.rockwool." + BlockRockwool.Type.byMetadata(ItemHelper.getItemDamage(stack)).getName() + ".name";
	}

}
