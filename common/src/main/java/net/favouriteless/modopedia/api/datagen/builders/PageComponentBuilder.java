package net.favouriteless.modopedia.api.datagen.builders;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.api.datagen.BookContentBuilder;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public abstract class PageComponentBuilder implements BookContentBuilder {

    private final ResourceLocation type;
    private final boolean isTemplate;

    private Either<Integer, String> x;
    private Either<Integer, String> y;

    protected PageComponentBuilder(ResourceLocation type, boolean isTemplate) {
        this.type = type;
        this.isTemplate = isTemplate;
    }

    protected PageComponentBuilder(ResourceLocation type) {
        this(type, false);
    }

    protected abstract void build(JsonObject json, RegistryOps<JsonElement> ops);

    public PageComponentBuilder x(int x) {
        this.x = Either.left(x);
        return this;
    }

    public PageComponentBuilder x(String x) {
        this.x = Either.right(x);
        return this;
    }

    public PageComponentBuilder y(int y) {
        this.y = Either.left(y);
        return this;
    }

    public PageComponentBuilder y(String y) {
        this.y = Either.right(y);
        return this;
    }

    @Override
    public final JsonElement build(RegistryOps<JsonElement> ops) {
        JsonObject json = new JsonObject();

        if(type != null)
            json.add(isTemplate ? "template" : "type", ResourceLocation.CODEC.encodeStart(ops, type).getOrThrow());
        if(x != null)
            json.add("x", resolveNum(x));
        if(y != null)
            json.add("y", resolveNum(y));
        build(json, ops);

        return json;
    }

    protected <L> JsonElement resolve(Either<L, String> either, Function<L, JsonElement> serializer) {
        return either.map(serializer, JsonPrimitive::new);
    }

    protected JsonElement resolveBool(Either<Boolean, String> either) {
        return either.map(JsonPrimitive::new, JsonPrimitive::new);
    }

    protected JsonElement resolveNum(Either<? extends Number, String> either) {
        return either.map(JsonPrimitive::new, JsonPrimitive::new);
    }

    protected JsonElement resolveString(Either<String, String> either) {
        return either.map(JsonPrimitive::new, JsonPrimitive::new);
    }

    protected JsonElement resolveString(String string) {
        return new JsonPrimitive(string);
    }

    protected JsonElement resolveResourceLocation(Either<ResourceLocation, String> either) {
        return either.map(l -> ResourceLocation.CODEC.encodeStart(JsonOps.INSTANCE, l).getOrThrow(), JsonPrimitive::new);
    }

}
