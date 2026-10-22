/*
 * This file ("TileEntityInputter.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerInputter;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.network.gui.INumberReactor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

public class TileEntityInputter extends TileEntityInventoryBase implements IButtonReactor, INumberReactor, MenuProvider {

    public static final int OKAY_BUTTON_ID = 133;

    public int sideToPut = -1;
    public int slotToPutStart;
    public int slotToPutEnd;

    public int sideToPull = -1;
    public int slotToPullStart;
    public int slotToPullEnd;

    public boolean isAdvanced;
    public FilterSettings leftFilter;
    public FilterSettings rightFilter;

    public TileEntityInputter(BlockEntityType<?> type, BlockPos pos, BlockState state, boolean isAdvanced) {
        super(type, pos, state, 1);
        this.isAdvanced = isAdvanced;
        if (isAdvanced) {
            this.leftFilter = new FilterSettings(12, false, true, false, false);
            this.rightFilter = new FilterSettings(12, false, true, false, false);
        }
    }

    public TileEntityInputter(BlockPos pos, BlockState state) {
        this(ActuallyBlocks.INPUTTER.getTileEntityType(), pos, state, false);
    }

    @Nullable
    public static Direction getDirectionFromSide(int side) {
        if (side < 0 || side >= 6) return null;
        return Direction.from3DDataValue(side);
    }

    @Override
    public void onNumberReceived(double number, int textID, Player player) {
        int val = Math.max((int) number, 0);
        if (textID == 0) {
            this.slotToPutStart = val;
        } else if (textID == 1) {
            this.slotToPutEnd = val;
        } else if (textID == 2) {
            this.slotToPullStart = val;
        } else if (textID == 3) {
            this.slotToPullEnd = val;
        }
        this.setChanged();
        this.sendUpdate();
    }

    @Override
    public void onButtonPressed(int buttonID, Player player) {
        if (buttonID == 0) {
            this.sideToPut++;
            if (this.sideToPut >= 6) this.sideToPut = -1;
            this.slotToPutStart = 0;
            this.slotToPutEnd = 0;
        } else if (buttonID == 1) {
            this.sideToPut--;
            if (this.sideToPut < -1) this.sideToPut = 5;
            this.slotToPutStart = 0;
            this.slotToPutEnd = 0;
        } else if (buttonID == 2) {
            this.sideToPull++;
            if (this.sideToPull >= 6) this.sideToPull = -1;
            this.slotToPullStart = 0;
            this.slotToPullEnd = 0;
        } else if (buttonID == 3) {
            this.sideToPull--;
            if (this.sideToPull < -1) this.sideToPull = 5;
            this.slotToPullStart = 0;
            this.slotToPullEnd = 0;
        } else if (this.isAdvanced) {
            if (buttonID >= 1000 && buttonID < 2000 && this.leftFilter != null) {
                this.leftFilter.onButtonPressed(buttonID - 1000);
            } else if (buttonID >= 2000 && this.rightFilter != null) {
                this.rightFilter.onButtonPressed(buttonID - 2000);
            }
        }
        this.setChanged();
        this.sendUpdate();
    }

    private void doPulling() {
        if (this.sideToPull == -1 || this.level == null) return;
        Direction dir = getDirectionFromSide(this.sideToPull);
        if (dir == null) return;

        BlockPos targetPos = this.worldPosition.relative(dir);
        if (!this.level.isLoaded(targetPos)) return;

        BlockEntity targetTile = this.level.getBlockEntity(targetPos);
        if (targetTile == null) return;

        IItemHandler handler = targetTile.getCapability(ForgeCapabilities.ITEM_HANDLER, dir.getOpposite()).orElse(null);
        if (handler == null) return;

        int start = Math.min(this.slotToPullStart, handler.getSlots() - 1);
        int end = this.slotToPullEnd <= 0 ? handler.getSlots() : Math.min(this.slotToPullEnd, handler.getSlots());

        for (int i = Math.max(0, start); i < end; i++) {
            ItemStack inSlot = handler.getStackInSlot(i);
            if (inSlot.isEmpty()) continue;
            if (this.isAdvanced && this.leftFilter != null && !this.leftFilter.check(inSlot)) continue;

            ItemStack simulated = handler.extractItem(i, 64, true);
            if (simulated.isEmpty()) continue;

            ItemStack remainder = ItemHandlerHelper.insertItem(this.inv, simulated, false);
            int extractedAmount = simulated.getCount() - remainder.getCount();
            if (extractedAmount > 0) {
                handler.extractItem(i, extractedAmount, false);
                this.setChanged();
                break;
            }
        }
    }

    private void doPutting() {
        if (this.sideToPut == -1 || this.level == null) return;
        ItemStack buffer = this.inv.getStackInSlot(0);
        if (buffer.isEmpty()) return;
        if (this.isAdvanced && this.rightFilter != null && !this.rightFilter.check(buffer)) return;

        Direction dir = getDirectionFromSide(this.sideToPut);
        if (dir == null) return;

        BlockPos targetPos = this.worldPosition.relative(dir);
        if (!this.level.isLoaded(targetPos)) return;

        BlockEntity targetTile = this.level.getBlockEntity(targetPos);
        if (targetTile == null) return;

        IItemHandler handler = targetTile.getCapability(ForgeCapabilities.ITEM_HANDLER, dir.getOpposite()).orElse(null);
        if (handler == null) return;

        int start = Math.min(this.slotToPutStart, handler.getSlots() - 1);
        int end = this.slotToPutEnd <= 0 ? handler.getSlots() : Math.min(this.slotToPutEnd, handler.getSlots());

        for (int i = Math.max(0, start); i < end; i++) {
            ItemStack remainder = handler.insertItem(i, buffer, false);
            if (remainder.getCount() != buffer.getCount()) {
                this.inv.setStackInSlot(0, remainder);
                this.setChanged();
                break;
            }
        }
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityInputter tile) {
            if (level.getGameTime() % 30 == 0) {
                if (!tile.isRedstonePowered) {
                    if (tile.inv.getStackInSlot(0).isEmpty()) {
                        tile.doPulling();
                    }
                    if (!tile.inv.getStackInSlot(0).isEmpty()) {
                        tile.doPutting();
                    }
                }
            }
        }
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("SideToPut", this.sideToPut);
            compound.putInt("SlotToPut", this.slotToPutStart);
            compound.putInt("SlotToPutEnd", this.slotToPutEnd);
            compound.putInt("SideToPull", this.sideToPull);
            compound.putInt("SlotToPull", this.slotToPullStart);
            compound.putInt("SlotToPullEnd", this.slotToPullEnd);
        }
        if (this.isAdvanced) {
            if (this.leftFilter != null) this.leftFilter.writeToNBT(compound, "LeftFilter");
            if (this.rightFilter != null) this.rightFilter.writeToNBT(compound, "RightFilter");
        }
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            this.sideToPut = compound.getInt("SideToPut");
            this.slotToPutStart = compound.getInt("SlotToPut");
            this.slotToPutEnd = compound.getInt("SlotToPutEnd");
            this.sideToPull = compound.getInt("SideToPull");
            this.slotToPullStart = compound.getInt("SlotToPull");
            this.slotToPullEnd = compound.getInt("SlotToPullEnd");
        }
        if (this.isAdvanced) {
            if (this.leftFilter != null) this.leftFilter.readFromNBT(compound, "LeftFilter");
            if (this.rightFilter != null) this.rightFilter.readFromNBT(compound, "RightFilter");
        }
        super.readSyncableNBT(compound, type);
    }

    @Override
    public boolean shouldSyncSlots() {
        return true;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(this.isAdvanced ? "container.actuallyadditions.inputterAdvanced" : "container.actuallyadditions.inputter");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
        return new ContainerInputter(windowId, playerInventory, this, this.isAdvanced);
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !automation || slot == 0;
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation || slot == 0;
    }
}
