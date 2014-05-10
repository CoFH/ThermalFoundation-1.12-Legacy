package thermalfoundation.fluid;

import cofh.fluid.BlockFluidInteractive;
import cofh.util.MathHelper;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.world.IBlockAccess;

import thermalfoundation.ThermalFoundation;

public class BlockFluidMana extends BlockFluidInteractive {

	public static final int LEVELS = 6;
	public static final Material materialFluidMana = new MaterialLiquid(MapColor.lapisColor);

	private static boolean effect = true;

	public BlockFluidMana() {

		super("thermalfoundation", TFFluids.fluidMana, materialFluidMana, "mana");
		setQuantaPerBlock(LEVELS);
		setTickRate(20);

		setHardness(2000F);
		setLightOpacity(2);
		setParticleColor(0.2F, 0.0F, 0.4F);
	}

	@Override
	public boolean preInit() {

		String category = "tweak";
		String comment = "Enable this for Fluid Mana to do...things.";
		effect = ThermalFoundation.config.get(category, "Fluid.Mana.Effect", true, comment);

		GameRegistry.registerBlock(this, "FluidMana");
		return true;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {

		return effect ? MathHelper.RANDOM.nextInt(15) : 0;
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {

		return TFFluids.fluidMana.getLuminosity();
	}

}
