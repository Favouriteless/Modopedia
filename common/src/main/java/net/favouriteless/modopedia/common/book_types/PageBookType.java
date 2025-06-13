package net.favouriteless.modopedia.common.book_types;

import net.favouriteless.modopedia.api.books.BookType;
import net.favouriteless.modopedia.common.init.MBookTypes;

public class PageBookType implements BookType {

    @Override
    public Type<PageBookType> type() {
        return MBookTypes.PAGE;
    }

}
