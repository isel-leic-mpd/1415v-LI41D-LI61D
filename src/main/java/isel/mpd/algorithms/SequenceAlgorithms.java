package isel.mpd.algorithms;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by lfalcao on 04/03/2015.
 */
public class SequenceAlgorithms {
    public <T> Iterable<T> filter(Iterable<T> seq, Predicate<T> pred) {
        ArrayList<T> filteredSeq = new ArrayList<>();
        for (T t : seq) {
            if (pred.test(t)) {
                filteredSeq.add(t);
            }
        }
        return filteredSeq;
    }

    public <T, R> Iterable<R> map(Iterable<T> seq, Function<T, R> transformer) {
        ArrayList<R> mappedSeq = new ArrayList<>();
        for (T t : seq) {
            mappedSeq.add(transformer.apply(t));
        }
        return mappedSeq;
    }
}

