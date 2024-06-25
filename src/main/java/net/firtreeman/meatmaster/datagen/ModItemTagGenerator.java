package net.firtreeman.meatmaster.datagen;

import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.block.ModBlocks;
import net.firtreeman.meatmaster.item.ModItems;
import net.firtreeman.meatmaster.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {

    public ModItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, MeatMaster.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModTags.Items.MEATS).add(
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
                ModItems.COOKED_SAUSAGE.get()
        );

        tag(ModTags.Items.MEAT_BLOCKS).add(
                ModBlocks.CREEPER_MEAT_BLOCK.get().asItem(),
                ModBlocks.COOKED_CREEPER_MEAT_BLOCK.get().asItem()
        );

        tag(ModTags.Items.MEATS).addTag(ModTags.Items.MEAT_BLOCKS);

        tag(ModTags.Items.EXTRA_REFINABLE_MEATS).addTag(ModTags.Items.MEATS);
        ModRecipeProvider.REFINABLES.forEach((ingredient, spice) -> tag(ModTags.Items.EXTRA_REFINABLE_MEATS).remove(ingredient));



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
    }
}
