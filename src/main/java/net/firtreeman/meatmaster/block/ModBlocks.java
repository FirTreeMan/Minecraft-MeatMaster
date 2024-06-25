package net.firtreeman.meatmaster.block;

import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.block.custom.*;
import net.firtreeman.meatmaster.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MeatMaster.MOD_ID);

    public static final RegistryObject<Block> CREEPER_MEAT_BLOCK = registerBlock("creeper_meat_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK)));
    public static final RegistryObject<Block> COOKED_CREEPER_MEAT_BLOCK = registerBlock("cooked_creeper_meat_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK)));

    public static final RegistryObject<Block> MEAT_REFINERY_STATION = registerBlock("meat_refinery_station", () -> new MeatRefineryStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> MEAT_COMPACTOR_STATION = registerBlock("meat_compactor_station", () -> new MeatCompactorStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> LATHERER_STATION = registerBlock("latherer_station", () -> new LathererStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> INDUSTRIAL_OVEN_STATION = registerBlock("industrial_oven_station", () -> new IndustrialOvenStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> MEAT_MASHER_STATION = registerBlock("meat_masher_station", () -> new MeatMasherStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> FOOD_TROUGH_STATION = registerBlock("food_trough_station", () -> new FoodTroughStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> HORMONE_RESEARCH_STATION = registerBlock("hormone_research_station", () -> new HormoneResearchStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
