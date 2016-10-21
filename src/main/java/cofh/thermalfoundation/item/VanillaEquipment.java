package cofh.thermalfoundation.item;

import cofh.core.item.tool.ItemBowAdv;
import cofh.core.item.tool.ItemFishingRodAdv;
import cofh.core.item.tool.ItemShearsAdv;
import cofh.core.item.tool.ItemSickleAdv;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.core.TFProps;
import net.minecraft.init.Items;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Locale;

import static cofh.lib.util.helpers.ItemHelper.ShapedRecipe;

public enum VanillaEquipment {

	// @formatter:off
    Wood(ToolMaterial.WOOD, "plankWood") {

    	@Override
    	protected void createTools() {

    		itemShears = new ItemShearsAdv(TOOL_MATERIAL);
    		itemFishingRod = Items.FISHING_ROD;
    		itemSickle = new ItemSickleAdv(TOOL_MATERIAL);
    		itemBow = Items.BOW;
    	}
    },
    Stone(ToolMaterial.STONE, "cobblestone"),
    Iron {

    	@Override
    	protected void createTools() {

    		itemShears =  Items.SHEARS;
    		itemFishingRod = new ItemFishingRodAdv(TOOL_MATERIAL);
    		itemSickle = new ItemSickleAdv(TOOL_MATERIAL);
    		itemBow = new ItemBowAdv(TOOL_MATERIAL);
    	}
    },
    Diamond(ToolMaterial.DIAMOND, "gemDiamond"),
    Gold
	;
	// @formatter:on

	public final ToolMaterial TOOL_MATERIAL;

	private final String ingot;
	private final float arrowSpeed = 1.0F;
	private float arrowDamage = 1.0F;
	private int luckModifier = 0;
	private int speedModifier = 0;

	public boolean[] enableTools = new boolean[4];

	public ItemShears itemShears;
	public ItemFishingRod itemFishingRod;
	public ItemSickleAdv itemSickle;
	public ItemBow itemBow;

	public ItemStack toolShears;
	public ItemStack toolFishingRod;
	public ItemStack toolSickle;
	public ItemStack toolBow;

	VanillaEquipment() {

		this(null, null);
	}

	VanillaEquipment(ToolMaterial material, String ingot) {

		TOOL_MATERIAL = material == null ? ToolMaterial.valueOf(name().toUpperCase(Locale.US)) : material;
		this.ingot = ingot == null ? "ingot" + name() : ingot;

		/* Fishing Rod */
		luckModifier = TOOL_MATERIAL.getHarvestLevel() / 2;
		speedModifier = (int) (TOOL_MATERIAL.getEfficiencyOnProperMaterial() / 5);

		/* Bow */
		// arrowSpeed = 2.0F + speed / 8F;
		arrowDamage = 1.0F + TOOL_MATERIAL.getDamageVsEntity() / 8F;
	}

	protected void createTools() {

		itemShears = new ItemShearsAdv(TOOL_MATERIAL);
		itemFishingRod = new ItemFishingRodAdv(TOOL_MATERIAL);
		itemSickle = new ItemSickleAdv(TOOL_MATERIAL);
		itemBow = new ItemBowAdv(TOOL_MATERIAL);
	}

	protected void preInitv() {

		final String NAME = name();
		final String TYPE = NAME.toLowerCase(Locale.US);
		final String TOOL = "thermalfoundation.tool." + TYPE;

		String category = "Equipment." + NAME;

		category += ".Tools";
		if (this != Iron) {
			enableTools[0] = ThermalFoundation.config.get(category, "Shears", true).getBoolean(true);
		}
		if (this != Wood) {
			enableTools[1] = ThermalFoundation.config.get(category, "FishingRod", true).getBoolean(true);
		}
		enableTools[2] = ThermalFoundation.config.get(category, "Sickle", true).getBoolean(true);
		if (this != Wood) {
			enableTools[3] = ThermalFoundation.config.get(category, "Bow", true).getBoolean(true);
		}

		for (int i = 0; i < enableTools.length; i++) {
			enableTools[i] &= !TFProps.disableAllTools;
		}

		final String TOOL_PATH = "thermalfoundation:tool/" + TYPE + "/" + NAME;

		createTools();

		if (itemShears instanceof ItemShearsAdv) {
			ItemShearsAdv itemShears = (ItemShearsAdv) this.itemShears;
			itemShears.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Shears");
//			itemShears.setTextureName(TOOL_PATH + "Shears").setCreativeTab(ThermalFoundation.tabTools);
			itemShears.setShowInCreative(enableTools[0] | TFProps.showDisabledEquipment);
			GameRegistry.registerItem(itemShears, "tool.shears" + NAME);
		}

		if (itemFishingRod instanceof ItemFishingRodAdv) {
			ItemFishingRodAdv itemFishingRod = (ItemFishingRodAdv) this.itemFishingRod;
			itemFishingRod.setRepairIngot(ingot).setUnlocalizedName(TOOL + "FishingRod");
//			itemFishingRod.setTextureName(TOOL_PATH + "FishingRod").setCreativeTab(ThermalFoundation.tabTools);
			itemFishingRod.setLuckModifier(luckModifier).setSpeedModifier(speedModifier);
			itemFishingRod.setShowInCreative(enableTools[1] | TFProps.showDisabledEquipment);
			GameRegistry.registerItem(itemFishingRod, "tool.fishingRod" + NAME);
		}

		itemSickle.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Sickle");
//		itemSickle.setTextureName(TOOL_PATH + "Sickle").setCreativeTab(ThermalFoundation.tabTools);
		itemSickle.setShowInCreative(enableTools[2] | TFProps.showDisabledEquipment);
		GameRegistry.registerItem(itemSickle, "tool.sickle" + NAME);

		if (itemBow instanceof ItemBowAdv) {
			ItemBowAdv itemBow = (ItemBowAdv) this.itemBow;
			itemBow.setRepairIngot(ingot).setArrowSpeed(arrowSpeed).setArrowDamage(arrowDamage).setUnlocalizedName(TOOL + "Bow");
//			itemBow.setTextureName(TOOL_PATH + "Bow").setCreativeTab(ThermalFoundation.tabTools);
			itemBow.setShowInCreative(enableTools[3] | TFProps.showDisabledEquipment);
			GameRegistry.registerItem(itemBow, "tool.bow" + NAME);
		}
	}

	protected void initializev() {

		final String NAME = name();

		// Tools
		toolShears = new ItemStack(itemShears);
		toolFishingRod = new ItemStack(itemFishingRod);
		toolSickle = new ItemStack(itemSickle);
		toolBow = new ItemStack(itemBow);

//		GameRegistry.registerCustomItemStack("tool" + NAME + "Shears", toolShears);
//		GameRegistry.registerCustomItemStack("tool" + NAME + "FishingRod", toolFishingRod);
//		GameRegistry.registerCustomItemStack("tool" + NAME + "Sickle", toolSickle);
//		GameRegistry.registerCustomItemStack("tool" + NAME + "Bow", toolBow);

//		itemShears.setRegistryName("tool" + NAME + "Shears");
//		itemFishingRod.setRegistryName("tool" + NAME + "FishingRod");
//		itemSickle.setRegistryName("tool" + NAME + "Sickle");
//		itemBow.setRegistryName("tool" + NAME + "Bow");

//		GameRegistry.register(itemShears);
//		GameRegistry.register(itemFishingRod);
//		GameRegistry.register(itemSickle);
//		GameRegistry.register(itemBow);
	}

	protected void postInitv() {

		// Tools
		if (enableTools[0]) {
			GameRegistry.addRecipe(ShapedRecipe(toolShears, " I", "I ", 'I', ingot));
		}
		if (enableTools[1]) {
			GameRegistry.addRecipe(ShapedRecipe(toolFishingRod, "  I", " I#", "S #", 'I', ingot, 'S', "stickWood", '#', Items.STRING));
		}
		if (enableTools[2]) {
			GameRegistry.addRecipe(ShapedRecipe(toolSickle, " I ", "  I", "SI ", 'I', ingot, 'S', "stickWood"));
		}
		if (enableTools[3]) {
			GameRegistry.addRecipe(ShapedRecipe(toolBow, " I#", "S #", " I#", 'I', ingot, 'S', "stickWood", '#', Items.STRING));
		}
	}

	public static void preInit() {

		for (VanillaEquipment e : values()) {
			e.preInitv();
		}
	}

	public static void initialize() {

		for (VanillaEquipment e : values()) {
			e.initializev();
		}
	}

	public static void postInit() {

		for (VanillaEquipment e : values()) {
			e.postInitv();
		}
	}

}
