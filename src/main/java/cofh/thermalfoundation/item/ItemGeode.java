package cofh.thermalfoundation.item;

import cofh.core.item.ItemMulti;
import cofh.core.util.core.IInitializer;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.item.ItemStack;

public class ItemGeode extends ItemMulti implements IInitializer {

	public ItemGeode() {

		super("thermalfoundation");

		setUnlocalizedName("geode");
		setCreativeTab(ThermalFoundation.tabItems);
	}

	/* IInitializer */
	@Override
	public boolean initialize() {

		geode = addItem(0, "geode");

		ThermalFoundation.proxy.addIModelRegister(this);

		return true;
	}

	@Override
	public boolean register() {

		return true;
	}

	/* REFERENCES */
	public static ItemStack geode;

}
