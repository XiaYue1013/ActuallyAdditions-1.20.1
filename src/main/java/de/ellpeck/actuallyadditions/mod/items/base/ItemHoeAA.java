/*
 * This file ("ItemHoeAA.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.base;

import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Tier;

public class ItemHoeAA extends HoeItem {
    public ItemHoeAA(Tier tier) {
        super(tier, -1, -2.0F, ActuallyItems.defaultProps());
    }
}
