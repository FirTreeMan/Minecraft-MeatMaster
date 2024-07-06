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

    public static final RegistryObject<Block> CREEPER_MEAT_BLOCK = registerBlock("creeper_meat_block", MeatBlock::new);
    public static final RegistryObject<Block> COOKED_CREEPER_MEAT_BLOCK = registerBlock("cooked_creeper_meat_block", MeatBlock::new);
    public static final RegistryObject<Block> DOG_LIVER_BLOCK = registerBlock("dog_liver_block", MeatBlock::new);
    public static final RegistryObject<Block> COOKED_DOG_LIVER_BLOCK = registerBlock("cooked_dog_liver_block", MeatBlock::new);
    public static final RegistryObject<Block> ENDERMEAT_BLOCK = registerBlock("endermeat_block", MeatBlock::new);
    public static final RegistryObject<Block> HORSE_MEAT_BLOCK = registerBlock("horse_meat_block", MeatBlock::new);
    public static final RegistryObject<Block> COOKED_HORSE_MEAT_BLOCK = registerBlock("cooked_horse_meat_block", MeatBlock::new);
    public static final RegistryObject<Block> CAT_MEAT_BLOCK = registerBlock("cat_meat_block", MeatBlock::new);
    public static final RegistryObject<Block> COOKED_CAT_MEAT_BLOCK = registerBlock("cooked_cat_meat_block", MeatBlock::new);
    public static final RegistryObject<Block> SQUID_MEAT_BLOCK = registerBlock("squid_meat_block", MeatBlock::new);
    public static final RegistryObject<Block> COOKED_SQUID_MEAT_BLOCK = registerBlock("cooked_squid_meat_block", MeatBlock::new);

    public static final RegistryObject<Block> BEEF_BLOCK = registerBlock("beef_block", MeatBlock::new);
    public static final RegistryObject<Block> COOKED_BEEF_BLOCK = registerBlock("cooked_beef_block", MeatBlock::new);
    public static final RegistryObject<Block> CHICKEN_BLOCK = registerBlock("chicken_block", MeatBlock::new);
    public static final RegistryObject<Block> COOKED_CHICKEN_BLOCK = registerBlock("cooked_chicken_block", MeatBlock::new);
    public static final RegistryObject<Block> COD_BLOCK = registerBlock("cod_block", MeatBlock::new);
    public static final RegistryObject<Block> COOKED_COD_BLOCK = registerBlock("cooked_cod_block", MeatBlock::new);
    public static final RegistryObject<Block> MUTTON_BLOCK = registerBlock("mutton_block", MeatBlock::new);
    public static final RegistryObject<Block> COOKED_MUTTON_BLOCK = registerBlock("cooked_mutton_block", MeatBlock::new);
    public static final RegistryObject<Block> PORKCHOP_BLOCK = registerBlock("porkchop_block", MeatBlock::new);
    public static final RegistryObject<Block> COOKED_PORKCHOP_BLOCK = registerBlock("cooked_porkchop_block", MeatBlock::new);
    public static final RegistryObject<Block> PUFFERFISH_BLOCK = registerBlock("pufferfish_block", MeatBlock::new);
    public static final RegistryObject<Block> RABBIT_BLOCK = registerBlock("rabbit_block", MeatBlock::new);
    public static final RegistryObject<Block> COOKED_RABBIT_BLOCK = registerBlock("cooked_rabbit_block", MeatBlock::new);
    public static final RegistryObject<Block> ROTTEN_FLESH_BLOCK = registerBlock("rotten_flesh_block", MeatBlock::new);
    public static final RegistryObject<Block> SALMON_BLOCK = registerBlock("salmon_block", MeatBlock::new);
    public static final RegistryObject<Block> COOKED_SALMON_BLOCK = registerBlock("cooked_salmon_block", MeatBlock::new);
    public static final RegistryObject<Block> TROPICAL_FISH_BLOCK = registerBlock("tropical_fish_block", MeatBlock::new);

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
