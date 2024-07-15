package net.firtreeman.meatmaster.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.firtreeman.meatmaster.MeatMaster;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class HormoneResearchStationScreen extends AbstractContainerScreen<HormoneResearchStationMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MeatMaster.MOD_ID, "textures/gui/hormone_research_station.png");

    public HormoneResearchStationScreen(HormoneResearchStationMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
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

        renderResearchProgress(pGuiGraphics, x, y);
        renderFillProgress(pGuiGraphics, x, y);
    }

    protected void renderResearchProgress(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isProcessingResearch()) {
            guiGraphics.blit(TEXTURE, x + 45, y + 30, 176, 0, menu.getScaledResearchProgress(), 4);

            // determiner
            if (menu.hasDeterminer()) {
                guiGraphics.blit(TEXTURE, x + 102, y + 27, 176 + menu.getDeterminerOffset(), 4, 25, 8);
                guiGraphics.blit(TEXTURE, x + 101, y + 26, 176, 12, 1, 10);
                guiGraphics.blit(TEXTURE, x + 127, y + 26, 176, 12, 1, 10);
            }
        }
    }

    protected void renderFillProgress(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isProcessingFill()) {
            guiGraphics.blit(TEXTURE, x + 46, y + 59, 176, 34, menu.getScaledFillProgressLeft(), 4);
            guiGraphics.blit(TEXTURE, x + 89, y + 59, 176, menu.getFillProgressRightHeight(), menu.getScaledFillProgressRight(), 4);
        }
        if (menu.hasBase())
            guiGraphics.blit(TEXTURE, x + 84, y + 55, 176, 22, 6, 12);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        menu.checkDeterminer();
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
