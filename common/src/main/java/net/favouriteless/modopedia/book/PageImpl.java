package net.favouriteless.modopedia.book;

import net.favouriteless.modopedia.api.books.Page;
import net.favouriteless.modopedia.api.books.PageComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PageImpl implements Page {

    private final List<PageComponent> components = new ArrayList<>();

    public PageImpl() {

    }

    @Override
    public List<PageComponent> getComponents() {
        return components;
    }

    public PageImpl addComponent(PageComponent... components) {
        Collections.addAll(this.components, components);
        return this;
    }

//    public final Codec<Page> PERSISTENT_CODEC =

}
