package net.favouriteless.modopedia.client.page_components;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.favouriteless.modopedia.api.Lookup;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.page_components.BookRenderContext;
import net.favouriteless.modopedia.api.book.page_components.PageComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ShowcasePageComponent extends PageComponent {

    protected ItemStack[] items;

    protected int width;
    protected int height;
    protected float scale;

    @Override
    public void init(Book book, Lookup lookup, Level level) {
        super.init(book, lookup, level);

        items = lookup.get("items").as(ItemStack[].class);
        if(items.length == 0)
            throw new IllegalArgumentException("Showcase cannot have zero items in it.");
        width = lookup.getOrDefault("width", 100).asInt();
        height = lookup.getOrDefault("height", 100).asInt();
        scale = lookup.getOrDefault("scale", 1.0F).asFloat();
    }

    @Override
    public void render(GuiGraphics graphics, BookRenderContext context, int mouseX, int mouseY, float partialTick) {
        Minecraft mc = Minecraft.getInstance();

        PoseStack pose = graphics.pose();
        ItemRenderer renderer = mc.getItemRenderer();
        MultiBufferSource source = mc.renderBuffers().bufferSource();

        ItemStack item = items[0];

        float scale = -Math.max(width, height) * 0.9F * this.scale;

        pose.pushPose();

        pose.translate(x + width / 2.0F, y + height / 2.0F, 150);
        pose.scale(scale, scale, scale);

        pose.mulPose(Axis.XN.rotationDegrees(15));
        pose.mulPose(Axis.YP.rotationDegrees(context.getTicks() + partialTick));

        Lighting.setupForFlatItems();
        renderer.renderStatic(item, ItemDisplayContext.FIXED, 0xF000F0, OverlayTexture.NO_OVERLAY, pose, source, mc.level, 0);

        pose.popPose();
    }
}
