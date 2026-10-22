/*
 * This file ("ContainerInputter.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotFilter;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotItemHandlerUnconditioned;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityInputter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class ContainerInputter extends AbstractContainerMenu {

    public final TileEntityInputter tileInputter;
    public final boolean isAdvanced;

    public static ContainerInputter fromNetwork(int windowId, Inventory inv, FriendlyByteBuf data) {
        TileEntityInputter tile = (TileEntityInputter) Objects.requireNonNull(inv.player.level().getBlockEntity(data.readBlockPos()));
        return new ContainerInputter(windowId, inv, tile, false);
    }

    public static ContainerInputter fromNetworkAdvanced(int windowId, Inventory inv, FriendlyByteBuf data) {
        TileEntityInputter tile = (TileEntityInputter) Objects.requireNonNull(inv.player.level().getBlockEntity(data.readBlockPos()));
        return new ContainerInputter(windowId, inv, tile, true);
    }

    public ContainerInputter(int windowId, Inventory inventory, TileEntityInputter tile, boolean isAdvanced) {
        super(isAdvanced ? ActuallyContainers.INPUTTER_ADVANCED_CONTAINER.get() : ActuallyContainers.INPUTTER_CONTAINER.get(), windowId);
        this.tileInputter = tile;
        this.isAdvanced = isAdvanced;

        this.addSlot(new SlotItemHandlerUnconditioned(this.tileInputter.inv, 0, 80, 21 + (isAdvanced ? 12 : 0)));

        if (isAdvanced) {
            for (int i = 0; i < 2; i++) {
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 4; y++) {
                        this.addSlot(new SlotFilter(i == 0 ? this.tileInputter.leftFilter : this.tileInputter.rightFilter, y + x * 4, 20 + i * 84 + x * 18, 6 + y * 18));
                    }
                }
            }
        }

        int offset = isAdvanced ? 48 : 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 101 + i * 18 + offset));
            }
        }
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 159 + offset));
        }
    }

    @Override
    public void clicked(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (SlotFilter.checkFilter(this, slotId, player)) {
            return;
        }
        super.clicked(slotId, dragType, clickTypeIn, player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        int inventoryStart = this.isAdvanced ? 25 : 1;
        int inventoryEnd = inventoryStart + 26;
        int hotbarStart = inventoryEnd + 1;
        int hotbarEnd = hotbarStart + 8;

        Slot theSlot = this.slots.get(slot);

        if (theSlot != null && theSlot.hasItem()) {
            ItemStack newStack = theSlot.getItem();
            ItemStack currentStack = newStack.copy();

            if (slot >= inventoryStart) {
                if (!this.moveItemStackTo(newStack, 0, 1, false)) {
                    if (slot <= inventoryEnd) {
                        if (!this.moveItemStackTo(newStack, hotbarStart, hotbarEnd + 1, false)) return ItemStack.EMPTY;
                    } else if (!this.moveItemStackTo(newStack, inventoryStart, inventoryEnd + 1, false)) return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(newStack, inventoryStart, hotbarEnd + 1, false)) return ItemStack.EMPTY;

            if (newStack.isEmpty()) {
                theSlot.set(ItemStack.EMPTY);
            } else {
                theSlot.setChanged();
            }

            if (newStack.getCount() == currentStack.getCount()) return ItemStack.EMPTY;
            theSlot.onTake(player, newStack);

            return currentStack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.tileInputter.canPlayerUse(player);
    }
}
