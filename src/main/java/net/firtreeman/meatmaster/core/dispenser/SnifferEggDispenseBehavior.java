package net.firtreeman.meatmaster.core.dispenser;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.block.DispenserBlock;
import org.slf4j.Logger;

public class SnifferEggDispenseBehavior extends OptionalDispenseItemBehavior {
    private static final Logger LOGGER = LogUtils.getLogger();

    public SnifferEggDispenseBehavior() {
    }

    protected ItemStack execute(BlockSource pSource, ItemStack pStack) {
        this.setSuccess(false);
        Item item = pStack.getItem();
        if (item instanceof BlockItem) {
            Direction facing = pSource.getBlockState().getValue(DispenserBlock.FACING);
            BlockPos blockPos = pSource.getPos().relative(facing);
            Direction toPlace = pSource.getLevel().isEmptyBlock(blockPos.below()) ? facing : Direction.UP;

            try {
                this.setSuccess(((BlockItem) item).place(new DirectionalPlaceContext(pSource.getLevel(), blockPos, facing, pStack, toPlace)).consumesAction());
            } catch (Exception e) {
                LOGGER.error("Error trying to place Sniffer Egg at {}", blockPos, e);
            }
        }

        return pStack;
    }
}
