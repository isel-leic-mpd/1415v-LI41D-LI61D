package isel.mpd.algorithms;

import isel.mpd.cars.Car;
import isel.mpd.misc.BaseTestAlgorithms;
import isel.mpd.misc.TestUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.OptionalDouble;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by lfalcao on 06/05/2015.
 */
public class MPDLazyStreamTests {

    protected static Collection<Car> cars;
    protected static MPDLazyStream<Car> lazyStream;

    @BeforeClass
    public static void setUp()  {
        cars = getCars();
        lazyStream = new MPDLazyStream<>(cars);
    }

    private static Collection<Car> getCars() {
        return Arrays.asList(
                new Car("Audi", "A4", 2010, "Red"),
                new Car("BMW", "X5", 2009, "Black"),
                new Car("BMW", "320D", 2005, "White"),
                new Car("Renault", "Clio", 2013, "Bordeaux"),
                new Car("Fiat", "Punto", 1996, "White"),
                new Car("Audi", "A4", 2010, "Red"),
                new Car("BMW", "X5", 2009, "Black"),
                new Car("BMW", "320D", 2005, "White"),
                new Car("Renault", "Clio", 2013, "Bordeaux"),
                new Car("Fiat", "Punto", 1996, "White"),
                new Car("Audi", "A4", 2010, "Red"),
                new Car("BMW", "X5", 2009, "Black"),
                new Car("BMW", "320D", 2005, "White"),
                new Car("Renault", "Clio", 2013, "Bordeaux"),
                new Car("Fiat", "Punto", 1996, "White"),
                new Car("Audi", "A4", 2010, "Red"),
                new Car("BMW", "X5", 2009, "Black"),
                new Car("BMW", "320D", 2005, "White"),
                new Car("Renault", "Clio", 2013, "Bordeaux"),
                new Car("Fiat", "Punto", 1996, "White"),
                new Car("Audi", "A4", 2010, "Red"),
                new Car("BMW", "X5", 2009, "Black"),
                new Car("BMW", "320D", 2005, "White"),
                new Car("Renault", "Clio", 2013, "Bordeaux"),
                new Car("Fiat", "Punto", 1996, "White"),
                new Car("Audi", "A4", 2010, "Red"),
                new Car("BMW", "X5", 2009, "Black"),
                new Car("BMW", "320D", 2005, "White"),
                new Car("Renault", "Clio", 2013, "Bordeaux"),
                new Car("Fiat", "Punto", 1996, "White"),
                new Car("Audi", "A4", 2010, "Red"),
                new Car("BMW", "X5", 2009, "Black"),
                new Car("BMW", "320D", 2005, "White"),
                new Car("Renault", "Clio", 2013, "Bordeaux"),
                new Car("Fiat", "Punto", 1996, "White"),
                new Car("Audi", "A4", 2010, "Red"),
                new Car("BMW", "X5", 2009, "Black"),
                new Car("BMW", "320D", 2005, "White"),
                new Car("Renault", "Clio", 2013, "Bordeaux"),
                new Car("Fiat", "Punto", 1996, "White"),
                new Car("Audi", "A4", 2010, "Red"),
                new Car("BMW", "X5", 2009, "Black"),
                new Car("BMW", "320D", 2005, "White"),
                new Car("Renault", "Clio", 2013, "Bordeaux"),
                new Car("Fiat", "Punto", 1996, "White"),
                new Car("Audi", "A4", 2010, "Red"),
                new Car("BMW", "X5", 2009, "Black"),
                new Car("BMW", "320D", 2005, "White"),
                new Car("Renault", "Clio", 2013, "Bordeaux"),
                new Car("Fiat", "Punto", 1996, "White"),
                new Car("Audi", "A4", 2010, "Red"),
                new Car("BMW", "X5", 2009, "Black"),
                new Car("BMW", "320D", 2005, "White"),
                new Car("Renault", "Clio", 2013, "Bordeaux"),
                new Car("Fiat", "Punto", 1996, "White"),
                new Car("Audi", "A4", 2010, "Red"),
                new Car("BMW", "X5", 2009, "Black"),
                new Car("BMW", "320D", 2005, "White"),
                new Car("Renault", "Clio", 2013, "Bordeaux"),
                new Car("Fiat", "Punto", 1996, "White"),
                new Car("Smart", "Punto", 2000, "White"));


    }

    @Test
    public void shouldBeAbleToUseIntremediateStreams() throws Exception {


        final MPDLazyStream<Car> stream1 = lazyStream.filter((Car c) -> c.getYear() > 2000);
        final MPDLazyStream<Car> stream2 = stream1.filter((Car c) -> c.getBrand().equals("BMW"));

        assertEquals(4, count(stream1));
        assertEquals(2, count(stream2));

    }


    @Test
    public void shouldBeAbleToCombinefilterAndMap() throws Exception {


        MPDLazyStream<String> stream1 = lazyStream.filter((Car c) -> c.getYear() > 2000)
                .map(c -> c.getBrand() + c.getModel());

        assertEquals(4, count(stream1));
        for (String s : stream1) {
            assertNotNull(s);
            assertTrue(s.length() > 0);
        }

    }


    @Test
    public void shouldGetAverageOfStringLengths() throws Exception {
        OptionalDouble average = lazyStream.average2(Car::getYear);

        assertEquals(getCars().stream().mapToInt(Car::getYear).average().getAsDouble(), average.getAsDouble());
    }

    private int count(MPDLazyStream<?> stream1) {
        int cnt = 0;
        for (Object car : stream1) {
            ++cnt;
        }
        return cnt;
    }


    @Test
    public void shouldExecuteAverageSeveralTimesAndHaveTheSameResult() throws Exception {
        Double averageExpected = 1000.0;
        TestUtils.executeAndCheckResult(MPDLazyStreamTests::calcAverage, averageExpected, 1);
    }



    private static Double calcAverage() {
        //return lazyStream.average4(Car::getYear, new Car("Dummy", "A4", 2010, "Red")).getAsDouble();
        return lazyStream.average4(Car::getWeight).getAsDouble();
    }


    private static Double calcAverageWithMap() {
        OptionalDouble res = lazyStream.stream().mapToDouble(Car::getYear).boxed().mapToInt(Double::intValue).average();
        return res.orElse(0.0);

        //return res.isPresent() ? res.getAsDouble() : 0.0;
    }
}
