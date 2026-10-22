package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.Crate;
import de.ellpeck.actuallyadditions.mod.inventory.ActuallyContainers;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerGiantChest;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;

public class CrateBE extends TileEntityInventoryBase implements IButtonReactor {

    private final Crate.Size size;

    public CrateBE(BlockPos pos, BlockState state, Crate.Size size) {
        super(getTypeForSize(size), pos, state, size.getSlots());
        this.size = size;
    }

    public static BlockEntityType<?> getTypeForSize(Crate.Size size) {
        return switch (size) {
            case SMALL -> ActuallyBlocks.CRATE_SMALL.getTileEntityType();
            case MEDIUM -> ActuallyBlocks.CRATE_MEDIUM.getTileEntityType();
            case LARGE -> ActuallyBlocks.CRATE_LARGE.getTileEntityType();
        };
    }

    public Crate.Size getSize() {
        return this.size;
    }

    @Override
    public void onButtonPressed(int buttonID, Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            int maxPage = this.size == Crate.Size.LARGE ? 2 : (this.size == Crate.Size.MEDIUM ? 1 : 0);
            int targetPage = Mth.clamp(buttonID, 0, maxPage);
            openMenu(serverPlayer, targetPage);
        }
    }

    public void openMenu(ServerPlayer player, int page) {
        NetworkHooks.openScreen(player, new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.translatable("container.actuallyadditions." + size.name().toLowerCase() + "_crate");
            }

            @Override
            public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
                return new ContainerGiantChest(ActuallyContainers.CRATE_CONTAINER.get(), id, inv, CrateBE.this, page);
            }
        }, buf -> {
            buf.writeBlockPos(this.worldPosition);
            buf.writeInt(page);
        });
    }
}
