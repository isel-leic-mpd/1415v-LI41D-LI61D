package isel.mpd.async;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lfalcao on 08/06/2015.
 */
public class Shop {
    private static ExecutorService executor = Executors.newFixedThreadPool(5);
    private String bestShop;

    public Shop(String bestShop) {

        this.bestShop = bestShop;
    }

    public static void delay() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public double getPrice(String product) throws Exception {
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

    private double calculatePrice(String product) throws Exception{
        delay();
        return Math.random();
    }



}