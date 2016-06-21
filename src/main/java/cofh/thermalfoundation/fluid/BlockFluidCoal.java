package cofh.thermalfoundation.fluid;

import cofh.core.fluid.BlockFluidCoFHBase;
import cofh.thermalfoundation.ThermalFoundation;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockFluidCoal extends BlockFluidCoFHBase {

	public static final int LEVELS = 6;
	public static final Material materialFluidCoal = new MaterialLiquid(MapColor.grayColor);

	private static boolean effect = true;

	public BlockFluidCoal(Fluid fluid) {

		super(fluid, Material.water, "thermalfoundation", "coal");
		setQuantaPerBlock(LEVELS);
		setTickRate(10);

		setHardness(100F);
		setLightOpacity(7);
		setParticleColor(0.2F, 0.2F, 0.2F);
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return effect ? 300 : 0;
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return 25;
	}

	@Override
	public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {

		return effect;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return effect;
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		GameRegistry.registerBlock(this, "FluidCoal");

		String category = "Fluid.Coal";
		String comment = "Enable this for Fluid Coal to be flammable.";
		effect = ThermalFoundation.CONFIG.get(category, "Flammable", true, comment);

		return true;
	}

}
