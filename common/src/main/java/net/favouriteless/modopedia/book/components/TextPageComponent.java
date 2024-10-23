package net.favouriteless.modopedia.book.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.books.BookRenderContext;
import net.favouriteless.modopedia.book.PageComponentRegistryImpl.PageComponentType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class TextPageComponent extends PositionedPageComponent {

    public static final MapCodec<TextPageComponent> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("x").forGetter(component -> component.x),
            Codec.INT.fieldOf("y").forGetter(component -> component.y),
            Codec.STRING.fieldOf("text").forGetter(component -> component.text.getString()),
            Codec.INT.optionalFieldOf("width", 100).forGetter(component -> component.width)
    ).apply(instance, TextPageComponent::new));

    protected final Component text;
    protected final int width;

    public TextPageComponent(int x, int y, String text, int width) {
        super(x, y);
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
