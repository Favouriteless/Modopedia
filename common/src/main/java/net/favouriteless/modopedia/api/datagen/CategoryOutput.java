package net.favouriteless.modopedia.api.datagen;

import net.favouriteless.modopedia.api.book.Category;

public interface CategoryOutput {

	void accept(String id, Category category);
}