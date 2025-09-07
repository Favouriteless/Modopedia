package net.favouriteless.modopedia.client.page_components;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.Lookup;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookTexture;
import net.favouriteless.modopedia.api.book.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.book.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.book.page_components.PageComponent;
import net.favouriteless.modopedia.api.book.page_components.PageWidgetHolder;
import net.favouriteless.modopedia.api.multiblock.Multiblock;
import net.favouriteless.modopedia.api.multiblock.MultiblockInstance;
import net.favouriteless.modopedia.api.multiblock.MultiblockVisualiser;
import net.favouriteless.modopedia.api.registries.client.MultiblockRegistry;
import net.favouriteless.modopedia.client.multiblock.render.MultiblockRenderer;
import net.favouriteless.modopedia.client.multiblock.PlacedMultiblock;
import net.favouriteless.modopedia.client.page_widgets.PageImageButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

public class MultiblockPageComponent extends PageComponent {

    public static final ResourceLocation ID = Modopedia.id("multiblock");

    private MultiblockInstance multiblock;
    private int width;
    private int height;

    private float offsetX;
    private float offsetY;

    private float scale;
    private boolean noOffsets;
    private float viewAngle;

    private boolean previewable;

    @Override
    public void init(Book book, Lookup lookup, Level level) {
        super.init(book, lookup, level);

        Multiblock multiblock = null;

        if(lookup.has("multiblock_id")) {
            ResourceLocation id = lookup.get("multiblock_id").as(ResourceLocation.class);
            multiblock = MultiblockRegistry.get().get(id);

            if(multiblock == null)
                throw new IllegalArgumentException(id + " is not a registered multiblock");
        }


        if(multiblock == null && lookup.has("multiblock"))
            multiblock = lookup.get("multiblock").as(Multiblock.class);

        if(multiblock == null)
            throw new NullPointerException("Lookup is missing a \"multiblock_id\" or \"multiblock\" key");

        this.multiblock = new PlacedMultiblock(multiblock, level);
        width = lookup.getOrDefault("width", 100).asInt();
        height = lookup.getOrDefault("height", 100).asInt();

        offsetX = lookup.getOrDefault("offset_x", 0.0F).asFloat();
        offsetY = lookup.getOrDefault("offset_y", 0.0F).asFloat();

        scale = lookup.getOrDefault("scale", 1.0F).asFloat();
        noOffsets = lookup.getOrDefault("no_offsets", false).asBoolean();
        viewAngle = lookup.getOrDefault("view_angle", 30.0F).asFloat();
        previewable = lookup.getOrDefault("previewable", false).asBoolean();
    }

    @Override
    public void initWidgets(PageWidgetHolder holder, BookRenderContext context) {
        if(!previewable)
            return;

        BookTexture bookTex = context.getBookTexture();
        ResourceLocation tex = bookTex.location();
        Rectangle rec = bookTex.widgets().get("preview");

        holder.addRenderableWidget(new PageImageButton(tex, x, y + height - rec.height(), rec.width(), rec.height(),
                rec.u(), rec.v(), bookTex.texWidth(), bookTex.texHeight(), b -> tryPlace())
        );
    }

    @Override
    public void tick(BookRenderContext context) {
        multiblock.tick();
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {
        Minecraft mc = Minecraft.getInstance();
        MultiBufferSource bufferSource = mc.renderBuffers().bufferSource();

        if(multiblock.getLevel() != mc.level)
            multiblock = new PlacedMultiblock(multiblock.getMultiblock(), mc.level, multiblock.getPos());

        PoseStack pose = graphics.pose();

        Multiblock m = multiblock.getMultiblock();
        Vec3i dims = m.getDimensions();

        float scale = -Math.min(width, height) / Mth.sqrt(dims.getX() * dims.getX() + dims.getY() * dims.getY() + dims.getZ() * dims.getZ());

        pose.pushPose();
        pose.translate(offsetX, offsetY, 0);

        pose.translate(x + width / 2.0F, y + height / 2.0F, 100);
        pose.scale(scale, scale, scale);
        pose.translate(-dims.getX() / 2.0F, -dims.getY() / 2.0F, 0);

        pose.translate(dims.getX() / 2.0F, dims.getY() / 2.0F, dims.getZ() / 2.0F);
        pose.scale(this.scale, this.scale, this.scale);
        pose.mulPose(Axis.XN.rotationDegrees(viewAngle));
        pose.mulPose(Axis.YP.rotationDegrees(180 + context.getTicks() + partialTick));
        pose.translate(-dims.getX() / 2.0F, -dims.getY() / 2.0F, -dims.getZ() / 2.0F);

        MultiblockRenderer.render(multiblock, pose, bufferSource, partialTick);

        pose.popPose();
    }

    private void tryPlace() {
        Minecraft mc = Minecraft.getInstance();
        MultiblockVisualiser.get().place(multiblock.getMultiblock(), mc.level, mc.player.blockPosition());
    }


}
