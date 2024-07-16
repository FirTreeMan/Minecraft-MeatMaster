package net.firtreeman.meatmaster.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.item.ModItems;
import net.firtreeman.meatmaster.util.HORMONE_TYPES;
import net.firtreeman.meatmaster.util.HormoneUtils;
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

public class HormoneFillRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final HORMONE_TYPES hormoneType;
    private final ItemStack output;
    private final ResourceLocation id;

    public HormoneFillRecipe(NonNullList<Ingredient> inputItems, ItemStack output, ResourceLocation id) {
        this.inputItems = inputItems;
        this.hormoneType = HormoneUtils.getHormone(output);
        this.output = output;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;

        return inputItems.get(0).test(pContainer.getItem(2)) &&
                HormoneUtils.getHormone(pContainer.getItem(2)) == hormoneType &&
                inputItems.get(1).test(pContainer.getItem(3)) &&
                HormoneUtils.getHormone(pContainer.getItem(3)) == HORMONE_TYPES.NONE;
    }

    public HORMONE_TYPES getHormoneType() {
        return hormoneType;
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
        return output.copy();
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

    public static class Type implements RecipeType<HormoneFillRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "hormone_fill";
    }

    public static class Serializer implements RecipeSerializer<HormoneFillRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(MeatMaster.MOD_ID, "hormone_fill");

        @Override
        public HormoneFillRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));
            HORMONE_TYPES hormoneType = HORMONE_TYPES.values()[pSerializedRecipe.get("hormone_type").getAsInt()];
            HormoneUtils.setHormone(output, hormoneType);

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);
            System.out.println(ingredients.get(0));
            System.out.println(ingredients.get(1));

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new HormoneFillRecipe(inputs, output, pRecipeId);
        }

        @Override
        public @Nullable HormoneFillRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            HORMONE_TYPES hormoneType = HORMONE_TYPES.values()[pBuffer.readInt()];
            ItemStack output = pBuffer.readItem();

            return new HormoneFillRecipe(inputs, output, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, HormoneFillRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());

            for (Ingredient ingredient: pRecipe.getIngredients())
                ingredient.toNetwork(pBuffer);

            pBuffer.writeInt(pRecipe.hormoneType.ordinal());
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}
