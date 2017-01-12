package cofh.thermalfoundation.fluid;

import cofh.core.fluid.BlockFluidCoFHBase;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Level;

import java.util.Random;

public class BlockFluidAerotheum extends BlockFluidCoFHBase {

    public static final int LEVELS = 6;
    public static final Material materialFluidAerotheum = new MaterialLiquid(MapColor.AIR);

    private static boolean effect = true;
    private static boolean enableSourceDissipate = true;
    private static boolean enableSourceFloat = true;
    private static int maxHeight = 120;

    public BlockFluidAerotheum(Fluid fluid) {

        super(fluid, materialFluidAerotheum, "thermalfoundation", "aerotheum");
        setQuantaPerBlock(LEVELS);
        setTickRate(8);

        setHardness(1F);
        setLightOpacity(0);
        setParticleColor(0.65F, 0.65F, 0.48F);
    }

    protected boolean shouldSourceBlockDissipate(World world, BlockPos pos) {

        int y = pos.getY();
        return enableSourceDissipate && (y + densityDir > maxHeight || y + densityDir > world.getHeight() || y + densityDir > maxHeight * 0.8F && !canDisplace(world, pos.add(0, densityDir, 0)));
    }

    protected boolean shouldSourceBlockFloat(World world, BlockPos pos) {

        IBlockState state = world.getBlockState(pos.add(0, densityDir, 0));
        return enableSourceFloat && state.getBlock() == this && getMetaFromState(state) != 0;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {

        if (!effect) {
            return;
        }
        if (entity instanceof EntityLivingBase) {
            if (entity.motionX > 0.1) {
                entity.motionX = 0.1;
            }
            if (entity.motionZ > 0.1) {
                entity.motionZ = 0.1;
            }
            if (entity.motionY < -0.2) {
                entity.motionY *= 0.5;
                if (entity.fallDistance > 20) {
                    entity.fallDistance = 20;
                } else {
                    entity.fallDistance *= 0.5;
                }
            }
        } else if (entity instanceof IProjectile) {
            entity.motionX *= world.rand.nextGaussian() * 1.5;
            entity.motionZ *= world.rand.nextGaussian() * 1.5;
        }
        if (ServerHelper.isClientWorld(world)) {
            return;
        }
        if (world.getTotalWorldTime() % 8 == 0 && entity instanceof EntityLivingBase && !((EntityLivingBase) entity).isEntityUndead()) {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 3 * 20, 0));
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 30 * 20, 0));
        }
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {

        return TFFluids.fluidAerotheum.getLuminosity();
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (getMetaFromState(state) == 0) {
            if (rand.nextInt(3) == 0) {
                if (shouldSourceBlockDissipate(world, pos)) {
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

    /* IInitializer */
    @Override
    public boolean preInit() {

        this.setRegistryName("fluid_aerotheum");
        GameRegistry.register(this);
        ItemBlock itemBlock = new ItemBlock(this);
        itemBlock.setRegistryName(this.getRegistryName());
        GameRegistry.register(itemBlock);

        String category = "Fluid.Aerotheum";
        String comment = "Enable this for Fluid Aerotheum to do...things.";
        effect = ThermalFoundation.config.get(category, "Effect", true, comment).getBoolean();

        comment = "Enable this for Fluid Aerotheum Source blocks to dissipate back into air above a given y-value.";
        enableSourceDissipate = ThermalFoundation.config.get(category, "Dissipate", enableSourceDissipate, comment).getBoolean();

        comment = "Enable this for Fluid Aerotheum Source blocks to gradually float upwards.";
        enableSourceFloat = ThermalFoundation.config.get(category, "Float", enableSourceFloat, comment).getBoolean();

        int cfgHeight;
        comment = "This adjusts the y-value where Fluid Aerotheum will *always* dissipate, if that is enabled.";
        cfgHeight = ThermalFoundation.config.get(category, "MaxHeight", maxHeight, comment).getInt();

        if (cfgHeight >= maxHeight / 2) {
            maxHeight = cfgHeight;
        } else {
            ThermalFoundation.log.log(Level.INFO, "'Fluid.Aerotheum.MaxHeight' config value is out of acceptable range. Using default: " + maxHeight + ".");
        }
        return true;
    }

}
