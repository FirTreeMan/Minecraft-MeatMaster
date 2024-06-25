package net.firtreeman.meatmaster.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.firtreeman.meatmaster.MeatMaster;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class IndustrialOvenStationScreen extends AbstractContainerScreen<IndustrialOvenStationMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MeatMaster.MOD_ID, "textures/gui/industrial_oven_station.png");

    private static final int[] leftOrnamentYOffsets = new int[]{15, 35, 55};

    public IndustrialOvenStationScreen(IndustrialOvenStationMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(pGuiGraphics, x, y);
    }

    protected void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isProcessing() || menu.isBurning()) {
            int[] scaledLit = menu.getScaledLit();

            guiGraphics.blit(TEXTURE, x + 90, y + 35, 176, 14, menu.getScaledProgress(), 17);
            for (int i = 0; i < scaledLit.length; i++)
                guiGraphics.blit(TEXTURE, x + 49, y + 17 + i * 19, 176, 0, scaledLit[i], 14);
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
