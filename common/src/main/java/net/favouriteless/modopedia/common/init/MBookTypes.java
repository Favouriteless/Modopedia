package net.favouriteless.modopedia.common.init;

import com.mojang.serialization.MapCodec;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.books.BookType.Type;
import net.favouriteless.modopedia.api.registries.BookTypeRegistry;
import net.favouriteless.modopedia.common.book_types.ClassicBookType;
import net.favouriteless.modopedia.common.book_types.PageBookType;
import net.favouriteless.modopedia.common.book_types.PamphletBookType;

public class MBookTypes {

    public static final Type<ClassicBookType> CLASSIC = new Type<>(Modopedia.id("classic"), MapCodec.unit(new ClassicBookType()));
    public static final Type<PamphletBookType> PAMPHLET = new Type<>(Modopedia.id("pamphlet"), MapCodec.unit(new PamphletBookType()));
    public static final Type<PageBookType> PAGE = new Type<>(Modopedia.id("page"), MapCodec.unit(new PageBookType()));

    public static void load() {
        BookTypeRegistry registry = BookTypeRegistry.get();

        registry.register(CLASSIC);
        registry.register(PAMPHLET);
        registry.register(PAGE);
    }

}
