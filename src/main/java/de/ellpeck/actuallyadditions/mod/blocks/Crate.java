package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.tile.CrateBE;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class Crate extends BlockContainerBase {
    private final Size size;

    public Crate(Size size) {
        super(ActuallyBlocks.defaultPickProps().pushReaction(PushReaction.BLOCK));
        this.size = size;
    }

    public Size getSize() {
        return size;
    }

    public enum Size {
        SMALL(117, 1),
        MEDIUM(234, 1),
        LARGE(351, 1);

        private final int slots;
        private final int stackBoost;

        Size(int slots, int stackBoost) {
            this.slots = slots;
            this.stackBoost = stackBoost;
        }

        public int getSlots() {
            return slots;
        }

        public int getStackBoost() {
            return stackBoost;
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CrateBE(pPos, pState, this.size);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide && player instanceof ServerPlayer serverPlayer) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof CrateBE crate) {
                crate.openMenu(serverPlayer, 0);
            }
        }
        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    @Override
    public boolean shouldDropInventory(Level world, BlockPos pos) {
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof CrateBE crate) {
            for (int i = 0; i < crate.inv.getSlots(); i++) {
                if (crate.inv.getStackInSlot(i).is(ActuallyItems.CRATE_KEEPER.get())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void playerDestroy(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (!world.isClientSide && blockEntity instanceof CrateBE crate) {
            int keeperSlot = -1;
            for (int i = 0; i < crate.inv.getSlots(); i++) {
                if (crate.inv.getStackInSlot(i).is(ActuallyItems.CRATE_KEEPER.get())) {
                    keeperSlot = i;
                    break;
                }
            }

            if (keeperSlot >= 0) {
                crate.inv.setStackInSlot(keeperSlot, ItemStack.EMPTY);

                ItemStack crateItem = new ItemStack(this);
                CompoundTag blockEntityTag = new CompoundTag();
                blockEntityTag.put("Items", crate.inv.serializeNBT());
                crateItem.getOrCreateTag().put("BlockEntityTag", blockEntityTag);

                popResource(world, pos, crateItem);
                return;
            }
        }
        super.playerDestroy(world, player, pos, state, blockEntity, tool);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(world, pos, state, placer, stack);
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("BlockEntityTag")) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof CrateBE crate) {
                CompoundTag beTag = tag.getCompound("BlockEntityTag");
                if (beTag.contains("Items")) {
                    crate.inv.deserializeNBT(beTag.getCompound("Items"));
                    crate.setChanged();
                }
            }
        }
    }
}
