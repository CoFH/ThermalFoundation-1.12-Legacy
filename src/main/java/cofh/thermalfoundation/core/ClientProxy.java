package cofh.thermalfoundation.core;

import codechicken.lib.render.CCIconRegister;
import cofh.thermalfoundation.block.TFBlocks;
import cofh.thermalfoundation.item.TFItems;
import cofh.thermalfoundation.render.entity.RenderEntityBasalz;
import cofh.thermalfoundation.render.entity.RenderEntityBlitz;
import cofh.thermalfoundation.render.entity.RenderEntityBlizz;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Locale;

;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        super.preInit();


        TFItems.itemMaterial.registerModelVariants();
        TFBlocks.blockOre.registerModels();
        TFBlocks.blockStorage.registerModels();
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void post() {
        super.post();
    }

    //TODO Rendering stuff
//    static RenderEntityAsIcon renderBlazeBolt = new RenderEntityAsIcon();
//    static RenderEntityAsIcon renderBlizzBolt = new RenderEntityAsIcon();
//    static RenderEntityAsIcon renderBlitzBolt = new RenderEntityAsIcon();
//    static RenderEntityAsIcon renderBasalzBolt = new RenderEntityAsIcon();

    @Override
    public void registerRenderInformation() {

//        RenderingRegistry.registerEntityRenderingHandler(EntityBlazeBolt.class, renderBlazeBolt);
//        RenderingRegistry.registerEntityRenderingHandler(EntityBlizzBolt.class, renderBlizzBolt);
//        RenderingRegistry.registerEntityRenderingHandler(EntityBlitzBolt.class, renderBlitzBolt);
//        RenderingRegistry.registerEntityRenderingHandler(EntityBasalzBolt.class, renderBasalzBolt);

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

//        renderBlazeBolt.setIcon(TextureUtils.getTexture("thermalfoundation:material/DustBlaze"));
//        renderBlizzBolt.setIcon(TFItems.itemMaterial.getIconFromDamage(TFItems.dustBlizz.getItemDamage()));
//        renderBlitzBolt.setIcon(TFItems.itemMaterial.getIconFromDamage(TFItems.dustBlitz.getItemDamage()));
//        renderBasalzBolt.setIcon(TFItems.itemMaterial.getIconFromDamage(TFItems.dustBasalz.getItemDamage()));
    }

    public static void registerFluidIcons(Fluid fluid) {
        String name = fluid.getName().substring(0, 1).toUpperCase(Locale.US) + fluid.getName().substring(1);
        CCIconRegister.registerTexture("thermalfoundation:fluid/Fluid_" + name + "_Still");
        CCIconRegister.registerTexture("thermalfoundation:fluid/Fluid_" + name + "_Flow");
    }

    //public static void setFluidIcons(Fluid fluid) {
    //    String name = StringHelper.titleCase(fluid.getName());
    //    fluid.setIcons(IconRegistry.getIcon("Fluid" + name), IconRegistry.getIcon("Fluid" + name, 1));
    //}

}
