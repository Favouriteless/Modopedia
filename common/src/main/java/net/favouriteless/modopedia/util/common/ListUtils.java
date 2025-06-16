package net.favouriteless.modopedia.util.common;

import java.util.List;

public class ListUtils {

    public static <T> int size(List<List<T>> list) {
        int size = 0;
        for(List<T> l : list)
            size += l.size();
        return size;
    }

}
