package cofh.thermalfoundation.gui;

import cofh.core.block.TileCore;
import cofh.core.util.helpers.ItemHelper;
import cofh.thermalfoundation.gui.client.GuiLexiconStudy;
import cofh.thermalfoundation.gui.client.GuiLexiconTransmute;
import cofh.thermalfoundation.gui.container.ContainerLexiconStudy;
import cofh.thermalfoundation.gui.container.ContainerLexiconTransmute;
import cofh.thermalfoundation.init.TFItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int TILE_ID = 0;
	public static final int LEXICON_STUDY_ID = 1;
	public static final int LEXICON_TRANSMUTE_ID = 2;

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

		switch (id) {
			case TILE_ID:
				TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
				if (tile instanceof TileCore) {
					return ((TileCore) tile).getGuiClient(player.inventory);
				}
				return null;
			case LEXICON_STUDY_ID:
				if (ItemHelper.isPlayerHoldingMainhand(TFItems.itemTome, player)) {
					return new GuiLexiconStudy(player.inventory, new ContainerLexiconStudy(player.getHeldItemMainhand(), player.inventory));
				}
				return null;
			case LEXICON_TRANSMUTE_ID:
				if (ItemHelper.isPlayerHoldingMainhand(TFItems.itemTome, player)) {
					return new GuiLexiconTransmute(player.inventory, new ContainerLexiconTransmute(player.inventory));
				}
				return null;
			default:
				return null;
		}
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

		switch (id) {
			case TILE_ID:
				TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
				if (tile instanceof TileCore) {
					return ((TileCore) tile).getGuiServer(player.inventory);
				}
				return null;
			case LEXICON_STUDY_ID:
				if (ItemHelper.isPlayerHoldingMainhand(TFItems.itemTome, player)) {
					return new ContainerLexiconStudy(player.getHeldItemMainhand(), player.inventory);
				}
				return null;
			case LEXICON_TRANSMUTE_ID:
				if (ItemHelper.isPlayerHoldingMainhand(TFItems.itemTome, player)) {
					return new ContainerLexiconTransmute(player.inventory);
				}
				return null;
			default:
				return null;
		}
	}

}
