/*
 * This file ("InitItems.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import com.google.common.collect.ImmutableSet;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.AABlockItem;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.Crate;
import de.ellpeck.actuallyadditions.mod.items.base.ItemArmorAA;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.items.base.ItemFoodBase;
import de.ellpeck.actuallyadditions.mod.items.base.ItemHoeAA;
import de.ellpeck.actuallyadditions.mod.items.base.ItemSwordAA;
import de.ellpeck.actuallyadditions.mod.items.lens.ItemLens;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheJams;
import de.ellpeck.actuallyadditions.mod.items.metalists.ThePotionRings;
import de.ellpeck.actuallyadditions.mod.material.ArmorMaterials;
import de.ellpeck.actuallyadditions.mod.material.ToolMaterials;
import de.ellpeck.actuallyadditions.mod.tile.CrateBE;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.function.Supplier;

public final class ActuallyItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ActuallyAdditions.MODID);

    // MISC ITEMS
    public static final RegistryObject<Item> CANOLA = ITEMS.register("canola", ItemBase::new);
    public static final RegistryObject<ItemCoffee> COFFEE_CUP = ITEMS.register("coffee_cup", ItemCoffee::new);
    public static final RegistryObject<Item> RING = ITEMS.register("ring", ItemBase::new);
    public static final RegistryObject<Item> BASIC_COIL = ITEMS.register("basic_coil", ItemBase::new);
    public static final RegistryObject<Item> ADVANCED_COIL = ITEMS.register("advanced_coil", ItemBase::new);
    public static final RegistryObject<Item> RICE = ITEMS.register("rice", ItemBase::new);
    public static final RegistryObject<Item> RICE_DOUGH = ITEMS.register("rice_dough", ItemBase::new);
    public static final RegistryObject<Item> TINY_COAL = ITEMS.register("tiny_coal", () -> new ItemBase() {
        @Override
        public int getBurnTime(@Nonnull ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
            return 200;
        }
    });
    public static final RegistryObject<Item> TINY_CHARCOAL = ITEMS.register("tiny_charcoal", () -> new ItemBase() {
        @Override
        public int getBurnTime(@Nonnull ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
            return 200;
        }
    });
    public static final RegistryObject<Item> RICE_SLIMEBALL = ITEMS.register("rice_slimeball", ItemBase::new);
    public static final RegistryObject<Item> EMPTY_CUP = ITEMS.register("empty_cup", ItemBase::new);
    public static final RegistryObject<Item> BATS_WING = ITEMS.register("bats_wing", ItemBase::new);
    public static final RegistryObject<Item> DRILL_CORE = ITEMS.register("drill_core", ItemBase::new);
    public static final RegistryObject<Item> LENS = ITEMS.register("lens", ItemBase::new);
    public static final RegistryObject<Item> ENDER_STAR = ITEMS.register("ender_star", () -> new ItemBase() {
        @Override
        public boolean isFoil(@Nonnull ItemStack pStack) {
            return true;
        }
    });
    public static final RegistryObject<Item> CRYSTALLIZED_CANOLA_SEED = ITEMS.register("crystallized_canola_seed", () -> new CanolaSeed(false));
    public static final RegistryObject<Item> EMPOWERED_CANOLA_SEED = ITEMS.register("empowered_canola_seed", () -> new CanolaSeed(true));
    public static final RegistryObject<Item> MASHED_FOOD = ITEMS.register("mashed_food", ItemBase::new);
    public static final RegistryObject<Item> BIOMASS = ITEMS.register("biomass", ItemBase::new);
    public static final RegistryObject<Item> BIOCOAL = ITEMS.register("biocoal", () -> new ItemBase() {
        @Override
        public int getBurnTime(@Nonnull ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
            return 800;
        }
    });
    public static final RegistryObject<Item> FERTILIZER = ITEMS.register("fertilizer", ItemFertilizer::new);

    // SHARDS
    public static final RegistryObject<Item> RESTONIA_CRYSTAL_SHARD = ITEMS.register("restonia_crystal_shard", ItemBase::new);
    public static final RegistryObject<Item> PALIS_CRYSTAL_SHARD = ITEMS.register("palis_crystal_shard", ItemBase::new);
    public static final RegistryObject<Item> DIAMATINE_CRYSTAL_SHARD = ITEMS.register("diamatine_crystal_shard", ItemBase::new);
    public static final RegistryObject<Item> VOID_CRYSTAL_SHARD = ITEMS.register("void_crystal_shard", ItemBase::new);
    public static final RegistryObject<Item> EMERADIC_CRYSTAL_SHARD = ITEMS.register("emeradic_crystal_shard", ItemBase::new);
    public static final RegistryObject<Item> ENORI_CRYSTAL_SHARD = ITEMS.register("enori_crystal_shard", ItemBase::new);

    // CRYSTALS
    public static final RegistryObject<Item> RESTONIA_CRYSTAL = ITEMS.register("restonia_crystal", ItemCrystal::new);
    public static final RegistryObject<Item> PALIS_CRYSTAL = ITEMS.register("palis_crystal", ItemCrystal::new);
    public static final RegistryObject<Item> DIAMATINE_CRYSTAL = ITEMS.register("diamatine_crystal", ItemCrystal::new);
    public static final RegistryObject<Item> VOID_CRYSTAL = ITEMS.register("void_crystal", ItemCrystal::new);
    public static final RegistryObject<Item> EMERADIC_CRYSTAL = ITEMS.register("emeradic_crystal", ItemCrystal::new);
    public static final RegistryObject<Item> ENORI_CRYSTAL = ITEMS.register("enori_crystal", ItemCrystal::new);

    public static final RegistryObject<Item> EMPOWERED_RESTONIA_CRYSTAL = ITEMS.register("empowered_restonia_crystal", () -> new ItemCrystal(true));
    public static final RegistryObject<Item> EMPOWERED_PALIS_CRYSTAL = ITEMS.register("empowered_palis_crystal", () -> new ItemCrystal(true));
    public static final RegistryObject<Item> EMPOWERED_DIAMATINE_CRYSTAL = ITEMS.register("empowered_diamatine_crystal", () -> new ItemCrystal(true));
    public static final RegistryObject<Item> EMPOWERED_VOID_CRYSTAL = ITEMS.register("empowered_void_crystal", () -> new ItemCrystal(true));
    public static final RegistryObject<Item> EMPOWERED_EMERADIC_CRYSTAL = ITEMS.register("empowered_emeradic_crystal", () -> new ItemCrystal(true));
    public static final RegistryObject<Item> EMPOWERED_ENORI_CRYSTAL = ITEMS.register("empowered_enori_crystal", () -> new ItemCrystal(true));

    // BLACK QUARTZ
    public static final RegistryObject<Item> BLACK_QUARTZ = ITEMS.register("black_quartz", ItemBase::new);

    // GOGGLES & UPGRADES
    public static final RegistryObject<Item> ENGINEERS_GOGGLES_ADVANCED = ITEMS.register("engineers_goggles_advanced", () -> new ItemEngineerGoggles(true));
    public static final RegistryObject<Item> ENGINEERS_GOGGLES = ITEMS.register("engineers_goggles", () -> new ItemEngineerGoggles(false));
    public static final RegistryObject<Item> LASER_UPGRADE_RANGE = ITEMS.register("laser_upgrade_range", ItemLaserRelayUpgrade::new);
    public static final RegistryObject<Item> LASER_UPGRADE_INVISIBILITY = ITEMS.register("laser_upgrade_invisibility", ItemLaserRelayUpgrade::new);
    public static final RegistryObject<Filler> HANDHELD_FILLER = ITEMS.register("handheld_filler", Filler::new);
    public static final RegistryObject<Item> TRAVELERS_SACK = ITEMS.register("travelers_sack", () -> new Sack(false));
    public static final RegistryObject<Item> VOID_SACK = ITEMS.register("void_sack", () -> new Sack(true));
    public static final RegistryObject<Item> WORM = ITEMS.register("worm", Worm::new);
    public static final RegistryObject<Item> PLAYER_PROBE = ITEMS.register("player_probe", ItemPlayerProbe::new);
    public static final RegistryObject<Item> FILTER = ITEMS.register("filter", ItemFilter::new);
    public static final RegistryObject<Item> ITEM_TAG = ITEMS.register("item_tag", ItemTag::new);
    public static final RegistryObject<Item> WATER_BOWL = ITEMS.register("water_bowl", ItemWaterBowl::new);
    public static final RegistryObject<Item> CRATE_KEEPER = ITEMS.register("crate_keeper", () -> new ItemGeneric(defaultProps().stacksTo(1)));

    // LENSES
    public static final RegistryObject<Item> LENS_OF_COLOR = ITEMS.register("lens_of_color", () -> new ItemLens(ActuallyAdditionsAPI.lensColor));
    public static final RegistryObject<Item> LENS_OF_DETONATION = ITEMS.register("lens_of_detonation", () -> new ItemLens(ActuallyAdditionsAPI.lensDetonation));
    public static final RegistryObject<Item> LENS_OF_CERTAIN_DEATH = ITEMS.register("lens_of_certain_death", () -> new ItemLens(ActuallyAdditionsAPI.lensDeath));
    public static final RegistryObject<Item> LENS_OF_THE_KILLER = ITEMS.register("lens_of_the_killer", () -> new ItemLens(ActuallyAdditionsAPI.lensEvenMoarDeath));
    public static final RegistryObject<Item> LENS_OF_DISENCHANTING = ITEMS.register("lens_of_disenchanting", () -> new ItemLens(ActuallyAdditionsAPI.lensDisenchanting));
    public static final RegistryObject<Item> LENS_OF_THE_MINER = ITEMS.register("lens_of_the_miner", () -> new ItemLens(ActuallyAdditionsAPI.lensMining));
    public static final RegistryObject<Item> LENS_OF_DISRUPTION = ITEMS.register("lens_of_disruption", () -> new ItemLens(ActuallyAdditionsAPI.lensDisruption));
    public static final RegistryObject<Item> LASER_WRENCH = ITEMS.register("laser_wrench", ItemLaserWrench::new);

    public static final RegistryObject<Item> ITEM_BOOKLET = ITEMS.register("booklet", ItemBooklet::new);
    public static final RegistryObject<Item> RING_OF_GROWTH = ITEMS.register("ring_of_growth", ItemGrowthRing::new);
    public static final RegistryObject<Item> RING_OF_MAGNETIZING = ITEMS.register("ring_of_magnetizing", ItemMagnetRing::new);
    public static final RegistryObject<Item> TELEPORT_STAFF = ITEMS.register("teleport_staff", ItemTeleportStaff::new);
    public static final RegistryObject<Item> WINGS_OF_THE_BATS = ITEMS.register("wings_of_the_bats", ItemWingsOfTheBats::new);

    // DRILLS
    public static final RegistryObject<Item> DRILL_MAIN = ITEMS.register("drill_light_blue", DrillItem::new);
    public static final RegistryObject<Item> DRILL_BLACK = ITEMS.register("drill_black", DrillItem::new);
    public static final RegistryObject<Item> DRILL_BLUE = ITEMS.register("drill_blue", DrillItem::new);
    public static final RegistryObject<Item> DRILL_BROWN = ITEMS.register("drill_brown", DrillItem::new);
    public static final RegistryObject<Item> DRILL_CYAN = ITEMS.register("drill_cyan", DrillItem::new);
    public static final RegistryObject<Item> DRILL_GRAY = ITEMS.register("drill_gray", DrillItem::new);
    public static final RegistryObject<Item> DRILL_GREEN = ITEMS.register("drill_green", DrillItem::new);
    public static final RegistryObject<Item> DRILL_LIGHT_GRAY = ITEMS.register("drill_light_gray", DrillItem::new);
    public static final RegistryObject<Item> DRILL_LIME = ITEMS.register("drill_lime", DrillItem::new);
    public static final RegistryObject<Item> DRILL_MAGENTA = ITEMS.register("drill_magenta", DrillItem::new);
    public static final RegistryObject<Item> DRILL_ORANGE = ITEMS.register("drill_orange", DrillItem::new);
    public static final RegistryObject<Item> DRILL_PINK = ITEMS.register("drill_pink", DrillItem::new);
    public static final RegistryObject<Item> DRILL_PURPLE = ITEMS.register("drill_purple", DrillItem::new);
    public static final RegistryObject<Item> DRILL_RED = ITEMS.register("drill_red", DrillItem::new);
    public static final RegistryObject<Item> DRILL_WHITE = ITEMS.register("drill_white", DrillItem::new);
    public static final RegistryObject<Item> DRILL_YELLOW = ITEMS.register("drill_yellow", DrillItem::new);

    // BATTERIES
    public static final RegistryObject<Item> SINGLE_BATTERY = ITEMS.register("single_battery", () -> new ItemBattery(200000, 1000));
    public static final RegistryObject<Item> DOUBLE_BATTERY = ITEMS.register("double_battery", () -> new ItemBattery(350000, 5000));
    public static final RegistryObject<Item> TRIPLE_BATTERY = ITEMS.register("triple_battery", () -> new ItemBattery(600000, 10000));
    public static final RegistryObject<Item> QUADRUPLE_BATTERY = ITEMS.register("quadruple_battery", () -> new ItemBattery(1000000, 30000));
    public static final RegistryObject<Item> QUINTUPLE_BATTERY = ITEMS.register("quintuple_battery", () -> new ItemBattery(2000000, 100000));

    // DRILL UPGRADES
    public static final RegistryObject<Item> DRILL_UPGRADE_SPEED = ITEMS.register("drill_upgrade_speed", () -> new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.SPEED));
    public static final RegistryObject<Item> DRILL_UPGRADE_SPEED_II = ITEMS.register("drill_upgrade_speed_ii", () -> new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.SPEED_II));
    public static final RegistryObject<Item> DRILL_UPGRADE_SPEED_III = ITEMS.register("drill_upgrade_speed_iii", () -> new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.SPEED_III));
    public static final RegistryObject<Item> DRILL_UPGRADE_SILK_TOUCH = ITEMS.register("drill_upgrade_silk_touch", () -> new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.SILK_TOUCH));
    public static final RegistryObject<Item> DRILL_UPGRADE_FORTUNE = ITEMS.register("drill_upgrade_fortune", () -> new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.FORTUNE));
    public static final RegistryObject<Item> DRILL_UPGRADE_FORTUNE_II = ITEMS.register("drill_upgrade_fortune_ii", () -> new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.FORTUNE_II));
    public static final RegistryObject<Item> DRILL_UPGRADE_THREE_BY_THREE = ITEMS.register("drill_upgrade_three_by_three", () -> new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.THREE_BY_THREE));
    public static final RegistryObject<Item> DRILL_UPGRADE_FIVE_BY_FIVE = ITEMS.register("drill_upgrade_five_by_five", () -> new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.FIVE_BY_FIVE));
    public static final RegistryObject<Item> DRILL_UPGRADE_BLOCK_PLACING = ITEMS.register("drill_upgrade_block_placing", () -> new ItemDrillUpgrade(ItemDrillUpgrade.UpgradeType.PLACER));

    public static final RegistryObject<Item> PHANTOM_CONNECTOR = ITEMS.register("phantom_connector", ItemPhantomConnector::new);
    public static final RegistryObject<Item> CRAFTER_ON_A_STICK = ITEMS.register("crafter_on_a_stick", ItemCrafterOnAStick::new);
    public static final RegistryObject<Item> SOLIDIFIED_EXPERIENCE = ITEMS.register("solidified_experience", ItemSolidifiedExperience::new);
    public static final RegistryObject<Item> LEAF_BLOWER = ITEMS.register("leaf_blower", () -> new ItemLeafBlower(false));
    public static final RegistryObject<Item> ADVANCED_LEAF_BLOWER = ITEMS.register("advanced_leaf_blower", () -> new ItemLeafBlower(true));

    public static final RegistryObject<Item> COFFEE_BEANS = ITEMS.register("coffee_beans", () -> new AABlockItem.AASeedItem(ActuallyBlocks.COFFEE.get(), ActuallyItems.defaultProps()));
    public static final RegistryObject<Item> RICE_SEEDS = ITEMS.register("rice_seeds", () -> new AABlockItem.AASeedItem(ActuallyBlocks.RICE.get(), ActuallyItems.defaultProps()));
    public static final RegistryObject<Item> CANOLA_SEEDS = ITEMS.register("canola_seeds", () -> new AABlockItem.AASeedItem(ActuallyBlocks.CANOLA.get(), ActuallyItems.defaultProps()));
    public static final RegistryObject<Item> FLAX_SEEDS = ITEMS.register("flax_seeds", () -> new AABlockItem.AASeedItem(ActuallyBlocks.FLAX.get(), ActuallyItems.defaultProps()));

    // SPECIAL CLASSIC ITEMS
    public static final RegistryObject<Item> KNIFE = ITEMS.register("knife", ItemKnife::new);
    public static final RegistryObject<Item> HAIRY_BALL = ITEMS.register("hairy_ball", ItemHairyBall::new);
    public static final RegistryObject<Item> BLACK_DYE = ITEMS.register("black_dye", ItemBase::new);
    public static final RegistryObject<Item> DOUGH = ITEMS.register("dough", ItemBase::new);
    public static final RegistryObject<Item> PAPER_CONE = ITEMS.register("paper_cone", ItemBase::new);
    public static final RegistryObject<Item> KNIFE_BLADE = ITEMS.register("knife_blade", ItemBase::new);
    public static final RegistryObject<Item> KNIFE_HANDLE = ITEMS.register("knife_handle", ItemBase::new);
    public static final RegistryObject<Item> RESONANT_RICE = ITEMS.register("resonant_rice", ItemResonantRice::new);
    public static final RegistryObject<Item> SPAWNER_CHANGER = ITEMS.register("spawner_changer", ItemSpawnerChanger::new);
    public static final RegistryObject<Item> SPAWNER_SHARD = ITEMS.register("spawner_shard", ItemBase::new);

    // CRATE UPGRADES
    public static final RegistryObject<Item> CHEST_TO_CRATE_UPGRADE = ITEMS.register("chest_to_crate_upgrade", () -> new ItemChestToCrateUpgrade(
        tile -> tile.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent() && !(tile instanceof CrateBE),
        () -> ActuallyBlocks.CRATE_SMALL.get().defaultBlockState()));
    public static final RegistryObject<Item> SMALL_TO_MEDIUM_CRATE_UPGRADE = ITEMS.register("small_to_medium_crate_upgrade", () -> new ItemChestToCrateUpgrade(
        tile -> tile instanceof CrateBE crate && crate.getSize() == Crate.Size.SMALL,
        () -> ActuallyBlocks.CRATE_MEDIUM.get().defaultBlockState()));
    public static final RegistryObject<Item> MEDIUM_TO_LARGE_CRATE_UPGRADE = ITEMS.register("medium_to_large_crate_upgrade", () -> new ItemChestToCrateUpgrade(
        tile -> tile instanceof CrateBE crate && crate.getSize() == Crate.Size.MEDIUM,
        () -> ActuallyBlocks.CRATE_LARGE.get().defaultBlockState()));

    // POTION RINGS & LIQUID BANNING
    public static final RegistryObject<Item> RING_OF_SPEED = ITEMS.register("ring_of_speed", () -> new ItemPotionRing(ThePotionRings.SPEED, false));
    public static final RegistryObject<Item> ADVANCED_RING_OF_SPEED = ITEMS.register("advanced_ring_of_speed", () -> new ItemPotionRing(ThePotionRings.SPEED, true));
    public static final RegistryObject<Item> RING_OF_HASTE = ITEMS.register("ring_of_haste", () -> new ItemPotionRing(ThePotionRings.HASTE, false));
    public static final RegistryObject<Item> ADVANCED_RING_OF_HASTE = ITEMS.register("advanced_ring_of_haste", () -> new ItemPotionRing(ThePotionRings.HASTE, true));
    public static final RegistryObject<Item> RING_OF_STRENGTH = ITEMS.register("ring_of_strength", () -> new ItemPotionRing(ThePotionRings.STRENGTH, false));
    public static final RegistryObject<Item> ADVANCED_RING_OF_STRENGTH = ITEMS.register("advanced_ring_of_strength", () -> new ItemPotionRing(ThePotionRings.STRENGTH, true));
    public static final RegistryObject<Item> RING_OF_JUMP_BOOST = ITEMS.register("ring_of_jump_boost", () -> new ItemPotionRing(ThePotionRings.JUMP_BOOST, false));
    public static final RegistryObject<Item> ADVANCED_RING_OF_JUMP_BOOST = ITEMS.register("advanced_ring_of_jump_boost", () -> new ItemPotionRing(ThePotionRings.JUMP_BOOST, true));
    public static final RegistryObject<Item> RING_OF_REGENERATION = ITEMS.register("ring_of_regeneration", () -> new ItemPotionRing(ThePotionRings.REGEN, false));
    public static final RegistryObject<Item> ADVANCED_RING_OF_REGENERATION = ITEMS.register("advanced_ring_of_regeneration", () -> new ItemPotionRing(ThePotionRings.REGEN, true));
    public static final RegistryObject<Item> RING_OF_RESISTANCE = ITEMS.register("ring_of_resistance", () -> new ItemPotionRing(ThePotionRings.RESISTANCE, false));
    public static final RegistryObject<Item> ADVANCED_RING_OF_RESISTANCE = ITEMS.register("advanced_ring_of_resistance", () -> new ItemPotionRing(ThePotionRings.RESISTANCE, true));
    public static final RegistryObject<Item> RING_OF_FIRE_RESISTANCE = ITEMS.register("ring_of_fire_resistance", () -> new ItemPotionRing(ThePotionRings.FIRE_RESISTANCE, false));
    public static final RegistryObject<Item> ADVANCED_RING_OF_FIRE_RESISTANCE = ITEMS.register("advanced_ring_of_fire_resistance", () -> new ItemPotionRing(ThePotionRings.FIRE_RESISTANCE, true));
    public static final RegistryObject<Item> RING_OF_WATER_BREATHING = ITEMS.register("ring_of_water_breathing", () -> new ItemPotionRing(ThePotionRings.WATER_BREATHING, false));
    public static final RegistryObject<Item> ADVANCED_RING_OF_WATER_BREATHING = ITEMS.register("advanced_ring_of_water_breathing", () -> new ItemPotionRing(ThePotionRings.WATER_BREATHING, true));
    public static final RegistryObject<Item> RING_OF_INVISIBILITY = ITEMS.register("ring_of_invisibility", () -> new ItemPotionRing(ThePotionRings.INVISIBILITY, false));
    public static final RegistryObject<Item> ADVANCED_RING_OF_INVISIBILITY = ITEMS.register("advanced_ring_of_invisibility", () -> new ItemPotionRing(ThePotionRings.INVISIBILITY, true));
    public static final RegistryObject<Item> RING_OF_NIGHT_VISION = ITEMS.register("ring_of_night_vision", () -> new ItemPotionRing(ThePotionRings.NIGHT_VISION, false));
    public static final RegistryObject<Item> ADVANCED_RING_OF_NIGHT_VISION = ITEMS.register("advanced_ring_of_night_vision", () -> new ItemPotionRing(ThePotionRings.NIGHT_VISION, true));

    public static final RegistryObject<Item> RING_OF_LIQUID_BANNING = ITEMS.register("ring_of_liquid_banning", ItemWaterRemovalRing::new);

    // FOODS
    public static final RegistryObject<Item> FOOD_CHEESE = ITEMS.register("food_cheese", () -> new ItemFoodBase(new FoodProperties.Builder().nutrition(1).saturationMod(0.05F).build()));
    public static final RegistryObject<Item> FOOD_PUMPKIN_STEW = ITEMS.register("food_pumpkin_stew", () -> new ItemFoodBase(new FoodProperties.Builder().nutrition(6).saturationMod(0.3F).build(), Rarity.COMMON, () -> new ItemStack(Items.BOWL), true));
    public static final RegistryObject<Item> FOOD_CARROT_JUICE = ITEMS.register("food_carrot_juice", () -> new ItemFoodBase(new FoodProperties.Builder().nutrition(4).saturationMod(0.2F).build(), Rarity.COMMON, () -> new ItemStack(Items.GLASS_BOTTLE), true));
    public static final RegistryObject<Item> FOOD_FISH_N_CHIPS = ITEMS.register("food_fish_n_chips", () -> new ItemFoodBase(new FoodProperties.Builder().nutrition(14).saturationMod(0.65F).build(), Rarity.UNCOMMON, () -> new ItemStack(ActuallyItems.PAPER_CONE.get()), false));
    public static final RegistryObject<Item> FOOD_FRENCH_FRIES = ITEMS.register("food_french_fries", () -> new ItemFoodBase(new FoodProperties.Builder().nutrition(10).saturationMod(0.6F).build(), Rarity.COMMON, () -> new ItemStack(ActuallyItems.PAPER_CONE.get()), false));
    public static final RegistryObject<Item> FOOD_FRENCH_FRY = ITEMS.register("food_french_fry", () -> new ItemFoodBase(new FoodProperties.Builder().nutrition(2).saturationMod(0.025F).build()));
    public static final RegistryObject<Item> FOOD_SPAGHETTI = ITEMS.register("food_spaghetti", () -> new ItemFoodBase(new FoodProperties.Builder().nutrition(7).saturationMod(0.4F).build(), Rarity.COMMON, () -> new ItemStack(Items.BOWL), false));
    public static final RegistryObject<Item> FOOD_NOODLE = ITEMS.register("food_noodle", () -> new ItemFoodBase(new FoodProperties.Builder().nutrition(1).saturationMod(0.01F).build()));
    public static final RegistryObject<Item> FOOD_CHOCOLATE_CAKE = ITEMS.register("food_chocolate_cake", () -> new ItemFoodBase(new FoodProperties.Builder().nutrition(16).saturationMod(0.8F).build(), Rarity.UNCOMMON));
    public static final RegistryObject<Item> FOOD_CHOCOLATE = ITEMS.register("food_chocolate", () -> new ItemFoodBase(new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).build()));
    public static final RegistryObject<Item> FOOD_TOAST = ITEMS.register("food_toast", () -> new ItemFoodBase(new FoodProperties.Builder().nutrition(3).saturationMod(0.08F).build()));
    public static final RegistryObject<Item> FOOD_SUBMARINE_SANDWICH = ITEMS.register("food_submarine_sandwich", () -> new ItemFoodBase(new FoodProperties.Builder().nutrition(9).saturationMod(0.4F).build(), Rarity.UNCOMMON));
    public static final RegistryObject<Item> FOOD_BIG_COOKIE = ITEMS.register("food_big_cookie", () -> new ItemFoodBase(new FoodProperties.Builder().nutrition(4).saturationMod(0.25F).build(), Rarity.UNCOMMON));
    public static final RegistryObject<Item> FOOD_HAMBURGER = ITEMS.register("food_hamburger", () -> new ItemFoodBase(new FoodProperties.Builder().nutrition(13).saturationMod(0.65F).build()));
    public static final RegistryObject<Item> FOOD_PIZZA = ITEMS.register("food_pizza", () -> new ItemFoodBase(new FoodProperties.Builder().nutrition(16).saturationMod(0.8F).build(), Rarity.UNCOMMON));
    public static final RegistryObject<Item> FOOD_BAGUETTE = ITEMS.register("food_baguette", () -> new ItemFoodBase(new FoodProperties.Builder().nutrition(6).saturationMod(0.5F).build()));
    public static final RegistryObject<Item> FOOD_RICE_BREAD = ITEMS.register("food_rice_bread", () -> new ItemFoodBase(new FoodProperties.Builder().nutrition(6).saturationMod(0.5F).build(), Rarity.UNCOMMON));
    public static final RegistryObject<Item> FOOD_DOUGHNUT = ITEMS.register("food_doughnut", () -> new ItemFoodBase(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build(), Rarity.EPIC));
    public static final RegistryObject<Item> FOOD_CHOCOLATE_TOAST = ITEMS.register("food_toast_o_chocolate", () -> new ItemFoodBase(new FoodProperties.Builder().nutrition(5).saturationMod(0.2F).build(), Rarity.RARE));
    public static final RegistryObject<Item> FOOD_BACON = ITEMS.register("food_bacon", () -> new ItemFoodBase(new FoodProperties.Builder().nutrition(4).saturationMod(0.1F).build()));

    // JAMS
    public static final RegistryObject<Item> JAM_CU_BA_RA = ITEMS.register("jam_cu_ba_ra", () -> new ItemJam(TheJams.CU_BA_RA));
    public static final RegistryObject<Item> JAM_GRA_KI_BA = ITEMS.register("jam_gra_ki_ba", () -> new ItemJam(TheJams.GRA_KI_BA));
    public static final RegistryObject<Item> JAM_PL_AP_LE = ITEMS.register("jam_pl_ap_le", () -> new ItemJam(TheJams.PL_AP_LE));
    public static final RegistryObject<Item> JAM_CH_AP_CI = ITEMS.register("jam_ch_ap_ci", () -> new ItemJam(TheJams.CH_AP_CI));
    public static final RegistryObject<Item> JAM_HO_ME_KI = ITEMS.register("jam_ho_me_ki", () -> new ItemJam(TheJams.HO_ME_KI));
    public static final RegistryObject<Item> JAM_PI_CO = ITEMS.register("jam_pi_co", () -> new ItemJam(TheJams.PI_CO));
    public static final RegistryObject<Item> JAM_HO_ME_CO = ITEMS.register("jam_ho_me_co", () -> new ItemJam(TheJams.HO_ME_CO));

    // TOOLS & ARMOR
    // Vanilla AIOTs
    public static final RegistryObject<Item> WOODEN_AIOT = ITEMS.register("wooden_aiot", () -> new AllInOneTool(Tiers.WOOD));
    public static final RegistryObject<Item> STONE_AIOT = ITEMS.register("stone_aiot", () -> new AllInOneTool(Tiers.STONE));
    public static final RegistryObject<Item> IRON_AIOT = ITEMS.register("iron_aiot", () -> new AllInOneTool(Tiers.IRON));
    public static final RegistryObject<Item> GOLD_AIOT = ITEMS.register("gold_aiot", () -> new AllInOneTool(Tiers.GOLD));
    public static final RegistryObject<Item> DIAMOND_AIOT = ITEMS.register("diamond_aiot", () -> new AllInOneTool(Tiers.DIAMOND));
    public static final RegistryObject<Item> NETHERITE_AIOT = ITEMS.register("netherite_aiot", () -> new AllInOneTool(Tiers.NETHERITE));

    // EMERALD
    public static final RegistryObject<Item> EMERALD_PICKAXE = ITEMS.register("emerald_pickaxe", () -> new ItemPickaxeAA(ToolMaterials.EMERALD));
    public static final RegistryObject<Item> EMERALD_AXE = ITEMS.register("emerald_axe", () -> new ItemAxeAA(ToolMaterials.EMERALD));
    public static final RegistryObject<Item> EMERALD_SHOVEL = ITEMS.register("emerald_shovel", () -> new ItemShovelAA(ToolMaterials.EMERALD));
    public static final RegistryObject<Item> EMERALD_SWORD = ITEMS.register("emerald_sword", () -> new ItemSwordAA(ToolMaterials.EMERALD));
    public static final RegistryObject<Item> EMERALD_HOE = ITEMS.register("emerald_hoe", () -> new ItemHoeAA(ToolMaterials.EMERALD));
    public static final RegistryObject<Item> EMERALD_AIOT = ITEMS.register("emerald_aiot", () -> new AllInOneTool(ToolMaterials.EMERALD));
    public static final RegistryObject<Item> EMERALD_HELMET = ITEMS.register("emerald_helmet", () -> new ItemArmorAA(ArmorMaterials.EMERALD, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> EMERALD_CHEST = ITEMS.register("emerald_chest", () -> new ItemArmorAA(ArmorMaterials.EMERALD, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> EMERALD_LEGGINGS = ITEMS.register("emerald_leggings", () -> new ItemArmorAA(ArmorMaterials.EMERALD, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> EMERALD_BOOTS = ITEMS.register("emerald_boots", () -> new ItemArmorAA(ArmorMaterials.EMERALD, ArmorItem.Type.BOOTS));

    // OBSIDIAN
    public static final RegistryObject<Item> OBSIDIAN_PICKAXE = ITEMS.register("obsidian_pickaxe", () -> new ItemPickaxeAA(ToolMaterials.OBSIDIAN));
    public static final RegistryObject<Item> OBSIDIAN_AXE = ITEMS.register("obsidian_axe", () -> new ItemAxeAA(ToolMaterials.OBSIDIAN));
    public static final RegistryObject<Item> OBSIDIAN_SHOVEL = ITEMS.register("obsidian_shovel", () -> new ItemShovelAA(ToolMaterials.OBSIDIAN));
    public static final RegistryObject<Item> OBSIDIAN_SWORD = ITEMS.register("obsidian_sword", () -> new ItemSwordAA(ToolMaterials.OBSIDIAN));
    public static final RegistryObject<Item> OBSIDIAN_HOE = ITEMS.register("obsidian_hoe", () -> new ItemHoeAA(ToolMaterials.OBSIDIAN));
    public static final RegistryObject<Item> OBSIDIAN_AIOT = ITEMS.register("obsidian_aiot", () -> new AllInOneTool(ToolMaterials.OBSIDIAN));
    public static final RegistryObject<Item> OBSIDIAN_HELMET = ITEMS.register("obsidian_helmet", () -> new ItemArmorAA(ArmorMaterials.OBSIDIAN, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> OBSIDIAN_CHEST = ITEMS.register("obsidian_chest", () -> new ItemArmorAA(ArmorMaterials.OBSIDIAN, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> OBSIDIAN_LEGGINGS = ITEMS.register("obsidian_leggings", () -> new ItemArmorAA(ArmorMaterials.OBSIDIAN, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> OBSIDIAN_BOOTS = ITEMS.register("obsidian_boots", () -> new ItemArmorAA(ArmorMaterials.OBSIDIAN, ArmorItem.Type.BOOTS));

    // BLACK QUARTZ
    public static final RegistryObject<Item> QUARTZ_PICKAXE = ITEMS.register("quartz_pickaxe", () -> new ItemPickaxeAA(ToolMaterials.BLACK_QUARTZ));
    public static final RegistryObject<Item> QUARTZ_AXE = ITEMS.register("quartz_axe", () -> new ItemAxeAA(ToolMaterials.BLACK_QUARTZ));
    public static final RegistryObject<Item> QUARTZ_SHOVEL = ITEMS.register("quartz_shovel", () -> new ItemShovelAA(ToolMaterials.BLACK_QUARTZ));
    public static final RegistryObject<Item> QUARTZ_SWORD = ITEMS.register("quartz_sword", () -> new ItemSwordAA(ToolMaterials.BLACK_QUARTZ));
    public static final RegistryObject<Item> QUARTZ_HOE = ITEMS.register("quartz_hoe", () -> new ItemHoeAA(ToolMaterials.BLACK_QUARTZ));
    public static final RegistryObject<Item> QUARTZ_AIOT = ITEMS.register("quartz_aiot", () -> new AllInOneTool(ToolMaterials.BLACK_QUARTZ));
    public static final RegistryObject<Item> QUARTZ_HELMET = ITEMS.register("quartz_helmet", () -> new ItemArmorAA(ArmorMaterials.QUARTZ, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> QUARTZ_CHEST = ITEMS.register("quartz_chest", () -> new ItemArmorAA(ArmorMaterials.QUARTZ, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> QUARTZ_LEGGINGS = ITEMS.register("quartz_leggings", () -> new ItemArmorAA(ArmorMaterials.QUARTZ, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> QUARTZ_BOOTS = ITEMS.register("quartz_boots", () -> new ItemArmorAA(ArmorMaterials.QUARTZ, ArmorItem.Type.BOOTS));

    // RESTONIA
    public static final RegistryObject<Item> RESTONIA_PICKAXE = ITEMS.register("restonia_pickaxe", () -> new ItemPickaxeAA(ToolMaterials.RESTONIA));
    public static final RegistryObject<Item> RESTONIA_AXE = ITEMS.register("restonia_axe", () -> new ItemAxeAA(ToolMaterials.RESTONIA));
    public static final RegistryObject<Item> RESTONIA_SHOVEL = ITEMS.register("restonia_shovel", () -> new ItemShovelAA(ToolMaterials.RESTONIA));
    public static final RegistryObject<Item> RESTONIA_SWORD = ITEMS.register("restonia_sword", () -> new ItemSwordAA(ToolMaterials.RESTONIA));
    public static final RegistryObject<Item> RESTONIA_HOE = ITEMS.register("restonia_hoe", () -> new ItemHoeAA(ToolMaterials.RESTONIA));
    public static final RegistryObject<Item> RESTONIA_AIOT = ITEMS.register("restonia_aiot", () -> new AllInOneTool(ToolMaterials.RESTONIA));
    public static final RegistryObject<Item> RESTONIA_HELMET = ITEMS.register("restonia_helmet", () -> new ItemArmorAA(ArmorMaterials.RESTONIA, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> RESTONIA_CHEST = ITEMS.register("restonia_chest", () -> new ItemArmorAA(ArmorMaterials.RESTONIA, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> RESTONIA_LEGGINGS = ITEMS.register("restonia_leggings", () -> new ItemArmorAA(ArmorMaterials.RESTONIA, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> RESTONIA_BOOTS = ITEMS.register("restonia_boots", () -> new ItemArmorAA(ArmorMaterials.RESTONIA, ArmorItem.Type.BOOTS));

    // PALIS
    public static final RegistryObject<Item> PALIS_PICKAXE = ITEMS.register("palis_pickaxe", () -> new ItemPickaxeAA(ToolMaterials.PALIS));
    public static final RegistryObject<Item> PALIS_AXE = ITEMS.register("palis_axe", () -> new ItemAxeAA(ToolMaterials.PALIS));
    public static final RegistryObject<Item> PALIS_SHOVEL = ITEMS.register("palis_shovel", () -> new ItemShovelAA(ToolMaterials.PALIS));
    public static final RegistryObject<Item> PALIS_SWORD = ITEMS.register("palis_sword", () -> new ItemSwordAA(ToolMaterials.PALIS));
    public static final RegistryObject<Item> PALIS_HOE = ITEMS.register("palis_hoe", () -> new ItemHoeAA(ToolMaterials.PALIS));
    public static final RegistryObject<Item> PALIS_AIOT = ITEMS.register("palis_aiot", () -> new AllInOneTool(ToolMaterials.PALIS));
    public static final RegistryObject<Item> PALIS_HELMET = ITEMS.register("palis_helmet", () -> new ItemArmorAA(ArmorMaterials.PALIS, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> PALIS_CHEST = ITEMS.register("palis_chest", () -> new ItemArmorAA(ArmorMaterials.PALIS, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> PALIS_LEGGINGS = ITEMS.register("palis_leggings", () -> new ItemArmorAA(ArmorMaterials.PALIS, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> PALIS_BOOTS = ITEMS.register("palis_boots", () -> new ItemArmorAA(ArmorMaterials.PALIS, ArmorItem.Type.BOOTS));

    // DIAMATINE
    public static final RegistryObject<Item> DIAMATINE_PICKAXE = ITEMS.register("diamatine_pickaxe", () -> new ItemPickaxeAA(ToolMaterials.DIAMATINE));
    public static final RegistryObject<Item> DIAMATINE_AXE = ITEMS.register("diamatine_axe", () -> new ItemAxeAA(ToolMaterials.DIAMATINE));
    public static final RegistryObject<Item> DIAMATINE_SHOVEL = ITEMS.register("diamatine_shovel", () -> new ItemShovelAA(ToolMaterials.DIAMATINE));
    public static final RegistryObject<Item> DIAMATINE_SWORD = ITEMS.register("diamatine_sword", () -> new ItemSwordAA(ToolMaterials.DIAMATINE));
    public static final RegistryObject<Item> DIAMATINE_HOE = ITEMS.register("diamatine_hoe", () -> new ItemHoeAA(ToolMaterials.DIAMATINE));
    public static final RegistryObject<Item> DIAMATINE_AIOT = ITEMS.register("diamatine_aiot", () -> new AllInOneTool(ToolMaterials.DIAMATINE));
    public static final RegistryObject<Item> DIAMATINE_HELMET = ITEMS.register("diamatine_helmet", () -> new ItemArmorAA(ArmorMaterials.DIAMATINE, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> DIAMATINE_CHEST = ITEMS.register("diamatine_chest", () -> new ItemArmorAA(ArmorMaterials.DIAMATINE, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> DIAMATINE_LEGGINGS = ITEMS.register("diamatine_leggings", () -> new ItemArmorAA(ArmorMaterials.DIAMATINE, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> DIAMATINE_BOOTS = ITEMS.register("diamatine_boots", () -> new ItemArmorAA(ArmorMaterials.DIAMATINE, ArmorItem.Type.BOOTS));

    // VOID
    public static final RegistryObject<Item> VOID_PICKAXE = ITEMS.register("void_pickaxe", () -> new ItemPickaxeAA(ToolMaterials.VOID));
    public static final RegistryObject<Item> VOID_AXE = ITEMS.register("void_axe", () -> new ItemAxeAA(ToolMaterials.VOID));
    public static final RegistryObject<Item> VOID_SHOVEL = ITEMS.register("void_shovel", () -> new ItemShovelAA(ToolMaterials.VOID));
    public static final RegistryObject<Item> VOID_SWORD = ITEMS.register("void_sword", () -> new ItemSwordAA(ToolMaterials.VOID));
    public static final RegistryObject<Item> VOID_HOE = ITEMS.register("void_hoe", () -> new ItemHoeAA(ToolMaterials.VOID));
    public static final RegistryObject<Item> VOID_AIOT = ITEMS.register("void_aiot", () -> new AllInOneTool(ToolMaterials.VOID));
    public static final RegistryObject<Item> VOID_HELMET = ITEMS.register("void_helmet", () -> new ItemArmorAA(ArmorMaterials.VOID, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> VOID_CHEST = ITEMS.register("void_chest", () -> new ItemArmorAA(ArmorMaterials.VOID, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> VOID_LEGGINGS = ITEMS.register("void_leggings", () -> new ItemArmorAA(ArmorMaterials.VOID, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> VOID_BOOTS = ITEMS.register("void_boots", () -> new ItemArmorAA(ArmorMaterials.VOID, ArmorItem.Type.BOOTS));

    // EMERADIC
    public static final RegistryObject<Item> EMERADIC_PICKAXE = ITEMS.register("emeradic_pickaxe", () -> new ItemPickaxeAA(ToolMaterials.EMERADIC));
    public static final RegistryObject<Item> EMERADIC_AXE = ITEMS.register("emeradic_axe", () -> new ItemAxeAA(ToolMaterials.EMERADIC));
    public static final RegistryObject<Item> EMERADIC_SHOVEL = ITEMS.register("emeradic_shovel", () -> new ItemShovelAA(ToolMaterials.EMERADIC));
    public static final RegistryObject<Item> EMERADIC_SWORD = ITEMS.register("emeradic_sword", () -> new ItemSwordAA(ToolMaterials.EMERADIC));
    public static final RegistryObject<Item> EMERADIC_HOE = ITEMS.register("emeradic_hoe", () -> new ItemHoeAA(ToolMaterials.EMERADIC));
    public static final RegistryObject<Item> EMERADIC_AIOT = ITEMS.register("emeradic_aiot", () -> new AllInOneTool(ToolMaterials.EMERADIC));
    public static final RegistryObject<Item> EMERADIC_HELMET = ITEMS.register("emeradic_helmet", () -> new ItemArmorAA(ArmorMaterials.EMERADIC, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> EMERADIC_CHEST = ITEMS.register("emeradic_chest", () -> new ItemArmorAA(ArmorMaterials.EMERADIC, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> EMERADIC_LEGGINGS = ITEMS.register("emeradic_leggings", () -> new ItemArmorAA(ArmorMaterials.EMERADIC, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> EMERADIC_BOOTS = ITEMS.register("emeradic_boots", () -> new ItemArmorAA(ArmorMaterials.EMERADIC, ArmorItem.Type.BOOTS));

    // ENORI
    public static final RegistryObject<Item> ENORI_PICKAXE = ITEMS.register("enori_pickaxe", () -> new ItemPickaxeAA(ToolMaterials.ENORI));
    public static final RegistryObject<Item> ENORI_AXE = ITEMS.register("enori_axe", () -> new ItemAxeAA(ToolMaterials.ENORI));
    public static final RegistryObject<Item> ENORI_SHOVEL = ITEMS.register("enori_shovel", () -> new ItemShovelAA(ToolMaterials.ENORI));
    public static final RegistryObject<Item> ENORI_SWORD = ITEMS.register("enori_sword", () -> new ItemSwordAA(ToolMaterials.ENORI));
    public static final RegistryObject<Item> ENORI_HOE = ITEMS.register("enori_hoe", () -> new ItemHoeAA(ToolMaterials.ENORI));
    public static final RegistryObject<Item> ENORI_AIOT = ITEMS.register("enori_aiot", () -> new AllInOneTool(ToolMaterials.ENORI));
    public static final RegistryObject<Item> ENORI_HELMET = ITEMS.register("enori_helmet", () -> new ItemArmorAA(ArmorMaterials.ENORI, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> ENORI_CHEST = ITEMS.register("enori_chest", () -> new ItemArmorAA(ArmorMaterials.ENORI, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> ENORI_LEGGINGS = ITEMS.register("enori_leggings", () -> new ItemArmorAA(ArmorMaterials.ENORI, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> ENORI_BOOTS = ITEMS.register("enori_boots", () -> new ItemArmorAA(ArmorMaterials.ENORI, ArmorItem.Type.BOOTS));

    // Banner Pattern
    public static final RegistryObject<BannerPatternItem> DRILL_PATTERN = ITEMS.register("drill_pattern", () -> new BannerPatternItem(ActuallyTags.BannerPatterns.PATTERN_DRILL, defaultNonStacking()));
    public static final RegistryObject<BannerPatternItem> LEAF_BLO_PATTERN = ITEMS.register("leaf_blo_pattern", () -> new BannerPatternItem(ActuallyTags.BannerPatterns.PATTERN_LEAF_BLO, defaultNonStacking()));
    public static final RegistryObject<BannerPatternItem> PHAN_CON_PATTERN = ITEMS.register("phan_con_pattern", () -> new BannerPatternItem(ActuallyTags.BannerPatterns.PATTERN_PHAN_CON, defaultNonStacking()));
    public static final RegistryObject<BannerPatternItem> BOOK_PATTERN = ITEMS.register("book_pattern", () -> new BannerPatternItem(ActuallyTags.BannerPatterns.PATTERN_BOOK, defaultNonStacking()));

    public static final Set<Supplier<? extends Item>> SIMPLE_ITEMS = ImmutableSet.of(
        // Crystals
        BLACK_QUARTZ, RESTONIA_CRYSTAL, PALIS_CRYSTAL, DIAMATINE_CRYSTAL,
        VOID_CRYSTAL, EMERADIC_CRYSTAL, ENORI_CRYSTAL, EMPOWERED_RESTONIA_CRYSTAL,
        EMPOWERED_PALIS_CRYSTAL, EMPOWERED_DIAMATINE_CRYSTAL, EMPOWERED_VOID_CRYSTAL, EMPOWERED_EMERADIC_CRYSTAL,
        EMPOWERED_ENORI_CRYSTAL,

        // Shards & Misc
        RESTONIA_CRYSTAL_SHARD, PALIS_CRYSTAL_SHARD, DIAMATINE_CRYSTAL_SHARD, VOID_CRYSTAL_SHARD, EMERADIC_CRYSTAL_SHARD,
        ENORI_CRYSTAL_SHARD, ENGINEERS_GOGGLES, ENGINEERS_GOGGLES_ADVANCED, LASER_UPGRADE_RANGE, LASER_UPGRADE_INVISIBILITY,
        FILTER,
        RING, BASIC_COIL, ADVANCED_COIL, RICE_DOUGH, TINY_COAL, TINY_CHARCOAL, RICE_SLIMEBALL, CANOLA, EMPTY_CUP,
        BATS_WING, DRILL_CORE, LENS, ENDER_STAR, CRYSTALLIZED_CANOLA_SEED, EMPOWERED_CANOLA_SEED, LENS_OF_COLOR,
        LENS_OF_DETONATION, LENS_OF_CERTAIN_DEATH, LENS_OF_THE_KILLER, LENS_OF_DISENCHANTING, LENS_OF_THE_MINER, LENS_OF_DISRUPTION,
        DRILL_UPGRADE_SPEED, DRILL_UPGRADE_SPEED_II, DRILL_UPGRADE_SPEED_III, DRILL_UPGRADE_SILK_TOUCH,
        DRILL_UPGRADE_FORTUNE, DRILL_UPGRADE_FORTUNE_II, DRILL_UPGRADE_THREE_BY_THREE, DRILL_UPGRADE_FIVE_BY_FIVE, DRILL_UPGRADE_BLOCK_PLACING,
        COFFEE_CUP, RICE, MASHED_FOOD, BIOMASS, BIOCOAL, FERTILIZER,
        SOLIDIFIED_EXPERIENCE, ITEM_TAG,
        COFFEE_BEANS, RICE_SEEDS, CANOLA_SEEDS, FLAX_SEEDS, DRILL_PATTERN, LEAF_BLO_PATTERN, PHAN_CON_PATTERN, BOOK_PATTERN,

        // Classic Special Items
        HAIRY_BALL, BLACK_DYE, DOUGH, PAPER_CONE, KNIFE_BLADE, KNIFE_HANDLE, RESONANT_RICE, SPAWNER_SHARD,

        // Rings
        RING_OF_SPEED, ADVANCED_RING_OF_SPEED,
        RING_OF_HASTE, ADVANCED_RING_OF_HASTE,
        RING_OF_STRENGTH, ADVANCED_RING_OF_STRENGTH,
        RING_OF_JUMP_BOOST, ADVANCED_RING_OF_JUMP_BOOST,
        RING_OF_REGENERATION, ADVANCED_RING_OF_REGENERATION,
        RING_OF_RESISTANCE, ADVANCED_RING_OF_RESISTANCE,
        RING_OF_FIRE_RESISTANCE, ADVANCED_RING_OF_FIRE_RESISTANCE,
        RING_OF_WATER_BREATHING, ADVANCED_RING_OF_WATER_BREATHING,
        RING_OF_INVISIBILITY, ADVANCED_RING_OF_INVISIBILITY,
        RING_OF_NIGHT_VISION, ADVANCED_RING_OF_NIGHT_VISION,

        // Foods
        FOOD_CHEESE, FOOD_PUMPKIN_STEW, FOOD_CARROT_JUICE, FOOD_FISH_N_CHIPS, FOOD_FRENCH_FRIES,
        FOOD_FRENCH_FRY, FOOD_SPAGHETTI, FOOD_NOODLE, FOOD_CHOCOLATE_CAKE, FOOD_CHOCOLATE,
        FOOD_TOAST, FOOD_SUBMARINE_SANDWICH, FOOD_BIG_COOKIE, FOOD_HAMBURGER, FOOD_PIZZA,
        FOOD_BAGUETTE, FOOD_RICE_BREAD, FOOD_DOUGHNUT, FOOD_CHOCOLATE_TOAST, FOOD_BACON,

        // Jams
        JAM_CU_BA_RA, JAM_GRA_KI_BA, JAM_PL_AP_LE, JAM_CH_AP_CI, JAM_HO_ME_KI, JAM_PI_CO, JAM_HO_ME_CO,

        // Armors
        EMERALD_HELMET, EMERALD_CHEST, EMERALD_LEGGINGS, EMERALD_BOOTS,
        OBSIDIAN_HELMET, OBSIDIAN_CHEST, OBSIDIAN_LEGGINGS, OBSIDIAN_BOOTS,
        QUARTZ_HELMET, QUARTZ_CHEST, QUARTZ_LEGGINGS, QUARTZ_BOOTS,
        RESTONIA_HELMET, RESTONIA_CHEST, RESTONIA_LEGGINGS, RESTONIA_BOOTS,
        PALIS_HELMET, PALIS_CHEST, PALIS_LEGGINGS, PALIS_BOOTS,
        DIAMATINE_HELMET, DIAMATINE_CHEST, DIAMATINE_LEGGINGS, DIAMATINE_BOOTS,
        VOID_HELMET, VOID_CHEST, VOID_LEGGINGS, VOID_BOOTS,
        EMERADIC_HELMET, EMERADIC_CHEST, EMERADIC_LEGGINGS, EMERADIC_BOOTS,
        ENORI_HELMET, ENORI_CHEST, ENORI_LEGGINGS, ENORI_BOOTS
    );

    public static final Set<Supplier<? extends Item>> TOOLS = ImmutableSet.of(
        // All in one tools
        WOODEN_AIOT, STONE_AIOT, IRON_AIOT, GOLD_AIOT, DIAMOND_AIOT, NETHERITE_AIOT,
        EMERALD_AIOT, OBSIDIAN_AIOT,
        QUARTZ_AIOT, RESTONIA_AIOT, PALIS_AIOT, DIAMATINE_AIOT, VOID_AIOT, EMERADIC_AIOT, ENORI_AIOT,

        // Tools
        EMERALD_PICKAXE, EMERALD_AXE, EMERALD_SHOVEL, EMERALD_SWORD, EMERALD_HOE,
        OBSIDIAN_PICKAXE, OBSIDIAN_AXE, OBSIDIAN_SHOVEL, OBSIDIAN_SWORD, OBSIDIAN_HOE,
        QUARTZ_PICKAXE, QUARTZ_AXE, QUARTZ_SHOVEL, QUARTZ_SWORD, QUARTZ_HOE,
        RESTONIA_PICKAXE, RESTONIA_AXE, RESTONIA_SHOVEL, RESTONIA_SWORD, RESTONIA_HOE,
        PALIS_PICKAXE, PALIS_AXE, PALIS_SHOVEL, PALIS_SWORD, PALIS_HOE,
        DIAMATINE_PICKAXE, DIAMATINE_AXE, DIAMATINE_SHOVEL, DIAMATINE_SWORD, DIAMATINE_HOE,
        VOID_PICKAXE, VOID_AXE, VOID_SHOVEL, VOID_SWORD, VOID_HOE,
        EMERADIC_PICKAXE, EMERADIC_AXE, EMERADIC_SHOVEL, EMERADIC_SWORD, EMERADIC_HOE,
        ENORI_PICKAXE, ENORI_AXE, ENORI_SHOVEL, ENORI_SWORD, ENORI_HOE,

        // Handheld & utility
        KNIFE, SPAWNER_CHANGER, CHEST_TO_CRATE_UPGRADE, SMALL_TO_MEDIUM_CRATE_UPGRADE, MEDIUM_TO_LARGE_CRATE_UPGRADE,
        LEAF_BLOWER, ADVANCED_LEAF_BLOWER, CRAFTER_ON_A_STICK,
        RING_OF_GROWTH, RING_OF_MAGNETIZING, RING_OF_LIQUID_BANNING, PHANTOM_CONNECTOR,
        LASER_WRENCH, TELEPORT_STAFF, WINGS_OF_THE_BATS, SINGLE_BATTERY, DOUBLE_BATTERY, TRIPLE_BATTERY, QUADRUPLE_BATTERY, QUINTUPLE_BATTERY,
        DRILL_BLACK, DRILL_BLUE, DRILL_BROWN, DRILL_CYAN, DRILL_GRAY, DRILL_GREEN, DRILL_MAIN, DRILL_LIGHT_GRAY,
        DRILL_LIME, DRILL_MAGENTA, DRILL_ORANGE, DRILL_PINK, DRILL_PURPLE, DRILL_RED, DRILL_WHITE, DRILL_YELLOW,
        HANDHELD_FILLER, TRAVELERS_SACK, VOID_SACK, PLAYER_PROBE, WATER_BOWL
    );

    private static Supplier<Item> basicItem() {
        return () -> new Item(defaultProps());
    }

    public static Item.Properties defaultProps() {
        return new Item.Properties();
    }

    public static Item.Properties defaultNonStacking() {
        return defaultProps().stacksTo(1);
    }

    public static void init(IEventBus evt) {
        ITEMS.register(evt);
    }
}
