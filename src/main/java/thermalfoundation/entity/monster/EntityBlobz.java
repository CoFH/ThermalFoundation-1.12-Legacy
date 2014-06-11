package thermalfoundation.entity.monster;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.IMob;
import net.minecraft.world.World;

public class EntityBlobz extends EntityLiving implements IMob {

	public static int entityId = -1;

	public float squishAmount;
	public float squishFactor;
	public float prevSquishFactor;

	protected int slimeJumpDelay;

	public static void initialize() {

	}

	public EntityBlobz(World world) {

		super(world);
	}

}
