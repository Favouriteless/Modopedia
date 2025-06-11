package net.favouriteless.modopedia.book.page_components;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.favouriteless.modopedia.api.Lookup;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.books.page_components.PageComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class EntityPageComponent extends PageComponent {

    protected Entity entity;
    protected float offsetY;
    protected float scale;
    protected int width;
    protected int height;

    @Override
    public void init(Book book, Lookup lookup, Level level) {
        super.init(book, lookup, level);

        ResourceLocation id = lookup.get("entity").as(ResourceLocation.class);

        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(id);
        if(type == null)
            throw new IllegalArgumentException(id + " is not a valid entity type");

        entity = type.create(level);

        if(lookup.has("tag"))
            entity.load(lookup.get("tag").as(CompoundTag.class));

        offsetY = lookup.getOrDefault("offset_y", 0.0F).asFloat();
        scale = lookup.getOrDefault("scale", 1.0F).asFloat();
        width = lookup.getOrDefault("width", 100).asInt();
        height = lookup.getOrDefault("height", 100).asInt();
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {
        Minecraft mc = Minecraft.getInstance();
        EntityRenderDispatcher dispatcher = mc.getEntityRenderDispatcher();

        BufferSource bufferSource = mc.renderBuffers().bufferSource();
        PoseStack pose = graphics.pose();

        float xSizeBb = entity.getBbWidth();
        float ySizeBb = entity.getBbHeight();

        float xSize = Math.max(1.0F, Mth.sqrt(xSizeBb * xSizeBb + ySizeBb * ySizeBb));
        float ySize = Math.max(1.0F, ySizeBb);

        float renderScale = (xSize > ySize ? width / xSize : height / ySize) * 0.8F * scale;
        float pixelHeight = ySizeBb * renderScale;
        float yOff = -(height - pixelHeight) / 2;

        pose.pushPose();
        pose.translate(x, y, 50);
        pose.translate(width / 2.0F, yOff + height - offsetY, 0);
        pose.scale(-renderScale, -renderScale, -renderScale);

        pose.mulPose(Axis.XN.rotationDegrees(15));
        pose.mulPose(Axis.YP.rotationDegrees(180 + context.getTicks() + partialTick));

        dispatcher.setRenderHitBoxes(false);
        dispatcher.render(entity, 0, 0, 0, 0, partialTick, pose, bufferSource, 0xF000F0);
        dispatcher.setRenderHitBoxes(true);

        pose.popPose();
    }
}
