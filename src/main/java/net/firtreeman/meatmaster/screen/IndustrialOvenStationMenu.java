package net.firtreeman.meatmaster.screen;

import net.firtreeman.meatmaster.block.ModBlocks;
import net.firtreeman.meatmaster.block.entity.IndustrialOvenStationBlockEntity;
import net.firtreeman.meatmaster.block.entity.IndustrialOvenStationBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class IndustrialOvenStationMenu extends AbstractContainerMenu {
    public final IndustrialOvenStationBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public IndustrialOvenStationMenu(int pContainerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(pContainerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(IndustrialOvenStationBlockEntity.SLOT_COUNT + 3));
    }

    public IndustrialOvenStationMenu(int pContainerId, Inventory inventory, BlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.INDUSTRIAL_OVEN_MENU.get(), pContainerId);
        checkContainerSize(inventory, IndustrialOvenStationBlockEntity.SLOT_COUNT);
        this.blockEntity = ((IndustrialOvenStationBlockEntity) blockEntity);
        this.level = inventory.player.level();
        this.data = data;

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 31, 16));
            this.addSlot(new SlotItemHandler(iItemHandler, 1, 31, 34));
            this.addSlot(new SlotItemHandler(iItemHandler, 2, 31, 52));
            this.addSlot(new SlotItemHandler(iItemHandler, 3, 66, 35));
            this.addSlot(new SlotItemHandler(iItemHandler, 4, 126, 35));
        });

        addDataSlots(data);
    }

    public boolean isProcessing() {
        return data.get(0) > 0;
    }

    public boolean isBurning() {
        return data.get(2) > 0 ||
               data.get(3) > 0 ||
               data.get(4) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 22;

        return (maxProgress != 0 && progress != 0) ? progress * progressArrowSize / maxProgress : 0;
    }

    public int[] getScaledLit() {
        int[] lit = new int[]{this.data.get(2), this.data.get(3), this.data.get(4)};
        int[] maxLit = new int[]{this.data.get(5), this.data.get(6), this.data.get(7)};
        int burnProgressSize = 14;
        int[] output = new int[3];
        
        for (int i = 0; i < output.length; i++) {
            output[i] = (lit[i] != 0 && maxLit[i] != 0) ? lit[i] * burnProgressSize / maxLit[i] : 0;
        }
        
        return output;
    }

    // quickMoveStack() credit: diesieben07 | https://github.com/diesieben07/SevenCommons
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    private static final int TE_INVENTORY_SLOT_COUNT = IndustrialOvenStationBlockEntity.SLOT_COUNT;

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.INDUSTRIAL_OVEN_STATION.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
