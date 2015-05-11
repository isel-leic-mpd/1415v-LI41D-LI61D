package isel.mpd.algorithms;

import java.util.Iterator;
import java.util.function.Function;

/**
 * Created by lfalcao on 06/05/2015.
 */
public class MPDMapLazyStream<T, R> extends MPDLazyStream<R> {
    private Function<T, R> mapper;

    public MPDMapLazyStream(MPDLazyStream<T> seq, Function<T, R> mapper) {
        super(seq);
        this.mapper = mapper;
    }

    @Override
    public Iterator<R> iterator() {
        return new Iterator<R>() {
            private Iterator<T> prevIter = (Iterator<T>) seq.iterator();

            @Override
            public boolean hasNext() {
                return prevIter.hasNext();
            }

            @Override
            public R next() {
                return mapper.apply(prevIter.next());
            }
        };
    }


}
