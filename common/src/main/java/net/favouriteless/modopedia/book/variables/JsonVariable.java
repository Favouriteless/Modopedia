package net.favouriteless.modopedia.book.variables;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.api.Variable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class JsonVariable implements Variable {

    private static final Map<TypeToken<?>, Codec<?>> tokenCodecs = new HashMap<>();
    private final JsonElement internal;

    static {
        registerCodec(ResourceLocation.class, ResourceLocation.CODEC);
        registerCodec(CompoundTag.class, CompoundTag.CODEC);
        registerCodec(ItemStack.class, ItemStack.CODEC);
        registerCodec(ResourceLocation[].class, ResourceLocation.CODEC.listOf().xmap(l -> l.toArray(new ResourceLocation[0]), Arrays::asList));
        registerCodec(CompoundTag[].class, CompoundTag.CODEC.listOf().xmap(l -> l.toArray(new CompoundTag[0]), Arrays::asList));
        registerCodec(ItemStack[].class, ItemStack.CODEC.listOf().xmap(l -> l.toArray(new ItemStack[0]), Arrays::asList));
        registerCodec(new TypeToken<>() {}, ResourceLocation.CODEC.listOf());
        registerCodec(new TypeToken<>() {}, CompoundTag.CODEC.listOf());
        registerCodec(new TypeToken<>() {}, ItemStack.CODEC.listOf());
    }

    private JsonVariable(JsonElement internal) {
        this.internal = internal;
    }

    public <T> T as(Class<T> clazz) {
        return as(TypeToken.of(clazz));
    }

    @Override
    public <T> T as(TypeToken<T> token) {
        Codec<T> codec = getCodec(token);

        if(codec == null)
            throw new JsonParseException(String.format("Could not decode JsonVariable: %s does not have a codec.", token.getType().getTypeName()));

        return codec.decode(JsonOps.INSTANCE, internal)
                .resultOrPartial(error -> { throw new JsonParseException("Could not decode JsonVariable: " + error); })
                .map(Pair::getFirst)
                .orElse(null);
    }

    @Override
    public int asInt() {
        return internal.getAsInt();
    }

    @Override
    public long asLong() {
        return internal.getAsLong();
    }

    @Override
    public float asFloat() {
        return internal.getAsInt();
    }

    @Override
    public double asDouble() {
        return internal.getAsDouble();
    }

    @Override
    public boolean asBoolean() {
        return internal.getAsBoolean();
    }

    @Override
    public String asString() {
        return internal.getAsString();
    }

    @Override
    public Stream<Variable> asStream() {
        if(!internal.isJsonArray())
            throw new JsonParseException("Could not decode JsonVariable: Cannot convert non-array variable to a stream");

        return StreamSupport.stream(unwrap().getAsJsonArray().spliterator(), false)
                .map(Variable::of);
    }

    public static <T> void registerCodec(Class<T> clazz, Codec<T> codec) {
        registerCodec(TypeToken.of(clazz), codec);
    }

    public static <T> void registerCodec(TypeToken<T> token, Codec<T> codec) {
        tokenCodecs.put(token, codec);
    }

    // ------------------------------------ Below this point is non-API functions ------------------------------------

    public JsonElement unwrap() {
        return internal;
    }

    public static Variable wrap(JsonElement internal) {
        return new JsonVariable(internal);
    }

    @SuppressWarnings("unchecked")
    private static <T> Codec<T> getCodec(Class<T> clazz) {
        return (Codec<T>)tokenCodecs.get(TypeToken.of(clazz));
    }

    @SuppressWarnings("unchecked")
    private static <T> Codec<T> getCodec(TypeToken<T> token) {
        return (Codec<T>)tokenCodecs.get(token);
    }

}
