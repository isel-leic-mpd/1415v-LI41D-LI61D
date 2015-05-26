package isel.mpd.algorithms;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by lfalcao on 27/05/2015.
 */
public class CountingCollector<T> implements Collector<T, List<Integer>, Integer>{
    @Override
    public Supplier<List<Integer>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<Integer>, T> accumulator() {
        return (list, t) -> list.add(1);
    }

    @Override
    public BinaryOperator<List<Integer>> combiner() {
        return (l1, l2) -> { l1.addAll(l2); return l1; };
    }

    @Override
    public Function<List<Integer>, Integer> finisher() {
        return List::size;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.CONCURRENT, Characteristics.IDENTITY_FINISH, Characteristics.UNORDERED);
    }
}
