package net.firtreeman.meatmaster.block.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class ShearableMeatBlock extends MeatBlock {
    private Item drop;
    private Block postShear;

    public ShearableMeatBlock(Item drop, Block postShear) {
        this(Properties.of().mapColor(MapColor.COLOR_GREEN).sound(SoundType.WET_GRASS).instrument(NoteBlockInstrument.BANJO));

        this.drop = drop;
        this.postShear = postShear;
    }

    private ShearableMeatBlock(Properties pProperties) {
        super(pProperties);
    }

    public Item getDrop() {
        return drop;
    }

    public Block getPostShear() {
        return postShear;
    }
}
