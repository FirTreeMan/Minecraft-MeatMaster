package net.firtreeman.meatmaster.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> LONG_TELEPORT_ITEM_DIST;
    public static final ForgeConfigSpec.ConfigValue<Integer> LONG_TELEPORT_SPICE_DIST;
    public static final ForgeConfigSpec.ConfigValue<Integer> HORMONE_RESEARCH_BLOCKS;

    static {
        BUILDER.push("Server Config for Meat Master");

        LONG_TELEPORT_ITEM_DIST = BUILDER.comment("How many blocks away eating Endermeat can teleport you.")
                .defineInRange("LongTeleportItem Distance", 500, 10, 10000);
        LONG_TELEPORT_SPICE_DIST = BUILDER.comment("How many blocks away eating food laced with Ender Spice can teleport you.")
                .defineInRange("EnderSpice Distance", 50, 1, 10000);
        HORMONE_RESEARCH_BLOCKS = BUILDER.comment("How many blocks it takes to research one Hormone Base.")
                .defineInRange("Hormone Research Blocks", 6912, 64, 64000);


        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
