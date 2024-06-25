package net.firtreeman.meatmaster.item.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ContainerFoodItem extends Item {
    private final Item containerItem;

    public ContainerFoodItem(Properties pProperties, Item containerItem) {
        super(pProperties.stacksTo(1));
        this.containerItem = containerItem;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        ItemStack stack = super.finishUsingItem(pStack, pLevel, pLivingEntity);
        return pLivingEntity instanceof Player && ((Player) pLivingEntity).getAbilities().instabuild ? stack : new ItemStack(containerItem);
    }
}
