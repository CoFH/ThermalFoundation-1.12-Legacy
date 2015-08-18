package cofh.thermalfoundation.gui.client;

import cofh.core.gui.GuiBaseAdv;
import cofh.core.gui.element.TabInfo;
import cofh.lib.gui.GuiColor;
import cofh.lib.gui.element.ElementButton;
import cofh.lib.gui.element.ElementListBox;
import cofh.lib.gui.element.ElementTextField;
import cofh.lib.gui.element.listbox.IListBoxElement;
import cofh.lib.gui.element.listbox.ListBoxElementText;
import cofh.lib.gui.element.listbox.SliderVertical;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalfoundation.gui.container.ContainerLexiconStudy;
import cofh.thermalfoundation.network.PacketTFBase;
import cofh.thermalfoundation.util.LexiconManager;

import java.util.Locale;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;

public class GuiLexiconStudy extends GuiBaseAdv {

	static final String TEX_PATH = "thermalfoundation:textures/gui/LexiconStudy.png";
	static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

	public String searchLocal = "<" + StringHelper.localize("info.thermalfoundation.lexicon.search") + ">";
	public String myInfo = "";

	ElementTextField searchBox = new ElementTextField(this, 42, 87, 124, 10) {

		public boolean searchUp = true;
		boolean rightClick = false;

		@Override
		public ElementTextField setFocused(boolean focused) {

			if (focused && searchUp) {
				setText("");
				searchUp = false;
			}
			return super.setFocused(focused);
		}

		@Override
		public boolean onKeyTyped(char charTyped, int keyTyped) {

			if (!super.onKeyTyped(charTyped, keyTyped)) {
				return false;
			}
			if (textLength <= 0) {
				buildFullOreList();
			} else {
				buildOreList(getText());
			}
			if (oreList.getElementCount() <= 0) {
				oreList.setSelectedIndex(-1);
			} else {
				oreList.setSelectedIndex(0);
			}
			if (oreList.getSelectedElement() != null) {
				lexicon.onSelectionChanged((String) oreList.getSelectedElement().getValue());
			}
			oreSlider.setLimits(0, oreList.getElementCount() - 8);
			return true;
		}

		@Override
		protected void onFocusLost() {

			if (textLength <= 0) {
				buildFullOreList();
				oreSlider.setLimits(0, oreList.getElementCount() - 8);
				setText("");
				searchUp = true;
			}
		}

		@Override
		public boolean onMousePressed(int mouseX, int mouseY, int mouseButton) {

			if (mouseButton == 1) {
				rightClick = true;
				buildFullOreList();
				oreList.setSelectedIndex(0);
				oreSlider.setLimits(0, oreList.getElementCount() - 8);
				setText("");
				setFocused(true);
			}
			return super.onMousePressed(mouseX, mouseY, mouseButton);
		}

		@Override
		public void onMouseReleased(int mouseX, int mouseY) {

			if (rightClick) {
				rightClick = false;
			} else {
				super.onMouseReleased(mouseX, mouseY);
			}
		}

		@Override
		public void drawBackground(int mouseX, int mouseY, float gameTicks) {

		}
	};

	ElementListBox oreList = new ElementListBox(this, 22, 104, 162, 84) {

		@Override
		protected void onSelectionChanged(int newIndex, IListBoxElement newElement) {

			if (newIndex > -1) {
				lexicon.onSelectionChanged((String) newElement.getValue());
			}
		}

		@Override
		protected void onScrollV(int newStartIndex) {

			oreSlider.setValue(newStartIndex);
		}

		@Override
		protected void onScrollH(int newStartIndex) {

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

		prevOre = new ElementButton(this, 57, 31, "PrevOre", 206, 0, 206, 20, 206, 40, 20, 20, TEX_PATH);
		nextOre = new ElementButton(this, 129, 31, "NextOre", 226, 0, 226, 20, 226, 40, 20, 20, TEX_PATH);
		setPreferredOre = new ElementButton(this, 78, 59, "SetPreferred", 206, 60, 206, 80, 206, 100, 20, 20, TEX_PATH);
		clearPreferredOre = new ElementButton(this, 108, 59, "ClearPreferred", 226, 60, 226, 80, 226, 100, 20, 20, TEX_PATH);

		oreList.borderColor = new GuiColor(120, 120, 120, 0).getColor();
		oreList.backgroundColor = new GuiColor(0, 0, 0, 48).getColor();

		addElement(prevOre);
		addElement(nextOre);
		addElement(setPreferredOre);
		addElement(clearPreferredOre);

		addElement(searchBox);
		addElement(oreList);

		buildFullOreList();
		lexicon.onSelectionChanged((String) oreList.getSelectedElement().getValue());

		oreSlider = new SliderVertical(this, 184, 105, 8, 82, oreList.getElementCount() - 8) {

			@Override
			public void onValueChanged(int value) {

				oreList.scrollToV(value);
			}
		};
		addElement(oreSlider);
		Keyboard.enableRepeatEvents(true);
	}

	@Override
	public void onGuiClosed() {

		Keyboard.enableRepeatEvents(false);
		super.onGuiClosed();
	}

	@Override
	protected void updateElementInformation() {

		if (lexicon.hasMultipleOres()) {
			prevOre.setActive();
			nextOre.setActive();
			prevOre.setToolTip("info.thermalfoundation.lexicon.prevEntry");
			nextOre.setToolTip("info.thermalfoundation.lexicon.nextEntry");
		} else {
			prevOre.setDisabled();
			nextOre.setDisabled();
			prevOre.setToolTip("info.thermalfoundation.lexicon.singleEntry");
			nextOre.setToolTip("info.thermalfoundation.lexicon.singleEntry");
		}
		if (lexicon.canSetPreferred()) {
			setPreferredOre.setActive();
			setPreferredOre.setToolTip("info.thermalfoundation.lexicon.setPreference");
		} else {
			setPreferredOre.setDisabled();
			setPreferredOre.clearToolTip();
		}
		if (lexicon.hasPreferredOre()) {
			clearPreferredOre.setActive();
			clearPreferredOre.setToolTip("info.thermalfoundation.lexicon.clearPreference");
		} else {
			clearPreferredOre.setDisabled();
			clearPreferredOre.clearToolTip();
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

		if (searchBox.isFocused()) {

		} else if (searchBox.getText().isEmpty()) {
			fontRendererObj.drawString(searchLocal, getCenteredOffset(searchLocal), 88, 0xe0e0e0);
		}
		super.drawGuiContainerForegroundLayer(x, y);
	}

	protected void buildOreList(String search) {

		oreList.removeAll();
		for (String oreName : LexiconManager.getSortedOreNames()) {
			if (oreName.toLowerCase(Locale.US).contains(search.toLowerCase(Locale.US))) {
				oreList.add(new ListBoxElementText(oreName));
			}
		}
	}

	protected void buildFullOreList() {

		oreList.removeAll();
		for (String oreName : LexiconManager.getSortedOreNames()) {
			oreList.add(new ListBoxElementText(oreName));
		}
	}

}
