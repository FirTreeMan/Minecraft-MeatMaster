package net.firtreeman.meatmaster.util;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiPredicate;

public class SubItemStackHandler extends ItemStackHandler {
    private final ItemStackHandler parent;
    private final int SLOT;
    private BiPredicate<Integer, ItemStack> itemValidPredicate;

    public SubItemStackHandler(ItemStackHandler parent, int slot) {
        super(1);
        this.parent = parent;
        this.SLOT = slot;
    }

    public SubItemStackHandler validateItems(BiPredicate<Integer, ItemStack> itemValidPredicate) {
        this.itemValidPredicate = itemValidPredicate;
        return this;
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return parent.insertItem(this.SLOT, stack, simulate);
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return parent.extractItem(this.SLOT, amount, simulate);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        if (itemValidPredicate == null)
            return super.isItemValid(slot, stack);
        return itemValidPredicate.test(slot, stack);
    }
}
