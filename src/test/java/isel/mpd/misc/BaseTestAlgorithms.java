package isel.mpd.misc;

import isel.mpd.algorithms.MPDStream;
import isel.mpd.cars.Car;
import org.junit.BeforeClass;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by lfalcao on 18/03/2015.
 */
public class BaseTestAlgorithms {
    protected static Collection<Car> cars;
    protected static MPDStream<Car> sequenceAlgorithms;

    @BeforeClass
    public static void setUp()  {
        cars = getCars();
        sequenceAlgorithms = new MPDStream<Car>(cars);
    }

    private static Collection<Car> getCars() {
        return Arrays.asList(
                new Car("Audi", "A4", 2010, "Red"),
                new Car("BMW", "X5", 2009, "Black"),
                new Car("Renault", "Clio", 2013, "Bordeaux"),
                new Car("Fiat", "Punto", 1996, "White"));
    }
}
