package cofh.thermalfoundation.fluid;

import cofh.core.fluid.BlockFluidCoFHBase;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockFluidSteam extends BlockFluidCoFHBase {

	public static final int LEVELS = 8;
	public static final Material materialFluidSteam = new MaterialLiquid(MapColor.silverColor);

	// private static boolean effect = true;

	public BlockFluidSteam(Fluid fluid) {

		super(fluid, materialFluidSteam, "thermalfoundation", "steam");
		setQuantaPerBlock(LEVELS);
		setTickRate(2);

		setHardness(1F);
		setLightOpacity(0);
		setParticleColor(0.9F, 0.9F, 0.9F);
	}

	@Override
	public boolean preInit() {

		GameRegistry.registerBlock(this, "FluidSteam");

		return true;
	}

}
