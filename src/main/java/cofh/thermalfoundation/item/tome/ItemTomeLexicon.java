package cofh.thermalfoundation.item.tome;

import cofh.api.item.IInventoryContainerItem;
import cofh.core.key.KeyBindingItemMultiMode;
import cofh.core.util.CoreUtils;
import cofh.core.util.helpers.ServerHelper;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.gui.GuiHandler;
import cofh.thermalfoundation.init.TFProps;
import cofh.thermalfoundation.util.LexiconManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

import static cofh.core.util.helpers.RecipeHelper.addShapedRecipe;

public class ItemTomeLexicon extends ItemTome implements IInventoryContainerItem {

	public ItemTomeLexicon() {

		super();

		setUnlocalizedName("lexicon");
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		if (StringHelper.displayShiftForDetail && !StringHelper.isShiftKeyDown()) {
			tooltip.add(StringHelper.shiftForDetails());
		}
		if (!StringHelper.isShiftKeyDown()) {
			return;
		}
		tooltip.add(StringHelper.getInfoText("info.thermalfoundation.tome.lexicon.0"));
		tooltip.add(StringHelper.localize("info.thermalfoundation.tome.lexicon.a." + (isEmpowered(stack) ? 1 : 0)) + StringHelper.END);
		tooltip.add(StringHelper.localizeFormat("info.thermalfoundation.tome.lexicon.b." + (isEmpowered(stack) ? 1 : 0), StringHelper.getKeyName(KeyBindingItemMultiMode.INSTANCE.getKey())));
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {

		if (!isEmpowered(stack)) {
			return;
		}
		NBTTagCompound tag = entity.getEntityData();
		tag.setLong(TFProps.LEXICON_TIMER, entity.world.getTotalWorldTime());
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

		ItemStack stack = player.getHeldItem(hand);
		if (CoreUtils.isFakePlayer(player) || hand != EnumHand.MAIN_HAND) {
			return new ActionResult<>(EnumActionResult.FAIL, stack);
		}
		if (ServerHelper.isServerWorld(world) && LexiconManager.getSortedOreNames().size() > 0) {
			if (isEmpowered(stack)) {
				player.openGui(ThermalFoundation.instance, GuiHandler.LEXICON_TRANSMUTE_ID, world, 0, 0, 0);
			} else {
				player.openGui(ThermalFoundation.instance, GuiHandler.LEXICON_STUDY_ID, world, 0, 0, 0);
			}
		}
		return new ActionResult<>(EnumActionResult.FAIL, stack);
	}

	/* IInventoryContainerItem */
	@Override
	public int getSizeInventory(ItemStack container) {

		return 3;
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		ForgeRegistries.ITEMS.register(setRegistryName("tome_lexicon"));
		ThermalFoundation.proxy.addIModelRegister(this);

		config();

		tomeLexicon = new ItemStack(this);

		return true;
	}

	@Override
	public boolean initialize() {

		if (enable) {
			addShapedRecipe(tomeLexicon, " L ", "GBI", " R ", 'B', Items.BOOK, 'G', "ingotGold", 'I', "ingotIron", 'L', "gemLapis", 'R', "dustRedstone");
		}
		return true;
	}

	public static void config() {

		String category = "Tome.Lexicon";
		String comment = "If TRUE, the recipe for the Forge Lexicon is enabled. Set this to FALSE only if you know what you are doing and/or want to create needless inconvenience.";
		enable = ThermalFoundation.CONFIG.getConfiguration().getBoolean("EnableRecipe", category, enable, comment);
	}

	public static boolean enable = true;

	/* REFERENCES */
	public static ItemStack tomeLexicon;

}
