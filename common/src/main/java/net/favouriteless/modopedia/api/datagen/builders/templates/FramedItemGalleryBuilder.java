package net.favouriteless.modopedia.api.datagen.builders.templates;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.page_components.ItemDisplay;
import net.favouriteless.modopedia.api.datagen.builders.page_components.TemplateComponentBuilder;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;

public class FramedItemGalleryBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("framed_item_gallery");

    private final Either<ItemDisplay, String> display;
    private Either<Integer, String> width;

    private FramedItemGalleryBuilder(ItemDisplay display) {
        super(ID);
        this.display = Either.left(display);
    }

    private FramedItemGalleryBuilder(String display) {
        super(ID);
        this.display = Either.right(display);
    }

    public static FramedItemGalleryBuilder of(ItemDisplay display) {
        return new FramedItemGalleryBuilder(display);
    }

    public static FramedItemGalleryBuilder of(String display) {
        return new FramedItemGalleryBuilder(display);
    }

    public FramedItemGalleryBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public FramedItemGalleryBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        json.add("display", resolve(display, d -> ItemDisplay.codec().encodeStart(ops, d).getOrThrow()));

        if(width != null)
            json.add("width", resolveNum(width));
    }

}
