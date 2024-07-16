package net.firtreeman.meatmaster.datagen.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.firtreeman.meatmaster.item.ModItems;
import net.firtreeman.meatmaster.recipe.HormoneFillRecipe;
import net.firtreeman.meatmaster.util.HORMONE_TYPES;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class HormoneFillRecipeBuilder implements RecipeBuilder {
    private final Ingredient ingredient;
    private final Ingredient secondaryIngredient;
    private final Item result = ModItems.SYRINGE_DART.get();
    private final HORMONE_TYPES hormoneType;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();

    public HormoneFillRecipeBuilder(Item ingredient, HORMONE_TYPES hormoneType) {
        this(Ingredient.of(ingredient), Ingredient.of(ModItems.SYRINGE_DART.get()), hormoneType);
    }

    public HormoneFillRecipeBuilder(Ingredient ingredient, Ingredient secondaryIngredient, HORMONE_TYPES hormoneType) {
        this.ingredient = ingredient;
        this.secondaryIngredient = secondaryIngredient;
        this.hormoneType = hormoneType;
    }

    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe",
                RecipeUnlockedTrigger.unlocked(pRecipeId))
                .rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);

        pFinishedRecipeConsumer.accept(new HormoneFillRecipeBuilder.Result(
                pRecipeId,
                this.result,
                this.ingredient,
                this.secondaryIngredient,
                this.hormoneType,
                this.advancement,
                new ResourceLocation(pRecipeId.getNamespace(),
                        "recipes/" + pRecipeId.getPath())));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final Ingredient ingredient;
        private final Ingredient secondaryIngredient;
        private final HORMONE_TYPES hormone_type;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, Item pResult, Ingredient pIngredient, Ingredient secondaryIngredient, HORMONE_TYPES hormoneType, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId) {
            this.id = pId;
            this.result = pResult;
            this.ingredient = pIngredient;
            this.secondaryIngredient = secondaryIngredient;
            this.hormone_type = hormoneType;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            JsonArray jsonArray = new JsonArray();
            jsonArray.add(this.ingredient.toJson());
            jsonArray.add(this.secondaryIngredient.toJson());
            pJson.add("ingredients", jsonArray);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            pJson.add("result", jsonObject);

            pJson.addProperty("hormone_type", this.hormone_type.ordinal());
        }

        @Override
        public ResourceLocation getId() {
            return new ResourceLocation("meatmaster:hormone_fill_of_" + hormone_type.toString().toLowerCase());
        }

        @Override
        public RecipeSerializer<?> getType() {
            return HormoneFillRecipe.Serializer.INSTANCE;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
