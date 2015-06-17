package isel.mpd.Async;

import java.util.Random;

/**
 * Created by lfalcao on 15/06/15.
 */
public class FakeService {

    private static Random random = new Random();

    public static void delay(long timeInMils) {
        try {
            Thread.sleep(timeInMils);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void delayFixed(String name) {
        delay(1000L);
    }

    public static void delay(String name) {
        int delay = 500 + random.nextInt(2000); try {
            System.out.println(name + " sleeping for " + delay);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e); }
    }
}
