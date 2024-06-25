package net.firtreeman.meatmaster.item.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SpiceItem extends Item {
    public static enum SPICE_NAMES {
        SALTY,
        EXPLOSIVE,
        TELEPORTING,
        POISONED,
        FLAMMABLE,
        BITTER,
        FILLING,
    };

    private final SPICE_NAMES spiceName;

    public SpiceItem(Properties pProperties, SPICE_NAMES spiceName) {
        super(pProperties);
        this.spiceName = spiceName;
    }

    public SPICE_NAMES getSpiceName() {
        return spiceName;
    }
}
