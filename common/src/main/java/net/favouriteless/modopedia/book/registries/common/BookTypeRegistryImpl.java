package net.favouriteless.modopedia.book.registries.common;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.books.BookType;
import net.favouriteless.modopedia.api.books.BookType.Type;
import net.favouriteless.modopedia.api.registries.BookTypeRegistry;
import net.minecraft.resources.ResourceLocation;

public class BookTypeRegistryImpl implements BookTypeRegistry {

    public static final BookTypeRegistryImpl INSTANCE = new BookTypeRegistryImpl();

    private final BiMap<ResourceLocation, Type<?>> types = HashBiMap.create();

    private final Codec<Type<?>> typeCodec = ResourceLocation.CODEC.flatXmap(
            r -> {
                Type<?> c = getType(r);
                return c != null ? DataResult.success(c) : DataResult.error(() -> "Unknown type " + r);
            },
            c -> {
                ResourceLocation location = types.inverse().get(c);
                return c != null ? DataResult.success(location) : DataResult.error(() -> "Unknown type " + location);
            }
    );
    private final Codec<BookType> codec = typeCodec.dispatch("id", BookType::type, Type::codec);


    private BookTypeRegistryImpl() {}

    @Override
    public void register(Type<?> type) {
        if(types.containsKey(type.id()))
            Modopedia.LOG.error("Attempted to register duplicate book type: {}", type.id());
        else
            types.put(type.id(), type);
    }

    @Override
    public Type<?> getType(ResourceLocation id) {
        return types.get(id);
    }

    @Override
    public Codec<BookType> codec() {
        return codec;
    }

}
