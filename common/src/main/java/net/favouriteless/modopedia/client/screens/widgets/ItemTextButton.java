package net.favouriteless.modopedia.client.screens.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemTextButton extends Button {

    public static final int SIZE = Minecraft.getInstance().font.lineHeight + 2;
    private final ItemStack stack;

    public ItemTextButton(int x, int y, int width, ItemStack stack, Component message, Button.OnPress onPress) {
        super(x, y, width, SIZE, message, onPress, DEFAULT_NARRATION);
        this.stack = stack;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        PoseStack poseStack = graphics.pose();

        float scale = (height * 0.9F) / 16F; // We want the item to always "fill" 95% of the size of the button.
        int x = getX();
        int y = getY();

        if(isHovered()) {
            RenderSystem.enableBlend();
            graphics.fill(x, y, x + width, y + height, 0x33000000);
            RenderSystem.disableBlend();
        }

        poseStack.pushPose();

        poseStack.scale(scale, scale, 1);
        poseStack.translate(x / scale, (y / scale) + height*0.05F, 0);
        graphics.renderItem(stack, 0, 0);

        poseStack.popPose();

        graphics.drawString(Minecraft.getInstance().font, getMessage(), x + height, y+1, 0x000000, false);
    }

    public static List<ItemTextButton> createItemTextButtons(Collection<String> ids, int x, int y, Factory factory) {
        int spacing = SIZE + 1;

        List<ItemTextButton> out = new ArrayList<>();
        for(String id : ids)
            out.add(factory.create(id, x, y + spacing*out.size()));

        return out;
    }

    @FunctionalInterface
    public interface Factory {

        ItemTextButton create(String id, int x, int y);

    }

}
