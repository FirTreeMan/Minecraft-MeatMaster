package net.firtreeman.meatmaster.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.block.ModBlocks;
import net.firtreeman.meatmaster.recipe.MeatCompactorRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class MeatCompactorCategory implements IRecipeCategory<MeatCompactorRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MeatMaster.MOD_ID, "meat_compactor");
    public static final ResourceLocation TEXTURE = new ResourceLocation(MeatMaster.MOD_ID, "textures/gui/meat_compactor_station.png");
    public static final RecipeType<MeatCompactorRecipe> MEAT_COMPACTOR_RECIPE_TYPE = new RecipeType<>(UID, MeatCompactorRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public MeatCompactorCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 4, 4, 168, 77);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MEAT_REFINERY_STATION.get()));
    }

    @Override
    public RecipeType<MeatCompactorRecipe> getRecipeType() {
        return MEAT_COMPACTOR_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.meatmaster.meat_compactor_station");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MeatCompactorRecipe recipe, IFocusGroup iFocusGroup) {
        for (int x = 0; x < 3; x++)
                for (int y = 0; y < 3; y++) {
                    // index 0-8 inclusive
                    int index = y * 3 + x;
                    builder.addSlot(RecipeIngredientRole.INPUT, 20 + 20 * x, 11 + 20 * y).addIngredients(recipe.getIngredients().get(index));
                }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 128, 31).addItemStack(recipe.getResultItem(null));
    }
}