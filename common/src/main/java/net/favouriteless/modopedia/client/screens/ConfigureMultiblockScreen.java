package net.favouriteless.modopedia.client.screens;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.multiblock.MultiblockInstance;
import net.favouriteless.modopedia.api.multiblock.MultiblockVisualiser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.GridLayout.RowHelper;
import net.minecraft.client.gui.layouts.LayoutSettings;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class ConfigureMultiblockScreen extends Screen {

    private static final int EDIT_WIDTH = 80;
    private static final int ROW_HEIGHT = 16;
    private static final int BUTTON_WIDTH = 16;

    private final MultiblockInstance instance;

    private EditBox xEditBox;
    private EditBox yEditBox;
    private EditBox zEditBox;

    public ConfigureMultiblockScreen(MultiblockInstance instance) {
        super(Component.translatable("screen.modopedia.configure_multiblock"));
        this.instance = instance;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private boolean numericBoxFilter(String value) {
        // This looks really hacky, but it's more efficient than using a regex
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected void init() {
        Minecraft mc = Minecraft.getInstance();

        LinearLayout layout = LinearLayout.vertical();

        layout.defaultCellSetting()
                .alignHorizontallyCenter()
                .padding(0, 10, 0, 0);

        layout.addChild(new StringWidget(Modopedia.translatedComponent("screen", "config_preview"), font), LayoutSettings.defaults().alignHorizontallyCenter());

        GridLayout grid = new GridLayout();
        grid.defaultCellSetting().padding(4, 4, 4, 0);
        layout.addChild(grid);

        RowHelper rowHelper = grid.createRowHelper(2);

        xEditBox = addRenderableWidget(new EditBox(mc.font, 0, 0, EDIT_WIDTH, ROW_HEIGHT, Modopedia.translatedComponent("screen", "config_preview.x")));
        yEditBox = addRenderableWidget(new EditBox(mc.font, 0, 18, EDIT_WIDTH, ROW_HEIGHT, Modopedia.translatedComponent("screen", "config_preview.y")));
        zEditBox = addRenderableWidget(new EditBox(mc.font, 0, 36, EDIT_WIDTH, ROW_HEIGHT, Modopedia.translatedComponent("screen", "config_preview.z")));
        updateEditBoxes();

        xEditBox.setFilter(this::numericBoxFilter);
        yEditBox.setFilter(this::numericBoxFilter);
        zEditBox.setFilter(this::numericBoxFilter);
        xEditBox.setResponder(value -> updateMultiblock());
        yEditBox.setResponder(value -> updateMultiblock());
        zEditBox.setResponder(value -> updateMultiblock());

        rowHelper.addChild(new StringWidget(Component.literal("X"), mc.font));
        rowHelper.addChild(xEditBox);

        rowHelper.addChild(new StringWidget(Component.literal("Y"), mc.font));
        rowHelper.addChild(yEditBox);

        rowHelper.addChild(new StringWidget(Component.literal("Z"), mc.font));
        rowHelper.addChild(zEditBox);


        // TODO: Create custom button class to handle left/right click.

        Component removeText = Modopedia.translatedComponent("screen", "config_preview.remove");
        layout.addChild(
                Button.builder(removeText, b -> removeMultiblock())
                        .width(font.width(removeText) + 12)
                        .build()
        );

        layout.arrangeElements();
        FrameLayout.alignInRectangle(layout, 0, 0, this.width, this.height, 0.5F, 0.5F);
        layout.visitWidgets(this::addRenderableWidget);
    }

    private void move(Axis axis, int mouseButton) {
        switch(mouseButton) {
            case GLFW.GLFW_MOUSE_BUTTON_LEFT -> instance.move(axis, 1);
            case GLFW.GLFW_MOUSE_BUTTON_RIGHT -> instance.move(axis, -1);
        }
        updateEditBoxes();
    }

    /**
     * Move the multiblock instance to match the edit boxes
     */
    private void updateMultiblock() {
        // While this can error, it should be impossible to enter non-numeric values into the text boxes anyway.
        // If this causes a crash, good. Better to know the rest of it is busted instead.
        instance.setPos(new BlockPos(
                Integer.parseInt(xEditBox.getValue()),
                Integer.parseInt(yEditBox.getValue()),
                Integer.parseInt(zEditBox.getValue())
        ));
    }

    /**
     * Update the edit boxes to match the multiblock instance
     */
    private void updateEditBoxes() {
        BlockPos pos = instance.getPos();
        xEditBox.setValue(String.valueOf(pos.getX()));
        yEditBox.setValue(String.valueOf(pos.getY()));
        zEditBox.setValue(String.valueOf(pos.getZ()));
    }

    private void removeMultiblock() {
        MultiblockVisualiser.get().remove(instance);
        Minecraft.getInstance().setScreen(null);
    }

}
