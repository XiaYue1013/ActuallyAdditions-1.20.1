/*
 * This file ("BallOfFurReturn.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.recipe;

import net.minecraft.world.item.ItemStack;

public class BallOfFurReturn {
    public final ItemStack returnItem;
    public final int weight;

    public BallOfFurReturn(ItemStack returnItem, int weight) {
        this.returnItem = returnItem;
        this.weight = weight;
    }
}
