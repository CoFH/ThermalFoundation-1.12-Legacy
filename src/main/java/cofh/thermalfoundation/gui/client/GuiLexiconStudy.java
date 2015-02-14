package cofh.thermalfoundation.gui.client;

import cofh.core.gui.GuiBaseAdv;
import cofh.core.gui.element.TabInfo;
import cofh.lib.gui.GuiColor;
import cofh.lib.gui.element.ElementListBox;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalfoundation.gui.container.ContainerLexiconStudy;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiLexiconStudy extends GuiBaseAdv {

	static final ResourceLocation TEXTURE = new ResourceLocation("thermalfoundation:textures/gui/LexiconStudy.png");

	public String myInfo = "";

	private ElementListBox oreList;

	public GuiLexiconStudy(InventoryPlayer inventory, ContainerLexiconStudy container) {

		super(container);
		texture = TEXTURE;
		name = "gui.thermalfoundation.lexicon.study";
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

		addTab(new TabInfo(this, myInfo).setOffsets(12, 10));

		oreList = new ElementListBox(this, 22, 26, 100, 164);
		oreList.borderColor = new GuiColor(120, 120, 120, 0).getColor();
		oreList.backgroundColor = new GuiColor(0, 0, 0, 32).getColor();

		addElement(oreList);

	}

	@Override
	protected void updateElementInformation() {

	}

	@Override
	public void handleElementButtonClick(String buttonName, int mouseButton) {

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {

		fontRendererObj.drawString(StringHelper.localize(name), getCenteredOffset(StringHelper.localize(name)), 16, 0xddbb1d);

		super.drawGuiContainerForegroundLayer(x, y);
	}

}
