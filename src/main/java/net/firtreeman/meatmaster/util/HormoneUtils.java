package net.firtreeman.meatmaster.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class HormoneUtils {
    public static final String TAG_HORMONE_TYPE = "HormoneType";

    public static ItemStack setHormone(ItemStack stack, HORMONE_TYPES hormoneType) {
        stack.getOrCreateTag().putInt(TAG_HORMONE_TYPE, hormoneType.ordinal());

        return stack;
    }

    public static ItemStack itemStackOf(ItemLike item, HORMONE_TYPES hormoneType) {
        return HormoneUtils.setHormone(new ItemStack(item), hormoneType);
    }

    public static ItemStack itemStackOf(ItemLike item, int count, HORMONE_TYPES hormoneType) {
        return HormoneUtils.setHormone(new ItemStack(item, count), hormoneType);
    }

    public static HORMONE_TYPES getHormone(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(TAG_HORMONE_TYPE))
            return HORMONE_TYPES.values()[tag.getInt(TAG_HORMONE_TYPE)];
        return HORMONE_TYPES.NONE;
    }
}
