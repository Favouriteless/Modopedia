package net.favouriteless.modopedia.api.datagen;

import java.util.List;

public interface BookContentOutput extends BookOutput {

	List<String> getEntries(String category);

	void addLink(String entry, String category);

}