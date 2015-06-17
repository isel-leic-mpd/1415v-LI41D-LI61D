package isel.mpd.Async;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lfalcao on 08/06/2015.
 */


public class Shop extends FakeService {
    private static ExecutorService executor = Executors.newFixedThreadPool(5);
    private String name;
    Random random = new Random();

    public Shop(String name) {

        this.name = name;
    }


    public double getPrice(String product) {
        return calculatePrice(product);
    }


    public CompletableFuture<Double> getPriceAsync(String product) {
        final CompletableFuture<Double> future = new CompletableFuture<>();
        executor.submit(() -> {
            final double price;
            try {
                price = calculatePrice(product);
                future.complete(price);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        return future;

    }

    public CompletableFuture<Double> getPriceAsyncV2(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));

    }

    public String getName() {
        return name;
    }

    public String getPriceStr(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[(random.nextInt(Discount.Code.values().length))];
        String ret = String.format(Locale.getDefault(), "%s:%.2f:%s", name, price, code);
        //System.out.println("getPrice " + System.currentTimeMillis());
        return ret;
    }

    private double calculatePrice(String product) {
        delay(name);
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }
}
