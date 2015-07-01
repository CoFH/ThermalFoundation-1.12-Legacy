package cofh.thermalfoundation.plugins.ee3;

import cofh.asm.relauncher.Strippable;
import cofh.thermalfoundation.block.BlockOre;
import cofh.thermalfoundation.item.TFItems;

public class EE3Plugin {

    public static void preInit() {

    }

    @Strippable("mod:EE3")
    public static void initialize() throws Throwable {
        EE3Helper.addPreAssignedEnergyValue(TFItems.ingotCopper, 128);
        EE3Helper.addPreAssignedEnergyValue(TFItems.ingotTin, 192);
        EE3Helper.addPreAssignedEnergyValue(TFItems.ingotNickel, 1024);
        EE3Helper.addPreAssignedEnergyValue(TFItems.ingotLead, 512);
        EE3Helper.addPreAssignedEnergyValue(TFItems.ingotSilver, 512);
        EE3Helper.addPreAssignedEnergyValue(TFItems.ingotPlatinum, 8192);
        EE3Helper.addPreAssignedEnergyValue(TFItems.ingotMithril, 16384);

        EE3Helper.addPreAssignedEnergyValue(BlockOre.oreCopper, 128);
        EE3Helper.addPreAssignedEnergyValue(BlockOre.oreTin, 192);
        EE3Helper.addPreAssignedEnergyValue(BlockOre.oreNickel, 1024);
        EE3Helper.addPreAssignedEnergyValue(BlockOre.oreLead, 512);
        EE3Helper.addPreAssignedEnergyValue(BlockOre.oreSilver, 512);
        EE3Helper.addPreAssignedEnergyValue(BlockOre.orePlatinum, 8192);
        EE3Helper.addPreAssignedEnergyValue(BlockOre.oreMithril, 16384);

        EE3Helper.addPreAssignedEnergyValue(TFItems.rodBlizz, 1536);
    }

    public static void postInit() {

    }

    public static void loadComplete() throws Throwable {

    }
}
