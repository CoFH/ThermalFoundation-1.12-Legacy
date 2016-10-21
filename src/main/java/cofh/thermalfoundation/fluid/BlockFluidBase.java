package cofh.thermalfoundation.fluid;

import codechicken.lib.render.particle.ParticleDrip;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

import static net.minecraft.util.EnumFacing.UP;

public abstract class BlockFluidBase extends BlockFluidClassic {

    //String name = "";
    String modName = "cofh";
    protected float particleRed = 1.0F;
    protected float particleGreen = 1.0F;
    protected float particleBlue = 1.0F;
    protected boolean shouldDisplaceFluids = false;

    public BlockFluidBase(Fluid fluid, Material material, String name) {

        super(fluid, material);

        //this.name = name.substring(0, 1).toUpperCase(Locale.US) + name.substring(1);

        //setRenderPass(1);
        setUnlocalizedName(modName + ".fluid." + name);
        displacements.put(this, false);
    }

    public BlockFluidBase(String modName, Fluid fluid, Material material, String name) {

        super(fluid, material);

        //this.name = StringHelper.titleCase(name);
        this.modName = modName;

        //setRenderPass(1);
        setUnlocalizedName(modName + ".fluid." + name);
        displacements.put(this, false);
    }

    public BlockFluidBase setParticleColor(int c) {

        return setParticleColor(((c >> 16) & 255) / 255f, ((c >> 8) & 255) / 255f, ((c >> 0) & 255) / 255f);
    }

    public BlockFluidBase setParticleColor(float particleRed, float particleGreen, float particleBlue) {

        this.particleRed = particleRed;
        this.particleGreen = particleGreen;
        this.particleBlue = particleBlue;

        return this;
    }

    public BlockFluidBase setDisplaceFluids(boolean a) {

        this.shouldDisplaceFluids = a;
        return this;
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, net.minecraft.entity.EntityLiving.SpawnPlacementType type) {

        return false;
    }

    public boolean preInit() {

        return true;
    }

    //@Override
    //@SideOnly(Side.CLIENT)
    //public IIcon getIcon(int side, int meta) {
    //
    //	return side <= 1 ? IconRegistry.getIcon("Fluid" + name) : IconRegistry.getIcon("Fluid" + name, 1);
    //}

    //@Override
    //@SideOnly(Side.CLIENT)
    //public void registerBlockIcons(IIconRegister ir) {
    //
    //	IconRegistry.addIcon("Fluid" + name, modName + ":fluid/Fluid_" + name + "_Still", ir);
    //	IconRegistry.addIcon("Fluid" + name + "1", modName + ":fluid/Fluid_" + name + "_Flow", ir);
    //}

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {

        super.randomDisplayTick(state, world, pos, rand);

        double px = pos.getX() + rand.nextFloat();
        double py = pos.getY() - 1.05D;
        double pz = pos.getZ() + rand.nextFloat();

        if (density < 0) {
            py = pos.getY() + 2.10D;
        }
        if (rand.nextInt(20) == 0 && world.isSideSolid(pos.add(0, densityDir, 0), densityDir == -1 ? UP : EnumFacing.DOWN) && !world.getBlockState(pos.add(0, 2 * densityDir, 0)).getMaterial().blocksMovement()) {
            Particle fx = new ParticleDrip(world, px, py, pz, particleRed, particleGreen, particleBlue, densityDir);
            FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
        }
    }

    @Override
    public boolean canDisplace(IBlockAccess world, BlockPos pos) {

        if (!shouldDisplaceFluids && world.getBlockState(pos).getMaterial().isLiquid()) {
            return false;
        }
        return super.canDisplace(world, pos);
    }

    @Override
    public boolean displaceIfPossible(World world, BlockPos pos) {

        if (!shouldDisplaceFluids && world.getBlockState(pos).getMaterial().isLiquid()) {
            return false;
        }
        return super.displaceIfPossible(world, pos);
    }

}
