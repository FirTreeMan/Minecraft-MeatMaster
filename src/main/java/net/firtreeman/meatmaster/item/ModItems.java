package net.firtreeman.meatmaster.item;

import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.item.custom.*;
import net.firtreeman.meatmaster.util.SPICE_TYPES;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MeatMaster.MOD_ID);

    public static final RegistryObject<Item> CREEPER_MEAT = ITEMS.register("creeper_meat", makeFoodItem(ModFoods.CREEPER_MEAT));
    public static final RegistryObject<Item> COOKED_CREEPER_MEAT = ITEMS.register("cooked_creeper_meat", makeFoodItem(ModFoods.COOKED_CREEPER_MEAT, VolatileMeatItem.class));
    public static final RegistryObject<Item> DOG_LIVER = ITEMS.register("dog_liver", makeFoodItem(ModFoods.DOG_LIVER));
    public static final RegistryObject<Item> COOKED_DOG_LIVER = ITEMS.register("cooked_dog_liver", makeFoodItem(ModFoods.COOKED_DOG_LIVER));
    public static final RegistryObject<Item> ENDERMEAT = ITEMS.register("endermeat", makeFoodItem(ModFoods.ENDERMEAT, LongTeleportItem.class));
    public static final RegistryObject<Item> HORSE_MEAT = ITEMS.register("horse_meat", makeFoodItem(ModFoods.HORSE_MEAT));
    public static final RegistryObject<Item> COOKED_HORSE_MEAT = ITEMS.register("cooked_horse_meat", makeFoodItem(ModFoods.COOKED_HORSE_MEAT));
    public static final RegistryObject<Item> COOKED_BLAZE_MEAT = ITEMS.register("cooked_blaze_meat", makeFoodItem(ModFoods.COOKED_BLAZE_MEAT, OnFireMeatItem.class));
    public static final RegistryObject<Item> CAT_MEAT = ITEMS.register("cat_meat", makeFoodItem(ModFoods.CAT_MEAT));
    public static final RegistryObject<Item> COOKED_CAT_MEAT = ITEMS.register("cooked_cat_meat", makeFoodItem(ModFoods.COOKED_CAT_MEAT));
    public static final RegistryObject<Item> SQUID_MEAT = ITEMS.register("squid_meat", makeFoodItem(ModFoods.SQUID_MEAT));
    public static final RegistryObject<Item> COOKED_SQUID_MEAT = ITEMS.register("cooked_squid_meat", makeFoodItem(ModFoods.COOKED_SQUID_MEAT));
    public static final RegistryObject<Item> BAT_WINGS = ITEMS.register("bat_wings", makeFoodItem(ModFoods.BAT_WINGS));
    public static final RegistryObject<Item> COOKED_BAT_WINGS = ITEMS.register("cooked_bat_wings", makeFoodItem(ModFoods.COOKED_BAT_WINGS));
    public static final RegistryObject<Item> FROG_LEGS = ITEMS.register("frog_legs", makeFoodItem(ModFoods.FROG_LEGS));
    public static final RegistryObject<Item> COOKED_FROG_LEGS = ITEMS.register("cooked_frog_legs", makeFoodItem(ModFoods.COOKED_FROG_LEGS));
    public static final RegistryObject<Item> SNIFFER_TOES = ITEMS.register("sniffer_toes", makeFoodItem(ModFoods.SNIFFER_TOES));
    public static final RegistryObject<Item> COOKED_SNIFFER_TOES = ITEMS.register("cooked_sniffer_toes", makeFoodItem(ModFoods.COOKED_SNIFFER_TOES));
    public static final RegistryObject<Item> MOLDY_SNIFFER_TOES = ITEMS.register("moldy_sniffer_toes", makeFoodItem(ModFoods.MOLDY_SNIFFER_TOES));
    public static final RegistryObject<Item> PANDA_BALLS = ITEMS.register("panda_balls", makeFoodItem(ModFoods.PANDA_BALLS));
    public static final RegistryObject<Item> COOKED_PANDA_BALLS = ITEMS.register("cooked_panda_balls", makeFoodItem(ModFoods.COOKED_PANDA_BALLS));
    public static final RegistryObject<Item> GUARDIAN_HEART = ITEMS.register("guardian_heart", makeFoodItem(ModFoods.GUARDIAN_HEART));
    public static final RegistryObject<Item> COOKED_GUARDIAN_HEART = ITEMS.register("cooked_guardian_heart", makeFoodItem(ModFoods.COOKED_GUARDIAN_HEART));
    public static final RegistryObject<Item> ELDER_GUARDIAN_HEART = ITEMS.register("elder_guardian_heart", makeFoodItem(ModFoods.ELDER_GUARDIAN_HEART));
    public static final RegistryObject<Item> COOKED_ELDER_GUARDIAN_HEART = ITEMS.register("cooked_elder_guardian_heart", makeFoodItem(ModFoods.COOKED_ELDER_GUARDIAN_HEART));

    public static final RegistryObject<Item> SAUSAGE = ITEMS.register("sausage", makeFoodItem(ModFoods.SAUSAGE));
    public static final RegistryObject<Item> COOKED_SAUSAGE = ITEMS.register("cooked_sausage", makeFoodItem(ModFoods.COOKED_SAUSAGE));

    public static final RegistryObject<Item> TABLE_SALT = ITEMS.register("table_salt", makeSpiceItem(SPICE_TYPES.SALTY));
    public static final RegistryObject<Item> GUNPOWDER_CLUMPS = ITEMS.register("gunpowder_clumps", makeSpiceItem(SPICE_TYPES.EXPLOSIVE));
    public static final RegistryObject<Item> ENDER_SPICE = ITEMS.register("ender_spice", makeSpiceItem(SPICE_TYPES.TELEPORTING));
    public static final RegistryObject<Item> LIVER_BITS = ITEMS.register("liver_bits", makeSpiceItem(SPICE_TYPES.POISONED));
    public static final RegistryObject<Item> BLAZE_PEPPER = ITEMS.register("blaze_pepper", makeSpiceItem(SPICE_TYPES.FLAMMABLE));
    public static final RegistryObject<Item> BITTER_CRUMBS = ITEMS.register("bitter_crumbs", makeSpiceItem(SPICE_TYPES.BITTER));
    public static final RegistryObject<Item> MEAT_RESIDUE = ITEMS.register("meat_residue", makeSpiceItem(SPICE_TYPES.FILLING));

    public static final RegistryObject<Item> SYRINGE_DART = ITEMS.register("syringe_dart", () -> new HormoneArrowItem(new Item.Properties()));
    public static final RegistryObject<Item> HORMONE_BASE = ITEMS.register("hormone_base", () -> new HormoneBaseItem(new Item.Properties()));

    public static final RegistryObject<Item> KEBAB = ITEMS.register("kebab", () -> new ContainerFoodItem(new Item.Properties().food(ModFoods.KEBAB), Items.END_ROD));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static Supplier<Item> makeFoodItem(FoodProperties food) {
        return makeFoodItem(food, Item.class);
    }

    public static <T extends Item> Supplier<T> makeFoodItem(FoodProperties food, Class<T> clazz) {
        return () -> {
            try {
                // equivalent to:
                // return new ItemChild(new Item.Properties().food(food));
                return clazz.getConstructor(new Class[]{Item.Properties.class}).newInstance(new Item.Properties().food(food));
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static Supplier<SpiceItem> makeSpiceItem(SPICE_TYPES spiceType) {
        return () -> new SpiceItem(new Item.Properties(), spiceType);
    }
}
