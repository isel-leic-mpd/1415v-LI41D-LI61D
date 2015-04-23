package isel.mpd.misc;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

/**
 * Delayed Singleton creation using using Guava
 */
public class Singleton {
    private static final Supplier<Singleton> INSTANCE =
            Suppliers.memoize(Singleton::new);

    public static Singleton getInstance() { return INSTANCE.get(); }

}
