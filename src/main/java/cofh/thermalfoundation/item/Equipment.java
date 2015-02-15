package cofh.thermalfoundation.item;

import cofh.core.item.ItemArmorAdv;
import cofh.core.item.tool.ItemAxeAdv;
import cofh.core.item.tool.ItemBowAdv;
import cofh.core.item.tool.ItemFishingRodAdv;
import cofh.core.item.tool.ItemHoeAdv;
import cofh.core.item.tool.ItemPickaxeAdv;
import cofh.core.item.tool.ItemShearsAdv;
import cofh.core.item.tool.ItemShovelAdv;
import cofh.core.item.tool.ItemSickleAdv;
import cofh.core.item.tool.ItemSwordAdv;
import cofh.thermalfoundation.ThermalFoundation;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.ShapedOreRecipe;

public enum Equipment {

	/* Name, Level, Uses, Speed, Damage, Ench, Dura, Absorption */
	Copper(1, 175, 4.0F, 0.5F, 6, 6, new int[] { 1, 3, 3, 1 }),
	Tin(1, 200, 4.5F, 1.0F, 7, 8, new int[] { 1, 4, 3, 1 }),
	Silver(2, 200, 6.0F, 1.5F, 20, 11, new int[] { 2, 4, 4, 1 }),
	Lead(1, 150, 5F, 1.0F, 9, 15, new int[] { 3, 5, 4, 2 }) {

		@Override
		protected final void createArmor() {

			AttributeModifier knockbackBonus;
			AttributeModifier movementBonus;

			knockbackBonus = new AttributeModifier("lead weight bonus", .25, 0);
			movementBonus = new AttributeModifier("lead weight bonus", -.15, 2);
			itemHelmet = new ItemArmorAdv(ARMOR_MATERIAL, 0);
			itemHelmet.putAttribute("generic.knockbackResistance", knockbackBonus);
			itemHelmet.putAttribute("generic.movementSpeed", movementBonus);
			knockbackBonus = new AttributeModifier("lead weight bonus", .25, 0);
			movementBonus = new AttributeModifier("lead weight bonus", -.15, 2);
			itemPlate = new ItemArmorAdv(ARMOR_MATERIAL, 1);
			itemPlate.putAttribute("generic.knockbackResistance", knockbackBonus);
			itemPlate.putAttribute("generic.movementSpeed", movementBonus);
			knockbackBonus = new AttributeModifier("lead weight bonus", .25, 0);
			movementBonus = new AttributeModifier("lead weight bonus", -.15, 2);
			itemLegs = new ItemArmorAdv(ARMOR_MATERIAL, 2);
			itemLegs.putAttribute("generic.knockbackResistance", knockbackBonus);
			itemLegs.putAttribute("generic.movementSpeed", movementBonus);
			knockbackBonus = new AttributeModifier("lead weight bonus", .25, 0);
			movementBonus = new AttributeModifier("lead weight bonus", -.15, 2);
			itemBoots = new ItemArmorAdv(ARMOR_MATERIAL, 3);
			itemBoots.putAttribute("generic.knockbackResistance", knockbackBonus);
			itemBoots.putAttribute("generic.movementSpeed", movementBonus);
		}
	},
	Nickel(2, 300, 6.5F, 2.5F, 18, 15, new int[] { 2, 5, 5, 2 }),
	Electrum(0, 100, 14.0F, 0.5F, 30, 8, new int[] { 2, 4, 4, 2 }),
	Invar(2, 450, 7.0F, 3.0F, 16, 25, new int[] { 2, 7, 5, 2 }),
	Bronze(2, 500, 6.0F, 2.0F, 15, 17, new int[] { 3, 6, 6, 2 }),
	Platinum(4, 1700, 9.0F, 4.0F, 9, 40, new int[] { 3, 8, 6, 3 }) {

		@Override
		protected final void createArmor() {

			AttributeModifier knockbackBonus;
			AttributeModifier movementBonus;

			knockbackBonus = new AttributeModifier("platinum weight bonus", .20, 0);
			movementBonus = new AttributeModifier("platinum weight bonus", -.08, 2);
			itemHelmet = new ItemArmorAdv(ARMOR_MATERIAL, 0);
			itemHelmet.putAttribute("generic.knockbackResistance", knockbackBonus);
			itemHelmet.putAttribute("generic.movementSpeed", movementBonus);
			knockbackBonus = new AttributeModifier("platinum weight bonus", .20, 0);
			movementBonus = new AttributeModifier("platinum weight bonus", -.08, 2);
			itemPlate = new ItemArmorAdv(ARMOR_MATERIAL, 1);
			itemPlate.putAttribute("generic.knockbackResistance", knockbackBonus);
			itemPlate.putAttribute("generic.movementSpeed", movementBonus);
			knockbackBonus = new AttributeModifier("platinum weight bonus", .20, 0);
			movementBonus = new AttributeModifier("platinum weight bonus", -.08, 2);
			itemLegs = new ItemArmorAdv(ARMOR_MATERIAL, 2);
			itemLegs.putAttribute("generic.knockbackResistance", knockbackBonus);
			itemLegs.putAttribute("generic.movementSpeed", movementBonus);
			knockbackBonus = new AttributeModifier("platinum weight bonus", .20, 0);
			movementBonus = new AttributeModifier("platinum weight bonus", -.08, 2);
			itemBoots = new ItemArmorAdv(ARMOR_MATERIAL, 3);
			itemBoots.putAttribute("generic.knockbackResistance", knockbackBonus);
			itemBoots.putAttribute("generic.movementSpeed", movementBonus);
		}
	},
	;

	public final ToolMaterial TOOL_MATERIAL;
	public final ArmorMaterial ARMOR_MATERIAL;

	private final String ingot;
	private final float arrowSpeed = 2.5F;
	private final float arrowDamage = 1.25F;

	public boolean enableArmor = true;
	public boolean[] enableTools = new boolean[9];

	public ItemArmorAdv itemHelmet;
	public ItemArmorAdv itemPlate;
	public ItemArmorAdv itemLegs;
	public ItemArmorAdv itemBoots;

	public ItemSwordAdv itemSword;
	public ItemShovelAdv itemShovel;
	public ItemPickaxeAdv itemPickaxe;
	public ItemAxeAdv itemAxe;
	public ItemHoeAdv itemHoe;
	public ItemShearsAdv itemShears;
	public ItemFishingRodAdv itemFishingRod;
	public ItemSickleAdv itemSickle;
	public ItemBowAdv itemBow;

	public ItemStack armorHelmet;
	public ItemStack armorPlate;
	public ItemStack armorLegs;
	public ItemStack armorBoots;

	public ItemStack toolSword;
	public ItemStack toolShovel;
	public ItemStack toolPickaxe;
	public ItemStack toolAxe;
	public ItemStack toolHoe;
	public ItemStack toolShears;
	public ItemStack toolFishingRod;
	public ItemStack toolSickle;
	public ItemStack toolBow;

	private Equipment(int level, int uses, float speed, float damage, int enchant, int durability, int[] absorb) {

		TOOL_MATERIAL = EnumHelper.addToolMaterial("TF:" + name().toUpperCase(), level, uses, speed, damage, enchant);
		ARMOR_MATERIAL = EnumHelper.addArmorMaterial("TF:" + name().toUpperCase(), durability, absorb, enchant);
		ingot = "ingot" + name();
	}

	protected void createArmor() {

		itemHelmet = new ItemArmorAdv(ARMOR_MATERIAL, 0);
		itemPlate = new ItemArmorAdv(ARMOR_MATERIAL, 1);
		itemLegs = new ItemArmorAdv(ARMOR_MATERIAL, 2);
		itemBoots = new ItemArmorAdv(ARMOR_MATERIAL, 3);
	}

	protected void createTools() {

		itemSword = new ItemSwordAdv(TOOL_MATERIAL);
		itemShovel = new ItemShovelAdv(TOOL_MATERIAL);
		itemPickaxe = new ItemPickaxeAdv(TOOL_MATERIAL);
		itemAxe = new ItemAxeAdv(TOOL_MATERIAL);
		itemHoe = new ItemHoeAdv(TOOL_MATERIAL);
		itemShears = new ItemShearsAdv(TOOL_MATERIAL);
		itemFishingRod = new ItemFishingRodAdv(TOOL_MATERIAL);
		itemSickle = new ItemSickleAdv(TOOL_MATERIAL);
		itemBow = new ItemBowAdv(TOOL_MATERIAL);
	}

	protected void preInitv() {

		final String NAME = name();
		final String TYPE = NAME.toLowerCase();
		final String ARMOR = "thermalfoundation.armor." + TYPE;
		final String TOOL = "thermalfoundation.tool." + TYPE;

		String category = "Equipment." + NAME;
		enableArmor = ThermalFoundation.config.get(category, "Armor", true);
		category += ".Tools";
		enableTools[0] = ThermalFoundation.config.get(category, "Sword", true);
		enableTools[1] = ThermalFoundation.config.get(category, "Shovel", true);
		enableTools[2] = ThermalFoundation.config.get(category, "Pickaxe", true);
		enableTools[3] = ThermalFoundation.config.get(category, "Axe", true);
		enableTools[4] = ThermalFoundation.config.get(category, "Hoe", true);
		enableTools[5] = ThermalFoundation.config.get(category, "Shears", true);
		enableTools[6] = ThermalFoundation.config.get(category, "FishingRod", true);
		enableTools[7] = ThermalFoundation.config.get(category, "Sickle", true);
		enableTools[8] = ThermalFoundation.config.get(category, "Bow", true);

		final String PATH_ARMOR = "thermalfoundation:textures/" + "armor/";
		final String[] TEXTURE = { PATH_ARMOR + NAME + "_1.png", PATH_ARMOR + NAME + "_2.png" };
		final String ARMOR_PATH = "thermalfoundation:armor/" + TYPE + "/" + NAME;
		final String TOOL_PATH = "thermalfoundation:tool/" + TYPE + "/" + NAME;

		createArmor();
		itemHelmet.setRepairIngot(ingot).setArmorTextures(TEXTURE).setUnlocalizedName(ARMOR + "Helmet");
		itemHelmet.setTextureName(ARMOR_PATH + "Helmet").setCreativeTab(ThermalFoundation.tabArmor);
		itemPlate.setRepairIngot(ingot).setArmorTextures(TEXTURE).setUnlocalizedName(ARMOR + "Plate");
		itemPlate.setTextureName(ARMOR_PATH + "Chestplate").setCreativeTab(ThermalFoundation.tabArmor);
		itemLegs.setRepairIngot(ingot).setArmorTextures(TEXTURE).setUnlocalizedName(ARMOR + "Legs");
		itemLegs.setTextureName(ARMOR_PATH + "Legs").setCreativeTab(ThermalFoundation.tabArmor);
		itemBoots.setRepairIngot(ingot).setArmorTextures(TEXTURE).setUnlocalizedName(ARMOR + "Boots");
		itemBoots.setTextureName(ARMOR_PATH + "Boots").setCreativeTab(ThermalFoundation.tabArmor);

		createTools();
		itemSword.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Sword");
		itemSword.setTextureName(TOOL_PATH + "Sword").setCreativeTab(ThermalFoundation.tabTools);
		itemShovel.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Shovel");
		itemShovel.setTextureName(TOOL_PATH + "Shovel").setCreativeTab(ThermalFoundation.tabTools);
		itemPickaxe.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Pickaxe");
		itemPickaxe.setTextureName(TOOL_PATH + "Pickaxe").setCreativeTab(ThermalFoundation.tabTools);
		itemAxe.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Axe");
		itemAxe.setTextureName(TOOL_PATH + "Axe").setCreativeTab(ThermalFoundation.tabTools);
		itemHoe.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Hoe");
		itemHoe.setTextureName(TOOL_PATH + "Hoe").setCreativeTab(ThermalFoundation.tabTools);
		itemShears.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Shears");
		itemShears.setTextureName(TOOL_PATH + "Shears").setCreativeTab(ThermalFoundation.tabTools);
		itemFishingRod.setRepairIngot(ingot).setUnlocalizedName(TOOL + "FishingRod");
		itemFishingRod.setTextureName(TOOL_PATH + "FishingRod").setCreativeTab(ThermalFoundation.tabTools);
		itemSickle.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Sickle");
		itemSickle.setTextureName(TOOL_PATH + "Sickle").setCreativeTab(ThermalFoundation.tabTools);
		itemBow.setRepairIngot(ingot).setArrowSpeed(arrowSpeed).setArrowDamage(arrowDamage).setUnlocalizedName(TOOL + "Bow");
		itemBow.setTextureName(TOOL_PATH + "Bow").setCreativeTab(ThermalFoundation.tabTools);

		GameRegistry.registerItem(itemHelmet, "armor.helmet" + NAME);
		GameRegistry.registerItem(itemPlate, "armor.plate" + NAME);
		GameRegistry.registerItem(itemLegs, "armor.legs" + NAME);
		GameRegistry.registerItem(itemBoots, "armor.boots" + NAME);

		GameRegistry.registerItem(itemSword, "tool.sword" + NAME);
		GameRegistry.registerItem(itemShovel, "tool.shovel" + NAME);
		GameRegistry.registerItem(itemPickaxe, "tool.pickaxe" + NAME);
		GameRegistry.registerItem(itemAxe, "tool.axe" + NAME);
		GameRegistry.registerItem(itemHoe, "tool.hoe" + NAME);
		GameRegistry.registerItem(itemShears, "tool.shears" + NAME);
		GameRegistry.registerItem(itemFishingRod, "tool.fishingRod" + NAME);
		GameRegistry.registerItem(itemSickle, "tool.sickle" + NAME);
		GameRegistry.registerItem(itemBow, "tool.bow" + NAME);

	}

	protected void initializev() {

		final String NAME = name();

		// Armor
		armorHelmet = new ItemStack(itemHelmet);
		armorPlate = new ItemStack(itemPlate);
		armorLegs = new ItemStack(itemLegs);
		armorBoots = new ItemStack(itemBoots);

		GameRegistry.registerCustomItemStack("armor" + NAME + "Helmet", armorHelmet);
		GameRegistry.registerCustomItemStack("armor" + NAME + "Plate", armorPlate);
		GameRegistry.registerCustomItemStack("armor" + NAME + "Legs", armorLegs);
		GameRegistry.registerCustomItemStack("armor" + NAME + "Boots", armorBoots);

		// Tools
		toolSword = new ItemStack(itemSword);
		toolShovel = new ItemStack(itemShovel);
		toolPickaxe = new ItemStack(itemPickaxe);
		toolAxe = new ItemStack(itemAxe);
		toolHoe = new ItemStack(itemHoe);
		toolShears = new ItemStack(itemShears);
		toolFishingRod = new ItemStack(itemFishingRod);
		toolSickle = new ItemStack(itemSickle);
		toolBow = new ItemStack(itemBow);

		GameRegistry.registerCustomItemStack("tool" + NAME + "Sword", toolSword);
		GameRegistry.registerCustomItemStack("tool" + NAME + "Shovel", toolShovel);
		GameRegistry.registerCustomItemStack("tool" + NAME + "Pickaxe", toolPickaxe);
		GameRegistry.registerCustomItemStack("tool" + NAME + "Axe", toolAxe);
		GameRegistry.registerCustomItemStack("tool" + NAME + "Hoe", toolHoe);
		GameRegistry.registerCustomItemStack("tool" + NAME + "Shears", toolShears);
		GameRegistry.registerCustomItemStack("tool" + NAME + "FishingRod", toolFishingRod);
		GameRegistry.registerCustomItemStack("tool" + NAME + "Sickle", toolSickle);
		GameRegistry.registerCustomItemStack("tool" + NAME + "Bow", toolBow);
	}

	protected void postInitv() {

		// Armor
		if (enableArmor) {
			GameRegistry.addRecipe(new ShapedOreRecipe(armorHelmet, new Object[] { "III", "I I", 'I', ingot }));
			GameRegistry.addRecipe(new ShapedOreRecipe(armorPlate, new Object[] { "I I", "III", "III", 'I', ingot }));
			GameRegistry.addRecipe(new ShapedOreRecipe(armorLegs, new Object[] { "III", "I I", "I I", 'I', ingot }));
			GameRegistry.addRecipe(new ShapedOreRecipe(armorBoots, new Object[] { "I I", "I I", 'I', ingot }));
		}

		// Tools
		if (enableTools[0]) {
			GameRegistry.addRecipe(new ShapedOreRecipe(toolSword, new Object[] { "I", "I", "S", 'I', ingot, 'S', "stickWood" }));
		}
		if (enableTools[1]) {
			GameRegistry.addRecipe(new ShapedOreRecipe(toolShovel, new Object[] { "I", "S", "S", 'I', ingot, 'S', "stickWood" }));
		}
		if (enableTools[2]) {
			GameRegistry.addRecipe(new ShapedOreRecipe(toolPickaxe, new Object[] { "III", " S ", " S ", 'I', ingot, 'S', "stickWood" }));
		}
		if (enableTools[3]) {
			GameRegistry.addRecipe(new ShapedOreRecipe(toolAxe, new Object[] { "II", "IS", " S", 'I', ingot, 'S', "stickWood" }));
		}
		if (enableTools[4]) {
			GameRegistry.addRecipe(new ShapedOreRecipe(toolHoe, new Object[] { "II", " S", " S", 'I', ingot, 'S', "stickWood" }));
		}
		if (enableTools[5]) {
			GameRegistry.addRecipe(new ShapedOreRecipe(toolShears, new Object[] { " I", "I ", 'I', ingot }));
		}
		if (enableTools[6]) {
			GameRegistry.addRecipe(new ShapedOreRecipe(toolFishingRod, new Object[] { "  I", " I#", "S #", 'I', ingot, 'S', "stickWood", '#', Items.string }));
		}
		if (enableTools[7]) {
			GameRegistry.addRecipe(new ShapedOreRecipe(toolSickle, new Object[] { " I ", "  I", "SI ", 'I', ingot, 'S', "stickWood" }));
		}
		if (enableTools[8]) {
			GameRegistry.addRecipe(new ShapedOreRecipe(toolBow, new Object[] { " I#", "S #", " I#", 'I', ingot, 'S', "stickWood", '#', Items.string }));
		}
	}

	public static void preInit() {

		for (Equipment e : values()) {
			e.preInitv();
		}
	}

	public static void initialize() {

		for (Equipment e : values()) {
			e.initializev();
		}
	}

	public static void postInit() {

		for (Equipment e : values()) {
			e.postInitv();
		}
	}

}
