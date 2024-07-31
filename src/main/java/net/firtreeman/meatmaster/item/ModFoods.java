package net.firtreeman.meatmaster.item;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

import java.util.function.Supplier;

public class ModFoods {
    public static final FoodProperties CREEPER_MEAT = makeMeat(4, 1.0F).build();
    public static final FoodProperties COOKED_CREEPER_MEAT = makeMeat(6, 8.0F).build();
    public static final FoodProperties DOG_LIVER = makeMeat(3, 3.0F).effect(makeMobEffect(MobEffects.POISON, 400, 1), 1.0F).build();
    public static final FoodProperties COOKED_DOG_LIVER = makeMeat(6, 11.0F).effect(makeMobEffect(MobEffects.POISON, 300, 0), 0.75F).build();
    public static final FoodProperties ENDERMEAT = makeMeat(10, 10.0F).effect(makeMobEffect(MobEffects.SLOW_FALLING, 400, 0), 1.0F).build();
    public static final FoodProperties HORSE_MEAT = makeMeat(3, 1.5F).build();
    public static final FoodProperties COOKED_HORSE_MEAT = makeMeat(7, 11.2F).build();
    public static final FoodProperties COOKED_BLAZE_MEAT = makeMeat(8, 4.0F).build();
    public static final FoodProperties CAT_MEAT = makeMeat(4, 5.5F).fast().build();
    public static final FoodProperties COOKED_CAT_MEAT = makeMeat(7, 7.5F).fast().build();
    public static final FoodProperties SQUID_MEAT = makeMeat(2, 1.5F).fast().build();
    public static final FoodProperties COOKED_SQUID_MEAT = makeMeat(4, 4.5F).fast().build();
    public static final FoodProperties BAT_WINGS = makeMeat(1, 0.5F).build();
    public static final FoodProperties COOKED_BAT_WINGS = makeMeat(3, 1.0F).fast().build();
    public static final FoodProperties FROG_LEGS = makeMeat(2, 1.0F).effect(makeMobEffect(MobEffects.JUMP, 400, 1), 1.0F).build();
    public static final FoodProperties COOKED_FROG_LEGS = makeMeat(2, 3.0F).fast().build();
    public static final FoodProperties SNIFFER_TOES = makeMeat(6, 2.0F).build();
    public static final FoodProperties COOKED_SNIFFER_TOES = makeMeat(7, 7.0F).build();
    public static final FoodProperties MOLDY_SNIFFER_TOES = makeMeat(8, 10.0F).effect(makeMobEffect(MobEffects.POISON, 600, 1), 1.0F).build();
    public static final FoodProperties PANDA_BALLS = makeMeat(2, 2.0F).build();
    public static final FoodProperties COOKED_PANDA_BALLS = makeMeat(5, 6.0F).build();
    public static final FoodProperties GUARDIAN_HEART = makeMeat(7, 0.5F).build();
    public static final FoodProperties COOKED_GUARDIAN_HEART = makeMeat(10, 3.0F).build();
    public static final FoodProperties ELDER_GUARDIAN_HEART = makeMeat(8, 1.5F).effect(makeMobEffect(MobEffects.DIG_SLOWDOWN, 6000, 2), 1.0F).build();
    public static final FoodProperties COOKED_ELDER_GUARDIAN_HEART = makeMeat(20, 3.0F).effect(makeMobEffect(MobEffects.DIG_SPEED, 6000, 1), 1.0F).effect(makeMobEffect(MobEffects.MOVEMENT_SPEED, 6000, 1), 1.0F).effect(makeMobEffect(MobEffects.INVISIBILITY, 600, 0), 1.0F).effect(makeMobEffect(MobEffects.DAMAGE_BOOST, 600, 0), 1.0F).alwaysEat().build();

    public static final FoodProperties SAUSAGE = makeMeat(2, 1.0F).fast().build();
    public static final FoodProperties COOKED_SAUSAGE = makeMeat(3, 1.5F).fast().build();

    public static final FoodProperties KEBAB = makeMeat(0, 0F).build();

    public static FoodProperties.Builder makeMeat(int nutrition, float saturation) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturation).meat();
    }

    public static Supplier<MobEffectInstance> makeMobEffect(MobEffect mobEffect, int duration, int amplifier) {
        return () -> new MobEffectInstance(mobEffect, duration, amplifier);
    }
}
