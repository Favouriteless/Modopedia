package net.favouriteless.modopedia.client.init;

import net.favouriteless.modopedia.api.registries.client.TemplateRegistry;
import net.favouriteless.modopedia.client.template_processors.*;

public class MTemplateProcessors {
    
    public static void load() {
        TemplateRegistry registry = TemplateRegistry.get();

        registry.registerProcessor(HeaderedTextProcessor.ID, new HeaderedTextProcessor());
        registry.registerProcessor(WidgetSpacingProcessor.ID, new WidgetSpacingProcessor());
        registry.registerProcessor(CraftingRecipeProcessor.ID, new CraftingRecipeProcessor());
        registry.registerProcessor(CookingRecipeProcessor.ID, new CookingRecipeProcessor());
        registry.registerProcessor(VerticalSpacingProcessor.ID, new VerticalSpacingProcessor());
    }
    
}
