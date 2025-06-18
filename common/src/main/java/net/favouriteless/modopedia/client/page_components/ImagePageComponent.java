package net.favouriteless.modopedia.client.page_components;

import com.google.common.reflect.TypeToken;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.Lookup;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookTexture;
import net.favouriteless.modopedia.api.book.BookTexture.FixedRectangle;
import net.favouriteless.modopedia.api.book.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.book.page_components.PageComponent;
import net.favouriteless.modopedia.api.book.page_components.PageWidgetHolder;
import net.favouriteless.modopedia.client.page_widgets.PageImageButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

import java.util.List;

public class ImagePageComponent extends PageComponent {

    public static final ResourceLocation ID = Modopedia.id("image");

    protected List<ResourceLocation> images;
    protected int width;
    protected int height;

    protected PageImageButton leftButton;
    protected PageImageButton rightButton;
    protected int selectedImage = 0;

    @Override
    public void init(Book book, Lookup lookup, Level level) {
        super.init(book, lookup, level);
        images = lookup.get("images").as(new TypeToken<>() {});
        width = lookup.getOrDefault("width", 100).asInt();
        height = lookup.getOrDefault("height", 100).asInt();

        if(images.isEmpty())
            throw new IllegalArgumentException("Image gallery cannot have zero images in it.");
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {
        // Sizes look weird-- it's actually to force the texture to stretch to the size of the widget.
        graphics.blit(images.get(selectedImage), x, y, 0, 0, 0, width, height, width, height);
    }

    @Override
    public void initWidgets(PageWidgetHolder holder, BookRenderContext context) {
        if(images.isEmpty())
            return;

        BookTexture bookTex = context.getBookTexture();
        ResourceLocation tex = bookTex.location();
        FixedRectangle left = bookTex.left();
        FixedRectangle right = bookTex.right();

        leftButton = holder.addRenderableWidget(
                new PageImageButton(tex, x, y + height - left.height(), left.width(), left.height(),
                        left.u(), left.v(), bookTex.texWidth(), bookTex.texHeight(), b -> changeImage(-1))
        );
        rightButton = holder.addRenderableWidget(
                new PageImageButton(tex, x + width - right.width(), y + height - right.height(), right.width(), right.height(),
                        right.u(), right.v(), bookTex.texWidth(), bookTex.texHeight(), b -> changeImage(1))
        );

        updateWidgetVisibility();
    }

    protected void changeImage(int by) {
        selectedImage = Mth.clamp(selectedImage + by, 0, images.size() - 1);
        updateWidgetVisibility();
    }

    protected void updateWidgetVisibility() {
        if(selectedImage <= 0) {
            selectedImage = 0;
            leftButton.active = false;
        }
        else {
            leftButton.active = true;
        }

        if(selectedImage >= images.size() - 1) {
            selectedImage = images.size() - 1;
            rightButton.active = false;
        }
        else {
            rightButton.active = true;
        }
    }

}
