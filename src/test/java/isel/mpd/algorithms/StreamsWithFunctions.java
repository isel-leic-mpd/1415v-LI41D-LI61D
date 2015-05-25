package isel.mpd.algorithms;

import org.junit.Test;

import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by lfalcao on 20/05/2015.
 */
public class StreamsWithFunctions {

    @Test
    public void shouldGenerateFibonacciSequenceWithMutableState() throws Exception {
        IntStream.generate(new FibonacciSupplier()).limit(10).forEach(System.out::println);
    }

    @Test
    public void shouldGenerateFibonacciSequenceWithImmutableState() throws Exception {
//        Stream.iterate(new int[] {1,1}, p -> new int[] { p[1], p[0] + p[1]})
//                .limit(10)
//                .forEach((a) -> System.out.println(Arrays.toString(a)));

        // Or
        Stream.iterate(new int[] {1,1}, p -> new int[] { p[1], p[0] + p[1]})
                .limit(10).mapToInt(a -> a[0]).forEach(System.out::println);



    }

    private class FibonacciSupplier implements IntSupplier {
        int curr = 1, prev = 0;

        @Override
        public int getAsInt() {
            int next = curr + prev;
            prev = curr;
            curr = next;

            return prev;
        }
    }
}
