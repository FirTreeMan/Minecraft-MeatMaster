package net.firtreeman.meatmaster.block.entity;

import net.firtreeman.meatmaster.block.ModBlockEntities;
import net.firtreeman.meatmaster.datagen.ModRecipeProvider;
import net.firtreeman.meatmaster.recipe.IndustrialOvenRecipe;
import net.firtreeman.meatmaster.screen.IndustrialOvenStationMenu;
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
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class IndustrialOvenStationBlockEntity extends BlockEntity implements MenuProvider {
    public static final int SLOT_COUNT = 5;
    private static final int FUEL_INPUT_SLOT_MAX = 3;
    private static final int BLOCK_INPUT_SLOT = 3;
    private static final int OUTPUT_SLOT = 4;

    private int lowestCountSlot = -1;

    private final ItemStackHandler itemHandler = new ItemStackHandler(SLOT_COUNT) {
        @Override
        public void setStackInSlot(int slot, @NotNull ItemStack stack) {
            super.setStackInSlot(slot, stack);
            if (slot < FUEL_INPUT_SLOT_MAX)
                fuelInputItemHandler.setStackInSlot(slot, stack);
            else if (slot == BLOCK_INPUT_SLOT)
                blockInputItemHandler.setStackInSlot(0, stack);
            else outputItemHandler.setStackInSlot(0, stack);
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            super.deserializeNBT(nbt);
            for (int i = 0; i < FUEL_INPUT_SLOT_MAX; i++)
                fuelInputItemHandler.setStackInSlot(i, itemHandler.getStackInSlot(i));
            outputItemHandler.setStackInSlot(0, itemHandler.getStackInSlot(OUTPUT_SLOT));
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case BLOCK_INPUT_SLOT -> true;
                default -> fuelInputItemHandler.isItemValid(slot, stack);
            };
        }
    };
    private final ItemStackHandler fuelInputItemHandler = new ItemStackHandler(3) {
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
            return getBurnDuration(stack) > 0;
        }
    };
    private final ItemStackHandler fuelInputCapabilityItemHandler = new ItemStackHandler(1) {
        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return fuelInputItemHandler.insertItem(lowestCountSlot, stack, simulate);
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return fuelInputItemHandler.extractItem(lowestCountSlot, amount, simulate);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return fuelInputItemHandler.isItemValid(lowestCountSlot, stack);
        }
    };
    private final ItemStackHandler blockInputItemHandler = new SubItemStackHandler(itemHandler, BLOCK_INPUT_SLOT).validateItems((slot, stack) -> ModRecipeProvider.COOKED_BLOCK_VARIANTS.keySet().stream().anyMatch(block -> stack.is(block.asItem())));
    private final ItemStackHandler outputItemHandler = new SubItemStackHandler(itemHandler, OUTPUT_SLOT).outputOnly();

    protected final ContainerData data;
    private int progress = 0;
    private int max_progress = 100;
    private int[] litTime = new int[3];
    private final int[] max_litTime = new int[3];

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> fuelInputLazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> fuelInputCapabilityLazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> blockInputLazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> outputLazyItemHandler = LazyOptional.empty();

    public IndustrialOvenStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.INDUSTRIAL_OVEN_SBE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> IndustrialOvenStationBlockEntity.this.progress;
                    case 1 -> IndustrialOvenStationBlockEntity.this.max_progress;
                    case 2 -> IndustrialOvenStationBlockEntity.this.litTime[0];
                    case 3 -> IndustrialOvenStationBlockEntity.this.litTime[1];
                    case 4 -> IndustrialOvenStationBlockEntity.this.litTime[2];
                    case 5 -> IndustrialOvenStationBlockEntity.this.max_litTime[0];
                    case 6 -> IndustrialOvenStationBlockEntity.this.max_litTime[1];
                    case 7 -> IndustrialOvenStationBlockEntity.this.max_litTime[2];

                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                 switch (pIndex) {
                    case 0 -> IndustrialOvenStationBlockEntity.this.progress = pValue;
                    case 1 -> IndustrialOvenStationBlockEntity.this.max_progress = pValue;
                    case 2 -> IndustrialOvenStationBlockEntity.this.litTime[0] = pValue;
                    case 3 -> IndustrialOvenStationBlockEntity.this.litTime[1] = pValue;
                    case 4 -> IndustrialOvenStationBlockEntity.this.litTime[2] = pValue;
                    case 5 -> IndustrialOvenStationBlockEntity.this.max_litTime[0] = pValue;
                    case 6 -> IndustrialOvenStationBlockEntity.this.max_litTime[1] = pValue;
                    case 7 -> IndustrialOvenStationBlockEntity.this.max_litTime[2] = pValue;
                };
            }

            @Override
            public int getCount() {
                // add 3, extra to hold max_lit[] values
                return SLOT_COUNT + 3;
            }
        };
    }

    public void calcLowestCountSlot() {
        int lowestCount = Integer.MAX_VALUE;
        int lowestIndex = 0;
        for (int i = 0; i < FUEL_INPUT_SLOT_MAX; i++){
            int itemCount = itemHandler.getStackInSlot(i).getCount();
            if (itemCount < lowestCount) {
                lowestCount = itemCount;
                lowestIndex = i;
                if (lowestCount == 0) break;
            }
        }
        if (lowestCountSlot != lowestIndex) {
            lowestCountSlot = lowestIndex;
            fuelInputCapabilityItemHandler.setStackInSlot(0, fuelInputItemHandler.getStackInSlot(lowestCountSlot));
        }
    }

    protected int getBurnDuration(ItemStack pFuel) {
      if (pFuel.isEmpty()) {
         return 0;
      } else {
         Item item = pFuel.getItem();
         return ForgeHooks.getBurnTime(pFuel, RecipeType.SMELTING);
      }
   }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (side == null) return lazyItemHandler.cast();

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return switch(side) {
                case UP -> blockInputLazyItemHandler.cast();
                case DOWN -> outputLazyItemHandler.cast();
                default -> fuelInputCapabilityLazyItemHandler.cast();
            };
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        fuelInputLazyItemHandler = LazyOptional.of(() -> fuelInputItemHandler);
        fuelInputCapabilityLazyItemHandler = LazyOptional.of(() -> fuelInputCapabilityItemHandler);
        blockInputLazyItemHandler = LazyOptional.of(() -> blockInputItemHandler);
        outputLazyItemHandler = LazyOptional.of(() -> outputItemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        fuelInputLazyItemHandler.invalidate();
        fuelInputCapabilityLazyItemHandler.invalidate();
        blockInputLazyItemHandler.invalidate();
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
        return Component.translatable("block.meatmaster.industrial_oven_station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new IndustrialOvenStationMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("industrial_oven_station.progress", progress);
        pTag.putInt("industrial_oven_station.max_progress", max_progress);
        pTag.putIntArray("industrial_oven_station.litTime", litTime);
        pTag.putIntArray("industrial_oven_station.max_litTime", max_litTime);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("industrial_oven_station.progress");
        max_progress = pTag.getInt("industrial_oven_station.max_progress");
        litTime = pTag.getIntArray("industrial_oven_station.litTime");
//        max_litTime = pTag.getIntArray("industrial_oven_station.max_litTime");
        for (int i = 0; i < FUEL_INPUT_SLOT_MAX; i++)
            max_litTime[i] = getBurnDuration(fuelInputItemHandler.getStackInSlot(i));
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        tickFuel();
        if (hasRecipe() && hasFuel()) {
            tryBurnFuel();
            increaseProgress();
            setChanged(pLevel, pPos, pState);

            if (progressFinished()) {
                makeItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
        calcLowestCountSlot();
    }

    private boolean hasRecipe() {
        Optional<IndustrialOvenRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) return false;

        ItemStack result = recipe.get().getResultItem(null);

        return canAddStackToOutput(result);
    }

    private Optional<IndustrialOvenRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(1);

        inventory.setItem(0, this.itemHandler.getStackInSlot(BLOCK_INPUT_SLOT));

        return this.level.getRecipeManager().getRecipeFor(IndustrialOvenRecipe.Type.INSTANCE, inventory, level);
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
        Optional<IndustrialOvenRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;

        ItemStack result = recipe.get().getResultItem(null);

        for (int i = 0; i < FUEL_INPUT_SLOT_MAX; i++)
            this.itemHandler.extractItem(i, 1, false);
        this.itemHandler.extractItem(BLOCK_INPUT_SLOT, 1, false);
        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(), result.getCount() + this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount()));
    }

    private void tickFuel() {
        for (int i = 0; i < FUEL_INPUT_SLOT_MAX; i++)
            if (litTime[i] > 0) litTime[i]--;
    }

    private boolean hasFuel(int slot) {
        return litTime[slot] > 0 || getBurnDuration(fuelInputItemHandler.getStackInSlot(slot)) > 0;
    }

    private boolean hasFuel() {
        for (int i = 0; i < FUEL_INPUT_SLOT_MAX; i++)
            if (!hasFuel(i)) return false;
        return true;
    }

    private void tryBurnFuel() {
        for (int i = 0; i < FUEL_INPUT_SLOT_MAX; i++) {
            if (litTime[i] <= 0) {
                max_litTime[i] = getBurnDuration(fuelInputItemHandler.getStackInSlot(i)) / 3;
                litTime[i] = max_litTime[i];
                fuelInputItemHandler.extractItem(i, 1, false);
            }
        }
    }

    private void resetProgress() {
        progress = 0;
    }
}
