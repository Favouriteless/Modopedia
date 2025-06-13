package net.favouriteless.modopedia.api.book.page_components;

/**
 * Represents an object which holds and manages {@link PageRenderable}s and {@link PageEventListener}s.
 */
public interface PageWidgetHolder {

    <T extends PageRenderable> T addRenderable(T renderable);

    <T extends PageEventListener> T addWidget(T widget);

    <T extends PageRenderable & PageEventListener> T addRenderableWidget(T widget);

}
