package net.firtreeman.meatmaster.datagen.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.recipe.MeatRefineryRecipe;
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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class MeatRefineryRecipeBuilder implements RecipeBuilder {
    private final Ingredient ingredient;
    private final String ingredientName;
    private final Item result;
    private final int count;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();

    public MeatRefineryRecipeBuilder(ItemLike ingredient, Item result, int count) {
        this(Ingredient.of(ingredient), ForgeRegistries.ITEMS.getKey((Item) ingredient).toString(), result, count);
    }

    public MeatRefineryRecipeBuilder(TagKey<Item> ingredient, Item result, int count) {
        this(Ingredient.of(ingredient), ingredient.toString().substring(ingredient.toString().lastIndexOf(' ') + 1, ingredient.toString().length() - 1).replace(":", "__"), result, count);
    }

    public MeatRefineryRecipeBuilder(Ingredient ingredient, String ingredientName, Item result, int count) {
        this.ingredient = ingredient;
        this.ingredientName = ingredientName;
        this.result = result.asItem();
        this.count = count;
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

        pFinishedRecipeConsumer.accept(new MeatRefineryRecipeBuilder.Result(
                pRecipeId,
                this.result,
                this.ingredient,
                this.ingredientName,
                this.count,
                this.advancement,
                new ResourceLocation(pRecipeId.getNamespace(),
                        "recipes/" + pRecipeId.getPath())));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final Ingredient ingredient;
        private final String ingredientName;
        private final int count;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, Item pResult, Ingredient pIngredient, String ingredientName, int pCount, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId) {
            this.id = pId;
            this.result = pResult;
            this.ingredient = pIngredient;
            this.ingredientName = ingredientName.substring(ingredientName.indexOf(':') + 1);
            this.count = pCount;
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
            if (this.count > 1) {
                jsonObject.addProperty("count", this.count);
            }

            pJson.add("result", jsonObject);
        }

        @Override
        public ResourceLocation getId() {
            return new ResourceLocation(ForgeRegistries.ITEMS.getKey(this.result).toString() + "_from_meat_refinery_of_" + ingredientName);
        }

        @Override
        public RecipeSerializer<?> getType() {
            return MeatRefineryRecipe.Serializer.INSTANCE;
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
