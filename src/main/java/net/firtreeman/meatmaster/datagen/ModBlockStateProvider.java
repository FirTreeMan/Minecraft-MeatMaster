package net.firtreeman.meatmaster.datagen;

import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.block.ModBlocks;
import net.firtreeman.meatmaster.block.custom.MeatBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MeatMaster.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (RegistryObject<Block> block: ModBlocks.BLOCKS.getEntries())
            if (block.get() instanceof MeatBlock)
                makeBlockWithItem(block);

        litBlockWithItem(ModBlocks.MEAT_REFINERY_STATION, "meat_refinery_station");
        simpleBlockWithItem(ModBlocks.MEAT_COMPACTOR_STATION.get(), new ModelFile.UncheckedModelFile(modLoc("block/meat_compactor_station")));
        simpleBlockWithItem(ModBlocks.LATHERER_STATION.get(), new ModelFile.UncheckedModelFile(modLoc("block/latherer_station")));
        litHorizBlockWithItem(ModBlocks.INDUSTRIAL_OVEN_STATION, "industrial_oven_station");
        simpleBlockWithItem(ModBlocks.MEAT_MASHER_STATION.get(), new ModelFile.UncheckedModelFile(modLoc("block/meat_masher_station")));
        simpleBlockWithItem(ModBlocks.FOOD_TROUGH_STATION.get(), new ModelFile.UncheckedModelFile(modLoc("block/food_trough_station")));
    }

    private void makeBlockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void blockEntityItem(RegistryObject<Block> blockRegistryObject, String blockName) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(modLoc("block/" + blockName)));
    }

    private void litHorizBlockWithItem(RegistryObject<Block> blockRegistryObject, String blockName) {
        Block block = blockRegistryObject.get();
        ModelFile modelFileOff = new ModelFile.UncheckedModelFile(modLoc("block/" + blockName));
        ModelFile modelFileOn = new ModelFile.UncheckedModelFile(modLoc("block/" + blockName + "_on"));

        this.getVariantBuilder(block)
            .forAllStates(state ->
                ConfiguredModel.builder()
                    .modelFile(state.getValue(BlockStateProperties.LIT) ? modelFileOn : modelFileOff)
                    .rotationY((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot())
                    .build());

        blockEntityItem(blockRegistryObject, blockName);
    }

    private void litBlockWithItem(RegistryObject<Block> blockRegistryObject, String blockName) {
        Block block = blockRegistryObject.get();
        ModelFile modelFileOff = new ModelFile.UncheckedModelFile(modLoc("block/" + blockName));
        ModelFile modelFileOn = new ModelFile.UncheckedModelFile(modLoc("block/" + blockName + "_on"));

        this.getVariantBuilder(block)
            .partialState().with(BlockStateProperties.LIT, false)
                .modelForState()
                .modelFile(modelFileOff)
                .addModel()
            .partialState().with(BlockStateProperties.LIT, true)
                .modelForState()
                .modelFile(modelFileOn)
                .addModel();

        blockEntityItem(blockRegistryObject, blockName);
    }

    private void horizBlockWithItem(RegistryObject<Block> blockRegistryObject, String blockName) {
        Block block = blockRegistryObject.get();
        ModelFile modelFile = new ModelFile.UncheckedModelFile(modLoc("block/" + blockName));

        this.getVariantBuilder(block).forAllStates(state ->
            ConfiguredModel.builder()
              .modelFile(modelFile)
              .rotationY((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot())
              .build());

        blockEntityItem(blockRegistryObject, blockName);
    }

//    private void makeMeatBlockWithItem(RegistryObject<Block> blockRegistryObject) {
//        simpleBlockWithItem(blockRegistryObject.get(), models().cubeAll(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), new ResourceLocation("meatmaster:block/meat_block_base")));
//    }
}
