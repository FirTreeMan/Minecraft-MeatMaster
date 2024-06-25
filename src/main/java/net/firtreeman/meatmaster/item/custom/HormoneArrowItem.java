package net.firtreeman.meatmaster.item.custom;

import net.firtreeman.meatmaster.entity.projectile.HormoneArrow;
import net.firtreeman.meatmaster.util.HORMONE_TYPES;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HormoneArrowItem extends ArrowItem {
    private final HORMONE_TYPES hormoneType;

    public HormoneArrowItem(Properties pProperties) {
        this(pProperties, HORMONE_TYPES.NONE);
    }

    public HormoneArrowItem(Properties pProperties, HORMONE_TYPES hormoneType) {
        super(pProperties);
        this.hormoneType = hormoneType;
    }

    public AbstractArrow createArrow(Level pLevel, ItemStack pStack, LivingEntity pShooter) {
        HormoneArrow arrow = new HormoneArrow(pLevel, pShooter, hormoneType);
        arrow.setEffectsFromItem(pStack);
        arrow.setBaseDamage(0D);

        return arrow;
   }
}
