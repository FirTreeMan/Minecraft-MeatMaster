package net.firtreeman.meatmaster.recipe;

import net.firtreeman.meatmaster.MeatMaster;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    private static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MeatMaster.MOD_ID);

    public static final RegistryObject<RecipeSerializer<MeatRefineryRecipe>> MEAT_REFINERY_SERIALIZER = SERIALIZERS.register("meat_refinery", () -> MeatRefineryRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<MeatCompactorRecipe>> MEAT_COMPACTOR_SERIALIZER = SERIALIZERS.register("meat_compactor", () -> MeatCompactorRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<IndustrialOvenRecipe>> INDUSTRIAL_OVEN_SERIALIZER = SERIALIZERS.register("industrial_oven", () -> IndustrialOvenRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<MeatMasherRecipe>> MEAT_MASHER_SERIALIZER = SERIALIZERS.register("meat_masher", () -> MeatMasherRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<HormoneResearchRecipe>> HORMONE_RESEARCH_SERIALIZER = SERIALIZERS.register("hormone_research", () -> HormoneResearchRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<HormoneFillRecipe>> HORMONE_FILL_SERIALIZER = SERIALIZERS.register("hormone_fill", () -> HormoneFillRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
