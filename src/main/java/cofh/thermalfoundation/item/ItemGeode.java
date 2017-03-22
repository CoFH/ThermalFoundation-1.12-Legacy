package cofh.thermalfoundation.item;

import cofh.core.item.ItemMulti;
import cofh.core.util.core.IInitializer;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.item.ItemStack;

public class ItemGeode extends ItemMulti implements IInitializer {

	public ItemGeode() {

		super("thermalfoundation");

		setUnlocalizedName("geode");
		setCreativeTab(ThermalFoundation.tabCommon);
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		geode = addItem(0, "geode");

		return true;
	}

	@Override
	public boolean initialize() {

		return true;
	}

	@Override
	public boolean postInit() {

		return true;
	}

	/* REFERENCES */
	public static ItemStack geode;

}
