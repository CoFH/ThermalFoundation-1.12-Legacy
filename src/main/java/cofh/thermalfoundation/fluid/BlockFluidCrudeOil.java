package cofh.thermalfoundation.fluid;

import cofh.core.fluid.BlockFluidCore;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BlockFluidCrudeOil extends BlockFluidCore {

	public static final int LEVELS = 6;
	public static final Material materialFluidCrudeOil = new MaterialLiquid(MapColor.BLACK);

	private static boolean effect = true;

	public BlockFluidCrudeOil(Fluid fluid) {

		super(fluid, Material.WATER, "thermalfoundation", "crude_oil");
		setQuantaPerBlock(LEVELS);
		setTickRate(10);

		setHardness(100F);
		setLightOpacity(7);
		setParticleColor(0.2F, 0.2F, 0.2F);
	}

	public static void config() {

		String category = "Fluid.CrudeOil";
		String comment = "If TRUE, Crude Oil will be flammable.";
		effect = ThermalFoundation.CONFIG.getConfiguration().getBoolean("Flammable", category, effect, comment);
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
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {

		return effect;
	}

	@Override
	public boolean isFireSource(World world, BlockPos pos, EnumFacing face) {

		return effect;
	}

	/* IInitializer */
	@Override
	public boolean initialize() {

		this.setRegistryName("fluid_crude_oil");
		ForgeRegistries.BLOCKS.register(this);
		ItemBlock itemBlock = new ItemBlock(this);
		itemBlock.setRegistryName(this.getRegistryName());
		ForgeRegistries.ITEMS.register(itemBlock);

		config();

		return true;
	}

}
