package net.firtreeman.meatmaster.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.RandomSequence;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class AddItemModifier extends LootModifier {
    public static final Supplier<Codec<AddItemModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst)
                    .and(ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(m -> m.item))
                    .and(Codec.INT.optionalFieldOf("minItems", 1).forGetter(m -> m.itemMin))
                    .and(Codec.INT.optionalFieldOf("maxItems", 1).forGetter(m -> m.itemMax))
                    .apply(inst, AddItemModifier::new)));
    private final Item item;
    private final int itemMin;
    private final int itemMax;

    public AddItemModifier(LootItemCondition[] conditionsIn, Item item) {
        this(conditionsIn, item, 1, 1);
    }

    public AddItemModifier(LootItemCondition[] conditionsIn, Item item, int itemMin, int itemMax) {
        super(conditionsIn);
        this.item = item;
        this.itemMin = itemMin;
        this.itemMax = itemMax;
    }

    public static LootItemCondition setResourceLocation(String resourceLocation) {
        return new LootTableIdCondition.Builder(new ResourceLocation(resourceLocation)).build();
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for (LootItemCondition condition: this.conditions)
            if (!condition.test(context)) return generatedLoot;

        int amt = context.getRandom().nextIntBetweenInclusive(itemMin, itemMax);
        generatedLoot.add(new ItemStack(item, amt));

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
