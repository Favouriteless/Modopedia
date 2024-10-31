package net.favouriteless.modopedia.api.text;

/**
 * Represents a formatting tag for text, e.g. $(b) for bold.
 */
public interface TextFormatter {

    /**
     * @return true if the given tag string matches this tag, otherwise false.
     */
    boolean matches(String tag);

    /**
     * Perform changes on the stack where this tag is relevant.
     */
    void apply(StyleStack styleStack, String tag);

}
