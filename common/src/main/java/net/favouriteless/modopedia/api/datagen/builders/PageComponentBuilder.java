package net.favouriteless.modopedia.api.datagen.builders;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.datagen.BookContentBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public abstract class PageComponentBuilder extends BookContentBuilder {

    private final ResourceLocation type;
    private final boolean isTemplate;

    private Either<Integer, String> x;
    private Either<Integer, String> y;

    protected PageComponentBuilder(ResourceLocation type, boolean isTemplate) {
        this.type = type;
        this.isTemplate = isTemplate;
    }

    protected abstract void build(JsonObject json);

    public PageComponentBuilder x(int x) {
        this.x = Either.left(x);
        return this;
    }

    public PageComponentBuilder x(String reference) {
        this.x = Either.right(reference);
        return this;
    }

    public PageComponentBuilder y(int y) {
        this.y = Either.left(y);
        return this;
    }

    public PageComponentBuilder y(String reference) {
        this.y = Either.right(reference);
        return this;
    }

    @Override
    protected final JsonElement build() {
        JsonObject json = new JsonObject();

        json.add(isTemplate ? "template" : "type", ResourceLocation.CODEC.encodeStart(JsonOps.INSTANCE, type).getOrThrow());
        resolveNum(x).ifPresent(j -> json.add("x", j));
        resolveNum(y).ifPresent(j -> json.add("y", j));
        build(json);

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
