package net.firtreeman.meatmaster.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties CREEPER_MEAT = new FoodProperties.Builder().nutrition(4).saturationMod(1.0F).meat().build();
    public static final FoodProperties COOKED_CREEPER_MEAT = new FoodProperties.Builder().nutrition(6).saturationMod(8.0F).meat().build();
    public static final FoodProperties DOG_LIVER = new FoodProperties.Builder().nutrition(3).saturationMod(3.0F).meat().effect(() -> new MobEffectInstance(MobEffects.POISON, 400, 1), 1.0F).build();
    public static final FoodProperties COOKED_DOG_LIVER = new FoodProperties.Builder().nutrition(6).saturationMod(11.0F).meat().effect(() -> new MobEffectInstance(MobEffects.POISON, 300, 0), 0.75F).build();
    public static final FoodProperties ENDERMEAT = new FoodProperties.Builder().nutrition(10).saturationMod(10.0F).effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 400, 0), 1.0F).meat().build();
    public static final FoodProperties HORSE_MEAT = new FoodProperties.Builder().nutrition(3).saturationMod(1.5F).meat().build();
    public static final FoodProperties COOKED_HORSE_MEAT = new FoodProperties.Builder().nutrition(7).saturationMod(11.2F).meat().build();
    public static final FoodProperties COOKED_BLAZE_MEAT = new FoodProperties.Builder().nutrition(8).saturationMod(14.0F).meat().build();
    public static final FoodProperties CAT_MEAT = new FoodProperties.Builder().nutrition(4).saturationMod(5.5F).meat().fast().build();
    public static final FoodProperties COOKED_CAT_MEAT = new FoodProperties.Builder().nutrition(7).saturationMod(7.5F).meat().fast().build();
    public static final FoodProperties SQUID_MEAT = new FoodProperties.Builder().nutrition(2).saturationMod(1.5F).meat().fast().build();
    public static final FoodProperties COOKED_SQUID_MEAT = new FoodProperties.Builder().nutrition(4).saturationMod(4.5F).meat().fast().build();
    public static final FoodProperties BAT_WINGS = new FoodProperties.Builder().nutrition(1).saturationMod(0.5F).meat().build();
    public static final FoodProperties COOKED_BAT_WINGS = new FoodProperties.Builder().nutrition(3).saturationMod(1.0F).meat().fast().build();
    public static final FoodProperties FROG_LEGS = new FoodProperties.Builder().nutrition(2).saturationMod(1.0F).meat().effect(() -> new MobEffectInstance(MobEffects.JUMP, 400, 1), 1.0F).build();
    public static final FoodProperties COOKED_FROG_LEGS = new FoodProperties.Builder().nutrition(2).saturationMod(3.0F).meat().fast().build();
    public static final FoodProperties SNIFFER_TOES = new FoodProperties.Builder().nutrition(6).saturationMod(2.0F).meat().build();
    public static final FoodProperties COOKED_SNIFFER_TOES = new FoodProperties.Builder().nutrition(7).saturationMod(7.0F).meat().build();
    public static final FoodProperties MOLDY_SNIFFER_TOES = new FoodProperties.Builder().nutrition(8).saturationMod(10.0F).meat().effect(() -> new MobEffectInstance(MobEffects.POISON, 600, 1), 1.0F).build();
    public static final FoodProperties PANDA_BALLS = new FoodProperties.Builder().nutrition(2).saturationMod(2.0F).meat().build();
    public static final FoodProperties COOKED_PANDA_BALLS = new FoodProperties.Builder().nutrition(5).saturationMod(6.0F).meat().build();
    public static final FoodProperties GUARDIAN_HEART = new FoodProperties.Builder().nutrition(7).saturationMod(0.5F).meat().build();
    public static final FoodProperties COOKED_GUARDIAN_HEART = new FoodProperties.Builder().nutrition(10).saturationMod(3.0F).meat().build();
    public static final FoodProperties ELDER_GUARDIAN_HEART = new FoodProperties.Builder().nutrition(8).saturationMod(1.5F).meat().build();
    public static final FoodProperties COOKED_ELDER_GUARDIAN_HEART = new FoodProperties.Builder().nutrition(20).saturationMod(3.0F).meat().build();

    public static final FoodProperties SAUSAGE = new FoodProperties.Builder().nutrition(2).saturationMod(1.0F).meat().fast().build();
    public static final FoodProperties COOKED_SAUSAGE = new FoodProperties.Builder().nutrition(3).saturationMod(1.5F).meat().fast().build();

    public static final FoodProperties KEBAB = new FoodProperties.Builder().nutrition(0).saturationMod(0F).meat().build();
}
