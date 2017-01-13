package cofh.thermalfoundation.item.armor;

import cofh.api.core.IInitializer;
import cofh.api.core.IModelRegister;
import cofh.core.item.ItemArmorAdv;
import cofh.core.item.tool.*;
import cofh.lib.util.helpers.StringHelper;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Locale;

public enum EquipmentTF implements IInitializer, IModelRegister {

	// @formatter:off
    COPPER("copper"),
    TIN("tin"),
    SILVER("silver"),
    LEAD("lead"),
    NICKEL("nickel"),
	ELECTRUM("electrum"),
	INVAR("invar"),
	BRONZE("bronze"),
	PLATINUM("platinum");
	// @formatter:on

	public final ArmorMaterial ARMOR_MATERIAL;
	public final ToolMaterial TOOL_MATERIAL;

	private final String name;
	private final String ingot;

	/* AXE */
	private float axeAttackSpeed = -3.2F;

	/* BOW */
	private float arrowSpeed = 1.0F;
	private float arrowDamage = 1.0F;

	/* FISHING ROD */
	private int luckModifier = 0;
	private int speedModifier = 0;

	public boolean enableArmor = true;
	public boolean[] enableTools = new boolean[9];

	/* ARMOR */
	public ItemArmorAdv itemHelmet;
	public ItemArmorAdv itemPlate;
	public ItemArmorAdv itemLegs;
	public ItemArmorAdv itemBoots;

	public ItemStack armorHelmet;
	public ItemStack armorPlate;
	public ItemStack armorLegs;
	public ItemStack armorBoots;

	/* TOOLS */
	public ItemSwordAdv itemSword;
	public ItemShovelAdv itemShovel;
	public ItemPickaxeAdv itemPickaxe;
	public ItemAxeAdv itemAxe;
	public ItemHoeAdv itemHoe;
	public ItemBowAdv itemBow;
	public ItemFishingRodAdv itemFishingRod;
	public ItemShearsAdv itemShears;
	public ItemSickleAdv itemSickle;

	public ItemStack toolSword;
	public ItemStack toolShovel;
	public ItemStack toolPickaxe;
	public ItemStack toolAxe;
	public ItemStack toolHoe;
	public ItemStack toolBow;
	public ItemStack toolFishingRod;
	public ItemStack toolShears;
	public ItemStack toolSickle;

	// TODO: This is to allow compilation
	EquipmentTF(String name) {

		this.name = name;
		this.ingot = name;

		ARMOR_MATERIAL = ArmorMaterial.DIAMOND;
		TOOL_MATERIAL = ToolMaterial.DIAMOND;
	}

	EquipmentTF(String name, ArmorMaterial armorMaterial, ToolMaterial toolMaterial, String ingot) {

		this.name = name;
		this.ingot = ingot == null ? "ingot" + StringHelper.titleCase(name) : ingot;

		ARMOR_MATERIAL = armorMaterial == null ? ArmorMaterial.valueOf(name.toUpperCase(Locale.US)) : armorMaterial;
		TOOL_MATERIAL = toolMaterial == null ? ToolMaterial.valueOf(name.toUpperCase(Locale.US)) : toolMaterial;

		/* AXE */

		/* BOW */
		// arrowSpeed = 2.0F + speed / 8F;
		arrowDamage = 1.0F + TOOL_MATERIAL.getDamageVsEntity() / 8F;

		/* FISHING ROD */
		luckModifier = TOOL_MATERIAL.getHarvestLevel() / 2;
		speedModifier = (int) (TOOL_MATERIAL.getEfficiencyOnProperMaterial() / 5);
	}

	protected void createTools() {

		itemSword = new ItemSwordAdv(TOOL_MATERIAL);
		itemShovel = new ItemShovelAdv(TOOL_MATERIAL);
		itemPickaxe = new ItemPickaxeAdv(TOOL_MATERIAL);
		itemAxe = new ItemAxeAdv(axeAttackSpeed, TOOL_MATERIAL);
		itemHoe = new ItemHoeAdv(TOOL_MATERIAL);
		itemBow = new ItemBowAdv(TOOL_MATERIAL);
		itemFishingRod = new ItemFishingRodAdv(TOOL_MATERIAL);
		itemShears = new ItemShearsAdv(TOOL_MATERIAL);
		itemSickle = new ItemSickleAdv(TOOL_MATERIAL);
	}

	protected void preInitArmor() {

	}

	protected void preInitTools() {

	}

	protected void postInitArmor() {

	}

	protected void postInitTools() {

	}

	/* HELPERS */

	/* IModelRegister */
	@Override
	@SideOnly (Side.CLIENT)
	public void registerModels() {

	}

	/* IInitializer */
	public boolean preInit() {

		for (EquipmentTF e : values()) {
			e.preInitArmor();
			e.preInitTools();
		}
		return true;
	}

	public boolean initialize() {

		return true;
	}

	public boolean postInit() {

		for (EquipmentTF e : values()) {
			e.postInitArmor();
			e.postInitTools();
		}
		return true;
	}
}
