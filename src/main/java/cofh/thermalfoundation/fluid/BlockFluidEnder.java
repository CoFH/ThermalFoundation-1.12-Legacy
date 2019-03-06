package cofh.thermalfoundation.fluid;

import cofh.core.fluid.BlockFluidCore;
import cofh.core.util.CoreUtils;
import cofh.core.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.init.TFFluids;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BlockFluidEnder extends BlockFluidCore {

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

	public static void config() {

		String category = "Fluid.Ender";
		String comment = "If TRUE, Fluid Ender will randomly teleport entities on contact.";
		effect = ThermalFoundation.CONFIG.getConfiguration().getBoolean("Effect", category, effect, comment);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {

		if (!effect || ServerHelper.isClientWorld(world)) {
			return;
		}
		if (entity instanceof EntityItem || entity instanceof EntityXPOrb) {
			return;
		}
		if (world.getTotalWorldTime() % 8 == 0) {
			BlockPos randPos = pos.add(-8 + world.rand.nextInt(17), world.rand.nextInt(8), -8 + world.rand.nextInt(17));

			if (!world.getBlockState(randPos).getMaterial().isSolid()) {
				if (entity instanceof EntityLivingBase) {
					CoreUtils.teleportEntityTo(entity, randPos);
				} else {
					entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
					entity.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
				}
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
		ForgeRegistries.BLOCKS.register(this);
		ItemBlock itemBlock = new ItemBlock(this);
		itemBlock.setRegistryName(this.getRegistryName());
		ForgeRegistries.ITEMS.register(itemBlock);

		config();

		return true;
	}

}
