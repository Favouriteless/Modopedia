package net.favouriteless.modopedia.api.datagen.builders.templates;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.common.datagen.builders.TemplateComponentBuilder;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.*;
import net.minecraft.world.entity.EntityType;

public class LargeFramedEntityBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("large_framed_entity");

    private final Either<EntityType<?>, String> entity;
    private Either<CompoundTag, String> tag;
    private Either<Float, String> offsetY;
    private Either<Float, String> scale;
    private Either<Integer, String> width;

    private LargeFramedEntityBuilder(EntityType<?> type) {
        super(ID);
        this.entity = Either.left(type);
    }

    private LargeFramedEntityBuilder(String type) {
        super(ID);
        this.entity = Either.right(type);
    }

    public static LargeFramedEntityBuilder of(EntityType<?> type) {
        return new LargeFramedEntityBuilder(type);
    }

    public static LargeFramedEntityBuilder of(String type) {
        return new LargeFramedEntityBuilder(type);
    }

    @Override
    public LargeFramedEntityBuilder x(int x) {
        return (LargeFramedEntityBuilder)super.x(x);
    }

    @Override
    public LargeFramedEntityBuilder x(String x) {
        return (LargeFramedEntityBuilder)super.x(x);
    }

    @Override
    public LargeFramedEntityBuilder y(int y) {
        return (LargeFramedEntityBuilder)super.y(y);
    }

    @Override
    public LargeFramedEntityBuilder y(String y) {
        return (LargeFramedEntityBuilder)super.y(y);
    }

    public LargeFramedEntityBuilder tag(CompoundTag tag) {
        this.tag = Either.left(tag);
        return this;
    }

    public LargeFramedEntityBuilder tag(String tag) {
        this.tag = Either.right(tag);
        return this;
    }

    public LargeFramedEntityBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public LargeFramedEntityBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public LargeFramedEntityBuilder scale(float scale) {
        this.scale = Either.left(scale);
        return this;
    }

    public LargeFramedEntityBuilder scale(String reference) {
        this.scale = Either.right(reference);
        return this;
    }

    public LargeFramedEntityBuilder offsetY(float offsetY) {
        this.offsetY = Either.left(offsetY);
        return this;
    }

    public LargeFramedEntityBuilder offsetY(String reference) {
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
