package net.favouriteless.modopedia.client.screens.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.CommonInputs;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;

import java.util.function.BiConsumer;

/**
 * MButton is a variation on {@link Button} which passes the mouse button id to its consumer on click.
 */
public class MButton extends AbstractWidget {

    public static final int TEXT_MARGIN = 2;
    public static final WidgetSprites SPRITES = new WidgetSprites(
            ResourceLocation.withDefaultNamespace("widget/button"),
            ResourceLocation.withDefaultNamespace("widget/button_disabled"),
            ResourceLocation.withDefaultNamespace("widget/button_highlighted")
    );

    public final BiConsumer<MButton, Integer> onClick;

    public MButton(int x, int y, int width, int height, Component message, BiConsumer<MButton, Integer> onClick) {
        super(x, y, width, height, message);
        this.onClick = onClick;
    }

    public MButton(int width, int height, Component message, BiConsumer<MButton, Integer> onClick) {
        super(0, 0, width, height, message);
        this.onClick = onClick;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        Minecraft mc = Minecraft.getInstance();

        graphics.setColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        graphics.blitSprite(SPRITES.get(active, isHoveredOrFocused()), getX(), getY(), getWidth(), getHeight());

        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        int colour = active ? 0xFFFFFF : 0xA0A0A0; // White : Gray
        renderScrollingString(graphics, mc.font, TEXT_MARGIN, colour | Mth.ceil(alpha * 255.0F) << 24);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        defaultButtonNarrationText(output);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(!active || !visible)
            return false;

        if(CommonInputs.selected(keyCode)) {
            playDownSound(Minecraft.getInstance().getSoundManager());
            onClick.accept(this, GLFW.GLFW_MOUSE_BUTTON_LEFT);
            return true;
        }

        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(!active || !visible)
            return false;
        if(button != GLFW.GLFW_MOUSE_BUTTON_LEFT && button != GLFW.GLFW_MOUSE_BUTTON_RIGHT)
            return false;

        if(clicked(mouseX, mouseY)) {
            playDownSound(Minecraft.getInstance().getSoundManager());
            onClick.accept(this, button);
            return true;
        }

        return false;
    }

}
