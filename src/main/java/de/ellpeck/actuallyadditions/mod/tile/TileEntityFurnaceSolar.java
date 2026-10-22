/*
 * This file ("TileEntityFurnaceSolar.java") is part of the Actually Additions mod for Minecraft.
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
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityFurnaceSolar extends TileEntityBase implements ISharingEnergyProvider, IEnergyDisplay {

    public static final int PRODUCE = 8;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(30000, 0, 100);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);
    private int oldEnergy;

    public TileEntityFurnaceSolar(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.SOLAR_PANEL.getTileEntityType(), pos, state);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityFurnaceSolar tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityFurnaceSolar tile) {
            tile.serverTick();
        }
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
    }

    @Override
    public void serverTick() {
        super.serverTick();
        if (!this.isRedstonePowered && this.level != null && !this.level.isClientSide) {
            if (this.level.isDay() && !this.level.isRaining() && this.level.dimensionType().hasSkyLight()) {
                int power = this.getPowerToGenerate(PRODUCE);
                if (power > 0) {
                    int received = this.storage.receiveEnergyInternal(power, false);
                    if (received > 0) {
                        this.setChanged();
                    }
                }
            }

            if (this.oldEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()) {
                this.oldEnergy = this.storage.getEnergyStored();
            }
        }
    }

    public int getPowerToGenerate(int power) {
        if (this.level == null) return 0;
        int maxY = this.level.getMaxBuildHeight() - this.worldPosition.getY();
        for (int y = 1; y <= maxY; y++) {
            if (power > 0) {
                BlockPos pos = this.worldPosition.above(y);
                BlockState state = this.level.getBlockState(pos);

                if (state.isSolidRender(this.level, pos)) {
                    return 0;
                } else if (!state.isAir()) {
                    power--;
                }
            } else {
                break;
            }
        }

        return Math.max(0, power);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public CustomEnergyStorage getEnergyStorage() {
        return this.storage;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean needsHoldShift() {
        return false;
    }

    @Override
    public int getEnergyToSplitShare() {
        return this.storage.getEnergyStored();
    }

    @Override
    public boolean doesShareEnergy() {
        return true;
    }

    @Override
    public Direction[] getEnergyShareSides() {
        return Direction.values();
    }

    @Override
    public boolean canShareTo(BlockEntity tile) {
        return true;
    }

    @Override
    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        this.lazyEnergy.invalidate();
    }
}
