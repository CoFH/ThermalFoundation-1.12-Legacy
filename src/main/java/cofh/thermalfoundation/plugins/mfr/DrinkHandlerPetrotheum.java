//package cofh.thermalfoundation.plugins.mfr;
//
//import cofh.lib.util.helpers.DamageHelper;
//
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.potion.Potion;
//import net.minecraft.potion.PotionEffect;
//import net.minecraft.util.DamageSource;
//
//import powercrystals.minefactoryreloaded.api.ILiquidDrinkHandler;
//
//public class DrinkHandlerPetrotheum implements ILiquidDrinkHandler {
//
//	public static DrinkHandlerPetrotheum instance = new DrinkHandlerPetrotheum();
//
//	@Override
//	public void onDrink(EntityLivingBase player) {
//
//		player.attackEntityFrom(new InternalPetrotheumDamage(), 10);
//		player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 300 * 20, 3));
//		player.addPotionEffect(new PotionEffect(Potion.resistance.id, 300 * 20, 2));
//	}
//
//	protected class InternalPetrotheumDamage extends DamageSource {
//
//		public InternalPetrotheumDamage() {
//
//			super(DamageHelper.petrotheum.damageType);
//			this.setDamageBypassesArmor();
//			this.setDifficultyScaled();
//		}
//	}
//
//}
