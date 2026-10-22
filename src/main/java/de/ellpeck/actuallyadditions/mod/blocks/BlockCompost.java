/*
 * This file ("BlockCompost.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import com.mojang.blaze3d.platform.Window;
import de.ellpeck.actuallyadditions.api.recipe.CompostRecipe;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCompost;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class BlockCompost extends BlockContainerBase implements IHudDisplay {

    protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 11.0D, 15.0D);

    public BlockCompost() {
        super(BlockBehaviour.Properties.of().strength(0.5F, 5.0F).sound(SoundType.WOOD).noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntityCompost(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
        return level.isClientSide ? TileEntityCompost::clientTick : TileEntityCompost::serverTick;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stackPlayer = player.getItemInHand(hand);
        if (!world.isClientSide) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileEntityCompost compost) {
                ItemStack slot = compost.inv.getStackInSlot(0);
                CompostRecipe recipeIn = TileEntityCompost.getRecipeForInput(slot);
                if (slot.isEmpty() || recipeIn != null) {
                    if (!stackPlayer.isEmpty()) {
                        CompostRecipe recipeHand = TileEntityCompost.getRecipeForInput(stackPlayer);
                        if (recipeHand != null && (recipeIn == null || recipeIn == recipeHand)) {
                            int maxAdd = stackPlayer.getCount();
                            if (slot.isEmpty()) {
                                ItemStack stackToAdd = stackPlayer.copy();
                                stackToAdd.setCount(maxAdd);
                                compost.inv.setStackInSlot(0, stackToAdd);
                                stackPlayer.shrink(maxAdd);
                                compost.setChanged();
                                world.sendBlockUpdated(pos, state, state, 3);
                                return InteractionResult.SUCCESS;
                            } else if (ItemStack.isSameItemSameTags(slot, stackPlayer)) {
                                int sizeAdded = Math.min(maxAdd, slot.getMaxStackSize() - slot.getCount());
                                if (sizeAdded > 0) {
                                    slot.grow(sizeAdded);
                                    stackPlayer.shrink(sizeAdded);
                                    compost.setChanged();
                                    world.sendBlockUpdated(pos, state, state, 3);
                                    return InteractionResult.SUCCESS;
                                }
                            }
                        }
                    }
                } else {
                    if (stackPlayer.isEmpty()) {
                        player.setItemInHand(hand, slot.copy());
                        compost.inv.setStackInSlot(0, ItemStack.EMPTY);
                        compost.setChanged();
                        world.sendBlockUpdated(pos, state, state, 3);
                        return InteractionResult.SUCCESS;
                    } else if (ItemStack.isSameItemSameTags(stackPlayer, slot)) {
                        int addedStackSize = Math.min(slot.getCount(), stackPlayer.getMaxStackSize() - stackPlayer.getCount());
                        if (addedStackSize > 0) {
                            stackPlayer.grow(addedStackSize);
                            slot.shrink(addedStackSize);
                            if (slot.isEmpty()) {
                                compost.inv.setStackInSlot(0, ItemStack.EMPTY);
                            }
                            compost.setChanged();
                            world.sendBlockUpdated(pos, state, state, 3);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(GuiGraphics guiGraphics, Minecraft minecraft, Player player, ItemStack stack, HitResult rayCast, Window resolution) {
        if (rayCast instanceof BlockHitResult blockHit) {
            BlockEntity tile = minecraft.level.getBlockEntity(blockHit.getBlockPos());
            if (tile instanceof TileEntityCompost compost) {
                ItemStack slot = compost.inv.getStackInSlot(0);
                String strg;
                if (slot.isEmpty()) {
                    strg = "Empty";
                } else {
                    strg = slot.getHoverName().getString() + " (" + slot.getCount() + "/" + slot.getMaxStackSize() + ")";
                    guiGraphics.renderItem(slot, resolution.getGuiScaledWidth() / 2 + 15, resolution.getGuiScaledHeight() / 2 - 29);
                    if (compost.conversionTime > 0) {
                        int progress = (int) (((float) compost.conversionTime / (float) TileEntityCompost.COMPOST_TIME_TICKS) * 100);
                        strg += " - " + progress + "%";
                    }
                }
                guiGraphics.drawString(minecraft.font, ChatFormatting.YELLOW + "" + ChatFormatting.ITALIC + strg, resolution.getGuiScaledWidth() / 2 + 35, resolution.getGuiScaledHeight() / 2 - 25, 0xFFFFFFFF);
            }
        }
    }
}
