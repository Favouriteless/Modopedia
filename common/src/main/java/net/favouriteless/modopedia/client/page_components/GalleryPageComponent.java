package net.favouriteless.modopedia.client.page_components;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.api.Lookup;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookTexture;
import net.favouriteless.modopedia.api.book.BookTexture.FixedRectangle;
import net.favouriteless.modopedia.api.book.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.book.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.book.page_components.PageComponent;
import net.favouriteless.modopedia.api.book.page_components.PageWidgetHolder;
import net.favouriteless.modopedia.api.registries.client.BookTextureRegistry;
import net.favouriteless.modopedia.book.PageComponentHolder;
import net.favouriteless.modopedia.client.page_widgets.PageImageButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

public class GalleryPageComponent extends PageComponent {

    private final PageComponentHolder holder;

    private PageImageButton leftButton;
    private PageImageButton rightButton;
    private int selectedComponent = 0;

    private int width;
    private int height;

    public GalleryPageComponent(PageComponentHolder holder) {
        this.holder = holder;
    }

    @Override
    public void init(Book book, Lookup lookup, Level level) {
        super.init(book, lookup, level);
        holder.initComponents(book, lookup.get("entry").asString(), level);

        BookTexture tex = BookTextureRegistry.get().getTexture(book.getTexture());
        Rectangle page = tex.pages().get(pageNum % tex.pages().size());

        width = lookup.getOrDefault("width", page.width()).asInt();
        height = lookup.getOrDefault("height", page.height()).asInt();
    }

    @Override
    public void initWidgets(PageWidgetHolder holder, BookRenderContext context) {
        if(this.holder.getComponents().size() < 2)
            return;

        BookTexture tex = context.getBookTexture();
        ResourceLocation loc = tex.location();

        FixedRectangle left = tex.left();
        FixedRectangle right = tex.right();

        leftButton = holder.addRenderableWidget(
                new PageImageButton(loc, x, y + height - left.height(), left.width(), left.height(),
                        left.u(), left.v(), tex.texWidth(), tex.texHeight(), b -> changeComponent(-1))
        );
        rightButton = holder.addRenderableWidget(
                new PageImageButton(loc, x + width - right.width(), y + height - right.height(), right.width(), right.height(),
                        right.u(), right.v(), tex.texWidth(), tex.texHeight(), b -> changeComponent(1))
        );

        updateWidgetVisibility();
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {
        PoseStack poseStack = graphics.pose();

        poseStack.pushPose();
        poseStack.translate(x, y, 0);

        holder.getComponent(selectedComponent).render(graphics, context, mouseX - x, mouseY - y, partialTick);

        poseStack.popPose();
    }

    protected void changeComponent(int by) {
        selectedComponent = Mth.clamp(selectedComponent + by, 0, holder.getComponents().size() - 1);
        updateWidgetVisibility();
    }

    protected void updateWidgetVisibility() {
        if(selectedComponent <= 0) {
            selectedComponent = 0;
            leftButton.active = false;
        }
        else {
            leftButton.active = true;
        }

        if(selectedComponent >= holder.getComponents().size() - 1) {
            selectedComponent = holder.getComponents().size() - 1;
            rightButton.active = false;
        }
        else {
            rightButton.active = true;
        }
    }

    @Override
    public void tick(BookRenderContext context) {
        holder.getComponent(selectedComponent).tick(context);
    }

    @Override
    public boolean mouseClicked(BookRenderContext context, double mouseX, double mouseY, int button) {
        return holder.getComponent(selectedComponent).mouseClicked(context, mouseX - x, mouseY - y, button);
    }

    @Override
    public boolean mouseReleased(BookRenderContext context, double mouseX, double mouseY, int button) {
        return holder.getComponent(selectedComponent).mouseReleased(context, mouseX - x, mouseY - y, button);
    }

    @Override
    public boolean mouseDragged(BookRenderContext context, double mouseX, double mouseY, int button, double dragX, double dragY) {
        return holder.getComponent(selectedComponent).mouseDragged(context, mouseX - x, mouseY - y, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(BookRenderContext context, double mouseX, double mouseY, double scrollX, double scrollY) {
        return holder.getComponent(selectedComponent).mouseScrolled(context, mouseX - x, mouseY - y, scrollX, scrollY);
    }

    @Override
    public boolean keyPressed(BookRenderContext context, int keyCode, int scanCode, int modifiers) {
        return holder.getComponent(selectedComponent).keyPressed(context, keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(BookRenderContext context, int keyCode, int scanCode, int modifiers) {
        return holder.getComponent(selectedComponent).keyReleased(context, keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(BookRenderContext context, char codePoint, int modifiers) {
        return holder.getComponent(selectedComponent).charTyped(context, codePoint, modifiers);
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return holder.getComponent(selectedComponent).isMouseOver(mouseX - x, mouseY - y);
    }

}
