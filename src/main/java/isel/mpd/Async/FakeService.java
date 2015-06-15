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

    public static void delay() {
        delay(1000L);
    }

    public static void delayRandom() {
        int delay = 500 + random.nextInt(2000); try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e); }
    }
}
