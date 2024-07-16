package net.firtreeman.meatmaster.block.entity;

import net.firtreeman.meatmaster.block.ModBlockEntities;
import net.firtreeman.meatmaster.block.custom.IndustrialOvenStationBlock;
import net.firtreeman.meatmaster.item.ModItems;
import net.firtreeman.meatmaster.item.custom.HormoneArrowItem;
import net.firtreeman.meatmaster.item.custom.HormoneBaseItem;
import net.firtreeman.meatmaster.recipe.HormoneFillRecipe;
import net.firtreeman.meatmaster.recipe.HormoneResearchRecipe;
import net.firtreeman.meatmaster.recipe.MeatRefineryRecipe;
import net.firtreeman.meatmaster.screen.HormoneResearchStationMenu;
import net.firtreeman.meatmaster.util.HORMONE_TYPES;
import net.firtreeman.meatmaster.util.HormoneUtils;
import net.firtreeman.meatmaster.util.ModTags;
import net.firtreeman.meatmaster.util.SubItemStackHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HormoneResearchStationBlockEntity extends BlockEntity implements MenuProvider {
    public static final int SLOT_COUNT = 5;
    private static final int MEAT_INPUT_SLOT = 0;
    private static final int DETERMINER_INPUT_SLOT = 1;
    private static final int HORMONE_BASE_SLOT = 2;
    private static final int SYRINGE_INPUT_SLOT = 3;
    private static final int HORMONE_OUTPUT_SLOT = 4;

    public static final Map<Item, HORMONE_TYPES> DETERMINERS = Map.ofEntries(
            Map.entry(Items.DIAMOND, HORMONE_TYPES.GROWTH),
            Map.entry(Items.EMERALD, HORMONE_TYPES.BREEDING),
            Map.entry(Items.IRON_INGOT, HORMONE_TYPES.YIELD)
    );

    public static final Map<HORMONE_TYPES, ItemStack> HORMONE_BASES =
            Arrays.stream(HORMONE_TYPES.values()).collect(Collectors.toMap(
                    Function.identity(),
                    s -> HormoneUtils.itemStackOf(ModItems.HORMONE_BASE.get(), s)
            ));

    public static final Map<HORMONE_TYPES, ItemStack> HORMONE_ARROWS =
            Arrays.stream(HORMONE_TYPES.values()).collect(Collectors.toMap(
                    Function.identity(),
                    s -> HormoneUtils.itemStackOf(ModItems.SYRINGE_DART.get(), s)
            ));

    private final ItemStackHandler itemHandler = new ItemStackHandler(SLOT_COUNT) {
        @Override
        public void setStackInSlot(int slot, @NotNull ItemStack stack) {
            super.setStackInSlot(slot, stack);
            switch (slot) {
                case MEAT_INPUT_SLOT -> meatInputItemHandler.setStackInSlot(0, stack);
                case DETERMINER_INPUT_SLOT -> determinerInputItemHandler.setStackInSlot(0, stack);
                case HORMONE_BASE_SLOT -> hormoneBaseItemHandler.setStackInSlot(0, stack);
                case SYRINGE_INPUT_SLOT ->  syringeInputItemHandler.setStackInSlot(0, stack);
                case HORMONE_OUTPUT_SLOT -> hormoneOutputItemHandler.setStackInSlot(0, stack);
            }
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            super.deserializeNBT(nbt);
            meatInputItemHandler.setStackInSlot(0, itemHandler.getStackInSlot(MEAT_INPUT_SLOT));
            hormoneOutputItemHandler.setStackInSlot(0, itemHandler.getStackInSlot(HORMONE_OUTPUT_SLOT));
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case MEAT_INPUT_SLOT -> meatInputItemHandler.isItemValid(slot, stack);
                case DETERMINER_INPUT_SLOT -> determinerInputItemHandler.isItemValid(slot, stack);
                case HORMONE_BASE_SLOT -> hormoneBaseItemHandler.isItemValid(slot, stack);
                case SYRINGE_INPUT_SLOT -> syringeInputItemHandler.isItemValid(slot, stack);
                case HORMONE_OUTPUT_SLOT -> hormoneOutputItemHandler.isItemValid(slot, stack);
                default -> true;
            };
        }
    };
    private final ItemStackHandler meatInputItemHandler = new SubItemStackHandler(itemHandler, MEAT_INPUT_SLOT).validateItems((slot, stack) -> stack.is(ModTags.Items.MEAT_BLOCKS));
    private final ItemStackHandler determinerInputItemHandler = new SubItemStackHandler(itemHandler, DETERMINER_INPUT_SLOT) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return DETERMINERS.containsKey(stack.getItem());
        }
    };
    private final ItemStackHandler hormoneBaseItemHandler = new SubItemStackHandler(itemHandler, HORMONE_BASE_SLOT).validateItems((slot, stack) -> stack.getItem() instanceof HormoneBaseItem);
    private final ItemStackHandler syringeInputItemHandler = new SubItemStackHandler(itemHandler, SYRINGE_INPUT_SLOT).validateItems((slot, stack) -> stack.is(ModItems.SYRINGE_DART.get()) && HormoneUtils.getHormone(stack) == HORMONE_TYPES.NONE);
    private final ItemStackHandler hormoneOutputItemHandler = new SubItemStackHandler(itemHandler, HORMONE_OUTPUT_SLOT).outputOnly();

    protected final ContainerData data;
    private int research_progress = 0;
    private int max_research_progress = 6912;
    private int fill_progress = 0;
    private int max_fill_progress = 60;
    private HORMONE_TYPES hormone = HORMONE_TYPES.NONE;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> meatInputLazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> determinerInputLazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> hormoneBaseLazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> syringeInputLazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> hormoneOutputLazyItemHandler = LazyOptional.empty();

    public HormoneResearchStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.HORMONE_RESEARCH_SBE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> HormoneResearchStationBlockEntity.this.research_progress;
                    case 1 -> HormoneResearchStationBlockEntity.this.max_research_progress;
                    case 2 -> HormoneResearchStationBlockEntity.this.fill_progress;
                    case 3 -> HormoneResearchStationBlockEntity.this.max_fill_progress;
                    case 4 -> HormoneResearchStationBlockEntity.this.hormone.ordinal();
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                 switch (pIndex) {
                    case 0 -> HormoneResearchStationBlockEntity.this.research_progress = pValue;
                    case 1 -> HormoneResearchStationBlockEntity.this.max_research_progress = pValue;
                    case 2 -> HormoneResearchStationBlockEntity.this.fill_progress = pValue;
                    case 3 -> HormoneResearchStationBlockEntity.this.max_fill_progress = pValue;
                    case 4 -> HormoneResearchStationBlockEntity.this.hormone = HORMONE_TYPES.values()[pValue];
                };
            }

            @Override
            public int getCount() {
                return SLOT_COUNT;
            }
        };
    }

    public boolean hasBase() {
        return this.itemHandler.getStackInSlot(HORMONE_BASE_SLOT).getItem() instanceof HormoneBaseItem;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (side == null) return lazyItemHandler.cast();

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return switch (side) {
                case DOWN -> hormoneOutputLazyItemHandler.cast();
                case SOUTH -> determinerInputLazyItemHandler.cast();
                case WEST -> hormoneBaseLazyItemHandler.cast();
                case EAST -> syringeInputLazyItemHandler.cast();
                default -> meatInputLazyItemHandler.cast();
            };
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        meatInputLazyItemHandler = LazyOptional.of(() -> meatInputItemHandler);
        determinerInputLazyItemHandler = LazyOptional.of(() -> determinerInputItemHandler);
        hormoneBaseLazyItemHandler = LazyOptional.of(() -> hormoneBaseItemHandler);
        syringeInputLazyItemHandler = LazyOptional.of(() -> syringeInputItemHandler);
        hormoneOutputLazyItemHandler = LazyOptional.of(() -> hormoneOutputItemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        meatInputLazyItemHandler.invalidate();
        determinerInputLazyItemHandler.invalidate();
        hormoneBaseLazyItemHandler.invalidate();
        syringeInputLazyItemHandler.invalidate();
        hormoneOutputLazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(SLOT_COUNT);
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.meatmaster.hormone_research_station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new HormoneResearchStationMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("hormone_research_station.research_progress", research_progress);
        pTag.putInt("hormone_research_station.max_research_progress", max_research_progress);
        pTag.putInt("hormone_research_station.fill_progress", fill_progress);
        pTag.putInt("hormone_research_station.max_fill_progress", max_fill_progress);
        pTag.putInt("hormone_research_station.hormone", hormone.ordinal());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        research_progress = pTag.getInt("hormone_research_station.research_progress");
        max_research_progress = pTag.getInt("hormone_research_station.max_research_progress");
        fill_progress = pTag.getInt("hormone_research_station.fill_progress");
        max_fill_progress = pTag.getInt("hormone_research_station.max_fill_progress");
        hormone = HORMONE_TYPES.values()[pTag.getInt("hormone_research_station.hormone")];
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        boolean flag = false;

        checkHormone();
        if (hasResearchRecipe()) {
            increaseResearchProgress();
            setState(pLevel, pPos, pState, true);
            flag = true;

            if (researchProgressFinished()) {
                makeResearchItem();
                resetResearchProgress();
            }
        }
        if (hasFillRecipe()) {
            increaseFillProgress();
            setState(pLevel, pPos, pState, true);
            flag = true;

            if (fillProgressFinished()) {
                makeFillItem();
                resetFillProgress();
            }
        } else {
            resetFillProgress();
        }

        if (!flag && pState.getValue(IndustrialOvenStationBlock.LIT))
            setState(pLevel, pPos, pState, false);
    }

    private void setState(Level pLevel, BlockPos pPos, BlockState pState, boolean isLit) {
        pState = pState.setValue(IndustrialOvenStationBlock.LIT, isLit);
        setChanged(pLevel, pPos, pState);
        pLevel.setBlock(pPos, pState, 3);
    }

    private boolean hasMeat() {
        return this.itemHandler.getStackInSlot(MEAT_INPUT_SLOT).is(ModTags.Items.MEAT_BLOCKS);
    }

    private boolean hasSyringes() {
        return this.itemHandler.getStackInSlot(SYRINGE_INPUT_SLOT).is(ModItems.SYRINGE_DART.get());
    }

    private void checkHormone() {
        HORMONE_TYPES baseHormone = HormoneUtils.getHormone(this.itemHandler.getStackInSlot(HORMONE_BASE_SLOT));
        if (baseHormone != HORMONE_TYPES.NONE)
            this.hormone = baseHormone;
        else this.hormone = DETERMINERS.getOrDefault(this.itemHandler.getStackInSlot(DETERMINER_INPUT_SLOT).getItem(), HORMONE_TYPES.NONE);
    }

    private boolean hasResearchRecipe() {
        Optional<HormoneResearchRecipe> recipe = getCurrentResearchRecipe();

        if (recipe.isEmpty()) return false;

        ItemStack result = recipe.get().getResultItem(null);

        return hasMeat();
    }

     private Optional<HormoneResearchRecipe> getCurrentResearchRecipe() {
        SimpleContainer inventory = new SimpleContainer(SLOT_COUNT);

        for (int i = 0; i < SLOT_COUNT; i++)
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));

        return this.level.getRecipeManager().getRecipeFor(HormoneResearchRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean hasFillRecipe() {
        Optional<HormoneFillRecipe> recipe = getCurrentFillRecipe();

        if (recipe.isEmpty()) return false;

        ItemStack result = recipe.get().getResultItem(null);

        return canAddStackToFillOutput(result);
    }

    private Optional<HormoneFillRecipe> getCurrentFillRecipe() {
        SimpleContainer inventory = new SimpleContainer(SLOT_COUNT);

        for (int i = 0; i < SLOT_COUNT; i++)
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));

        return this.level.getRecipeManager().getRecipeFor(HormoneFillRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean canAddStackToFillOutput(ItemStack result) {
        ItemStack output = this.itemHandler.getStackInSlot(HORMONE_OUTPUT_SLOT);

        return (output.isEmpty() || (output.is(result.getItem()) && HormoneUtils.getHormone(output) == HormoneUtils.getHormone(result)))
            // can add amount?
            && output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    private void increaseResearchProgress() {
        this.itemHandler.extractItem(MEAT_INPUT_SLOT, 1, false);
        research_progress++;
    }

    private void increaseFillProgress() {
        fill_progress++;
    }

    private boolean researchProgressFinished() {
        return research_progress >= max_research_progress;
    }

    private boolean fillProgressFinished() {
        return fill_progress >= max_fill_progress;
    }

    private void makeResearchItem() {
        Optional<HormoneResearchRecipe> recipe = getCurrentResearchRecipe();

        if (recipe.isEmpty()) return;

        ItemStack result = recipe.get().getResultItem(null);

        this.itemHandler.setStackInSlot(HORMONE_BASE_SLOT, result);
    }

    private void makeFillItem() {
        Optional<HormoneFillRecipe> recipe = getCurrentFillRecipe();

        if (recipe.isEmpty()) return;

        ItemStack result = recipe.get().getResultItem(null);

        this.itemHandler.extractItem(SYRINGE_INPUT_SLOT, 1, false);
        this.itemHandler.setStackInSlot(HORMONE_OUTPUT_SLOT, HormoneUtils.itemStackOf(result.getItem(), result.getCount() + this.itemHandler.getStackInSlot(HORMONE_OUTPUT_SLOT).getCount(), HormoneUtils.getHormone(result)));
    }

    private void resetResearchProgress() {
        research_progress = 0;
    }

    private void resetFillProgress() {
        fill_progress = 0;
    }
}
