package net.firtreeman.meatmaster.item;

import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.item.custom.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MeatMaster.MOD_ID);

    public static final RegistryObject<Item> CREEPER_MEAT = ITEMS.register("creeper_meat", () -> new Item(new Item.Properties().food(ModFoods.CREEPER_MEAT)));
    public static final RegistryObject<Item> COOKED_CREEPER_MEAT = ITEMS.register("cooked_creeper_meat", () -> new VolatileMeatItem(new Item.Properties().food(ModFoods.COOKED_CREEPER_MEAT)));
    public static final RegistryObject<Item> DOG_LIVER = ITEMS.register("dog_liver", () -> new Item(new Item.Properties().food(ModFoods.DOG_LIVER)));
    public static final RegistryObject<Item> COOKED_DOG_LIVER = ITEMS.register("cooked_dog_liver", () -> new Item(new Item.Properties().food(ModFoods.COOKED_DOG_LIVER)));
    public static final RegistryObject<Item> ENDERMEAT = ITEMS.register("endermeat", () -> new LongTeleportItem(new Item.Properties().food(ModFoods.ENDERMEAT)));
    public static final RegistryObject<Item> HORSE_MEAT = ITEMS.register("horse_meat", () -> new Item(new Item.Properties().food(ModFoods.HORSE_MEAT)));
    public static final RegistryObject<Item> COOKED_HORSE_MEAT = ITEMS.register("cooked_horse_meat", () -> new Item(new Item.Properties().food(ModFoods.COOKED_HORSE_MEAT)));
    public static final RegistryObject<Item> COOKED_BLAZE_MEAT = ITEMS.register("cooked_blaze_meat", () -> new OnFireMeatItem(new Item.Properties().food(ModFoods.COOKED_BLAZE_MEAT)));
    public static final RegistryObject<Item> CAT_MEAT = ITEMS.register("cat_meat", () -> new Item(new Item.Properties().food(ModFoods.CAT_MEAT)));
    public static final RegistryObject<Item> COOKED_CAT_MEAT = ITEMS.register("cooked_cat_meat", () -> new Item(new Item.Properties().food(ModFoods.COOKED_CAT_MEAT)));
    public static final RegistryObject<Item> SQUID_MEAT = ITEMS.register("squid_meat", () -> new Item(new Item.Properties().food(ModFoods.SQUID_MEAT)));
    public static final RegistryObject<Item> COOKED_SQUID_MEAT = ITEMS.register("cooked_squid_meat", () -> new Item(new Item.Properties().food(ModFoods.COOKED_SQUID_MEAT)));
    public static final RegistryObject<Item> BAT_WINGS = ITEMS.register("bat_wings", () -> new Item(new Item.Properties().food(ModFoods.BAT_WINGS)));
    public static final RegistryObject<Item> COOKED_BAT_WINGS = ITEMS.register("cooked_bat_wings", () -> new Item(new Item.Properties().food(ModFoods.COOKED_BAT_WINGS)));
    public static final RegistryObject<Item> FROG_LEGS = ITEMS.register("frog_legs", () -> new Item(new Item.Properties().food(ModFoods.FROG_LEGS)));
    public static final RegistryObject<Item> COOKED_FROG_LEGS = ITEMS.register("cooked_frog_legs", () -> new Item(new Item.Properties().food(ModFoods.COOKED_FROG_LEGS)));
    public static final RegistryObject<Item> SNIFFER_TOES = ITEMS.register("sniffer_toes", () -> new Item(new Item.Properties().food(ModFoods.SNIFFER_TOES)));
    public static final RegistryObject<Item> COOKED_SNIFFER_TOES = ITEMS.register("cooked_sniffer_toes", () -> new Item(new Item.Properties().food(ModFoods.COOKED_SNIFFER_TOES)));
    public static final RegistryObject<Item> MOLDY_SNIFFER_TOES = ITEMS.register("moldy_sniffer_toes", () -> new Item(new Item.Properties().food(ModFoods.MOLDY_SNIFFER_TOES)));
    public static final RegistryObject<Item> PANDA_BALLS = ITEMS.register("panda_balls", () -> new Item(new Item.Properties().food(ModFoods.PANDA_BALLS)));
    public static final RegistryObject<Item> COOKED_PANDA_BALLS = ITEMS.register("cooked_panda_balls", () -> new Item(new Item.Properties().food(ModFoods.COOKED_PANDA_BALLS)));
    public static final RegistryObject<Item> GUARDIAN_HEART = ITEMS.register("guardian_heart", () -> new Item(new Item.Properties().food(ModFoods.GUARDIAN_HEART)));
    public static final RegistryObject<Item> COOKED_GUARDIAN_HEART = ITEMS.register("cooked_guardian_heart", () -> new Item(new Item.Properties().food(ModFoods.COOKED_GUARDIAN_HEART)));
    public static final RegistryObject<Item> ELDER_GUARDIAN_HEART = ITEMS.register("elder_guardian_heart", () -> new Item(new Item.Properties().food(ModFoods.ELDER_GUARDIAN_HEART)));
    public static final RegistryObject<Item> COOKED_ELDER_GUARDIAN_HEART = ITEMS.register("cooked_elder_guardian_heart", () -> new Item(new Item.Properties().food(ModFoods.COOKED_ELDER_GUARDIAN_HEART)));

    public static final RegistryObject<Item> SAUSAGE = ITEMS.register("sausage", () -> new Item(new Item.Properties().food(ModFoods.SAUSAGE)));
    public static final RegistryObject<Item> COOKED_SAUSAGE = ITEMS.register("cooked_sausage", () -> new Item(new Item.Properties().food(ModFoods.COOKED_SAUSAGE)));

    public static final RegistryObject<Item> TABLE_SALT = ITEMS.register("table_salt", () -> new SpiceItem(new Item.Properties(), SpiceItem.SPICE_NAMES.SALTY));
    public static final RegistryObject<Item> GUNPOWDER_CLUMPS = ITEMS.register("gunpowder_clumps", () -> new SpiceItem(new Item.Properties(), SpiceItem.SPICE_NAMES.EXPLOSIVE));
    public static final RegistryObject<Item> ENDER_SPICE = ITEMS.register("ender_spice", () -> new SpiceItem(new Item.Properties(), SpiceItem.SPICE_NAMES.TELEPORTING));
    public static final RegistryObject<Item> LIVER_BITS = ITEMS.register("liver_bits", () -> new SpiceItem(new Item.Properties(), SpiceItem.SPICE_NAMES.POISONED));
    public static final RegistryObject<Item> BLAZE_PEPPER = ITEMS.register("blaze_pepper", () -> new SpiceItem(new Item.Properties(), SpiceItem.SPICE_NAMES.FLAMMABLE));
    public static final RegistryObject<Item> BITTER_CRUMBS = ITEMS.register("bitter_crumbs", () -> new SpiceItem(new Item.Properties(), SpiceItem.SPICE_NAMES.BITTER));
    public static final RegistryObject<Item> MEAT_RESIDUE = ITEMS.register("meat_residue", () -> new SpiceItem(new Item.Properties(), SpiceItem.SPICE_NAMES.FILLING));

    public static final RegistryObject<Item> SYRINGE_DART = ITEMS.register("syringe_dart", () -> new HormoneArrowItem(new Item.Properties()));
    public static final RegistryObject<Item> HORMONE_BASE = ITEMS.register("hormone_base", () -> new HormoneBaseItem(new Item.Properties()));

    public static final RegistryObject<Item> KEBAB = ITEMS.register("kebab", () -> new ContainerFoodItem(new Item.Properties().food(ModFoods.KEBAB), Items.END_ROD));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
