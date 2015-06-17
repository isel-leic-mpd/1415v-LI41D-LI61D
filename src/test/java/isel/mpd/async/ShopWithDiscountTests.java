package isel.mpd.async;

import isel.mpd.Async.Discount;
import isel.mpd.Async.Quote;
import isel.mpd.Async.Shop;
import isel.mpd.misc.TestUtils;
import isel.mpd.weather.data.stringsuppliers.StringSupplier;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by lfalcao on 15/06/15.
 */
public class ShopWithDiscountTests extends BaseShopTests  {
    @Test
    public void shouldGetPricesWithDiscountSequential() throws Exception {
        TestUtils.executeAndMeasurePerformance(() -> getPricesWithDiscount(shops.stream()), 1, "getPricesWithDiscountSequential");
    }

    @Test
    public void shouldGetPricesWithDiscountParallel() throws Exception {
        TestUtils.executeAndMeasurePerformance(() -> getPricesWithDiscount(shops.parallelStream()), 1, "getPricesWithDiscountParallel");
    }


    @Test
    public void shouldGetPricesWithDiscountAsync() throws Exception {
        TestUtils.executeAndMeasurePerformance(this::getPricesWithDiscountAsync, 1, "getPricesWithDiscountAsync");
    }

    @Test
    public void shouldGetPricesWithDiscountAsyncOldFashion() throws Exception {
        TestUtils.executeAndMeasurePerformance(this::getPricesWithDiscountAsyncOldFashion, 1, "getPricesWithDiscountAsyncOldFashion");
    }

    @Test
    public void shouldShowPricesAsAvailable() throws Exception {
        TestUtils.executeAndMeasurePerformance(this::showPricesAsAvailableAsync, 1, "showPricesAsAvailable");
    }

    private List<String> getPricesWithDiscount(Stream<Shop> stream) {
        return stream
                .map(s -> s.getPriceStr(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(toList());
    }

    private List<String> getPricesWithDiscountAsync() {
        List<CompletableFuture<String>> priceFutures = findPricesStream()
                .collect(toList());

        return priceFutures.stream().map(CompletableFuture::join).collect(toList());
    }

    private List<String> getPricesWithDiscountAsyncOldFashion() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<String>> pricesFutures = new ArrayList<>(shops.size());

        for (final Shop shop : shops) {
            pricesFutures.add(executor.submit(() -> shop.getPriceStr(product)));
        }



        for (final Future<String> priceFuture : pricesFutures) {
            pricesFutures.add(executor.submit(
                    () -> {
                        try {
                            Quote quote = Quote.parse(priceFuture.get());
                            String price = Discount.applyDiscount(quote);
                            return price;
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    }
            ));
        }
        List<String> prices = new ArrayList<>(shops.size());

        for (final Future<String> priceFuture : pricesFutures) {
            try {
                prices.add(priceFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        return prices;

    }

    private Stream<CompletableFuture<String>> findPricesStream() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        return shops.stream()
                .map(s -> CompletableFuture.supplyAsync(() -> s.getPriceStr(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote->
                                CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor))
                );
    }

    private List<String> showPricesAsAvailableAsync() {
        long start = System.nanoTime();

        CompletableFuture[] completableFutures = shops.stream()
                .map(s -> CompletableFuture.supplyAsync(() -> s.getPriceStr(product)))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote ->
                                CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote)))
                )
                .map(f -> f.thenAccept((s) ->
                        System.out.println(s + " (done in " +
                                ((System.nanoTime() - start) / 1_000_000) + " msecs)")
        ))
                .toArray(size -> new CompletableFuture[size]);

        CompletableFuture.allOf(completableFutures).join();

        System.out.println("All shops have now responded in "
                + ((System.nanoTime() - start) / 1_000_000) + " msecs");

        return null;
    }
}
