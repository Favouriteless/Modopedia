package net.favouriteless.modopedia.common.book_types;

import net.favouriteless.modopedia.api.books.BookType;
import net.favouriteless.modopedia.common.init.MBookTypes;

public class PamphletBookType implements BookType {

    @Override
    public Type<PamphletBookType> type() {
        return MBookTypes.PAMPHLET;
    }

}
