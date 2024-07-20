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
import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.slf4j.Logger;

public class TurtleEggDispenseBehavior extends OptionalDispenseItemBehavior {
    private static final Logger LOGGER = LogUtils.getLogger();

    public TurtleEggDispenseBehavior() {
    }

    protected ItemStack execute(BlockSource pSource, ItemStack pStack) {
        this.setSuccess(false);
        Item item = pStack.getItem();
        if (item instanceof BlockItem) {
            Direction facing = pSource.getBlockState().getValue(DispenserBlock.FACING);
            BlockPos blockPos = pSource.getPos().relative(facing);
            Direction toPlace = pSource.getLevel().isEmptyBlock(blockPos.below()) ? facing : Direction.UP;

            try {
                if (!TurtleEggBlock.onSand(pSource.getLevel(), blockPos)) return pStack;

                this.setSuccess(((BlockItem) item).place(new DirectionalPlaceContext(pSource.getLevel(), blockPos, facing, pStack, toPlace)).consumesAction());
                if (this.isSuccess()) return pStack;

                BlockState blockState = pSource.getLevel().getBlockState(blockPos);
                Item targetItem = blockState.getBlock().asItem();
                if (item == targetItem) {
                    int eggs = blockState.getValue(BlockStateProperties.EGGS);
                    if (eggs < TurtleEggBlock.MAX_EGGS) {
                        blockState.setValue(BlockStateProperties.EGGS, eggs + 1);
                        pSource.getLevel().setBlockAndUpdate(blockPos, blockState);
                        this.setSuccess(true);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Error trying to place Turtle Egg at {}", blockPos, e);
            }
        }

        return pStack;
    }
}
