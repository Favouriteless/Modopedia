package net.favouriteless.modopedia.util;

public class StringUtils {

    public static boolean isTranslationKey(String string) {
        return string.contains(".") && string.matches("[a-z._-]+");
    }

}
