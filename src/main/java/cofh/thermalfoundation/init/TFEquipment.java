package cofh.thermalfoundation.init;

import cofh.core.item.ItemArmorCore;
import cofh.core.item.tool.*;
import cofh.core.render.IModelRegister;
import cofh.core.util.helpers.MathHelper;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Locale;

import static cofh.core.util.helpers.RecipeHelper.addShapedRecipe;

public class TFEquipment {

	public static final TFEquipment INSTANCE = new TFEquipment();

	private TFEquipment() {

	}

	public static void preInit() {

		for (ArmorSet e : ArmorSet.values()) {
			e.preInit();
			ThermalFoundation.proxy.addIModelRegister(e);
		}
		for (ToolSet e : ToolSet.values()) {
			e.preInit();
			ThermalFoundation.proxy.addIModelRegister(e);
		}
		for (ToolSetVanilla e : ToolSetVanilla.values()) {
			e.preInit();
			ThermalFoundation.proxy.addIModelRegister(e);
		}
		for (HorseArmor e : HorseArmor.values()) {
			e.preInit();
			ThermalFoundation.proxy.addIModelRegister(e);
		}
		MinecraftForge.EVENT_BUS.register(INSTANCE);
	}

	/* EVENT HANDLING */
	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event) {

		for (ArmorSet e : ArmorSet.values()) {
			e.initialize();
		}
		for (ToolSet e : ToolSet.values()) {
			e.initialize();
		}
		for (ToolSetVanilla e : ToolSetVanilla.values()) {
			e.initialize();
		}
		for (HorseArmor e : HorseArmor.values()) {
			e.initialize();
		}
	}

	/* MATERIALS */
	public static final ArmorMaterial ARMOR_MATERIAL_COPPER = EnumHelper.addArmorMaterial("TF:COPPER", "copper_armor", 10, new int[] { 1, 3, 3, 1 }, 8, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0F);
	public static final ArmorMaterial ARMOR_MATERIAL_TIN = EnumHelper.addArmorMaterial("TF:TIN", "tin_armor", 8, new int[] { 1, 3, 4, 1 }, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0F);
	public static final ArmorMaterial ARMOR_MATERIAL_SILVER = EnumHelper.addArmorMaterial("TF:SILVER", "silver_armor", 8, new int[] { 2, 4, 4, 1 }, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0F);
	public static final ArmorMaterial ARMOR_MATERIAL_LEAD = EnumHelper.addArmorMaterial("TF:LEAD", "lead_armor", 12, new int[] { 2, 4, 5, 3 }, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0F);
	public static final ArmorMaterial ARMOR_MATERIAL_ALUMINUM = EnumHelper.addArmorMaterial("TF:ALUMINUM", "aluminum_armor", 12, new int[] { 1, 3, 4, 2 }, 14, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0F);
	public static final ArmorMaterial ARMOR_MATERIAL_NICKEL = EnumHelper.addArmorMaterial("TF:NICKEL", "nickel_armor", 15, new int[] { 2, 5, 5, 2 }, 18, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0F);
	public static final ArmorMaterial ARMOR_MATERIAL_PLATINUM = EnumHelper.addArmorMaterial("TF:PLATINUM", "platinum_armor", 35, new int[] { 3, 6, 8, 3 }, 16, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2F);
	public static final ArmorMaterial ARMOR_MATERIAL_STEEL = EnumHelper.addArmorMaterial("TF:STEEL", "steel_armor", 22, new int[] { 2, 5, 7, 2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1F);
	public static final ArmorMaterial ARMOR_MATERIAL_ELECTRUM = EnumHelper.addArmorMaterial("TF:ELECTRUM", "electrum_armor", 8, new int[] { 2, 4, 4, 2 }, 30, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0F);
	public static final ArmorMaterial ARMOR_MATERIAL_INVAR = EnumHelper.addArmorMaterial("TF:INVAR", "invar_armor", 21, new int[] { 2, 5, 7, 2 }, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1F);
	public static final ArmorMaterial ARMOR_MATERIAL_BRONZE = EnumHelper.addArmorMaterial("TF:BRONZE", "bronze_armor", 18, new int[] { 2, 6, 6, 2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1F);
	public static final ArmorMaterial ARMOR_MATERIAL_CONSTANTAN = EnumHelper.addArmorMaterial("TF:CONSTANTAN", "constantan_armor", 13, new int[] { 2, 4, 4, 2 }, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0F);

	public static final ToolMaterial TOOL_MATERIAL_COPPER = EnumHelper.addToolMaterial("TF:COPPER", 1, 175, 4.0F, 1.0F, 7);
	public static final ToolMaterial TOOL_MATERIAL_TIN = EnumHelper.addToolMaterial("TF:TIN", 1, 150, 4.5F, 1.0F, 7);
	public static final ToolMaterial TOOL_MATERIAL_SILVER = EnumHelper.addToolMaterial("TF:SILVER", 1, 75, 6.0F, 1.0F, 25);
	public static final ToolMaterial TOOL_MATERIAL_LEAD = EnumHelper.addToolMaterial("TF:LEAD", 1, 100, 5.0F, 1.0F, 9);
	public static final ToolMaterial TOOL_MATERIAL_ALUMINUM = EnumHelper.addToolMaterial("TF:ALUMINUM", 1, 225, 10.0F, 1.0F, 14);
	public static final ToolMaterial TOOL_MATERIAL_NICKEL = EnumHelper.addToolMaterial("TF:NICKEL", 2, 300, 6.5F, 2.5F, 18);
	public static final ToolMaterial TOOL_MATERIAL_PLATINUM = EnumHelper.addToolMaterial("TF:PLATINUM", 4, 1400, 9.0F, 3.5F, 16);
	public static final ToolMaterial TOOL_MATERIAL_STEEL = EnumHelper.addToolMaterial("TF:STEEL", 2, 400, 6.5F, 2.5F, 10);
	public static final ToolMaterial TOOL_MATERIAL_ELECTRUM = EnumHelper.addToolMaterial("TF:ELECTRUM", 0, 100, 14.0F, 0.5F, 30);
	public static final ToolMaterial TOOL_MATERIAL_INVAR = EnumHelper.addToolMaterial("TF:INVAR", 2, 425, 6.5F, 2.5F, 12);
	public static final ToolMaterial TOOL_MATERIAL_BRONZE = EnumHelper.addToolMaterial("TF:BRONZE", 2, 325, 6.0F, 2.0F, 10);
	public static final ToolMaterial TOOL_MATERIAL_CONSTANTAN = EnumHelper.addToolMaterial("TF:CONSTANTAN", 2, 275, 6.0F, 1.5F, 12);

	public static final HorseArmorType HORSE_ARMOR_COPPER = EnumHelper.addHorseArmor("TF:COPPER", ThermalFoundation.MOD_ID + ":textures/horse_armor/" + "copper.png", 4);
	public static final HorseArmorType HORSE_ARMOR_TIN = EnumHelper.addHorseArmor("TF:TIN", ThermalFoundation.MOD_ID + ":textures/horse_armor/" + "tin.png", 4);
	public static final HorseArmorType HORSE_ARMOR_SILVER = EnumHelper.addHorseArmor("TF:SILVER", ThermalFoundation.MOD_ID + ":textures/horse_armor/" + "silver.png", 8);
	public static final HorseArmorType HORSE_ARMOR_LEAD = EnumHelper.addHorseArmor("TF:LEAD", ThermalFoundation.MOD_ID + ":textures/horse_armor/" + "lead.png", 5);
	public static final HorseArmorType HORSE_ARMOR_ALUMINUM = EnumHelper.addHorseArmor("TF:ALUMINUM", ThermalFoundation.MOD_ID + ":textures/horse_armor/" + "aluminum.png", 5);
	public static final HorseArmorType HORSE_ARMOR_NICKEL = EnumHelper.addHorseArmor("TF:NICKEL", ThermalFoundation.MOD_ID + ":textures/horse_armor/" + "nickel.png", 9);
	public static final HorseArmorType HORSE_ARMOR_PLATINUM = EnumHelper.addHorseArmor("TF:PLATINUM", ThermalFoundation.MOD_ID + ":textures/horse_armor/" + "platinum.png", 12);
	public static final HorseArmorType HORSE_ARMOR_STEEL = EnumHelper.addHorseArmor("TF:STEEL", ThermalFoundation.MOD_ID + ":textures/horse_armor/" + "steel.png", 9);
	public static final HorseArmorType HORSE_ARMOR_ELECTRUM = EnumHelper.addHorseArmor("TF:ELECTRUM", ThermalFoundation.MOD_ID + ":textures/horse_armor/" + "electrum.png", 8);
	public static final HorseArmorType HORSE_ARMOR_INVAR = EnumHelper.addHorseArmor("TF:INVAR", ThermalFoundation.MOD_ID + ":textures/horse_armor/" + "invar.png", 9);
	public static final HorseArmorType HORSE_ARMOR_BRONZE = EnumHelper.addHorseArmor("TF:BRONZE", ThermalFoundation.MOD_ID + ":textures/horse_armor/" + "bronze.png", 8);
	public static final HorseArmorType HORSE_ARMOR_CONSTANTAN = EnumHelper.addHorseArmor("TF:CONSTANTAN", ThermalFoundation.MOD_ID + ":textures/horse_armor/" + "constantan.png", 8);

	/* ARMOR */
	public enum ArmorSet implements IModelRegister {

		// @formatter:off
		COPPER("copper", ARMOR_MATERIAL_COPPER, "ingotCopper"),
		TIN("tin", ARMOR_MATERIAL_TIN, "ingotTin"),
		SILVER("silver", ARMOR_MATERIAL_SILVER, "ingotSilver"),
		LEAD("lead", ARMOR_MATERIAL_LEAD, "ingotLead"),
		ALUMINUM("aluminum", ARMOR_MATERIAL_ALUMINUM, "ingotAluminum"),
		NICKEL("nickel", ARMOR_MATERIAL_NICKEL, "ingotNickel"),
		PLATINUM("platinum", ARMOR_MATERIAL_PLATINUM, "ingotPlatinum"),
		// IRIDIUM
		// MITHRIL
		STEEL("steel", ARMOR_MATERIAL_STEEL, "ingotSteel"),
		ELECTRUM("electrum", ARMOR_MATERIAL_ELECTRUM, "ingotElectrum"),
		INVAR("invar", ARMOR_MATERIAL_INVAR, "ingotInvar"),
		BRONZE("bronze", ARMOR_MATERIAL_BRONZE, "ingotBronze"),
		CONSTANTAN("constantan", ARMOR_MATERIAL_CONSTANTAN, "ingotConstantan");
		// @formatter:on

		public final String name;
		public final String ingot;
		public final ArmorMaterial ARMOR_MATERIAL;

		public ItemArmorCore itemHelmet;
		public ItemArmorCore itemChestplate;
		public ItemArmorCore itemLegs;
		public ItemArmorCore itemBoots;

		public ItemStack armorHelmet;
		public ItemStack armorChestplate;
		public ItemStack armorLegs;
		public ItemStack armorBoots;

		public boolean[] enable = new boolean[4];

		ArmorSet(String name, ArmorMaterial material, String ingot) {

			this.name = name.toLowerCase(Locale.US);
			this.ingot = ingot;
			ARMOR_MATERIAL = material;
		}

		protected void create() {

			itemHelmet = new ItemArmorCore(ARMOR_MATERIAL, EntityEquipmentSlot.HEAD);
			itemChestplate = new ItemArmorCore(ARMOR_MATERIAL, EntityEquipmentSlot.CHEST);
			itemLegs = new ItemArmorCore(ARMOR_MATERIAL, EntityEquipmentSlot.LEGS);
			itemBoots = new ItemArmorCore(ARMOR_MATERIAL, EntityEquipmentSlot.FEET);
		}

		protected void preInit() {

			final String ARMOR = "thermalfoundation.armor." + name;
			final String PATH_ARMOR = "thermalfoundation:textures/armor/";
			final String[] TEXTURE = { PATH_ARMOR + name + "_1.png", PATH_ARMOR + name + "_2.png" };

			String category = "Equipment.Armor." + StringHelper.titleCase(name);

			enable[0] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Helmet", true).getBoolean(true);
			enable[1] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Chestplate", true).getBoolean(true);
			enable[2] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Leggings", true).getBoolean(true);
			enable[3] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Boots", true).getBoolean(true);

			for (int i = 0; i < enable.length; i++) {
				enable[i] &= !TFProps.disableAllArmor;
			}
			create();

			/* HELMET */
			itemHelmet.setArmorTextures(TEXTURE).setRepairIngot(ingot).setUnlocalizedName(ARMOR + "Helmet").setCreativeTab(ThermalFoundation.tabBasicArmor);
			itemHelmet.setShowInCreative(enable[0]);
			itemHelmet.setRegistryName("armor.helmet_" + name);
			ForgeRegistries.ITEMS.register(itemHelmet);

			/* PLATE */
			itemChestplate.setArmorTextures(TEXTURE).setRepairIngot(ingot).setUnlocalizedName(ARMOR + "Chestplate").setCreativeTab(ThermalFoundation.tabBasicArmor);
			itemChestplate.setShowInCreative(enable[1]);
			itemChestplate.setRegistryName("armor.plate_" + name);
			ForgeRegistries.ITEMS.register(itemChestplate);

			/* LEGS */
			itemLegs.setArmorTextures(TEXTURE).setRepairIngot(ingot).setUnlocalizedName(ARMOR + "Legs").setCreativeTab(ThermalFoundation.tabBasicArmor);
			itemLegs.setShowInCreative(enable[2]);
			itemLegs.setRegistryName("armor.legs_" + name);
			ForgeRegistries.ITEMS.register(itemLegs);

			/* BOOTS */
			itemBoots.setArmorTextures(TEXTURE).setRepairIngot(ingot).setUnlocalizedName(ARMOR + "Boots").setCreativeTab(ThermalFoundation.tabBasicArmor);
			itemBoots.setShowInCreative(enable[3]);
			itemBoots.setRegistryName("armor.boots_" + name);
			ForgeRegistries.ITEMS.register(itemBoots);

			/* REFERENCES */
			armorHelmet = new ItemStack(itemHelmet);
			armorChestplate = new ItemStack(itemChestplate);
			armorLegs = new ItemStack(itemLegs);
			armorBoots = new ItemStack(itemBoots);
		}

		protected void initialize() {

			if (enable[0]) {
				addShapedRecipe(armorHelmet, "III", "I I", 'I', ingot);
			}
			if (enable[1]) {
				addShapedRecipe(armorChestplate, "I I", "III", "III", 'I', ingot);
			}
			if (enable[2]) {
				addShapedRecipe(armorLegs, "III", "I I", "I I", 'I', ingot);
			}
			if (enable[3]) {
				addShapedRecipe(armorBoots, "I I", "I I", 'I', ingot);
			}
		}

		/* HELPERS */
		@SideOnly (Side.CLIENT)
		public void registerModel(Item item, String stackName) {

			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(ThermalFoundation.MOD_ID + ":armor", "type=" + stackName));
		}

		/* IModelRegister */
		@Override
		@SideOnly (Side.CLIENT)
		public void registerModels() {

			registerModel(itemHelmet, "helmet_" + name);
			registerModel(itemChestplate, "chestplate_" + name);
			registerModel(itemLegs, "leggings_" + name);
			registerModel(itemBoots, "boots_" + name);
		}
	}

	/* TOOLS */
	public enum ToolSet implements IModelRegister {

		// @formatter:off
		COPPER("copper", TOOL_MATERIAL_COPPER, "ingotCopper"),
		TIN("tin", TOOL_MATERIAL_TIN, "ingotTin"),
		SILVER("silver", TOOL_MATERIAL_SILVER, "ingotSilver"),
		LEAD("lead", TOOL_MATERIAL_LEAD, "ingotLead"),
		ALUMINUM("aluminum", TOOL_MATERIAL_ALUMINUM, "ingotAluminum"),
		NICKEL("nickel", TOOL_MATERIAL_NICKEL, "ingotNickel"),
		PLATINUM("platinum", TOOL_MATERIAL_PLATINUM, "ingotPlatinum"),
		// IRIDIUM
		// MITHRIL
		STEEL("steel", TOOL_MATERIAL_STEEL, "ingotSteel"),
		ELECTRUM("electrum", TOOL_MATERIAL_ELECTRUM, "ingotElectrum"),
		INVAR("invar",TOOL_MATERIAL_INVAR, "ingotInvar"),
		BRONZE("bronze", TOOL_MATERIAL_BRONZE, "ingotBronze"),
		CONSTANTAN("constantan", TOOL_MATERIAL_CONSTANTAN, "ingotConstantan");
		// @formatter:on

		public final String name;
		public final String ingot;
		public final ToolMaterial material;

		/* BOW */
		private float arrowDamage;
		private float arrowSpeed;
		private float zoomMultiplier = 0.15F;

		/* FISHING ROD */
		private int luckModifier;
		private int speedModifier;

		/* TOOLS */
		public ItemSwordCore itemSword;
		public ItemShovelCore itemShovel;
		public ItemPickaxeCore itemPickaxe;
		public ItemAxeCore itemAxe;
		public ItemHoeCore itemHoe;
		public ItemBowCore itemBow;
		public ItemFishingRodCore itemFishingRod;
		public ItemShearsCore itemShears;
		public ItemSickleCore itemSickle;
		public ItemHammerCore itemHammer;
		public ItemExcavatorCore itemExcavator;
		public ItemShieldCore itemShield;

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
		public ItemStack toolExcavator;
		public ItemStack toolShield;

		public boolean[] enable = new boolean[12];

		ToolSet(String name, ToolMaterial materialIn, String ingot) {

			this.name = name.toLowerCase(Locale.US);
			this.ingot = ingot;
			this.material = materialIn;

			arrowDamage = material.getAttackDamage() / 4;
			arrowSpeed = material.getEfficiency() / 20;
			zoomMultiplier = MathHelper.clamp(material.getEfficiency() / 30, zoomMultiplier, zoomMultiplier * 2);

			luckModifier = material.getHarvestLevel() / 2;
			speedModifier = (int) material.getEfficiency() / 3;
		}

		protected void create() {

			itemSword = new ItemSwordCore(material);
			itemShovel = new ItemShovelCore(material);
			itemPickaxe = new ItemPickaxeCore(material);
			itemAxe = new ItemAxeCore(material);
			itemHoe = new ItemHoeCore(material);
			itemBow = new ItemBowCore(material);
			itemFishingRod = new ItemFishingRodCore(material);
			itemShears = new ItemShearsCore(material);
			itemSickle = new ItemSickleCore(material);
			itemHammer = new ItemHammerCore(material);
			itemExcavator = new ItemExcavatorCore(material);
			itemShield = new ItemShieldCore(material);
		}

		protected void preInit() {

			final String TOOL = "thermalfoundation.tool." + name;

			String category = "Equipment.Tools." + StringHelper.titleCase(name);

			enable[0] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Sword", true).getBoolean(true);
			enable[1] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Shovel", true).getBoolean(true);
			enable[2] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Pickaxe", true).getBoolean(true);
			enable[3] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Axe", true).getBoolean(true);
			enable[4] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Hoe", true).getBoolean(true);
			enable[5] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Bow", true).getBoolean(true) & !TFProps.disableAllBows;
			enable[6] = ThermalFoundation.CONFIG.getConfiguration().get(category, "FishingRod", true).getBoolean(true) & !TFProps.disableAllFishingRods;
			enable[7] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Shears", true).getBoolean(true) & !TFProps.disableAllShears;
			enable[8] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Sickle", true).getBoolean(true);
			enable[9] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Hammer", true).getBoolean(true);
			enable[10] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Excavator", true).getBoolean(true);
			enable[11] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Shield", true).getBoolean(true) & !TFProps.disableAllShields;

			for (int i = 0; i < enable.length; i++) {
				enable[i] &= !TFProps.disableAllTools;
			}
			create();

			/* SWORD */
			itemSword.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Sword").setCreativeTab(ThermalFoundation.tabBasicCombat);
			itemSword.setShowInCreative(enable[0] | TFProps.showDisabledEquipment);
			itemSword.setRegistryName("tool.sword_" + name);
			ForgeRegistries.ITEMS.register(itemSword);

			/* SHOVEL */
			itemShovel.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Shovel").setCreativeTab(ThermalFoundation.tabBasicTools);
			itemShovel.setShowInCreative(enable[1] | TFProps.showDisabledEquipment);
			itemShovel.setRegistryName("tool.shovel_" + name);
			ForgeRegistries.ITEMS.register(itemShovel);

			/* PICKAXE */
			itemPickaxe.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Pickaxe").setCreativeTab(ThermalFoundation.tabBasicTools);
			itemPickaxe.setShowInCreative(enable[2] | TFProps.showDisabledEquipment);
			itemPickaxe.setRegistryName("tool.pickaxe_" + name);
			ForgeRegistries.ITEMS.register(itemPickaxe);

			/* AXE */
			itemAxe.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Axe").setCreativeTab(ThermalFoundation.tabBasicTools);
			itemAxe.setShowInCreative(enable[3] | TFProps.showDisabledEquipment);
			itemAxe.setRegistryName("tool.axe_" + name);
			ForgeRegistries.ITEMS.register(itemAxe);

			/* HOE */
			itemHoe.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Hoe").setCreativeTab(ThermalFoundation.tabBasicTools);
			itemHoe.setShowInCreative(enable[4] | TFProps.showDisabledEquipment);
			itemHoe.setRegistryName("tool.hoe_" + name);
			ForgeRegistries.ITEMS.register(itemHoe);

			/* BOW */
			itemBow.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Bow").setCreativeTab(ThermalFoundation.tabBasicCombat);
			itemBow.setArrowDamage(arrowDamage).setArrowSpeed(arrowSpeed).setZoomMultiplier(zoomMultiplier);
			itemBow.setShowInCreative(enable[5] | TFProps.showDisabledEquipment);
			itemBow.setRegistryName("tool.bow_" + name);
			ForgeRegistries.ITEMS.register(itemBow);

			/* FISHING ROD */
			itemFishingRod.setRepairIngot(ingot).setUnlocalizedName(TOOL + "FishingRod").setCreativeTab(ThermalFoundation.tabBasicTools);
			itemFishingRod.setLuckModifier(luckModifier).setSpeedModifier(speedModifier);
			itemFishingRod.setShowInCreative(enable[6]);
			itemFishingRod.setRegistryName("tool.fishing_rod_" + name);
			ForgeRegistries.ITEMS.register(itemFishingRod);

			/* SHEARS */
			itemShears.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Shears").setCreativeTab(ThermalFoundation.tabBasicTools);
			itemShears.setShowInCreative(enable[7] | TFProps.showDisabledEquipment);
			itemShears.setRegistryName("tool.shears_" + name);
			ForgeRegistries.ITEMS.register(itemShears);

			/* SICKLE */
			itemSickle.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Sickle").setCreativeTab(ThermalFoundation.tabBasicTools);
			itemSickle.setShowInCreative(enable[8] | TFProps.showDisabledEquipment);
			itemSickle.setRegistryName("tool.sickle_" + name);
			ForgeRegistries.ITEMS.register(itemSickle);

			/* HAMMER */
			itemHammer.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Hammer").setCreativeTab(ThermalFoundation.tabBasicTools);
			itemHammer.setShowInCreative(enable[9] | TFProps.showDisabledEquipment);
			itemHammer.setRegistryName("tool.hammer_" + name);
			ForgeRegistries.ITEMS.register(itemHammer);

			/* EXCAVATOR */
			itemExcavator.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Excavator").setCreativeTab(ThermalFoundation.tabBasicTools);
			itemExcavator.setShowInCreative(enable[10] | TFProps.showDisabledEquipment);
			itemExcavator.setRegistryName("tool.excavator_" + name);
			ForgeRegistries.ITEMS.register(itemExcavator);

			/* SHIELD */
			itemShield.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Shield").setCreativeTab(ThermalFoundation.tabBasicCombat);
			itemShield.setShowInCreative(enable[11] | TFProps.showDisabledEquipment);
			itemShield.setRegistryName("tool.shield_" + name);
			ForgeRegistries.ITEMS.register(itemShield);


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
			toolExcavator = new ItemStack(itemExcavator);
			toolShield = new ItemStack(itemShield);
		}

		protected void initialize() {

			if (enable[0]) {
				addShapedRecipe(toolSword, "I", "I", "S", 'I', ingot, 'S', "stickWood");
			}
			if (enable[1]) {
				addShapedRecipe(toolShovel, "I", "S", "S", 'I', ingot, 'S', "stickWood");
			}
			if (enable[2]) {
				addShapedRecipe(toolPickaxe, "III", " S ", " S ", 'I', ingot, 'S', "stickWood");
			}
			if (enable[3]) {
				addShapedRecipe(toolAxe, "II", "IS", " S", 'I', ingot, 'S', "stickWood");
			}
			if (enable[4]) {
				addShapedRecipe(toolHoe, "II", " S", " S", 'I', ingot, 'S', "stickWood");
			}
			if (enable[5]) {
				addShapedRecipe(toolBow, " I#", "S #", " I#", 'I', ingot, 'S', "stickWood", '#', "string");
			}
			if (enable[6]) {
				addShapedRecipe(toolFishingRod, "  I", " I#", "S #", 'I', ingot, 'S', "stickWood", '#', "string");
			}
			if (enable[7]) {
				addShapedRecipe(toolShears, " I", "I ", 'I', ingot);
			}
			if (enable[8]) {
				addShapedRecipe(toolSickle, " I ", "  I", "SI ", 'I', ingot, 'S', "stickWood");
			}
			if (enable[9]) {
				addShapedRecipe(toolHammer, "III", "ISI", " S ", 'I', ingot, 'S', "stickWood");
			}
			if (enable[10]) {
				addShapedRecipe(toolExcavator, " I ", "ISI", " S ", 'I', ingot, 'S', "stickWood");
			}
			if (enable[11]) {
				addShapedRecipe(toolShield, "III", "ISI", " I ", 'I', ingot, 'S', Items.SHIELD);
			}
		}

		/* HELPERS */
		@SideOnly (Side.CLIENT)
		public void registerModel(Item item, String stackName) {

			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(ThermalFoundation.MOD_ID + ":tool", "type=" + stackName));
		}

		@SideOnly (Side.CLIENT)
		public void registerModelOverride(Item item, String stackName) {

			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(ThermalFoundation.MOD_ID + ":tool/" + stackName, "inventory"));
		}

		/* IModelRegister */
		@Override
		@SideOnly (Side.CLIENT)
		public void registerModels() {

			registerModel(itemSword, "sword_" + name);
			registerModel(itemShovel, "shovel_" + name);
			registerModel(itemPickaxe, "pickaxe_" + name);
			registerModel(itemAxe, "axe_" + name);
			registerModel(itemHoe, "hoe_" + name);
			registerModelOverride(itemBow, "bow_" + name);
			registerModelOverride(itemFishingRod, "fishing_rod_" + name);
			registerModel(itemShears, "shears_" + name);
			registerModel(itemSickle, "sickle_" + name);
			registerModel(itemHammer, "hammer_" + name);
			registerModel(itemExcavator, "excavator_" + name);
			registerModelOverride(itemShield, "shield_" + name);
		}
	}

	/* VANILLA */
	public enum ToolSetVanilla implements IModelRegister {

		// @formatter:off
    	WOOD("wood", ToolMaterial.WOOD, "plankWood") {

    		@Override
    		protected void create() {

    			itemBow = Items.BOW;
    			itemFishingRod = Items.FISHING_ROD;
				itemShears = new ItemShearsCore(material);
    			itemSickle = new ItemSickleCore(material);
    			itemHammer = new ItemHammerCore(material);
    			itemExcavator = new ItemExcavatorCore(material);
    			itemShield = Items.SHIELD;
    		}
    	},
    	STONE("stone", ToolMaterial.STONE, "cobblestone"),
    	IRON("iron", ToolMaterial.IRON, "ingotIron") {

    		@Override
    		protected void create() {

				itemBow = new ItemBowCore(material);
    			itemFishingRod = new ItemFishingRodCore(material);
				itemShears = Items.SHEARS;
    			itemSickle = new ItemSickleCore(material);
				itemHammer = new ItemHammerCore(material);
			    itemExcavator = new ItemExcavatorCore(material);
				itemShield = new ItemShieldCore(material);

    		}
    	},
    	DIAMOND("diamond", ToolMaterial.DIAMOND, "gemDiamond"),
    	GOLD("gold", ToolMaterial.GOLD, "ingotGold");
		// @formatter:on

		public final String name;
		public final String ingot;
		public final ToolMaterial material;

		/* BOW */
		private float arrowSpeed;
		private float arrowDamage;
		private float zoomMultiplier = 0.15F;

		/* FISHING ROD */
		private int luckModifier;
		private int speedModifier;

		/* TOOLS */
		public ItemBow itemBow;
		public ItemFishingRod itemFishingRod;
		public ItemShears itemShears;
		public ItemSickleCore itemSickle;
		public ItemHammerCore itemHammer;
		public ItemExcavatorCore itemExcavator;
		public Item itemShield;

		public ItemStack toolBow;
		public ItemStack toolFishingRod;
		public ItemStack toolShears;
		public ItemStack toolSickle;
		public ItemStack toolHammer;
		public ItemStack toolExcavator;
		public ItemStack toolShield;

		public boolean[] enable = new boolean[7];

		ToolSetVanilla(String name, ToolMaterial materialIn, String ingot) {

			this.name = name.toLowerCase(Locale.US);
			this.ingot = ingot;
			this.material = materialIn;

			/* BOW */
			arrowDamage = material.getAttackDamage() / 4;
			arrowSpeed = material.getEfficiency() / 20;
			zoomMultiplier = MathHelper.clamp(material.getEfficiency() / 30, 0, zoomMultiplier * 2);

			/* FISHING ROD */
			luckModifier = material.getHarvestLevel() / 2;
			speedModifier = (int) material.getEfficiency() / 3;
		}

		protected void create() {

			itemBow = new ItemBowCore(material);
			itemFishingRod = new ItemFishingRodCore(material);
			itemShears = new ItemShearsCore(material);
			itemSickle = new ItemSickleCore(material);
			itemHammer = new ItemHammerCore(material);
			itemExcavator = new ItemExcavatorCore(material);
			itemShield = new ItemShieldCore(material);
		}

		protected boolean enableDefault(ToolSetVanilla type) {

			return type != WOOD && type != STONE;
		}

		protected void preInit() {

			final String TOOL = "thermalfoundation.tool." + name;

			String category = "Equipment.Tool." + StringHelper.titleCase(name);

			if (this != WOOD) {
				enable[0] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Bow", enableDefault(this)).getBoolean(enableDefault(this)) & !TFProps.disableAllBows;
				enable[1] = ThermalFoundation.CONFIG.getConfiguration().get(category, "FishingRod", enableDefault(this)).getBoolean(enableDefault(this)) & !TFProps.disableAllFishingRods;
			}
			if (this != IRON) {
				enable[2] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Shears", enableDefault(this)).getBoolean(enableDefault(this)) & !TFProps.disableAllShears;
			}
			enable[3] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Sickle", enableDefault(this)).getBoolean(enableDefault(this));
			enable[4] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Hammer", enableDefault(this)).getBoolean(enableDefault(this));
			enable[5] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Excavator", enableDefault(this)).getBoolean(enableDefault(this));

			if (this != WOOD) {
				enable[6] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Shield", enableDefault(this)).getBoolean(enableDefault(this)) & !TFProps.disableAllShields;
			}
			for (int i = 0; i < enable.length; i++) {
				enable[i] &= !TFProps.disableAllTools;
				enable[i] &= !TFProps.disableVanillaTools;
			}
			create();

			/* BOW */
			if (itemBow instanceof ItemBowCore) {
				ItemBowCore itemBow = (ItemBowCore) this.itemBow;
				itemBow.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Bow").setCreativeTab(CreativeTabs.COMBAT);
				itemBow.setArrowDamage(arrowDamage).setArrowSpeed(arrowSpeed).setZoomMultiplier(zoomMultiplier);
				itemBow.setShowInCreative(enable[0] | TFProps.showDisabledEquipment);
				itemBow.setRegistryName("tool.bow_" + name);
				ForgeRegistries.ITEMS.register(itemBow);
			}

			/* FISHING ROD */
			if (itemFishingRod instanceof ItemFishingRodCore) {
				ItemFishingRodCore itemFishingRod = (ItemFishingRodCore) this.itemFishingRod;
				itemFishingRod.setRepairIngot(ingot).setUnlocalizedName(TOOL + "FishingRod").setCreativeTab(CreativeTabs.TOOLS);
				itemFishingRod.setLuckModifier(luckModifier).setSpeedModifier(speedModifier);
				itemFishingRod.setShowInCreative(enable[1] | TFProps.showDisabledEquipment);
				itemFishingRod.setRegistryName("tool.fishing_rod_" + name);
				ForgeRegistries.ITEMS.register(itemFishingRod);
			}

			/* SHEARS */
			if (itemShears instanceof ItemShearsCore) {
				ItemShearsCore itemShears = (ItemShearsCore) this.itemShears;
				itemShears.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Shears").setCreativeTab(CreativeTabs.TOOLS);
				itemShears.setShowInCreative(enable[2] | TFProps.showDisabledEquipment);
				itemShears.setRegistryName("tool.shears_" + name);
				ForgeRegistries.ITEMS.register(itemShears);
			}

			/* SICKLE */
			itemSickle.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Sickle").setCreativeTab(CreativeTabs.TOOLS);
			itemSickle.setShowInCreative(enable[3] | TFProps.showDisabledEquipment);
			itemSickle.setRegistryName("tool.sickle_" + name);
			ForgeRegistries.ITEMS.register(itemSickle);

			/* HAMMER */
			itemHammer.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Hammer").setCreativeTab(CreativeTabs.TOOLS);
			itemHammer.setShowInCreative(enable[4] | TFProps.showDisabledEquipment);
			itemHammer.setRegistryName("tool.hammer_" + name);
			ForgeRegistries.ITEMS.register(itemHammer);

			/* EXCAVATOR */
			itemExcavator.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Excavator").setCreativeTab(CreativeTabs.TOOLS);
			itemExcavator.setShowInCreative(enable[5] | TFProps.showDisabledEquipment);
			itemExcavator.setRegistryName("tool.excavator_" + name);
			ForgeRegistries.ITEMS.register(itemExcavator);

			/* SHIELD */
			if (itemShield instanceof ItemShieldCore) {
				((ItemShieldCore) itemShield).setRepairIngot(ingot).setUnlocalizedName(TOOL + "Shield").setCreativeTab(CreativeTabs.COMBAT);
				((ItemShieldCore) itemShield).setShowInCreative(enable[6] | TFProps.showDisabledEquipment);
				itemShield.setRegistryName("tool.shield_" + name);
				ForgeRegistries.ITEMS.register(itemShield);
			}

			toolBow = new ItemStack(itemBow);
			toolFishingRod = new ItemStack(itemFishingRod);
			toolShears = new ItemStack(itemShears);
			toolSickle = new ItemStack(itemSickle);
			toolHammer = new ItemStack(itemHammer);
			toolExcavator = new ItemStack(itemExcavator);
			toolShield = new ItemStack(itemShield);
		}

		protected void initialize() {

			if (enable[0]) {
				addShapedRecipe(toolBow, " I#", "S #", " I#", 'I', ingot, 'S', "stickWood", '#', "string");
			}
			if (enable[1]) {
				addShapedRecipe(toolFishingRod, "  I", " I#", "S #", 'I', ingot, 'S', "stickWood", '#', "string");
			}
			if (enable[2]) {
				addShapedRecipe(toolShears, " I", "I ", 'I', ingot);
			}
			if (enable[3]) {
				addShapedRecipe(toolSickle, " I ", "  I", "SI ", 'I', ingot, 'S', "stickWood");
			}
			if (enable[4]) {
				addShapedRecipe(toolHammer, "III", "ISI", " S ", 'I', ingot, 'S', "stickWood");
			}
			if (enable[5]) {
				addShapedRecipe(toolExcavator, " I ", "ISI", " S ", 'I', ingot, 'S', "stickWood");
			}
			if (enable[6]) {
				addShapedRecipe(toolShield, "III", "ISI", " I ", 'I', ingot, 'S', Items.SHIELD);
			}
		}

		/* HELPERS */
		@SideOnly (Side.CLIENT)
		public void registerModel(Item item, String stackName) {

			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(ThermalFoundation.MOD_ID + ":tool", "type=" + stackName));
		}

		@SideOnly (Side.CLIENT)
		public void registerModelOverride(Item item, String stackName) {

			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(ThermalFoundation.MOD_ID + ":tool/" + stackName, "inventory"));
		}

		/* IModelRegister */
		@Override
		@SideOnly (Side.CLIENT)
		public void registerModels() {

			if (itemBow instanceof ItemBowCore) {
				registerModelOverride(itemBow, "bow_" + name);
			}
			if (itemFishingRod instanceof ItemFishingRodCore) {
				registerModelOverride(itemFishingRod, "fishing_rod_" + name);
			}
			if (itemShears instanceof ItemShearsCore) {
				registerModel(itemShears, "shears_" + name);
			}
			registerModel(itemSickle, "sickle_" + name);
			registerModel(itemHammer, "hammer_" + name);
			registerModel(itemExcavator, "excavator_" + name);

			if (itemShield instanceof ItemShieldCore) {
				registerModelOverride(itemShield, "shield_" + name);
			}
		}
	}

	/* HORSE ARMOR */
	public enum HorseArmor implements IModelRegister {

		// @formatter:off
		COPPER("copper", HORSE_ARMOR_COPPER, "ingotCopper"),
		TIN("tin", HORSE_ARMOR_TIN, "ingotTin"),
		SILVER("silver", HORSE_ARMOR_SILVER, "ingotSilver"),
		LEAD("lead", HORSE_ARMOR_LEAD, "ingotLead"),
		ALUMINUM("aluminum", HORSE_ARMOR_ALUMINUM, "ingotAluminum"),
		NICKEL("nickel", HORSE_ARMOR_NICKEL, "ingotNickel"),
		PLATINUM("platinum", HORSE_ARMOR_PLATINUM, "ingotPlatinum"),
		// IRIDIUM
		// MITHRIL
		STEEL("steel", HORSE_ARMOR_STEEL, "ingotSteel"),
		ELECTRUM("electrum", HORSE_ARMOR_ELECTRUM, "ingotElectrum"),
		INVAR("invar", HORSE_ARMOR_INVAR, "ingotInvar"),
		BRONZE("bronze", HORSE_ARMOR_BRONZE, "ingotBronze"),
		CONSTANTAN("constantan", HORSE_ARMOR_CONSTANTAN, "ingotConstantan");
		// @formatter:on

		public final String name;
		public final String ingot;
		public final HorseArmorType ARMOR_TYPE;

		public ItemHorseArmorCore itemArmor;

		public ItemStack armor;

		public boolean enable;

		HorseArmor(String name, HorseArmorType type, String ingot) {

			this.name = name.toLowerCase(Locale.US);
			this.ingot = ingot;
			ARMOR_TYPE = type;
		}

		protected void preInit() {

			final String ARMOR = "thermalfoundation.horseArmor." + name;

			String category = "Items.HorseArmor";

			enable = ThermalFoundation.CONFIG.getConfiguration().get(category, StringHelper.titleCase(name), true).getBoolean(true);
			enable &= TFProps.enableHorseArmorCrafting;
			itemArmor = new ItemHorseArmorCore(ARMOR_TYPE);

			itemArmor.setUnlocalizedName(ARMOR).setCreativeTab(CreativeTabs.MISC);
			itemArmor.setShowInCreative(enable);
			itemArmor.setRegistryName("horse_armor_" + name);
			ForgeRegistries.ITEMS.register(itemArmor);

			/* REFERENCES */
			armor = new ItemStack(itemArmor);
		}

		protected void initialize() {

			if (enable) {
				addShapedRecipe(armor, "I I", "LCL", "I I", 'C', "blockWool", 'L', Items.LEATHER, 'I', ingot);
			}
		}

		/* HELPERS */
		@SideOnly (Side.CLIENT)
		public void registerModel(Item item, String stackName) {

			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(ThermalFoundation.MOD_ID + ":horse_armor", "type=" + stackName));
		}

		/* IModelRegister */
		@Override
		@SideOnly (Side.CLIENT)
		public void registerModels() {

			registerModel(itemArmor, "horse_armor_" + name);
		}
	}

}
