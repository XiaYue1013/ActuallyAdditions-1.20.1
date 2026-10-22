/*
 * This file ("ItemWaterRemovalRing.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;

public class ItemWaterRemovalRing extends ItemEnergy {

    public ItemWaterRemovalRing() {
        super(800000, 1000);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity player, int itemSlot, boolean isSelected) {
        if (!(player instanceof Player thePlayer) || world.isClientSide || player.isShiftKeyDown()) {
            return;
        }

        ItemStack mainHand = thePlayer.getMainHandItem();
        ItemStack offHand = thePlayer.getOffhandItem();
        boolean isEquipped = (StackUtil.isValid(mainHand) && mainHand == stack) || (StackUtil.isValid(offHand) && offHand == stack);

        int energyUse = 150;
        if (isEquipped && this.getEnergyStored(stack) >= energyUse) {
            int range = 3;
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    for (int y = -range; y <= range; y++) {
                        int theX = Mth.floor(player.getX() + x);
                        int theY = Mth.floor(player.getY() + y);
                        int theZ = Mth.floor(player.getZ() + z);

                        BlockPos pos = new BlockPos(theX, theY, theZ);
                        BlockState state = world.getBlockState(pos);
                        FluidState fluid = world.getFluidState(pos);

                        if (state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) && this.getEnergyStored(stack) >= energyUse) {
                            world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.WATERLOGGED, false));
                            if (!thePlayer.isCreative()) {
                                this.extractEnergyInternal(stack, energyUse, false);
                            }
                        } else if ((fluid.is(FluidTags.WATER) || state.is(Blocks.WATER)) && this.getEnergyStored(stack) >= energyUse) {
                            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                            if (!thePlayer.isCreative()) {
                                this.extractEnergyInternal(stack, energyUse, false);
                            }
                        } else if ((fluid.is(FluidTags.LAVA) || state.is(Blocks.LAVA)) && this.getEnergyStored(stack) >= energyUse * 2) {
                            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                            if (!thePlayer.isCreative()) {
                                this.extractEnergyInternal(stack, energyUse * 2, false);
                            }
                        }
                    }
                }
            }
        }
    }
}
