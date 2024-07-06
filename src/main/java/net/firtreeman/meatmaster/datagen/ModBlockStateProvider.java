package net.firtreeman.meatmaster.datagen;

import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.block.ModBlocks;
import net.firtreeman.meatmaster.block.custom.MeatBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
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

//    private void makeMeatBlockWithItem(RegistryObject<Block> blockRegistryObject) {
//        simpleBlockWithItem(blockRegistryObject.get(), models().cubeAll(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), new ResourceLocation("meatmaster:block/meat_block_base")));
//    }
}
