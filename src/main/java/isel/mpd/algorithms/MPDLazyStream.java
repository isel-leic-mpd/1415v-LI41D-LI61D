package isel.mpd.algorithms;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by lfalcao on 04/05/2015.
 */
public class MPDLazyStream<T> implements Iterable<T> {


    public MPDLazyStream(Iterable<T> seq) {

    }

    public MPDLazyStream<T> filter(Predicate<T> pred) {
        // TODO
        return null;
    }

    public <R> MPDLazyStream<R> map(Function<T, R> transformer) {
        // TODO
        return null;
    }


    @Override
    public Iterator<T> iterator() {
        // TODO
        return null;
    }
}
