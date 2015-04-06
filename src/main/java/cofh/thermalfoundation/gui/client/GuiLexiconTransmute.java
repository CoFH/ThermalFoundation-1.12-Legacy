package cofh.thermalfoundation.gui.client;

import cofh.core.gui.GuiBaseAdv;
import cofh.core.gui.element.TabInfo;
import cofh.core.util.oredict.OreDictionaryArbiter;
import cofh.lib.gui.element.ElementButton;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalfoundation.gui.container.ContainerLexiconTransmute;
import cofh.thermalfoundation.network.PacketTFBase;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiLexiconTransmute extends GuiBaseAdv {

	static final String TEX_PATH = "thermalfoundation:textures/gui/LexiconTransmute.png";
	static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

	public String myInfo = "";

	ElementButton prevOre;
	ElementButton nextOre;
	ElementButton prevName;
	ElementButton nextName;
	ElementButton transmute;

	ContainerLexiconTransmute lexicon;

	public GuiLexiconTransmute(InventoryPlayer inventory, ContainerLexiconTransmute container) {

		super(container);
		lexicon = container;
		texture = TEXTURE;
		name = "gui.thermalfoundation.lexicon.transmute";
		allowUserInput = false;

		drawTitle = false;
		drawInventory = false;

		xSize = 206;
		ySize = 204;

		myInfo = StringHelper.localize("tab.thermalfoundation.lexicon.transmute.0") + "\n\n"
				+ StringHelper.localize("tab.thermalfoundation.lexicon.transmute.1");
	}

	@Override
	public void initGui() {

		super.initGui();

		addTab(new TabInfo(this, myInfo).setOffsets(12, 10));

		prevOre = new ElementButton(this, 57, 31, "PrevOre", 206, 0, 206, 20, 206, 40, 20, 20, TEX_PATH).setToolTipLocalized(true);
		nextOre = new ElementButton(this, 129, 31, "NextOre", 226, 0, 226, 20, 226, 40, 20, 20, TEX_PATH).setToolTipLocalized(true);
		prevName = new ElementButton(this, 20, 81, "PrevName", 206, 0, 206, 20, 206, 40, 20, 20, TEX_PATH).setToolTipLocalized(true);
		nextName = new ElementButton(this, 166, 81, "NextName", 226, 0, 226, 20, 226, 40, 20, 20, TEX_PATH).setToolTipLocalized(true);
		transmute = new ElementButton(this, 83, 59, "Transmute", 206, 60, 206, 80, 206, 100, 40, 20, TEX_PATH).setToolTipLocalized(true);

		addElement(prevOre);
		addElement(nextOre);
		addElement(prevName);
		addElement(nextName);
		addElement(transmute);
	}

	@Override
	protected void updateElementInformation() {

		if (lexicon.hasMultipleOres()) {
			prevOre.setActive();
			nextOre.setActive();
		} else {
			prevOre.setDisabled();
			nextOre.setDisabled();
		}
		if (lexicon.hasMultipleNames()) {
			prevName.setActive();
			nextName.setActive();
		} else {
			prevName.setDisabled();
			nextName.setDisabled();
		}
		if (lexicon.canTransmute()) {
			transmute.setActive();
		} else {
			transmute.setDisabled();
		}
	}

	@Override
	public void handleElementButtonClick(String buttonName, int mouseButton) {

		if (buttonName.equalsIgnoreCase("PrevOre")) {
			PacketTFBase.sendLexiconTransmutePacketToServer(ContainerLexiconTransmute.ORE_PREV);
		} else if (buttonName.equalsIgnoreCase("NextOre")) {
			PacketTFBase.sendLexiconTransmutePacketToServer(ContainerLexiconTransmute.ORE_NEXT);
		} else if (buttonName.equalsIgnoreCase("PrevName")) {
			PacketTFBase.sendLexiconTransmutePacketToServer(ContainerLexiconTransmute.NAME_PREV);
		} else if (buttonName.equalsIgnoreCase("NextName")) {
			PacketTFBase.sendLexiconTransmutePacketToServer(ContainerLexiconTransmute.NAME_NEXT);
		} else if (buttonName.equalsIgnoreCase("Transmute")) {
			PacketTFBase.sendLexiconTransmutePacketToServer(ContainerLexiconTransmute.TRANSMUTE);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {

		fontRendererObj.drawString(StringHelper.localize(name), getCenteredOffset(StringHelper.localize(name)), 16, 0xddbb1d);
		// 0xd2c0a3
		if (lexicon != null) {
			String oreName = lexicon.getOreName();
			if (!oreName.equals(OreDictionaryArbiter.UNKNOWN)) {
				fontRendererObj.drawString(oreName, getCenteredOffset(oreName), 88, 0xffffff);
			}
		}
		super.drawGuiContainerForegroundLayer(x, y);
	}

}
