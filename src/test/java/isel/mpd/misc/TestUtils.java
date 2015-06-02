package isel.mpd.misc;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import static junit.framework.Assert.assertEquals;

/**
 * Created by lfalcao on 13/05/2015.
 */
public class TestUtils {


    private static final int DEFAULT_REPETITIONS = 10;

    public static <T>void executeAndCheckResult(Supplier<T> supplier, T val) {
        executeAndCheckResult(supplier, val, DEFAULT_REPETITIONS);
    }

    public static <T>void executeAndCheckResult(Supplier<T> supplier, T val, int numRep) {
        System.out.println("--------------------------------------------------------");
        for (int i = 0; i < numRep; i++) {
            T res = supplier.get();
            System.out.printf("Result in iteration %d was %s\n", i, res);
            assertEquals("Value was not equal on iteration " + i, val,  res);
        }
    }


    public static <T>void executeAndMesurePerformance(Runnable supplier, int numRep, String message) {
        System.out.println("--------------------------------------------------------------------------------------");
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < numRep; i++) {
            long start = System.nanoTime();
            supplier.run();
            long elapsed = System.nanoTime() - start;
            fastest = fastest < elapsed ? fastest : elapsed;
        }

        System.out.println(message + " - Fastest execution in " +  fastest / 1_000_000 + " ms");
    }
}
