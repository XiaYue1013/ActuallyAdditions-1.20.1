/*
 * This file ("ItemPickaxeAA.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;

public class ItemPickaxeAA extends PickaxeItem {
    public ItemPickaxeAA(Tier tier) {
        super(tier, 1, -2.8F, ActuallyItems.defaultProps());
    }
}
