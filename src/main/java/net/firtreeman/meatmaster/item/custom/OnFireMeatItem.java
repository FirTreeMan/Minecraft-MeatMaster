package net.firtreeman.meatmaster.item.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class OnFireMeatItem extends Item {
    private static final int secondsOnFire = 10;

    public OnFireMeatItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        pLivingEntity.setSecondsOnFire(secondsOnFire);
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }
}
