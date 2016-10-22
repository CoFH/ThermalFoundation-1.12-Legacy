package cofh.thermalfoundation.fluid;

import codechicken.lib.util.CommonUtils;
import codechicken.lib.util.EntityUtils;
import codechicken.lib.vec.Vector3;
import cofh.core.fluid.BlockFluidCoFHBase;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockFluidEnder extends BlockFluidCoFHBase {

    public static final int LEVELS = 4;
    public static final Material materialFluidEnder = new MaterialLiquid(MapColor.GREEN);

    private static boolean effect = true;

    public BlockFluidEnder() {
        super("thermalfoundation", TFFluids.fluidEnder, materialFluidEnder, "ender");
        setQuantaPerBlock(LEVELS);
        setTickRate(20);

        setHardness(2000F);
        setLightOpacity(7);
        setParticleColor(0.05F, 0.2F, 0.2F);
    }

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

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (!effect || CommonUtils.isClientWorld(world) || !(entity instanceof EntityLivingBase)) {
            return;
        }
        if (world.getTotalWorldTime() % 8 == 0) {
            int x2 = pos.getX() - 8 + world.rand.nextInt(17);
            int y2 = pos.getY() + world.rand.nextInt(8);
            int z2 = pos.getZ() - 8 + world.rand.nextInt(17);

            if (!world.getBlockState(new BlockPos(x2, y2, z2)).getMaterial().isSolid()) {
                EntityUtils.teleportEntityTo((EntityLivingBase) entity, new Vector3(x2, y2, z2));
            }
        }
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return TFFluids.fluidEnder.getLuminosity();
    }

}
