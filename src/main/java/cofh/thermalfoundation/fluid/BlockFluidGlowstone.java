package cofh.thermalfoundation.fluid;

import codechicken.lib.util.CommonUtils;
import cofh.core.fluid.BlockFluidCoFHBase;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Level;

import java.util.Random;

public class BlockFluidGlowstone extends BlockFluidCoFHBase {

    public static final int LEVELS = 6;
    public static final Material materialFluidGlowstone = new MaterialLiquid(MapColor.YELLOW);

    private static boolean effect = true;
    private static boolean enableSourceCondense = true;
    private static boolean enableSourceFloat = true;
    private static int maxHeight = 120;

    public BlockFluidGlowstone() {
        super("thermalfoundation", TFFluids.fluidGlowstone, materialFluidGlowstone, "glowstone");
        setQuantaPerBlock(LEVELS);
        setTickRate(10);

        setHardness(1F);
        setLightOpacity(0);
        setParticleColor(1.0F, 0.9F, 0.05F);
    }

    @Override
    public boolean preInit() {
        GameRegistry.registerBlock(this, "FluidGlowstone");

        String category = "Fluid.Glowstone";
        String comment = "Enable this for Fluid Glowstone to do...something.";
        effect = ThermalFoundation.config.get(category, "Effect", true, comment).getBoolean();

        comment = "Enable this for Fluid Glowstone Source blocks to condense back into solid Glowstone above a given y-value.";
        enableSourceCondense = ThermalFoundation.config.get(category, "Condense", enableSourceCondense, comment).getBoolean();

        comment = "Enable this for Fluid Glowstone Source blocks to gradually float upwards.";
        enableSourceFloat = ThermalFoundation.config.get(category, "Float", enableSourceFloat, comment).getBoolean();

        int cfgHeight;
        comment = "This adjusts the y-value where Fluid Glowstone will *always* condense, if that is enabled. It will also condense above 80% of this value, if it cannot flow.";
        cfgHeight = ThermalFoundation.config.get(category, "MaxHeight", maxHeight, comment).getInt();

        if (cfgHeight >= maxHeight / 2) {
            maxHeight = cfgHeight;
        } else {
            ThermalFoundation.log.log(Level.INFO, "'Fluid.Glowstone.MaxHeight' config value is out of acceptable range. Using default: " + maxHeight + ".");
        }
        return true;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (!effect) {
            return;
        }
        if (entity instanceof EntityLivingBase) {
            if (entity.motionY < -0.2) {
                entity.motionY *= 0.5;
                if (entity.fallDistance > 20) {
                    entity.fallDistance = 20;
                } else {
                    entity.fallDistance *= 0.95;
                }
            }
        }
        if (CommonUtils.isClientWorld(world)) {
            return;
        }
        if (world.getTotalWorldTime() % 8 == 0 && entity instanceof EntityLivingBase && !((EntityLivingBase) entity).isEntityUndead()) {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SPEED, 6 * 20, 0));
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 6 * 20, 0));
        }
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return TFFluids.fluidGlowstone.getLuminosity();
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (getMetaFromState(state) == 0) {
            if (rand.nextInt(3) == 0) {
                if (shouldSourceBlockCondense(world, pos)) {
                    world.setBlockState(pos, Blocks.GLOWSTONE.getDefaultState());
                    return;
                }
                if (shouldSourceBlockFloat(world, pos)) {
                    world.setBlockState(pos.add(0, densityDir, 0), this.getDefaultState(), 3);//TODO, is default state meta 0?
                    world.setBlockToAir(pos);
                    return;
                }
            }
        } else if (pos.getY() + densityDir > maxHeight) {
            int quantaRemaining = quantaPerBlock - getMetaFromState(state);
            int expQuanta = -101;
            int y2 = pos.getY() - densityDir;

            if (world.getBlockState(pos.add(0, y2, 0)).getBlock() == this || world.getBlockState(pos.add(-1, y2, 0)).getBlock() == this || world.getBlockState(pos.add(1, y2, 0)).getBlock() == this || world.getBlockState(pos.add(0, y2, -1)).getBlock() == this || world.getBlockState(pos.add(0, y2, 1)).getBlock() == this) {
                expQuanta = quantaPerBlock - 1;

            } else {
                int maxQuanta = -100;
                maxQuanta = getLargerQuanta(world, pos.add(-1, 0, 0), maxQuanta);
                maxQuanta = getLargerQuanta(world, pos.add(1, 0, 0), maxQuanta);
                maxQuanta = getLargerQuanta(world, pos.add(0, 0, -1), maxQuanta);
                maxQuanta = getLargerQuanta(world, pos.add(0, 0, 1), maxQuanta);

                expQuanta = maxQuanta - 1;
            }
            // decay calculation
            if (expQuanta != quantaRemaining) {
                quantaRemaining = expQuanta;
                if (expQuanta <= 0) {
                    world.setBlockToAir(pos);
                } else {
                    world.setBlockState(pos, getDefaultState().withProperty(LEVEL, quantaPerBlock - expQuanta), 3);
                    world.scheduleBlockUpdate(pos, this, tickRate, 0);
                    world.notifyNeighborsOfStateChange(pos, this);
                }
            }
            return;
        }
        super.updateTick(world, pos, state, rand);
    }

    protected boolean shouldSourceBlockCondense(World world, BlockPos pos) {
        return enableSourceCondense && (pos.getY() + densityDir > maxHeight || pos.getY() + densityDir > world.getHeight() || pos.getY() + densityDir > maxHeight * 0.8F && !canDisplace(world, pos.add(0, densityDir, 0)));
    }

    protected boolean shouldSourceBlockFloat(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos.add(0, densityDir, 0));
        return enableSourceFloat && (state.getBlock() == this && state.getBlock().getMetaFromState(state) != 0);
    }

}
