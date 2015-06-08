package isel.mpd.algorithms;

import isel.mpd.algorithms.primes.Primes;
import org.junit.Test;

import static isel.mpd.misc.TestUtils.executeAndMeasurePerformance;
import static java.util.stream.Collectors.joining;

/**
 * Created by lfalcao on 27/05/2015.
 */
public class PrimesTests {
    @Test
    public void shouldGenerateTheFirstNPrimes() throws Exception {
        final int NUM_EXECUTIONS = 5;
        final int PRIMES_TILL = 200_000;
        int numPrimes = Primes.partitionPrimesSimpleOptimization(PRIMES_TILL).get(true).size();
        System.out.println(numPrimes);




        executeAndMeasurePerformance(() -> Primes.partitionPrimesSimpleOptimization(PRIMES_TILL), NUM_EXECUTIONS, "partitionPrimesSimpleOptimization");
        executeAndMeasurePerformance(() -> Primes.partitionPrimesWithAuxiliaryListOptimization(PRIMES_TILL), NUM_EXECUTIONS, "partitionPrimesWithAuxiliaryListOptimization");
        executeAndMeasurePerformance(() -> Primes.partitionPrimesWithAuxiliaryListAndFilterOptimization(PRIMES_TILL), NUM_EXECUTIONS, "partitionPrimesWithAuxiliaryListAndFilterOptimization");
        executeAndMeasurePerformance(() -> Primes.partitionPrimesWithAuxiliaryListAndTakeWhileOptimization(PRIMES_TILL), NUM_EXECUTIONS, "partitionPrimesWithAuxiliaryListAndTakeWhileOptimization");




    }




}
