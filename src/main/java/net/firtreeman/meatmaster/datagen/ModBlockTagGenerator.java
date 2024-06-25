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
                ModBlocks.CREEPER_MEAT_BLOCK.get()
        );
        tag(ModTags.Blocks.COOKED_MEAT_BLOCKS).add(
                ModBlocks.COOKED_CREEPER_MEAT_BLOCK.get()
        );

        tag(ModTags.Blocks.MEAT_BLOCKS).addTag(
                ModTags.Blocks.UNCOOKED_MEAT_BLOCKS
        );
        tag(ModTags.Blocks.MEAT_BLOCKS).addTag(
                ModTags.Blocks.COOKED_MEAT_BLOCKS
        );
    }
}
