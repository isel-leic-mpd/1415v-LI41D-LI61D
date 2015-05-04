package isel.mpd.algorithms;

import isel.mpd.cars.Car;
import isel.mpd.misc.BaseTestAlgorithms;
import org.junit.Test;

import java.util.Collection;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;


public class TestFilterAlgorithm extends BaseTestAlgorithms {

    private String name = "TestFilterAlgorithm";

    /**
     * Version 1: Inner class
     */
    @Test
    public void shouldFilterCarsAboveYearWithInnerClass() {
        // Arrange

        // Act
        Iterable<Car> res = sequenceAlgorithms.filter(new AboveYearPredicate(2012));

        // Assert
        assertEquals(1, ((Collection<Car>)res).size());
    }



    /**
     * Version 2: Anonymous class
     */
    @Test
    public void shouldFilterCarsAboveYearWithAnonymousClass() {
        // Arrange
        final int year = 2012;

        Predicate<Car> pred = new Predicate<Car>() {
            @Override
            public boolean test(Car car) {
                return car.getYear() > year;
            }
        };

        // Act
        Iterable<Car> res = sequenceAlgorithms.filter(pred);

        // Assert
        assertEquals(1, ((Collection<Car>)res).size());
    }

    /**
     * Version 4:Another anonymous class
     */
    @Test
    public void shouldFilterCarsAboveYearWithAnotherAnonymousClass() {
        // Arrange

        // Act
        Iterable<Car> res = sequenceAlgorithms.filter(new Predicate<Car>() {
            @Override
            public boolean test(Car car) {
                return car.getYear() > 2012;
            }
        });

        // Assert
        assertEquals(1, ((Collection<Car>)res).size());
    }


    /**
     * Version 4: method reference
     */
    @Test
    public void shouldFilterCarsAboveYearWithMethodReference() {
        // Arrange
        Predicate<Car> pred = this::carAboveYear;

        // Act
        Iterable<Car> res = sequenceAlgorithms.filter(pred);

        // Assert
        assertEquals(1, ((Collection<Car>)res).size());
    }

    /**
     * Version 4: method reference
     */
    @Test
    public void shouldFilterCarsAboveYearWithLambda() {
        // Arrange

        // Act
        Iterable<Car> res = sequenceAlgorithms.filter((Car c) -> c.getYear() > 2012 );

        // Assert
        assertEquals(1, ((Collection<Car>)res).size());
    }





    private boolean carAboveYear(Car o) {
        return o.getYear() > 2012;
    }



    public class AboveYearPredicate implements Predicate<Car> {

        private String name = "AboveYearPredicate";
        private int year;

        public AboveYearPredicate(int year) {
            this.year = year;
        }

        @Override
        public boolean test(Car car) {
            System.out.println("Own Name: " + name + "; Outer name: " + TestFilterAlgorithm.this.name);
            return car.getYear() > year;
        }
    }

}
