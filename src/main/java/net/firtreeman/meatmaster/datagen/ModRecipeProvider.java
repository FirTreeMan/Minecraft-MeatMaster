package net.firtreeman.meatmaster.datagen;

import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.block.ModBlocks;
import net.firtreeman.meatmaster.block.entity.HormoneResearchStationBlockEntity;
import net.firtreeman.meatmaster.datagen.recipes.*;
import net.firtreeman.meatmaster.item.ModItems;
import net.firtreeman.meatmaster.util.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

import static net.firtreeman.meatmaster.util.ModItemUtils.*;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {


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

            if (uncooked.getFoodProperties().getNutrition() >= 2)
                new MeatMasherRecipeBuilder(uncooked, ModItems.SAUSAGE.get(), ModItems.MEAT_RESIDUE.get())
                    .unlockedBy(getHasName(uncooked), has(uncooked))
                    .save(pWriter);
            if (cooked.getFoodProperties().getNutrition() >= 2)
                new MeatMasherRecipeBuilder(cooked, ModItems.SAUSAGE.get(), ModItems.MEAT_RESIDUE.get())
                    .unlockedBy(getHasName(cooked), has(cooked))
                    .save(pWriter);
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
                    .save(pWriter, MeatMaster.MOD_ID + ":" + getConversionRecipeName(item, block));
        });

        HormoneResearchStationBlockEntity.DETERMINERS.forEach((determiner, hormoneType) -> {
            new HormoneResearchRecipeBuilder(ModTags.Items.MEAT_BLOCKS, determiner, hormoneType)
                    .unlockedBy(getHasName(determiner), has(determiner))
                    .save(pWriter);
        });

        HormoneResearchStationBlockEntity.HORMONE_BASES.forEach((hormoneType, hormoneBase) -> {
            new HormoneFillRecipeBuilder(hormoneBase.getItem(), hormoneType)
                    .unlockedBy(getHasName(hormoneBase.getItem()), has(hormoneBase.getItem()))
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
                .pattern("PDP")
                .pattern("CPC")
                .define('O', Items.OBSIDIAN)
                .define('P', Items.PISTON)
                .define('D', Items.DIAMOND)
                .define('C', Items.COBBLESTONE)
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
                .pattern("R R")
                .pattern("WBW")
                .pattern("FWF")
                .define('R', Items.REDSTONE)
                .define('W', ItemTags.PLANKS)
                .define('B', Items.BUCKET)
                .define('F', ItemTags.FENCES)
                .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SYRINGE_DART.get(), 8)
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
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.KEBAB.get())
                .requires(Items.END_ROD)
                .requires(ModTags.Items.VALID_KEBAB_INGREDIENTS)
                .requires(ModTags.Items.VALID_KEBAB_INGREDIENTS)
                .requires(ModTags.Items.VALID_KEBAB_INGREDIENTS)
                .unlockedBy(getHasName(Items.END_ROD), has(Items.END_ROD))
                .save(pWriter, MeatMaster.MOD_ID + ":alt_kebab");
    }
}
