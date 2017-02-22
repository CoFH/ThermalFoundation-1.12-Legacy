package cofh.thermalfoundation.item;

import cofh.api.core.IInitializer;
import cofh.core.item.ItemMulti;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemCoin extends ItemMulti implements IInitializer {

	public ItemCoin() {

		super("thermalfoundation");

		setUnlocalizedName("coin");
		setCreativeTab(ThermalFoundation.tabCommon);
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		coinIron = addOreDictItem(0, "coinIron");
		coinGold = addOreDictItem(1, "coinGold");

		coinCopper = addOreDictItem(64, "coinCopper");
		coinTin = addOreDictItem(65, "coinTin");
		coinSilver = addOreDictItem(66, "coinSilver");
		coinLead = addOreDictItem(67, "coinLead");
		coinAluminum = addOreDictItem(68, "coinAluminum");
		coinNickel = addOreDictItem(69, "coinNickel");
		coinPlatinum = addOreDictItem(70, "coinPlatinum", EnumRarity.UNCOMMON);
		coinIridium = addOreDictItem(71, "coinIridium", EnumRarity.UNCOMMON);
		coinMithril = addOreDictItem(72, "coinMithril", EnumRarity.RARE);

		coinSteel = addOreDictItem(96, "coinSteel");
		coinElectrum = addOreDictItem(97, "coinElectrum");
		coinInvar = addOreDictItem(98, "coinInvar");
		coinBronze = addOreDictItem(99, "coinBronze");
		coinConstantan = addOreDictItem(100, "coinConstantan");
		coinSignalum = addOreDictItem(101, "coinSignalum", EnumRarity.UNCOMMON);
		coinLumium = addOreDictItem(102, "coinLumium", EnumRarity.UNCOMMON);
		coinEnderium = addOreDictItem(103, "coinEnderium", EnumRarity.RARE);

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
	public static ItemStack coinIron;
	public static ItemStack coinGold;

	public static ItemStack coinCopper;
	public static ItemStack coinTin;
	public static ItemStack coinSilver;
	public static ItemStack coinLead;
	public static ItemStack coinAluminum;
	public static ItemStack coinNickel;
	public static ItemStack coinPlatinum;
	public static ItemStack coinIridium;
	public static ItemStack coinMithril;

	public static ItemStack coinSteel;
	public static ItemStack coinElectrum;
	public static ItemStack coinInvar;
	public static ItemStack coinBronze;
	public static ItemStack coinConstantan;
	public static ItemStack coinSignalum;
	public static ItemStack coinLumium;
	public static ItemStack coinEnderium;

}
