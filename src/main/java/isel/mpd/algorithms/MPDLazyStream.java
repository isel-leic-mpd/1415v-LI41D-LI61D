package isel.mpd.algorithms;

import com.google.common.collect.Lists;
import isel.mpd.cars.Car;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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



    public T reduce(T identity, BinaryOperator<T> accumulator) {
        T result = identity;
        for (T t : (Iterable<T>)seq) {
            result = accumulator.apply(result, t);
        }
        return result;

    }


    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        Objects.requireNonNull(accumulator);
        T result = null;
        boolean hasElements = false;
        for (T t : (Iterable<T>)seq) {
            if(hasElements) {
                result = accumulator.apply(result, t);
            } else {
                hasElements = true;
                result = t;
            }
        }
        return hasElements ? Optional.of(result) : Optional.empty();
    }

    @Override
    public void forEach(Consumer<? super T> consumer) {
        for (T t :(Iterable<T>)seq) {
            consumer.accept(t);
        }
    }

    public Optional<T> max(Comparator<? super T> comparator) {
        return reduce((max, t) -> comparator.compare(max, t) >= 0 ? max : t);
    }


    public OptionalDouble average(ToIntFunction<T> converter) {
        Averager<T> averager = new Averager<T>(converter);
        forEach(averager);
        return averager.average();
    }

    public OptionalDouble average2(ToIntFunction<T> converter) {
        Averager<T> averager = new Averager<T>(converter);
        reduce(null, averager);
        return averager.average();
    }


    public OptionalDouble average3(ToIntFunction<? super T> converter) {
        Stream<T> stream = Lists.newArrayList((Iterable<T>) seq).stream();
        int sum = stream
                .mapToInt(converter)
                .reduce(0, (i1, i2) -> i1 + i2);

        stream = Lists.newArrayList((Iterable<T>) seq).stream();
        long count = stream.count();
        return count == 0 ? OptionalDouble.empty() : OptionalDouble.of((double)sum / count);

    }

    public OptionalDouble average4(ToIntFunction<T> converter, T identity) {
        Averager<T> averager = new Averager<T>(converter);
        stream().parallel().reduce(identity, averager);
        return averager.average();
    }

    public Stream<T> stream() {
        //return StreamSupport.stream(((Iterable<T>) seq).spliterator(), false);
        return Lists.newArrayList((Iterable<T>) seq).stream();
    }

    @Override
    public Iterator<T> iterator() {
        return (Iterator<T>) seq.iterator();
    }

    private class Averager<T>  implements BinaryOperator<T>, Consumer<T> {
        public int sum = 0;
        public int cnt = 0;
        private final ToIntFunction<T> converter;

        public Averager(ToIntFunction<T> converter) {
            Objects.requireNonNull(converter);
            this.converter = converter;
        }

        @Override
        public T apply(T t, T t2) {
            accept(t2);
            return t2;
        }

        public OptionalDouble average() {
            return cnt == 0 ? OptionalDouble.empty() : OptionalDouble.of((double)sum / cnt);
        }

        @Override
        public void accept(T t) {
            System.out.printf("Accept called in thread %d. T is %s\n", Thread.currentThread().getId(), t);
            if(t != null) {
                cnt++;
                sum += converter.applyAsInt(t);
            }
        }
    }
}
