package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ItemTagsGenerator extends ItemTagsProvider {
    public ItemTagsGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider,
        TagsProvider<Block> blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, blockTagProvider.contentsGetter(), ActuallyAdditions.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ActuallyTags.Items.COFFEE_BEANS)
            .add(ActuallyItems.COFFEE_BEANS.get());
        tag(ActuallyTags.Items.TINY_COALS)
            .add(ActuallyItems.TINY_COAL.get())
            .add(ActuallyItems.TINY_CHARCOAL.get());
        tag(ActuallyTags.Items.DRILLS).add(
            ActuallyItems.DRILL_MAIN.get(),
            ActuallyItems.DRILL_BLACK.get(), ActuallyItems.DRILL_BLACK.get(), ActuallyItems.DRILL_BLUE.get(), ActuallyItems.DRILL_BROWN.get(),
            ActuallyItems.DRILL_CYAN.get(), ActuallyItems.DRILL_GRAY.get(), ActuallyItems.DRILL_GREEN.get(), ActuallyItems.DRILL_LIGHT_GRAY.get(),
            ActuallyItems.DRILL_LIME.get(), ActuallyItems.DRILL_MAGENTA.get(), ActuallyItems.DRILL_ORANGE.get(), ActuallyItems.DRILL_PINK.get(),
            ActuallyItems.DRILL_PURPLE.get(), ActuallyItems.DRILL_RED.get(), ActuallyItems.DRILL_WHITE.get(), ActuallyItems.DRILL_YELLOW.get()
        );
        tag(ActuallyTags.Items.CRYSTALS)
                .add(ActuallyItems.RESTONIA_CRYSTAL.get(), ActuallyItems.PALIS_CRYSTAL.get(),
                        ActuallyItems.DIAMATINE_CRYSTAL.get(), ActuallyItems.VOID_CRYSTAL.get(),
                        ActuallyItems.EMERADIC_CRYSTAL.get(), ActuallyItems.ENORI_CRYSTAL.get());
        this.tag(ActuallyTags.Items.CRYSTAL_BLOCKS)
                .add(ActuallyBlocks.RESTONIA_CRYSTAL.getItem(), ActuallyBlocks.PALIS_CRYSTAL.getItem(),
                        ActuallyBlocks.DIAMATINE_CRYSTAL.getItem(), ActuallyBlocks.VOID_CRYSTAL.getItem(),
                        ActuallyBlocks.EMERADIC_CRYSTAL.getItem(), ActuallyBlocks.ENORI_CRYSTAL.getItem());

        tag(Tags.Items.SLIMEBALLS)
                .add(ActuallyItems.RICE_SLIMEBALL.get());

        tag(ActuallyTags.Items.CROPS_RICE).add(ActuallyItems.RICE.get());
        tag(ActuallyTags.Items.CROPS_COFFEE).add(ActuallyItems.COFFEE_BEANS.get());
        tag(ActuallyTags.Items.CROPS_CANOLA).add(ActuallyItems.CANOLA.get());
        tag(ActuallyTags.Items.CROPS_FLAX).add(ActuallyItems.FLAX_SEEDS.get());
        tag(Tags.Items.CROPS).addTags(ActuallyTags.Items.CROPS_RICE, ActuallyTags.Items.CROPS_COFFEE, ActuallyTags.Items.CROPS_CANOLA, ActuallyTags.Items.CROPS_FLAX);

        tag(ActuallyTags.Items.SEEDS_RICE).add(ActuallyItems.RICE_SEEDS.get());
        tag(ActuallyTags.Items.SEEDS_COFFEE).add(ActuallyItems.COFFEE_BEANS.get());
        tag(ActuallyTags.Items.SEEDS_CANOLA).add(ActuallyItems.CANOLA_SEEDS.get());
        tag(ActuallyTags.Items.SEEDS_FLAX).add(ActuallyItems.FLAX_SEEDS.get());
        tag(Tags.Items.SEEDS).addTags(ActuallyTags.Items.SEEDS_RICE, ActuallyTags.Items.SEEDS_COFFEE, ActuallyTags.Items.SEEDS_CANOLA, ActuallyTags.Items.SEEDS_FLAX);

        tag(ActuallyTags.Items.GEMS_BLACK_QUARTZ).add(ActuallyItems.BLACK_QUARTZ.get());
        tag(ActuallyTags.Items.ORES_BLACK_QUARTZ).add(ActuallyBlocks.BLACK_QUARTZ_ORE.getItem());
        tag(Tags.Items.ORES).addTags(ActuallyTags.Items.ORES_BLACK_QUARTZ);
        tag(Tags.Items.ORES_IN_GROUND_STONE).add(ActuallyBlocks.BLACK_QUARTZ_ORE.getItem());
        tag(ActuallyTags.Items.STORAGE_BLOCKS_BLACK_QUARTZ).add(ActuallyBlocks.BLACK_QUARTZ.getItem());

        tag(net.minecraft.tags.ItemTags.FLOWERS).add(ActuallyBlocks.BLACK_LOTUS.getItem());
        tag(net.minecraft.tags.ItemTags.SMALL_FLOWERS).add(ActuallyBlocks.BLACK_LOTUS.getItem());
        tag(Tags.Items.STORAGE_BLOCKS).add(ActuallyBlocks.CHARCOAL_BLOCK.getItem(), ActuallyBlocks.ENDER_PEARL_BLOCK.getItem());

        tag(net.minecraft.tags.ItemTags.PICKAXES).add(
            ActuallyItems.EMERALD_PICKAXE.get(), ActuallyItems.OBSIDIAN_PICKAXE.get(), ActuallyItems.QUARTZ_PICKAXE.get(),
            ActuallyItems.RESTONIA_PICKAXE.get(), ActuallyItems.PALIS_PICKAXE.get(), ActuallyItems.DIAMATINE_PICKAXE.get(),
            ActuallyItems.VOID_PICKAXE.get(), ActuallyItems.EMERADIC_PICKAXE.get(), ActuallyItems.ENORI_PICKAXE.get(),
            ActuallyItems.WOODEN_AIOT.get(), ActuallyItems.STONE_AIOT.get(), ActuallyItems.IRON_AIOT.get(),
            ActuallyItems.GOLD_AIOT.get(), ActuallyItems.DIAMOND_AIOT.get(), ActuallyItems.NETHERITE_AIOT.get(),
            ActuallyItems.EMERALD_AIOT.get(), ActuallyItems.OBSIDIAN_AIOT.get(), ActuallyItems.QUARTZ_AIOT.get(),
            ActuallyItems.RESTONIA_AIOT.get(), ActuallyItems.PALIS_AIOT.get(), ActuallyItems.DIAMATINE_AIOT.get(),
            ActuallyItems.VOID_AIOT.get(), ActuallyItems.EMERADIC_AIOT.get(), ActuallyItems.ENORI_AIOT.get()
        );
        tag(net.minecraft.tags.ItemTags.AXES).add(
            ActuallyItems.EMERALD_AXE.get(), ActuallyItems.OBSIDIAN_AXE.get(), ActuallyItems.QUARTZ_AXE.get(),
            ActuallyItems.RESTONIA_AXE.get(), ActuallyItems.PALIS_AXE.get(), ActuallyItems.DIAMATINE_AXE.get(),
            ActuallyItems.VOID_AXE.get(), ActuallyItems.EMERADIC_AXE.get(), ActuallyItems.ENORI_AXE.get(),
            ActuallyItems.WOODEN_AIOT.get(), ActuallyItems.STONE_AIOT.get(), ActuallyItems.IRON_AIOT.get(),
            ActuallyItems.GOLD_AIOT.get(), ActuallyItems.DIAMOND_AIOT.get(), ActuallyItems.NETHERITE_AIOT.get(),
            ActuallyItems.EMERALD_AIOT.get(), ActuallyItems.OBSIDIAN_AIOT.get(), ActuallyItems.QUARTZ_AIOT.get(),
            ActuallyItems.RESTONIA_AIOT.get(), ActuallyItems.PALIS_AIOT.get(), ActuallyItems.DIAMATINE_AIOT.get(),
            ActuallyItems.VOID_AIOT.get(), ActuallyItems.EMERADIC_AIOT.get(), ActuallyItems.ENORI_AIOT.get()
        );
        tag(net.minecraft.tags.ItemTags.SHOVELS).add(
            ActuallyItems.EMERALD_SHOVEL.get(), ActuallyItems.OBSIDIAN_SHOVEL.get(), ActuallyItems.QUARTZ_SHOVEL.get(),
            ActuallyItems.RESTONIA_SHOVEL.get(), ActuallyItems.PALIS_SHOVEL.get(), ActuallyItems.DIAMATINE_SHOVEL.get(),
            ActuallyItems.VOID_SHOVEL.get(), ActuallyItems.EMERADIC_SHOVEL.get(), ActuallyItems.ENORI_SHOVEL.get(),
            ActuallyItems.WOODEN_AIOT.get(), ActuallyItems.STONE_AIOT.get(), ActuallyItems.IRON_AIOT.get(),
            ActuallyItems.GOLD_AIOT.get(), ActuallyItems.DIAMOND_AIOT.get(), ActuallyItems.NETHERITE_AIOT.get(),
            ActuallyItems.EMERALD_AIOT.get(), ActuallyItems.OBSIDIAN_AIOT.get(), ActuallyItems.QUARTZ_AIOT.get(),
            ActuallyItems.RESTONIA_AIOT.get(), ActuallyItems.PALIS_AIOT.get(), ActuallyItems.DIAMATINE_AIOT.get(),
            ActuallyItems.VOID_AIOT.get(), ActuallyItems.EMERADIC_AIOT.get(), ActuallyItems.ENORI_AIOT.get()
        );
        tag(net.minecraft.tags.ItemTags.SWORDS).add(
            ActuallyItems.EMERALD_SWORD.get(), ActuallyItems.OBSIDIAN_SWORD.get(), ActuallyItems.QUARTZ_SWORD.get(),
            ActuallyItems.RESTONIA_SWORD.get(), ActuallyItems.PALIS_SWORD.get(), ActuallyItems.DIAMATINE_SWORD.get(),
            ActuallyItems.VOID_SWORD.get(), ActuallyItems.EMERADIC_SWORD.get(), ActuallyItems.ENORI_SWORD.get(),
            ActuallyItems.WOODEN_AIOT.get(), ActuallyItems.STONE_AIOT.get(), ActuallyItems.IRON_AIOT.get(),
            ActuallyItems.GOLD_AIOT.get(), ActuallyItems.DIAMOND_AIOT.get(), ActuallyItems.NETHERITE_AIOT.get(),
            ActuallyItems.EMERALD_AIOT.get(), ActuallyItems.OBSIDIAN_AIOT.get(), ActuallyItems.QUARTZ_AIOT.get(),
            ActuallyItems.RESTONIA_AIOT.get(), ActuallyItems.PALIS_AIOT.get(), ActuallyItems.DIAMATINE_AIOT.get(),
            ActuallyItems.VOID_AIOT.get(), ActuallyItems.EMERADIC_AIOT.get(), ActuallyItems.ENORI_AIOT.get()
        );
        tag(net.minecraft.tags.ItemTags.HOES).add(
            ActuallyItems.EMERALD_HOE.get(), ActuallyItems.OBSIDIAN_HOE.get(), ActuallyItems.QUARTZ_HOE.get(),
            ActuallyItems.RESTONIA_HOE.get(), ActuallyItems.PALIS_HOE.get(), ActuallyItems.DIAMATINE_HOE.get(),
            ActuallyItems.VOID_HOE.get(), ActuallyItems.EMERADIC_HOE.get(), ActuallyItems.ENORI_HOE.get(),
            ActuallyItems.WOODEN_AIOT.get(), ActuallyItems.STONE_AIOT.get(), ActuallyItems.IRON_AIOT.get(),
            ActuallyItems.GOLD_AIOT.get(), ActuallyItems.DIAMOND_AIOT.get(), ActuallyItems.NETHERITE_AIOT.get(),
            ActuallyItems.EMERALD_AIOT.get(), ActuallyItems.OBSIDIAN_AIOT.get(), ActuallyItems.QUARTZ_AIOT.get(),
            ActuallyItems.RESTONIA_AIOT.get(), ActuallyItems.PALIS_AIOT.get(), ActuallyItems.DIAMATINE_AIOT.get(),
            ActuallyItems.VOID_AIOT.get(), ActuallyItems.EMERADIC_AIOT.get(), ActuallyItems.ENORI_AIOT.get()
        );
    }
}
