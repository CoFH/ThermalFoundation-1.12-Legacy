package cofh.thermalfoundation.client.model;

import codechicken.lib.model.loader.CCBakedModelLoader;
import codechicken.lib.util.ItemNBTUtils;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by brandon3055 on 28/07/2016.
 */
public class FishingRodModelOverrideList extends ItemOverrideList {

	public FishingRodModelOverrideList() {

		super(ImmutableList.<ItemOverride>of());
	}

	@Override
	public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {

		ItemStack copy = stack.copy();

		if (entity instanceof EntityPlayer && ((EntityPlayer) entity).fishEntity != null) {
			ItemNBTUtils.setBoolean(copy, "IsCast", true);
		}

		IBakedModel model = CCBakedModelLoader.getModel(copy);
		if (model == null) {
			return originalModel;
		}
		return model;
	}
}
