package cofh.thermalfoundation.item.tool;

import codechicken.lib.model.ModelRegistryHelper;
import codechicken.lib.model.SimpleOverrideBakedModel;
import cofh.core.item.tool.ItemBowAdv;
import cofh.core.item.tool.ItemFishingRodAdv;
import cofh.core.item.tool.ItemShearsAdv;
import cofh.core.item.tool.ItemSickleAdv;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.client.model.BowModelOverrideList;
import cofh.thermalfoundation.client.model.FishingRodModelOverrideList;
import cofh.thermalfoundation.client.model.TFBakedModelProvider;
import cofh.thermalfoundation.core.TFProps;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

    		itemShears = Items.SHEARS;
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

	protected void preInitMaterial() {

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
			itemShears.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Shears").setCreativeTab(ThermalFoundation.tabTools);
			itemShears.setShowInCreative(enableTools[0] | TFProps.showDisabledEquipment);
			itemShears.setRegistryName("tool.shears" + NAME);
			GameRegistry.register(itemShears);
		}

		if (itemFishingRod instanceof ItemFishingRodAdv) {
			ItemFishingRodAdv itemFishingRod = (ItemFishingRodAdv) this.itemFishingRod;
			itemFishingRod.setRepairIngot(ingot).setUnlocalizedName(TOOL + "FishingRod").setCreativeTab(ThermalFoundation.tabTools);
			itemFishingRod.setLuckModifier(luckModifier).setSpeedModifier(speedModifier);
			itemFishingRod.setShowInCreative(enableTools[1] | TFProps.showDisabledEquipment);
			itemFishingRod.setRegistryName("tool.fishingRod" + NAME);
			GameRegistry.register(itemFishingRod);
		}

		itemSickle.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Sickle").setCreativeTab(ThermalFoundation.tabTools);
		itemSickle.setShowInCreative(enableTools[2] | TFProps.showDisabledEquipment);
		itemSickle.setRegistryName("tool.sickle" + NAME);
		GameRegistry.register(itemSickle);

		if (itemBow instanceof ItemBowAdv) {
			ItemBowAdv itemBow = (ItemBowAdv) this.itemBow;
			itemBow.setRepairIngot(ingot).setArrowSpeed(arrowSpeed).setArrowDamage(arrowDamage).setUnlocalizedName(TOOL + "Bow").setCreativeTab(ThermalFoundation.tabTools);
			itemBow.setShowInCreative(enableTools[3] | TFProps.showDisabledEquipment);
			itemBow.setRegistryName("tool.bow" + NAME);
			GameRegistry.register(itemBow);
		}
	}

	protected void initializeMaterial() {

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

	protected void postInitMaterial() {

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
			e.preInitMaterial();
		}
	}

	public static void initialize() {

		for (VanillaEquipment e : values()) {
			e.initializeMaterial();
		}
	}

	public static void postInit() {

		for (VanillaEquipment e : values()) {
			e.postInitMaterial();
		}
	}

	@SideOnly (Side.CLIENT)
	public static void registerModels() {

		for (VanillaEquipment e : values()) {
			e.registerMaterialModels();
		}
	}

	@SideOnly (Side.CLIENT)
	protected void registerMaterialModels() {

		if (itemShears instanceof ItemShearsAdv) {
			registerModel(itemShears, 0, "shears");
		}

		if (itemFishingRod instanceof ItemFishingRodAdv) {
			ModelLoader.setCustomModelResourceLocation(itemFishingRod, 0, new ModelResourceLocation(itemFishingRod.getRegistryName(), "inventory"));
			TFBakedModelProvider.RODS.put(itemFishingRod, name().toLowerCase() + "_fishing_rod");
			ModelRegistryHelper.register(new ModelResourceLocation(itemFishingRod.getRegistryName(), "inventory"), new SimpleOverrideBakedModel(new FishingRodModelOverrideList()));
		}

		if (itemBow instanceof ItemBowAdv) {
			ModelLoader.setCustomModelResourceLocation(itemBow, 0, new ModelResourceLocation(itemBow.getRegistryName(), "inventory"));
			TFBakedModelProvider.BOWS.put(itemBow, name().toLowerCase() + "_bow");
			ModelRegistryHelper.register(new ModelResourceLocation(itemBow.getRegistryName(), "inventory"), new SimpleOverrideBakedModel(new BowModelOverrideList()));
		}

		registerModel(itemSickle, 0, "sickle");
	}

	@SideOnly (Side.CLIENT)
	private void registerModel(Item item, int meta, String tool) {

		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation("thermalfoundation:tool", "type=" + name().toLowerCase() + tool));
	}

}
