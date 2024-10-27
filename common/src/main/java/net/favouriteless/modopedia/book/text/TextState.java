package net.favouriteless.modopedia.book.text;

import net.favouriteless.modopedia.api.text.StyleStack;
import net.minecraft.network.chat.Style;

import java.util.*;
import java.util.function.UnaryOperator;

public class TextState implements StyleStack {

    private Style baseStyle;
    private final Deque<PartialTextState> stack = new ArrayDeque<>();

    public TextState(Style baseStyle) {
        this.baseStyle = baseStyle;
        stack.push(new PartialTextState(baseStyle));
    }

    @Override
    public void modify(UnaryOperator<Style> op) {
        if(!stack.isEmpty())
            stack.peek().modify(op);
    }

    @Override
    public void push(Style style) {
        stack.push(new PartialTextState(style.applyTo(peek())));
    }

    @Override
    public void push() {
        stack.push(new PartialTextState(peek()));
    }

    @Override
    public Style pop() {
        return stack.pop().getStyle();
    }

    @Override
    public Style peek() {
        return !stack.isEmpty() ? stack.peek().getStyle() : baseStyle;
    }

    @Override
    public Style getBaseStyle() {
        return baseStyle;
    }

    @Override
    public void setBaseStyle(Style style) {
        baseStyle = style;
        for(PartialTextState partial : stack) {
            partial.replace(style); // Iterate through entire stack replacing bases.
            style = partial.getStyle();
        }
    }

    private static class PartialTextState {

        private Style style; // The current style
        private List<UnaryOperator<Style>> modifications = new ArrayList<>();

        private PartialTextState(Style style) {
            this.style = style;
        }

        public Style getStyle() {
            return style;
        }

        public void modify(UnaryOperator<Style> op) {
            if(modifications == null)
                modifications = new LinkedList<>();
            modifications.add(op);
            style = op.apply(style);
        }

        public void replace(Style style) {
            if(modifications != null) {
                for (UnaryOperator<Style> op : modifications)
                    style = op.apply(style);
            }
            this.style = style;
        }

    }

}
