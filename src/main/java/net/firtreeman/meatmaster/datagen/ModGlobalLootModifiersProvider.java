package net.firtreeman.meatmaster.datagen;

import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.item.ModItems;
import net.firtreeman.meatmaster.loot.AddItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, MeatMaster.MOD_ID);
    }

    @Override
    protected void start() {
        add("creeper_meat", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/creeper")).build(),
                LootItemRandomChanceCondition.randomChance(0.5F).build(),
        }, ModItems.CREEPER_MEAT.get()));
        add("dog_liver", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/wolf")).build(),
                LootItemRandomChanceCondition.randomChance(1.0F).build(),
        }, ModItems.DOG_LIVER.get(), 0, 2));
        add("endermeat", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/enderman")).build(),
                LootItemRandomChanceCondition.randomChance(0.1F).build(),
        }, ModItems.ENDERMEAT.get()));
        add("horse_meat", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/horse")).build(),
                LootItemRandomChanceCondition.randomChance(1.0F).build(),
        }, ModItems.HORSE_MEAT.get(), 1, 3));
        add("cooked_blaze_meat", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/blaze")).build(),
                LootItemRandomChanceCondition.randomChance(0.5F).build(),
        }, ModItems.COOKED_BLAZE_MEAT.get()));
        add("cat_meat", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/cat")).build(),
                LootItemRandomChanceCondition.randomChance(1.0F).build(),
        }, ModItems.CAT_MEAT.get()));
        add("squid_meat", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/squid")).build(),
                LootItemRandomChanceCondition.randomChance(1.0F).build(),
        }, ModItems.SQUID_MEAT.get(), 1, 2));
    }
}
