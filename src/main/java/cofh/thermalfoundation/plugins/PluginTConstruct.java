package cofh.thermalfoundation.plugins;

import cofh.core.util.ModPlugin;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.item.EnumRarity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import java.util.ArrayList;
import java.util.List;

public class PluginTConstruct extends ModPlugin {

	public static final String MOD_ID = "tconstruct";
	public static final String MOD_NAME = "Tinkers' Construct";

	public PluginTConstruct() {

		super(MOD_ID, MOD_NAME);
	}

	/* IInitializer */
	@Override
	public boolean initialize() {

		String category = "Plugins";
		String comment = "If TRUE, support for " + MOD_NAME + " is enabled.";
		enable = Loader.isModLoaded(MOD_ID) && ThermalFoundation.CONFIG.getConfiguration().getBoolean(MOD_NAME, category, true, comment);

		if (!enable) {
			return false;
		}
		try {
			addFluid("platinum", 0x89cee8, 1400, EnumRarity.UNCOMMON);
			addFluid("iridium", 0xcccde3, 1400, EnumRarity.UNCOMMON);
			addFluid("invar", 0x949d99, 1400);
			addFluid("constantan", 0xcda25f, 650);
			addFluid("signalum", 0xc95822, 1000, EnumRarity.UNCOMMON);
			addFluid("lumium", 0xeae59e, 1000, EnumRarity.UNCOMMON);
			addFluid("enderium", 0x2a7575, 1600, EnumRarity.RARE);

			ArrayList<FluidStack> alloyList = new ArrayList<>();

			/* INVAR */
			alloyList.add(new FluidStack(FluidRegistry.getFluid("invar"), 144));
			alloyList.add(new FluidStack(FluidRegistry.getFluid("iron"), 96));
			alloyList.add(new FluidStack(FluidRegistry.getFluid("nickel"), 48));
			addAlloy(alloyList);
			alloyList.clear();

			/* CONSTANTAN */
			alloyList.add(new FluidStack(FluidRegistry.getFluid("constantan"), 144));
			alloyList.add(new FluidStack(FluidRegistry.getFluid("copper"), 72));
			alloyList.add(new FluidStack(FluidRegistry.getFluid("nickel"), 72));
			addAlloy(alloyList);
			alloyList.clear();

			/* SIGNALUM */
			alloyList.add(new FluidStack(FluidRegistry.getFluid("signalum"), 144));
			alloyList.add(new FluidStack(FluidRegistry.getFluid("copper"), 108));
			alloyList.add(new FluidStack(FluidRegistry.getFluid("silver"), 36));
			alloyList.add(new FluidStack(FluidRegistry.getFluid("redstone"), 250));
			addAlloy(alloyList);
			alloyList.clear();

			/* LUMIUM */
			alloyList.add(new FluidStack(FluidRegistry.getFluid("lumium"), 144));
			alloyList.add(new FluidStack(FluidRegistry.getFluid("tin"), 108));
			alloyList.add(new FluidStack(FluidRegistry.getFluid("silver"), 36));
			alloyList.add(new FluidStack(FluidRegistry.getFluid("glowstone"), 250));
			addAlloy(alloyList);
			alloyList.clear();

			/* ENDERIUM */
			alloyList.add(new FluidStack(FluidRegistry.getFluid("enderium"), 144));
			alloyList.add(new FluidStack(FluidRegistry.getFluid("lead"), 108));
			alloyList.add(new FluidStack(FluidRegistry.getFluid("platinum"), 36));
			alloyList.add(new FluidStack(FluidRegistry.getFluid("ender"), 250));
			addAlloy(alloyList);
			alloyList.clear();

		} catch (Throwable t) {
			ThermalFoundation.LOG.error("Thermal Foundation: " + MOD_NAME + " Plugin encountered an error:", t);
			error = true;
		}
		return !error;
	}

	@Override
	public boolean register() {

		if (!enable) {
			return false;
		}
		if (!error) {
			ThermalFoundation.LOG.info("Thermal Foundation: " + MOD_NAME + " Plugin Enabled.");
		}
		return !error;
	}

	/* HELPERS */
	private static void addFluid(String fluidName, int color, int temperature, EnumRarity rarity) {

		Fluid fluid = new FluidMolten(fluidName).setColor(color).setTemperature(temperature).setRarity(rarity);
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);

		NBTTagCompound message = new NBTTagCompound();
		message.setString("fluid", fluid.getName());
		message.setString("ore", StringHelper.titleCase(fluidName));
		message.setBoolean("toolforge", true);
		FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", message);
	}

	private static void addFluid(String fluidName, int color, int temperature) {

		addFluid(fluidName, color, temperature, EnumRarity.COMMON);
	}

	private static void addAlloy(List<FluidStack> fluidStacks) {

		NBTTagList tagList = new NBTTagList();

		for (FluidStack stack : fluidStacks) {
			tagList.appendTag(stack.writeToNBT(new NBTTagCompound()));
		}
		NBTTagCompound message = new NBTTagCompound();
		message.setTag("alloy", tagList);
		FMLInterModComms.sendMessage("tconstruct", "alloy", message);
	}

	public static class FluidMolten extends Fluid {

		public int color = 0xFFFFFF;

		public FluidMolten(String fluidName) {

			super(fluidName, new ResourceLocation("tconstruct:blocks/fluids/molten_metal"), new ResourceLocation("tconstruct:blocks/fluids/molten_metal_flow"));
		}

		public FluidMolten setColor(int color) {

			this.color = 0xFF000000 | color;
			return this;
		}

		@Override
		public int getColor() {

			return color;
		}

	}

}
