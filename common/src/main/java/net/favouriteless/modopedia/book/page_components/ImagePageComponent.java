package net.favouriteless.modopedia.book.page_components;

import net.favouriteless.modopedia.api.Variable.Lookup;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookTexture;
import net.favouriteless.modopedia.api.books.BookTexture.WidgetDetails;
import net.favouriteless.modopedia.api.books.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.api.books.page_components.PageWidgetHolder;
import net.favouriteless.modopedia.book.page_widgets.PageImageButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

public class ImagePageComponent extends PageComponent {

    protected ResourceLocation[] images;
    protected int width;
    protected int height;

    protected PageImageButton leftButton;
    protected PageImageButton rightButton;
    protected int selectedImage = 0;

    @Override
    public void init(Book book, Lookup lookup, Level level) {
        super.init(book, lookup, level);
        images = lookup.get("images").as(ResourceLocation[].class);
        width = lookup.getOrDefault("width", 100).asInt();
        height = lookup.getOrDefault("height", 100).asInt();

        if(images.length == 0)
            throw new IllegalArgumentException("Image gallery cannot have zero images in it.");
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {
        // Sizes look weird-- it's actually to force the texture to stretch to the size of the widget.
        graphics.blit(images[selectedImage], x, y, 0, 0, 0, width, height, width, height);
    }

    @Override
    public void initWidgets(PageWidgetHolder holder, BookRenderContext context) {
        if(images.length < 1)
            return;

        BookTexture bookTex = context.getBookTexture();
        ResourceLocation tex = bookTex.location();
        WidgetDetails left = bookTex.left();
        WidgetDetails right = bookTex.right();

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
        selectedImage = Mth.clamp(selectedImage + by, 0, images.length-1);
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

        if(selectedImage >= images.length - 1) {
            selectedImage = images.length - 1;
            rightButton.active = false;
        }
        else {
            rightButton.active = true;
        }
    }

}
