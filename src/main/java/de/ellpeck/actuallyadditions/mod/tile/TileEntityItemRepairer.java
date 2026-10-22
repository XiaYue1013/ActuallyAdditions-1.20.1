/*
 * This file ("TileEntityItemRepairer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerRepairer;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class TileEntityItemRepairer extends TileEntityInventoryBase implements MenuProvider, IEnergyDisplay {

    public static final int SLOT_INPUT = 0;
    public static final int SLOT_OUTPUT = 1;
    public static final int ENERGY_USE = 2500;

    public final CustomEnergyStorage storage = new CustomEnergyStorage(300000, 6000, 0);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);

    public int nextRepairTick;
    private int lastEnergy;

    private static final Set<String> REPAIR_BLACKLIST = new HashSet<>();

    public TileEntityItemRepairer(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.ITEM_REPAIRER.getTileEntityType(), pos, state, 2);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityItemRepairer tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityItemRepairer tile) {
            tile.serverTick();
        }
    }

    public static boolean canBeRepaired(ItemStack stack) {
        if (!stack.isEmpty() && stack.isDamageableItem()) {
            if (isBlacklisted(stack)) {
                return false;
            }
            return stack.getDamageValue() > 0;
        }
        return false;
    }

    public static boolean isBlacklisted(ItemStack stack) {
        if (stack.isEmpty()) return true;
        String name = net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(stack.getItem()).toString();
        return REPAIR_BLACKLIST.contains(name);
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("NextRepairTick", this.nextRepairTick);
        }
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            this.nextRepairTick = compound.getInt("NextRepairTick");
        }
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
    }

    @Override
    public void serverTick() {
        super.serverTick();
        if (this.level != null && !this.level.isClientSide) {
            ItemStack input = this.inv.getStackInSlot(SLOT_INPUT);
            ItemStack output = this.inv.getStackInSlot(SLOT_OUTPUT);

            if (output.isEmpty() && canBeRepaired(input)) {
                if (input.getDamageValue() <= 0) {
                    this.inv.setStackInSlot(SLOT_OUTPUT, input.copy());
                    this.inv.setStackInSlot(SLOT_INPUT, ItemStack.EMPTY);
                    this.nextRepairTick = 0;
                    this.setChanged();
                } else {
                    if (this.storage.getEnergyStored() >= ENERGY_USE) {
                        this.nextRepairTick++;
                        this.storage.extractEnergyInternal(ENERGY_USE, false);
                        if (this.nextRepairTick >= 4) {
                            this.nextRepairTick = 0;
                            input.setDamageValue(Math.max(0, input.getDamageValue() - 1));
                            if (input.getDamageValue() <= 0) {
                                this.inv.setStackInSlot(SLOT_OUTPUT, input.copy());
                                this.inv.setStackInSlot(SLOT_INPUT, ItemStack.EMPTY);
                            }
                            this.setChanged();
                        }
                    }
                }
            } else {
                this.nextRepairTick = 0;
            }

            if (this.lastEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()) {
                this.lastEnergy = this.storage.getEnergyStored();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getEnergyScaled(int i) {
        return this.storage.getEnergyStored() * i / this.storage.getMaxEnergyStored();
    }

    public int getItemDamageToScale(int i) {
        ItemStack stack = this.inv.getStackInSlot(SLOT_INPUT);
        if (!stack.isEmpty() && stack.getMaxDamage() > 0) {
            return (stack.getMaxDamage() - stack.getDamageValue()) * i / stack.getMaxDamage();
        }
        return 0;
    }

    @Override
    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }

    @Override
    public IAcceptor getAcceptor() {
        return (slot, stack, automation) -> !isBlacklisted(stack) && (!automation || slot == SLOT_INPUT);
    }

    @Override
    public IRemover getRemover() {
        return (slot, automation) -> !automation || slot == SLOT_OUTPUT;
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
    public Component getDisplayName() {
        return Component.translatable("container.actuallyadditions.repairer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
        return new ContainerRepairer(windowId, playerInventory, this);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        this.lazyEnergy.invalidate();
    }
}
