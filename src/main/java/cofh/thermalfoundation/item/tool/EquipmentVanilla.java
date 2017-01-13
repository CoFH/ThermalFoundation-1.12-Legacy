package cofh.thermalfoundation.item.tool;

import codechicken.lib.model.ModelRegistryHelper;
import codechicken.lib.model.SimpleOverrideBakedModel;
import cofh.api.core.IInitializer;
import cofh.api.core.IModelRegister;
import cofh.core.item.tool.ItemBowAdv;
import cofh.core.item.tool.ItemFishingRodAdv;
import cofh.core.item.tool.ItemShearsAdv;
import cofh.core.item.tool.ItemSickleAdv;
import cofh.lib.util.helpers.StringHelper;
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

public enum EquipmentVanilla implements IInitializer, IModelRegister {

	// @formatter:off
    WOOD("wood", ToolMaterial.WOOD, "plankWood") {

    	@Override
    	protected void createTools() {

    		itemBow = Items.BOW;
    		itemFishingRod = Items.FISHING_ROD;
			itemShears = new ItemShearsAdv(TOOL_MATERIAL);
    		itemSickle = new ItemSickleAdv(TOOL_MATERIAL);
    	}
    },
    STONE("stone", ToolMaterial.STONE, "cobblestone"),
    IRON("iron", ToolMaterial.IRON, "ingotIron") {

    	@Override
    	protected void createTools() {

			itemBow = new ItemBowAdv(TOOL_MATERIAL);
    		itemFishingRod = new ItemFishingRodAdv(TOOL_MATERIAL);
			itemShears = Items.SHEARS;
    		itemSickle = new ItemSickleAdv(TOOL_MATERIAL);

    	}
    },
    DIAMOND("diamond", ToolMaterial.DIAMOND, "gemDiamond"),
    GOLD("gold", ToolMaterial.GOLD, "ingotGold");
	// @formatter:on

	public final ToolMaterial TOOL_MATERIAL;

	private final String name;
	private final String ingot;

	/* BOW */
	private float arrowSpeed = 1.0F;
	private float arrowDamage = 1.0F;

	/* FISHING ROD */
	private int luckModifier = 0;
	private int speedModifier = 0;

	public boolean[] enableTools = new boolean[4];

	public ItemBow itemBow;
	public ItemFishingRod itemFishingRod;
	public ItemShears itemShears;
	public ItemSickleAdv itemSickle;

	public ItemStack toolBow;
	public ItemStack toolFishingRod;
	public ItemStack toolShears;
	public ItemStack toolSickle;

	EquipmentVanilla(String name, ToolMaterial material, String ingot) {

		this.name = name;
		this.ingot = ingot == null ? "ingot" + StringHelper.titleCase(name) : ingot;

		TOOL_MATERIAL = material == null ? ToolMaterial.valueOf(name.toUpperCase(Locale.US)) : material;

		/* BOW */
		// arrowSpeed = 2.0F + speed / 8F;
		arrowDamage = 1.0F + TOOL_MATERIAL.getDamageVsEntity() / 8F;

		/* FISHING ROD */
		luckModifier = TOOL_MATERIAL.getHarvestLevel() / 2;
		speedModifier = (int) (TOOL_MATERIAL.getEfficiencyOnProperMaterial() / 5);
	}

	protected void createTools() {

		itemBow = new ItemBowAdv(TOOL_MATERIAL);
		itemFishingRod = new ItemFishingRodAdv(TOOL_MATERIAL);
		itemShears = new ItemShearsAdv(TOOL_MATERIAL);
		itemSickle = new ItemSickleAdv(TOOL_MATERIAL);
	}

	protected void preInitTools() {

		final String NAME = StringHelper.titleCase(name);
		final String TOOL = "thermalfoundation.tool." + name.toLowerCase(Locale.US);

		String category = "Equipment." + NAME;

		category += ".Tools";
		if (this != WOOD) {
			enableTools[0] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Bow", true).getBoolean(true);
			enableTools[1] = ThermalFoundation.CONFIG.getConfiguration().get(category, "FishingRod", true).getBoolean(true);
		}
		if (this != IRON) {
			enableTools[2] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Shears", true).getBoolean(true);
		}
		enableTools[3] = ThermalFoundation.CONFIG.getConfiguration().get(category, "Sickle", true).getBoolean(true);

		for (int i = 0; i < enableTools.length; i++) {
			enableTools[i] &= !TFProps.disableAllTools;
		}
		createTools();

		/* BOW */
		if (itemBow instanceof ItemBowAdv) {
			ItemBowAdv itemBow = (ItemBowAdv) this.itemBow;
			itemBow.setRepairIngot(ingot).setArrowSpeed(arrowSpeed).setArrowDamage(arrowDamage).setUnlocalizedName(TOOL + "Bow").setCreativeTab(ThermalFoundation.tabTools);
			itemBow.setShowInCreative(enableTools[3] | TFProps.showDisabledEquipment);
			itemBow.setRegistryName("tool.bow" + NAME);
			GameRegistry.register(itemBow);
		}

		/* FISHING ROD */
		if (itemFishingRod instanceof ItemFishingRodAdv) {
			ItemFishingRodAdv itemFishingRod = (ItemFishingRodAdv) this.itemFishingRod;
			itemFishingRod.setRepairIngot(ingot).setUnlocalizedName(TOOL + "FishingRod").setCreativeTab(ThermalFoundation.tabTools);
			itemFishingRod.setLuckModifier(luckModifier).setSpeedModifier(speedModifier);
			itemFishingRod.setShowInCreative(enableTools[1] | TFProps.showDisabledEquipment);
			itemFishingRod.setRegistryName("tool.fishingRod" + NAME);
			GameRegistry.register(itemFishingRod);
		}

		/* SHEARS */
		if (itemShears instanceof ItemShearsAdv) {
			ItemShearsAdv itemShears = (ItemShearsAdv) this.itemShears;
			itemShears.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Shears").setCreativeTab(ThermalFoundation.tabTools);
			itemShears.setShowInCreative(enableTools[0] | TFProps.showDisabledEquipment);
			itemShears.setRegistryName("tool.shears" + NAME);
			GameRegistry.register(itemShears);
		}

		/* SICKLE */
		itemSickle.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Sickle").setCreativeTab(ThermalFoundation.tabTools);
		itemSickle.setShowInCreative(enableTools[2] | TFProps.showDisabledEquipment);
		itemSickle.setRegistryName("tool.sickle" + NAME);
		GameRegistry.register(itemSickle);

		toolBow = new ItemStack(itemBow);
		toolFishingRod = new ItemStack(itemFishingRod);
		toolShears = new ItemStack(itemShears);
		toolSickle = new ItemStack(itemSickle);
	}

	protected void postInitTools() {

		if (enableTools[0]) {
			GameRegistry.addRecipe(ShapedRecipe(toolBow, " I#", "S #", " I#", 'I', ingot, 'S', "stickWood", '#', Items.STRING));
		}
		if (enableTools[1]) {
			GameRegistry.addRecipe(ShapedRecipe(toolFishingRod, "  I", " I#", "S #", 'I', ingot, 'S', "stickWood", '#', Items.STRING));
		}
		if (enableTools[2]) {
			GameRegistry.addRecipe(ShapedRecipe(toolShears, " I", "I ", 'I', ingot));
		}
		if (enableTools[3]) {
			GameRegistry.addRecipe(ShapedRecipe(toolSickle, " I ", "  I", "SI ", 'I', ingot, 'S', "stickWood"));
		}
	}

	/* HELPERS */
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

	/* IModelRegister */
	@Override
	@SideOnly (Side.CLIENT)
	public void registerModels() {

		for (EquipmentVanilla e : values()) {
			e.registerMaterialModels();
		}
	}

	/* IInitializer */
	public boolean preInit() {

		for (EquipmentVanilla e : values()) {
			e.preInitTools();
		}
		return true;
	}

	public boolean initialize() {

		return true;
	}

	public boolean postInit() {

		for (EquipmentVanilla e : values()) {
			e.postInitTools();
		}
		return true;
	}

}
