package isel.mpd.misc;

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
        for (int i = 0; i < numRep; i++) {
            T res = supplier.get();
            System.out.printf("Result in iteration %d was %s\n", i, res);
            assertEquals("Value was not equal on iteration " + i, res, val);
        }


    }
}
