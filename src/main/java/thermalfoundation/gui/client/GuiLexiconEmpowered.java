package thermalfoundation.gui.client;

import cofh.core.gui.GuiBaseAdv;
import cofh.lib.util.helpers.StringHelper;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import thermalfoundation.gui.container.ContainerLexiconEmpowered;

public class GuiLexiconEmpowered extends GuiBaseAdv {

	static final ResourceLocation TEXTURE = new ResourceLocation("thermalfoundation:textures/gui/LexiconConvert.png");

	public String myInfo = "";

	public GuiLexiconEmpowered(InventoryPlayer inventory, ContainerLexiconEmpowered container) {

		super(container);
		texture = TEXTURE;
		name = container.getInventoryName();
		allowUserInput = false;

		drawTitle = false;
		drawInventory = false;

		xSize = 206;
		ySize = 204;

		myInfo = StringHelper.localize("tab.thermalfoundation.lexicon.convert");
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
