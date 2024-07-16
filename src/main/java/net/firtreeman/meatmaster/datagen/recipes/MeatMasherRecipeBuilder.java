package net.firtreeman.meatmaster.datagen.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.firtreeman.meatmaster.recipe.MeatMasherRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Consumer;

public class MeatMasherRecipeBuilder implements RecipeBuilder {
    private final Ingredient ingredient;
    private final String ingredientName;
    private final Item result;
    private final Item failResult;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();

    public MeatMasherRecipeBuilder(ItemLike ingredient, Item result, Item failResult) {
        this(Ingredient.of(ingredient), ForgeRegistries.ITEMS.getKey((Item) ingredient).toString(), result, failResult);
    }

    public MeatMasherRecipeBuilder(TagKey<Item> ingredient, Item result, Item failResult) {
        this(Ingredient.of(ingredient), ingredient.toString().substring(ingredient.toString().lastIndexOf(' ') + 1, ingredient.toString().length() - 1).replace(":", "__"), result, failResult);
    }

    public MeatMasherRecipeBuilder(Ingredient ingredient, String ingredientName, Item result, Item failResult) {
        this.ingredient = ingredient;
        this.ingredientName = ingredientName;
        this.result = result.asItem();
        this.failResult = failResult.asItem();
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

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public Item getResult() {
        return result;
    }

    public Item getFailResult() {
        return failResult;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe",
                RecipeUnlockedTrigger.unlocked(pRecipeId))
                .rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);

        pFinishedRecipeConsumer.accept(new MeatMasherRecipeBuilder.Result(
                pRecipeId,
                this.result,
                this.failResult,
                this.ingredient,
                this.ingredientName,
                this.advancement,
                new ResourceLocation(pRecipeId.getNamespace(),
                        "recipes/" + pRecipeId.getPath())));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final Item failResult;
        private final Ingredient ingredient;
        private final String ingredientName;
        private final int count;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, Item pResult, Item pFailResult, Ingredient pIngredient, String ingredientName, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId) {
            this.id = pId;
            this.result = pResult;
            this.failResult = pFailResult;
            this.ingredient = pIngredient;
            this.ingredientName = ingredientName.substring(ingredientName.indexOf(':') + 1);
            this.count = this.ingredient.getItems()[0].getItem().getFoodProperties().getNutrition() / this.result.getFoodProperties().getNutrition();
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            JsonArray jsonArray = new JsonArray();
            jsonArray.add(this.ingredient.toJson());
            pJson.add("ingredients", jsonArray);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());

            JsonObject failJsonObject = new JsonObject();
            failJsonObject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.failResult).toString());

            if (this.count > 1) {
                jsonObject.addProperty("count", this.count);
                failJsonObject.addProperty("count", this.count);
            }

            pJson.add("result", jsonObject);
            pJson.add("fail_result", failJsonObject);
        }

        @Override
        public ResourceLocation getId() {
            return new ResourceLocation("meatmaster:meat_masher_of_" + ingredientName);
        }

        @Override
        public RecipeSerializer<?> getType() {
            return MeatMasherRecipe.Serializer.INSTANCE;
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
