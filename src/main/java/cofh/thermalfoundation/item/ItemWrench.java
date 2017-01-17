package cofh.thermalfoundation.item;

import codechicken.lib.raytracer.RayTracer;
import cofh.api.block.IDismantleable;
import cofh.api.core.IInitializer;
import cofh.api.item.IToolHammer;
import cofh.asm.relauncher.Implementable;
import cofh.core.item.ItemCoFHBase;
import cofh.core.util.StateMapper;
import cofh.lib.util.helpers.BlockHelper;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

import static cofh.lib.util.helpers.ItemHelper.ShapedRecipe;
import static cofh.lib.util.helpers.ItemHelper.addRecipe;

@Implementable ({ "buildcraft.api.tools.IToolWrench", "com.brandon3055.draconicevolution.api.ICrystalBinder" })
public class ItemWrench extends ItemCoFHBase implements IInitializer, IToolHammer {

	public ItemWrench() {

		super("thermalfoundation");

		setUnlocalizedName("util", "wrench");
		setCreativeTab(ThermalFoundation.tabCommon);

		setHarvestLevel("wrench", 1);
		setHasSubtypes(true);
		setMaxStackSize(1);
	}

	@Override
	public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {

		return true;
	}

	@Override
	public boolean isFull3D() {

		return true;
	}

	@Override
	public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		if (state == null) {
			return EnumActionResult.PASS;
		}
		RayTraceResult traceResult = RayTracer.retrace(player);
		PlayerInteractEvent event = new PlayerInteractEvent.RightClickBlock(player, hand, stack, pos, side, traceResult.hitVec);
		if (MinecraftForge.EVENT_BUS.post(event) || event.getResult() == Result.DENY) {
			return EnumActionResult.PASS;
		}
		if (ServerHelper.isServerWorld(world) && player.isSneaking() && block instanceof IDismantleable && ((IDismantleable) block).canDismantle(world, pos, state, player)) {
			((IDismantleable) block).dismantleBlock(world, pos, state, player, false);
			return EnumActionResult.SUCCESS;
		}
		if (BlockHelper.canRotate(block)) {
			if (player.isSneaking()) {
				world.setBlockState(pos, BlockHelper.rotateVanillaBlockAlt(world, state, pos), 3);
				world.playSound(null, pos, block.getSoundType(state, world, pos, player).getBreakSound(), SoundCategory.BLOCKS, 1.0F, 0.6F);
			} else {
				world.setBlockState(pos, BlockHelper.rotateVanillaBlock(world, state, pos), 3);
				world.playSound(null, pos, block.getSoundType(state, world, pos, player).getBreakSound(), SoundCategory.BLOCKS, 1.0F, 0.8F);
			}
			return ServerHelper.isServerWorld(world) ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
		} else if (!player.isSneaking() && block.rotateBlock(world, pos, side)) {
			player.swingArm(hand);
			return ServerHelper.isServerWorld(world) ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
		}
		return EnumActionResult.PASS;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {

		Multimap multimap = HashMultimap.create();
		if (slot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", 1, 0));
		}
		return multimap;
	}

	/* IModelRegister */
	@Override
	@SideOnly (Side.CLIENT)
	public void registerModels() {

		StateMapper mapper = new StateMapper(modName, "util", name);
		ModelBakery.registerItemVariants(this);
		ModelLoader.setCustomMeshDefinition(this, mapper);

		for (Map.Entry<Integer, ItemEntry> entry : itemMap.entrySet()) {
			ModelLoader.setCustomModelResourceLocation(this, entry.getKey(), new ModelResourceLocation(modName + ":" + "util", "type=" + entry.getValue().name));
		}
	}

	/* IToolHammer */
	@Override
	public boolean isUsable(ItemStack item, EntityLivingBase user, BlockPos pos) {

		return true;
	}

	@Override
	public boolean isUsable(ItemStack item, EntityLivingBase user, Entity entity) {

		return true;
	}

	@Override
	public void toolUsed(ItemStack item, EntityLivingBase user, BlockPos pos) {

	}

	@Override
	public void toolUsed(ItemStack item, EntityLivingBase user, Entity entity) {

	}

	/* IToolWrench */
	public boolean canWrench(EntityPlayer player, BlockPos pos) {

		return true;
	}

	public boolean canWrench(EntityPlayer player, Entity entity) {

		return true;
	}

	public void wrenchUsed(EntityPlayer player, BlockPos pos) {

	}

	public void wrenchUsed(EntityPlayer player, Entity entity) {

	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		wrenchBasic = addItem(0, "wrenchBasic");

		return true;
	}

	@Override
	public boolean initialize() {

		return true;
	}

	@Override
	public boolean postInit() {

		addRecipe(ShapedRecipe(wrenchBasic, new Object[] { "I I", " T ", " I ", 'I', "ingotIron", 'T', "ingotTin" }));

		return true;
	}

	/* REFERENCES */
	public static ItemStack wrenchBasic;

	/* TYPE */
	enum Type {
		BASIC, ENDER
	}

}
