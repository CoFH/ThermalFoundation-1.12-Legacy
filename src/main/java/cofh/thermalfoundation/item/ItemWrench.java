package cofh.thermalfoundation.item;

import cofh.api.block.IDismantleable;
import cofh.api.item.IToolHammer;
import cofh.core.item.ItemMulti;
import cofh.core.util.StateMapper;
import cofh.core.util.core.IInitializer;
import cofh.core.util.helpers.BlockHelper;
import cofh.core.util.helpers.ItemHelper;
import cofh.core.util.helpers.ServerHelper;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

import static cofh.core.util.helpers.RecipeHelper.addShapedRecipe;

//TODO FIXME @Optional
//@Implementable ({ "buildcraft.api.tools.IToolWrench", "com.brandon3055.draconicevolution.api.ICrystalBinder" })
public class ItemWrench extends ItemMulti implements IInitializer, IToolHammer {

	public ItemWrench() {

		super("thermalfoundation");

		setUnlocalizedName("util", "wrench");
		setCreativeTab(ThermalFoundation.tabUtils);

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
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		if (world.isAirBlock(pos)) {
			return EnumActionResult.PASS;
		}
		PlayerInteractEvent event = new PlayerInteractEvent.RightClickBlock(player, hand, pos, side, new Vec3d(hitX, hitY, hitZ));
		if (MinecraftForge.EVENT_BUS.post(event) || event.getResult() == Result.DENY) {
			return EnumActionResult.PASS;
		}
		if (ServerHelper.isServerWorld(world) && player.isSneaking() && block instanceof IDismantleable && ((IDismantleable) block).canDismantle(world, pos, state, player)) {
			((IDismantleable) block).dismantleBlock(world, pos, state, player, false);
			return EnumActionResult.SUCCESS;
		}
		if (BlockHelper.canRotate(block)) {
			world.setBlockState(pos, BlockHelper.rotateVanillaBlock(world, state, pos), 3);
			player.swingArm(hand);
			return ServerHelper.isServerWorld(world) ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
		} else if (!player.isSneaking() && block.rotateBlock(world, pos, side)) {
			player.swingArm(hand);
			return ServerHelper.isServerWorld(world) ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
		}
		return EnumActionResult.PASS;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {

		int metadata = ItemHelper.getItemDamage(stack);

		Multimap<String, AttributeModifier> multimap = HashMultimap.create();
		if (slot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", metadata > 0 ? 2 : 1, 0));
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

	//	/* IMultiModeItem */
	//	@Override
	//	public int getMode(ItemStack stack) {
	//
	//		return !stack.hasTagCompound() ? 0 : stack.getTagCompound().getInteger("Mode");
	//	}
	//
	//	@Override
	//	public boolean setMode(ItemStack stack, int mode) {
	//
	//		if (!stack.hasTagCompound()) {
	//			stack.setTagCompound(new NBTTagCompound());
	//		}
	//		stack.getTagCompound().setInteger("Mode", mode);
	//		return false;
	//	}
	//
	//	@Override
	//	public boolean incrMode(ItemStack stack) {
	//
	//		if (!stack.hasTagCompound()) {
	//			stack.setTagCompound(new NBTTagCompound());
	//		}
	//		int curMode = getMode(stack);
	//		curMode++;
	//		if (curMode >= getNumModes(stack)) {
	//			curMode = 0;
	//		}
	//		stack.getTagCompound().setInteger("Mode", curMode);
	//		return true;
	//	}
	//
	//	@Override
	//	public boolean decrMode(ItemStack stack) {
	//
	//		if (!stack.hasTagCompound()) {
	//			stack.setTagCompound(new NBTTagCompound());
	//		}
	//		int curMode = getMode(stack);
	//		curMode--;
	//		if (curMode <= 0) {
	//			curMode = getNumModes(stack) - 1;
	//		}
	//		stack.getTagCompound().setInteger("Mode", curMode);
	//		return true;
	//	}
	//
	//	@Override
	//	public int getNumModes(ItemStack stack) {
	//
	//		return 3;
	//	}
	//
	//	@Override
	//	public void onModeChange(EntityPlayer player, ItemStack stack) {
	//
	//		ChatHelper.sendIndexedChatMessageToPlayer(player, new TextComponentTranslation("info.thermalexpansion.capacitor.a." + getMode(stack)));
	//	}

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

	/* IMPLEMENTABLES */

	/* IToolWrench */
	public boolean canWrench(EntityPlayer player, EnumHand hand, ItemStack wrench, RayTraceResult rayTrace) {

		ItemStack stack = player.getHeldItemMainhand();
		return true;
	}

	public void wrenchUsed(EntityPlayer player, EnumHand hand, ItemStack wrench, RayTraceResult rayTrace) {

	}

	/* IInitializer */
	@Override
	public boolean initialize() {

		wrenchBasic = addItem(0, "wrench0");
		//		wrenchHardened = addItem(1, "wrench1");
		//		wrenchReinforced = addItem(2, "wrench2", EnumRarity.UNCOMMON);
		//		wrenchSignalum = addItem(3, "wrench3", EnumRarity.UNCOMMON);
		//		wrenchResonant = addItem(4, "wrench4", EnumRarity.RARE);

		ThermalFoundation.proxy.addIModelRegister(this);

		return true;
	}

	@Override
	public boolean register() {

		// @formatter:off

		addShapedRecipe(wrenchBasic,
				"I I",
				" T ",
				" I ",
				'I', "ingotIron",
				'T', "ingotTin"
		);
//		addRecipe(new RecipeUpgrade(wrenchHardened,
//			"Y Y",
//				"IXI",
//				" Y ",
//				'I', "ingotInvar",
//				'X', wrenchBasic,
//				'Y', "nuggetTin"
//		));
//		addRecipe(new RecipeUpgrade(wrenchReinforced,
//				"Y Y",
//				"IXI",
//				" Y ",
//				'I', "ingotElectrum",
//				'X', wrenchHardened,
//				'Y', "nuggetInvar"
//		));
//		addRecipe(new RecipeUpgrade(wrenchSignalum,
//				"Y Y",
//				"IXI",
//				" Y ",
//				'I', "ingotSignalum",
//				'X', wrenchReinforced,
//				'Y', "nuggetElectrum"
//		));
//		addRecipe(new RecipeUpgrade(wrenchResonant,
//				"Y Y",
//				"IXI",
//				" Y ",
//				'I', "ingotEnderium",
//				'X', wrenchSignalum,
//				'Y', "nuggetSignalum"
//		));

		// @formatter:on

		return true;
	}

	/* REFERENCES */
	public static ItemStack wrenchBasic;
	public static ItemStack wrenchHardened;
	public static ItemStack wrenchReinforced;
	public static ItemStack wrenchSignalum;
	public static ItemStack wrenchResonant;

}
