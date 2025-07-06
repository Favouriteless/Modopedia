package net.favouriteless.modopedia.api.datagen.builders;

import net.favouriteless.modopedia.api.book.BookTexture;
import net.favouriteless.modopedia.api.book.BookTexture.FixedRectangle;
import net.favouriteless.modopedia.api.book.BookTexture.Rectangle;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class BookTextureBuilder {

    public static Stage1 of() {
        return new Builder();
    }

    public interface Stage1 {
        Stage2 texture(ResourceLocation location, int texWidth, int texHeight);
    }

    public interface Stage2 {
        Stage3 sized(int width, int height);
    }

    public interface Stage3 {
        Stage4 titleBacker(int x, int y, int u, int v, int width, int height);
    }

    public interface Stage4 {
        Stage5 leftButton(int x, int y, int u, int v, int width, int height);
    }

    public interface Stage5 {
        Stage6 rightButton(int x, int y, int u, int v, int width, int height);
    }

    public interface Stage6 {
        Stage7 backButton(int x, int y, int u, int v, int width, int height);
    }

    public interface Stage7 {
        Stage8 refreshButton(int x, int y, int u, int v, int width, int height);
    }

    public interface Stage8 {
        FinalStage page(int u, int v, int width, int height);
    }

    public interface FinalStage {

        FinalStage page(int u, int v, int width, int height);

        FinalStage widget(String name, int u, int v, int width, int height);

        void build(String id, BiConsumer<String, BookTexture> output);

    }

    public static class Builder implements Stage1, Stage2, Stage3, Stage4, Stage5, Stage6, Stage7, Stage8, FinalStage {

        private ResourceLocation location;
        private int texWidth;
        private int texHeight;
        private int width;
        private int height;
        private FixedRectangle titleBacker;
        private FixedRectangle left;
        private FixedRectangle right;
        private FixedRectangle back;
        private FixedRectangle refresh;

        private List<Rectangle> pages = new ArrayList<>();
        private Map<String, Rectangle> widgets = new HashMap<>();

        private Builder() {}

        @Override
        public Stage2 texture(ResourceLocation location, int texWidth, int texHeight) {
            this.location = location;
            this.texWidth = texWidth;
            this.texHeight = texHeight;
            return this;
        }

        @Override
        public Stage3 sized(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        @Override
        public Stage4 titleBacker(int x, int y, int u, int v, int width, int height) {
            this.titleBacker = FixedRectangle.of(x, y, u, v, width, height);
            return this;
        }

        @Override
        public Stage5 leftButton(int x, int y, int u, int v, int width, int height) {
            this.left = FixedRectangle.of(x, y, u, v, width, height);
            return this;
        }

        @Override
        public Stage6 rightButton(int x, int y, int u, int v, int width, int height) {
            this.right = FixedRectangle.of(x, y, u, v, width, height);
            return this;
        }

        @Override
        public Stage7 backButton(int x, int y, int u, int v, int width, int height) {
            this.back = FixedRectangle.of(x, y, u, v, width, height);
            return this;
        }

        @Override
        public Stage8 refreshButton(int x, int y, int u, int v, int width, int height) {
            this.refresh = FixedRectangle.of(x, y, u, v, width, height);
            return this;
        }

        @Override
        public FinalStage page(int u, int v, int width, int height) {
            pages.add(Rectangle.of(u, v, width, height));
            return this;
        }

        @Override
        public FinalStage widget(String name, int u, int v, int width, int height) {
            widgets.put(name, Rectangle.of(u, v, width, height));
            return this;
        }

        @Override
        public void build(String id, BiConsumer<String, BookTexture> output) {
            if(pages.isEmpty())
                throw new IllegalStateException("BookTexture has zero pages:" + id);
            output.accept(id, new BookTexture(location, width, height, texWidth, texHeight, pages, titleBacker, left, right, back, refresh, widgets));
        }

    }

}
