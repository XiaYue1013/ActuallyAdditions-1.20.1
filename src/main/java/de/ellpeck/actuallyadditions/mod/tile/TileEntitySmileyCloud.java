/*
 * This file ("TileEntitySmileyCloud.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerSmileyCloud;
import de.ellpeck.actuallyadditions.mod.network.gui.IStringReactor;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class TileEntitySmileyCloud extends TileEntityBase implements IStringReactor, MenuProvider {

    public String name;
    private String nameBefore;

    public TileEntitySmileyCloud(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.SMILEY_CLOUD.getTileEntityType(), pos, state);
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (this.name != null && type != NBTType.SAVE_BLOCK) {
            compound.putString("Name", this.name);
        }
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            this.name = compound.getString("Name");
        }
    }

    @Override
    public void onTextReceived(String text, int textID, Player player) {
        this.name = text;
        this.setChanged();
        this.sendUpdate();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.actuallyadditions.cloud.name");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        return new ContainerSmileyCloud(windowId, inventory, this);
    }
}
