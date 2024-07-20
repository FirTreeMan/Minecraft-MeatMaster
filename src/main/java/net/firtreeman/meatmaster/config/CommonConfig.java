package net.firtreeman.meatmaster.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> DISPENSABLE_SNIFFER_EGG;

    static {
        BUILDER.push("Common Config for Meat Master");

        DISPENSABLE_SNIFFER_EGG = BUILDER.comment("If true, Sniffer Eggs can be placed by dispensers.")
                .define("Dispensable Sniffer Egg", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
