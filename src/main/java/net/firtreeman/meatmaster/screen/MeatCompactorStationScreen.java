package net.firtreeman.meatmaster.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.firtreeman.meatmaster.MeatMaster;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MeatCompactorStationScreen extends AbstractContainerScreen<MeatCompactorStationMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MeatMaster.MOD_ID, "textures/gui/meat_compactor_station.png");

    private static final int[] leftOrnamentYOffsets = new int[]{15, 35, 55};

    public MeatCompactorStationScreen(MeatCompactorStationMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
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
        if (menu.isProcessing()) {
            int scaledProgress = menu.getScaledProgress();
            int auxiliaryScaledProgress = menu.getAuxiliaryScaledProgress();
            int widgetScaledProgress = menu.getWidgetScaledProgress();

            guiGraphics.blit(TEXTURE, x + 82, y + 34, 176, 0, scaledProgress, 16);
            guiGraphics.blit(TEXTURE, x + 82, y + 20, 176, 16, auxiliaryScaledProgress, 8);
            guiGraphics.blit(TEXTURE, x + 82, y + 57, 176, 24, auxiliaryScaledProgress, 8);

            for (int i: leftOrnamentYOffsets) {
                guiGraphics.blit(TEXTURE, x + 6 + widgetScaledProgress, y + i, 176, 47, 2, 16);
                guiGraphics.blit(TEXTURE, x + 6, y + i, 176, 32, widgetScaledProgress, 15);
            }
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
