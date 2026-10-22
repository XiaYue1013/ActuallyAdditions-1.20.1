/*
 * This file ("ItemPotionRing.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.api.misc.IDisplayStandItem;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.items.metalists.ThePotionRings;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class ItemPotionRing extends ItemBase implements IDisplayStandItem {

    public static final int MAX_BLAZE = 800;
    private final ThePotionRings ringType;
    private final boolean isAdvanced;

    public ItemPotionRing(ThePotionRings ringType, boolean isAdvanced) {
        super(ActuallyItems.defaultNonStacking().rarity(ringType.rarity));
        this.ringType = ringType;
        this.isAdvanced = isAdvanced;
    }

    public ThePotionRings getRingType() {
        return this.ringType;
    }

    public boolean isAdvanced() {
        return this.isAdvanced;
    }

    public static int getStoredBlaze(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null ? tag.getInt("Blaze") : 0;
    }

    public static void setStoredBlaze(ItemStack stack, int amount) {
        stack.getOrCreateTag().putInt("Blaze", amount);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round((float) getStoredBlaze(stack) * 13.0F / (float) MAX_BLAZE);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        float f = Math.max(0.0F, (float) getStoredBlaze(stack) / (float) MAX_BLAZE);
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity player, int itemSlot, boolean isSelected) {
        if (!world.isClientSide && player instanceof Player thePlayer) {
            int storedBlaze = getStoredBlaze(stack);
            if (storedBlaze > 0) {
                ItemStack mainHand = thePlayer.getMainHandItem();
                ItemStack offHand = thePlayer.getOffhandItem();
                boolean isHeld = (mainHand == stack || offHand == stack);
                if (this.isAdvanced || isHeld) {
                    if (this.effectEntity(thePlayer)) {
                        if (world.getGameTime() % 10 == 0) {
                            setStoredBlaze(stack, storedBlaze - 1);
                        }
                    }
                }
            }
        }
    }

    public boolean effectEntity(LivingEntity entity) {
        MobEffect effect = this.ringType.effect;
        MobEffectInstance active = entity.getEffect(effect);
        if (!this.ringType.needsWaitBeforeActivating || active == null || active.getDuration() <= 1) {
            int amplifier = this.isAdvanced ? this.ringType.advancedAmplifier : this.ringType.normalAmplifier;
            entity.addEffect(new MobEffectInstance(effect, this.ringType.activeTime, amplifier, true, false));
            return true;
        }
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        InteractionHand otherHand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack otherStack = player.getItemInHand(otherHand);
        if (otherStack.is(Items.BLAZE_POWDER)) {
            int current = getStoredBlaze(stack);
            if (current < MAX_BLAZE) {
                int needed = (MAX_BLAZE - current + 19) / 20;
                int toConsume = Math.min(needed, otherStack.getCount());
                int newBlaze = Math.min(MAX_BLAZE, current + toConsume * 20);
                setStoredBlaze(stack, newBlaze);
                if (!player.getAbilities().instabuild) {
                    otherStack.shrink(toConsume);
                }
                player.playSound(SoundEvents.BLAZE_SHOOT, 0.5F, 1.5F);
                return InteractionResultHolder.sidedSuccess(stack, world.isClientSide);
            }
        }
        return super.use(world, player, hand);
    }

    @Override
    public boolean update(ItemStack stack, BlockEntity tile, int elapsedTicks) {
        int range = this.isAdvanced ? 48 : 16;
        BlockPos pos = tile.getBlockPos();
        Level level = tile.getLevel();
        if (level == null) return false;
        AABB aabb = new AABB(pos).inflate(range);
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, aabb);
        if (!entities.isEmpty()) {
            if (this.isAdvanced) {
                for (LivingEntity e : entities) {
                    this.effectEntity(e);
                }
                return true;
            } else {
                for (LivingEntity e : entities) {
                    if (e.hasEffect(this.ringType.effect)) {
                        this.effectEntity(e);
                        return true;
                    }
                }
                Collections.shuffle(entities);
                this.effectEntity(entities.get(0));
                return true;
            }
        }
        return false;
    }

    @Override
    public int getUsePerTick(ItemStack stack, BlockEntity tile, int elapsedTicks) {
        return 325;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.actuallyadditions.potion_ring.storage", getStoredBlaze(stack), MAX_BLAZE).withStyle(ChatFormatting.GRAY));
    }
}
