package cofh.thermalfoundation.gui;

import cofh.core.block.TileCoFHBase;
import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalfoundation.gui.client.GuiLexiconStudy;
import cofh.thermalfoundation.gui.client.GuiLexiconTransmute;
import cofh.thermalfoundation.gui.container.ContainerLexiconStudy;
import cofh.thermalfoundation.gui.container.ContainerLexiconTransmute;
import cofh.thermalfoundation.item.ItemLexicon;
import cpw.mods.fml.common.network.IGuiHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	public static final int TILE_ID = 0;
	public static final int LEXICON_STUDY_ID = 1;
	public static final int LEXICON_TRANSMUTE_ID = 2;

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

		switch (id) {
		case TILE_ID:
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof TileCoFHBase) {
				return ((TileCoFHBase) tile).getGuiClient(player.inventory);
			}
		case LEXICON_STUDY_ID:
			if (ItemHelper.isPlayerHoldingItem(ItemLexicon.class, player)) {
				return new GuiLexiconStudy(player.inventory, new ContainerLexiconStudy(player.getCurrentEquippedItem(), player.inventory));
			}
		case LEXICON_TRANSMUTE_ID:
			if (ItemHelper.isPlayerHoldingItem(ItemLexicon.class, player)) {
				return new GuiLexiconTransmute(player.inventory, new ContainerLexiconTransmute(player.inventory));
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
		case LEXICON_STUDY_ID:
			if (ItemHelper.isPlayerHoldingItem(ItemLexicon.class, player)) {
				return new ContainerLexiconStudy(player.getCurrentEquippedItem(), player.inventory);
			}
		case LEXICON_TRANSMUTE_ID:
			if (ItemHelper.isPlayerHoldingItem(ItemLexicon.class, player)) {
				return new ContainerLexiconTransmute(player.inventory);
			}
		default:
			return null;
		}
	}

}
