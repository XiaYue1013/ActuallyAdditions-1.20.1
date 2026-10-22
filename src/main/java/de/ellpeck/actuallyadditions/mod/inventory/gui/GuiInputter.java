/*
 * This file ("GuiInputter.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerInputter;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityInputter;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class GuiInputter extends AAScreen<ContainerInputter> {

    public static final int OFFSET_ADVANCED = 48;
    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_inputter");
    private static final ResourceLocation RES_LOC_ADVANCED = AssetUtil.getGuiLocation("gui_inputter_advanced");

    private static final String[] SIDES = new String[] {
            "Disabled", "Down", "Up", "North", "South", "West", "East"
    };

    public final TileEntityInputter tileInputter;
    private final boolean isAdvanced;

    private EditBox fieldPutStart;
    private EditBox fieldPutEnd;
    private EditBox fieldPullStart;
    private EditBox fieldPullEnd;

    private FilterSettingsGui leftFilterGui;
    private FilterSettingsGui rightFilterGui;

    public GuiInputter(ContainerInputter container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.tileInputter = container.tileInputter;
        this.isAdvanced = container.isAdvanced;
        this.imageWidth = 176;
        this.imageHeight = 97 + 86 + (this.isAdvanced ? OFFSET_ADVANCED : 0);
    }

    @Override
    public void init() {
        super.init();

        int topOffset = this.isAdvanced ? OFFSET_ADVANCED : 0;

        if (this.isAdvanced && this.tileInputter.leftFilter != null && this.tileInputter.rightFilter != null) {
            this.leftFilterGui = new FilterSettingsGui(this.tileInputter.leftFilter, this.leftPos + 3, this.topPos + 6, true, this::addRenderableWidget,
                    btnId -> PacketHandlerHelper.sendButtonPacket(this.tileInputter, 1000 + btnId), 0);
            this.rightFilterGui = new FilterSettingsGui(this.tileInputter.rightFilter, this.leftPos + 157, this.topPos + 6, true, this::addRenderableWidget,
                    btnId -> PacketHandlerHelper.sendButtonPacket(this.tileInputter, 2000 + btnId), 0);
        }

        this.fieldPullStart = new EditBox(this.font, this.leftPos + 6, this.topPos + 80 + topOffset, 34, 12, Component.literal(""));
        this.fieldPullStart.setMaxLength(5);
        this.fieldPullStart.setValue(String.valueOf(this.tileInputter.slotToPullStart));
        this.addRenderableWidget(this.fieldPullStart);

        this.fieldPullEnd = new EditBox(this.font, this.leftPos + 46, this.topPos + 80 + topOffset, 34, 12, Component.literal(""));
        this.fieldPullEnd.setMaxLength(5);
        this.fieldPullEnd.setValue(String.valueOf(this.tileInputter.slotToPullEnd));
        this.addRenderableWidget(this.fieldPullEnd);

        this.fieldPutStart = new EditBox(this.font, this.leftPos + 96, this.topPos + 80 + topOffset, 34, 12, Component.literal(""));
        this.fieldPutStart.setMaxLength(5);
        this.fieldPutStart.setValue(String.valueOf(this.tileInputter.slotToPutStart));
        this.addRenderableWidget(this.fieldPutStart);

        this.fieldPutEnd = new EditBox(this.font, this.leftPos + 136, this.topPos + 80 + topOffset, 34, 12, Component.literal(""));
        this.fieldPutEnd.setMaxLength(5);
        this.fieldPutEnd.setValue(String.valueOf(this.tileInputter.slotToPutEnd));
        this.addRenderableWidget(this.fieldPutEnd);

        // Side selector buttons: Pull (< and >)
        this.addRenderableWidget(Button.builder(Component.literal("<"), btn -> PacketHandlerHelper.sendButtonPacket(this.tileInputter, 3))
                .bounds(this.leftPos + 5, this.topPos + 43 + topOffset, 14, 14).build());
        this.addRenderableWidget(Button.builder(Component.literal(">"), btn -> PacketHandlerHelper.sendButtonPacket(this.tileInputter, 2))
                .bounds(this.leftPos + 68, this.topPos + 43 + topOffset, 14, 14).build());

        // Side selector buttons: Put (< and >)
        this.addRenderableWidget(Button.builder(Component.literal("<"), btn -> PacketHandlerHelper.sendButtonPacket(this.tileInputter, 1))
                .bounds(this.leftPos + 94, this.topPos + 43 + topOffset, 14, 14).build());
        this.addRenderableWidget(Button.builder(Component.literal(">"), btn -> PacketHandlerHelper.sendButtonPacket(this.tileInputter, 0))
                .bounds(this.leftPos + 157, this.topPos + 43 + topOffset, 14, 14).build());

        // Confirm OK button for text boxes
        this.addRenderableWidget(Button.builder(Component.literal("✓"), btn -> this.sendFields())
                .bounds(this.leftPos + 82, this.topPos + 80 + topOffset, 12, 12).build());
    }

    private void sendFields() {
        sendNumber(this.fieldPutStart.getValue(), 0);
        sendNumber(this.fieldPutEnd.getValue(), 1);
        sendNumber(this.fieldPullStart.getValue(), 2);
        sendNumber(this.fieldPullEnd.getValue(), 3);
    }

    private void sendNumber(String text, int id) {
        try {
            int val = Integer.parseInt(text.trim());
            PacketHandlerHelper.sendNumberPacket(this.tileInputter, val, id);
        } catch (NumberFormatException ignored) {
        }
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int x, int y, float f) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, x, y, f);

        int topOffset = this.isAdvanced ? OFFSET_ADVANCED : 0;

        String pullSideStr = SIDES[Math.max(0, Math.min(SIDES.length - 1, this.tileInputter.sideToPull + 1))];
        String putSideStr = SIDES[Math.max(0, Math.min(SIDES.length - 1, this.tileInputter.sideToPut + 1))];

        guiGraphics.drawCenteredString(this.font, Component.literal(pullSideStr), this.leftPos + 43, this.topPos + 46 + topOffset, 0xFFFFFF);
        guiGraphics.drawCenteredString(this.font, Component.literal(putSideStr), this.leftPos + 132, this.topPos + 46 + topOffset, 0xFFFFFF);

        guiGraphics.drawString(this.font, Component.literal("Pull").withStyle(ChatFormatting.DARK_GRAY), this.leftPos + 32, this.topPos + 32 + topOffset, 0x404040, false);
        guiGraphics.drawString(this.font, Component.literal("Put").withStyle(ChatFormatting.DARK_GRAY), this.leftPos + 125, this.topPos + 32 + topOffset, 0x404040, false);

        if (this.leftFilterGui != null) this.leftFilterGui.drawHover(guiGraphics, x, y);
        if (this.rightFilterGui != null) this.rightFilterGui.drawHover(guiGraphics, x, y);

        this.renderTooltip(guiGraphics, x, y);
    }

    @Override
    public void renderBg(GuiGraphics guiGraphics, float f, int x, int y) {
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        ResourceLocation loc = this.isAdvanced ? RES_LOC_ADVANCED : RES_LOC;
        int topOffset = this.isAdvanced ? OFFSET_ADVANCED : 0;

        guiGraphics.blit(AssetUtil.GUI_INVENTORY_LOCATION, this.leftPos, this.topPos + 97 + topOffset, 0, 0, 176, 86);
        guiGraphics.blit(loc, this.leftPos, this.topPos, 0, 0, 176, 97 + topOffset);
    }
}
