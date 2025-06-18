package net.favouriteless.modopedia.api.datagen.builders.page_components;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.TextPageComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class EntityPageComponentBuilder extends PageComponentBuilder {

    private final Either<EntityType<?>, String> entity;
    private Either<Float, String> offsetY;
    private Either<Float, String> scale;
    private Either<Integer, String> width;
    private Either<Integer, String> height;

    private EntityPageComponentBuilder(EntityType<?> type) {
        super(TextPageComponent.ID);
        this.entity = Either.left(type);
    }

    private EntityPageComponentBuilder(String reference) {
        super(TextPageComponent.ID);
        this.entity = Either.right(reference);
    }

    public static EntityPageComponentBuilder of(EntityType<?> type) {
        return new EntityPageComponentBuilder(type);
    }

    public static EntityPageComponentBuilder of(String type) {
        return new EntityPageComponentBuilder(type);
    }

    @Override
    public EntityPageComponentBuilder x(int x) {
        return (EntityPageComponentBuilder)super.x(x);
    }

    @Override
    public EntityPageComponentBuilder x(String reference) {
        return (EntityPageComponentBuilder)super.x(reference);
    }

    @Override
    public EntityPageComponentBuilder y(int y) {
        return (EntityPageComponentBuilder)super.y(y);
    }

    @Override
    public EntityPageComponentBuilder y(String reference) {
        return (EntityPageComponentBuilder)super.y(reference);
    }

    public EntityPageComponentBuilder scale(float scale) {
        this.scale = Either.left(scale);
        return this;
    }

    public EntityPageComponentBuilder scale(String reference) {
        this.scale = Either.right(reference);
        return this;
    }

    public EntityPageComponentBuilder offsetY(float offsetY) {
        this.offsetY = Either.left(offsetY);
        return this;
    }

    public EntityPageComponentBuilder offsetY(String reference) {
        this.offsetY = Either.right(reference);
        return this;
    }

    public EntityPageComponentBuilder width(int width) {
        this.width = Either.left(width);
        return this;
    }

    public EntityPageComponentBuilder width(String reference) {
        this.width = Either.right(reference);
        return this;
    }

    public EntityPageComponentBuilder height(int height) {
        this.height = Either.left(height);
        return this;
    }

    public EntityPageComponentBuilder height(String reference) {
        this.height = Either.right(reference);
        return this;
    }


    @Override
    protected void build(JsonObject json) {
        json.add("entity", resolve(entity).orElse(ResourceLocation.CODEC.encodeStart(JsonOps.INSTANCE, BuiltInRegistries.ENTITY_TYPE.getKey(entity.left().orElseThrow())).getOrThrow()));

        if(offsetY != null)
            resolveNum(offsetY).ifPresent(o -> json.add("offset_y", o));
        if(scale != null)
            resolveNum(scale).ifPresent(s -> json.add("scale", s));
        if(width != null)
            resolveNum(width).ifPresent(w -> json.add("width", w));
        if(height != null)
            resolveNum(height).ifPresent(h -> json.add("height", h));
    }

}
