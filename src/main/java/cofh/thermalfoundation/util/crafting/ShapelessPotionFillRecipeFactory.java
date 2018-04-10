package cofh.thermalfoundation.util.crafting;

import cofh.core.init.CoreProps;
import cofh.thermalfoundation.util.helpers.FluidPotionHelper;
import com.google.gson.JsonObject;
import gnu.trove.set.hash.THashSet;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;

public class ShapelessPotionFillRecipeFactory implements IRecipeFactory {

	private static final THashSet<Item> POTION_ITEMS = new THashSet<>();

	static {
		POTION_ITEMS.add(Items.POTIONITEM);
		POTION_ITEMS.add(Items.SPLASH_POTION);
		POTION_ITEMS.add(Items.LINGERING_POTION);
	}

	@Override
	public IRecipe parse(JsonContext context, JsonObject json) {

		ShapelessOreRecipe recipe = ShapelessOreRecipe.factory(context, json);

		return new ShapelessPotionFillRecipe(new ResourceLocation("thermalfoundation", "potion_fill_shapeless"), recipe.getRecipeOutput(), recipe.getIngredients().toArray());
	}

	/* RECIPE */
	public static class ShapelessPotionFillRecipe extends ShapelessOreRecipe {

		public ShapelessPotionFillRecipe(ResourceLocation group, ItemStack result, Object... recipe) {

			super(group, result, recipe);
		}

		public boolean isPotion(ItemStack stack) {

			//return POTION_ITEMS.contains(stack.getItem());
			Item item = stack.getItem();
			return item.equals(Items.POTIONITEM) || item.equals(Items.SPLASH_POTION) || item.equals(Items.LINGERING_POTION);
		}

		@Override
		public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {

			NonNullList<ItemStack> ret = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

			for (int i = 0; i < ret.size(); i++) {
				if (isPotion(inv.getStackInSlot(i))) {
					ret.set(i, new ItemStack(Items.GLASS_BOTTLE));
				}
			}
			return ret;
		}

		@Override
		@Nonnull
		public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {

			ItemStack potionStack = ItemStack.EMPTY;
			IFluidHandlerItem handler = null;

			for (int i = 0; i < inv.getSizeInventory(); ++i) {
				ItemStack stack = inv.getStackInSlot(i);
				if (isPotion(stack)) {
					potionStack = stack.copy();
				} else if (handler == null) {
					handler = FluidUtil.getFluidHandler(stack.copy());
				}
			}
			if (potionStack == ItemStack.EMPTY || handler == null) {
				return ItemStack.EMPTY;
			}
			FluidStack potionFluid = FluidPotionHelper.getFluidPotion(CoreProps.BOTTLE_VOLUME, potionStack);

			if (handler.fill(potionFluid, false) < CoreProps.BOTTLE_VOLUME) {
				return ItemStack.EMPTY;
			}
			handler.fill(potionFluid, true);
			return handler.getContainer().copy();
		}

		@Override
		public boolean isDynamic() {

			return true;
		}
	}

}
