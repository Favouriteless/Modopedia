package net.favouriteless.modopedia.book;

import com.google.gson.JsonObject;
import net.favouriteless.modopedia.api.books.Page;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.book.components.PageComponentHolder;
import net.minecraft.world.level.Level;

import java.util.Collection;

public class PageImpl implements Page {

    private final PageComponentHolder holder;

    public PageImpl(JsonObject json, int pageNum) {
        holder = new PageComponentHolder(json, pageNum);
    }

    @Override
    public Collection<PageComponent> getComponents() {
        return holder.getComponents();
    }

    @Override
    public void onDataReload(Level level) {
        holder.onDataReload(level);
    }

    // ------------------------------------ Below this point is non-API functions ------------------------------------

}
