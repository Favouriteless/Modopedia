package net.favouriteless.modopedia.api.books.page_components;

public interface PageWidgetHolder {

    <T extends PageRenderable> T addRenderable(T renderable, int pageNum);

    <T extends PageEventListener> T addWidget(T widget, int pageNum);

    <T extends PageRenderable & PageEventListener> T addRenderableWidget(T widget, int pageNum);


}
