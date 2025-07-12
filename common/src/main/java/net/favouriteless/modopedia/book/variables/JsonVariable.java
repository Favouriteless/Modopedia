package net.favouriteless.modopedia.book.variables;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.book.page_components.ItemDisplay;
import net.favouriteless.modopedia.api.multiblock.Multiblock;
import net.favouriteless.modopedia.book.text.Justify;
import net.favouriteless.modopedia.client.multiblock.BlockStateCodec;
import net.favouriteless.modopedia.util.common.MExtraCodecs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class JsonVariable implements Variable {

    private static final Map<TypeToken<?>, Codec<?>> tokenCodecs = new HashMap<>();

    private final JsonElement internal;
    private final RegistryOps<JsonElement> ops;
    private Object cached;

    static {
        registerCodec(ResourceLocation.class, ResourceLocation.CODEC);
        registerCodec(CompoundTag.class, CompoundTag.CODEC);
        registerCodec(ItemStack.class, ItemStack.CODEC);
        registerCodec(ResourceLocation[].class, ResourceLocation.CODEC.listOf().xmap(l -> l.toArray(new ResourceLocation[0]), Arrays::asList));
        registerCodec(CompoundTag[].class, CompoundTag.CODEC.listOf().xmap(l -> l.toArray(new CompoundTag[0]), Arrays::asList));
        registerCodec(ItemStack[].class, MExtraCodecs.ITEM_LIST.xmap(l -> l.toArray(new ItemStack[0]), Arrays::asList));
        registerCodec(new TypeToken<>() {}, ResourceLocation.CODEC.listOf());
        registerCodec(new TypeToken<>() {}, CompoundTag.CODEC.listOf());
        registerCodec(new TypeToken<>() {}, ItemStack.CODEC.listOf());
        registerCodec(new TypeToken<>() {}, MExtraCodecs.ITEM_LIST);
        registerCodec(Multiblock.class, Multiblock.codec());
        registerCodec(Justify.class, Justify.CODEC);
        registerCodec(CompoundTag.class, CompoundTag.CODEC);
        registerCodec(BlockState.class, BlockStateCodec.CODEC);
        registerCodec(ItemDisplay.class, ItemDisplay.codec());
    }

    private JsonVariable(JsonElement internal, RegistryOps<JsonElement> ops) {
        this.internal = internal;
        this.ops = ops;
    }

    public <T> T as(Class<T> clazz) {
        return as(TypeToken.of(clazz));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T as(TypeToken<T> token) {
        Codec<T> codec = getCodec(token);
        if(codec == null)
            throw new JsonParseException(String.format("Could not decode JsonVariable: %s does not have a codec.", token.getType().getTypeName()));

        if(cached == null || token.getType() != cached.getClass()) {
            cached = codec.decode(ops, internal)
                    .resultOrPartial(error -> { throw new JsonParseException("Could not decode JsonVariable: " + error); })
                    .map(Pair::getFirst)
                    .orElse(null);
        }

        return (T)cached;
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
        return internal.getAsFloat();
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

        return StreamSupport.stream(internal.getAsJsonArray().spliterator(), false).map(element -> of(element, this.ops));
    }

    public static <T> void registerCodec(Class<T> clazz, Codec<T> codec) {
        registerCodec(TypeToken.of(clazz), codec);
    }

    public static <T> void registerCodec(TypeToken<T> token, Codec<T> codec) {
        tokenCodecs.put(token, codec);
    }

    // ------------------------------------ Below this point is non-API functions ------------------------------------

    public static Variable of(JsonElement internal, RegistryOps<JsonElement> ops) {
        return new JsonVariable(internal, ops);
    }

    @SuppressWarnings("unchecked")
    private static <T> Codec<T> getCodec(TypeToken<T> token) {
        return (Codec<T>)tokenCodecs.get(token);
    }

}
