package net.firtreeman.meatmaster.item.custom;

import net.firtreeman.meatmaster.util.SPICE_TYPES;
import net.minecraft.world.item.Item;

public class SpiceItem extends Item {
    private final SPICE_TYPES spiceType;

    public SpiceItem(Properties pProperties, SPICE_TYPES spiceType) {
        super(pProperties);
        this.spiceType = spiceType;
    }

    public SPICE_TYPES getSpiceType() {
        return spiceType;
    }
}
