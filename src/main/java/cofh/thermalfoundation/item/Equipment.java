package cofh.thermalfoundation.item;

import static cofh.lib.util.helpers.ItemHelper.ShapedRecipe;

import cofh.api.core.IModelRegister;
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
import cofh.thermalfoundation.core.TFProps;

import java.util.Locale;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public enum Equipment implements IModelRegister {

	// @formatter:off
	/* Name, Level, Uses, Speed, Damage, Ench, Dura, Absorption, Toughness */
	Copper(      1,  175,  4.0F,  0.75F,    6,    6, new int[] { 1, 3, 3, 1 }, 0.0F),
	Tin(         1,  200,  4.5F,   1.0F,    7,    8, new int[] { 1, 4, 3, 1 }, 0.0F),
	Silver(      2,  200,  6.0F,   1.5F,   20,   11, new int[] { 2, 4, 4, 1 }, 0.0F),
	Lead(        1,  150,    5F,   1.0F,    9,   15, new int[] { 2, 5, 4, 3 }, 0.0F) {

		@Override
		protected final void createArmor() {

			AttributeModifier knockbackBonus;
			AttributeModifier movementBonus;

			knockbackBonus = new AttributeModifier("lead weight bonus", .25, 0);
			movementBonus = new AttributeModifier("lead weight bonus", -.15, 2);
			helmet = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.HEAD);
			helmet.putAttribute("generic.knockbackResistance", knockbackBonus);
			helmet.putAttribute("generic.movementSpeed", movementBonus);
			knockbackBonus = new AttributeModifier("lead weight bonus", .25, 0);
			movementBonus = new AttributeModifier("lead weight bonus", -.15, 2);
			plate = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.CHEST);
			plate.putAttribute("generic.knockbackResistance", knockbackBonus);
			plate.putAttribute("generic.movementSpeed", movementBonus);
			knockbackBonus = new AttributeModifier("lead weight bonus", .25, 0);
			movementBonus = new AttributeModifier("lead weight bonus", -.15, 2);
			legs = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.LEGS);
			legs.putAttribute("generic.knockbackResistance", knockbackBonus);
			legs.putAttribute("generic.movementSpeed", movementBonus);
			knockbackBonus = new AttributeModifier("lead weight bonus", .25, 0);
			movementBonus = new AttributeModifier("lead weight bonus", -.15, 2);
			boots = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.FEET);
			boots.putAttribute("generic.knockbackResistance", knockbackBonus);
			boots.putAttribute("generic.movementSpeed", movementBonus);
		}
	},
	Nickel(      2,  300,  6.5F,   2.5F,   18,   15, new int[] { 2, 5, 5, 2 }, 0.0F),
	Electrum(    0,  100, 14.0F,   0.5F,   30,    8, new int[] { 2, 4, 4, 2 }, 1.0F),
	Invar(       2,  450,  7.0F,   3.0F,   16,   21, new int[] { 2, 7, 5, 2 }, 1.0F),
	Bronze(      2,  500,  6.0F,   2.0F,   15,   18, new int[] { 3, 6, 6, 2 }, 1.0F),
	Platinum(    4, 1700,  9.0F,   4.0F,    9,   40, new int[] { 3, 8, 6, 3 }, 2.0F) {

		@Override
		protected final void createArmor() {

			AttributeModifier knockbackBonus;
			AttributeModifier movementBonus;

			knockbackBonus = new AttributeModifier("platinum weight bonus", .20, 0);
			movementBonus = new AttributeModifier("platinum weight bonus", -.08, 2);
			helmet = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.HEAD);
			helmet.putAttribute("generic.knockbackResistance", knockbackBonus);
			helmet.putAttribute("generic.movementSpeed", movementBonus);
			knockbackBonus = new AttributeModifier("platinum weight bonus", .25, 0);
			movementBonus = new AttributeModifier("platinum weight bonus", -.08, 2);
			plate = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.CHEST);
			plate.putAttribute("generic.knockbackResistance", knockbackBonus);
			plate.putAttribute("generic.movementSpeed", movementBonus);
			knockbackBonus = new AttributeModifier("platinum weight bonus", .25, 0);
			movementBonus = new AttributeModifier("platinum weight bonus", -.08, 2);
			legs = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.LEGS);
			legs.putAttribute("generic.knockbackResistance", knockbackBonus);
			legs.putAttribute("generic.movementSpeed", movementBonus);
			knockbackBonus = new AttributeModifier("platinum weight bonus", .20, 0);
			movementBonus = new AttributeModifier("platinum weight bonus", -.08, 2);
			boots = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.FEET);
			boots.putAttribute("generic.knockbackResistance", knockbackBonus);
			boots.putAttribute("generic.movementSpeed", movementBonus);
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

	public ItemArmorAdv helmet;
	public ItemArmorAdv plate;
	public ItemArmorAdv legs;
	public ItemArmorAdv boots;

	public ItemSwordAdv sword;
	public ItemShovelAdv shovel;
	public ItemPickaxeAdv pickaxe;
	public ItemAxeAdv axe;
	public ItemHoeAdv hoe;
	public ItemShearsAdv shears;
	public ItemFishingRodAdv fishingRod;
	public ItemSickleAdv sickle;
	public ItemBowAdv bow;

	Equipment(int level, int uses, float speed, float damage, int enchant, int durability, int[] absorb, float toughness) {

		TOOL_MATERIAL = EnumHelper.addToolMaterial("TF:" + name().toUpperCase(Locale.US), level, uses, speed, damage, enchant);
		ARMOR_MATERIAL = EnumHelper.addArmorMaterial("TF:" + name().toUpperCase(Locale.US), "TF:" + name().toLowerCase(Locale.US), durability, absorb, enchant, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, toughness);
		ingot = "ingot" + name();

		/* Fishing Rod */
		luckModifier = level / 2;
		speedModifier = (int) (speed / 5);

		/* Bow */
		// arrowSpeed = 2.0F + speed / 8F;
		arrowDamage = 1.0F + damage / 8F;
	}

	protected void createArmor() {

		helmet = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.HEAD);
		plate = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.CHEST);
		legs = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.LEGS);
		boots = new ItemArmorAdv(ARMOR_MATERIAL, EntityEquipmentSlot.FEET);
	}

	protected void createTools() {

		sword = new ItemSwordAdv(TOOL_MATERIAL);
		shovel = new ItemShovelAdv(TOOL_MATERIAL);
		pickaxe = new ItemPickaxeAdv(TOOL_MATERIAL);
		axe = new ItemAxeAdv(TOOL_MATERIAL);
		hoe = new ItemHoeAdv(TOOL_MATERIAL);
		shears = new ItemShearsAdv(TOOL_MATERIAL);
		fishingRod = new ItemFishingRodAdv(TOOL_MATERIAL);
		sickle = new ItemSickleAdv(TOOL_MATERIAL);
		bow = new ItemBowAdv(TOOL_MATERIAL);
	}

	protected void preInitv() {

		final String NAME = name();
		final String TYPE = NAME.toLowerCase(Locale.US);
		final String ARMOR = "thermalfoundation.armor." + TYPE;
		final String TOOL = "thermalfoundation.tool." + TYPE;

		String category = "Equipment." + NAME;
		enableArmor = ThermalFoundation.CONFIG.get(category, "Armor", true);
		enableArmor &= !TFProps.disableAllArmor;

		category += ".Tools";
		enableTools[0] = ThermalFoundation.CONFIG.get(category, "Sword", true);
		enableTools[1] = ThermalFoundation.CONFIG.get(category, "Shovel", true);
		enableTools[2] = ThermalFoundation.CONFIG.get(category, "Pickaxe", true);
		enableTools[3] = ThermalFoundation.CONFIG.get(category, "Axe", true);
		enableTools[4] = ThermalFoundation.CONFIG.get(category, "Hoe", true);
		enableTools[5] = ThermalFoundation.CONFIG.get(category, "Shears", true);
		enableTools[6] = ThermalFoundation.CONFIG.get(category, "FishingRod", true);
		enableTools[7] = ThermalFoundation.CONFIG.get(category, "Sickle", true);
		enableTools[8] = ThermalFoundation.CONFIG.get(category, "Bow", true);

		for (int i = 0; i < enableTools.length; i++) {
			enableTools[i] &= !TFProps.disableAllTools;
		}

		final String PATH_ARMOR = "thermalfoundation:textures/" + "armor/";
		final String[] TEXTURE = { PATH_ARMOR + NAME.toLowerCase() + "_1.png", PATH_ARMOR + NAME.toLowerCase() + "_2.png" };

		createArmor();
		helmet.setRepairIngot(ingot).setArmorTextures(TEXTURE).setUnlocalizedName(ARMOR + "Helmet");
		helmet.setCreativeTab(ThermalFoundation.tabArmor);
		helmet.setShowInCreative(enableArmor | TFProps.showDisabledEquipment);
		plate.setRepairIngot(ingot).setArmorTextures(TEXTURE).setUnlocalizedName(ARMOR + "Plate");
		plate.setCreativeTab(ThermalFoundation.tabArmor);
		plate.setShowInCreative(enableArmor | TFProps.showDisabledEquipment);
		legs.setRepairIngot(ingot).setArmorTextures(TEXTURE).setUnlocalizedName(ARMOR + "Legs");
		legs.setCreativeTab(ThermalFoundation.tabArmor);
		legs.setShowInCreative(enableArmor | TFProps.showDisabledEquipment);
		boots.setRepairIngot(ingot).setArmorTextures(TEXTURE).setUnlocalizedName(ARMOR + "Boots");
		boots.setCreativeTab(ThermalFoundation.tabArmor);
		boots.setShowInCreative(enableArmor | TFProps.showDisabledEquipment);

		createTools();
		sword.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Sword");
		sword.setCreativeTab(ThermalFoundation.tabTools);
		sword.setShowInCreative(enableTools[0] | TFProps.showDisabledEquipment);

		shovel.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Shovel");
		shovel.setCreativeTab(ThermalFoundation.tabTools);
		shovel.setShowInCreative(enableTools[1] | TFProps.showDisabledEquipment);

		pickaxe.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Pickaxe");
		pickaxe.setCreativeTab(ThermalFoundation.tabTools);
		pickaxe.setShowInCreative(enableTools[2] | TFProps.showDisabledEquipment);

		axe.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Axe");
		axe.setCreativeTab(ThermalFoundation.tabTools);
		axe.setShowInCreative(enableTools[3] | TFProps.showDisabledEquipment);

		hoe.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Hoe");
		hoe.setCreativeTab(ThermalFoundation.tabTools);
		hoe.setShowInCreative(enableTools[4] | TFProps.showDisabledEquipment);

		shears.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Shears");
		shears.setCreativeTab(ThermalFoundation.tabTools);
		shears.setShowInCreative(enableTools[5] | TFProps.showDisabledEquipment);

		fishingRod.setRepairIngot(ingot).setUnlocalizedName(TOOL + "FishingRod");
		fishingRod.setCreativeTab(ThermalFoundation.tabTools);
		fishingRod.setLuckModifier(luckModifier).setSpeedModifier(speedModifier);
		fishingRod.setShowInCreative(enableTools[6] | TFProps.showDisabledEquipment);

		sickle.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Sickle");
		sickle.setCreativeTab(ThermalFoundation.tabTools);
		sickle.setShowInCreative(enableTools[7] | TFProps.showDisabledEquipment);

		bow.setRepairIngot(ingot).setArrowSpeed(arrowSpeed).setArrowDamage(arrowDamage).setUnlocalizedName(TOOL + "Bow");
		bow.setCreativeTab(ThermalFoundation.tabTools);
		bow.setShowInCreative(enableTools[8] | TFProps.showDisabledEquipment);

		GameRegistry.register(helmet.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "armor.helmet" + NAME)));
		GameRegistry.register(plate.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "armor.plate" + NAME)));
		GameRegistry.register(legs.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "armor.legs" + NAME)));
		GameRegistry.register(boots.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "armor.boots" + NAME)));

		GameRegistry.register(sword.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "tool.sword" + NAME)));
		GameRegistry.register(shovel.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "tool.shovel" + NAME)));
		GameRegistry.register(pickaxe.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "tool.pickaxe" + NAME)));
		GameRegistry.register(axe.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "tool.axe" + NAME)));
		GameRegistry.register(hoe.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "tool.hoe" + NAME)));
		GameRegistry.register(shears.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "tool.shears" + NAME)));
		GameRegistry.register(fishingRod.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "tool.fishingRod" + NAME)));
		GameRegistry.register(sickle.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "tool.sickle" + NAME)));
		GameRegistry.register(bow.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "tool.bow" + NAME)));

		ThermalFoundation.proxy.addModelRegister(this);
	}

	protected void initializev() {

	}

	protected void postInitv() {

		// Armor
		if (enableArmor) {
			GameRegistry.addRecipe(ShapedRecipe(helmet, new Object[] { "III", "I I", 'I', ingot }));
			GameRegistry.addRecipe(ShapedRecipe(plate, new Object[] { "I I", "III", "III", 'I', ingot }));
			GameRegistry.addRecipe(ShapedRecipe(legs, new Object[] { "III", "I I", "I I", 'I', ingot }));
			GameRegistry.addRecipe(ShapedRecipe(boots, new Object[] { "I I", "I I", 'I', ingot }));
		}

		// Tools
		if (enableTools[0]) {
			GameRegistry.addRecipe(ShapedRecipe(sword, new Object[] { "I", "I", "S", 'I', ingot, 'S', "stickWood" }));
		}
		if (enableTools[1]) {
			GameRegistry.addRecipe(ShapedRecipe(shovel, new Object[] { "I", "S", "S", 'I', ingot, 'S', "stickWood" }));
		}
		if (enableTools[2]) {
			GameRegistry.addRecipe(ShapedRecipe(pickaxe, new Object[] { "III", " S ", " S ", 'I', ingot, 'S', "stickWood" }));
		}
		if (enableTools[3]) {
			GameRegistry.addRecipe(ShapedRecipe(axe, new Object[] { "II", "IS", " S", 'I', ingot, 'S', "stickWood" }));
		}
		if (enableTools[4]) {
			GameRegistry.addRecipe(ShapedRecipe(hoe, new Object[] { "II", " S", " S", 'I', ingot, 'S', "stickWood" }));
		}
		if (enableTools[5]) {
			GameRegistry.addRecipe(ShapedRecipe(shears, new Object[] { " I", "I ", 'I', ingot }));
		}
		if (enableTools[6]) {
			GameRegistry.addRecipe(ShapedRecipe(fishingRod, new Object[] { "  I", " I#", "S #", 'I', ingot, 'S', "stickWood", '#', Items.STRING }));
		}
		if (enableTools[7]) {
			GameRegistry.addRecipe(ShapedRecipe(sickle, new Object[] { " I ", "  I", "SI ", 'I', ingot, 'S', "stickWood" }));
		}
		if (enableTools[8]) {
			GameRegistry.addRecipe(ShapedRecipe(bow, new Object[] { " I#", "S #", " I#", 'I', ingot, 'S', "stickWood", '#', Items.STRING }));
		}
	}

	public static void preInit() {

		VanillaEquipment.preInit();
		for (Equipment e : values()) {
			e.preInitv();
		}
	}

	public static void initialize() {

		VanillaEquipment.initialize();
		for (Equipment e : values()) {
			e.initializev();
		}
	}

	public static void postInit() {

		VanillaEquipment.postInit();
		for (Equipment e : values()) {
			e.postInitv();
		}
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(helmet, 0, new ModelResourceLocation(new ResourceLocation(ThermalFoundation.modId, "equipment"), this.toString().toLowerCase() + "_helmet"));
		ModelLoader.setCustomModelResourceLocation(plate, 0, new ModelResourceLocation(new ResourceLocation(ThermalFoundation.modId, "equipment"), this.toString().toLowerCase() + "_plate"));
		ModelLoader.setCustomModelResourceLocation(legs, 0, new ModelResourceLocation(new ResourceLocation(ThermalFoundation.modId, "equipment"), this.toString().toLowerCase() + "_legs"));
		ModelLoader.setCustomModelResourceLocation(boots, 0, new ModelResourceLocation(new ResourceLocation(ThermalFoundation.modId, "equipment"), this.toString().toLowerCase() + "_boots"));
		ModelLoader.setCustomModelResourceLocation(sword, 0, new ModelResourceLocation(new ResourceLocation(ThermalFoundation.modId, "equipment"), this.toString().toLowerCase() + "_sword"));
		ModelLoader.setCustomModelResourceLocation(shovel, 0, new ModelResourceLocation(new ResourceLocation(ThermalFoundation.modId, "equipment"), this.toString().toLowerCase() + "_shovel"));
		ModelLoader.setCustomModelResourceLocation(pickaxe, 0, new ModelResourceLocation(new ResourceLocation(ThermalFoundation.modId, "equipment"), this.toString().toLowerCase() + "_pickaxe"));
		ModelLoader.setCustomModelResourceLocation(axe, 0, new ModelResourceLocation(new ResourceLocation(ThermalFoundation.modId, "equipment"), this.toString().toLowerCase() + "_axe"));
		ModelLoader.setCustomModelResourceLocation(hoe, 0, new ModelResourceLocation(new ResourceLocation(ThermalFoundation.modId, "equipment"), this.toString().toLowerCase() + "_hoe"));
		ModelLoader.setCustomModelResourceLocation(shears, 0, new ModelResourceLocation(new ResourceLocation(ThermalFoundation.modId, "equipment"), this.toString().toLowerCase() + "_shears"));
		ModelLoader.setCustomModelResourceLocation(sickle, 0, new ModelResourceLocation(new ResourceLocation(ThermalFoundation.modId, "equipment"), this.toString().toLowerCase() + "_sickle"));

		ModelLoader.setCustomModelResourceLocation(fishingRod, 0, new ModelResourceLocation(new ResourceLocation(ThermalFoundation.modId, "equipment/" + this.toString().toLowerCase() + "/fishing_rod"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(bow, 0, new ModelResourceLocation(new ResourceLocation(ThermalFoundation.modId, "equipment/" + this.toString().toLowerCase() + "/bow"), "inventory"));
	}
}
