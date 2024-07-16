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
import net.firtreeman.meatmaster.recipe.IndustrialOvenRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class IndustrialOvenCategory implements IRecipeCategory<IndustrialOvenRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MeatMaster.MOD_ID, "industrial_oven");
    public static final ResourceLocation TEXTURE = new ResourceLocation(MeatMaster.MOD_ID, "textures/gui/industrial_oven_station.png");
    public static final RecipeType<IndustrialOvenRecipe> INDUSTRIAL_OVEN_RECIPE_TYPE = new RecipeType<>(UID, IndustrialOvenRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public IndustrialOvenCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 4, 4, 168, 77);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.INDUSTRIAL_OVEN_STATION.get()));
    }

    @Override
    public RecipeType<IndustrialOvenRecipe> getRecipeType() {
        return INDUSTRIAL_OVEN_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.meatmaster.industrial_oven_station");
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
    public void setRecipe(IRecipeLayoutBuilder builder, IndustrialOvenRecipe recipe, IFocusGroup iFocusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 12).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 31).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 50).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 62, 31).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 122, 31).addItemStack(recipe.getResultItem(null));
    }
}
