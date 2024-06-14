package net.favouriteless.modopedia.books;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.books.Entry;
import net.favouriteless.modopedia.books.components.TextPageComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TestBook extends BookImpl {

    public TestBook() {
        super(Modopedia.id("test"), Modopedia.id("classic"),
                Component.literal("Test Book"), Component.literal("Test Subtitle"),
                Modopedia.id("texures/gui/book/default.png"), null);

        Entry entry = new EntryImpl("test", Component.literal("Test Entry"), new ItemStack(Items.END_CRYSTAL))
                .addPage(new PageImpl().addComponent(new TextPageComponent("Testing body!", 0)));

        addEntry(entry);
        addCategory(new CategoryImpl("test", Component.literal("Test Category"), Component.literal("CatSubtitle"),
                "Test description", new ItemStack(Items.ENDER_PEARL), null).addEntry(entry));
    }

}
