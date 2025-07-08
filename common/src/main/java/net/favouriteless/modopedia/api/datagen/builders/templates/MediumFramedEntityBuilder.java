package net.favouriteless.modopedia.api.datagen.builders.templates;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.datagen.builders.page_components.TemplateComponentBuilder;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.*;
import net.minecraft.world.entity.EntityType;

public class MediumFramedEntityBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("medium_framed_entity");

    private final Either<EntityType<?>, String> entity;
    private Either<CompoundTag, String> tag;
    private Either<Float, String> offsetY;
    private Either<Float, String> scale;
    private Either<Integer, String> width;

    private MediumFramedEntityBuilder(EntityType<?> type) {
        super(ID);
        this.entity = Either.left(type);
    }

    private MediumFramedEntityBuilder(String type) {
        super(ID);
        this.entity = Either.right(type);
    }

    public static MediumFramedEntityBuilder of(EntityType<?> type) {
        return new MediumFramedEntityBuilder(type);
    }

    public static MediumFramedEntityBuilder of(String type) {
        return new MediumFramedEntityBuilder(type);
    }

    @Override
    public MediumFramedEntityBuilder x(int x) {
        return (MediumFramedEntityBuilder)super.x(x);
    }

    @Override
    public MediumFramedEntityBuilder x(String x) {
        return (MediumFramedEntityBuilder)super.x(x);
    }

    @Override
    public MediumFramedEntityBuilder y(int y) {
        return (MediumFramedEntityBuilder)super.y(y);
    }

    @Override
    public MediumFramedEntityBuilder y(String y) {
        return (MediumFramedEntityBuilder)super.y(y);
    }

    public MediumFramedEntityBuilder tag(CompoundTag tag) {
        this.tag = Either.left(tag);
        return this;
    }

    public MediumFramedEntityBuilder tag(String tag) {
        this.tag = Either.right(tag);
        return this;
    }

    public MediumFramedEntityBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public MediumFramedEntityBuilder width(String width) {
        this.width = Either.right(width);
        return this;
    }

    public MediumFramedEntityBuilder scale(float scale) {
        this.scale = Either.left(scale);
        return this;
    }

    public MediumFramedEntityBuilder scale(String reference) {
        this.scale = Either.right(reference);
        return this;
    }

    public MediumFramedEntityBuilder offsetY(float offsetY) {
        this.offsetY = Either.left(offsetY);
        return this;
    }

    public MediumFramedEntityBuilder offsetY(String reference) {
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
