/*
 * This file ("TileEntityFishingNet.java") is part of the Actually Additions mod for Minecraft.
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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class TileEntityFishingNet extends TileEntityBase {

    public int timeUntilNextDrop;

    public TileEntityFishingNet(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.FISHING_NET.getTileEntityType(), pos, state);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityFishingNet tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityFishingNet tile) {
            tile.serverTick();
        }
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("TimeUntilNextDrop", this.timeUntilNextDrop);
        }
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            this.timeUntilNextDrop = compound.getInt("TimeUntilNextDrop");
        }
    }

    @Override
    public void serverTick() {
        super.serverTick();
        if (this.level != null && !this.level.isClientSide && !this.isRedstonePowered) {
            if (this.level.getFluidState(this.worldPosition.below()).is(FluidTags.WATER)) {
                if (this.timeUntilNextDrop > 0) {
                    this.timeUntilNextDrop--;
                    if (this.timeUntilNextDrop <= 0) {
                        if (this.level instanceof ServerLevel serverLevel) {
                            LootTable lootTable = serverLevel.getServer().getLootData().getLootTable(BuiltInLootTables.FISHING);
                            LootParams params = new LootParams.Builder(serverLevel)
                                    .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.worldPosition))
                                    .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                                    .create(LootContextParamSets.FISHING);
                            List<ItemStack> fishables = lootTable.getRandomItems(params);
                            for (ItemStack fishable : fishables) {
                                ItemStack leftover = this.storeInContainer(fishable);
                                if (!leftover.isEmpty()) {
                                    ItemEntity item = new ItemEntity(serverLevel, this.worldPosition.getX() + 0.5D, this.worldPosition.getY() + 0.5D, this.worldPosition.getZ() + 0.5D, leftover.copy());
                                    item.lifespan = 2000;
                                    serverLevel.addFreshEntity(item);
                                }
                            }
                        }
                    }
                } else {
                    int time = 15000;
                    this.timeUntilNextDrop = time + (this.level.random.nextInt(Math.max(1, time / 2)));
                    this.setChanged();
                }
            }
        }
    }

    private ItemStack storeInContainer(ItemStack stack) {
        for (Direction side : Direction.values()) {
            BlockEntity tile = this.tilesAround[side.ordinal()];
            if (tile != null) {
                LazyOptional<IItemHandler> cap = tile.getCapability(ForgeCapabilities.ITEM_HANDLER, side.getOpposite());
                if (cap.isPresent()) {
                    IItemHandler handler = cap.orElse(null);
                    if (handler != null) {
                        for (int i = 0; i < handler.getSlots(); i++) {
                            stack = handler.insertItem(i, stack, false);
                            if (stack.isEmpty()) {
                                return ItemStack.EMPTY;
                            }
                        }
                    }
                }
            }
        }
        return stack;
    }

    @Override
    public boolean shouldSaveDataOnChangeOrWorldStart() {
        return true;
    }
}
