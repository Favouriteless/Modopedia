package net.favouriteless.modopedia.client.page_widgets;

import net.favouriteless.modopedia.api.book.page_components.AbstractPageWidget;
import net.favouriteless.modopedia.api.book.page_components.BookRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

public class PageImageButton extends AbstractPageWidget {

    protected final ResourceLocation texture;
    protected final int u;
    protected final int v;
    protected final int texWidth;
    protected final int texHeight;
    protected final OnPress onPress;

    public PageImageButton(ResourceLocation texture, int x, int y, int width, int height, int u, int v, int texWidth, int texHeight, OnPress onPress) {
        super(x, y, width, height);
        this.texture = texture;
        this.u = u;
        this.v = v;
        this.texWidth = texWidth;
        this.texHeight = texHeight;
        this.onPress = onPress;
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {
        int v = context.isHovered(mouseX, mouseY, x, y, width, height) ? this.v + height : this.v;
        graphics.blit(texture, x, y, u, v, width, height, texWidth, texHeight);
    }

    @Override
    public boolean mouseClicked(BookRenderContext context, double mouseX, double mouseY, int button) {
        if(active && button == 0 && context.isHovered(mouseX, mouseY, x, y, width, height)) {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            onPress.onPress(this);
            return true;
        }

        return false;
    }

    @FunctionalInterface
    public interface OnPress {

        void onPress(PageImageButton button);

    }

}
