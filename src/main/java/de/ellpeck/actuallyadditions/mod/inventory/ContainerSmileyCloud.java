/*
 * This file ("ContainerSmileyCloud.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.tile.TileEntitySmileyCloud;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class ContainerSmileyCloud extends AbstractContainerMenu {

    public final TileEntitySmileyCloud cloud;

    public static ContainerSmileyCloud fromNetwork(int windowId, Inventory inv, FriendlyByteBuf data) {
        TileEntitySmileyCloud tile = (TileEntitySmileyCloud) Objects.requireNonNull(inv.player.level().getBlockEntity(data.readBlockPos()));
        return new ContainerSmileyCloud(windowId, inv, tile);
    }

    public ContainerSmileyCloud(int windowId, Inventory inventory, TileEntitySmileyCloud tile) {
        super(ActuallyContainers.CLOUD_CONTAINER.get(), windowId);
        this.cloud = tile;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.cloud.canPlayerUse(player);
    }
}
