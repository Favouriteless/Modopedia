package net.favouriteless.modopedia.client.screens.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class ItemButton extends Button {

    private final ItemStack stack;

    public ItemButton(int x, int y, int width, int height, Button.OnPress onPress, ItemStack stack, Component message) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
        this.stack = stack;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        PoseStack poseStack = graphics.pose();
        float scale = width / 16f; // We want the item to always "fill" the size of the button.

        poseStack.pushPose();
        poseStack.scale(scale, scale, 1);

        graphics.renderItem(stack, getX(), getY());

        poseStack.popPose();
    }

}
