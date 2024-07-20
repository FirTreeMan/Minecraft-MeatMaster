package net.firtreeman.meatmaster.datagen;

import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.item.ModItems;
import net.firtreeman.meatmaster.loot.AddMeatDropItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, MeatMaster.MOD_ID);
    }

    @Override
    protected void start() {
        add("creeper_meat", new AddMeatDropItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/creeper")).build(),
                LootItemRandomChanceCondition.randomChance(1.0F).build(),
        }, ModItems.CREEPER_MEAT.get()));
        add("dog_liver", new AddMeatDropItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/wolf")).build(),
                LootItemRandomChanceCondition.randomChance(1.0F).build(),
        }, ModItems.DOG_LIVER.get(), 2));
        add("endermeat", new AddMeatDropItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/enderman")).build(),
//                LootItemRandomChanceCondition.randomChance(0.1F).build(),
                LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1F, 0.03F).build(),
        }, ModItems.ENDERMEAT.get()));
        add("horse_meat", new AddMeatDropItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/horse")).build(),
                LootItemRandomChanceCondition.randomChance(1.0F).build(),
        }, ModItems.HORSE_MEAT.get(), 1, 3));
        add("cooked_blaze_meat", new AddMeatDropItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/blaze")).build(),
                LootItemRandomChanceCondition.randomChance(1.0F).build(),
        }, ModItems.COOKED_BLAZE_MEAT.get()));
        add("cat_meat", new AddMeatDropItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/cat")).build(),
                LootItemRandomChanceCondition.randomChance(1.0F).build(),
        }, ModItems.CAT_MEAT.get()));
        add("squid_meat", new AddMeatDropItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/squid")).build(),
                LootItemRandomChanceCondition.randomChance(1.0F).build(),
        }, ModItems.SQUID_MEAT.get(), 2));
        add("bat_wings", new AddMeatDropItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/bat")).build(),
                LootItemRandomChanceCondition.randomChance(1.0F).build(),
        }, ModItems.BAT_WINGS.get()));
        add("frog_legs", new AddMeatDropItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/frog")).build(),
                LootItemRandomChanceCondition.randomChance(1.0F).build(),
        }, ModItems.FROG_LEGS.get()));
        add("sniffer_toes", new AddMeatDropItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/sniffer")).build(),
                LootItemRandomChanceCondition.randomChance(1.0F).build(),
        }, ModItems.SNIFFER_TOES.get(), 2));
        add("moldy_sniffer_toes", new AddMeatDropItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/sniffer")).build(),
                LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.2F, 0.03F).build(),
        }, ModItems.MOLDY_SNIFFER_TOES.get(), false));
        add("panda_balls", new AddMeatDropItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/panda")).build(),
                LootItemRandomChanceCondition.randomChance(1.0F).build(),
        }, ModItems.PANDA_BALLS.get()));
        add("guardian_heart", new AddMeatDropItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/guardian")).build(),
                LootItemRandomChanceCondition.randomChance(1.0F).build(),
        }, ModItems.GUARDIAN_HEART.get()));
        add("elder_guardian_heart", new AddMeatDropItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/elder_guardian")).build(),
                LootItemRandomChanceCondition.randomChance(1.0F).build(),
        }, ModItems.ELDER_GUARDIAN_HEART.get()));
    }
}
