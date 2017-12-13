package cofh.thermalfoundation.fluid;

import cofh.core.fluid.BlockFluidInteractive;
import cofh.core.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.init.TFFluids;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Random;

public class BlockFluidPetrotheum extends BlockFluidInteractive {

	public static final int LEVELS = 6;
	public static final Material materialFluidPetrotheum = new MaterialLiquid(MapColor.STONE);

	private static boolean enableSourceFall = true;
	private static boolean effect = true;
	private static boolean extreme = false;

	public BlockFluidPetrotheum(Fluid fluid) {

		super(fluid, materialFluidPetrotheum, "thermalfoundation", "petrotheum");
		setQuantaPerBlock(LEVELS);
		setTickRate(10);

		setHardness(1000F);
		setLightOpacity(1);
		setParticleColor(0.4F, 0.3F, 0.2F);
	}

	public static void config() {

		String category = "Fluid.Petrotheum";
		String comment;

		comment = "If TRUE, Fluid Petrotheum will break apart stone blocks.";
		effect = ThermalFoundation.CONFIG.getConfiguration().getBoolean("Effect", category, effect, comment);

		comment = "If TRUE, Fluid Petrotheum will have an EXTREME effect on stone blocks. Fun but not recommended.";
		extreme = ThermalFoundation.CONFIG.getConfiguration().getBoolean("Extreme", category, extreme, comment);

		comment = "If TRUE, Fluid Petrotheum Source blocks will gradually fall downwards.";
		enableSourceFall = ThermalFoundation.CONFIG.getConfiguration().getBoolean("Fall", category, enableSourceFall, comment);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {

		if (!effect) {
			return;
		}
		if (ServerHelper.isClientWorld(world)) {
			return;
		}
		if (world.getTotalWorldTime() % 8 != 0) {
			return;
		}
		if (world.getTotalWorldTime() % 8 == 0 && entity instanceof EntityLivingBase && !((EntityLivingBase) entity).isEntityUndead()) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.HASTE, 6 * 20, 0));
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 6 * 20, 0));
		}
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {

		return TFFluids.fluidPetrotheum.getLuminosity();
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {

		if (effect) {
			checkForInteraction(world, pos);
		}
		if (enableSourceFall && state.getBlock().getMetaFromState(state) == 0) {
			BlockPos offsetPos = pos.add(0, densityDir, 0);
			IBlockState offsetState = world.getBlockState(offsetPos);
			int bMeta = offsetState.getBlock().getMetaFromState(offsetState);

			if (offsetState.getBlock() == this && bMeta != 0) {
				world.setBlockState(offsetPos, getDefaultState(), 3);
				world.setBlockToAir(pos);
				return;
			}
		}
		super.updateTick(world, pos, state, rand);
	}

	protected void checkForInteraction(World world, BlockPos pos) {

		if (world.getBlockState(pos).getBlock() != this) {
			return;
		}

		for (EnumFacing face : EnumFacing.VALUES) {
			interactWithBlock(world, pos.offset(face));
		}
	}

	protected void interactWithBlock(World world, BlockPos pos) {

		IBlockState state = world.getBlockState(pos);

		if (state.getBlock().isAir(state, world, pos) || state.getBlock() == this) {
			return;
		}

		if (extreme && state.getMaterial() == Material.ROCK && state.getBlock().getBlockHardness(state, world, pos) > 0) {
			state.getBlock().dropBlockAsItem(world, pos, state, 0);
			world.setBlockToAir(pos);
			triggerInteractionEffects(world, pos);
		} else if (hasInteraction(state)) {
			world.setBlockState(pos, getInteraction(state), 3);
		}
	}

	protected void triggerInteractionEffects(World world, BlockPos pos) {

		world.playSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 0.5F, 0.9F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F, false);
	}

	/* HELPERS */
	public void addInteractions() {

		addInteraction(Blocks.STONE, Blocks.GRAVEL);
		addInteraction(Blocks.COBBLESTONE, Blocks.GRAVEL);
		addInteraction(Blocks.STONEBRICK, Blocks.GRAVEL);
		addInteraction(Blocks.MOSSY_COBBLESTONE, Blocks.GRAVEL);
	}

	/* IInitializer */
	@Override
	public boolean initialize() {

		this.setRegistryName("fluid_petrotheum");
		ForgeRegistries.BLOCKS.register(this);
		ItemBlock itemBlock = new ItemBlock(this);
		itemBlock.setRegistryName(this.getRegistryName());
		ForgeRegistries.ITEMS.register(itemBlock);

		config();
		addInteractions();

		return true;
	}

}
