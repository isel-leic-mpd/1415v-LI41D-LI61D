package isel.mpd.misc;

import java.util.function.Supplier;

/**
 * Created by lfalcao on 27/04/2015.
 */
public class LazyInitializer<T> implements Supplier<T>  {
    Supplier<T> supplier;

    public LazyInitializer(Supplier<T> realSupplier) {
        supplier = () -> {
            final T val = realSupplier.get();
            supplier = () -> val;
            return val;
        };
    }

    //@Override
    public T get() {
        return supplier.get();
    }

    public static <T> Supplier<T> lazily(Supplier<T> realSupplier) {
        return new LazyInitializer(realSupplier);
    }
}
