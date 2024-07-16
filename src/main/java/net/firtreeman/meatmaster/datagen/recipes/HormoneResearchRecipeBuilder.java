package net.firtreeman.meatmaster.datagen.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.firtreeman.meatmaster.item.ModItems;
import net.firtreeman.meatmaster.recipe.HormoneResearchRecipe;
import net.firtreeman.meatmaster.util.HORMONE_TYPES;
import net.firtreeman.meatmaster.util.HormoneUtils;
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
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class HormoneResearchRecipeBuilder implements RecipeBuilder {
    private final Ingredient ingredient;
    private final Ingredient determiner;
    private final String determinerName;
    private final Item result = ModItems.HORMONE_BASE.get();
    private final HORMONE_TYPES hormone_type;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();

    public HormoneResearchRecipeBuilder(TagKey<Item> ingredient, Item determiner, HORMONE_TYPES hormone_type) {
        this(Ingredient.of(ingredient), Ingredient.of(determiner), determiner.toString().substring(determiner.toString().lastIndexOf(' ') + 1).replace(":", "__"), hormone_type);
    }

    public HormoneResearchRecipeBuilder(Ingredient ingredient, Ingredient determiner, String determinerName, HORMONE_TYPES hormone_type) {
        this.ingredient = ingredient;
        this.determiner = determiner;
        this.determinerName = determinerName;
        this.hormone_type = hormone_type;
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

        pFinishedRecipeConsumer.accept(new HormoneResearchRecipeBuilder.Result(
                pRecipeId,
                this.result,
                this.ingredient,
                this.determiner,
                this.determinerName,
                this.hormone_type,
                this.advancement,
                new ResourceLocation(pRecipeId.getNamespace(),
                        "recipes/" + pRecipeId.getPath())));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final Ingredient ingredient;
        private final Ingredient determiner;
        private final String determinerName;
        private final HORMONE_TYPES hormone_type;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, Item pResult, Ingredient pIngredient, Ingredient pDeterminer, String determinerName, HORMONE_TYPES hormoneType, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId) {
            this.id = pId;
            this.result = pResult;
            this.ingredient = pIngredient;
            this.determiner = pDeterminer;
            this.determinerName = determinerName.substring(determinerName.indexOf(':') + 1);
            this.hormone_type = hormoneType;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            JsonArray jsonArray = new JsonArray();
            jsonArray.add(this.ingredient.toJson());
            jsonArray.add(this.determiner.toJson());
            pJson.add("ingredients", jsonArray);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            pJson.add("result", jsonObject);

            pJson.addProperty("hormone_type", this.hormone_type.ordinal());
        }

        @Override
        public ResourceLocation getId() {
            return new ResourceLocation("meatmaster:hormone_research_of_" + determinerName);
        }

        @Override
        public RecipeSerializer<?> getType() {
            return HormoneResearchRecipe.Serializer.INSTANCE;
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
