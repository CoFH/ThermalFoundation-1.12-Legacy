package cofh.thermalfoundation.fluid;

import cofh.core.fluid.BlockFluidCoFHBase;
import cofh.core.util.CoreUtils;
import cofh.thermalfoundation.ThermalFoundation;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFluidEnder extends BlockFluidCoFHBase {

	public static final int LEVELS = 4;
	public static final Material materialFluidEnder = new MaterialLiquid(MapColor.greenColor);

	private static boolean effect = true;

	public BlockFluidEnder() {

		super("thermalfoundation", TFFluids.fluidEnder, materialFluidEnder, "ender");
		setQuantaPerBlock(LEVELS);
		setTickRate(30);

		setHardness(2000F);
		setLightOpacity(7);
		setParticleColor(0.05F, 0.2F, 0.2F);
	}

	@Override
	public boolean preInit() {

		GameRegistry.registerBlock(this, "FluidEnder");

		String category = "tweak";
		String comment = "Enable this for Fluid Ender to randomly teleport entities on contact.";
		effect = ThermalFoundation.config.get(category, "Fluid.Ender.Effect", true, comment);

		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {

		if (!effect || world.isRemote) {
			return;
		}
		if (world.getTotalWorldTime() % 4 == 0) {
			int x2 = x - 8 + world.rand.nextInt(17);
			int y2 = y + world.rand.nextInt(8);
			int z2 = z - 8 + world.rand.nextInt(17);

			if (!world.getBlock(x2, y2, z2).getMaterial().isSolid()) {
				CoreUtils.teleportEntityTo(entity, x2, y2, z2);
			}
		}
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {

		return TFFluids.fluidEnder.getLuminosity();
	}

}
