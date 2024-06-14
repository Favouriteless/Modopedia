package net.favouriteless.modopedia.books.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.BookRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.Optional;

public class TextPageComponent extends ModopediaPageComponent {

    public static final Codec<TextPageComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("text").forGetter(data -> data.text.getString()),
            Codec.INT.optionalFieldOf("width").forGetter(data -> Optional.of(data.width))
    ).apply(instance, (text, optional) -> new TextPageComponent(text, optional.orElse(100))));

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

}
