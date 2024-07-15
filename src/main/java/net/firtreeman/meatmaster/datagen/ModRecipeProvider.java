package net.firtreeman.meatmaster.datagen;

import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.block.ModBlocks;
import net.firtreeman.meatmaster.datagen.recipes.IndustrialOvenRecipeBuilder;
import net.firtreeman.meatmaster.datagen.recipes.MeatCompactorRecipeBuilder;
import net.firtreeman.meatmaster.datagen.recipes.MeatRefineryRecipeBuilder;
import net.firtreeman.meatmaster.item.ModItems;
import net.firtreeman.meatmaster.util.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public static final Map<Item, Item> COOKED_VARIANTS = Map.ofEntries(
            Map.entry(ModItems.CREEPER_MEAT.get(), ModItems.COOKED_CREEPER_MEAT.get()),
            Map.entry(ModItems.DOG_LIVER.get(), ModItems.COOKED_DOG_LIVER.get()),
            Map.entry(ModItems.HORSE_MEAT.get(), ModItems.COOKED_HORSE_MEAT.get()),
            Map.entry(ModItems.CAT_MEAT.get(), ModItems.COOKED_CAT_MEAT.get()),
            Map.entry(ModItems.SQUID_MEAT.get(), ModItems.COOKED_SQUID_MEAT.get()),
            Map.entry(ModItems.BAT_WINGS.get(), ModItems.COOKED_BAT_WINGS.get()),
            Map.entry(ModItems.FROG_LEGS.get(), ModItems.COOKED_FROG_LEGS.get()),
            Map.entry(ModItems.SNIFFER_TOES.get(), ModItems.COOKED_SNIFFER_TOES.get()),
            Map.entry(ModItems.PANDA_BALLS.get(), ModItems.COOKED_PANDA_BALLS.get()),
            Map.entry(ModItems.GUARDIAN_HEART.get(), ModItems.COOKED_GUARDIAN_HEART.get()),
            Map.entry(ModItems.ELDER_GUARDIAN_HEART.get(), ModItems.COOKED_ELDER_GUARDIAN_HEART.get())
    );
    public static final Map<Block, Block> COOKED_BLOCK_VARIANTS = Map.ofEntries(
            Map.entry(ModBlocks.CREEPER_MEAT_BLOCK.get(), ModBlocks.COOKED_CREEPER_MEAT_BLOCK.get()),
            Map.entry(ModBlocks.DOG_LIVER_BLOCK.get(), ModBlocks.COOKED_DOG_LIVER_BLOCK.get()),
            Map.entry(ModBlocks.HORSE_MEAT_BLOCK.get(), ModBlocks.COOKED_HORSE_MEAT_BLOCK.get()),
            Map.entry(ModBlocks.CAT_MEAT_BLOCK.get(), ModBlocks.COOKED_CAT_MEAT_BLOCK.get()),
            Map.entry(ModBlocks.SQUID_MEAT_BLOCK.get(), ModBlocks.COOKED_SQUID_MEAT_BLOCK.get()),
            Map.entry(ModBlocks.BAT_WINGS_BLOCK.get(), ModBlocks.COOKED_BAT_WINGS_BLOCK.get()),
            Map.entry(ModBlocks.FROG_LEGS_BLOCK.get(), ModBlocks.COOKED_FROG_LEGS_BLOCK.get()),
            Map.entry(ModBlocks.SNIFFER_TOES_BLOCK.get(), ModBlocks.COOKED_SNIFFER_TOES_BLOCK.get()),
            Map.entry(ModBlocks.PANDA_BALLS_BLOCK.get(), ModBlocks.COOKED_PANDA_BALLS_BLOCK.get()),
            Map.entry(ModBlocks.GUARDIAN_HEART_BLOCK.get(), ModBlocks.COOKED_GUARDIAN_HEART_BLOCK.get()),
            Map.entry(ModBlocks.ELDER_GUARDIAN_HEART_BLOCK.get(), ModBlocks.COOKED_ELDER_GUARDIAN_HEART_BLOCK.get()),

            Map.entry(ModBlocks.BEEF_BLOCK.get(), ModBlocks.COOKED_BEEF_BLOCK.get()),
            Map.entry(ModBlocks.CHICKEN_BLOCK.get(), ModBlocks.COOKED_CHICKEN_BLOCK.get()),
            Map.entry(ModBlocks.COD_BLOCK.get(), ModBlocks.COOKED_COD_BLOCK.get()),
            Map.entry(ModBlocks.MUTTON_BLOCK.get(), ModBlocks.COOKED_MUTTON_BLOCK.get()),
            Map.entry(ModBlocks.PORKCHOP_BLOCK.get(), ModBlocks.COOKED_PORKCHOP_BLOCK.get()),
            Map.entry(ModBlocks.RABBIT_BLOCK.get(), ModBlocks.COOKED_RABBIT_BLOCK.get()),
            Map.entry(ModBlocks.SALMON_BLOCK.get(), ModBlocks.COOKED_SALMON_BLOCK.get())
    );

    public static final Map<Item, Item> REFINABLES = Map.ofEntries(
            Map.entry(ModItems.CREEPER_MEAT.get(), ModItems.GUNPOWDER_CLUMPS.get()),
            Map.entry(ModItems.ENDERMEAT.get(), ModItems.ENDER_SPICE.get()),
            Map.entry(ModItems.DOG_LIVER.get(), ModItems.LIVER_BITS.get()),
            Map.entry(ModItems.COOKED_BLAZE_MEAT.get(), ModItems.BLAZE_PEPPER.get()),
            Map.entry(Items.GUNPOWDER, ModItems.GUNPOWDER_CLUMPS.get()),
            Map.entry(Items.COAL.asItem(), ModItems.BITTER_CRUMBS.get()),
            Map.entry(Items.CHARCOAL.asItem(), ModItems.BITTER_CRUMBS.get())
    );
    public static final Map<Item, Item> COMPACTABLES = Map.ofEntries(
            Map.entry(ModItems.CREEPER_MEAT.get(), ModBlocks.CREEPER_MEAT_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_CREEPER_MEAT.get(), ModBlocks.COOKED_CREEPER_MEAT_BLOCK.get().asItem()),
            Map.entry(ModItems.DOG_LIVER.get(), ModBlocks.DOG_LIVER_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_DOG_LIVER.get(), ModBlocks.COOKED_DOG_LIVER_BLOCK.get().asItem()),
            Map.entry(ModItems.ENDERMEAT.get(), ModBlocks.ENDERMEAT_BLOCK.get().asItem()),
            Map.entry(ModItems.HORSE_MEAT.get(), ModBlocks.HORSE_MEAT_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_HORSE_MEAT.get(), ModBlocks.COOKED_HORSE_MEAT_BLOCK.get().asItem()),
            Map.entry(ModItems.CAT_MEAT.get(), ModBlocks.CAT_MEAT_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_CAT_MEAT.get(), ModBlocks.COOKED_CAT_MEAT_BLOCK.get().asItem()),
            Map.entry(ModItems.SQUID_MEAT.get(), ModBlocks.SQUID_MEAT_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_SQUID_MEAT.get(), ModBlocks.COOKED_SQUID_MEAT_BLOCK.get().asItem()),
            Map.entry(ModItems.BAT_WINGS.get(), ModBlocks.BAT_WINGS_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_BAT_WINGS.get(), ModBlocks.COOKED_BAT_WINGS_BLOCK.get().asItem()),
            Map.entry(ModItems.FROG_LEGS.get(), ModBlocks.FROG_LEGS_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_FROG_LEGS.get(), ModBlocks.COOKED_FROG_LEGS_BLOCK.get().asItem()),
            Map.entry(ModItems.SNIFFER_TOES.get(), ModBlocks.SNIFFER_TOES_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_SNIFFER_TOES.get(), ModBlocks.COOKED_SNIFFER_TOES_BLOCK.get().asItem()),
            Map.entry(ModItems.MOLDY_SNIFFER_TOES.get(), ModBlocks.MOLDY_SNIFFER_TOES_BLOCK.get().asItem()),
            Map.entry(ModItems.PANDA_BALLS.get(), ModBlocks.PANDA_BALLS_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_PANDA_BALLS.get(), ModBlocks.COOKED_PANDA_BALLS_BLOCK.get().asItem()),
            Map.entry(ModItems.GUARDIAN_HEART.get(), ModBlocks.GUARDIAN_HEART_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_GUARDIAN_HEART.get(), ModBlocks.COOKED_GUARDIAN_HEART_BLOCK.get().asItem()),
            Map.entry(ModItems.ELDER_GUARDIAN_HEART.get(), ModBlocks.ELDER_GUARDIAN_HEART_BLOCK.get().asItem()),
            Map.entry(ModItems.COOKED_ELDER_GUARDIAN_HEART.get(), ModBlocks.COOKED_ELDER_GUARDIAN_HEART_BLOCK.get().asItem()),

            Map.entry(Items.BEEF, ModBlocks.BEEF_BLOCK.get().asItem()),
            Map.entry(Items.COOKED_BEEF, ModBlocks.COOKED_BEEF_BLOCK.get().asItem()),
            Map.entry(Items.CHICKEN, ModBlocks.CHICKEN_BLOCK.get().asItem()),
            Map.entry(Items.COOKED_CHICKEN, ModBlocks.COOKED_CHICKEN_BLOCK.get().asItem()),
            Map.entry(Items.COD, ModBlocks.COD_BLOCK.get().asItem()),
            Map.entry(Items.COOKED_COD, ModBlocks.COOKED_COD_BLOCK.get().asItem()),
            Map.entry(Items.MUTTON, ModBlocks.MUTTON_BLOCK.get().asItem()),
            Map.entry(Items.COOKED_MUTTON, ModBlocks.COOKED_MUTTON_BLOCK.get().asItem()),
            Map.entry(Items.PORKCHOP, ModBlocks.PORKCHOP_BLOCK.get().asItem()),
            Map.entry(Items.COOKED_PORKCHOP, ModBlocks.COOKED_PORKCHOP_BLOCK.get().asItem()),
            Map.entry(Items.PUFFERFISH, ModBlocks.PUFFERFISH_BLOCK.get().asItem()),
            Map.entry(Items.RABBIT, ModBlocks.RABBIT_BLOCK.get().asItem()),
            Map.entry(Items.COOKED_RABBIT, ModBlocks.COOKED_RABBIT_BLOCK.get().asItem()),
            Map.entry(Items.ROTTEN_FLESH, ModBlocks.ROTTEN_FLESH_BLOCK.get().asItem()),
            Map.entry(Items.SALMON, ModBlocks.SALMON_BLOCK.get().asItem()),
            Map.entry(Items.COOKED_SALMON, ModBlocks.COOKED_SALMON_BLOCK.get().asItem()),
            Map.entry(Items.TROPICAL_FISH, ModBlocks.TROPICAL_FISH_BLOCK.get().asItem())
    );

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        COOKED_VARIANTS.forEach((uncooked, cooked) -> {
            SimpleCookingRecipeBuilder.smelting(Ingredient.of(uncooked), RecipeCategory.FOOD, cooked, 0.25F, 200)
                    .unlockedBy(getHasName(uncooked), has(uncooked)).save(pWriter, MeatMaster.MOD_ID + ":" + getItemName(cooked) + "_from_smelting");
            SimpleCookingRecipeBuilder.smoking(Ingredient.of(uncooked), RecipeCategory.FOOD, cooked, 0.25F, 100)
                    .unlockedBy(getHasName(uncooked), has(uncooked)).save(pWriter, MeatMaster.MOD_ID + ":" + getItemName(cooked) + "_from_smoking");
            SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(uncooked), RecipeCategory.FOOD, cooked, 0.25F, 600)
                    .unlockedBy(getHasName(uncooked), has(uncooked)).save(pWriter, MeatMaster.MOD_ID + ":" + getItemName(cooked) + "_from_campfire_cooking");
        });
        COOKED_BLOCK_VARIANTS.forEach((uncooked, cooked) -> {
            new IndustrialOvenRecipeBuilder(uncooked.asItem(), cooked.asItem())
                    .unlockedBy(getHasName(uncooked), has(uncooked))
                    .save(pWriter);
        });

        REFINABLES.forEach((ingredient, result) -> {
            new MeatRefineryRecipeBuilder(ingredient, result, 1)
                    .unlockedBy(getHasName(result), has(result))
                    .save(pWriter);
        });
        COMPACTABLES.forEach((item, block) -> {
            new MeatCompactorRecipeBuilder(item, block)
                    .unlockedBy(getHasName(block), has(block))
                    .save(pWriter);
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, block)
                    .pattern("###")
                    .pattern("###")
                    .pattern("###")
                    .define('#', item)
                    .unlockedBy(getHasName(item), has(item))
                    .save(pWriter);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, item, 9)
                    .requires(block)
                    .unlockedBy(getHasName(block), has(block))
                    .save(pWriter);
        });

        new MeatRefineryRecipeBuilder(Tags.Items.STONE, ModItems.TABLE_SALT.get(), 1)
                .unlockedBy(getHasName(Items.COBBLESTONE), has(Tags.Items.STONE))
                .save(pWriter);
        new MeatRefineryRecipeBuilder(ModTags.Items.EXTRA_REFINABLE_MEATS, ModItems.MEAT_RESIDUE.get(), 1)
                .unlockedBy(getHasName(Items.PORKCHOP), has(ModTags.Items.EXTRA_REFINABLE_MEATS))
                .save(pWriter);
        new IndustrialOvenRecipeBuilder(ModTags.Items.MEAT_ITEMS, ModItems.BITTER_CRUMBS.get())
                .unlockedBy(getHasName(Items.PORKCHOP), has(ModTags.Items.MEAT_ITEMS))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MEAT_REFINERY_STATION.get())
                .pattern("IPI")
                .pattern("C C")
                .pattern("CCC")
                .define('I', Items.IRON_INGOT)
                .define('P', Items.PISTON)
                .define('C', Items.COBBLESTONE)
                .unlockedBy(getHasName(Items.PISTON), has(Items.PISTON))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MEAT_COMPACTOR_STATION.get())
                .pattern("OPO")
                .pattern("P P")
                .pattern("OPO")
                .define('O', Items.OBSIDIAN)
                .define('P', Items.PISTON)
                .unlockedBy(getHasName(Items.PISTON), has(Items.PISTON))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.LATHERER_STATION.get())
                .pattern("WIW")
                .pattern(" B ")
                .pattern("WIW")
                .define('W', ItemTags.PLANKS)
                .define('I', Items.IRON_INGOT)
                .define('B', Items.BUCKET)
                .unlockedBy(getHasName(Items.PISTON), has(Items.PISTON))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.INDUSTRIAL_OVEN_STATION.get())
                .pattern("INI")
                .pattern("OQO")
                .pattern("ONO")
                .define('I', Items.IRON_INGOT)
                .define('N', Items.NETHER_BRICK)
                .define('O', Items.OBSIDIAN)
                .define('Q', Items.QUARTZ)
                .unlockedBy(getHasName(Items.QUARTZ), has(Items.QUARTZ))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MEAT_MASHER_STATION.get())
                .pattern("CCC")
                .pattern("P P")
                .pattern("CIC")
                .define('C', Items.COBBLESTONE)
                .define('I', Items.IRON_BLOCK)
                .define('P', Items.PISTON)
                .unlockedBy(getHasName(Items.PISTON), has(Items.PISTON))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.FOOD_TROUGH_STATION.get())
                .pattern("G G")
                .pattern("WBW")
                .pattern("FWF")
                .define('G', Items.GOLD_INGOT)
                .define('W', ItemTags.PLANKS)
                .define('B', Items.BUCKET)
                .define('F', ItemTags.FENCES)
                .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.HORMONE_RESEARCH_STATION.get())
                .pattern("GNG")
                .pattern("ISI")
                .pattern("IDI")
                .define('G', Items.GOLD_BLOCK)
                .define('N', Items.NETHERITE_INGOT)
                .define('I', Items.IRON_BLOCK)
                .define('S', Items.NETHER_STAR)
                .define('D', Items.DIAMOND_BLOCK)
                .unlockedBy(getHasName(Items.NETHER_STAR), has(Items.NETHER_STAR))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SYRINGE_DART.get())
                .pattern("GGG")
                .pattern(" G ")
                .pattern(" F ")
                .define('G', Items.GLASS)
                .define('F', Items.FLINT)
                .unlockedBy(getHasName(Items.GLASS), has(Items.GLASS))
                .unlockedBy(getHasName(Items.FLINT), has(Items.FLINT))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.KEBAB.get())
                .requires(Items.END_ROD)
                .requires(ModTags.Items.VALID_KEBAB_INGREDIENTS)
                .requires(ModTags.Items.VALID_KEBAB_INGREDIENTS)
                .unlockedBy(getHasName(Items.END_ROD), has(Items.END_ROD))
                .save(pWriter);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
      oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
      oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

   protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
      for(ItemLike itemlike : pIngredients) {
         SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike)).save(pFinishedRecipeConsumer, MeatMaster.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
      }

   }
}
