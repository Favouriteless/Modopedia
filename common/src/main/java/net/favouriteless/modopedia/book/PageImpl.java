package net.favouriteless.modopedia.book;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.PageComponentRegistry;
import net.favouriteless.modopedia.api.books.Page;
import net.favouriteless.modopedia.api.books.PageComponent;

import java.util.List;

public class PageImpl implements Page {

    private final List<PageComponent> components;

    public PageImpl(List<PageComponent> components) {
        this.components = components;
    }

    @Override
    public List<PageComponent> getComponents() {
        return components;
    }

    // ------------------------------------ Below this point is non-API functions ------------------------------------

    public static final Codec<Page> PERSISTENT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            PageComponentRegistry.get().codec().listOf().fieldOf("components").forGetter(Page::getComponents)
    ).apply(instance, PageImpl::new));

}
