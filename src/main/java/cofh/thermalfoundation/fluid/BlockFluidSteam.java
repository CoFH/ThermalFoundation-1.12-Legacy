package cofh.thermalfoundation.fluid;

import cofh.core.fluid.BlockFluidCoFHBase;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockFluidSteam extends BlockFluidCoFHBase {

	public static final int LEVELS = 8;
	public static final Material materialFluidSteam = new MaterialLiquid(MapColor.SILVER);

    public BlockFluidSteam(Fluid fluid) {

        super(fluid, materialFluidSteam, "thermalfoundation", "steam");
		setQuantaPerBlock(LEVELS);
		setTickRate(2);

		setHardness(1F);
		setLightOpacity(0);
		setParticleColor(0.9F, 0.9F, 0.9F);
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		this.setRegistryName("fluid_steam");
		GameRegistry.register(this);
		ItemBlock itemBlock = new ItemBlock(this);
		itemBlock.setRegistryName(this.getRegistryName());
		GameRegistry.register(itemBlock);
		return true;
	}

}
