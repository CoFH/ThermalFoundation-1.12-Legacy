package cofh.thermalfoundation.gui;

import cofh.core.block.TileCoFHBase;
import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalfoundation.gui.client.GuiLexicon;
import cofh.thermalfoundation.gui.client.GuiLexiconEmpowered;
import cofh.thermalfoundation.gui.container.ContainerLexicon;
import cofh.thermalfoundation.gui.container.ContainerLexiconEmpowered;
import cofh.thermalfoundation.item.ItemLexicon;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	public static final int TILE_ID = 0;
	public static final int LEXICON_ID = 1;
	public static final int LEXICON_EMP_ID = 2;

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

		switch (id) {
		case TILE_ID:
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof TileCoFHBase) {
				return ((TileCoFHBase) tile).getGuiClient(player.inventory);
			}
		case LEXICON_ID:
			if (ItemHelper.isPlayerHoldingItem(ItemLexicon.class, player)) {
				return new GuiLexicon(player.inventory, new ContainerLexicon(player.getCurrentEquippedItem(), player.inventory));
			}
		case LEXICON_EMP_ID:
			if (ItemHelper.isPlayerHoldingItem(ItemLexicon.class, player)) {
				return new GuiLexiconEmpowered(player.inventory, new ContainerLexiconEmpowered(player.getCurrentEquippedItem(), player.inventory));
			}
		default:
			return null;
		}
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

		switch (id) {
		case TILE_ID:
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof TileCoFHBase) {
				return ((TileCoFHBase) tile).getGuiServer(player.inventory);
			}
		case LEXICON_ID:
			if (ItemHelper.isPlayerHoldingItem(ItemLexicon.class, player)) {
				return new ContainerLexicon(player.getCurrentEquippedItem(), player.inventory);
			}
		case LEXICON_EMP_ID:
			if (ItemHelper.isPlayerHoldingItem(ItemLexicon.class, player)) {
				return new ContainerLexiconEmpowered(player.getCurrentEquippedItem(), player.inventory);
			}
		default:
			return null;
		}
	}

}
