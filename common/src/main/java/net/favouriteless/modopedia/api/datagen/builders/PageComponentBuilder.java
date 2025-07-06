package net.favouriteless.modopedia.api.datagen.builders;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.api.datagen.BookContentBuilder;

import net.minecraft.resources.*;

import java.util.Optional;

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
    public JsonElement build(RegistryOps<JsonElement> ops) {
        JsonObject json = new JsonObject();

        if(type != null)
            json.add(isTemplate ? "template" : "type", ResourceLocation.CODEC.encodeStart(ops, type).getOrThrow());
        if(x != null)
            resolveNum(x).ifPresent(j -> json.add("x", j));
        if(y != null)
            resolveNum(y).ifPresent(j -> json.add("y", j));
        build(json, ops);

        return json;
    }

    protected <L> Optional<JsonElement> resolve(Either<L, String> either) {
        return either.right().isPresent() ? Optional.of(new JsonPrimitive(either.right().get())) : Optional.empty();
    }

    protected Optional<JsonElement> resolveString(Either<String, String> either) {
        if(either.left().isPresent())
            return Optional.of(new JsonPrimitive(either.left().get()));
        if(either.right().isPresent())
            return Optional.of(new JsonPrimitive(either.right().get()));
        return Optional.empty();
    }

    protected Optional<JsonElement> resolveBool(Either<Boolean, String> either) {
        if(either.left().isPresent())
            return Optional.of(new JsonPrimitive(either.left().get()));
        if(either.right().isPresent())
            return Optional.of(new JsonPrimitive(either.right().get()));
        return Optional.empty();
    }

    protected Optional<JsonElement> resolveNum(Either<? extends Number, String> either) {
        if(either.left().isPresent())
            return Optional.of(new JsonPrimitive(either.left().get()));
        if(either.right().isPresent())
            return Optional.of(new JsonPrimitive(either.right().get()));
        return Optional.empty();
    }

    protected <L> L orThrow(Either<L, String> either) {
        return either.left().orElseThrow();
    }

}
