package isel.mpd.algorithms;

import java.util.Iterator;

/**
 * Created by lfalcao on 06/05/2015.
 */
public class MPDLimitStream<T> extends MPDLazyStream<T> {
    private int limit;

    public <T> MPDLimitStream(MPDLazyStream<T> seq, int limit) {
        super(seq);
        this.limit = limit;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Iterator<T> prevIter = (Iterator<T>) seq.iterator();
            int cnt = 0;


            @Override
            public boolean hasNext() {
                return cnt < limit && prevIter.hasNext();
            }

            @Override
            public T next() {
                ++cnt;
                return prevIter.next();
            }
        };
    }
}
