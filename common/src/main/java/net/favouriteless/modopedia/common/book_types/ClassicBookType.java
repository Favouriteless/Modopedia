package net.favouriteless.modopedia.common.book_types;

import net.favouriteless.modopedia.api.book.BookType;
import net.favouriteless.modopedia.common.init.MBookTypes;

public class ClassicBookType implements BookType {

    @Override
    public Type<ClassicBookType> type() {
        return MBookTypes.CLASSIC;
    }

}
