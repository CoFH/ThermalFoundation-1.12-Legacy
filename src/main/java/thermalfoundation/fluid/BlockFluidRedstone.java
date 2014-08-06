package thermalfoundation.fluid;

import cofh.core.fluid.BlockFluidCoFHBase;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import thermalfoundation.ThermalFoundation;

public class BlockFluidRedstone extends BlockFluidCoFHBase {

	public static final int LEVELS = 8;
	public static final Material materialFluidRedstone = new MaterialLiquid(MapColor.redColor);

	private static boolean effect = true;

	public BlockFluidRedstone() {

		super("thermalfoundation", TFFluids.fluidRedstone, Material.water, "redstone");
		setQuantaPerBlock(LEVELS);
		setTickRate(5);

		setHardness(100F);
		setLightOpacity(2);
		setParticleColor(0.4F, 0.0F, 0.0F);
	}

	@Override
	public boolean preInit() {

		String category = "tweak";
		String comment = "Enable this for Fluid Redstone to emit a signal proportional to its fluid level.";
		effect = ThermalFoundation.config.get(category, "Fluid.Redstone.Effect", true, comment);

		GameRegistry.registerBlock(this, "FluidRedstone");
		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {

	}

	@Override
	public boolean canProvidePower() {

		return effect;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {

		return effect ? world.getBlockMetadata(x, y, z) * 2 + 1 : 0;
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {

		return TFFluids.fluidRedstone.getLuminosity();
	}

}
