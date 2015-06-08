package isel.mpd.async;

import isel.mpd.misc.TestUtils;
import org.junit.Test;

import java.lang.reflect.Executable;
import java.util.concurrent.*;

/**
 * Created by lfalcao on 08/06/2015.
 */
public class UsingFutures {


    public void runningLongOperationSequential() {
        doSomePreviousWork();
        long result = 0, result2 = 0;
        try {
            result = doSomeLongOperation();
            result2 = doAnotherLongOperation();
        } catch (Exception e) {
            processException(e);
        }

        doSomeAfterWork();
        processResult(result, result2);
    }

    private void processException(Exception e) {

    }


    public void runningLongOperationWithExecutorService() {
        ExecutorService service = Executors.newCachedThreadPool();
        doSomePreviousWork();
        final Future<Long> futureResult = service.submit(() -> doSomeLongOperation());
        final Future<Long> futureResult2 = service.submit(() -> doAnotherLongOperation());



        doSomeAfterWork();

        try {
            processResult(futureResult.get(3, TimeUnit.SECONDS), futureResult2.get(3, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
          processException(e);
        }
    }

    @Test
    public void usingShopAsync() throws Exception {

        TestUtils.executeAndMeasurePerformance(this::testShop1, 1, "testShop1");

    }

    private void testShop1() {
        Shop shop = new Shop("BestShop");
        final CompletableFuture<Double> iPhone23S = shop.getPriceAsync("iPhone23S");
        doSomeAfterWork();
        try {
            iPhone23S.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private void processResult(long result, long result2) {
    }

    private void doSomeAfterWork() {

    }

    private void doSomePreviousWork() {

    }



    private long doSomeLongOperation() throws Exception{
        return 3209432840932893288L;
    }
    private long doAnotherLongOperation() throws Exception {
        return 943280943288439L;
    }

}
