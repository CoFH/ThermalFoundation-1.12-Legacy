package cofh.thermalfoundation.core;

import codechicken.lib.model.loader.CCBakedModelLoader;
import codechicken.lib.render.CCIconRegister;
import cofh.thermalfoundation.block.TFBlocks;
import cofh.thermalfoundation.client.model.TFBakedModelProvider;
import cofh.thermalfoundation.entity.monster.EntityBasalz;
import cofh.thermalfoundation.entity.monster.EntityBlitz;
import cofh.thermalfoundation.entity.monster.EntityBlizz;
import cofh.thermalfoundation.entity.projectile.EntityBasalzBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlazeBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlitzBolt;
import cofh.thermalfoundation.entity.projectile.EntityBlizzBolt;
import cofh.thermalfoundation.fluid.TFFluids;
import cofh.thermalfoundation.item.Equipment;
import cofh.thermalfoundation.item.TFItems;
import cofh.thermalfoundation.render.entity.RenderEntityAsIcon;
import cofh.thermalfoundation.render.entity.RenderEntityBasalz;
import cofh.thermalfoundation.render.entity.RenderEntityBlitz;
import cofh.thermalfoundation.render.entity.RenderEntityBlizz;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Locale;

;

public class ClientProxy extends CommonProxy {

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


        RenderingRegistry.registerEntityRenderingHandler(EntityBlazeBolt.class, new IRenderFactory<EntityBlazeBolt>() {
            @Override
            public Render<? super EntityBlazeBolt> createRenderFor(RenderManager manager) {
                return new RenderEntityAsIcon(manager).setIcon("thermalfoundation:items/material/dust_blaze");
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
//            registerFluidIcons(TFFluids.fluidRedstone, event.map);
//            registerFluidIcons(TFFluids.fluidGlowstone, event.map);
//            registerFluidIcons(TFFluids.fluidEnder, event.map);
//            registerFluidIcons(TFFluids.fluidPyrotheum, event.map);
//            registerFluidIcons(TFFluids.fluidCryotheum, event.map);
//            registerFluidIcons(TFFluids.fluidAerotheum, event.map);
//            registerFluidIcons(TFFluids.fluidPetrotheum, event.map);
//            registerFluidIcons(TFFluids.fluidMana, event.map);
//            registerFluidIcons(TFFluids.fluidCoal, event.map);
//            registerFluidIcons(TFFluids.fluidSteam, event.map);
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

    public static void registerFluidIcons(Fluid fluid) {
        String name = fluid.getName().substring(0, 1).toUpperCase(Locale.US) + fluid.getName().substring(1);
        CCIconRegister.registerTexture("thermalfoundation:fluid/fluid_" + name + "_still");
        CCIconRegister.registerTexture("thermalfoundation:fluid/fluid_" + name + "_flow");
    }

    //public static void setFluidIcons(Fluid fluid) {
    //    String name = StringHelper.titleCase(fluid.getName());
    //    fluid.setIcons(IconRegistry.getIcon("Fluid" + name), IconRegistry.getIcon("Fluid" + name, 1));
    //}

}
