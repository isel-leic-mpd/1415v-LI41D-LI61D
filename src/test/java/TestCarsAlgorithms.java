import isel.mpd.cars.Car;
import isel.mpd.cars.CarsAlgorithms;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;


/**
 * Created by lfalcao on 04/03/2015.
 */



public class TestCarsAlgorithms {
    private static Iterable<Car> cars;
    private static CarsAlgorithms carsAlgorithms;

    @BeforeClass
    public static void setUp()  {
        cars = getCars();
        carsAlgorithms = new CarsAlgorithms();
    }



    private static Iterable<Car> getCars() {
        return Arrays.asList(
                new Car("Audi", "A4", 2010, "Red"),
                new Car("BMW", "X5", 2009, "Black"),
                new Car("Renault", "Clio", 2012, "Bordeaux"),
                new Car("Fiat", "Punto", 1996, "White"));
    }

    @Test
    public void shouldFilterCarsAboveYearUnoptimizedVersion() {
        // Arrange

        // Act
        Iterable<Car> res = carsAlgorithms.filter(cars, new Predicate<Car>() {
            @Override
            public boolean test(Car car) {
                return car.getYear() > 2010;
            }
        });

        // Assert
        assertEquals(1, ((Collection<Car>)res).size());
    }

    @Test
    public void shouldFilterCarsAboveYearOptimizedVersion() {
        // Arrange

        // Act
        Iterable<Car> res = carsAlgorithms.filterOptimized(cars, new Predicate<Car>() {
            @Override
            public boolean test(Car car) {
                return car.getYear() > 2010;
            }
        });

        // Assert
        assertEquals(1, ((Collection<Car>)res).size());
    }
}
