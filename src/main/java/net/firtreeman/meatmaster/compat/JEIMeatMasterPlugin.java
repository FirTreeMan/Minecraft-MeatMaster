package net.firtreeman.meatmaster.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.recipe.MeatRefineryRecipe;
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
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<MeatRefineryRecipe> meatRefineryRecipes = recipeManager.getAllRecipesFor(MeatRefineryRecipe.Type.INSTANCE);
        registration.addRecipes(MeatRefineryCategory.MEAT_REFINERY_RECIPE_TYPE, meatRefineryRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(MeatRefineryStationScreen.class, 72, 38, 31, 10);
    }
}
