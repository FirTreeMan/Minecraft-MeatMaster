package net.firtreeman.meatmaster.item.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class VolatileMeatItem extends Item {
    public VolatileMeatItem(Properties pProperties) {
        super(pProperties);
    }

//    @Override
//    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
//        if (pEntity instanceof Player) {
//            ((Player) pEntity).getInventory().removeItem(pStack);
//            pLevel.explode(null, pEntity.getX(), pEntity.getY() + 2.0, pEntity.getZ(), 4.0F, Level.ExplosionInteraction.TNT);
//        }
//        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
//    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pLivingEntity instanceof Player) {
            ((Player) pLivingEntity).getInventory().removeItem(pStack);
            pLevel.explode(null, pLivingEntity.getX(), pLivingEntity.getY() + 2.0, pLivingEntity.getZ(), 4.0F, Level.ExplosionInteraction.TNT);
        }
        return ItemStack.EMPTY;
    }
}
