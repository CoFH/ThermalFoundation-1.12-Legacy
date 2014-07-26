package thermalfoundation.gui.client;

import cofh.gui.GuiBaseAdv;
import cofh.util.StringHelper;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import thermalfoundation.gui.container.ContainerLexicon;

public class GuiLexicon extends GuiBaseAdv {

	static final ResourceLocation TEXTURE = new ResourceLocation("thermalfoundation:textures/gui/LexiconStudy.png");

	public String myInfo = "";

	public GuiLexicon(InventoryPlayer inventory, ContainerLexicon container) {

		super(container);
		texture = TEXTURE;
		name = container.getInventoryName();
		allowUserInput = false;

		drawTitle = false;
		drawInventory = false;

		xSize = 206;
		ySize = 204;

		myInfo = StringHelper.localize("tab.thermalfoundation.lexicon.study");
	}

	@Override
	public void initGui() {

		super.initGui();

		// addTab(new TabInfo(this, myInfo));
	}

	@Override
	protected void updateElementInformation() {

	}

}
