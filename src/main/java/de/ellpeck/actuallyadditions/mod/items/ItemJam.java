/*
 * This file ("ItemJam.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemFoodBase;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheJams;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ItemJam extends ItemFoodBase {

    private final TheJams jam;

    public ItemJam(TheJams jam) {
        super(
            new FoodProperties.Builder()
                .nutrition(jam.healAmount)
                .saturationMod(jam.saturation)
                .alwaysEat()
                .build(),
            jam.rarity,
            () -> new ItemStack(Items.GLASS_BOTTLE),
            true
        );
        this.jam = jam;
    }

    public TheJams getJam() {
        return this.jam;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, world, entity);
        if (!world.isClientSide) {
            MobEffect first = this.jam.getFirstEffect();
            if (first != null) {
                entity.addEffect(new MobEffectInstance(first, 200));
            }
            MobEffect second = this.jam.getSecondEffect();
            if (second != null) {
                entity.addEffect(new MobEffectInstance(second, 600));
            }
        }
        return result;
    }
}
