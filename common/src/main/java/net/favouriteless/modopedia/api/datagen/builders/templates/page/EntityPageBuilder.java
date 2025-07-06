package net.favouriteless.modopedia.api.datagen.builders.templates.page;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.datagen.builders.TemplateComponentBuilder;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.*;
import net.minecraft.world.entity.EntityType;

public class EntityPageBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("page/entity");

    private final String text;
    private Either<Integer, String> width;
    private Either<Integer, String> height;
    private Either<Integer, String> textOffset;

    private final Either<EntityType<?>, String> entity;
    private Either<CompoundTag, String> tag;
    private Either<Float, String> offsetY;
    private Either<Float, String> scale;

    private EntityPageBuilder(EntityType<?> type, String text) {
        super(ID);
        this.entity = Either.left(type);
        this.text = text;
    }

    private EntityPageBuilder(String type, String text) {
        super(ID);
        this.entity = Either.right(type);
        this.text = text;
    }

    public static EntityPageBuilder of(EntityType<?> type, String text) {
        return new EntityPageBuilder(type, text);
    }

    public static EntityPageBuilder of(String type, String text) {
        return new EntityPageBuilder(type, text);
    }

    @Override
    public EntityPageBuilder x(int x) {
        return (EntityPageBuilder)super.x(x);
    }

    @Override
    public EntityPageBuilder x(String x) {
        return (EntityPageBuilder)super.x(x);
    }

    @Override
    public EntityPageBuilder y(int y) {
        return (EntityPageBuilder)super.y(y);
    }

    @Override
    public EntityPageBuilder y(String y) {
        return (EntityPageBuilder)super.y(y);
    }

    public EntityPageBuilder tag(CompoundTag tag) {
        this.tag = Either.left(tag);
        return this;
    }

    public EntityPageBuilder tag(String tag) {
        this.tag = Either.right(tag);
        return this;
    }

    public EntityPageBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public EntityPageBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public EntityPageBuilder height(int height) {
        this.height = Either.left(height);
        return this;
    }

    public EntityPageBuilder height(String height) {
        this.height = Either.right(height);
        return this;
    }

    public EntityPageBuilder scale(float scale) {
        this.scale = Either.left(scale);
        return this;
    }

    public EntityPageBuilder scale(String reference) {
        this.scale = Either.right(reference);
        return this;
    }

    public EntityPageBuilder offsetY(float offsetY) {
        this.offsetY = Either.left(offsetY);
        return this;
    }

    public EntityPageBuilder offsetY(String reference) {
        this.offsetY = Either.right(reference);
        return this;
    }

    public EntityPageBuilder textOffset(int textOffset) {
        this.textOffset = Either.left(textOffset);
        return this;
    }

    public EntityPageBuilder textOffset(String textOffset) {
        this.textOffset = Either.right(textOffset);
        return this;
    }


    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        json.add("entity", resolve(entity).orElseGet(() -> ResourceLocation.CODEC.encodeStart(ops, BuiltInRegistries.ENTITY_TYPE.getKey(orThrow(entity))).getOrThrow()));
        json.add("text", new JsonPrimitive(text));

        if(tag != null)
            json.add("tag", resolve(tag).orElseGet(() -> CompoundTag.CODEC.encodeStart(ops, orThrow(tag)).getOrThrow()));
        if(offsetY != null)
            resolveNum(offsetY).ifPresent(o -> json.add("offset_y", o));
        if(scale != null)
            resolveNum(scale).ifPresent(s -> json.add("scale", s));
        if(height != null)
            resolveNum(height).ifPresent(w -> json.add("height", w));
        if(width != null)
            resolveNum(width).ifPresent(w -> json.add("width", w));
        if(textOffset != null)
            resolveNum(textOffset).ifPresent(w -> json.add("text_offset", w));
    }

}
