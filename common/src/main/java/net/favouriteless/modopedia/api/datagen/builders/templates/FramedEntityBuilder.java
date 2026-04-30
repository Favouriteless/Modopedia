package net.favouriteless.modopedia.api.datagen.builders.templates;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.datagen.builders.page_components.TemplateComponentBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class FramedEntityBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation LARGE_ID = Modopedia.id("large_framed_entity");
    public static final ResourceLocation MEDIUM_ID = Modopedia.id("medium_framed_entity");

    private final Either<EntityType<?>, String> entity;
    private Either<CompoundTag, String> tag;
    private Either<Float, String> offsetY;
    private Either<Float, String> scale;
    private Either<Integer, String> width;

    private FramedEntityBuilder(EntityType<?> type, ResourceLocation id) {
        super(id);
        this.entity = Either.left(type);
    }

    private FramedEntityBuilder(String type, ResourceLocation id) {
        super(id);
        this.entity = Either.right(type);
    }

    public static FramedEntityBuilder large(EntityType<?> type) {
        return new FramedEntityBuilder(type, LARGE_ID);
    }

    public static FramedEntityBuilder large(String type) {
        return new FramedEntityBuilder(type, LARGE_ID);
    }

    public static FramedEntityBuilder medium(EntityType<?> type) {
        return new FramedEntityBuilder(type, MEDIUM_ID);
    }

    public static FramedEntityBuilder medium(String type) {
        return new FramedEntityBuilder(type, MEDIUM_ID);
    }

    @Override
    public FramedEntityBuilder x(int x) {
        return (FramedEntityBuilder)super.x(x);
    }

    @Override
    public FramedEntityBuilder x(String x) {
        return (FramedEntityBuilder)super.x(x);
    }

    @Override
    public FramedEntityBuilder y(int y) {
        return (FramedEntityBuilder)super.y(y);
    }

    @Override
    public FramedEntityBuilder y(String y) {
        return (FramedEntityBuilder)super.y(y);
    }

    public FramedEntityBuilder tag(CompoundTag tag) {
        this.tag = Either.left(tag);
        return this;
    }

    public FramedEntityBuilder tag(String tag) {
        this.tag = Either.right(tag);
        return this;
    }

    public FramedEntityBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public FramedEntityBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public FramedEntityBuilder scale(float scale) {
        this.scale = Either.left(scale);
        return this;
    }

    public FramedEntityBuilder scale(String reference) {
        this.scale = Either.right(reference);
        return this;
    }

    public FramedEntityBuilder offsetY(float offsetY) {
        this.offsetY = Either.left(offsetY);
        return this;
    }

    public FramedEntityBuilder offsetY(String reference) {
        this.offsetY = Either.right(reference);
        return this;
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        json.add("entity", resolve(entity, e -> ResourceLocation.CODEC.encodeStart(ops, BuiltInRegistries.ENTITY_TYPE.getKey(e)).getOrThrow()));

        if(tag != null)
            json.add("tag", resolve(tag, t -> CompoundTag.CODEC.encodeStart(ops, t).getOrThrow()));
        if(offsetY != null)
            json.add("offset_y", resolveNum(offsetY));
        if(scale != null)
            json.add("scale", resolveNum(scale));
        if(width != null)
            json.add("width", resolveNum(width));
    }

}
