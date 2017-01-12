package cofh.thermalfoundation.init;

import net.minecraft.util.DamageSource;

/**
 * Created by covers1624 on 17/10/2016.
 */
public class ModEntities {

	public static final DamageSource pyrotheumDamage = new DamageSource("pyrotheum").setDamageBypassesArmor().setFireDamage();
	public static final DamageSource cryotheumDamage = new DamageSource("cryotheum").setDamageBypassesArmor();
	public static final DamageSource petrotheumDamage = new DamageSource("petrotheum").setDamageBypassesArmor();
	public static final DamageSource manaDamage = new DamageSource("mana").setDamageBypassesArmor().setMagicDamage();
	public static final DamageSource fluxDamage = new DamageSource("flux").setDamageBypassesArmor();

}
