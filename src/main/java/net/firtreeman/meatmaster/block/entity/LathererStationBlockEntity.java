package net.firtreeman.meatmaster.block.entity;

import net.firtreeman.meatmaster.block.ModBlockEntities;
import net.firtreeman.meatmaster.item.custom.SpiceItem;
import net.firtreeman.meatmaster.screen.LathererStationMenu;
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

public class LathererStationBlockEntity extends BlockEntity implements MenuProvider {
    public static final int SLOT_COUNT = 3;
    private static final int SPICE_INPUT_SLOT = 0;
    private static final int FOOD_INPUT_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;

    private final ItemStackHandler itemHandler = new ItemStackHandler(SLOT_COUNT) {
        @Override
        public void setStackInSlot(int slot, @NotNull ItemStack stack) {
            super.setStackInSlot(slot, stack);
            switch (slot) {
                case 0 -> spiceInputItemHandler.setStackInSlot(0, stack);
                case 1 -> foodInputItemHandler.setStackInSlot(0, stack);
                case 2 -> outputItemHandler.setStackInSlot(0, stack);
            }
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            super.deserializeNBT(nbt);
            spiceInputItemHandler.setStackInSlot(0, itemHandler.getStackInSlot(SPICE_INPUT_SLOT));
            foodInputItemHandler.setStackInSlot(0, itemHandler.getStackInSlot(FOOD_INPUT_SLOT));
            outputItemHandler.setStackInSlot(0, itemHandler.getStackInSlot(OUTPUT_SLOT));
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case SPICE_INPUT_SLOT -> spiceInputItemHandler.isItemValid(slot, stack);
                case FOOD_INPUT_SLOT -> foodInputItemHandler.isItemValid(slot, stack);
                default -> true;
            };
        }
    };
    private final ItemStackHandler spiceInputItemHandler = new SubItemStackHandler(itemHandler, SPICE_INPUT_SLOT) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return stack.is(ModTags.Items.SPICES);
        }
    };
    private final ItemStackHandler foodInputItemHandler = new SubItemStackHandler(itemHandler, FOOD_INPUT_SLOT) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return stack.isEdible();
        }
    };
    private final ItemStackHandler outputItemHandler = new SubItemStackHandler(itemHandler, OUTPUT_SLOT);

    protected final ContainerData data;
    private int progress = 0;
    private int max_progress = 80;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> spiceInputLazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> foodInputLazyItemHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> outputLazyItemHandler = LazyOptional.empty();

    public LathererStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.LATHERER_SBE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> LathererStationBlockEntity.this.progress;
                    case 1 -> LathererStationBlockEntity.this.max_progress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                 switch (pIndex) {
                    case 0 -> LathererStationBlockEntity.this.progress = pValue;
                    case 1 -> LathererStationBlockEntity.this.max_progress = pValue;
                };
            }

            @Override
            public int getCount() {
                return SLOT_COUNT;
            }
        };
    }

    public static boolean checkValidItem(ItemStackHandler itemHandler, int slot) {
        return itemHandler.isItemValid(slot, itemHandler.getStackInSlot(slot));
    }

    public static boolean itemStackIsSpiced(ItemStack itemStack) {
        return itemStack.isEdible() && itemStack.getTag() != null && !itemStack.getTag().getString("Spice").isEmpty();
    }

    public static ItemStack makeSpicedItemStack(ItemStack itemStack, SpiceItem spiceItem) {
        itemStack.getOrCreateTag().putString("Spice", String.valueOf(spiceItem.getSpiceName()));
        return itemStack;
    }

    public static String getItemStackSpice(ItemStack itemStack) {
        return itemStack.getTag() == null ? "" : itemStack.getTag().getString("Spice");
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (side == null) return lazyItemHandler.cast();

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == Direction.UP)
                return spiceInputLazyItemHandler.cast();
            else if (side == Direction.DOWN)
                return outputLazyItemHandler.cast();
            return foodInputLazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        spiceInputLazyItemHandler = LazyOptional.of(() -> spiceInputItemHandler);
        foodInputLazyItemHandler = LazyOptional.of(() -> foodInputItemHandler);
        outputLazyItemHandler = LazyOptional.of(() -> outputItemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        spiceInputLazyItemHandler.invalidate();
        foodInputLazyItemHandler.invalidate();
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
        return Component.translatable("block.meatmaster.latherer_station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new LathererStationMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("latherer_station.progress", progress);
        pTag.putInt("latherer_station.max_progress", max_progress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("latherer_station.progress");
        max_progress = pTag.getInt("latherer_station.max_progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (hasRecipe()) {
            increaseProgress();
            setChanged(pLevel, pPos, pState);

            if (progressFinished()) {
                makeItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private boolean hasRecipe() {
        if (checkValidItem(this.itemHandler, SPICE_INPUT_SLOT) &&
                checkValidItem(this.itemHandler, FOOD_INPUT_SLOT) &&
                !itemStackIsSpiced(this.itemHandler.getStackInSlot(FOOD_INPUT_SLOT))) {
            ItemStack result = new ItemStack(this.itemHandler.getStackInSlot(FOOD_INPUT_SLOT).getItem(), 1);
            return canAddStackToOutput(result);
        }
        return false;
    }

    private boolean canAddStackToOutput(ItemStack result) {
        ItemStack outputItemStack = this.itemHandler.getStackInSlot(OUTPUT_SLOT);
        SpiceItem spiceItem = (SpiceItem) this.itemHandler.getStackInSlot(SPICE_INPUT_SLOT).getItem();
        makeSpicedItemStack(result, spiceItem);
        // can add item?
        return outputItemStack.isEmpty() || (outputItemStack.is(result.getItem()) &&
                // matching spice?
                (getItemStackSpice(result).equals(String.valueOf(spiceItem.getSpiceName())))) &&
            // can add amount?
            outputItemStack.getCount() + result.getCount() <= outputItemStack.getMaxStackSize();
    }

    private void increaseProgress() {
        progress++;
    }

    private boolean progressFinished() {
        return progress >= max_progress;
    }

    private void makeItem() {
        SpiceItem spiceItem = (SpiceItem) this.itemHandler.getStackInSlot(SPICE_INPUT_SLOT).getItem();
        ItemStack result = new ItemStack(this.itemHandler.getStackInSlot(FOOD_INPUT_SLOT).getItem(), 1);

        this.itemHandler.extractItem(SPICE_INPUT_SLOT, 1, false);
        this.itemHandler.extractItem(FOOD_INPUT_SLOT, 1, false);
        this.itemHandler.setStackInSlot(OUTPUT_SLOT, makeSpicedItemStack(new ItemStack(result.getItem(), result.getCount() + this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount()), spiceItem));
    }

    private void resetProgress() {
        progress = 0;
    }
}
