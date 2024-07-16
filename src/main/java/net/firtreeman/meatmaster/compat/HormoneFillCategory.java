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
import net.firtreeman.meatmaster.recipe.HormoneFillRecipe;
import net.firtreeman.meatmaster.util.HormoneUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class HormoneFillCategory implements IRecipeCategory<HormoneFillRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MeatMaster.MOD_ID, "hormone_fill");
    public static final ResourceLocation TEXTURE = new ResourceLocation(MeatMaster.MOD_ID, "textures/gui/hormone_research_station.png");
    public static final RecipeType<HormoneFillRecipe> HORMONE_FILL_RECIPE_TYPE = new RecipeType<>(UID, HormoneFillRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public HormoneFillCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 4, 4, 168, 77);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.HORMONE_RESEARCH_STATION.get()));
    }

    @Override
    public RecipeType<HormoneFillRecipe> getRecipeType() {
        return HORMONE_FILL_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.meatmaster.hormone_research_station");
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
    public void setRecipe(IRecipeLayoutBuilder builder, HormoneFillRecipe recipe, IFocusGroup iFocusGroup) {
        builder.addSlot(RecipeIngredientRole.CATALYST, 75, 20).addItemStack(HormoneUtils.setHormone(recipe.getIngredients().get(0).getItems()[0], recipe.getHormoneType()));
        builder.addSlot(RecipeIngredientRole.INPUT, 24, 50).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 126, 50).addItemStack(HormoneUtils.setHormone(recipe.getResultItem(null), recipe.getHormoneType()));
    }
}
