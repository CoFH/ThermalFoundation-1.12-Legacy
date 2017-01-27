package cofh.thermalfoundation.init;

import cofh.core.item.ItemArmorMulti;
import cofh.core.item.tool.*;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

import java.util.Locale;

import static cofh.lib.util.helpers.ItemHelper.ShapedRecipe;
import static cofh.lib.util.helpers.ItemHelper.addRecipe;

public class TFEquipment {

	private TFEquipment() {

	}

	public static boolean preInit() {

		/* ARMOR */
		itemHelmet = new ItemArmorMulti(ThermalFoundation.MOD_ID, EntityEquipmentSlot.HEAD);
		itemHelmet.setUnlocalizedName("helmet").setCreativeTab(ThermalFoundation.tabArmor);
		ThermalFoundation.proxy.addIModelRegister(itemHelmet);

		itemChestplate = new ItemArmorMulti(ThermalFoundation.MOD_ID, EntityEquipmentSlot.CHEST);
		itemChestplate.setUnlocalizedName("chestplate").setCreativeTab(ThermalFoundation.tabArmor);
		ThermalFoundation.proxy.addIModelRegister(itemChestplate);

		itemLeggings = new ItemArmorMulti(ThermalFoundation.MOD_ID, EntityEquipmentSlot.LEGS);
		itemLeggings.setUnlocalizedName("leggings").setCreativeTab(ThermalFoundation.tabArmor);
		ThermalFoundation.proxy.addIModelRegister(itemLeggings);

		itemBoots = new ItemArmorMulti(ThermalFoundation.MOD_ID, EntityEquipmentSlot.FEET);
		itemBoots.setUnlocalizedName("boots").setCreativeTab(ThermalFoundation.tabArmor);
		ThermalFoundation.proxy.addIModelRegister(itemBoots);

		/* TOOLS */
		itemSword = new ItemSwordMulti(ThermalFoundation.MOD_ID);
		itemSword.setUnlocalizedName("sword").setCreativeTab(ThermalFoundation.tabTools);
		ThermalFoundation.proxy.addIModelRegister(itemSword);

		itemShovel = new ItemShovelMulti(ThermalFoundation.MOD_ID);
		itemShovel.setUnlocalizedName("shovel").setCreativeTab(ThermalFoundation.tabTools);
		ThermalFoundation.proxy.addIModelRegister(itemShovel);

		itemPickaxe = new ItemPickaxeMulti(ThermalFoundation.MOD_ID);
		itemPickaxe.setUnlocalizedName("pickaxe").setCreativeTab(ThermalFoundation.tabTools);
		ThermalFoundation.proxy.addIModelRegister(itemPickaxe);

		itemAxe = new ItemAxeMulti(ThermalFoundation.MOD_ID);
		itemAxe.setUnlocalizedName("axe").setCreativeTab(ThermalFoundation.tabTools);
		ThermalFoundation.proxy.addIModelRegister(itemAxe);

		itemHoe = new ItemHoeMulti(ThermalFoundation.MOD_ID);
		itemHoe.setUnlocalizedName("hoe").setCreativeTab(ThermalFoundation.tabTools);
		ThermalFoundation.proxy.addIModelRegister(itemHoe);

		itemBow = new ItemBowMulti(ThermalFoundation.MOD_ID);
		itemBow.setUnlocalizedName("bow").setCreativeTab(ThermalFoundation.tabTools);
		ThermalFoundation.proxy.addIModelRegister(itemBow);

		itemFishingRod = new ItemFishingRodMulti(ThermalFoundation.MOD_ID);
		itemFishingRod.setUnlocalizedName("fishing_rod").setCreativeTab(ThermalFoundation.tabTools);
		ThermalFoundation.proxy.addIModelRegister(itemFishingRod);

		itemShears = new ItemShearsMulti(ThermalFoundation.MOD_ID);
		itemShears.setUnlocalizedName("shears").setCreativeTab(ThermalFoundation.tabTools);
		ThermalFoundation.proxy.addIModelRegister(itemShears);

		itemSickle = new ItemSickleMulti(ThermalFoundation.MOD_ID);
		itemSickle.setUnlocalizedName("sickle").setCreativeTab(ThermalFoundation.tabTools);
		ThermalFoundation.proxy.addIModelRegister(itemSickle);

		itemHammer = new ItemHammerMulti(ThermalFoundation.MOD_ID);
		itemHammer.setUnlocalizedName("hammer").setCreativeTab(ThermalFoundation.tabTools);
		ThermalFoundation.proxy.addIModelRegister(itemHammer);

		//itemShield = new ItemShieldMulti(ThermalFoundation.MOD_ID);
		//itemShield.setUnlocalizedName("shield").setCreativeTab(ThermalFoundation.tabTools);
		//ThermalFoundation.proxy.addIModelRegister(itemShield);

		/* VANILLA */
		itemBowVanilla = new ItemBowMulti(ThermalFoundation.MOD_ID);
		itemBowVanilla.setUnlocalizedName("bow", "bow_vanilla").setCreativeTab(CreativeTabs.COMBAT);
		ThermalFoundation.proxy.addIModelRegister(itemBowVanilla);

		itemFishingRodVanilla = new ItemFishingRodMulti(ThermalFoundation.MOD_ID);
		itemFishingRodVanilla.setUnlocalizedName("fishing_rod", "fishing_rod_vanilla").setCreativeTab(CreativeTabs.TOOLS);
		ThermalFoundation.proxy.addIModelRegister(itemFishingRodVanilla);

		itemShearsVanilla = new ItemShearsMulti(ThermalFoundation.MOD_ID);
		itemShearsVanilla.setUnlocalizedName("shears", "shears_vanilla").setCreativeTab(CreativeTabs.TOOLS);
		ThermalFoundation.proxy.addIModelRegister(itemShearsVanilla);

		itemSickleVanilla = new ItemSickleMulti(ThermalFoundation.MOD_ID);
		itemSickleVanilla.setUnlocalizedName("sickle", "sickle_vanilla").setCreativeTab(CreativeTabs.TOOLS);
		ThermalFoundation.proxy.addIModelRegister(itemSickleVanilla);

		itemHammerVanilla = new ItemHammerMulti(ThermalFoundation.MOD_ID);
		itemHammerVanilla.setUnlocalizedName("hammer", "hammer_vanilla").setCreativeTab(CreativeTabs.TOOLS);
		ThermalFoundation.proxy.addIModelRegister(itemHammerVanilla);

		//itemShield = new ItemShieldMulti(ThermalFoundation.MOD_ID);
		//itemShield.setUnlocalizedName("shield", "shield_vanilla").setCreativeTab(CreativeTabs.COMBAT);
		//ThermalFoundation.proxy.addIModelRegister(itemShield);

		for (ArmorSet e : ArmorSet.values()) {
			e.preInit();
		}
		for (ToolSet e : ToolSet.values()) {
			e.preInit();
		}
		for (ToolSetVanilla e : ToolSetVanilla.values()) {
			e.preInit();
		}
		return true;
	}

	public static boolean initialize() {

		for (ArmorSet e : ArmorSet.values()) {
			e.initialize();
		}
		for (ToolSet e : ToolSet.values()) {
			e.initialize();
		}
		for (ToolSetVanilla e : ToolSetVanilla.values()) {
			e.initialize();
		}
		return true;
	}

	public static boolean postInit() {

		for (ArmorSet e : ArmorSet.values()) {
			e.postInit();
		}
		for (ToolSet e : ToolSet.values()) {
			e.postInit();
		}
		for (ToolSetVanilla e : ToolSetVanilla.values()) {
			e.postInit();
		}
		return true;
	}

	/* ARMOR */
	public enum ArmorSet {

		// @formatter:off
		COPPER(0, "copper", ARMOR_MATERIAL_COPPER, "ingotCopper"),
		TIN(1, "tin", ARMOR_MATERIAL_TIN, "ingotTin"),
		SILVER(2, "silver", ARMOR_MATERIAL_SILVER, "ingotSilver"),
		LEAD(3, "lead", ARMOR_MATERIAL_LEAD, "ingotLead"),
		NICKEL(5, "nickel", ARMOR_MATERIAL_NICKEL, "ingotNickel"),
		PLATINUM(6, "platinum", ARMOR_MATERIAL_PLATINUM, "ingotPlatinum"),
		ELECTRUM(17, "electrum", ARMOR_MATERIAL_ELECTRUM, "ingotElectrum"),
		INVAR(18, "invar", ARMOR_MATERIAL_INVAR, "ingotInvar"),
		BRONZE(19, "bronze", ARMOR_MATERIAL_BRONZE, "ingotBronze");
		// @formatter: on

		private final int metadata;
		private final String name;
		private final String ingot;
		private final ArmorMaterial material;

		public ItemStack armorHelmet;
		public ItemStack armorChestplate;
		public ItemStack armorLeggings;
		public ItemStack armorBoots;

		public boolean[] enable = new boolean[4];

		ArmorSet(int metadata, String name, ArmorMaterial material, String ingot) {

			this.metadata = metadata;
			this.name = name.toLowerCase(Locale.US);
			this.ingot = ingot;
			this.material = material;
		}

		protected void preInit() {

			String pathArmor = "thermalfoundation:textures/armor/";
			String[] armorTexture = { pathArmor + name + "_1.png", pathArmor + name + "_2.png" };

			armorHelmet = itemHelmet.addItem(metadata, name, material, armorTexture, ingot);
			armorChestplate = itemChestplate.addItem(metadata, name, material, armorTexture, ingot);
			armorLeggings = itemLeggings.addItem(metadata, name, material, armorTexture, ingot);
			armorBoots = itemBoots.addItem(metadata, name, material, armorTexture, ingot);
		}

		protected void initialize() {

			String category = "Equipment.Armor." + StringHelper.titleCase(name);

			enable[0] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Helmet", true).getBoolean(true);
			enable[1] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Chestplate", true).getBoolean(true);
			enable[2] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Leggings", true).getBoolean(true);
			enable[3] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Boots", true).getBoolean(true);

			for (int i = 0; i < enable.length; i++) {
				enable[i] &= !TFProps.disableAllArmor;
			}
		}

		protected void postInit() {

			if (enable[0]) {
				addRecipe(ShapedRecipe(armorHelmet, "III", "I I", 'I', ingot));
			}
			if (enable[1]) {
				addRecipe(ShapedRecipe(armorChestplate, "I I", "III", "III", 'I', ingot));
			}
			if (enable[2]) {
				addRecipe(ShapedRecipe(armorLeggings, "III", "I I", "I I", 'I', ingot));
			}
			if (enable[3]) {
				addRecipe(ShapedRecipe(armorBoots, "I I", "I I", 'I', ingot));
			}
		}

	}

	/* TOOLS */
	public enum ToolSet {

		// @formatter:off
		COPPER(0, "copper", TOOL_MATERIAL_COPPER, "ingotCopper"),
		TIN(1, "tin", TOOL_MATERIAL_TIN, "ingotTin"),
		SILVER(2, "silver", TOOL_MATERIAL_SILVER, "ingotSilver"),
		LEAD(3, "lead", TOOL_MATERIAL_LEAD, "ingotLead"),
		NICKEL(5, "nickel", TOOL_MATERIAL_NICKEL, "ingotNickel"),
		PLATINUM(6, "platinum", TOOL_MATERIAL_PLATINUM, "ingotPlatinum"),
		ELECTRUM(17, "electrum", TOOL_MATERIAL_ELECTRUM, "ingotElectrum"),
		INVAR(18, "invar",TOOL_MATERIAL_INVAR, "ingotInvar"),
		BRONZE(19, "bronze", TOOL_MATERIAL_BRONZE, "ingotBronze");
		// @formatter: on

		private final int metadata;
		private final String name;
		private final String ingot;
		private final ToolMaterial material;

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
		public ItemStack toolShield;

		public boolean[] enable = new boolean[11];

		ToolSet(int metadata, String name, ToolMaterial material, String ingot) {

			this.metadata = metadata;
			this.name = name.toLowerCase(Locale.US);
			this.ingot = ingot;
			this.material = material;
		}

		protected void preInit() {

			toolSword = itemSword.addItem(metadata, name, material, ingot);
			toolShovel = itemShovel.addItem(metadata, name, material, ingot);
			toolPickaxe = itemPickaxe.addItem(metadata, name, material, ingot);
			toolAxe = itemAxe.addItem(metadata, name, material, ingot);
			toolHoe = itemHoe.addItem(metadata, name, material, ingot);
			toolBow = itemBow.addItem(metadata, name, material, ingot, material.getDamageVsEntity() / 2, material.getEfficiencyOnProperMaterial() / 10);
			toolFishingRod = itemFishingRod.addItem(metadata, name, material, ingot, material.getHarvestLevel() / 2, (int) material.getEfficiencyOnProperMaterial() / 3);
			toolShears = itemShears.addItem(metadata, name, material, ingot);
			toolSickle = itemSickle.addItem(metadata, name, material, ingot);
			toolHammer = itemHammer.addItem(metadata, name, material, ingot);
			//toolShield = itemShield.addItem(metadata, name, material, ingot);
		}

		protected void initialize() {

			String category = "Equipment.Tool." + StringHelper.titleCase(name);

			enable[0] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Sword", true).getBoolean(true);
			enable[1] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Shovel", true).getBoolean(true);
			enable[2] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Pickaxe", true).getBoolean(true);
			enable[3] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Axe", true).getBoolean(true);
			enable[4] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Hoe", true).getBoolean(true);
			enable[5] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Bow", true).getBoolean(true);
			enable[6] = ThermalFoundation.CONFIG.getConfiguration().get(category, "FishingRod", true).getBoolean(true);
			enable[7] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Shears", true).getBoolean(true);
			enable[8] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Sickle", true).getBoolean(true);
			enable[9] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Hammer", true).getBoolean(true);
			// TODO: Add Shield
			// enable[10] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Shield", true).getBoolean(true);

			enable[10] = false;

			for (int i = 0; i < enable.length; i++) {
				enable[i] &= !TFProps.disableAllTools;
			}
		}

		protected void postInit() {

			if (enable[0]) {
				addRecipe(ShapedRecipe(toolSword, "I", "I", "S", 'I', ingot, 'S', "stickWood"));
			}
			if (enable[1]) {
				addRecipe(ShapedRecipe(toolShovel, "I", "S", "S", 'I', ingot, 'S', "stickWood"));
			}
			if (enable[2]) {
				addRecipe(ShapedRecipe(toolPickaxe, "III", " S ", " S ", 'I', ingot, 'S', "stickWood"));
			}
			if (enable[3]) {
				addRecipe(ShapedRecipe(toolAxe, "II", "IS", " S", 'I', ingot, 'S', "stickWood"));
			}
			if (enable[4]) {
				addRecipe(ShapedRecipe(toolHoe, "II", " S", " S", 'I', ingot, 'S', "stickWood"));
			}
			if (enable[5]) {
				addRecipe(ShapedRecipe(toolBow, " I#", "S #", " I#", 'I', ingot, 'S', "stickWood", '#', Items.STRING));
			}
			if (enable[6]) {
				addRecipe(ShapedRecipe(toolFishingRod, "  I", " I#", "S #", 'I', ingot, 'S', "stickWood", '#', Items.STRING));
			}
			if (enable[7]) {
				addRecipe(ShapedRecipe(toolShears, " I", "I ", 'I', ingot));
			}
			if (enable[8]) {
				addRecipe(ShapedRecipe(toolSickle, " I ", "  I", "SI ", 'I', ingot, 'S', "stickWood"));
			}
			if (enable[9]) {
				addRecipe(ShapedRecipe(toolHammer, "III", "ISI", " S ", 'I', ingot, 'S', "stickWood"));
			}
			if (enable[10]) {
				addRecipe(ShapedRecipe(toolShield, "ISI", "III", " I ", 'I', ingot, 'S', "ingotIron"));
			}
		}

	}

	/* VANILLA */
	public enum ToolSetVanilla {

		// @formatter:off
		WOOD(0, "wood", ToolMaterial.WOOD, "plankWood"),
    	STONE(1, "stone", ToolMaterial.STONE, "cobblestone"),
    	IRON(2, "iron", ToolMaterial.IRON, "ingotIron"),
    	DIAMOND(3, "diamond", ToolMaterial.DIAMOND, "gemDiamond"),
    	GOLD(4, "gold", ToolMaterial.GOLD, "ingotGold");
		// @formatter: on

		private final int metadata;
		private final String name;
		private final String ingot;
		private final ToolMaterial material;

		public ItemStack toolBow;
		public ItemStack toolFishingRod;
		public ItemStack toolShears;
		public ItemStack toolSickle;
		public ItemStack toolHammer;
		public ItemStack toolShield;

		public boolean[] enable = new boolean[6];

		ToolSetVanilla(int metadata, String name, ToolMaterial material, String ingot) {

			this.metadata = metadata;
			this.name = name.toLowerCase(Locale.US);
			this.ingot = ingot;
			this.material = material;
		}

		protected void preInit() {

			if (this != WOOD) {
				toolBow = itemBowVanilla.addItem(metadata, name, material, ingot);
				toolFishingRod = itemFishingRodVanilla.addItem(metadata, name, material, ingot, material.getHarvestLevel() / 2, (int) material.getEfficiencyOnProperMaterial() / 3);
			}
			if (this != IRON) {
				toolShears = itemShearsVanilla.addItem(metadata, name, material, ingot);
			}
			toolSickle = itemSickleVanilla.addItem(metadata, name, material, ingot);
			toolHammer = itemHammerVanilla.addItem(metadata, name, material, ingot);
			//toolShield = itemShieldVanilla.addItem(metadata, name, material, ingot);
		}

		protected void initialize() {

			String category = "Equipment.Tool." + StringHelper.titleCase(name);

			if (this != WOOD) {
				enable[0] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Bow", true).getBoolean(true);
				enable[1] = ThermalFoundation.CONFIG.getConfiguration().get(category, "FishingRod", true).getBoolean(true);
			}
			if (this != IRON) {
				enable[2] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Shears", true).getBoolean(true);
			}
			enable[3] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Sickle", true).getBoolean(true);
			enable[4] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Hammer", true).getBoolean(true);
			// TODO: Add Shield
			// enable[5] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Shield", true).getBoolean(true);

			enable[5] = false;

			for (int i = 0; i < enable.length; i++) {
				enable[i] &= !TFProps.disableAllTools;
			}
		}

		protected void postInit() {

			if (enable[0]) {
				addRecipe(ShapedRecipe(toolBow, " I#", "S #", " I#", 'I', ingot, 'S', "stickWood", '#', Items.STRING));
			}
			if (enable[1]) {
				addRecipe(ShapedRecipe(toolFishingRod, "  I", " I#", "S #", 'I', ingot, 'S', "stickWood", '#', Items.STRING));
			}
			if (enable[2]) {
				addRecipe(ShapedRecipe(toolShears, " I", "I ", 'I', ingot));
			}
			if (enable[3]) {
				addRecipe(ShapedRecipe(toolSickle, " I ", "  I", "SI ", 'I', ingot, 'S', "stickWood"));
			}
			if (enable[4]) {
				addRecipe(ShapedRecipe(toolHammer, "III", "ISI", " S ", 'I', ingot, 'S', "stickWood"));
			}
			if (enable[5]) {
				addRecipe(ShapedRecipe(toolShield, "ISI", "III", " I ", 'I', ingot, 'S', "ingotIron"));
			}
		}

	}

	/* REFERENCES */
	public static ItemArmorMulti itemHelmet;
	public static ItemArmorMulti itemChestplate;
	public static ItemArmorMulti itemLeggings;
	public static ItemArmorMulti itemBoots;

	public static ItemSwordMulti itemSword;
	public static ItemShovelMulti itemShovel;
	public static ItemPickaxeMulti itemPickaxe;
	public static ItemAxeMulti itemAxe;
	public static ItemHoeMulti itemHoe;
	public static ItemBowMulti itemBow;
	public static ItemFishingRodMulti itemFishingRod;
	public static ItemShearsMulti itemShears;
	public static ItemSickleMulti itemSickle;
	public static ItemHammerMulti itemHammer;
	//public static ItemShieldMulti itemShield;

	public static ItemBowMulti itemBowVanilla;
	public static ItemFishingRodMulti itemFishingRodVanilla;
	public static ItemShearsMulti itemShearsVanilla;
	public static ItemSickleMulti itemSickleVanilla;
	public static ItemHammerMulti itemHammerVanilla;
	//public static ItemShieldMulti itemShieldVanilla;

	/* MATERIALS */
	public static final ArmorMaterial ARMOR_MATERIAL_COPPER = EnumHelper.addArmorMaterial("TF:COPPER", "copper_armor", 6, new int[] { 1, 3, 3, 1 }, 6, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0F);
	public static final ArmorMaterial ARMOR_MATERIAL_TIN = EnumHelper.addArmorMaterial("TF:TIN", "tin_armor", 8, new int[] { 1, 4, 3, 1 }, 7, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0F);
	public static final ArmorMaterial ARMOR_MATERIAL_SILVER = EnumHelper.addArmorMaterial("TF:SILVER", "silver_armor", 11, new int[] { 2, 4, 4, 1 }, 20, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0F);
	public static final ArmorMaterial ARMOR_MATERIAL_LEAD = EnumHelper.addArmorMaterial("TF:LEAD", "lead_armor", 15, new int[] { 2, 5, 4, 3 }, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0F);
	public static final ArmorMaterial ARMOR_MATERIAL_NICKEL = EnumHelper.addArmorMaterial("TF:NICKEL", "nickel_armor", 15, new int[] { 2, 5, 5, 2 }, 18, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0F);
	public static final ArmorMaterial ARMOR_MATERIAL_ELECTRUM = EnumHelper.addArmorMaterial("TF:ELECTRUM", "electrum_armor", 8, new int[] { 2, 4, 4, 2 }, 30, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0F);
	public static final ArmorMaterial ARMOR_MATERIAL_INVAR = EnumHelper.addArmorMaterial("TF:INVAR", "invar_armor", 21, new int[] { 2, 7, 5, 2 }, 16, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1F);
	public static final ArmorMaterial ARMOR_MATERIAL_BRONZE = EnumHelper.addArmorMaterial("TF:BRONZE", "bronze_armor", 18, new int[] { 3, 6, 6, 2 }, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1F);
	public static final ArmorMaterial ARMOR_MATERIAL_PLATINUM = EnumHelper.addArmorMaterial("TF:PLATINUM", "platinum_armor", 40, new int[] { 3, 8, 6, 3 }, 9, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2F);

	public static final ToolMaterial TOOL_MATERIAL_COPPER = EnumHelper.addToolMaterial("TF:COPPER", 1, 175, 4.0F, 1.0F, 6);
	public static final ToolMaterial TOOL_MATERIAL_TIN = EnumHelper.addToolMaterial("TF:TIN", 1, 200, 4.5F, 1.0F, 7);
	public static final ToolMaterial TOOL_MATERIAL_SILVER = EnumHelper.addToolMaterial("TF:SILVER", 2, 200, 6.0F, 1.5F, 20);
	public static final ToolMaterial TOOL_MATERIAL_LEAD = EnumHelper.addToolMaterial("TF:LEAD", 1, 150, 5.0F, 1.0F, 9);
	public static final ToolMaterial TOOL_MATERIAL_NICKEL = EnumHelper.addToolMaterial("TF:NICKEL", 2, 300, 6.5F, 2.5F, 18);
	public static final ToolMaterial TOOL_MATERIAL_ELECTRUM = EnumHelper.addToolMaterial("TF:ELECTRUM", 0, 100, 14.0F, 0.5F, 30);
	public static final ToolMaterial TOOL_MATERIAL_INVAR = EnumHelper.addToolMaterial("TF:INVAR", 2, 450, 7.0F, 3.0F, 16);
	public static final ToolMaterial TOOL_MATERIAL_BRONZE = EnumHelper.addToolMaterial("TF:BRONZE", 2, 500, 6.0F, 2.0F, 15);
	public static final ToolMaterial TOOL_MATERIAL_PLATINUM = EnumHelper.addToolMaterial("TF:PLATINUM", 4, 1700, 9.0F, 4.0F, 9);

}
