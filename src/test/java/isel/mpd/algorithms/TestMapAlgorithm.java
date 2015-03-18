package isel.mpd.algorithms;

import isel.mpd.cars.Car;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class TestMapAlgorithm extends BaseTestAlgorithms {

    @Test
    public void shouldMapCarsToItsNames() throws Exception {
        // Arrange

        // Act
        Iterable<String> res = sequenceAlgorithms.map(cars, (Car::getModel));

        // Assert
        assertEquals(cars.size(), ((Collection<String>) res).size());
    }
}
