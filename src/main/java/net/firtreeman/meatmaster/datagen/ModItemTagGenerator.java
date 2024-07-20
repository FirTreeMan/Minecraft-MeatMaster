package net.firtreeman.meatmaster.datagen;

import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.block.ModBlocks;
import net.firtreeman.meatmaster.item.ModItems;
import net.firtreeman.meatmaster.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static net.firtreeman.meatmaster.util.ModItemUtils.COOKED_VARIANTS;
import static net.firtreeman.meatmaster.util.ModItemUtils.REFINABLES;

public class ModItemTagGenerator extends ItemTagsProvider {

    public ModItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, MeatMaster.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModTags.Items.MEAT_ITEMS).add(
                Items.BEEF,
                Items.COOKED_BEEF,
                Items.PORKCHOP,
                Items.COOKED_PORKCHOP,
                Items.CHICKEN,
                Items.COOKED_CHICKEN,
                Items.COD,
                Items.COOKED_COD,
                Items.SALMON,
                Items.COOKED_SALMON,
                Items.MUTTON,
                Items.RABBIT,
                Items.COOKED_MUTTON,
                Items.COOKED_RABBIT,
                Items.ROTTEN_FLESH,
                Items.TROPICAL_FISH,
                Items.PUFFERFISH,
                ModItems.CREEPER_MEAT.get(),
                ModItems.COOKED_CREEPER_MEAT.get(),
                ModItems.DOG_LIVER.get(),
                ModItems.COOKED_DOG_LIVER.get(),
                ModItems.ENDERMEAT.get(),
                ModItems.HORSE_MEAT.get(),
                ModItems.COOKED_HORSE_MEAT.get(),
                ModItems.COOKED_BLAZE_MEAT.get(),
                ModItems.SQUID_MEAT.get(),
                ModItems.COOKED_SQUID_MEAT.get(),
                ModItems.SAUSAGE.get(),
                ModItems.COOKED_SAUSAGE.get(),
                ModItems.BAT_WINGS.get(),
                ModItems.COOKED_BAT_WINGS.get(),
                ModItems.FROG_LEGS.get(),
                ModItems.COOKED_FROG_LEGS.get(),
                ModItems.SNIFFER_TOES.get(),
                ModItems.COOKED_SNIFFER_TOES.get(),
                ModItems.MOLDY_SNIFFER_TOES.get(),
                ModItems.PANDA_BALLS.get(),
                ModItems.COOKED_PANDA_BALLS.get(),
                ModItems.GUARDIAN_HEART.get(),
                ModItems.COOKED_GUARDIAN_HEART.get(),
                ModItems.ELDER_GUARDIAN_HEART.get(),
                ModItems.COOKED_ELDER_GUARDIAN_HEART.get()
        );

        tag(ModTags.Items.MEAT_BLOCKS).add(
                ModBlocks.CREEPER_MEAT_BLOCK.get().asItem(),
                ModBlocks.COOKED_CREEPER_MEAT_BLOCK.get().asItem(),
                ModBlocks.DOG_LIVER_BLOCK.get().asItem(),
                ModBlocks.COOKED_DOG_LIVER_BLOCK.get().asItem(),
                ModBlocks.ENDERMEAT_BLOCK.get().asItem(),
                ModBlocks.HORSE_MEAT_BLOCK.get().asItem(),
                ModBlocks.COOKED_HORSE_MEAT_BLOCK.get().asItem(),
                ModBlocks.CAT_MEAT_BLOCK.get().asItem(),
                ModBlocks.COOKED_CAT_MEAT_BLOCK.get().asItem(),
                ModBlocks.SQUID_MEAT_BLOCK.get().asItem(),
                ModBlocks.COOKED_SQUID_MEAT_BLOCK.get().asItem(),
                ModBlocks.BAT_WINGS_BLOCK.get().asItem(),
                ModBlocks.COOKED_BAT_WINGS_BLOCK.get().asItem(),
                ModBlocks.FROG_LEGS_BLOCK.get().asItem(),
                ModBlocks.COOKED_FROG_LEGS_BLOCK.get().asItem(),
                ModBlocks.SNIFFER_TOES_BLOCK.get().asItem(),
                ModBlocks.COOKED_SNIFFER_TOES_BLOCK.get().asItem(),
                ModBlocks.MOLDY_SNIFFER_TOES_BLOCK.get().asItem(),
                ModBlocks.PANDA_BALLS_BLOCK.get().asItem(),
                ModBlocks.COOKED_PANDA_BALLS_BLOCK.get().asItem(),
                ModBlocks.GUARDIAN_HEART_BLOCK.get().asItem(),
                ModBlocks.COOKED_GUARDIAN_HEART_BLOCK.get().asItem(),
                ModBlocks.ELDER_GUARDIAN_HEART_BLOCK.get().asItem(),
                ModBlocks.COOKED_ELDER_GUARDIAN_HEART_BLOCK.get().asItem(),

                ModBlocks.BEEF_BLOCK.get().asItem(),
                ModBlocks.COOKED_BEEF_BLOCK.get().asItem(),
                ModBlocks.CHICKEN_BLOCK.get().asItem(),
                ModBlocks.COOKED_CHICKEN_BLOCK.get().asItem(),
                ModBlocks.COD_BLOCK.get().asItem(),
                ModBlocks.COOKED_COD_BLOCK.get().asItem(),
                ModBlocks.MUTTON_BLOCK.get().asItem(),
                ModBlocks.COOKED_MUTTON_BLOCK.get().asItem(),
                ModBlocks.PORKCHOP_BLOCK.get().asItem(),
                ModBlocks.COOKED_PORKCHOP_BLOCK.get().asItem(),
                ModBlocks.PUFFERFISH_BLOCK.get().asItem(),
                ModBlocks.RABBIT_BLOCK.get().asItem(),
                ModBlocks.COOKED_RABBIT_BLOCK.get().asItem(),
                ModBlocks.ROTTEN_FLESH_BLOCK.get().asItem(),
                ModBlocks.SALMON_BLOCK.get().asItem(),
                ModBlocks.COOKED_SALMON_BLOCK.get().asItem(),
                ModBlocks.TROPICAL_FISH_BLOCK.get().asItem()
        );

        tag(ModTags.Items.MEATS).addTag(ModTags.Items.MEAT_ITEMS);
        tag(ModTags.Items.MEATS).addTag(ModTags.Items.MEAT_BLOCKS);

        tag(ModTags.Items.EXTRA_REFINABLE_MEATS).addTag(ModTags.Items.MEAT_ITEMS);
        REFINABLES.forEach((ingredient, spice) -> tag(ModTags.Items.EXTRA_REFINABLE_MEATS).remove(ingredient));

        tag(ModTags.Items.MASHABLE_MEATS).addTag(ModTags.Items.MEAT_ITEMS);
        COOKED_VARIANTS.forEach((uncooked, cooked) -> {
            for (Item food: new Item[]{uncooked, cooked})
                if (food.getFoodProperties().getNutrition() < 2)
                    tag(ModTags.Items.MASHABLE_MEATS).remove(food);
        });

        tag(ModTags.Items.SPICES).add(
                ModItems.TABLE_SALT.get(),
                ModItems.GUNPOWDER_CLUMPS.get(),
                ModItems.ENDER_SPICE.get(),
                ModItems.LIVER_BITS.get(),
                ModItems.BLAZE_PEPPER.get(),
                ModItems.BITTER_CRUMBS.get(),
                ModItems.MEAT_RESIDUE.get()
        );

        tag(ModTags.Items.VALID_KEBAB_INGREDIENTS).addTag(ModTags.Items.MEATS);

//        tag(ModTags.Items.HORMONE_ARROWS).add(
//                ModItems.SYRINGE_DART.get(),
//                ModItems.GROWTH_SYRINGE_DART.get(),
//                ModItems.BREEDING_SYRINGE_DART.get(),
//                ModItems.YIELD_SYRINGE_DART.get()
//        );
//        tag(ModTags.Items.HORMONE_BASES).add(
//                ModItems.HORMONE_BASE.get(),
//                ModItems.GROWTH_HORMONE_BASE.get(),
//                ModItems.BREEDING_HORMONE_BASE.get(),
//                ModItems.YIELD_HORMONE_BASE.get()
//        );
    }
}
