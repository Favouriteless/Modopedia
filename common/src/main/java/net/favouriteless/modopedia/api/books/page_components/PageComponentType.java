package net.favouriteless.modopedia.api.books.page_components;

import java.util.function.Supplier;

public record PageComponentType(Supplier<PageComponent> supplier) {

    public PageComponent create() {
        return supplier.get();
    }

}
