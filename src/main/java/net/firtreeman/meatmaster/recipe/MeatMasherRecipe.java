package net.firtreeman.meatmaster.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.block.entity.MeatMasherStationBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class MeatMasherRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ItemStack failOutput;
    private final ResourceLocation id;

    public MeatMasherRecipe(NonNullList<Ingredient> inputItems, ItemStack output, ItemStack failOutput, ResourceLocation id) {
        this.inputItems = inputItems;
        this.output = output;
        this.failOutput = failOutput;
        this.id = id;
    }

    public ItemStack getOutput() {
        return output.copy();
    }

    public ItemStack getFailOutput() {
        return failOutput.copy();
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;

        return inputItems.get(0).test(pContainer.getItem(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return Math.random() > MeatMasherStationBlockEntity.MALFUNCTION_CHANCE ? output.copy() : failOutput.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<MeatMasherRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "meat_masher";
    }

    public static class Serializer implements RecipeSerializer<MeatMasherRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(MeatMaster.MOD_ID, "meat_masher");

        @Override
        public MeatMasherRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));
            ItemStack failOutput = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "fail_result"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new MeatMasherRecipe(inputs, output, failOutput, pRecipeId);
        }

        @Override
        public @Nullable MeatMasherRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            ItemStack failOutput = pBuffer.readItem();

            return new MeatMasherRecipe(inputs, output, failOutput, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, MeatMasherRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());

            for (Ingredient ingredient: pRecipe.getIngredients())
                ingredient.toNetwork(pBuffer);

            pBuffer.writeItemStack(pRecipe.getOutput(), false);
            pBuffer.writeItemStack(pRecipe.getFailOutput(), false);
        }
    }
}
