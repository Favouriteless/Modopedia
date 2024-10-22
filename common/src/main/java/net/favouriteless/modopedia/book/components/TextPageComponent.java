package net.favouriteless.modopedia.book.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.BookRenderContext;
import net.favouriteless.modopedia.common.PageComponentRegistryImpl.PageComponentType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class TextPageComponent extends PageComponentImpl {

    public static final MapCodec<TextPageComponent> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("text").forGetter(component -> component.text.getString()),
            Codec.INT.optionalFieldOf("width", 100).forGetter(component -> component.width)
    ).apply(instance, TextPageComponent::new));

    private final Component text;
    private final int width;

    public TextPageComponent(String text, int width) {
        this.text = Component.literal(text);
        this.width = width;
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int xMouse, int yMouse, float partialTicks) {
        graphics.drawString(Minecraft.getInstance().font, text, x, y, 0, false);
    }

    @Override
    public PageComponentType type() {
        return DefaultPageComponents.TEXT;
    }

}
