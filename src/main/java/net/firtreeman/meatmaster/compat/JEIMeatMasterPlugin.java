package net.firtreeman.meatmaster.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.recipe.IndustrialOvenRecipe;
import net.firtreeman.meatmaster.recipe.MeatCompactorRecipe;
import net.firtreeman.meatmaster.recipe.MeatMasherRecipe;
import net.firtreeman.meatmaster.recipe.MeatRefineryRecipe;
import net.firtreeman.meatmaster.screen.IndustrialOvenStationScreen;
import net.firtreeman.meatmaster.screen.MeatCompactorStationScreen;
import net.firtreeman.meatmaster.screen.MeatMasherStationScreen;
import net.firtreeman.meatmaster.screen.MeatRefineryStationScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIMeatMasterPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MeatMaster.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new MeatRefineryCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MeatCompactorCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new IndustrialOvenCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MeatMasherCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<MeatRefineryRecipe> meatRefineryRecipes = recipeManager.getAllRecipesFor(MeatRefineryRecipe.Type.INSTANCE);
        registration.addRecipes(MeatRefineryCategory.MEAT_REFINERY_RECIPE_TYPE, meatRefineryRecipes);

        List<MeatCompactorRecipe> meatCompactorRecipes = recipeManager.getAllRecipesFor(MeatCompactorRecipe.Type.INSTANCE);
        registration.addRecipes(MeatCompactorCategory.MEAT_COMPACTOR_RECIPE_TYPE, meatCompactorRecipes);

        List<IndustrialOvenRecipe> industrialOvenRecipes = recipeManager.getAllRecipesFor(IndustrialOvenRecipe.Type.INSTANCE);
        registration.addRecipes(IndustrialOvenCategory.INDUSTRIAL_OVEN_RECIPE_TYPE, industrialOvenRecipes);

        List<MeatMasherRecipe> meatMasherRecipes = recipeManager.getAllRecipesFor(MeatMasherRecipe.Type.INSTANCE);
        registration.addRecipes(MeatMasherCategory.MEAT_MASHER_RECIPE_TYPE, meatMasherRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(MeatRefineryStationScreen.class, 72, 38, 31, 10, MeatRefineryCategory.MEAT_REFINERY_RECIPE_TYPE);
        registration.addRecipeClickArea(MeatCompactorStationScreen.class, 82, 35, 42, 16, MeatCompactorCategory.MEAT_COMPACTOR_RECIPE_TYPE);
        registration.addRecipeClickArea(IndustrialOvenStationScreen.class, 89, 34, 24, 17, IndustrialOvenCategory.INDUSTRIAL_OVEN_RECIPE_TYPE);
        registration.addRecipeClickArea(MeatMasherStationScreen.class, 74, 35, 22, 17, MeatMasherCategory.MEAT_MASHER_RECIPE_TYPE);
    }
}
