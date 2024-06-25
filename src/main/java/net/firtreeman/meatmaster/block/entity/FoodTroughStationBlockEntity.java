package net.firtreeman.meatmaster.block.entity;

import net.firtreeman.meatmaster.block.ModBlockEntities;
import net.firtreeman.meatmaster.block.custom.FoodTroughStationBlock;
import net.firtreeman.meatmaster.screen.FoodTroughStationMenu;
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

import java.util.ArrayList;

public class FoodTroughStationBlockEntity extends BlockEntity implements MenuProvider {
    public static final int SLOT_COUNT = 1;
    private static final int INPUT_SLOT = 0;
    private static final int MAX_COOLDOWN_TIME = 20;
    private static final Direction[] SPREAD_DIRECTIONS = new Direction[]{Direction.DOWN, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

    private final ItemStackHandler itemHandler = new ItemStackHandler(SLOT_COUNT);
    protected final ContainerData data;
    private int cooldownTime = -1;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public FoodTroughStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FOOD_TROUGH_SBE.get(), pPos, pBlockState);

        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
            }

            @Override
            public int getCount() {
                return SLOT_COUNT;
            }
        };
    }

    public ItemStack getStack() {
        return this.itemHandler.getStackInSlot(INPUT_SLOT);
    }

    public Item getItem() {
        return this.itemHandler.getStackInSlot(INPUT_SLOT).getItem();
    }

    public int getCount() {
        return this.itemHandler.getStackInSlot(INPUT_SLOT).getCount();
    }

    public boolean isEmpty() {
        return this.itemHandler.getStackInSlot(INPUT_SLOT).isEmpty();
    }

    public void eat() {
        this.itemHandler.getStackInSlot(INPUT_SLOT).shrink(1);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (side == null) return lazyItemHandler.cast();

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
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
        return Component.translatable("block.meatmaster.food_trough_station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FoodTroughStationMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("TransferCooldown", this.cooldownTime);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        this.cooldownTime = pTag.getInt("TransferCooldown");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (canTransfer() && pState.getValue(FoodTroughStationBlock.LIT)) {
            System.out.println("z");
            tryTransfer(pLevel, pPos);
            setChanged(pLevel, pPos, pState);
        } else {
            idleTick();
        }
    }

    // block is able to act exactly once every MAX_COOLDOWN_TIME ticks
    public void idleTick() {
        if (cooldownTime <= 0) cooldownTime = MAX_COOLDOWN_TIME;
        cooldownTime -= 1;
    }

    public boolean canTransfer() {
        return cooldownTime <= 0;
    }

    public boolean tryTransfer(Level pLevel, BlockPos pPos) {

        ArrayList<FoodTroughStationBlockEntity> nearbyFoodTroughs = new ArrayList<>();
        int totalCount = this.getCount();

        for (Direction direction: SPREAD_DIRECTIONS) {
            BlockPos blockpos = pPos.relative(direction);
            BlockState blockstate = pLevel.getBlockState(blockpos);
            BlockEntity blockEntity = pLevel.getBlockEntity(blockpos);

            if (blockEntity instanceof FoodTroughStationBlockEntity foundFoodTrough) {
                System.out.println("a");
                if ((foundFoodTrough.isEmpty() || foundFoodTrough.getItem() == this.getItem()) && foundFoodTrough.getCount() < this.getCount()) {
                    totalCount += foundFoodTrough.getCount();
                    nearbyFoodTroughs.add(foundFoodTrough);
                }
            }
        }
        System.out.println(totalCount);

        if (nearbyFoodTroughs.isEmpty()) return false;

        int meanCount = totalCount / (nearbyFoodTroughs.size() + 1);
        for (FoodTroughStationBlockEntity foundFoodTrough: nearbyFoodTroughs) {
            if (foundFoodTrough.isEmpty())
                foundFoodTrough.itemHandler.setStackInSlot(INPUT_SLOT, new ItemStack(this.getItem(), 0));

            int difference = meanCount - foundFoodTrough.getCount();
            this.getStack().shrink(difference);
            foundFoodTrough.getStack().grow(difference);
        }

        this.cooldownTime = MAX_COOLDOWN_TIME;
        return true;
    }
}
