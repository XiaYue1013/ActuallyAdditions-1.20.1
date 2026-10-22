/*
 * This file ("ItemChestToCrateUpgrade.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ItemChestToCrateUpgrade extends ItemBase {

    private final Predicate<BlockEntity> targetPredicate;
    private final Supplier<BlockState> targetState;

    public ItemChestToCrateUpgrade(Predicate<BlockEntity> targetPredicate, Supplier<BlockState> targetState) {
        super(ActuallyItems.defaultNonStacking().rarity(Rarity.RARE));
        this.targetPredicate = targetPredicate;
        this.targetState = targetState;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null || !player.isShiftKeyDown()) {
            return InteractionResult.PASS;
        }

        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockEntity tile = world.getBlockEntity(pos);

        if (tile != null && this.targetPredicate.test(tile)) {
            if (!world.isClientSide) {
                // Extract items from old tile
                List<ItemStack> items = new ArrayList<>();
                tile.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                    for (int i = 0; i < handler.getSlots(); i++) {
                        ItemStack s = handler.getStackInSlot(i);
                        if (!s.isEmpty()) {
                            items.add(s.copy());
                        }
                    }
                });

                // Replace block
                world.levelEvent(2001, pos, Block.getId(world.getBlockState(pos)));
                BlockState newState = this.targetState.get();
                world.setBlock(pos, newState, 3);

                // Insert into new block entity
                BlockEntity newTile = world.getBlockEntity(pos);
                if (newTile != null) {
                    newTile.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                        if (handler instanceof IItemHandlerModifiable modifiable) {
                            for (int i = 0; i < items.size() && i < modifiable.getSlots(); i++) {
                                modifiable.setStackInSlot(i, items.get(i));
                            }
                        }
                    });
                    newTile.setChanged();
                }

                if (!player.getAbilities().instabuild) {
                    context.getItemInHand().shrink(1);
                }
                world.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        return InteractionResult.PASS;
    }
}
