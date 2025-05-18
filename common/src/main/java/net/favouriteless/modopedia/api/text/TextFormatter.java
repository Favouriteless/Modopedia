package net.favouriteless.modopedia.api.text;

/**
 * A formatting tag for text, e.g. $(b) for bold.
 */
public interface TextFormatter {

    /**
     * @param tag The formatting tag string, given in the form $(tag), not including the parentheses $().
     *
     * @return true if the given tag string matches this tag, otherwise false.
     */
    boolean matches(String tag);

    /**
     * Perform changes on the stack where this tag is relevant.
     */
    void apply(StyleStack styleStack, String tag);

}
