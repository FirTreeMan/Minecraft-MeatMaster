package net.firtreeman.meatmaster.util;

import net.firtreeman.meatmaster.MeatMaster;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> EXTRA_REFINABLE_MEATS = tag("extra_refinable_meats");
        public static final TagKey<Item> MASHABLE_MEATS = tag("mashable_meats");
        public static final TagKey<Item> EXTRACTABLE_MEATS = tag("extractable_meats");
        public static final TagKey<Item> MEATS = tag("meats");
        public static final TagKey<Item> MEAT_BLOCKS = tag("meat_blocks");
        public static final TagKey<Item> SPICES = tag("spices");
        public static final TagKey<Item> VALID_KEBAB_INGREDIENTS = tag("valid_kebab_ingredients");
//        public static final TagKey<Item> HORMONE_ARROWS = tag("hormone_arrows");
//        public static final TagKey<Item> HORMONE_BASES = tag("hormone_bases");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(MeatMaster.MOD_ID, name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> MEAT_BLOCKS = tag("meat_blocks");
        public static final TagKey<Block> UNCOOKED_MEAT_BLOCKS = tag("uncooked_meat_blocks");
        public static final TagKey<Block> COOKED_MEAT_BLOCKS = tag("cooked_meat_blocks");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(MeatMaster.MOD_ID, name));
        }
    }
}
