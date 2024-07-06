package net.firtreeman.meatmaster.item.custom;

import net.firtreeman.meatmaster.entity.projectile.HormoneArrow;
import net.firtreeman.meatmaster.util.HORMONE_TYPES;
import net.firtreeman.meatmaster.util.HormoneUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HormoneBaseItem extends Item {
    public HormoneBaseItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }

    @Override
    public Component getName(ItemStack pStack) {
        HORMONE_TYPES hormoneType = HormoneUtils.getHormone(pStack);

        if (hormoneType != HORMONE_TYPES.NONE) {
            String hormoneName = hormoneType.name().toLowerCase();
            return Component.translatable(this.getDescriptionId() + '.' + hormoneName);
        }
        return super.getName(pStack);
    }
}
