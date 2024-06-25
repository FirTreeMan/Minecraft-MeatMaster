package net.firtreeman.meatmaster.item.custom;

import net.firtreeman.meatmaster.entity.projectile.HormoneArrow;
import net.firtreeman.meatmaster.util.HORMONE_TYPES;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HormoneBaseItem extends Item {
    private final HORMONE_TYPES hormoneType;

    public HormoneBaseItem(Properties pProperties) {
        this(pProperties.stacksTo(1), HORMONE_TYPES.NONE);
    }

    public HormoneBaseItem(Properties pProperties, HORMONE_TYPES hormoneType) {
        super(pProperties);
        this.hormoneType = hormoneType;
    }

    public HORMONE_TYPES getHormoneType() {
        return this.hormoneType;
    }
}
