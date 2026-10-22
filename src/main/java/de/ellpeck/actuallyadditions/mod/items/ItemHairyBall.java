/*
 * This file ("ItemHairyBall.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.BallOfFurReturn;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class ItemHairyBall extends ItemBase {

    public ItemHairyBall() {
        super(ActuallyItems.defaultProps().rarity(Rarity.EPIC));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void livingTickEvent(LivingEvent.LivingTickEvent event) {
        LivingEntity living = event.getEntity();
        if (living != null && !living.level().isClientSide && living instanceof Cat cat && cat.isTame()) {
            if (living.level().random.nextInt(10000) == 0) {
                ItemEntity item = new ItemEntity(living.level(), living.getX() + 0.5, living.getY() + 0.5, living.getZ() + 0.5, new ItemStack(this));
                living.level().addFreshEntity(item);
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide) {
            ItemStack returnItem = getRandomReturnItem(world.random);
            if (!player.getInventory().add(returnItem)) {
                ItemEntity entityItem = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), returnItem);
                entityItem.setPickUpDelay(0);
                world.addFreshEntity(entityItem);
            }
            stack.shrink(1);
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, world.random.nextFloat() * 0.1F + 0.9F);
        }
        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide);
    }

    public static ItemStack getRandomReturnItem(RandomSource rand) {
        List<BallOfFurReturn> items = ActuallyAdditionsAPI.BALL_OF_FUR_RETURN_ITEMS;
        if (items.isEmpty()) {
            return new ItemStack(net.minecraft.world.item.Items.STRING);
        }
        int totalWeight = 0;
        for (BallOfFurReturn entry : items) {
            totalWeight += entry.weight;
        }
        if (totalWeight <= 0) return new ItemStack(net.minecraft.world.item.Items.STRING);
        int rolled = rand.nextInt(totalWeight);
        int current = 0;
        for (BallOfFurReturn entry : items) {
            current += entry.weight;
            if (rolled < current) {
                return entry.returnItem.copy();
            }
        }
        return items.get(0).returnItem.copy();
    }
}
