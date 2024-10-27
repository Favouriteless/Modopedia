package net.favouriteless.modopedia.api.text;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Style;

import java.util.function.UnaryOperator;

/**
 * Represents a stack of styles for rendering text. See {@link PoseStack} for a similar concept.
 */
public interface StyleStack {

    /**
     * Apply a modification to the {@link Style} on top of the stack.
     */
    void modify(UnaryOperator<Style> op);

    /**
     * Push a {@link Style} to the top of the stack.
     *
     * @param style Style to get merged into the top of the stack. Any values set will override existing ones, while
     *              values not set will be taken from the original.
     */
    void push(Style style);

    /**
     * Push a {@link Style} to the top of the stack-- the stack will create a new state using the current top style.
     */
    void push();

    /**
     * Remove the {@link Style} on top of the stack.
     *
     * @return The style removed, or the base style if one was not removed.
     */
    Style pop();

    /**
     * @return The {@link Style} on top of the stack.
     */
    Style peek();

    /**
     * @return The {@link Style} at the base of this stack (i.e. what the stack was created with)
     */
    Style getBaseStyle();

    /**
     * Replace the base {@link Style} for this stack and re-apply all modifications to it.
     */
    void setBaseStyle(Style style);

}
