package thermalfoundation.fluid;

import cofh.fluid.BlockFluidCoFHBase;
import cofh.util.CoreUtils;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import thermalfoundation.ThermalFoundation;

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

		String category = "tweak";
		String comment = "Enable this for Fluid Ender to randomly teleport entities on contact.";
		effect = ThermalFoundation.config.get(category, "Fluid.Ender.Effect", true, comment);

		GameRegistry.registerBlock(this, "FluidEnder");
		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {

		if (!effect) {
			return;
		}
		if (world.getTotalWorldTime() % 4 == 0) {
			int x2 = x - 8 + world.rand.nextInt(17);
			int y2 = y + world.rand.nextInt(8);
			int z2 = z - 8 + world.rand.nextInt(17);

			if (!world.getBlock(x2, y2, z2).getMaterial().isSolid()) {
				if (entity instanceof EntityLivingBase) {
					CoreUtils.teleportEntityTo((EntityLivingBase) entity, x2, y2, z2);
				} else {
					entity.setPosition(x2, y2, z2);
					entity.worldObj.playSoundEffect(x2, y2, z2, "mob.endermen.portal", 1.0F, 1.0F);
					entity.playSound("mob.endermen.portal", 1.0F, 1.0F);
				}
			}
		}
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {

		return TFFluids.fluidEnder.getLuminosity();
	}

}
