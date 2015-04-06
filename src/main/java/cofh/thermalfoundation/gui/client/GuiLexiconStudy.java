package cofh.thermalfoundation.gui.client;

import cofh.core.gui.GuiBaseAdv;
import cofh.core.gui.element.TabInfo;
import cofh.lib.gui.GuiColor;
import cofh.lib.gui.element.ElementButton;
import cofh.lib.gui.element.ElementListBox;
import cofh.lib.gui.element.listbox.IListBoxElement;
import cofh.lib.gui.element.listbox.ListBoxElementText;
import cofh.lib.gui.element.listbox.SliderVertical;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalfoundation.gui.container.ContainerLexiconStudy;
import cofh.thermalfoundation.network.PacketTFBase;
import cofh.thermalfoundation.util.LexiconManager;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiLexiconStudy extends GuiBaseAdv {

	static final String TEX_PATH = "thermalfoundation:textures/gui/LexiconStudy.png";
	static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

	public String myInfo = "";

	ElementListBox oreList = new ElementListBox(this, 22, 84, 162, 104) {

		@Override
		protected void onSelectionChanged(int newIndex, IListBoxElement newElement) {

			lexicon.onSelectionChanged((String) newElement.getValue());
		}

		@Override
		public boolean onMouseWheel(int mouseX, int mouseY, int movement) {

			if (!StringHelper.isControlKeyDown()) {
				oreSlider.onMouseWheel(mouseX, mouseY, movement);
			}
			return super.onMouseWheel(mouseX, mouseY, movement);
		}
	};
	SliderVertical oreSlider;

	ElementButton prevOre;
	ElementButton nextOre;
	ElementButton setPreferredOre;
	ElementButton clearPreferredOre;

	ContainerLexiconStudy lexicon;

	public GuiLexiconStudy(InventoryPlayer inventory, ContainerLexiconStudy container) {

		super(container);
		lexicon = container;
		texture = TEXTURE;
		name = "gui.thermalfoundation.lexicon.study";
		allowUserInput = false;

		drawTitle = false;
		drawInventory = false;

		xSize = 206;
		ySize = 204;

		myInfo = StringHelper.localize("tab.thermalfoundation.lexicon.study.0") + "\n\n" + StringHelper.localize("tab.thermalfoundation.lexicon.study.1");
	}

	@Override
	public void initGui() {

		super.initGui();

		addTab(new TabInfo(this, myInfo).setOffsets(12, 10));

		prevOre = new ElementButton(this, 57, 31, "PrevOre", 206, 0, 206, 20, 206, 40, 20, 20, TEX_PATH).setToolTipLocalized(true);
		nextOre = new ElementButton(this, 129, 31, "NextOre", 226, 0, 226, 20, 226, 40, 20, 20, TEX_PATH).setToolTipLocalized(true);
		setPreferredOre = new ElementButton(this, 78, 59, "SetPreferred", 206, 60, 206, 80, 206, 100, 20, 20, TEX_PATH).setToolTipLocalized(true);
		clearPreferredOre = new ElementButton(this, 108, 59, "ClearPreferred", 226, 60, 226, 80, 226, 100, 20, 20, TEX_PATH).setToolTipLocalized(true);

		oreList.borderColor = new GuiColor(120, 120, 120, 0).getColor();
		oreList.backgroundColor = new GuiColor(0, 0, 0, 48).getColor();

		addElement(prevOre);
		addElement(nextOre);
		addElement(setPreferredOre);
		addElement(clearPreferredOre);

		addElement(oreList);

		for (String oreName : LexiconManager.getSortedOreNames()) {
			oreList.add(new ListBoxElementText(oreName));
		}
		lexicon.onSelectionChanged((String) oreList.getSelectedElement().getValue());

		oreSlider = new SliderVertical(this, 184, 85, 8, 102, oreList.getElementCount() - 10) {

			@Override
			public void onValueChanged(int value) {

				oreList.scrollToV(value);

				if (!_isDragging) {
					onStopDragging();
				}
			}
		};
		addElement(oreSlider);
	}

	@Override
	protected void updateElementInformation() {

		if (lexicon.hasMultipleOres()) {
			prevOre.setActive();
			nextOre.setActive();
		} else {
			prevOre.setDisabled();
			nextOre.setDisabled();
			prevOre.setToolTip("info.thermalfoundation.lexicon.singleEntry");
			nextOre.setToolTip("info.thermalfoundation.lexicon.singleEntry");
		}
		if (lexicon.canSetPreferred()) {
			setPreferredOre.setActive();
		} else {
			setPreferredOre.setDisabled();
		}
		if (lexicon.hasPreferredOre()) {
			clearPreferredOre.setActive();
		} else {
			clearPreferredOre.setDisabled();
		}
	}

	@Override
	public void handleElementButtonClick(String buttonName, int mouseButton) {

		if (buttonName.equalsIgnoreCase("PrevOre")) {
			PacketTFBase.sendLexiconStudyPacketToServer(ContainerLexiconStudy.ORE_PREV);
		} else if (buttonName.equalsIgnoreCase("NextOre")) {
			PacketTFBase.sendLexiconStudyPacketToServer(ContainerLexiconStudy.ORE_NEXT);
		} else if (buttonName.equalsIgnoreCase("SetPreferred")) {
			PacketTFBase.sendLexiconStudyPacketToServer(ContainerLexiconStudy.SET_PREFERRED);
		} else if (buttonName.equalsIgnoreCase("ClearPreferred")) {
			PacketTFBase.sendLexiconStudyPacketToServer(ContainerLexiconStudy.CLEAR_PREFERRED);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {

		fontRendererObj.drawString(StringHelper.localize(name), getCenteredOffset(StringHelper.localize(name)), 16, 0xddbb1d);

		super.drawGuiContainerForegroundLayer(x, y);
	}

}
