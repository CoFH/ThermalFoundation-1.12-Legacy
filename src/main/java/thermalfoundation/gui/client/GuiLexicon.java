package thermalfoundation.gui.client;

import cofh.gui.GuiBaseAdv;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import thermalfoundation.gui.container.ContainerLexicon;

public class GuiLexicon extends GuiBaseAdv {

	static final String INFO = "Consult the knowledge of the world and alter your personal destiny.";
	static final ResourceLocation TEXTURE = new ResourceLocation("thermalfoundation:textures/gui/LexiconStudy.png");

	public GuiLexicon(InventoryPlayer inventory, ContainerLexicon container) {

		super(container);
		texture = TEXTURE;
		name = container.getInventoryName();
		allowUserInput = false;

		drawTitle = false;
		drawInventory = false;

		xSize = 206;
		ySize = 204;
	}

	@Override
	public void initGui() {

		super.initGui();
	}

	@Override
	protected void updateElementInformation() {

	}

}
