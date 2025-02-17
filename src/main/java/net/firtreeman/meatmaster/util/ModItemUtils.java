package net.firtreeman.meatmaster.util;

import net.firtreeman.meatmaster.block.ModBlocks;
import net.firtreeman.meatmaster.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.Map;

public class ModItemUtils {
    public static final Map<Item, Item> COOKED_VARIANTS = Map.ofEntries(
            Map.entry(ModItems.CREEPER_MEAT.get(), ModItems.COOKED_CREEPER_MEAT.get()),
            Map.entry(ModItems.DOG_LIVER.get(), ModItems.COOKED_DOG_LIVER.get()),
            Map.entry(ModItems.HORSE_MEAT.get(), ModItems.COOKED_HORSE_MEAT.get()),
            Map.entry(ModItems.CAT_MEAT.get(), ModItems.COOKED_CAT_MEAT.get()),
            Map.entry(ModItems.SQUID_MEAT.get(), ModItems.COOKED_SQUID_MEAT.get()),
            Map.entry(ModItems.BAT_WINGS.get(), ModItems.COOKED_BAT_WINGS.get()),
            Map.entry(ModItems.FROG_LEGS.get(), ModItems.COOKED_FROG_LEGS.get()),
            Map.entry(ModItems.SNIFFER_TOES.get(), ModItems.COOKED_SNIFFER_TOES.get()),
            Map.entry(ModItems.PANDA_BALLS.get(), ModItems.COOKED_PANDA_BALLS.get()),
            Map.entry(ModItems.GUARDIAN_HEART.get(), ModItems.COOKED_GUARDIAN_HEART.get()),
            Map.entry(ModItems.ELDER_GUARDIAN_HEART.get(), ModItems.COOKED_ELDER_GUARDIAN_HEART.get())
    );
    public static final Map<Block, Block> COOKED_BLOCK_VARIANTS = Map.ofEntries(
            Map.entry(ModBlocks.CREEPER_MEAT_BLOCK.get(), ModBlocks.COOKED_CREEPER_MEAT_BLOCK.get()),
            Map.entry(ModBlocks.DOG_LIVER_BLOCK.get(), ModBlocks.COOKED_DOG_LIVER_BLOCK.get()),
            Map.entry(ModBlocks.HORSE_MEAT_BLOCK.get(), ModBlocks.COOKED_HORSE_MEAT_BLOCK.get()),
            Map.entry(ModBlocks.CAT_MEAT_BLOCK.get(), ModBlocks.COOKED_CAT_MEAT_BLOCK.get()),
            Map.entry(ModBlocks.SQUID_MEAT_BLOCK.get(), ModBlocks.COOKED_SQUID_MEAT_BLOCK.get()),
            Map.entry(ModBlocks.BAT_WINGS_BLOCK.get(), ModBlocks.COOKED_BAT_WINGS_BLOCK.get()),
            Map.entry(ModBlocks.FROG_LEGS_BLOCK.get(), ModBlocks.COOKED_FROG_LEGS_BLOCK.get()),
            Map.entry(ModBlocks.SNIFFER_TOES_BLOCK.get(), ModBlocks.COOKED_SNIFFER_TOES_BLOCK.get()),
            Map.entry(ModBlocks.PANDA_BALLS_BLOCK.get(), ModBlocks.COOKED_PANDA_BALLS_BLOCK.get()),
            Map.entry(ModBlocks.GUARDIAN_HEART_BLOCK.get(), ModBlocks.COOKED_GUARDIAN_HEART_BLOCK.get()),
            Map.entry(ModBlocks.ELDER_GUARDIAN_HEART_BLOCK.get(), ModBlocks.COOKED_ELDER_GUARDIAN_HEART_BLOCK.get()),

            Map.entry(ModBlocks.BEEF_BLOCK.get(), ModBlocks.COOKED_BEEF_BLOCK.get()),
            Map.entry(ModBlocks.CHICKEN_BLOCK.get(), ModBlocks.COOKED_CHICKEN_BLOCK.get()),
            Map.entry(ModBlocks.COD_BLOCK.get(), ModBlocks.COOKED_COD_BLOCK.get()),
            Map.entry(ModBlocks.MUTTON_BLOCK.get(), ModBlocks.COOKED_MUTTON_BLOCK.get()),
            Map.entry(ModBlocks.PORKCHOP_BLOCK.get(), ModBlocks.COOKED_PORKCHOP_BLOCK.get()),
            Map.entry(ModBlocks.RABBIT_BLOCK.get(), ModBlocks.COOKED_RABBIT_BLOCK.get()),
            Map.entry(ModBlocks.SALMON_BLOCK.get(), ModBlocks.COOKED_SALMON_BLOCK.get())
    );

    public static final Map<Item, Item> REFINABLES = Map.ofEntries(
            Map.entry(ModItems.CREEPER_MEAT.get(), ModItems.GUNPOWDER_CLUMPS.get()),
            Map.entry(ModItems.ENDERMEAT.get(), ModItems.ENDER_SPICE.get()),
            Map.entry(ModItems.DOG_LIVER.get(), ModItems.LIVER_BITS.get()),
            Map.entry(ModItems.COOKED_BLAZE_MEAT.get(), ModItems.BLAZE_PEPPER.get()),
            Map.entry(Items.GUNPOWDER, ModItems.GUNPOWDER_CLUMPS.get()),
            Map.entry(Items.COAL.asItem(), ModItems.BITTER_CRUMBS.get()),
            Map.entry(Items.CHARCOAL.asItem(), ModItems.BITTER_CRUMBS.get())
    );
    public static final Map<Item, Item> COMPACTABLES = Map.ofEntries(
            Map.entry(ModItems.CREEPER_MEAT.get(), ModBlocks.CREEPER_MEAT_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_CREEPER_MEAT.get(), ModBlocks.COOKED_CREEPER_MEAT_BLOCK.get().asItem()),
            Map.entry(ModItems.DOG_LIVER.get(), ModBlocks.DOG_LIVER_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_DOG_LIVER.get(), ModBlocks.COOKED_DOG_LIVER_BLOCK.get().asItem()),
            Map.entry(ModItems.ENDERMEAT.get(), ModBlocks.ENDERMEAT_BLOCK.get().asItem()),
            Map.entry(ModItems.HORSE_MEAT.get(), ModBlocks.HORSE_MEAT_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_HORSE_MEAT.get(), ModBlocks.COOKED_HORSE_MEAT_BLOCK.get().asItem()),
            Map.entry(ModItems.CAT_MEAT.get(), ModBlocks.CAT_MEAT_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_CAT_MEAT.get(), ModBlocks.COOKED_CAT_MEAT_BLOCK.get().asItem()),
            Map.entry(ModItems.SQUID_MEAT.get(), ModBlocks.SQUID_MEAT_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_SQUID_MEAT.get(), ModBlocks.COOKED_SQUID_MEAT_BLOCK.get().asItem()),
            Map.entry(ModItems.BAT_WINGS.get(), ModBlocks.BAT_WINGS_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_BAT_WINGS.get(), ModBlocks.COOKED_BAT_WINGS_BLOCK.get().asItem()),
            Map.entry(ModItems.FROG_LEGS.get(), ModBlocks.FROG_LEGS_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_FROG_LEGS.get(), ModBlocks.COOKED_FROG_LEGS_BLOCK.get().asItem()),
            Map.entry(ModItems.SNIFFER_TOES.get(), ModBlocks.SNIFFER_TOES_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_SNIFFER_TOES.get(), ModBlocks.COOKED_SNIFFER_TOES_BLOCK.get().asItem()),
            Map.entry(ModItems.MOLDY_SNIFFER_TOES.get(), ModBlocks.MOLDY_SNIFFER_TOES_BLOCK.get().asItem()),
            Map.entry(ModItems.PANDA_BALLS.get(), ModBlocks.PANDA_BALLS_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_PANDA_BALLS.get(), ModBlocks.COOKED_PANDA_BALLS_BLOCK.get().asItem()),
            Map.entry(ModItems.GUARDIAN_HEART.get(), ModBlocks.GUARDIAN_HEART_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_GUARDIAN_HEART.get(), ModBlocks.COOKED_GUARDIAN_HEART_BLOCK.get().asItem()),
            Map.entry(ModItems.ELDER_GUARDIAN_HEART.get(), ModBlocks.ELDER_GUARDIAN_HEART_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_ELDER_GUARDIAN_HEART.get(), ModBlocks.COOKED_ELDER_GUARDIAN_HEART_BLOCK.get().asItem()),

            Map.entry(Items.BEEF, ModBlocks.BEEF_BLOCK.get().asItem()),
            Map.entry(Items.COOKED_BEEF, ModBlocks.COOKED_BEEF_BLOCK.get().asItem()),
            Map.entry(Items.CHICKEN, ModBlocks.CHICKEN_BLOCK.get().asItem()),
            Map.entry(Items.COOKED_CHICKEN, ModBlocks.COOKED_CHICKEN_BLOCK.get().asItem()),
            Map.entry(Items.COD, ModBlocks.COD_BLOCK.get().asItem()),
            Map.entry(Items.COOKED_COD, ModBlocks.COOKED_COD_BLOCK.get().asItem()),
            Map.entry(Items.MUTTON, ModBlocks.MUTTON_BLOCK.get().asItem()),
            Map.entry(Items.COOKED_MUTTON, ModBlocks.COOKED_MUTTON_BLOCK.get().asItem()),
            Map.entry(Items.PORKCHOP, ModBlocks.PORKCHOP_BLOCK.get().asItem()),
            Map.entry(Items.COOKED_PORKCHOP, ModBlocks.COOKED_PORKCHOP_BLOCK.get().asItem()),
            Map.entry(Items.PUFFERFISH, ModBlocks.PUFFERFISH_BLOCK.get().asItem()),
            Map.entry(Items.RABBIT, ModBlocks.RABBIT_BLOCK.get().asItem()),
            Map.entry(Items.COOKED_RABBIT, ModBlocks.COOKED_RABBIT_BLOCK.get().asItem()),
            Map.entry(Items.ROTTEN_FLESH, ModBlocks.ROTTEN_FLESH_BLOCK.get().asItem()),
            Map.entry(Items.SALMON, ModBlocks.SALMON_BLOCK.get().asItem()),
            Map.entry(Items.COOKED_SALMON, ModBlocks.COOKED_SALMON_BLOCK.get().asItem()),
            Map.entry(Items.TROPICAL_FISH, ModBlocks.TROPICAL_FISH_BLOCK.get().asItem())
    );
}
