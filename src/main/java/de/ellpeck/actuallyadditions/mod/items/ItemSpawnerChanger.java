/*
 * This file ("ItemSpawnerChanger.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSpawnerChanger extends ItemBase {

    public ItemSpawnerChanger() {
        super(ActuallyItems.defaultNonStacking());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        if (world.getBlockState(pos).is(Blocks.SPAWNER)) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof SpawnerBlockEntity spawner) {
                String entityStr = getStoredEntity(stack);
                if (entityStr != null && !entityStr.isEmpty()) {
                    if (!world.isClientSide) {
                        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation(entityStr));
                        if (type != null) {
                            spawner.getSpawner().setEntityId(type, world, world.random, pos);
                            tile.setChanged();
                            world.sendBlockUpdated(pos, world.getBlockState(pos), world.getBlockState(pos), 3);

                            if (player != null && !player.getAbilities().instabuild) {
                                stack.shrink(1);
                            }
                        }
                    }
                    return InteractionResult.sidedSuccess(world.isClientSide);
                }
            }
        }
        return super.useOn(context);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        if (!player.level().isClientSide) {
            if (!(entity instanceof Player) && entity.canChangeDimensions() && getStoredEntity(stack) == null) {
                ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
                if (key != null) {
                    stack.getOrCreateTag().putString("Entity", key.toString());
                    entity.discard();
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.sidedSuccess(player.level().isClientSide);
    }

    public static String getStoredEntity(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains("Entity") ? tag.getString("Entity") : null;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        String entity = getStoredEntity(stack);
        if (entity != null) {
            tooltip.add(Component.literal("Entity: " + entity).withStyle(ChatFormatting.GREEN));
        }
    }
}
