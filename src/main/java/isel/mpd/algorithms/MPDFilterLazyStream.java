package isel.mpd.algorithms;

import java.util.Iterator;
import java.util.function.Predicate;

/**
 * Created by lfalcao on 06/05/2015.
 */
public class MPDFilterLazyStream<T> extends MPDLazyStream<T> {
    Predicate<T> predicate;

    public MPDFilterLazyStream(MPDLazyStream<T> seq, Predicate<T> pred) {
        super(seq);
        this.predicate = pred;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Iterator<T> prevIter = (Iterator<T>) seq.iterator();
            T next;


            @Override
            public boolean hasNext() {
                if(next != null) {
                    return true;
                }

                while (prevIter.hasNext()) {
                    T prevIterNext = prevIter.next();
                    if(predicate.test(prevIterNext)) {
                        next = prevIterNext;
                        return true;
                    }
                }
                return false;
            }

            @Override
            public T next() {
                T ret = next;
                next = null;
                return ret;
            }
        };
    }
}
