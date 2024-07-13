package net.firtreeman.meatmaster.datagen;

import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.block.ModBlocks;
import net.firtreeman.meatmaster.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MeatMaster.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModTags.Blocks.UNCOOKED_MEAT_BLOCKS).add(
                ModBlocks.CREEPER_MEAT_BLOCK.get(),
                ModBlocks.DOG_LIVER_BLOCK.get(),
                ModBlocks.ENDERMEAT_BLOCK.get(),
                ModBlocks.HORSE_MEAT_BLOCK.get(),
                ModBlocks.CAT_MEAT_BLOCK.get(),
                ModBlocks.SQUID_MEAT_BLOCK.get(),
                ModBlocks.BAT_WINGS_BLOCK.get(),
                ModBlocks.FROG_LEGS_BLOCK.get(),
                ModBlocks.SNIFFER_TOES_BLOCK.get(),
                ModBlocks.MOLDY_SNIFFER_TOES_BLOCK.get(),
                ModBlocks.PANDA_BALLS_BLOCK.get(),
                ModBlocks.GUARDIAN_HEART_BLOCK.get(),
                ModBlocks.ELDER_GUARDIAN_HEART_BLOCK.get(),

                ModBlocks.BEEF_BLOCK.get(),
                ModBlocks.CHICKEN_BLOCK.get(),
                ModBlocks.COD_BLOCK.get(),
                ModBlocks.MUTTON_BLOCK.get(),
                ModBlocks.PORKCHOP_BLOCK.get(),
                ModBlocks.PUFFERFISH_BLOCK.get(),
                ModBlocks.RABBIT_BLOCK.get(),
                ModBlocks.ROTTEN_FLESH_BLOCK.get(),
                ModBlocks.SALMON_BLOCK.get(),
                ModBlocks.TROPICAL_FISH_BLOCK.get()

        );
        tag(ModTags.Blocks.COOKED_MEAT_BLOCKS).add(
                ModBlocks.COOKED_CREEPER_MEAT_BLOCK.get(),
                ModBlocks.COOKED_DOG_LIVER_BLOCK.get(),
                ModBlocks.COOKED_HORSE_MEAT_BLOCK.get(),
                ModBlocks.COOKED_CAT_MEAT_BLOCK.get(),
                ModBlocks.COOKED_SQUID_MEAT_BLOCK.get(),
                ModBlocks.COOKED_BAT_WINGS_BLOCK.get(),
                ModBlocks.COOKED_FROG_LEGS_BLOCK.get(),
                ModBlocks.COOKED_SNIFFER_TOES_BLOCK.get(),
                ModBlocks.COOKED_PANDA_BALLS_BLOCK.get(),
                ModBlocks.COOKED_GUARDIAN_HEART_BLOCK.get(),
                ModBlocks.COOKED_ELDER_GUARDIAN_HEART_BLOCK.get(),

                ModBlocks.COOKED_BEEF_BLOCK.get(),
                ModBlocks.COOKED_CHICKEN_BLOCK.get(),
                ModBlocks.COOKED_COD_BLOCK.get(),
                ModBlocks.COOKED_MUTTON_BLOCK.get(),
                ModBlocks.COOKED_PORKCHOP_BLOCK.get(),
                ModBlocks.COOKED_RABBIT_BLOCK.get(),
                ModBlocks.COOKED_SALMON_BLOCK.get()
        );

        tag(ModTags.Blocks.MEAT_BLOCKS).addTag(ModTags.Blocks.UNCOOKED_MEAT_BLOCKS);
        tag(ModTags.Blocks.MEAT_BLOCKS).addTag(ModTags.Blocks.COOKED_MEAT_BLOCKS);
    }
}
