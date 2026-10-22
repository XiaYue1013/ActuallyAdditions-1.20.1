package de.ellpeck.actuallyadditions.mod.gen;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public class ActuallyConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_BLACK_QUARTZ = createKey("ore_black_quartz");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_BLACK_LOTUS = createKey("flower_black_lotus");

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
		RuleTest stoneRuleTest = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
		RuleTest deepslateRuleTest = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
		List<OreConfiguration.TargetBlockState> list = List.of(
				OreConfiguration.target(stoneRuleTest, ActuallyBlocks.BLACK_QUARTZ_ORE.get().defaultBlockState()),
				OreConfiguration.target(deepslateRuleTest, ActuallyBlocks.BLACK_QUARTZ_ORE.get().defaultBlockState())
		);
		FeatureUtils.register(context, ORE_BLACK_QUARTZ, Feature.ORE, new OreConfiguration(list, 6));

		FeatureUtils.register(context, FLOWER_BLACK_LOTUS, Feature.FLOWER,
				new RandomPatchConfiguration(32, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
						new SimpleBlockConfiguration(BlockStateProvider.simple(ActuallyBlocks.BLACK_LOTUS.get())))));
	}

	private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, ActuallyAdditions.modLoc(name));
	}
}
