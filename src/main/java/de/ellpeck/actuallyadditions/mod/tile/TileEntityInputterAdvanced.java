/*
 * This file ("TileEntityInputterAdvanced.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityInputterAdvanced extends TileEntityInputter {

    public TileEntityInputterAdvanced(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.INPUTTER_ADVANCED.getTileEntityType(), pos, state, true);
    }
}
