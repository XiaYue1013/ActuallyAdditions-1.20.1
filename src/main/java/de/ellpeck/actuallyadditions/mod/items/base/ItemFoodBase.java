/*
 * This file ("ItemFoodBase.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.base;

import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class ItemFoodBase extends ItemBase {

    private final Supplier<ItemStack> returnItem;
    private final boolean isDrink;

    public ItemFoodBase(FoodProperties foodProperties) {
        this(foodProperties, Rarity.COMMON, null, false);
    }

    public ItemFoodBase(int heal, float saturation, boolean wolfFood) {
        this(wolfFood 
            ? new FoodProperties.Builder().nutrition(heal).saturationMod(saturation).meat().build() 
            : new FoodProperties.Builder().nutrition(heal).saturationMod(saturation).build());
    }

    public ItemFoodBase(FoodProperties foodProperties, Rarity rarity) {
        this(foodProperties, rarity, null, false);
    }

    public ItemFoodBase(FoodProperties foodProperties, Rarity rarity, Supplier<ItemStack> returnItem, boolean isDrink) {
        super(ActuallyItems.defaultProps().food(foodProperties).rarity(rarity));
        this.returnItem = returnItem;
        this.isDrink = isDrink;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return this.isDrink ? UseAnim.DRINK : UseAnim.EAT;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, world, entity);
        if (this.returnItem != null && entity instanceof Player player && !player.getAbilities().instabuild) {
            ItemStack ret = this.returnItem.get().copy();
            if (!ret.isEmpty()) {
                if (result.isEmpty()) {
                    return ret;
                }
                if (!player.getInventory().add(ret)) {
                    player.drop(ret, false);
                }
            }
        }
        return result;
    }
}
