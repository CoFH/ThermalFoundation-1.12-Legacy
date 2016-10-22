package cofh.thermalfoundation.client.model;

import codechicken.lib.model.bakery.SimplePerspectiveAwareLayerModelBakery;
import codechicken.lib.model.loader.IBakedModelLoader;
import codechicken.lib.util.ItemNBTUtils;
import codechicken.lib.util.TransformUtils;
import cofh.thermalfoundation.ThermalFoundation;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by brandon3055 on 28/07/2016.
 */
public class TFBakedModelProvider implements IBakedModelLoader {

    public static final Map<Item, String> BOWS = Collections.synchronizedMap(new HashMap<Item, String>());
    public static final Map<Item, String> RODS = Collections.synchronizedMap(new HashMap<Item, String>());
    public static final Map<String, ResourceLocation> resourceCache = new HashMap<String, ResourceLocation>();

    public static final TFBakedModelProvider INSTANCE = new TFBakedModelProvider();

    public static class TFKeyProvider implements IModKeyProvider {

        public static final TFKeyProvider INSTANCE = new TFKeyProvider();

        @Override
        public String getMod() {
            return ThermalFoundation.modId.toLowerCase();
        }

        @Override
        public String createKey(ItemStack stack) {
            if (stack != null && BOWS.containsKey(stack.getItem())) {
                String key = BOWS.get(stack.getItem());
                String texture = "thermalfoundation:items/tool/" + key.substring(0, key.indexOf("_")) + "/" + key;
                if (ItemNBTUtils.hasKey(stack, "DrawStage")) {
                    int stage = ItemNBTUtils.getInteger(stack, "DrawStage");
                    texture += "_" + stage;
                }
                return texture;
            }
            if (stack != null && RODS.containsKey(stack.getItem())) {
                String key = RODS.get(stack.getItem());
                String texture = "thermalfoundation:items/tool/" + key.substring(0, key.indexOf("_")) + "/" + key + (ItemNBTUtils.hasKey(stack, "IsCast") ? "_cast" : "_uncast");
                return texture;
            }

            return null;
        }

        @Override
        public String createKey(IBlockState state) {
            return null;
        }
    }

    @Override
    public IModKeyProvider createKeyProvider() {
        return TFKeyProvider.INSTANCE;
    }

    @Override
    public void addTextures(ImmutableList.Builder<ResourceLocation> builder) {
        for (String s : BOWS.values()) {

            builder.add(getResource("thermalfoundation:items/tool/" + s.substring(0, s.indexOf("_")) + "/" + s));
            builder.add(getResource("thermalfoundation:items/tool/" + s.substring(0, s.indexOf("_")) + "/" + s + "_0"));
            builder.add(getResource("thermalfoundation:items/tool/" + s.substring(0, s.indexOf("_")) + "/" + s + "_1"));
            builder.add(getResource("thermalfoundation:items/tool/" + s.substring(0, s.indexOf("_")) + "/" + s + "_2"));
        }

        for (String s : RODS.values()) {
            builder.add(getResource("thermalfoundation:items/tool/" + s.substring(0, s.indexOf("_")) + "/" + s + "_uncast"));
            builder.add(getResource("thermalfoundation:items/tool/" + s.substring(0, s.indexOf("_")) + "/" + s + "_cast"));
        }
    }

    @Override
    public IBakedModel bakeModel(String key) {
        SimplePerspectiveAwareLayerModelBakery bakery = new SimplePerspectiveAwareLayerModelBakery(getResource(key));
        return bakery.bake(TransformUtils.DEFAULT_ITEM);
    }

    private static ResourceLocation getResource(String string) {
        if (!resourceCache.containsKey(string)) {
            resourceCache.put(string, new ResourceLocation(string));
        }
        return resourceCache.get(string);
    }
}
