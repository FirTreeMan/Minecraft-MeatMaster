package net.firtreeman.meatmaster.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.firtreeman.meatmaster.datagen.ModRecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static net.firtreeman.meatmaster.util.ModItemUtils.COOKED_VARIANTS;

public class AddMeatDropItemModifier extends LootModifier {
    public static final Supplier<Codec<AddMeatDropItemModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst)
                    .and(ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(m -> m.item))
                    .and(Codec.INT.optionalFieldOf("minItems", 1).forGetter(m -> m.itemMin))
                    .and(Codec.INT.optionalFieldOf("maxItems", 1).forGetter(m -> m.itemMax))
                    .and(Codec.BOOL.optionalFieldOf("rare", true).forGetter(m -> m.rare))
                    .apply(inst, AddMeatDropItemModifier::new)));
    private final Item item;
    private final int itemMin;
    private final int itemMax;
    private final boolean rare;

    public AddMeatDropItemModifier(LootItemCondition[] conditionsIn, Item item) {
        this(conditionsIn, item, 0, 1, true);
    }

    public AddMeatDropItemModifier(LootItemCondition[] conditionsIn, Item item, int itemMax) {
        this(conditionsIn, item, 0, itemMax, true);
    }

    public AddMeatDropItemModifier(LootItemCondition[] conditionsIn, Item item, int itemMin, int itemMax) {
        this(conditionsIn, item, itemMin, itemMax, true);
    }

    public AddMeatDropItemModifier(LootItemCondition[] conditionsIn, Item item, boolean rare) {
        this(conditionsIn, item, 0, 1, rare);
    }

    public AddMeatDropItemModifier(LootItemCondition[] conditionsIn, Item item, int itemMin, int itemMax, boolean rare) {
        super(conditionsIn);
        this.item = item;
        this.itemMax = itemMax;
        this.itemMin = itemMin;
        this.rare = rare;
    }

    public static LootItemCondition setResourceLocation(String resourceLocation) {
        return new LootTableIdCondition.Builder(new ResourceLocation(resourceLocation)).build();
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for (LootItemCondition condition: this.conditions)
            if (!condition.test(context)) return generatedLoot;

        boolean onFire = context.getParam(LootContextParams.THIS_ENTITY).isOnFire();
        ItemStack stack = new ItemStack(onFire ? COOKED_VARIANTS.getOrDefault(item, item) : item);

        // instead of dropping more items with Looting, rare drops have an increased chance to drop
        // so the amount dropped should be unchanged with Looting if the drop is rare
        // rare drop chance w/ Looting should already be set with LootItemRandomChanceWithLootingCondition
        int amt = this.rare ? context.getRandom().nextIntBetweenInclusive(0, itemMax + context.getLootingModifier()) : 1;
        stack.setCount(amt);

        generatedLoot.add(new ItemStack(item, amt));

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
