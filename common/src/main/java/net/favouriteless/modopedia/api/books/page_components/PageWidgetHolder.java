package net.favouriteless.modopedia.api.books.page_components;

public interface PageWidgetHolder {

    <T extends PageRenderable> T addRenderable(T renderable);

    <T extends PageEventListener> T addWidget(T widget);

    <T extends PageRenderable & PageEventListener> T addRenderableWidget(T widget);

}
