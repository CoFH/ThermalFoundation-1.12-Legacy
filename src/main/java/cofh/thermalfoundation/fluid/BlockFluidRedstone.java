package cofh.thermalfoundation.fluid;

import cofh.core.fluid.BlockFluidCoFHBase;
import cofh.thermalfoundation.ThermalFoundation;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockFluidRedstone extends BlockFluidCoFHBase {

	public static final int LEVELS = 8;
	public static final Material materialFluidRedstone = new MaterialLiquid(MapColor.redColor);

	private static boolean effect = true;

	public BlockFluidRedstone(Fluid fluid) {

		super(fluid, Material.water, "thermalfoundation", "redstone");
		setQuantaPerBlock(LEVELS);
		setTickRate(5);

		setHardness(100F);
		setLightOpacity(2);
		setParticleColor(0.4F, 0.0F, 0.0F);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {

	}

	@Override
	public int getWeakPower(IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing side) {

		return effect ? getMetaFromState(state) * 2 + 1 : 0;
	}

	@Override
	public boolean canProvidePower() {

		return effect;
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		GameRegistry.registerBlock(this, "FluidRedstone");

		String category = "Fluid.Redstone";
		String comment = "Enable this for Fluid Redstone to emit a signal proportional to its fluid level.";
		effect = ThermalFoundation.CONFIG.get(category, "Effect", true, comment);

		return true;
	}

}
