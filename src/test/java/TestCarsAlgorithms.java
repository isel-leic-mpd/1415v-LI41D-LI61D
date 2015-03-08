import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;


/**
 * Created by lfalcao on 04/03/2015.
 */



public class TestCarsAlgorithms {
    private static Iterable<Car> cars;
    private static CarsAlgorithms carsAlgorithms;

    @BeforeClass
    public static void setUp() {
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
    public void shouldFilterCarsByYearWithCarsAboveInCollection() {
        // Arrange

        // Act
        Iterable<Car> res = carsAlgorithms.getCarsAboveYear(cars, 2010);

        // Assert
        assertEquals(1, ((Collection<Car>)res).size());
    }
}