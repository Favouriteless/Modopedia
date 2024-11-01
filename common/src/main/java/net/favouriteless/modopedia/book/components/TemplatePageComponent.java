package net.favouriteless.modopedia.book.components;

import net.favouriteless.modopedia.api.Variable.Lookup;
import net.favouriteless.modopedia.api.books.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.books.TemplateProcessor;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.book.PageComponentHolder;
import net.favouriteless.modopedia.book.TemplateRegistry;
import net.favouriteless.modopedia.book.variables.RemoteVariable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.List;

public class TemplatePageComponent extends PageComponent {

    private static final List<String> passthroughExempt = List.of("template", "processor", "x", "y");

    private final PageComponentHolder holder;
    private TemplateProcessor processor;

    public TemplatePageComponent(PageComponentHolder holder) {
        this.holder = holder;
    }

    @Override
    public void init(Lookup lookup, Level level) {
        super.init(lookup, level);
        for(String key : lookup.keys()) { // The passthroughs for the template are actually on the parent object so we create an extra remote link.
            if(!passthroughExempt.contains(key))
                holder.set(key, RemoteVariable.of(key, lookup));
        }
        processor = TemplateRegistry.getProcessor(lookup.get("template").as(ResourceLocation.class)); // Run processor before the components load
        if(processor != null)
            processor.init(holder, level);

        holder.initComponents(level);
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTicks) {
        for(PageComponent component : holder.getComponents()) {
            component.render(graphics, context, mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public boolean pageClicked(BookRenderContext context, double mouseX, double mouseY, int button) {
        for(PageComponent component : holder.getComponents()) {
            if(component.pageClicked(context, mouseX, mouseY, button))
                return true;
        }
        return false;
    }

}
