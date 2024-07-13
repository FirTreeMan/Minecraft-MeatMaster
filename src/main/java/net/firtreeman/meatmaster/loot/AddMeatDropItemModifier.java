package net.firtreeman.meatmaster.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.firtreeman.meatmaster.datagen.ModRecipeProvider;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class AddMeatDropItemModifier extends LootModifier {
    public static final Supplier<Codec<AddMeatDropItemModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst)
                    .and(ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(m -> m.item))
                    .and(Codec.INT.optionalFieldOf("minItems", 1).forGetter(m -> m.itemMin))
                    .and(Codec.INT.optionalFieldOf("maxItems", 1).forGetter(m -> m.itemMax))
                    .apply(inst, AddMeatDropItemModifier::new)));
    private final Item item;
    private final int itemMin;
    private final int itemMax;

    public AddMeatDropItemModifier(LootItemCondition[] conditionsIn, Item item) {
        this(conditionsIn, item, 0, 1);
    }

    public AddMeatDropItemModifier(LootItemCondition[] conditionsIn, Item item, int itemMax) {
        this(conditionsIn, item, 0, itemMax);
    }

    public AddMeatDropItemModifier(LootItemCondition[] conditionsIn, Item item, int itemMin, int itemMax) {
        super(conditionsIn);
        this.item = item;
        this.itemMax = itemMax;
        this.itemMin = itemMin;
    }

    public static LootItemCondition setResourceLocation(String resourceLocation) {
        return new LootTableIdCondition.Builder(new ResourceLocation(resourceLocation)).build();
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for (LootItemCondition condition: this.conditions)
            if (!condition.test(context)) return generatedLoot;

        boolean onFire = context.getParam(LootContextParams.THIS_ENTITY).isOnFire();

        ItemStack stack = new ItemStack(onFire ? ModRecipeProvider.COOKED_VARIANTS.getOrDefault(item, item) : item);
        int amt = context.getRandom().nextIntBetweenInclusive(0, itemMax + context.getLootingModifier());
        stack.setCount(amt);

        generatedLoot.add(new ItemStack(item, amt));

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
