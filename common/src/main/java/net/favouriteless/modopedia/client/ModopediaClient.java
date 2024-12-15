package net.favouriteless.modopedia.client;

import net.favouriteless.modopedia.api.BookRegistry;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.BookTypeRegistry;
import net.favouriteless.modopedia.client.screens.BookScreen;
import net.favouriteless.modopedia.common.data_components.MDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ModopediaClient {

    public static void init() {

    }

    public static void tryOpenBook(Book book) {
        if(book == null)
            return;

        BookScreen screen = BookTypeRegistry.get().getScreen(book);

        if(screen != null)
            Minecraft.getInstance().setScreen(screen);
    }

    public static void tryOpenBook(ResourceLocation id) {
        tryOpenBook(BookRegistry.get().getBook(id));
    }

    public static void tryOpenBook(ItemStack stack) {
        tryOpenBook(stack.get(MDataComponents.BOOK.get()));
    }

}
