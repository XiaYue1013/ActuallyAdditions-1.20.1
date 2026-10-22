/*
 * This file ("TheJams.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.metalists;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Rarity;

import java.util.function.Supplier;

public enum TheJams {

    CU_BA_RA("cu_ba_ra", 6, 0.1F, Rarity.RARE, () -> MobEffects.DAMAGE_BOOST, () -> MobEffects.FIRE_RESISTANCE, 12595273),
    GRA_KI_BA("gra_ki_ba", 6, 0.1F, Rarity.RARE, () -> MobEffects.NIGHT_VISION, () -> MobEffects.WATER_BREATHING, 5492820),
    PL_AP_LE("pl_ap_le", 6, 0.1F, Rarity.RARE, () -> MobEffects.BLINDNESS, () -> MobEffects.DIG_SPEED, 13226009),
    CH_AP_CI("ch_ap_ci", 6, 0.1F, Rarity.RARE, () -> MobEffects.REGENERATION, () -> MobEffects.MOVEMENT_SPEED, 13189222),
    HO_ME_KI("ho_me_ki", 6, 0.1F, Rarity.RARE, () -> MobEffects.REGENERATION, () -> MobEffects.INVISIBILITY, 2031360),
    PI_CO("pi_co", 6, 0.1F, Rarity.RARE, () -> MobEffects.CONFUSION, () -> MobEffects.MOVEMENT_SPEED, 16056203),
    HO_ME_CO("ho_me_co", 6, 0.1F, Rarity.RARE, () -> MobEffects.REGENERATION, () -> MobEffects.WATER_BREATHING, 10462208);

    public final String name;
    public final int healAmount;
    public final float saturation;
    public final Rarity rarity;
    private final Supplier<MobEffect> firstEffect;
    private final Supplier<MobEffect> secondEffect;
    public final int color;

    TheJams(String name, int healAmount, float saturation, Rarity rarity, Supplier<MobEffect> firstEffect, Supplier<MobEffect> secondEffect, int color) {
        this.name = name;
        this.healAmount = healAmount;
        this.saturation = saturation;
        this.rarity = rarity;
        this.firstEffect = firstEffect;
        this.secondEffect = secondEffect;
        this.color = color;
    }

    public MobEffect getFirstEffect() {
        return this.firstEffect.get();
    }

    public MobEffect getSecondEffect() {
        return this.secondEffect.get();
    }
}
