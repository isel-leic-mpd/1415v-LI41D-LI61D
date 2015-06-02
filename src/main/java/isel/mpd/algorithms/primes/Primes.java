package isel.mpd.algorithms.primes;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 */
public class Primes {
    static class PrimesCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

        private BiFunction<List<Integer>, Integer, Boolean> isPrime;

        public PrimesCollector(BiFunction<List<Integer>, Integer, Boolean> isPrime) {
            this.isPrime = isPrime;
        }

        @Override
        public Supplier<Map<Boolean, List<Integer>>> supplier() {
            return () -> new HashMap<Boolean, List<Integer>>() {{
                        put(true, new ArrayList<>());
                        put(false, new ArrayList<>());
                    }};
        }


        @Override
        public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
            return (m, candidate) -> m.get(this.isPrime.apply(m.get(true), candidate)).add(candidate);
        }

        @Override
        public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
            return (m1, m2) -> {
                m1.get(true).addAll(m2.get(true));
                m1.get(false).addAll(m2.get(false));
                return m1;
            };
        }

        @Override
        public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
        }
    }


    public static Map<Boolean, List<Integer>> partitionPrimesSimpleOptimization(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(Collectors.partitioningBy(Primes::isPrime));
    }

    public static Map<Boolean, List<Integer>> partitionPrimesWithAuxiliaryListOptimization(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(new PrimesCollector(Primes::isPrimeWithList));
    }

    public static Map<Boolean, List<Integer>> partitionPrimesWithAuxiliaryListAndFilterOptimization(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(new PrimesCollector(Primes::isPrimeWithListAndFilter));
    }

    public static Map<Boolean, List<Integer>> partitionPrimesWithAuxiliaryListAndTakeWhileOptimization(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(new PrimesCollector(Primes::isPrimeWithTakeWhile));
    }

    private static boolean isPrime(int n) {
        final int candidatePrime = (int)Math.sqrt(n);
        return IntStream.rangeClosed(2, candidatePrime).noneMatch((i) -> n % i == 0);
    }


    private static boolean isPrimeWithList(List<Integer> primes, int n) {
        return primes.stream().noneMatch((i) -> n % i == 0);

    }

    private static boolean isPrimeWithListAndFilter(List<Integer> primes, int n) {
        final int candidatePrime = (int)Math.sqrt(n);
        return primes.stream().filter(i -> i <= candidatePrime).noneMatch((i) -> n % i == 0);
    }

    private static boolean isPrimeWithTakeWhile(List<Integer> primes, int n) {
        final int candidatePrime = (int)Math.sqrt(n);
        return takeWhile(primes, i -> i <= candidatePrime).stream().noneMatch((i) -> n % i == 0);
    }

    private static <A> List<A> takeWhile(List<A> list, Predicate<A> p) {
        int i = 0;

        for (A item : list) {
            if(!p.test(item)) {
                return list.subList(0, i);
            }
            ++i;
        }
        return list;
    }

}
