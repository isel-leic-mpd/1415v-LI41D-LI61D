package isel.mpd.algorithms;

import java.util.Iterator;
import java.util.OptionalDouble;
import java.util.function.*;

/**
 * Created by lfalcao on 04/05/2015.
 */
public class MPDLazyStream<T> implements Iterable<T> {
    protected Iterable<?> seq;

    public MPDLazyStream(Iterable<?> seq) {
        this.seq = seq;
    }

    public MPDLazyStream<T> filter(Predicate<T> pred) {
        return new MPDFilterLazyStream<>(this, pred);
    }

    public <R> MPDLazyStream<R> map(Function<T, R> mapper) {
        return new MPDMapLazyStream<>(this, mapper);
    }

    public <T> MPDLazyStream<T> limit(int limit) {
        return new MPDLimitStream(this, limit);
    }

    @Override
    public Iterator<T> iterator() {
        return (Iterator<T>) seq.iterator();
    }
}
