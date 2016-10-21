package cofh.thermalfoundation.plugins.tconstruct;

import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.item.TFItems;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import static codechicken.lib.util.ItemUtils.copyStack;

public class TConstructPlugin {

    public static void preInit() {

    }

    public static void initialize() {

        String comment = "Only change this if you absolutely know what you are doing.";
        String category2 = "Plugins.TConstruct.Material.";
        String category;
        int id;

        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("FluidName", "pyrotheum");
        tag.setInteger("Temperature", 4000);
        tag.setInteger("Duration", 240);
        FMLInterModComms.sendMessage("TConstruct", "addSmelteryFuel", tag);

		/* SILVER */
        category = category2 + "Silver";
        id = ThermalFoundation.config.get(category, "Id", 1023, comment).getInt();
        tag = new NBTTagCompound();
        tag.setInteger("Id", id);
        tag.setString("Name", "Silver");
        tag.setString("localizationString", "material.thermalfoundation.silver");
        tag.setInteger("Durability", ThermalFoundation.config.get(category, "Durability", 80).getInt());
        tag.setInteger("MiningSpeed", ThermalFoundation.config.get(category, "MiningSpeed", 1200).getInt());
        tag.setInteger("HarvestLevel", ThermalFoundation.config.get(category, "HarvestLevel", 2).getInt());
        tag.setInteger("Attack", ThermalFoundation.config.get(category, "Attack", 3).getInt());
        tag.setFloat("HandleModifier", 1.3f);
        tag.setFloat("Bow_ProjectileSpeed", 4.2f);
        tag.setInteger("Bow_DrawSpeed", 40);
        tag.setFloat("Projectile_Mass", 2.5f);
        tag.setFloat("Projectile_Fragility", 0.7f);
        tag.setString("Style", TextFormatting.AQUA.toString());
        tag.setInteger("Color", 0xFFD9EEEB);
        FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);

        tag = new NBTTagCompound();
        tag.setString("FluidName", "silver.molten");
        tag.setInteger("MaterialId", id);
        FMLInterModComms.sendMessage("TConstruct", "addPartCastingMaterial", tag);

        tag = new NBTTagCompound();
        tag.setInteger("MaterialId", id);
        tag.setTag("Item", copyStack(TFItems.ingotSilver, 1).writeToNBT(new NBTTagCompound()));
        // tag.setTag("Shard", );
        tag.setInteger("Value", 2);
        FMLInterModComms.sendMessage("TConstruct", "addMaterialItem", tag);

		/* LEAD */
        category = category2 + "Lead";
        id = ThermalFoundation.config.get(category, "Id", 1022, comment).getInt();
        tag = new NBTTagCompound();
        tag.setInteger("Id", id);
        tag.setString("Name", "Lead");
        tag.setString("localizationString", "material.thermalfoundation.lead");
        tag.setInteger("Durability", ThermalFoundation.config.get(category, "Durability", 250).getInt());
        tag.setInteger("MiningSpeed", ThermalFoundation.config.get(category, "MiningSpeed", 900).getInt());
        tag.setInteger("HarvestLevel", ThermalFoundation.config.get(category, "HarvestLevel", 1).getInt());
        tag.setInteger("Attack", ThermalFoundation.config.get(category, "Attack", 2).getInt());
        tag.setFloat("HandleModifier", 1.1f);
        tag.setInteger("Stonebound", 1);
        tag.setFloat("Bow_ProjectileSpeed", 2.2f);
        tag.setInteger("Bow_DrawSpeed", 30);
        tag.setFloat("Projectile_Mass", 6f);
        tag.setFloat("Projectile_Fragility", 0.9f);
        tag.setString("Style", TextFormatting.DARK_PURPLE.toString());
        tag.setInteger("Color", 0xFF7380A7);
        FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);

        tag = new NBTTagCompound();
        tag.setString("FluidName", "lead.molten");
        tag.setInteger("MaterialId", id);
        FMLInterModComms.sendMessage("TConstruct", "addPartCastingMaterial", tag);

        tag = new NBTTagCompound();
        tag.setInteger("MaterialId", id);
        tag.setTag("Item", copyStack(TFItems.ingotLead, 1).writeToNBT(new NBTTagCompound()));
        // tag.setTag("Shard", );
        tag.setInteger("Value", 2);
        FMLInterModComms.sendMessage("TConstruct", "addMaterialItem", tag);

		/* NICKEL */
        category = category2 + "Nickel";
        id = ThermalFoundation.config.get(category, "Id", 1021, comment).getInt();
        tag = new NBTTagCompound();
        tag.setInteger("Id", id);
        tag.setString("Name", "Nickel");
        tag.setString("localizationString", "material.thermalfoundation.nickel");
        tag.setInteger("Durability", ThermalFoundation.config.get(category, "Durability", 750).getInt());
        tag.setInteger("MiningSpeed", ThermalFoundation.config.get(category, "MiningSpeed", 1100).getInt());
        tag.setInteger("HarvestLevel", ThermalFoundation.config.get(category, "HarvestLevel", 2).getInt());
        tag.setInteger("Attack", ThermalFoundation.config.get(category, "Attack", 2).getInt());
        tag.setFloat("HandleModifier", 1.35f);
        tag.setInteger("Reinforced", 1);
        tag.setFloat("Bow_ProjectileSpeed", 4.6f);
        tag.setInteger("Bow_DrawSpeed", 50);
        tag.setFloat("Projectile_Mass", 3f);
        tag.setFloat("Projectile_Fragility", 0.6f);
        tag.setString("Style", TextFormatting.YELLOW.toString());
        tag.setInteger("Color", 0xFFFFFFDE);
        FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);

        tag = new NBTTagCompound();
        tag.setString("FluidName", "nickel.molten");
        tag.setInteger("MaterialId", id);
        FMLInterModComms.sendMessage("TConstruct", "addPartCastingMaterial", tag);

        tag = new NBTTagCompound();
        tag.setInteger("MaterialId", id);
        tag.setTag("Item", copyStack(TFItems.ingotNickel, 1).writeToNBT(new NBTTagCompound()));
        // tag.setTag("Shard", );
        tag.setInteger("Value", 2);
        FMLInterModComms.sendMessage("TConstruct", "addMaterialItem", tag);

		/* PLATINUM */
        category = category2 + "Platinum";
        id = ThermalFoundation.config.get(category, "Id", 1024, comment).getInt();
        tag = new NBTTagCompound();
        tag.setInteger("Id", id);
        tag.setString("Name", "Platinum");
        tag.setString("localizationString", "material.thermalfoundation.platinum");
        tag.setInteger("Durability", ThermalFoundation.config.get(category, "Durability", 1050).getInt());
        tag.setInteger("MiningSpeed", ThermalFoundation.config.get(category, "MiningSpeed", 1400).getInt());
        tag.setInteger("HarvestLevel", ThermalFoundation.config.get(category, "HarvestLevel", 4).getInt());
        tag.setInteger("Attack", ThermalFoundation.config.get(category, "Attack", 5).getInt());
        tag.setFloat("HandleModifier", 1.5f);
        tag.setInteger("Reinforced", 2);
        tag.setFloat("Bow_ProjectileSpeed", 5.7f);
        tag.setInteger("Bow_DrawSpeed", 60);
        tag.setFloat("Projectile_Mass", 5.4f);
        tag.setFloat("Projectile_Fragility", 0.4f);
        tag.setString("Style", TextFormatting.AQUA.toString());
        tag.setInteger("Color", 0xFF6FE1ED);
        FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);

        tag = new NBTTagCompound();
        tag.setString("FluidName", "platinum.molten");
        tag.setInteger("MaterialId", id);
        FMLInterModComms.sendMessage("TConstruct", "addPartCastingMaterial", tag);

        tag = new NBTTagCompound();
        tag.setInteger("MaterialId", id);
        tag.setTag("Item", copyStack(TFItems.ingotPlatinum, 1).writeToNBT(new NBTTagCompound()));
        // tag.setTag("Shard", );
        tag.setInteger("Value", 2);
        FMLInterModComms.sendMessage("TConstruct", "addMaterialItem", tag);

		/* INVAR */
        category = category2 + "Invar";
        id = ThermalFoundation.config.get(category, "Id", 1020, comment).getInt();
        tag = new NBTTagCompound();
        tag.setInteger("Id", id);
        tag.setString("Name", "Invar");
        tag.setString("localizationString", "material.thermalfoundation.invar");
        tag.setInteger("Durability", ThermalFoundation.config.get(category, "Durability", 450).getInt());
        tag.setInteger("MiningSpeed", ThermalFoundation.config.get(category, "MiningSpeed", 700).getInt());
        tag.setInteger("HarvestLevel", ThermalFoundation.config.get(category, "HarvestLevel", 2).getInt());
        tag.setInteger("Attack", ThermalFoundation.config.get(category, "Attack", 2).getInt());
        tag.setFloat("HandleModifier", 1.4f);
        tag.setInteger("Reinforced", 1);
        tag.setFloat("Bow_ProjectileSpeed", 4.7f);
        tag.setInteger("Bow_DrawSpeed", 47);
        tag.setFloat("Projectile_Mass", 3f);
        tag.setFloat("Projectile_Fragility", 0.7f);
        tag.setString("Style", TextFormatting.GRAY.toString());
        tag.setInteger("Color", 0xFFDCE1DE);
        FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);

        tag = new NBTTagCompound();
        tag.setString("FluidName", "invar.molten");
        tag.setInteger("MaterialId", id);
        FMLInterModComms.sendMessage("TConstruct", "addPartCastingMaterial", tag);

        tag = new NBTTagCompound();
        tag.setInteger("MaterialId", id);
        tag.setTag("Item", copyStack(TFItems.ingotInvar, 1).writeToNBT(new NBTTagCompound()));
        // tag.setTag("Shard", );
        tag.setInteger("Value", 2);
        FMLInterModComms.sendMessage("TConstruct", "addMaterialItem", tag);

		/* ELECTRUM */
        category = category2 + "Electrum";
        id = ThermalFoundation.config.get(category, "Id", 1025, comment).getInt();
        tag = new NBTTagCompound();
        tag.setInteger("Id", id);
        tag.setString("Name", "Electrum");
        tag.setString("localizationString", "material.thermalfoundation.electrum");
        tag.setInteger("Durability", ThermalFoundation.config.get(category, "Durability", 90).getInt());
        tag.setInteger("MiningSpeed", ThermalFoundation.config.get(category, "MiningSpeed", 1700).getInt());
        tag.setInteger("HarvestLevel", ThermalFoundation.config.get(category, "HarvestLevel", 1).getInt());
        tag.setInteger("Attack", ThermalFoundation.config.get(category, "Attack", 2).getInt());
        tag.setInteger("Durability", 90);
        tag.setInteger("MiningSpeed", 1700);
        tag.setInteger("HarvestLevel", 1);
        tag.setInteger("Attack", 2);
        tag.setFloat("HandleModifier", 1.4f);
        tag.setInteger("Reinforced", 1);
        tag.setInteger("Stonebound", 1);
        tag.setFloat("Bow_ProjectileSpeed", 3.7f);
        tag.setInteger("Bow_DrawSpeed", 37);
        tag.setFloat("Projectile_Mass", 5f);
        tag.setFloat("Projectile_Fragility", 0.7f);
        tag.setString("Style", TextFormatting.YELLOW.toString());
        tag.setInteger("Color", 0xFFEEE155);
        FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);

        tag = new NBTTagCompound();
        tag.setString("FluidName", "electrum.molten");
        tag.setInteger("MaterialId", id);
        FMLInterModComms.sendMessage("TConstruct", "addPartCastingMaterial", tag);

        tag = new NBTTagCompound();
        tag.setInteger("MaterialId", id);
        tag.setTag("Item", copyStack(TFItems.ingotElectrum, 1).writeToNBT(new NBTTagCompound()));
        // tag.setTag("Shard", );
        tag.setInteger("Value", 2);
        FMLInterModComms.sendMessage("TConstruct", "addMaterialItem", tag);

    }

    public static void postInit() {

    }

    public static void loadComplete() {

        ThermalFoundation.log.info("Thermal Foundation: Tinker's Construct Plugin Enabled.");
    }

}
