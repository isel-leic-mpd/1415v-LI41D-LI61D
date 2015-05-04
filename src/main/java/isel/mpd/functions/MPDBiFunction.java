package isel.mpd.functions;

import java.util.function.Function;

/**
 * Created by lfalcao on 04/05/2015.
 */
public interface MPDBiFunction<T,U,R> {
    default <V> MPDBiFunction<T,U,V> andThen(Function<R, V> after) {
        return (t, u) -> after.apply(this.apply(t, u));
    }

    R apply(T t, U u);
}
