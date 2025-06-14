package net.favouriteless.modopedia.client.screens.widgets;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class BookImageButton extends Button {

    private final Holder<SoundEvent> sound;
    private final ResourceLocation texture;
    private final int u;
    private final int v;
    private final int texWidth;
    private final int texHeight;

    public BookImageButton(ResourceLocation texture, int x, int y, int width, int height, int u, int v, int texWidth, int texHeight,
                           OnPress onPress, Holder<SoundEvent> sound) {
        super(x, y, width, height, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
        this.texture = texture;
        this.u = u;
        this.v = v;
        this.texWidth = texWidth;
        this.texHeight = texHeight;
        this.sound = sound;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        int v = isHovered() ? this.v + height : this.v;
        graphics.blit(texture, getX(), getY(), u, v, width, height, texWidth, texHeight);
    }

    @Override
    public void playDownSound(SoundManager handler) {
        handler.play(SimpleSoundInstance.forUI(sound, 1.0F));
    }

}
