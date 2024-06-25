package net.firtreeman.meatmaster.entity.projectile;

import net.firtreeman.meatmaster.util.HORMONE_TYPES;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;

public class HormoneArrow extends Arrow {
    private HORMONE_TYPES hormoneType;

    public HormoneArrow(EntityType<? extends Arrow> pEntityType, Level pLevel, HORMONE_TYPES hormoneType) {
        super(pEntityType, pLevel);
        this.hormoneType = hormoneType;
    }

    public HormoneArrow(Level pLevel, double pX, double pY, double pZ, HORMONE_TYPES hormoneType) {
        super(pLevel, pX, pY, pZ);
        this.hormoneType = hormoneType;
    }

    public HormoneArrow(Level pLevel, LivingEntity pShooter, HORMONE_TYPES hormoneType) {
        super(pLevel, pShooter);
        this.hormoneType = hormoneType;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);

        if (this.hormoneType != HORMONE_TYPES.NONE) {
            pCompound.putInt("HormoneType", this.hormoneType.ordinal());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        if (pCompound.contains("HormoneType")) {
            this.hormoneType = HORMONE_TYPES.values()[pCompound.getInt("HormoneType")];
        }
    }

    public HORMONE_TYPES getHormoneType() {
        return this.hormoneType;
    }
}
