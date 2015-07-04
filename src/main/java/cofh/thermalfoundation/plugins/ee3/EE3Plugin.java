package cofh.thermalfoundation.plugins.ee3;

import cofh.api.modhelpers.EE3Helper;
import cofh.asm.relauncher.Strippable;
import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.fluid.TFFluids;
import cofh.thermalfoundation.item.TFItems;

import net.minecraftforge.fluids.FluidStack;

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
		EE3Helper.addPreAssignedEnergyValue(TFItems.ingotPlatinum, 16384);
		EE3Helper.addPreAssignedEnergyValue(TFItems.ingotMithril, 16384);

		EE3Helper.addPreAssignedEnergyValue(TFItems.rodBlizz, 1536);

		EE3Helper.addRecipe(ItemHelper.cloneStack(TFItems.dustSignalum, 4), TFItems.dustCopper, TFItems.dustCopper, TFItems.dustCopper, TFItems.dustSilver,
				new FluidStack(TFFluids.fluidRedstone, 1000));

		EE3Helper.addRecipe(ItemHelper.cloneStack(TFItems.dustLumium, 4), TFItems.dustTin, TFItems.dustTin, TFItems.dustTin, TFItems.dustSilver,
				new FluidStack(TFFluids.fluidGlowstone, 1000));

		EE3Helper.addRecipe(ItemHelper.cloneStack(TFItems.dustEnderium, 4), TFItems.dustTin, TFItems.dustTin, TFItems.dustSilver, TFItems.dustPlatinum,
				new FluidStack(TFFluids.fluidEnder, 1000));

	}

	public static void postInit() {

	}

	@Strippable("mod:EE3")
	public static void loadComplete() {

		ThermalFoundation.log.info("Thermal Foundation: Equivalent Exchange 3 Plugin Enabled.");
	}

}
