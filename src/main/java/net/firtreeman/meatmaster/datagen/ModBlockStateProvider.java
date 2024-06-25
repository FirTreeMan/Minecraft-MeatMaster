package net.firtreeman.meatmaster.datagen;

import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MeatMaster.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        makeBlockWithItem(ModBlocks.CREEPER_MEAT_BLOCK);
        makeBlockWithItem(ModBlocks.COOKED_CREEPER_MEAT_BLOCK);

        simpleBlockWithItem(ModBlocks.MEAT_REFINERY_STATION.get(), new ModelFile.UncheckedModelFile(modLoc("block/meat_refinery_station")));
        simpleBlockWithItem(ModBlocks.MEAT_COMPACTOR_STATION.get(), new ModelFile.UncheckedModelFile(modLoc("block/meat_compactor_station")));
        simpleBlockWithItem(ModBlocks.LATHERER_STATION.get(), new ModelFile.UncheckedModelFile(modLoc("block/latherer_station")));
        simpleBlockWithItem(ModBlocks.INDUSTRIAL_OVEN_STATION.get(), new ModelFile.UncheckedModelFile(modLoc("block/industrial_oven_station")));
        simpleBlockWithItem(ModBlocks.MEAT_MASHER_STATION.get(), new ModelFile.UncheckedModelFile(modLoc("block/meat_masher_station")));
        simpleBlockWithItem(ModBlocks.FOOD_TROUGH_STATION.get(), new ModelFile.UncheckedModelFile(modLoc("block/food_trough_station")));
    }

    private void makeBlockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
