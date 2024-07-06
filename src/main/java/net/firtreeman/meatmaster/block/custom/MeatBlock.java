package net.firtreeman.meatmaster.block.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class MeatBlock extends Block {
    public MeatBlock() {
        this(Properties.of().mapColor(MapColor.COLOR_RED).sound(SoundType.SLIME_BLOCK).instrument(NoteBlockInstrument.BANJO));
    }

    public MeatBlock(Properties pProperties) {
        super(pProperties);
    }
}
