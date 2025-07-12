package net.favouriteless.modopedia.api.datagen.builders.page_components.components;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.api.book.page_components.ItemDisplay;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.ItemGalleryPageComponent;
import net.minecraft.resources.RegistryOps;

public class ItemGalleryBuilder extends PageComponentBuilder {

    private final Either<ItemDisplay, String> display;

    private ItemGalleryBuilder(ItemDisplay display) {
        super(ItemGalleryPageComponent.ID);
        this.display = Either.left(display);
    }

    private ItemGalleryBuilder(String display) {
        super(ItemGalleryPageComponent.ID);
        this.display = Either.right(display);
    }

    public static ItemGalleryBuilder of(ItemDisplay display) {
        return new ItemGalleryBuilder(display);
    }

    public static ItemGalleryBuilder of(String display) {
        return new ItemGalleryBuilder(display);
    }

    @Override
    public ItemGalleryBuilder x(int x) {
        return (ItemGalleryBuilder)super.x(x);
    }

    @Override
    public ItemGalleryBuilder x(String x) {
        return (ItemGalleryBuilder)super.x(x);
    }

    @Override
    public ItemGalleryBuilder y(int y) {
        return (ItemGalleryBuilder)super.y(y);
    }

    @Override
    public ItemGalleryBuilder y(String y) {
        return (ItemGalleryBuilder)super.y(y);
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        json.add("display", resolve(display, d -> ItemDisplay.codec().encodeStart(ops, d).getOrThrow()));
    }

}