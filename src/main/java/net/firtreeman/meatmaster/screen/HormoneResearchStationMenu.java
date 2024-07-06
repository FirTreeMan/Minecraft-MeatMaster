package net.firtreeman.meatmaster.screen;

import net.firtreeman.meatmaster.block.ModBlocks;
import net.firtreeman.meatmaster.block.entity.HormoneResearchStationBlockEntity;
import net.firtreeman.meatmaster.util.HORMONE_TYPES;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.openjdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

public class HormoneResearchStationMenu extends AbstractContainerMenu {
    public final HormoneResearchStationBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    private int determinerOffset = 0;

    public HormoneResearchStationMenu(int pContainerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(pContainerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(HormoneResearchStationBlockEntity.SLOT_COUNT));
    }

    public HormoneResearchStationMenu(int pContainerId, Inventory inventory, BlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.HORMONE_RESEARCH_MENU.get(), pContainerId);
        checkContainerSize(inventory, HormoneResearchStationBlockEntity.SLOT_COUNT);
        this.blockEntity = ((HormoneResearchStationBlockEntity) blockEntity);
        this.level = inventory.player.level();
        this.data = data;

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 28, 24));
            this.addSlot(new SlotItemHandler(iItemHandler, 1, 130, 24));
            this.addSlot(new SlotItemHandler(iItemHandler, 2, 79, 24));
            this.addSlot(new SlotItemHandler(iItemHandler, 3, 28, 54));
            this.addSlot(new SlotItemHandler(iItemHandler, 4, 130, 54));
        });

        addDataSlots(data);
    }

    public boolean isProcessingResearch() {
        return data.get(0) > 0;
    }

    public boolean isProcessingFill() {
        return data.get(2) > 0;
    }

    public boolean hasBase() {
        return blockEntity.hasBase();
    }

    public boolean hasDeterminer() {
        return HORMONE_TYPES.values()[this.data.get(4)] != HORMONE_TYPES.NONE;
    }

    public void checkDeterminer() {
        if (hasDeterminer())
            this.determinerOffset++;
        else this.determinerOffset = 0;
    }

    public int getDeterminerOffset() {
        int determinerWidth = 75;
        int determinerSpace = 25;
        byte slowdown = 2;

        this.determinerOffset %= slowdown * (determinerWidth - determinerSpace);

        return this.determinerOffset / slowdown;
    }

    public int getScaledResearchProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 29;

        return (maxProgress != 0 && progress != 0) ? progress * progressArrowSize / maxProgress : 0;
    }

    public int getScaledFillProgressLeft() {
        int progress = this.data.get(2);
        int maxProgress = this.data.get(3);
        int progressArrowSize = 78;
        int progressLeftArrowSize = 39;

        return (maxProgress != 0 && progress != 0) ? Math.min(progress * progressArrowSize / maxProgress, progressLeftArrowSize) : 0;
    }

    public int getScaledFillProgressRight() {
        int progress = this.data.get(2);
        int maxProgress = this.data.get(3);
        int progressArrowSize = 78;
        int progressRightArrowSize = 35;
        int progressArrowDifference = progressArrowSize - progressRightArrowSize;

        return (maxProgress != 0 && progress != 0) ? Math.max(progress * progressArrowSize / maxProgress - progressArrowDifference, 0) : 0;
    }

    public int getFillProgressRightHeight() {
        int progressRightArrowOffset = 38;
        int progressRightArrowHeight = 4;

        return progressRightArrowOffset + progressRightArrowHeight *
            switch (HORMONE_TYPES.values()[this.data.get(4)]) {
                case GROWTH -> 0;
                case BREEDING -> 1;
                case YIELD -> 2;
                default -> throw new ValueException("getFIllProgressRightHeight() should only be called when the determiner is valid");
            };
    }

    // quickMoveStack() credit: diesieben07 | https://github.com/diesieben07/SevenCommons
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    private static final int TE_INVENTORY_SLOT_COUNT = HormoneResearchStationBlockEntity.SLOT_COUNT;

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
                pPlayer, ModBlocks.HORMONE_RESEARCH_STATION.get());
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
