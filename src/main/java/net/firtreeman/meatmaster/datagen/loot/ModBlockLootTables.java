package net.firtreeman.meatmaster.datagen.loot;

import net.firtreeman.meatmaster.block.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.CREEPER_MEAT_BLOCK.get());
        this.dropSelf(ModBlocks.COOKED_CREEPER_MEAT_BLOCK.get());

        this.dropSelf(ModBlocks.MEAT_REFINERY_STATION.get());
        this.dropSelf(ModBlocks.MEAT_COMPACTOR_STATION.get());
        this.dropSelf(ModBlocks.LATHERER_STATION.get());
        this.dropSelf(ModBlocks.INDUSTRIAL_OVEN_STATION.get());
        this.dropSelf(ModBlocks.MEAT_MASHER_STATION.get());
        this.dropSelf(ModBlocks.FOOD_TROUGH_STATION.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
