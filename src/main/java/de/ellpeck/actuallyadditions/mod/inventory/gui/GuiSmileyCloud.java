/*
 * This file ("GuiSmileyCloud.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerSmileyCloud;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntitySmileyCloud;
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
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class GuiSmileyCloud extends AAScreen<ContainerSmileyCloud> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_smiley_cloud");

    private final TileEntitySmileyCloud cloud;
    private EditBox nameField;

    public GuiSmileyCloud(ContainerSmileyCloud container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.cloud = container.cloud;
        this.imageWidth = 124;
        this.imageHeight = 20;
    }

    @Override
    public void init() {
        super.init();

        this.nameField = new EditBox(this.font, this.leftPos + 5, this.topPos + 6, 90, 10, Component.literal(""));
        this.nameField.setMaxLength(20);
        this.nameField.setBordered(false);
        if (this.cloud.name != null) {
            this.nameField.setValue(this.cloud.name);
        }
        this.nameField.setFocused(true);
        this.addRenderableWidget(this.nameField);

        this.addRenderableWidget(Button.builder(Component.literal("OK"), btn -> this.setCloudName())
                .bounds(this.leftPos + 98, this.topPos + 4, 22, 12).build());
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            this.setCloudName();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void setCloudName() {
        PacketHandlerHelper.sendStringPacket(this.cloud, this.nameField.getValue(), 0);
        this.onClose();
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int x, int y, float f) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, x, y, f);

        String name = this.cloud.name == null || this.cloud.name.isEmpty()
                ? ""
                : ChatFormatting.GOLD + this.cloud.name + ChatFormatting.RESET + " the ";
        String localizedName = name + "Smiley Cloud";
        guiGraphics.drawCenteredString(this.font, Component.literal(localizedName), this.leftPos + this.imageWidth / 2, this.topPos - 12, 0xFFFFFF);
    }

    @Override
    public void renderBg(GuiGraphics guiGraphics, float f, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(RES_LOC, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }
}
