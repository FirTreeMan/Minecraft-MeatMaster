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
import net.firtreeman.meatmaster.block.entity.MeatMasherStationBlockEntity;
import net.firtreeman.meatmaster.recipe.MeatMasherRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class MeatMasherCategory implements IRecipeCategory<MeatMasherRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MeatMaster.MOD_ID, "meat_masher");
    public static final ResourceLocation TEXTURE = new ResourceLocation(MeatMaster.MOD_ID, "textures/gui/meat_masher_station.png");
    public static final RecipeType<MeatMasherRecipe> MEAT_MASHER_RECIPE_TYPE = new RecipeType<>(UID, MeatMasherRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public MeatMasherCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 4, 4, 168, 77);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MEAT_MASHER_STATION.get()));
    }

    @Override
    public RecipeType<MeatMasherRecipe> getRecipeType() {
        return MEAT_MASHER_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.meatmaster.meat_masher_station");
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
    public void setRecipe(IRecipeLayoutBuilder builder, MeatMasherRecipe recipe, IFocusGroup iFocusGroup) {
        ItemStack output = recipe.getOutput();
        ItemStack failOutput = recipe.getFailOutput();
        output.getOrCreateTag().putDouble("JEIToolTipPercentage", 1.0 - MeatMasherStationBlockEntity.MALFUNCTION_CHANCE);
        failOutput.getOrCreateTag().putDouble("JEIToolTipPercentage", MeatMasherStationBlockEntity.MALFUNCTION_CHANCE);

        builder.addSlot(RecipeIngredientRole.INPUT, 46, 31).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 106, 16).addItemStack(output);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 106, 43).addItemStack(failOutput);
    }
}
