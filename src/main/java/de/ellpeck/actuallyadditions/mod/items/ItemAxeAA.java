/*
 * This file ("ItemAxeAA.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;

public class ItemAxeAA extends AxeItem {
    public ItemAxeAA(Tier tier) {
        super(tier, 5.0F, -3.0F, ActuallyItems.defaultProps());
    }
}
