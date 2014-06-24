package thermalfoundation.gui.client;

import cofh.gui.GuiBaseAdv;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import thermalfoundation.gui.container.ContainerLexiconEmpowered;

public class GuiLexiconEmpowered extends GuiBaseAdv {

	static final String INFO = "Convert equivalent items according to an arcane set of rules laid down by higher powers.";
	static final ResourceLocation TEXTURE = new ResourceLocation("thermalfoundation:textures/gui/LexiconConvert.png");

	public GuiLexiconEmpowered(InventoryPlayer inventory, ContainerLexiconEmpowered container) {

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
