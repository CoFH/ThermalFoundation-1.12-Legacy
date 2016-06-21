package cofh.thermalfoundation.item;

import static cofh.lib.util.helpers.ItemHelper.*;

import cofh.api.block.IDismantleable;
import cofh.api.core.IInitializer;
import cofh.api.item.IToolHammer;
import cofh.asm.relauncher.Implementable;
import cofh.core.item.ItemToolBase;
import cofh.core.util.StateMapper;
import cofh.lib.util.helpers.ServerHelper;
import cofh.thermalfoundation.ThermalFoundation;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Implementable("buildcraft.api.tools.IToolWrench")
public class ItemWrench extends ItemToolBase implements IInitializer, IToolHammer {

	public ItemWrench() {

		super("thermalfoundation");

		setUnlocalizedName("tool", "wrench");
		setCreativeTab(ThermalFoundation.tabCommon);

		setHasSubtypes(true);
		setHarvestLevel("wrench", 1);
	}

	@Override
	public boolean doesSneakBypassUse(World world, BlockPos pos, EntityPlayer player) {

		return true;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {

		return player.canPlayerEdit(pos.offset(side), side, stack);
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {

		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		if (block == null) {
			return false;
		}
		PlayerInteractEvent event = new PlayerInteractEvent(player, Action.RIGHT_CLICK_BLOCK, pos, side, world, new Vec3(hitX, hitY, hitZ));
		if (MinecraftForge.EVENT_BUS.post(event) || event.getResult() == Result.DENY || event.useBlock == Result.DENY) {
			return false;
		}
		if (ServerHelper.isServerWorld(world) && player.isSneaking() && block instanceof IDismantleable
				&& ((IDismantleable) block).canDismantle(world, pos, state, player)) {
			((IDismantleable) block).dismantleBlock(world, pos, state, player, false);
			return true;
		}

		if (!player.isSneaking() && block.rotateBlock(world, pos, side)) {
			player.swingItem();
			return ServerHelper.isServerWorld(world);
		}
		return false;
	}

	@Override
	public Multimap getItemAttributeModifiers() {

		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Tool modifier", 1, 0));
		return multimap;
	}

	/* IModelRegister */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {

		StateMapper mapper = new StateMapper(modName, "tool", name);
		ModelBakery.registerItemVariants(this);
		ModelLoader.setCustomMeshDefinition(this, mapper);

		for (Map.Entry<Integer, ItemEntry> entry : itemMap.entrySet()) {
			ModelLoader.setCustomModelResourceLocation(this, entry.getKey(), new ModelResourceLocation(modName + ":" + "tool", entry.getValue().name));
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

}
