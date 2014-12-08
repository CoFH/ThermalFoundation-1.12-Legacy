package thermalfoundation.fluid;

import cofh.core.fluid.BlockFluidCoFHBase;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import thermalfoundation.ThermalFoundation;

public class BlockFluidCoal extends BlockFluidCoFHBase {

	public static final int LEVELS = 6;
	public static final Material materialFluidCoal = new MaterialLiquid(MapColor.grayColor);

	private static boolean effect = true;

	public BlockFluidCoal() {

		super("thermalfoundation", TFFluids.fluidCoal, Material.water, "coal");
		setQuantaPerBlock(LEVELS);
		setTickRate(30);

		setHardness(100F);
		setLightOpacity(7);
		setParticleColor(0.2F, 0.2F, 0.2F);
	}

	@Override
	public boolean preInit() {

		GameRegistry.registerBlock(this, "FluidCoal");

		String category = "tweak";
		String comment = "Enable this for Fluid Coal to be flammable.";
		effect = ThermalFoundation.config.get(category, "Fluid.Coal.Flammable", true, comment);

		return true;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {

		return effect ? 300 : 0;
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {

		return 25;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {

		return effect;
	}

	@Override
	public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side) {

		return effect;
	}

}
