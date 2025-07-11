package net.favouriteless.modopedia.api.datagen.builders.templates;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.page_components.ItemDisplay;
import net.favouriteless.modopedia.api.datagen.builders.page_components.TemplateComponentBuilder;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;

public class GridFramedItemGalleryBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("grid_framed_item_gallery");

    private final Either<ItemDisplay, String> display;

    private GridFramedItemGalleryBuilder(ItemDisplay display) {
        super(ID);
        this.display = Either.left(display);
    }

    private GridFramedItemGalleryBuilder(String display) {
        super(ID);
        this.display = Either.right(display);
    }

    public static GridFramedItemGalleryBuilder of(ItemDisplay display) {
        return new GridFramedItemGalleryBuilder(display);
    }

    public static GridFramedItemGalleryBuilder of(String display) {
        return new GridFramedItemGalleryBuilder(display);
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        json.add("display", resolve(display, d -> ItemDisplay.codec().encodeStart(ops, d).getOrThrow()));
    }

}
