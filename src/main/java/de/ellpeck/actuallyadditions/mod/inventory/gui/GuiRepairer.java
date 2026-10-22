/*
 * This file ("GuiRepairer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerRepairer;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityItemRepairer;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class GuiRepairer extends AAScreen<ContainerRepairer> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_repairer");
    private final TileEntityItemRepairer tileRepairer;
    private EnergyDisplay energy;

    public GuiRepairer(ContainerRepairer container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.tileRepairer = container.tileRepairer;
        this.imageWidth = 176;
        this.imageHeight = 93 + 86;
    }

    @Override
    public void init() {
        super.init();
        this.energy = new EnergyDisplay(this.leftPos + 27, this.topPos + 5, this.tileRepairer.storage);
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int x, int y, float f) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, x, y, f);
        this.energy.render(guiGraphics, x, y);
        renderTooltip(guiGraphics, x, y);
    }

    @Override
    public void renderBg(GuiGraphics guiGraphics, float f, int x, int y) {
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

        guiGraphics.blit(AssetUtil.GUI_INVENTORY_LOCATION, this.leftPos, this.topPos + 93, 0, 0, 176, 86);
        guiGraphics.blit(RES_LOC, this.leftPos, this.topPos, 0, 0, 176, 93);

        if (TileEntityItemRepairer.canBeRepaired(this.tileRepairer.inv.getStackInSlot(TileEntityItemRepairer.SLOT_INPUT))) {
            int i = this.tileRepairer.getItemDamageToScale(22);
            guiGraphics.blit(RES_LOC, this.leftPos + 73, this.topPos + 52, 176, 28, i, 16);
        }

        this.energy.draw(guiGraphics);
    }
}
