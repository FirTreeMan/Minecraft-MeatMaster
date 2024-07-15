package net.firtreeman.meatmaster.block.entity;

import net.firtreeman.meatmaster.block.ModBlockEntities;
import net.firtreeman.meatmaster.block.custom.MeatRefineryStationBlock;
import net.firtreeman.meatmaster.datagen.ModRecipeProvider;
import net.firtreeman.meatmaster.recipe.MeatCompactorRecipe;
import net.firtreeman.meatmaster.screen.MeatCompactorStationMenu;
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
import net.minecraft.world.item.ItemStack;
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

import java.util.Optional;

public class MeatCompactorStationBlockEntity extends BlockEntity implements MenuProvider {
    public static final int SLOT_COUNT = 10;
    private static final int INPUT_SLOT_MAX = 9;
    private static final int OUTPUT_SLOT = 9;

    private int lowestCountSlot = -1;

    private final ItemStackHandler itemHandler = new ItemStackHandler(SLOT_COUNT) {
        @Override
        public void setStackInSlot(int slot, @NotNull ItemStack stack) {
            super.setStackInSlot(slot, stack);
            if (slot < INPUT_SLOT_MAX)
                inputItemHandler.setStackInSlot(slot, stack);
            else outputItemHandler.setStackInSlot(0, stack);
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            super.deserializeNBT(nbt);
            for (int i = 0; i < INPUT_SLOT_MAX; i++)
                inputItemHandler.setStackInSlot(i, itemHandler.getStackInSlot(i));
            outputItemHandler.setStackInSlot(0, itemHandler.getStackInSlot(OUTPUT_SLOT));
        }
    };
    private final ItemStackHandler inputItemHandler = new ItemStackHandler(9) {
        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return itemHandler.insertItem(slot, stack, simulate);
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return itemHandler.extractItem(slot, amount, simulate);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return ModRecipeProvider.COMPACTABLES.keySet().stream().anyMatch(stack::is);
        }
    };
    private final ItemStackHandler inputCapabilityItemHandler = new ItemStackHandler(1) {
        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return inputItemHandler.insertItem(lowestCountSlot, stack, simulate);
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return inputItemHandler.extractItem(lowestCountSlot, amount, simulate);
        }
    };
    private final ItemStackHandler outputItemHandler = new SubItemStackHandler(itemHandler, OUTPUT_SLOT).outputOnly();

    protected final ContainerData data;
    private int progress = 0;
    private int max_progress = 100;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> inputLazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> inputCapabilityLazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> outputLazyItemHandler = LazyOptional.empty();

    public MeatCompactorStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MEAT_COMPACTOR_SBE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> MeatCompactorStationBlockEntity.this.progress;
                    case 1 -> MeatCompactorStationBlockEntity.this.max_progress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                 switch (pIndex) {
                    case 0 -> MeatCompactorStationBlockEntity.this.progress = pValue;
                    case 1 -> MeatCompactorStationBlockEntity.this.max_progress = pValue;
                };
            }

            @Override
            public int getCount() {
                return SLOT_COUNT;
            }
        };
    }

    public void calcLowestCountSlot() {
        int lowestCount = Integer.MAX_VALUE;
        int lowestIndex = 0;
        for (int i = 0; i < INPUT_SLOT_MAX; i++){
            int itemCount = itemHandler.getStackInSlot(i).getCount();
            if (itemCount < lowestCount) {
                lowestCount = itemCount;
                lowestIndex = i;
                if (lowestCount == 0) break;
            }
        }
        if (lowestCountSlot != lowestIndex) {
            lowestCountSlot = lowestIndex;
            inputCapabilityItemHandler.setStackInSlot(0, inputItemHandler.getStackInSlot(lowestCountSlot));
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (side == null) return lazyItemHandler.cast();

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == Direction.UP)
                return inputCapabilityLazyItemHandler.cast();
            return outputLazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        inputLazyItemHandler = LazyOptional.of(() -> inputItemHandler);
        inputCapabilityLazyItemHandler = LazyOptional.of(() -> inputCapabilityItemHandler);
        outputLazyItemHandler = LazyOptional.of(() -> outputItemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        inputLazyItemHandler.invalidate();
        inputCapabilityLazyItemHandler.invalidate();
        outputLazyItemHandler.invalidate();
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
        return Component.translatable("block.meatmaster.meat_compactor_station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new MeatCompactorStationMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("meat_compactor_station.progress", progress);
        pTag.putInt("meat_compactor_station.max_progress", max_progress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("meat_compactor_station.progress");
        max_progress = pTag.getInt("meat_compactor_station.max_progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (hasRecipe()) {
            increaseProgress();
            setState(pLevel, pPos, pState, true);

            if (progressFinished()) {
                makeItem();
                resetProgress();
            }
        } else {
            resetProgress();
            if (pState.getValue(MeatRefineryStationBlock.LIT))
                setState(pLevel, pPos, pState, false);
        }
        calcLowestCountSlot();
    }

    private void setState(Level pLevel, BlockPos pPos, BlockState pState, boolean isLit) {
        pState = pState.setValue(MeatRefineryStationBlock.LIT, isLit);
        setChanged(pLevel, pPos, pState);
        pLevel.setBlock(pPos, pState, 3);
    }

    private boolean hasRecipe() {
        Optional<MeatCompactorRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) return false;

        ItemStack result = recipe.get().getResultItem(null);

        return canAddStackToOutput(result);
    }

    private Optional<MeatCompactorRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(SLOT_COUNT);

        for (int i = 0; i < SLOT_COUNT; i++)
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));

        return this.level.getRecipeManager().getRecipeFor(MeatCompactorRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean canAddStackToOutput(ItemStack result) {
        // can add item?
        return (this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(result.getItem()))
            // can add amount?
            && this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount() <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private void increaseProgress() {
        progress++;
    }

    private boolean progressFinished() {
        return progress >= max_progress;
    }

    private void makeItem() {
        Optional<MeatCompactorRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;

        ItemStack result = recipe.get().getResultItem(null);

        for (int i = 0; i < INPUT_SLOT_MAX; i++)
            this.itemHandler.extractItem(i, 1, false);
        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(), result.getCount() + this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount()));
    }

    private void resetProgress() {
        progress = 0;
    }
}
