package de.ellpeck.actuallyadditions.mod.material;

import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

/**
 * todo: review to ensure all values act as intended
 */
public class ToolMaterials {
	public static final Tier BLACK_QUARTZ = TierSortingRegistry.registerTier(new ForgeTier(2, 280, 6.5f, 2.0f, 10, ActuallyTags.Blocks.NEEDS_BLACK_QUARTZ_TOOL, () -> Ingredient.of(ActuallyItems.BLACK_QUARTZ.get())), new ResourceLocation(ActuallyAdditions.MODID, "black_quartz"), List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
	public static final Tier RESTONIA = TierSortingRegistry.registerTier(new ForgeTier(2, 300, 7.0f, 2.25f, 12, ActuallyTags.Blocks.NEEDS_RESTONIA_TOOL, () -> Ingredient.of(ActuallyItems.RESTONIA_CRYSTAL.get())), new ResourceLocation(ActuallyAdditions.MODID, "restonia"), List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
	public static final Tier PALIS = TierSortingRegistry.registerTier(new ForgeTier(2, 300, 7.0f, 2.25f, 12, ActuallyTags.Blocks.NEEDS_PALIS_TOOL, () -> Ingredient.of(ActuallyItems.PALIS_CRYSTAL.get())), new ResourceLocation(ActuallyAdditions.MODID, "palis"), List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
	public static final Tier DIAMATINE = TierSortingRegistry.registerTier(new ForgeTier(3, 1600, 9.0f, 4.0f, 14, ActuallyTags.Blocks.NEEDS_DIAMATINE_TOOL, () -> Ingredient.of(ActuallyItems.DIAMATINE_CRYSTAL.get())), new ResourceLocation(ActuallyAdditions.MODID, "diamatine"), List.of(Tiers.DIAMOND), List.of(Tiers.NETHERITE));
	public static final Tier VOID = TierSortingRegistry.registerTier(new ForgeTier(2, 280, 6.0f, 2.0f, 8, ActuallyTags.Blocks.NEEDS_VOID_TOOL, () -> Ingredient.of(ActuallyItems.VOID_CRYSTAL.get())), new ResourceLocation(ActuallyAdditions.MODID, "void"), List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
	public static final Tier EMERADIC = TierSortingRegistry.registerTier(new ForgeTier(4, 2200, 9.5f, 5.55f, 18, ActuallyTags.Blocks.NEEDS_EMERADIC_TOOL, () -> Ingredient.of(ActuallyItems.EMERADIC_CRYSTAL.get())), new ResourceLocation(ActuallyAdditions.MODID, "emeradic"), List.of(Tiers.NETHERITE), List.of());
	public static final Tier EMERALD = TierSortingRegistry.registerTier(new ForgeTier(3, 2000, 9.0f, 5.0f, 15, net.minecraft.tags.BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(net.minecraft.world.item.Items.EMERALD)), new ResourceLocation(ActuallyAdditions.MODID, "emerald"), List.of(Tiers.DIAMOND), List.of(Tiers.NETHERITE));
	public static final Tier OBSIDIAN = TierSortingRegistry.registerTier(new ForgeTier(3, 8000, 4.0f, 2.0f, 15, net.minecraft.tags.BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(net.minecraft.world.item.Items.OBSIDIAN)), new ResourceLocation(ActuallyAdditions.MODID, "obsidian"), List.of(Tiers.DIAMOND), List.of(Tiers.NETHERITE));
	public static final Tier ENORI = TierSortingRegistry.registerTier(new ForgeTier(2, 280, 6.25f, 6.25f, 15, ActuallyTags.Blocks.NEEDS_ENORI_TOOL, () -> Ingredient.of(ActuallyItems.ENORI_CRYSTAL.get())), new ResourceLocation(ActuallyAdditions.MODID, "enori"), List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
}
