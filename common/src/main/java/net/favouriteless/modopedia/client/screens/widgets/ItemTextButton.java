package net.favouriteless.modopedia.client.screens.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.favouriteless.modopedia.api.books.Book;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class ItemTextButton extends Button {

    private final ItemStack stack;

    public ItemTextButton(int x, int y, int width, int height, Button.OnPress onPress, ItemStack stack, Book book,
                          Component message) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
        this.stack = stack;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        PoseStack poseStack = graphics.pose();

        float scale = height / 16F; // We want the item to always "fill" the size of the button.
        int x = getX();
        int y = getY();

        poseStack.pushPose();
        poseStack.scale(scale, scale, 1);
        graphics.renderItem(stack, x, y);
        poseStack.popPose();

        graphics.drawString(Minecraft.getInstance().font, getMessage(), x + height, y, 0x000000);

    }

}
