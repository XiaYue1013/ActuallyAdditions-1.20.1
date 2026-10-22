/*
 * This file ("GuiGiantChest.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.blocks.Crate;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerGiantChest;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.CrateBE;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiGiantChest extends AbstractContainerScreen<ContainerGiantChest> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_giant_chest");

    private final CrateBE chest;
    private final int page;

    public GuiGiantChest(ContainerGiantChest container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);
        this.chest = container.tileChest;
        this.page = container.page;

        this.imageWidth = 242;
        this.imageHeight = 258;
    }

    @Override
    protected void init() {
        super.init();

        if (this.page > 0) {
            this.addRenderableWidget(Button.builder(Component.literal("<"), btn -> PacketHandlerHelper.sendButtonPacket(this.chest, this.page - 1))
                .bounds(this.leftPos + 13, this.topPos + 172, 20, 20).build());
        }

        int maxPage = this.chest.getSize() == Crate.Size.LARGE ? 2 : (this.chest.getSize() == Crate.Size.MEDIUM ? 1 : 0);
        if (this.page < maxPage) {
            this.addRenderableWidget(Button.builder(Component.literal(">"), btn -> PacketHandlerHelper.sendButtonPacket(this.chest, this.page + 1))
                .bounds(this.leftPos + 209, this.topPos + 172, 20, 20).build());
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        guiGraphics.blit(RES_LOC, this.leftPos, this.topPos, 0, 0, 242, 190);
        guiGraphics.blit(AssetUtil.GUI_INVENTORY_LOCATION, this.leftPos + 33, this.topPos + 172, 0, 0, 176, 86);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, 8, 6, 0x404040, false);
    }
}
