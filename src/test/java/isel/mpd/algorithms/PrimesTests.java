package isel.mpd.algorithms;

import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

/**
 * Created by lfalcao on 27/05/2015.
 */
public class PrimesTests {
    @Test
    public void shoudGenerateTheFirstNPrimes() throws Exception {
        List<Integer> primes = partitionPrimes(100);

        //Map<Boolean, List<Integer>> primesAndNonPrimes = IntStream.range(1, 100).collect(partitioningBy(this::isPrime));
    }

    private List<Integer> partitionPrimes(int i) {
        return IntStream.range(1, i).boxed().filter(this::isPrime).collect(toList());
    }


    boolean isPrime(int n) {
        final int canditatePrime = (int)Math.sqrt(n);
        return !IntStream.range(2,canditatePrime).anyMatch((i) ->  n % i == 0);
    }
}
