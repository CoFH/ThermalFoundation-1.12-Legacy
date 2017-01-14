package cofh.thermalfoundation.item;

import codechicken.lib.model.ModelRegistryHelper;
import codechicken.lib.model.SimpleOverrideBakedModel;
import cofh.core.item.ItemArmorAdv;
import cofh.core.item.tool.*;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.client.model.BowModelOverrideList;
import cofh.thermalfoundation.client.model.FishingRodModelOverrideList;
import cofh.thermalfoundation.client.model.TFBakedModelProvider;
import cofh.thermalfoundation.core.TFProps;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Locale;

import static cofh.lib.util.helpers.ItemHelper.ShapedRecipe;

public enum Equipment {

	// @formatter:off
	/* Name, Level, Uses, Speed, Damage, Ench, Dura, Absorption */
	Copper(      1,  175,  4.0F,  0.75F,    6,    6, new int[] { 1, 3, 3, 1 }, 0.0F, -3.2F),
	Tin(         1,  200,  4.5F,   1.0F,    7,    8, new int[] { 1, 4, 3, 1 }, 0.0F, -3.1F),
	Silver(      2,  200,  6.0F,   1.5F,   20,   11, new int[] { 2, 4, 4, 1 }, 0.0F, -3.1F),
	Lead(        1,  150,    5F,   1.0F,    9,   15, new int[] { 2, 5, 4, 3 }, 0.0F, -3.1F) {

		@Override
		protected final void createArmor() {

			AttributeModifier knockbackBonus;
			AttributeModifier movementBonus;

			knockbackBonus = new AttributeModifier("lead weight bonus", .25, 0);
			movementBonus = new AttributeModifier("lead weight bonus", -.15, 2);
			itemHelmet = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.HEAD);
			itemHelmet.putAttribute("generic.knockbackResistance", knockbackBonus);
			itemHelmet.putAttribute("generic.movementSpeed", movementBonus);
			knockbackBonus = new AttributeModifier("lead weight bonus", .25, 0);
			movementBonus = new AttributeModifier("lead weight bonus", -.15, 2);
			itemPlate = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.CHEST);
			itemPlate.putAttribute("generic.knockbackResistance", knockbackBonus);
			itemPlate.putAttribute("generic.movementSpeed", movementBonus);
			knockbackBonus = new AttributeModifier("lead weight bonus", .25, 0);
			movementBonus = new AttributeModifier("lead weight bonus", -.15, 2);
			itemLegs = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.LEGS);
			itemLegs.putAttribute("generic.knockbackResistance", knockbackBonus);
			itemLegs.putAttribute("generic.movementSpeed", movementBonus);
			knockbackBonus = new AttributeModifier("lead weight bonus", .25, 0);
			movementBonus = new AttributeModifier("lead weight bonus", -.15, 2);
			itemBoots = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.FEET);
			itemBoots.putAttribute("generic.knockbackResistance", knockbackBonus);
			itemBoots.putAttribute("generic.movementSpeed", movementBonus);
		}
	},
	Nickel(      2,  300,  6.5F,   2.5F,   18,   15, new int[] { 2, 5, 5, 2 }, 0.0F, -3.1F),
	Electrum(    0,  100, 14.0F,   0.5F,   30,    8, new int[] { 2, 4, 4, 2 }, 1.0F, -3.0F),
	Invar(       2,  450,  7.0F,   3.0F,   16,   21, new int[] { 2, 7, 5, 2 }, 1.0F, -3.0F),
	Bronze(      2,  500,  6.0F,   2.0F,   15,   18, new int[] { 3, 6, 6, 2 }, 1.0F, -3.0F),
	Platinum(    4, 1700,  9.0F,   4.0F,    9,   40, new int[] { 3, 8, 6, 3 }, 2.0F, -2.9F) {

		@Override
		protected final void createArmor() {

			AttributeModifier knockbackBonus;
			AttributeModifier movementBonus;

			knockbackBonus = new AttributeModifier("platinum weight bonus", .20, 0);
			movementBonus = new AttributeModifier("platinum weight bonus", -.08, 2);
			itemHelmet = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.HEAD);
			itemHelmet.putAttribute("generic.knockbackResistance", knockbackBonus);
			itemHelmet.putAttribute("generic.movementSpeed", movementBonus);
			knockbackBonus = new AttributeModifier("platinum weight bonus", .25, 0);
			movementBonus = new AttributeModifier("platinum weight bonus", -.08, 2);
			itemPlate = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.CHEST);
			itemPlate.putAttribute("generic.knockbackResistance", knockbackBonus);
			itemPlate.putAttribute("generic.movementSpeed", movementBonus);
			knockbackBonus = new AttributeModifier("platinum weight bonus", .25, 0);
			movementBonus = new AttributeModifier("platinum weight bonus", -.08, 2);
			itemLegs = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.LEGS);
			itemLegs.putAttribute("generic.knockbackResistance", knockbackBonus);
			itemLegs.putAttribute("generic.movementSpeed", movementBonus);
			knockbackBonus = new AttributeModifier("platinum weight bonus", .20, 0);
			movementBonus = new AttributeModifier("platinum weight bonus", -.08, 2);
			itemBoots = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.FEET);
			itemBoots.putAttribute("generic.knockbackResistance", knockbackBonus);
			itemBoots.putAttribute("generic.movementSpeed", movementBonus);
		}
	},
	;
	// @formatter:on

	public final ToolMaterial TOOL_MATERIAL;
	public final ArmorMaterial ARMOR_MATERIAL;

	private final String ingot;
	private final float arrowSpeed = 2.0F;
	private float arrowDamage = 1.0F;
	private int luckModifier = 0;
	private int speedModifier = 0;

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
	private float axeAttackSpeed;

	Equipment(int level, int uses, float speed, float damage, int enchant, int durability, int[] absorb, float armorToughness, float axeAttackSpeed) {

		TOOL_MATERIAL = EnumHelper.addToolMaterial("TF:" + name().toUpperCase(Locale.US), level, uses, speed, damage, enchant);
		ARMOR_MATERIAL = EnumHelper.addArmorMaterial("TF:" + name().toUpperCase(Locale.US), name(), durability, absorb, enchant, SoundEvents.ITEM_ARMOR_EQUIP_IRON, armorToughness); //TODO toughness;
		ingot = "ingot" + name();

		/* Fishing Rod */
		luckModifier = level / 2;
		speedModifier = (int) (speed / 5);

		/* Bow */
		// arrowSpeed = 2.0F + speed / 8F;
		arrowDamage = 1.0F + damage / 8F;
		this.axeAttackSpeed = axeAttackSpeed;
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
		itemShears = new ItemShearsAdv(TOOL_MATERIAL);
		itemFishingRod = new ItemFishingRodAdv(TOOL_MATERIAL);
		itemSickle = new ItemSickleAdv(TOOL_MATERIAL);
		itemBow = new ItemBowAdv(TOOL_MATERIAL);
	}

	protected void preInitMaterial() {

		final String NAME = name();
		final String TYPE = NAME.toLowerCase(Locale.US);
		final String ARMOR = "thermalfoundation.armor." + TYPE;
		final String TOOL = "thermalfoundation.tool." + TYPE;

		String category = "Equipment." + NAME;
		enableArmor = ThermalFoundation.CONFIG.getConfiguration().get(category, "Armor", true).getBoolean(true);
		enableArmor &= !TFProps.disableAllArmor;

		category += ".Tools";
		enableTools[0] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Sword", true).getBoolean(true);
		enableTools[1] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Shovel", true).getBoolean(true);
		enableTools[2] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Pickaxe", true).getBoolean(true);
		enableTools[3] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Axe", true).getBoolean(true);
		enableTools[4] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Hoe", true).getBoolean(true);
		enableTools[5] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Shears", true).getBoolean(true);
		enableTools[6] = ThermalFoundation.CONFIG.getConfiguration().get(category, "FishingRod", true).getBoolean(true);
		enableTools[7] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Sickle", true).getBoolean(true);
		enableTools[8] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Bow", true).getBoolean(true);

		for (int i = 0; i < enableTools.length; i++) {
			enableTools[i] &= !TFProps.disableAllTools;
		}

		final String PATH_ARMOR = "thermalfoundation:textures/armor/";
		final String[] TEXTURE = { PATH_ARMOR + NAME.toLowerCase() + "_1.png", PATH_ARMOR + NAME.toLowerCase() + "_2.png" };

		createArmor();
		itemHelmet.setRepairIngot(ingot).setArmorTextures(TEXTURE).setUnlocalizedName(ARMOR + "Helmet").setCreativeTab(ThermalFoundation.tabArmor);//		itemHelmet.setTextureName(ARMOR_PATH + "Helmet");
		itemHelmet.setShowInCreative(enableArmor | TFProps.showDisabledEquipment);
		itemPlate.setRepairIngot(ingot).setArmorTextures(TEXTURE).setUnlocalizedName(ARMOR + "Plate").setCreativeTab(ThermalFoundation.tabArmor);//		itemPlate.setTextureName(ARMOR_PATH + "Chestplate");
		itemPlate.setShowInCreative(enableArmor | TFProps.showDisabledEquipment);
		itemLegs.setRepairIngot(ingot).setArmorTextures(TEXTURE).setUnlocalizedName(ARMOR + "Legs").setCreativeTab(ThermalFoundation.tabArmor);//		itemLegs.setTextureName(ARMOR_PATH + "Legs");
		itemLegs.setShowInCreative(enableArmor | TFProps.showDisabledEquipment);
		itemBoots.setRepairIngot(ingot).setArmorTextures(TEXTURE).setUnlocalizedName(ARMOR + "Boots").setCreativeTab(ThermalFoundation.tabArmor);//		itemBoots.setTextureName(ARMOR_PATH + "Boots");
		itemBoots.setShowInCreative(enableArmor | TFProps.showDisabledEquipment);

		createTools();
		itemSword.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Sword").setCreativeTab(ThermalFoundation.tabTools);//		itemSword.setTextureName(TOOL_PATH + "Sword");
		itemSword.setShowInCreative(enableTools[0] | TFProps.showDisabledEquipment);
		itemShovel.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Shovel").setCreativeTab(ThermalFoundation.tabTools);//		itemShovel.setTextureName(TOOL_PATH + "Shovel");
		itemShovel.setShowInCreative(enableTools[1] | TFProps.showDisabledEquipment);
		itemPickaxe.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Pickaxe").setCreativeTab(ThermalFoundation.tabTools);//		itemPickaxe.setTextureName(TOOL_PATH + "Pickaxe");
		itemPickaxe.setShowInCreative(enableTools[2] | TFProps.showDisabledEquipment);
		itemAxe.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Axe").setCreativeTab(ThermalFoundation.tabTools);//		itemAxe.setTextureName(TOOL_PATH + "Axe");
		itemAxe.setShowInCreative(enableTools[3] | TFProps.showDisabledEquipment);
		itemHoe.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Hoe").setCreativeTab(ThermalFoundation.tabTools);//		itemHoe.setTextureName(TOOL_PATH + "Hoe");
		itemHoe.setShowInCreative(enableTools[4] | TFProps.showDisabledEquipment);
		itemShears.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Shears").setCreativeTab(ThermalFoundation.tabTools);//		itemShears.setTextureName(TOOL_PATH + "Shears");
		itemShears.setShowInCreative(enableTools[5] | TFProps.showDisabledEquipment);
		itemFishingRod.setRepairIngot(ingot).setUnlocalizedName(TOOL + "FishingRod").setCreativeTab(ThermalFoundation.tabTools);//		itemFishingRod.setTextureName(TOOL_PATH + "FishingRod");
		itemFishingRod.setLuckModifier(luckModifier).setSpeedModifier(speedModifier);
		itemFishingRod.setShowInCreative(enableTools[6] | TFProps.showDisabledEquipment);
		itemSickle.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Sickle").setCreativeTab(ThermalFoundation.tabTools);//		itemSickle.setTextureName(TOOL_PATH + "Sickle");
		itemSickle.setShowInCreative(enableTools[7] | TFProps.showDisabledEquipment);
		itemBow.setRepairIngot(ingot).setArrowSpeed(arrowSpeed).setArrowDamage(arrowDamage).setUnlocalizedName(TOOL + "Bow").setCreativeTab(ThermalFoundation.tabTools);//		itemBow.setTextureName(TOOL_PATH + "Bow");
		itemBow.setShowInCreative(enableTools[8] | TFProps.showDisabledEquipment);

		itemHelmet.setRegistryName("armor.helmet" + NAME);
		itemPlate.setRegistryName("armor.plate" + NAME);
		itemLegs.setRegistryName("armor.legs" + NAME);
		itemBoots.setRegistryName("armor.boots" + NAME);
		itemSword.setRegistryName("tool.sword" + NAME);
		itemShovel.setRegistryName("tool.shovel" + NAME);
		itemPickaxe.setRegistryName("tool.pickaxe" + NAME);
		itemAxe.setRegistryName("tool.axe" + NAME);
		itemHoe.setRegistryName("tool.hoe" + NAME);
		itemShears.setRegistryName("tool.shears" + NAME);
		itemFishingRod.setRegistryName("tool.fishingRod" + NAME);
		itemSickle.setRegistryName("tool.sickle" + NAME);
		itemBow.setRegistryName("tool.bow" + NAME);

		GameRegistry.register(itemHelmet);
		GameRegistry.register(itemPlate);
		GameRegistry.register(itemLegs);
		GameRegistry.register(itemBoots);
		GameRegistry.register(itemSword);
		GameRegistry.register(itemShovel);
		GameRegistry.register(itemPickaxe);
		GameRegistry.register(itemAxe);
		GameRegistry.register(itemHoe);
		GameRegistry.register(itemShears);
		GameRegistry.register(itemFishingRod);
		GameRegistry.register(itemSickle);
		GameRegistry.register(itemBow);
	}

	protected void initializeMaterial() {

		final String NAME = name();

		// Armor
		armorHelmet = new ItemStack(itemHelmet);
		armorPlate = new ItemStack(itemPlate);
		armorLegs = new ItemStack(itemLegs);
		armorBoots = new ItemStack(itemBoots);

		//		GameRegistry.registerCustomItemStack("armor" + NAME + "Helmet", armorHelmet);
		//		GameRegistry.registerCustomItemStack("armor" + NAME + "Plate", armorPlate);
		//		GameRegistry.registerCustomItemStack("armor" + NAME + "Legs", armorLegs);
		//		GameRegistry.registerCustomItemStack("armor" + NAME + "Boots", armorBoots);

		//		itemHelmet.setRegistryName("armor" + NAME + "Helmet");
		//		itemPlate.setRegistryName("armor" + NAME + "Plate");
		//		itemLegs.setRegistryName("armor" + NAME + "Legs");
		//		itemBoots.setRegistryName("armor" + NAME + "Boots");

		//		GameRegistry.register(itemHelmet);
		//		GameRegistry.register(itemPlate);
		//		GameRegistry.register(itemLegs);
		//		GameRegistry.register(itemBoots);

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

		//		GameRegistry.registerCustomItemStack("tool" + NAME + "Sword", toolSword);
		//		GameRegistry.registerCustomItemStack("tool" + NAME + "Shovel", toolShovel);
		//		GameRegistry.registerCustomItemStack("tool" + NAME + "Pickaxe", toolPickaxe);
		//		GameRegistry.registerCustomItemStack("tool" + NAME + "Axe", toolAxe);
		//		GameRegistry.registerCustomItemStack("tool" + NAME + "Hoe", toolHoe);
		//		GameRegistry.registerCustomItemStack("tool" + NAME + "Shears", toolShears);
		//		GameRegistry.registerCustomItemStack("tool" + NAME + "FishingRod", toolFishingRod);
		//		GameRegistry.registerCustomItemStack("tool" + NAME + "Sickle", toolSickle);
		//		GameRegistry.registerCustomItemStack("tool" + NAME + "Bow", toolBow);

		//		itemSword.setRegistryName("tool" + NAME + "Sword");
		//		itemShovel.setRegistryName("tool" + NAME + "Shovel");
		//		itemPickaxe.setRegistryName("tool" + NAME + "Pickaxe");
		//		itemAxe.setRegistryName("tool" + NAME + "Axe");
		//		itemHoe.setRegistryName("tool" + NAME + "Hoe");
		//		itemShears.setRegistryName("tool" + NAME + "Shears");
		//		itemFishingRod.setRegistryName("tool" + NAME + "FishingRod");
		//		itemSickle.setRegistryName("tool" + NAME + "Sickle");
		//		itemBow.setRegistryName("tool" + NAME + "Bow");

		//		GameRegistry.register(itemSword);
		//		GameRegistry.register(itemShovel);
		//		GameRegistry.register(itemPickaxe);
		//		GameRegistry.register(itemAxe);
		//		GameRegistry.register(itemHoe);
		//		GameRegistry.register(itemShears);
		//		GameRegistry.register(itemFishingRod);
		//		GameRegistry.register(itemSickle);
		//		GameRegistry.register(itemBow);
	}

	protected void postInitMaterial() {

		// Armor
		if (enableArmor) {
			GameRegistry.addRecipe(ShapedRecipe(armorHelmet, "III", "I I", 'I', ingot));
			GameRegistry.addRecipe(ShapedRecipe(armorPlate, "I I", "III", "III", 'I', ingot));
			GameRegistry.addRecipe(ShapedRecipe(armorLegs, "III", "I I", "I I", 'I', ingot));
			GameRegistry.addRecipe(ShapedRecipe(armorBoots, "I I", "I I", 'I', ingot));
		}

		// Tools
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
			GameRegistry.addRecipe(ShapedRecipe(toolShears, " I", "I ", 'I', ingot));
		}
		if (enableTools[6]) {
			GameRegistry.addRecipe(ShapedRecipe(toolFishingRod, "  I", " I#", "S #", 'I', ingot, 'S', "stickWood", '#', Items.STRING));
		}
		if (enableTools[7]) {
			GameRegistry.addRecipe(ShapedRecipe(toolSickle, " I ", "  I", "SI ", 'I', ingot, 'S', "stickWood"));
		}
		if (enableTools[8]) {
			GameRegistry.addRecipe(ShapedRecipe(toolBow, " I#", "S #", " I#", 'I', ingot, 'S', "stickWood", '#', Items.STRING));
		}
	}

	public static void preInit() {

		//EquipmentVanilla.preInit();
		for (Equipment e : values()) {
			e.preInitMaterial();
		}
	}

	public static void initialize() {

		//EquipmentVanilla.initialize();
		for (Equipment e : values()) {
			e.initializeMaterial();
		}
	}

	public static void postInit() {

		//EquipmentVanilla.postInit();
		for (Equipment e : values()) {
			e.postInitMaterial();
		}
	}

	@SideOnly (Side.CLIENT)
	public static void registerModels() {

		for (Equipment e : values()) {
			e.registerMaterialModels();
		}
	}

	@SideOnly (Side.CLIENT)
	protected void registerMaterialModels() {

		//EquipmentVanilla.registerModels();
		registerModel(itemSword, 0, "sword");
		registerModel(itemShovel, 0, "shovel");
		registerModel(itemPickaxe, 0, "pickaxe");
		registerModel(itemAxe, 0, "axe");
		registerModel(itemHoe, 0, "hoe");
		registerModel(itemShears, 0, "shears");
		registerModel(itemSickle, 0, "sickle");

		registerArmorItemModel(itemHelmet, 0, "helmet");
		registerArmorItemModel(itemPlate, 0, "chestplate");
		registerArmorItemModel(itemLegs, 0, "legs");
		registerArmorItemModel(itemBoots, 0, "boots");

		ModelLoader.setCustomModelResourceLocation(itemBow, 0, new ModelResourceLocation(itemBow.getRegistryName(), "inventory"));
		TFBakedModelProvider.BOWS.put(itemBow, name().toLowerCase() + "_bow");
		ModelRegistryHelper.register(new ModelResourceLocation(itemBow.getRegistryName(), "inventory"), new SimpleOverrideBakedModel(new BowModelOverrideList()));

		ModelLoader.setCustomModelResourceLocation(itemFishingRod, 0, new ModelResourceLocation(itemFishingRod.getRegistryName(), "inventory"));
		TFBakedModelProvider.RODS.put(itemFishingRod, name().toLowerCase() + "_fishing_rod");
		ModelRegistryHelper.register(new ModelResourceLocation(itemFishingRod.getRegistryName(), "inventory"), new SimpleOverrideBakedModel(new FishingRodModelOverrideList()));
	}

	@SideOnly (Side.CLIENT)
	private void registerModel(Item item, int meta, String tool) {

		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation("thermalfoundation:tool", "type=" + name().toLowerCase() + tool));
	}

	@SideOnly (Side.CLIENT)
	private void registerArmorItemModel(Item item, int meta, String tool) {

		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation("thermalfoundation:armor", "type=" + name().toLowerCase() + tool));
	}
}
