package net.favouriteless.modopedia.book.page_components;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.api.Lookup;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.TemplateProcessor;
import net.favouriteless.modopedia.api.books.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.favouriteless.modopedia.api.registries.TemplateRegistry;
import net.favouriteless.modopedia.book.PageComponentHolder;
import net.favouriteless.modopedia.book.variables.RemoteVariable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.List;

public class TemplatePageComponent extends PageComponent {

    private static final List<String> passthroughExempt = List.of("template", "processor", "x", "y");

    private final PageComponentHolder holder;

    public TemplatePageComponent(PageComponentHolder holder) {
        this.holder = holder;
    }

    @Override
    public void init(Book book, Lookup lookup, Level level) {
        super.init(book, lookup, level);
        for(String key : lookup.keys()) { // The passthroughs for the template are actually on the parent lookup so we create an extra remote link.
            if(!passthroughExempt.contains(key))
                holder.set(key, RemoteVariable.of(key, lookup));
        }
        TemplateProcessor processor = TemplateRegistry.get().getProcessor(lookup.get("template").as(ResourceLocation.class)); // Run processor before the components load
        if(processor != null)
            processor.init(holder, level);

        holder.initComponents(book, lookup.get("entry").asString(), level);
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {
        PoseStack poseStack = graphics.pose();

        poseStack.pushPose();
        poseStack.translate(x, y, 0);
        for(PageComponent component : holder.getComponents()) {
            component.render(graphics, context, mouseX - x, mouseY - y, partialTick);
        }
        poseStack.popPose();
    }

    @Override
    public void tick(BookRenderContext context) {
        for(PageComponent component : holder.getComponents()) {
            component.tick(context);
        }
    }

    @Override
    public boolean mouseClicked(BookRenderContext context, double mouseX, double mouseY, int button) {
        for(PageComponent component : holder.getComponents()) {
            if(component.mouseClicked(context, mouseX - x, mouseY - y, button))
                return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(BookRenderContext context, double mouseX, double mouseY, int button) {
        for(PageComponent component : holder.getComponents()) {
            if(component.mouseReleased(context, mouseX - x, mouseY - y, button))
                return true;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(BookRenderContext context, double mouseX, double mouseY, int button, double dragX, double dragY) {
        for(PageComponent component : holder.getComponents()) {
            if(component.mouseDragged(context, mouseX - x, mouseY - y, button, dragX, dragY))
                return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(BookRenderContext context, double mouseX, double mouseY, double scrollX, double scrollY) {
        for(PageComponent component : holder.getComponents()) {
            if(component.mouseScrolled(context, mouseX - x, mouseY - y, scrollX, scrollY))
                return true;
        }
        return false;
    }

    @Override
    public boolean keyPressed(BookRenderContext context, int keyCode, int scanCode, int modifiers) {
        for(PageComponent component : holder.getComponents()) {
            if(component.keyPressed(context, keyCode, scanCode, modifiers))
                return true;
        }
        return false;
    }

    @Override
    public boolean keyReleased(BookRenderContext context, int keyCode, int scanCode, int modifiers) {
        for(PageComponent component : holder.getComponents()) {
            if(component.keyReleased(context, keyCode, scanCode, modifiers))
                return true;
        }
        return false;
    }

    @Override
    public boolean charTyped(BookRenderContext context, char codePoint, int modifiers) {
        for(PageComponent component : holder.getComponents()) {
            if(component.charTyped(context, codePoint, modifiers))
                return true;
        }
        return false;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        for(PageComponent component : holder.getComponents()) {
            if(component.isMouseOver(mouseX - x, mouseY - y))
                return true;
        }
        return false;
    }

}
