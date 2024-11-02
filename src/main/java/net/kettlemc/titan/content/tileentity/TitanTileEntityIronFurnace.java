package net.kettlemc.titan.content.tileentity;

import net.kettlemc.titan.content.block.TitanBlocks;

public class TitanTileEntityIronFurnace extends TitanTileEntityFurnace {

    public TitanTileEntityIronFurnace() {
        super(TitanBlocks.IRON_FURNACE.getTranslationKey(), 1, 0.75);
    }

}
