package isel.mpd.algorithms;

import isel.mpd.cars.Car;
import isel.mpd.misc.BaseTestAlgorithms;
import org.junit.Test;

import java.util.Collection;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class TestMapAlgorithm extends BaseTestAlgorithms {

    @Test
    public void shouldMapCarsToItsNames() throws Exception {
        // Arrange

        // Act
        Iterable<String> res = sequenceAlgorithms.map((Car::getModel));

        // Assert
        assertEquals(cars.size(), ((Collection<String>) res).size());
    }


    @Test
    public void shouldMapCarsAfterFilter() throws Exception {
        // Arrange

        Stream<Car> carsStream = cars.stream();

        // Act
        Iterable<String> res = sequenceAlgorithms.filter((c) -> {
            System.out.println("filtered " + c.toString());
            return c.getYear() > 2000;
        })
        .map((c) -> {
            System.out.println("mapped " + c.toString());
            return c.getModel();
        }).limit(2);

        System.out.println("----------------------------------------------");

        res = carsStream.filter((c) -> {
            System.out.println("filtered " + c.toString());
            return c.getYear() > 2000;
        }).map((c) -> {
                    System.out.println("mapped " + c.toString());
                    return c.getModel();
                }).limit(2)
                .collect(toList());


//        Iterable<Car> carsFiltered = SequenceAlgorithms.filter(cars, (c) -> c.getYear() > 2012);
//        Iterable<String> modesls = SequenceAlgorithms.map(carsFiltered, Car::getModel);
//
//
//        Iterable<String> modesls = SequenceAlgorithms.map(
//                SequenceAlgorithms.filter(cars, (c) -> c.getYear() > 2012), Car::getModel);


        // Assert
        assertEquals(cars.size(), ((Collection<String>) res).size());
    }
}
