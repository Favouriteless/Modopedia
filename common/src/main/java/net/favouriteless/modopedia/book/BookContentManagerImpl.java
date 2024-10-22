package net.favouriteless.modopedia.book;

import net.favouriteless.modopedia.api.books.BookContentManager;

public class BookContentManagerImpl implements BookContentManager {

    public static final BookContentManager INSTANCE = new BookContentManagerImpl();

    private BookContentManagerImpl() {}

}
