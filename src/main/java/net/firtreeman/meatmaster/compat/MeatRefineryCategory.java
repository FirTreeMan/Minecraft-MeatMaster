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
import net.firtreeman.meatmaster.recipe.MeatRefineryRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class MeatRefineryCategory implements IRecipeCategory<MeatRefineryRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MeatMaster.MOD_ID, "meat_refinery");
    public static final ResourceLocation TEXTURE = new ResourceLocation(MeatMaster.MOD_ID, "textures/gui/meat_refinery_station.png");
    public static final RecipeType<MeatRefineryRecipe> MEAT_REFINERY_RECIPE_TYPE = new RecipeType<>(UID, MeatRefineryRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public MeatRefineryCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 4, 4, 168, 77);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MEAT_REFINERY_STATION.get()));
    }

    @Override
    public RecipeType<MeatRefineryRecipe> getRecipeType() {
        return MEAT_REFINERY_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.meatmaster.meat_refinery_station");
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
    public void setRecipe(IRecipeLayoutBuilder builder, MeatRefineryRecipe recipe, IFocusGroup iFocusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 44, 31).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 112, 31).addItemStack(recipe.getResultItem(null));
    }
}
