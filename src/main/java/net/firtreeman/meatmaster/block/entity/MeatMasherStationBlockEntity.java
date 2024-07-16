package net.firtreeman.meatmaster.block.entity;

import net.firtreeman.meatmaster.block.ModBlockEntities;
import net.firtreeman.meatmaster.block.custom.MeatRefineryStationBlock;
import net.firtreeman.meatmaster.item.ModFoods;
import net.firtreeman.meatmaster.item.ModItems;
import net.firtreeman.meatmaster.recipe.MeatMasherRecipe;
import net.firtreeman.meatmaster.recipe.MeatRefineryRecipe;
import net.firtreeman.meatmaster.screen.MeatMasherStationMenu;
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
import net.minecraft.world.food.FoodProperties;
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

public class MeatMasherStationBlockEntity extends BlockEntity implements MenuProvider {
    public static final int SLOT_COUNT = 3;
    private static final int INPUT_SLOT = 0;
    private static final int SAUSAGE_OUTPUT_SLOT = 1;
    private static final int RESIDUE_OUTPUT_SLOT = 2;

    public static final double MALFUNCTION_CHANCE = 0.1;


    private final ItemStackHandler itemHandler = new ItemStackHandler(SLOT_COUNT) {
        @Override
        public void setStackInSlot(int slot, @NotNull ItemStack stack) {
            super.setStackInSlot(slot, stack);
            switch (slot) {
                case INPUT_SLOT -> inputItemHandler.setStackInSlot(0, stack);
                case SAUSAGE_OUTPUT_SLOT -> outputItemHandler.setStackInSlot(0, stack);
                case RESIDUE_OUTPUT_SLOT -> outputItemHandler.setStackInSlot(1, stack);
            }
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            super.deserializeNBT(nbt);
            inputItemHandler.setStackInSlot(0, itemHandler.getStackInSlot(INPUT_SLOT));
            outputItemHandler.setStackInSlot(0, itemHandler.getStackInSlot(SAUSAGE_OUTPUT_SLOT));
            outputItemHandler.setStackInSlot(1, itemHandler.getStackInSlot(RESIDUE_OUTPUT_SLOT));
        }
    };
    private final ItemStackHandler inputItemHandler = new SubItemStackHandler(itemHandler, INPUT_SLOT).validateItems(
            (slot, stack) ->
                stack.isEdible() &&
                stack.getItem().isEdible() &&
                stack.getFoodProperties(null).isMeat() &&
                stack.getFoodProperties(null).getNutrition() >= 2
    );
    private final ItemStackHandler outputItemHandler = new ItemStackHandler(2) {
        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return itemHandler.insertItem(INPUT_SLOT + slot + 1, stack, simulate);
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return itemHandler.extractItem(INPUT_SLOT + slot + 1, amount, simulate);
        }
    };

    protected final ContainerData data;
    private int progress = 0;
    private int max_progress = 30;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> inputLazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> outputLazyItemHandler = LazyOptional.empty();

    public MeatMasherStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MEAT_MASHER_SBE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> MeatMasherStationBlockEntity.this.progress;
                    case 1 -> MeatMasherStationBlockEntity.this.max_progress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                 switch (pIndex) {
                    case 0 -> MeatMasherStationBlockEntity.this.progress = pValue;
                    case 1 -> MeatMasherStationBlockEntity.this.max_progress = pValue;
                };
            }

            @Override
            public int getCount() {
                return SLOT_COUNT;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (side == null) return lazyItemHandler.cast();

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side != Direction.DOWN)
                return inputLazyItemHandler.cast();
            return outputLazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        inputLazyItemHandler = LazyOptional.of(() -> inputItemHandler);
        outputLazyItemHandler = LazyOptional.of(() -> outputItemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        inputLazyItemHandler.invalidate();
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
        return Component.translatable("block.meatmaster.meat_masher_station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new MeatMasherStationMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("MEAT_MASHER_station.progress", progress);
        pTag.putInt("MEAT_MASHER_station.max_progress", max_progress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("MEAT_MASHER_station.progress");
        max_progress = pTag.getInt("MEAT_MASHER_station.max_progress");
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
    }

    private void setState(Level pLevel, BlockPos pPos, BlockState pState, boolean isLit) {
        pState = pState.setValue(MeatRefineryStationBlock.LIT, isLit);
        setChanged(pLevel, pPos, pState);
        pLevel.setBlock(pPos, pState, 3);
    }

    private boolean hasRecipe() {
        Optional<MeatMasherRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) return false;

        int resultCount = recipe.get().getResultItem(null).getCount();

        return canAddStackToOutput(resultCount, recipe.get().getOutput(), recipe.get().getFailOutput());
    }

//    private ItemStack sausageOutput(ItemStack input) {
//        FoodProperties foodProperties = input.getFoodProperties(null);
//
//        assert input.isEdible() && foodProperties.isMeat() && foodProperties.getNutrition() >= 2;
//
//        int nutrition = foodProperties.getNutrition();
//        int sausageOutput = nutrition / SAUSAGE_NUTRITION;
//
//        ItemStack sausageResult = new ItemStack(ModItems.SAUSAGE.get(), sausageOutput);
//
//        return sausageResult;
//    }

    private Optional<MeatMasherRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(SLOT_COUNT);

        for (int i = 0; i < SLOT_COUNT; i++)
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));

        return this.level.getRecipeManager().getRecipeFor(MeatMasherRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean canAddStackToOutput(int itemCount, ItemStack output, ItemStack failOutput) {
        return canAddStackToOutput(new ItemStack(output.getItem(), itemCount), SAUSAGE_OUTPUT_SLOT) &&
                canAddStackToOutput(new ItemStack(failOutput.getItem(), itemCount), RESIDUE_OUTPUT_SLOT);
    }

    private boolean canAddStackToOutput(ItemStack result, int slot) {
        // can add item?
        return (this.itemHandler.getStackInSlot(slot).isEmpty() || this.itemHandler.getStackInSlot(slot).is(result.getItem()))
            // can add amount?
            && this.itemHandler.getStackInSlot(slot).getCount() + result.getCount() <= this.itemHandler.getStackInSlot(slot).getMaxStackSize();
    }

    private void increaseProgress() {
        progress++;
    }

    private boolean progressFinished() {
        return progress >= max_progress;
    }

    private void makeItem() {
//        ItemStack sausageResult = sausageOutput(this.itemHandler.getStackInSlot(INPUT_SLOT));

        Optional<MeatMasherRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;

        ItemStack result = recipe.get().getResultItem(null);
        int slot = result.getItem() == recipe.get().getOutput().getItem() ? SAUSAGE_OUTPUT_SLOT : RESIDUE_OUTPUT_SLOT;

        this.itemHandler.extractItem(INPUT_SLOT, 1, false);
        this.itemHandler.setStackInSlot(slot, new ItemStack(result.getItem(), result.getCount() + this.itemHandler.getStackInSlot(slot).getCount()));
//        if (this.getLevel().getRandom().nextDouble() > MALFUNCTION_CHANCE)
//            this.itemHandler.setStackInSlot(SAUSAGE_OUTPUT_SLOT, new ItemStack(sausageResult.getItem(), sausageResult.getCount() + this.itemHandler.getStackInSlot(SAUSAGE_OUTPUT_SLOT).getCount()));
//        else this.itemHandler.setStackInSlot(RESIDUE_OUTPUT_SLOT, new ItemStack(ModItems.MEAT_RESIDUE.get(), sausageResult.getCount() + this.itemHandler.getStackInSlot(RESIDUE_OUTPUT_SLOT).getCount()));
    }

    private void resetProgress() {
        progress = 0;
    }
}
