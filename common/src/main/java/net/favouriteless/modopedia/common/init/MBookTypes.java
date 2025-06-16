package net.favouriteless.modopedia.common.init;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.book.BookType.Type;
import net.favouriteless.modopedia.api.registries.common.BookTypeRegistry;
import net.favouriteless.modopedia.common.book_types.ClassicBookType;
import net.favouriteless.modopedia.common.book_types.PageBookType;
import net.favouriteless.modopedia.common.book_types.PamphletBookType;

public class MBookTypes {

    public static final Type<ClassicBookType> CLASSIC = new Type<>(Modopedia.id("classic"), ClassicBookType.CODEC);
    public static final Type<PamphletBookType> PAMPHLET = new Type<>(Modopedia.id("pamphlet"), PamphletBookType.CODEC);
    public static final Type<PageBookType> PAGE = new Type<>(Modopedia.id("page"), PageBookType.CODEC);

    public static void load() {
        BookTypeRegistry registry = BookTypeRegistry.get();
        registry.register(CLASSIC);
        registry.register(PAMPHLET);
        registry.register(PAGE);
    }

}
