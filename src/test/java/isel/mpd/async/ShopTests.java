package isel.mpd.async;

import isel.mpd.Async.Shop;
import isel.mpd.misc.TestUtils;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by lfalcao on 15/06/15.
 */
public class ShopTests  extends BaseShopTests {


    @Test
    public void usingShopAsync() throws Exception {

        TestUtils.executeAndMeasurePerformance(this::testShop1, 1, "testShop1");

    }


    @Test
    public void shouldFindTheBestPrice() throws Exception {
        //System.out.println(Runtime.getRuntime().availableProcessors());
        TestUtils.executeAndMeasurePerformance(this::getPrices, 1, "findBestPrice");

    }

    @Test
    public void shouldFindTheBestPriceParallel() throws Exception {
        TestUtils.executeAndMeasurePerformance(this::getPricesParallel, 1, "findBestPriceParallel");

    }

    @Test
    public void shouldGetAllPricesCompletableFutures() throws Exception {
        TestUtils.executeAndMeasurePerformance(this::getPricesWithCompletableFutures, 1, "getPricesWithCompletableFutures");

    }

    private List<String> getPrices() {
        return getPricesFromStream(shops.stream());
    }

    private List<String> getPricesParallel() {
        return getPricesFromStream(shops.parallelStream());
    }

    private List<String> getPricesFromStream(Stream<Shop> stream) {
        String product = "iGadget64";
        return stream
                .map(shop -> String.format("%s price is %.2f",
                        shop.getName(), shop.getPrice(product)))
                .collect(toList());
    }

    private List<String> getPricesWithCompletableFutures() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);


        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                .map(shop ->
                    CompletableFuture.supplyAsync(
                            () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)),
                            executorService
                            ))

                .collect(toList());

        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }

    private Double testShop1() {
        Shop shop = new Shop("BestShop");
        final CompletableFuture<Double> iPhone23S = shop.getPriceAsync("iPhone23S");
        doSomeAfterWork();
        try {
            return iPhone23S.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void doSomeAfterWork() {

    }
}


