package net.favouriteless.modopedia.util;

import java.util.function.Supplier;

public class Lazy<T> implements Supplier<T> {

    /**
     * Creates a lazy-initialized object.
     *
     * @param supplier The supplier for the value, to be called the first time the value is needed.
     */
    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }

    private final Supplier<T> supplier;
    private T cached;

    private Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T get() {
        if(cached == null)
            cached = supplier.get();
        return cached;
    }

}