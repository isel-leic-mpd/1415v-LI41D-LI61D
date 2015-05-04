package isel.mpd.algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by lfalcao on 04/03/2015.
 */
public class MPDStream<T> implements Iterable<T> {
    private Iterable<T> seq;

    public MPDStream(Iterable<T> seq) {
        this.seq = seq;
    }

    public MPDStream<T> filter(Predicate<T> pred) {
        ArrayList<T> filteredSeq = new ArrayList<>();
        for (T t : this) {
            if (pred.test(t)) {
                filteredSeq.add(t);
            }
        }
        return new MPDStream<>(filteredSeq);
    }

    public <R> MPDStream<R> map(Function<T, R> transformer) {
        ArrayList<R> mappedSeq = new ArrayList<>();
        for (T t : this) {
            mappedSeq.add(transformer.apply(t));
        }
        return new MPDStream<>(mappedSeq);
    }

    public <R> MPDStream<R> limit(int limitNumber) {
        ArrayList<R> mappedSeq = new ArrayList<>();
        for (R r : mappedSeq) {
            if(limitNumber-- <= 0)
                break;
            mappedSeq.add(r);
        }
        return new MPDStream<>(mappedSeq);
    }

    @Override
    public Iterator<T> iterator() {
        return seq.iterator();
    }
}

