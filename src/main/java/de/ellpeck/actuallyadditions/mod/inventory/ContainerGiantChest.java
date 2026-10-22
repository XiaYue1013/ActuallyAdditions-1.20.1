/*
 * This file ("ContainerGiantChest.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotItemHandlerUnconditioned;
import de.ellpeck.actuallyadditions.mod.tile.CrateBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ContainerGiantChest extends AbstractContainerMenu {

    public final CrateBE tileChest;
    public final int page;

    public static ContainerGiantChest fromNetwork(int windowId, Inventory inv, FriendlyByteBuf data) {
        BlockPos pos = data.readBlockPos();
        int page = data.readInt();
        CrateBE tile = (CrateBE) inv.player.level().getBlockEntity(pos);
        return new ContainerGiantChest(ActuallyContainers.CRATE_CONTAINER.get(), windowId, inv, tile, page);
    }

    public ContainerGiantChest(MenuType<?> type, int windowId, Inventory inventory, CrateBE tile, int page) {
        super(type, windowId);
        this.tileChest = tile;
        this.page = page;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 13; j++) {
                int slotIndex = 9 * 13 * page + j + i * 13;
                this.addSlot(new SlotItemHandlerUnconditioned(this.tileChest.inv, slotIndex, 5 + j * 18, 5 + i * 18));
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 33 + 8 + j * 18, 172 + 4 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, 33 + 8 + i * 18, 172 + 62));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);

        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemstack = slotStack.copy();

            int crateSlots = 117;
            int invStart = crateSlots;
            int invEnd = invStart + 27;
            int hotbarStart = invEnd;
            int hotbarEnd = hotbarStart + 9;

            if (slotIndex < crateSlots) {
                if (!this.moveItemStackTo(slotStack, invStart, hotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(slotStack, 0, crateSlots, false)) {
                    if (slotIndex < invEnd) {
                        if (!this.moveItemStackTo(slotStack, hotbarStart, hotbarEnd, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (slotIndex < hotbarEnd) {
                        if (!this.moveItemStackTo(slotStack, invStart, invEnd, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotStack);
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.tileChest.canPlayerUse(player);
    }
}
