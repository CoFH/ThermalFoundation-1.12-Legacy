package cofh.thermalfoundation.item;

import cofh.api.core.IInitializer;
import cofh.api.core.IModelRegister;
import cofh.core.item.ItemArmorAdv;
import cofh.core.item.tool.*;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.core.TFProps;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Locale;

import static cofh.lib.util.helpers.ItemHelper.ShapedRecipe;

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
	public boolean[] enableTools = new boolean[10];

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
	public ItemHammerAdv itemHammer;

	public ItemStack toolSword;
	public ItemStack toolShovel;
	public ItemStack toolPickaxe;
	public ItemStack toolAxe;
	public ItemStack toolHoe;
	public ItemStack toolBow;
	public ItemStack toolFishingRod;
	public ItemStack toolShears;
	public ItemStack toolSickle;
	public ItemStack toolHammer;

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

	protected void createArmor() {

		itemHelmet = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.HEAD);
		itemPlate = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.CHEST);
		itemLegs = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.LEGS);
		itemBoots = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.FEET);
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

		final String NAME = StringHelper.titleCase(name);
		final String ARMOR = "thermalfoundation.armor." + name.toLowerCase(Locale.US);

		String category = "Equipment." + NAME + ".Armor";
	}

	protected void preInitTools() {

		final String NAME = StringHelper.titleCase(name);
		final String TOOL = "thermalfoundation.tool." + name.toLowerCase(Locale.US);

		String category = "Equipment." + NAME + ".Tools";

		enableTools[0] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Sword", true).getBoolean(true);
		enableTools[1] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Shovel", true).getBoolean(true);
		enableTools[2] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Pickaxe", true).getBoolean(true);
		enableTools[3] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Axe", true).getBoolean(true);
		enableTools[4] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Hoe", true).getBoolean(true);
		enableTools[5] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Bow", true).getBoolean(true);
		enableTools[6] = ThermalFoundation.CONFIG.getConfiguration().get(category, "FishingRod", true).getBoolean(true);
		enableTools[7] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Shears", true).getBoolean(true);
		enableTools[8] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Sickle", true).getBoolean(true);
		enableTools[9] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Hammer", true).getBoolean(true);

		for (int i = 0; i < enableTools.length; i++) {
			enableTools[i] &= !TFProps.disableAllTools;
		}
		createTools();

		/* SWORD */
		itemSword.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Sword").setCreativeTab(ThermalFoundation.tabTools);
		itemSword.setShowInCreative(enableTools[0] | TFProps.showDisabledEquipment);
		itemSword.setRegistryName("tool.sword" + NAME);
		GameRegistry.register(itemSword);

		/* SHOVEL */
		itemShovel.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Shovel").setCreativeTab(ThermalFoundation.tabTools);
		itemShovel.setShowInCreative(enableTools[1] | TFProps.showDisabledEquipment);
		itemShovel.setRegistryName("tool.shovel" + NAME);
		GameRegistry.register(itemShovel);

		/* PICKAXE */
		itemPickaxe.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Pickaxe").setCreativeTab(ThermalFoundation.tabTools);
		itemPickaxe.setShowInCreative(enableTools[2] | TFProps.showDisabledEquipment);
		itemPickaxe.setRegistryName("tool.pickaxe" + NAME);
		GameRegistry.register(itemPickaxe);

		/* AXE */
		itemAxe.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Axe").setCreativeTab(ThermalFoundation.tabTools);
		itemAxe.setShowInCreative(enableTools[3] | TFProps.showDisabledEquipment);
		itemAxe.setRegistryName("tool.axe" + NAME);
		GameRegistry.register(itemAxe);

		/* HOE */
		itemHoe.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Hoe").setCreativeTab(ThermalFoundation.tabTools);
		itemHoe.setShowInCreative(enableTools[4] | TFProps.showDisabledEquipment);
		itemHoe.setRegistryName("tool.hoe" + NAME);
		GameRegistry.register(itemHoe);

		/* BOW */
		itemBow.setRepairIngot(ingot).setArrowSpeed(arrowSpeed).setArrowDamage(arrowDamage).setUnlocalizedName(TOOL + "Bow").setCreativeTab(ThermalFoundation.tabTools);
		itemBow.setShowInCreative(enableTools[5] | TFProps.showDisabledEquipment);
		itemBow.setRegistryName("tool.bow" + NAME);
		GameRegistry.register(itemBow);

		/* FISHING ROD */
		itemFishingRod.setRepairIngot(ingot).setUnlocalizedName(TOOL + "FishingRod").setCreativeTab(ThermalFoundation.tabTools);
		itemFishingRod.setLuckModifier(luckModifier).setSpeedModifier(speedModifier);
		itemFishingRod.setShowInCreative(enableTools[6] | TFProps.showDisabledEquipment);
		itemFishingRod.setRegistryName("tool.fishingRod" + NAME);
		GameRegistry.register(itemFishingRod);

		/* SHEARS */
		itemShears.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Shears").setCreativeTab(ThermalFoundation.tabTools);
		itemShears.setShowInCreative(enableTools[7] | TFProps.showDisabledEquipment);
		itemShears.setRegistryName("tool.shears" + NAME);
		GameRegistry.register(itemShears);

		/* SICKLE */
		itemSickle.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Sickle").setCreativeTab(ThermalFoundation.tabTools);
		itemSickle.setShowInCreative(enableTools[8] | TFProps.showDisabledEquipment);
		itemSickle.setRegistryName("tool.sickle" + NAME);
		GameRegistry.register(itemSickle);

		/* HAMMER */
		itemHammer.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Hammer").setCreativeTab(ThermalFoundation.tabTools);
		itemHammer.setShowInCreative(enableTools[9] | TFProps.showDisabledEquipment);
		itemHammer.setRegistryName("tool.hammer" + NAME);
		GameRegistry.register(itemHammer);

		/* REFERENCES */
		toolSword = new ItemStack(itemSword);
		toolShovel = new ItemStack(itemShovel);
		toolPickaxe = new ItemStack(itemPickaxe);
		toolAxe = new ItemStack(itemAxe);
		toolHoe = new ItemStack(itemHoe);
		toolBow = new ItemStack(itemBow);
		toolFishingRod = new ItemStack(itemFishingRod);
		toolShears = new ItemStack(itemShears);
		toolSickle = new ItemStack(itemSickle);
		toolHammer = new ItemStack(itemHammer);
	}

	protected void postInitArmor() {

	}

	protected void postInitTools() {

		if (enableTools[0]) {
			GameRegistry.addRecipe(ShapedRecipe(toolSword, "I", "I", "S", 'I', ingot, 'S', "stickWood"));
		}
		if (enableTools[1]) {
			GameRegistry.addRecipe(ShapedRecipe(toolShovel, "I", "S", "S", 'I', ingot, 'S', "stickWood"));
		}
		if (enableTools[2]) {
			GameRegistry.addRecipe(ShapedRecipe(toolPickaxe, "III", " S ", " S ", 'I', ingot, 'S', "stickWood"));
		}
		if (enableTools[3]) {
			GameRegistry.addRecipe(ShapedRecipe(toolAxe, "II", "IS", " S", 'I', ingot, 'S', "stickWood"));
		}
		if (enableTools[4]) {
			GameRegistry.addRecipe(ShapedRecipe(toolHoe, "II", " S", " S", 'I', ingot, 'S', "stickWood"));
		}
		if (enableTools[5]) {
			GameRegistry.addRecipe(ShapedRecipe(toolBow, " I#", "S #", " I#", 'I', ingot, 'S', "stickWood", '#', Items.STRING));
		}
		if (enableTools[6]) {
			GameRegistry.addRecipe(ShapedRecipe(toolFishingRod, "  I", " I#", "S #", 'I', ingot, 'S', "stickWood", '#', Items.STRING));
		}
		if (enableTools[7]) {
			GameRegistry.addRecipe(ShapedRecipe(toolShears, " I", "I ", 'I', ingot));
		}
		if (enableTools[8]) {
			GameRegistry.addRecipe(ShapedRecipe(toolSickle, " I ", "  I", "SI ", 'I', ingot, 'S', "stickWood"));
		}
		if (enableTools[9]) {
			GameRegistry.addRecipe(ShapedRecipe(toolHammer, "III", "ISI", " S ", 'I', ingot, 'S', "stickWood"));
		}
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
