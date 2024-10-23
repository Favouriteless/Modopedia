package net.favouriteless.modopedia.book.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.books.BookRenderContext;
import net.favouriteless.modopedia.book.PageComponentRegistryImpl.PageComponentType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ImagePageComponent extends PositionedPageComponent {

    public static final MapCodec<ImagePageComponent> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("x").forGetter(component -> component.x),
            Codec.INT.fieldOf("y").forGetter(component -> component.y),
            ResourceLocation.CODEC.fieldOf("image").forGetter(component -> component.image),
            Codec.INT.optionalFieldOf("width", 100).forGetter(component -> component.width),
            Codec.INT.optionalFieldOf("height", 100).forGetter(component -> component.height)
            ).apply(instance, ImagePageComponent::new));

    protected final ResourceLocation image;
    private final int width;
    private final int height;

    public ImagePageComponent(int x, int y, ResourceLocation image, int width, int height) {
        super(x, y);
        this.image = image;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int xMouse, int yMouse, float partialTicks) {
        graphics.blit(image, x, y, 0, 0, width, height);
    }

    @Override
    public PageComponentType type() {
        return DefaultPageComponents.IMAGE;
    }

}
