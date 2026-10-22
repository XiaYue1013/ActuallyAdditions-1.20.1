/*
 * This file ("CompostRecipe.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class CompostRecipe {

    protected final Ingredient input;
    protected final ItemStack output;
    protected final BlockState inputDisplay;
    protected final BlockState outputDisplay;

    public CompostRecipe(ItemStack input, Block inputDisplay, ItemStack output, Block outputDisplay) {
        this(Ingredient.of(input), inputDisplay.defaultBlockState(), output, outputDisplay.defaultBlockState());
    }

    public CompostRecipe(Ingredient input, BlockState inputDisplay, ItemStack output, BlockState outputDisplay) {
        this.input = input;
        this.output = output;
        this.inputDisplay = inputDisplay;
        this.outputDisplay = outputDisplay;
    }

    public boolean matches(ItemStack stack) {
        return this.input.test(stack);
    }

    public Ingredient getInput() {
        return this.input;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public BlockState getInputDisplay() {
        return this.inputDisplay;
    }

    public BlockState getOutputDisplay() {
        return this.outputDisplay;
    }
}
