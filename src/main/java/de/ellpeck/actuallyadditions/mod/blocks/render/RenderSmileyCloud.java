/*
 * This file ("RenderSmileyCloud.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.render;

import com.mojang.blaze3d.vertex.PoseStack;
import de.ellpeck.actuallyadditions.mod.tile.TileEntitySmileyCloud;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderSmileyCloud implements BlockEntityRenderer<TileEntitySmileyCloud> {

    public RenderSmileyCloud(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileEntitySmileyCloud theCloud, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        if (theCloud != null && theCloud.name != null && !theCloud.name.isEmpty()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && mc.player.distanceToSqr(theCloud.getBlockPos().getX() + 0.5D, theCloud.getBlockPos().getY() + 0.5D, theCloud.getBlockPos().getZ() + 0.5D) <= 36.0D) {
                matrices.pushPose();
                matrices.translate(0.5D, 1.25D, 0.5D);
                matrices.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
                matrices.scale(-0.025F, -0.025F, 0.025F);

                Font font = mc.font;
                String text = theCloud.name;
                float x = -font.width(text) / 2.0F;
                font.drawInBatch(text, x, 0, 0xFFFFFF, false, matrices.last().pose(), buffer, Font.DisplayMode.SEE_THROUGH, 0x40000000, combinedLight);
                font.drawInBatch(text, x, 0, 0xFFFFFF, false, matrices.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, combinedLight);
                matrices.popPose();
            }
        }
    }
}
