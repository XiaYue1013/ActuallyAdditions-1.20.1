/*
 * This file ("TileEntityCompost.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.CompostRecipe;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityCompost extends TileEntityInventoryBase {

    public static final int COMPOST_TIME_TICKS = 3000;

    public int conversionTime;
    protected CompostRecipe recipe;

    public TileEntityCompost(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.COMPOST.getTileEntityType(), pos, state, 1);
    }

    public static CompostRecipe getRecipeForInput(ItemStack input) {
        if (!input.isEmpty()) {
            for (CompostRecipe r : ActuallyAdditionsAPI.COMPOST_RECIPES) {
                if (r.matches(input)) return r;
            }
        }
        return null;
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("ConversionTime", this.conversionTime);
        }
    }

    @Override
    public boolean shouldSyncSlots() {
        return true;
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            this.conversionTime = compound.getInt("ConversionTime");
        }
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityCompost tile) {
            boolean wasConverting = tile.conversionTime > 0;
            ItemStack input = tile.inv.getStackInSlot(0);
            if (!input.isEmpty()) {
                if (tile.recipe == null || !tile.recipe.matches(input)) {
                    tile.recipe = getRecipeForInput(input);
                }
                if (tile.recipe != null) {
                    tile.conversionTime++;
                    if (tile.conversionTime >= COMPOST_TIME_TICKS) {
                        ItemStack output = tile.recipe.getOutput().copy();
                        output.setCount(input.getCount());
                        tile.inv.setStackInSlot(0, output);
                        tile.conversionTime = 0;
                        tile.recipe = null;
                        tile.setChanged();
                        level.sendBlockUpdated(pos, state, state, 3);
                    }
                } else {
                    tile.conversionTime = 0;
                }
            } else {
                tile.conversionTime = 0;
            }

            if (wasConverting != (tile.conversionTime > 0)) {
                tile.setChanged();
            }
        }
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> getRecipeForInput(stack) != null;
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !this.inv.getStackInSlot(0).isEmpty() && getRecipeForInput(this.inv.getStackInSlot(0)) == null;
    }
}
