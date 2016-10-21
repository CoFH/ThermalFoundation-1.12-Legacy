package cofh.thermalfoundation.block;

import codechicken.lib.inventory.InventoryUtils;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

public class ItemBlockOre extends ItemBlock {

    public ItemBlockOre(Block block) {

        super(block);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    @Override
    public String getItemStackDisplayName(ItemStack item) {

        return I18n.translateToLocal(getUnlocalizedName(item));
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {

        return "tile.thermalfoundation.ore." + BlockOre.NAMES[InventoryUtils.actualDamage(item)] + ".name";
    }

    @Override
    public int getMetadata(int i) {

        return i;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {

        return EnumRarity.values()[BlockOre.RARITY[InventoryUtils.actualDamage(stack)]];
    }

}
