package cofh.thermalfoundation.item;

import cofh.core.item.ItemMulti;
import cofh.core.util.core.IInitializer;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ItemGeode extends ItemMulti implements IInitializer {

	public ItemGeode() {

		super("thermalfoundation");

		setUnlocalizedName("geode");
		setCreativeTab(ThermalFoundation.tabItems);
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		ForgeRegistries.ITEMS.register(setRegistryName("geode"));
		ThermalFoundation.proxy.addIModelRegister(this);

		geode = addItem(0, "geode");

		return true;
	}

	@Override
	public boolean initialize() {

		return true;
	}

	/* REFERENCES */
	public static ItemStack geode;

}
