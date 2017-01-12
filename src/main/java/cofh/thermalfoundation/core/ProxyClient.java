package cofh.thermalfoundation.core;

import codechicken.lib.model.ModelRegistryHelper;
import codechicken.lib.model.SimpleBakedItemModel;
import codechicken.lib.model.loader.CCBakedModelLoader;
import codechicken.lib.render.CCIconRegister;
import cofh.core.render.IconRegistry;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalfoundation.block.TFBlocks;
import cofh.thermalfoundation.client.model.TFBakedModelProvider;
import cofh.thermalfoundation.entity.monster.EntityBasalz;
import cofh.thermalfoundation.entity.monster.EntityBlitz;
import cofh.thermalfoundation.entity.monster.EntityBlizz;
import cofh.thermalfoundation.entity.projectile.EntityBasalzBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlitzBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlizzBolt;
import cofh.thermalfoundation.fluid.TFFluids;
import cofh.thermalfoundation.item.Equipment;
import cofh.thermalfoundation.item.TFItems;
import cofh.thermalfoundation.render.entity.RenderEntityAsIcon;
import cofh.thermalfoundation.render.entity.RenderEntityBasalz;
import cofh.thermalfoundation.render.entity.RenderEntityBlitz;
import cofh.thermalfoundation.render.entity.RenderEntityBlizz;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ProxyClient extends Proxy {

    @Override
    public void preInit() {
        super.preInit();

        CCBakedModelLoader.registerLoader(TFBakedModelProvider.INSTANCE);
        TFItems.itemMaterial.registerModelVariants();
        TFBlocks.blockOre.registerModels();
        TFBlocks.blockStorage.registerModels();
        TFFluids.registerModels();
        Equipment.registerModels();
        registerRenderInformation();

        registerBucketModel(TFItems.bucketRedstone, TFFluids.fluidRedstone);
        registerBucketModel(TFItems.bucketGlowstone, TFFluids.fluidGlowstone);
        registerBucketModel(TFItems.bucketEnder, TFFluids.fluidEnder);
        registerBucketModel(TFItems.bucketPyrotheum, TFFluids.fluidPyrotheum);
        registerBucketModel(TFItems.bucketCryotheum, TFFluids.fluidCryotheum);
        registerBucketModel(TFItems.bucketAerotheum, TFFluids.fluidAerotheum);
        registerBucketModel(TFItems.bucketPetrotheum, TFFluids.fluidPetrotheum);
        registerBucketModel(TFItems.bucketMana, TFFluids.fluidMana);
        registerBucketModel(TFItems.bucketCoal, TFFluids.fluidCoal);
        //Piggy back the lexicon model off the main Material blockstate file.
        ModelLoader.setCustomModelResourceLocation(TFItems.itemLexicon, 0, new ModelResourceLocation("thermalfoundation:material", "type=lexicon"));

    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void post() {
        super.post();
    }

    @Override
    public void registerRenderInformation() {

        RenderingRegistry.registerEntityRenderingHandler(EntityBasalz.class, new IRenderFactory<EntityBasalz>() {
            @Override
            public Render<? super EntityBasalz> createRenderFor(RenderManager manager) {
                return new RenderEntityBasalz(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityBlitz.class, new IRenderFactory<EntityBlitz>() {
            @Override
            public Render<? super EntityBlitz> createRenderFor(RenderManager manager) {
                return new RenderEntityBlitz(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityBlizz.class, new IRenderFactory<EntityBlizz>() {
            @Override
            public Render<? super EntityBlizz> createRenderFor(RenderManager manager) {
                return new RenderEntityBlizz(manager);
            }
        });

        RenderingRegistry.registerEntityRenderingHandler(EntityBlizzBolt.class, new IRenderFactory<EntityBlizzBolt>() {
            @Override
            public Render<? super EntityBlizzBolt> createRenderFor(RenderManager manager) {
                return new RenderEntityAsIcon(manager).setIcon("thermalfoundation:items/material/dust_blizz");
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityBlitzBolt.class, new IRenderFactory<EntityBlitzBolt>() {
            @Override
            public Render<? super EntityBlitzBolt> createRenderFor(RenderManager manager) {
                return new RenderEntityAsIcon(manager).setIcon("thermalfoundation:items/material/dust_blitz");
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityBasalzBolt.class, new IRenderFactory<EntityBasalzBolt>() {
            @Override
            public Render<? super EntityBasalzBolt> createRenderFor(RenderManager manager) {
                return new RenderEntityAsIcon(manager).setIcon("thermalfoundation:items/material/dust_basalz");
            }
        });

        //MinecraftForgeClient.registerItemRenderer(TFItems.itemBucket, new RenderFluidOverlayItem());
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerIcons(TextureStitchEvent.Pre event) {

        //        if (event.map.getTextureType() == 0) {
        addIconToRegistry(TFFluids.fluidRedstone, event.getMap());
        addIconToRegistry(TFFluids.fluidGlowstone, event.getMap());
        addIconToRegistry(TFFluids.fluidEnder, event.getMap());
        addIconToRegistry(TFFluids.fluidPyrotheum, event.getMap());
        addIconToRegistry(TFFluids.fluidCryotheum, event.getMap());
        addIconToRegistry(TFFluids.fluidAerotheum, event.getMap());
        addIconToRegistry(TFFluids.fluidPetrotheum, event.getMap());
        addIconToRegistry(TFFluids.fluidMana, event.getMap());
        addIconToRegistry(TFFluids.fluidCoal, event.getMap());
        addIconToRegistry(TFFluids.fluidSteam, event.getMap());
        //
        //        } else if (event.map.getTextureType() == 1) {
        //            CCIconRegister.registerTexture("thermalfoundation:material/DustBlaze");
        //
        //            if (TFProps.iconBlazePowder) {
        //                //TODO Replace model location.
        //                //Items.blaze_powder.setTextureName("thermalfoundation:material/DustBlaze");
        //                //Items.blaze_powder.registerIcons(event.map);
        //            }
        //        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void initializeIcons(TextureStitchEvent.Post event) {
        RenderEntityBlizz.initialize();
        RenderEntityBlitz.initialize();
        RenderEntityBasalz.initialize();
    }

    public static void addIconToRegistry(Fluid fluid, TextureMap map) {
        String name = fluid.getName();
        IconRegistry.addIcon("Fluid" + StringHelper.titleCase(name), fluid.getStill(), map);
        IconRegistry.addIcon("Fluid" + StringHelper.titleCase(name) + "1", fluid.getFlowing(), map);
        //CCIconRegister.registerTexture("thermalfoundation:fluid/fluid_" + name + "_still");
        //CCIconRegister.registerTexture("thermalfoundation:fluid/fluid_" + name + "_flow");
    }

    public static void registerBucketModel(ItemStack bucket, Fluid fluid) {
        ResourceLocation bucketTexture = new ResourceLocation("thermalfoundation", "items/bucket/bucket_" + fluid.getName());
        ModelResourceLocation modelLocation = new ModelResourceLocation(bucket.getItem().getRegistryName(), "bucket=" + fluid.getName());
        ModelLoader.setCustomModelResourceLocation(bucket.getItem(), bucket.getMetadata(), modelLocation);
        CCIconRegister.registerTexture(bucketTexture);
        ModelRegistryHelper.register(modelLocation, new SimpleBakedItemModel(bucketTexture));
    }

    //public static void setFluidIcons(Fluid fluid) {
    //    String name = StringHelper.titleCase(fluid.getName());
    //    fluid.setIcons(IconRegistry.getIcon("Fluid" + name), IconRegistry.getIcon("Fluid" + name, 1));
    //}

}
