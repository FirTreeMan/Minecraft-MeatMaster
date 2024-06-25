package net.firtreeman.meatmaster.block;

import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.block.entity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MeatMaster.MOD_ID);

    public static final RegistryObject<BlockEntityType<MeatRefineryStationBlockEntity>> MEAT_REFINERY_SBE =
            BLOCK_ENTITIES.register("meat_refinery_sbe", () ->
                    BlockEntityType.Builder.of(MeatRefineryStationBlockEntity::new, ModBlocks.MEAT_REFINERY_STATION.get()).build(null));
    public static final RegistryObject<BlockEntityType<MeatCompactorStationBlockEntity>> MEAT_COMPACTOR_SBE =
            BLOCK_ENTITIES.register("meat_compactor_sbe", () ->
                    BlockEntityType.Builder.of(MeatCompactorStationBlockEntity::new, ModBlocks.MEAT_COMPACTOR_STATION.get()).build(null));
    public static final RegistryObject<BlockEntityType<LathererStationBlockEntity>> LATHERER_SBE =
            BLOCK_ENTITIES.register("latherer_sbe", () ->
                    BlockEntityType.Builder.of(LathererStationBlockEntity::new, ModBlocks.LATHERER_STATION.get()).build(null));
    public static final RegistryObject<BlockEntityType<IndustrialOvenStationBlockEntity>> INDUSTRIAL_OVEN_SBE =
            BLOCK_ENTITIES.register("industrial_oven_sbe", () ->
                    BlockEntityType.Builder.of(IndustrialOvenStationBlockEntity::new, ModBlocks.INDUSTRIAL_OVEN_STATION.get()).build(null));
    public static final RegistryObject<BlockEntityType<MeatMasherStationBlockEntity>> MEAT_MASHER_SBE =
            BLOCK_ENTITIES.register("meat_masher_sbe", () ->
                    BlockEntityType.Builder.of(MeatMasherStationBlockEntity::new, ModBlocks.MEAT_MASHER_STATION.get()).build(null));
    public static final RegistryObject<BlockEntityType<FoodTroughStationBlockEntity>> FOOD_TROUGH_SBE =
            BLOCK_ENTITIES.register("food_trough_sbe", () ->
                    BlockEntityType.Builder.of(FoodTroughStationBlockEntity::new, ModBlocks.FOOD_TROUGH_STATION.get()).build(null));
    public static final RegistryObject<BlockEntityType<HormoneResearchStationBlockEntity>> HORMONE_RESEARCH_SBE =
            BLOCK_ENTITIES.register("hormone_research_sbe", () ->
                    BlockEntityType.Builder.of(HormoneResearchStationBlockEntity::new, ModBlocks.HORMONE_RESEARCH_STATION.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
