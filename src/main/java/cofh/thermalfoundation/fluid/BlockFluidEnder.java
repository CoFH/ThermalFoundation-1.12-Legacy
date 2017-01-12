package cofh.thermalfoundation.fluid;

import cofh.core.fluid.BlockFluidCoFHBase;
import cofh.core.util.CoreUtils;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockFluidEnder extends BlockFluidCoFHBase {

    public static final int LEVELS = 4;
    public static final Material materialFluidEnder = new MaterialLiquid(MapColor.GREEN);

    private static boolean effect = true;

    public BlockFluidEnder(Fluid fluid) {

        super(fluid, materialFluidEnder, "thermalfoundation", "ender");
        setQuantaPerBlock(LEVELS);
        setTickRate(20);

        setHardness(2000F);
        setLightOpacity(7);
        setParticleColor(0.05F, 0.2F, 0.2F);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (!effect || ServerHelper.isClientWorld(world)) {
            return;
        }
        if (world.getTotalWorldTime() % 8 == 0) {
            int x = pos.getX() - 8 + world.rand.nextInt(17);
            int y = pos.getY() + world.rand.nextInt(8);
            int z = pos.getZ() - 8 + world.rand.nextInt(17);

            if (!world.getBlockState(new BlockPos(x, y, z)).getMaterial().isSolid()) {
                CoreUtils.teleportEntityTo(entity, x, y, z);
            }
        }
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {

        return TFFluids.fluidEnder.getLuminosity();
    }

    /* IInitializer */
    @Override
    public boolean preInit() {

        this.setRegistryName("fluid_ender");
        GameRegistry.register(this);
        ItemBlock itemBlock = new ItemBlock(this);
        itemBlock.setRegistryName(this.getRegistryName());
        GameRegistry.register(itemBlock);

        String category = "Fluid.Ender";
        String comment = "Enable this for Fluid Ender to randomly teleport entities on contact.";
        effect = ThermalFoundation.config.get(category, "Effect", true, comment).getBoolean();

        return true;
    }

}
