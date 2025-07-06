package net.favouriteless.modopedia.api.datagen.builders.page_components.components;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.EntityPageComponent;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.*;
import net.minecraft.world.entity.EntityType;

public class EntityBuilder extends PageComponentBuilder {

    private final Either<EntityType<?>, String> entity;
    private Either<CompoundTag, String> tag;
    private Either<Float, String> offsetY;
    private Either<Float, String> scale;
    private Either<Integer, String> width;
    private Either<Integer, String> height;

    private EntityBuilder(EntityType<?> type) {
        super(EntityPageComponent.ID);
        this.entity = Either.left(type);
    }

    private EntityBuilder(String type) {
        super(EntityPageComponent.ID);
        this.entity = Either.right(type);
    }

    public static EntityBuilder of(EntityType<?> type) {
        return new EntityBuilder(type);
    }

    public static EntityBuilder of(String type) {
        return new EntityBuilder(type);
    }

    @Override
    public EntityBuilder x(int x) {
        return (EntityBuilder)super.x(x);
    }

    @Override
    public EntityBuilder x(String x) {
        return (EntityBuilder)super.x(x);
    }

    @Override
    public EntityBuilder y(int y) {
        return (EntityBuilder)super.y(y);
    }

    @Override
    public EntityBuilder y(String y) {
        return (EntityBuilder)super.y(y);
    }

    public EntityBuilder tag(CompoundTag tag) {
        this.tag = Either.left(tag);
        return this;
    }

    public EntityBuilder tag(String tag) {
        this.tag = Either.right(tag);
        return this;
    }

    public EntityBuilder scale(float scale) {
        this.scale = Either.left(scale);
        return this;
    }

    public EntityBuilder scale(String reference) {
        this.scale = Either.right(reference);
        return this;
    }

    public EntityBuilder offsetY(float offsetY) {
        this.offsetY = Either.left(offsetY);
        return this;
    }

    public EntityBuilder offsetY(String reference) {
        this.offsetY = Either.right(reference);
        return this;
    }

    public EntityBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public EntityBuilder width(String reference) {
        this.width = Either.right(reference);
        return this;
    }

    public EntityBuilder height(int height) {
        this.height = Either.left(height);
        return this;
    }

    public EntityBuilder height(String reference) {
        this.height = Either.right(reference);
        return this;
    }


    @Override
    protected void build(JsonObject json, final RegistryOps<JsonElement> ops) {
        json.add("entity", resolve(entity, e -> ResourceLocation.CODEC.encodeStart(ops, BuiltInRegistries.ENTITY_TYPE.getKey(e)).getOrThrow()));

        if(tag != null)
            json.add("tag", resolve(tag, t -> CompoundTag.CODEC.encodeStart(ops, t).getOrThrow()));
        if(offsetY != null)
            json.add("offset_y", resolveNum(offsetY));
        if(scale != null)
            json.add("scale", resolveNum(scale));
        if(width != null)
            json.add("width", resolveNum(width));
        if(height != null)
            json.add("height", resolveNum(height));
    }

}
