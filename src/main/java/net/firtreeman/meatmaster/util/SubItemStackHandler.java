package net.firtreeman.meatmaster.util;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiPredicate;

public class SubItemStackHandler extends ItemStackHandler {
    private final ItemStackHandler parent;
    private final int SLOT;
    private BiPredicate<Integer, ItemStack> itemValidBiPredicate;

    public SubItemStackHandler(ItemStackHandler parent, int slot) {
        super(1);
        this.parent = parent;
        this.SLOT = slot;
    }

    public SubItemStackHandler outputOnly() {
        return this.validateItems((slot, stack) -> false);
    }

    public SubItemStackHandler validateItems(BiPredicate<Integer, ItemStack> itemValidPredicate) {
        if (this.itemValidBiPredicate != null)
            throw new IllegalArgumentException("Already assigned a BiPredicate for SubItemStackHandler.isItemValid()");

        this.itemValidBiPredicate = itemValidPredicate;
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
        if (itemValidBiPredicate == null)
            return super.isItemValid(slot, stack);
        return itemValidBiPredicate.test(slot, stack);
    }
}
