package cofh.thermalfoundation.item;

import static cofh.lib.util.helpers.ItemHelper.ShapedRecipe;

import cofh.api.core.IModelRegister;
import cofh.core.item.tool.ItemBowAdv;
import cofh.core.item.tool.ItemFishingRodAdv;
import cofh.core.item.tool.ItemShearsAdv;
import cofh.core.item.tool.ItemSickleAdv;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.core.TFProps;

import java.util.Locale;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemShears;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public enum VanillaEquipment implements IModelRegister {

	// @formatter:off
	Wood(ToolMaterial.WOOD, "plankWood") {

		@Override
		protected void createTools() {

			shears = new ItemShearsAdv(TOOL_MATERIAL);
			fishingRod = Items.FISHING_ROD;
			sickle = new ItemSickleAdv(TOOL_MATERIAL);
			bow = Items.BOW;
		}
	},
	Stone(ToolMaterial.STONE, "cobblestone"),
	Iron {

		@Override
		protected void createTools() {

			shears =  Items.SHEARS;
			fishingRod = new ItemFishingRodAdv(TOOL_MATERIAL);
			sickle = new ItemSickleAdv(TOOL_MATERIAL);
			bow = new ItemBowAdv(TOOL_MATERIAL);
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

	public ItemShears shears;
	public ItemFishingRod fishingRod;
	public ItemSickleAdv sickle;
	public ItemBow bow;

	private VanillaEquipment() {

		this(null, null);
	}

	private VanillaEquipment(ToolMaterial material, String ingot) {

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

		shears = new ItemShearsAdv(TOOL_MATERIAL);
		fishingRod = new ItemFishingRodAdv(TOOL_MATERIAL);
		sickle = new ItemSickleAdv(TOOL_MATERIAL);
		bow = new ItemBowAdv(TOOL_MATERIAL);
	}

	protected void preInitv() {

		final String NAME = name();
		final String TYPE = NAME.toLowerCase(Locale.US);
		final String TOOL = "thermalfoundation.equipment." + TYPE;

		String category = "Equipment." + NAME;

		category += ".Tools";
		if (this != Iron) {
			enableTools[0] = ThermalFoundation.CONFIG.get(category, "Shears", true);
		}
		if (this != Wood) {
			enableTools[1] = ThermalFoundation.CONFIG.get(category, "FishingRod", true);
		}
		enableTools[2] = ThermalFoundation.CONFIG.get(category, "Sickle", true);
		if (this != Wood) {
			enableTools[3] = ThermalFoundation.CONFIG.get(category, "Bow", true);
		}

		for (int i = 0; i < enableTools.length; i++) {
			enableTools[i] &= !TFProps.disableAllTools;
		}

		createTools();

		if (shears instanceof ItemShearsAdv) {
			ItemShearsAdv itemShears = (ItemShearsAdv) this.shears;
			itemShears.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Shears");
			itemShears.setShowInCreative(enableTools[0] | TFProps.showDisabledEquipment);
			GameRegistry.register(itemShears.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "equipment.shears" + NAME)));
		}

		if (fishingRod instanceof ItemFishingRodAdv) {
			ItemFishingRodAdv itemFishingRod = (ItemFishingRodAdv) this.fishingRod;
			itemFishingRod.setRepairIngot(ingot).setUnlocalizedName(TOOL + "FishingRod");
			itemFishingRod.setLuckModifier(luckModifier).setSpeedModifier(speedModifier);
			itemFishingRod.setShowInCreative(enableTools[1] | TFProps.showDisabledEquipment);
			GameRegistry.register(itemFishingRod.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "equipment.fishingRod" + NAME)));
		}

		sickle.setRepairIngot(ingot).setUnlocalizedName(TOOL + "Sickle");
		sickle.setShowInCreative(enableTools[2] | TFProps.showDisabledEquipment);
		GameRegistry.register(sickle.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "equipment.sickle" + NAME)));

		if (bow instanceof ItemBowAdv) {
			ItemBowAdv itemBow = (ItemBowAdv) this.bow;
			itemBow.setRepairIngot(ingot).setArrowSpeed(arrowSpeed).setArrowDamage(arrowDamage).setUnlocalizedName(TOOL + "Bow");
			itemBow.setShowInCreative(enableTools[3] | TFProps.showDisabledEquipment);
			GameRegistry.register(itemBow.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "equipment.bow" + NAME)));
		}

		ThermalFoundation.proxy.addModelRegister(this);
	}

	protected void initializev() {

	}

	protected void postInitv() {

		// Tools
		if (enableTools[0]) {
			GameRegistry.addRecipe(ShapedRecipe(shears, new Object[] { " I", "I ", 'I', ingot }));
		}
		if (enableTools[1]) {
			GameRegistry.addRecipe(ShapedRecipe(fishingRod, new Object[] { "  I", " I#", "S #", 'I', ingot, 'S', "stickWood", '#', Items.STRING }));
		}
		if (enableTools[2]) {
			GameRegistry.addRecipe(ShapedRecipe(sickle, new Object[] { " I ", "  I", "SI ", 'I', ingot, 'S', "stickWood" }));
		}
		if (enableTools[3]) {
			GameRegistry.addRecipe(ShapedRecipe(bow, new Object[] { " I#", "S #", " I#", 'I', ingot, 'S', "stickWood", '#', Items.STRING }));
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

	@Override
	public void registerModels() {

		if (shears instanceof ItemShearsAdv)
			ModelLoader.setCustomModelResourceLocation(shears, 0, new ModelResourceLocation(new ResourceLocation(ThermalFoundation.modId, "equipment"), this.toString().toLowerCase() + "_shears"));

		if (fishingRod instanceof ItemFishingRodAdv)
			ModelLoader.setCustomModelResourceLocation(fishingRod, 0, new ModelResourceLocation(new ResourceLocation(ThermalFoundation.modId, "equipment/" + this.toString().toLowerCase() + "/fishing_rod"), "inventory"));

		ModelLoader.setCustomModelResourceLocation(sickle, 0, new ModelResourceLocation(new ResourceLocation(ThermalFoundation.modId, "equipment"), this.toString().toLowerCase() + "_sickle"));

		if (bow instanceof ItemBowAdv)
			ModelLoader.setCustomModelResourceLocation(bow, 0, new ModelResourceLocation(new ResourceLocation(ThermalFoundation.modId, "equipment/" + this.toString().toLowerCase() + "/bow"), "inventory"));
	}
}
