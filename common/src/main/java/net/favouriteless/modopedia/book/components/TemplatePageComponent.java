package net.favouriteless.modopedia.book.components;

import net.favouriteless.modopedia.api.Variable.Lookup;
import net.favouriteless.modopedia.api.books.BookRenderContext;
import net.favouriteless.modopedia.api.books.TemplateProcessor;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.book.TemplateRegistry;
import net.favouriteless.modopedia.book.variables.RemoteVariable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.List;

public class TemplatePageComponent extends PageComponent {

    private static final List<String> passthroughExempt = List.of("template", "processor", "x", "y");

    private PageComponentHolder holder;
    private TemplateProcessor processor;

    @Override
    public void init(Lookup lookup) {
        super.init(lookup);
        ResourceLocation location = lookup.get("template").as(ResourceLocation.class);

        holder = new PageComponentHolder(TemplateRegistry.getTemplate(location).getData(), pageNum);
        for(String key : lookup.keys()) { // The passthroughs for the template are actually on the parent object so we create an extra remote link.
            if(!passthroughExempt.contains(key))
                holder.set(key, RemoteVariable.of(key, lookup));
        }

        processor = TemplateRegistry.getProcessor(location);
        if(processor != null)
            processor.init(holder);

        holder.initComponents();
    }

    @Override
    public void refreshData(Level level, Lookup lookup) {
        if(processor != null)
            processor.refreshData(level, holder);
        holder.onDataReload(level);
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int xMouse, int yMouse, float partialTicks) {
        for(PageComponent component : holder.getComponents()) {
            component.render(graphics, context, xMouse, yMouse, partialTicks);
        }
    }

}
