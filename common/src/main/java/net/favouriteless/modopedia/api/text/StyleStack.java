package net.favouriteless.modopedia.api.text;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Style;

import java.util.function.UnaryOperator;

/**
 * Represents a stack of styles for rendering text. See {@link PoseStack} for a similar concept.
 */
public interface StyleStack {

    /**
     * Apply a modification to the {@link Style} on top of the stack. Use this to ensure the stack can be reconstructed
     * if the base style is replaced.
     */
    void modify(UnaryOperator<Style> op);

    /**
     * Push a {@link Style} to the top of the stack.
     */
    void push(Style style);

    /**
     * Remove the {@link Style} on top of the stack.
     *
     * @return The style removed, or the base style if one was not removed.
     */
    Style pop();

    /**
     * @return The {@link Style} on top of the stack.
     */
    Style last();

    /**
     * @return The {@link Style} at the base of this stack (i.e. what the stack was created with)
     */
    Style getBaseStyle();

    /**
     * Replace the base {@link Style} for this stack and re-apply all modifications to it.
     */
    void setBaseStyle(Style style);

}
