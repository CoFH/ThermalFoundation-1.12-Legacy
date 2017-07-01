package cofh.thermalfoundation.block;

import cofh.core.block.ItemBlockCore;
import cofh.core.util.helpers.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemBlockGlassAlloy extends ItemBlockCore {

	public ItemBlockGlassAlloy(Block block) {

		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {

		return "tile.thermalfoundation.glass." + BlockGlassAlloy.Type.byMetadata(ItemHelper.getItemDamage(stack)).getName() + ".name";
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {

		return BlockGlassAlloy.Type.byMetadata(ItemHelper.getItemDamage(stack)).getRarity();
	}

}
