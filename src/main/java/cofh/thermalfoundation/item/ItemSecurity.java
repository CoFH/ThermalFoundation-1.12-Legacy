package cofh.thermalfoundation.item;

import static cofh.lib.util.helpers.ItemHelper.*;

import cofh.api.core.IInitializer;
import cofh.api.core.IModelRegister;
import cofh.core.item.ItemCoFHBase;
import cofh.core.util.StateMapper;
import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalfoundation.ThermalFoundation;

import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSecurity extends ItemCoFHBase implements IInitializer, IModelRegister {

	public ItemSecurity() {

		super("thermalfoundation");

		setUnlocalizedName("tool");
		setCreativeTab(ThermalFoundation.tabCommon);

		setHasSubtypes(true);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {

	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

		return EnumActionResult.PASS;
	}

	@Override
	public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

		boolean ret = false;

		switch (Type.values()[ItemHelper.getItemDamage(stack)]) {
		case LOCK:
			//ret = doLockUseFirst(stack, player, world, pos, side);
			break;
		default:
			break;
		}
		return EnumActionResult.PASS;
	}

	private boolean doLockUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side) {

		//TODO figure out what is supposed to go here
		return false;
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

	/* IInitializer */
	@Override
	public boolean preInit() {

		lock = addItem(Type.LOCK.ordinal(), "lock");

		GameRegistry.register(this.setRegistryName(new ResourceLocation(ThermalFoundation.modId, "security")));

		return true;
	}

	@Override
	public boolean initialize() {

		return true;
	}

	@Override
	public boolean postInit() {

		addRecipe(ShapedRecipe(lock, new Object[] { " S ", "SBS", "SSS", 'B', "ingotBronze", 'S', "nuggetSignalum" }));

		return true;
	}

	/* REFERENCES */
	public static ItemStack lock;

	/* TYPE */
	enum Type {
		LOCK;
	}

}
