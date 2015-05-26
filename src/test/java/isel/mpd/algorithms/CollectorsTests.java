package isel.mpd.algorithms;

import isel.mpd.weather.WeatherDay;
import isel.mpd.weather.data.FileStreamSupplier;
import isel.mpd.weather.data.WeatherDayCsvCreator;
import isel.mpd.weather.data.WeatherDayRepositoryCsv;
import isel.mpd.weather.data.stringsuppliers.SimpleStringSupplierFromStream;
import isel.mpd.weather.data.stringsuppliers.StringSupplier;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;
import static junit.framework.Assert.assertEquals;

/**
 */
public class CollectorsTests {
    private static WeatherDayRepositoryCsv wdr;

    @BeforeClass
    public static void beforeClass() {
        final Path path = Paths.get(ClassLoader.getSystemClassLoader().getResource("weather-lisbon-history-long.csv").getPath().substring(1));
        StringSupplier stringSupplier1 = new SimpleStringSupplierFromStream(new FileStreamSupplier(path.toString()));
        wdr = new WeatherDayRepositoryCsv(stringSupplier1, new WeatherDayCsvCreator());

    }
    @Test
    public void shouldObtainMaximumAndMinumumTemperatures() throws Exception {
        final List<WeatherDay> weatherDays = wdr.getWeatherDays();

        System.out.println(weatherDays.stream().map(WeatherDay::getDate).collect(joining("## 34 ##")));

        // Get the days with the maximum and minumum temperature
        Optional<WeatherDay> maxMaxTemp = weatherDays.stream().collect(maxBy(comparing(WeatherDay::getMaxTempC)));
        System.out.println("Maximum temperature on day: " + maxMaxTemp.get());

        Optional<WeatherDay> maxMinTemp = weatherDays.stream()
                .collect(maxBy(comparing(WeatherDay::getMinTempC)));

        System.out.println("Maximum minimum temperature on day: " + maxMinTemp.get());

        Optional<WeatherDay> minTemp = weatherDays.stream()
                .collect(minBy(comparing(WeatherDay::getMinTempC)));

        System.out.println("Minimum temperature on day: " + minTemp.get());

    }

    @Test
    public void shouldObtainSummaryStatisticsForTemperatures() throws Exception {
        final IntSummaryStatistics sumStatsMaxTemp = wdr.getWeatherDays().stream().collect(summarizingInt(WeatherDay::getMaxTempC));

        System.out.println(sumStatsMaxTemp);

        final IntSummaryStatistics sumStatsMinTemp = wdr.getWeatherDays().stream().collect(summarizingInt(WeatherDay::getMinTempC));

        System.out.println(sumStatsMinTemp);
    }


    @Test
    public void shouldBeAbleToCalculateMaxWithReducing() throws Exception {
        int sumTemps = wdr.getWeatherDays().stream()
                .collect(reducing(0, WeatherDay::getMaxTempC, Integer::sum));
        System.out.println("Sum of temperatures: " + sumTemps);
    }

    @Test
     public void shouldAbleToCollectToAList() throws Exception {
        final List<WeatherDay> weatherDaysList = wdr.getWeatherDays().stream().collect(toList());

        // Equivalent with reduce
//        final List<WeatherDay> weatherDaysListFromReduce = wdr.getWeatherDays().stream()
//                .reduce(new ArrayList<WeatherDay>(),
//                        (List<WeatherDay> list, WeatherDay wd) -> {
//                            list.add(wd);
//                            return list;
//                        },
//                        (l1, l2) -> {
//                            l1.addAll(l2);
//                            return l1;
//                        }
//                );

    }

    /////////////// Grouping tests ///////////////

    enum TemperatureInterval {
        COLD, WARM, HOT
    }
    @Test
    public void shouldAggrupateWeatherInfoByTemperature() throws Exception {
        Map<TemperatureInterval, List<WeatherDay>> wdByTemperature = wdr.getWeatherDays().stream()
                .collect(groupingBy((wd) -> {
                    if (wd.getMaxTempC() < 12 )
                        return TemperatureInterval.COLD;
                    if(wd.getMaxTempC() > 22)
                        return  TemperatureInterval.HOT;
                    return TemperatureInterval.WARM;
                }));

        wdByTemperature.entrySet().stream().forEach(System.out::println);

    }

    private <T, U> Map<U, Long> createHistogram(Stream<T> s, Function<T, U> classifier) {
        return s.collect(groupingBy(classifier, counting()));
    }

    @Test
    public void shouldGetTheMapWithTemperaturesHistogram() throws Exception {
        Map<Integer, Long> histogramMaxTemperatures = wdr.getWeatherDays().stream()
                .collect(groupingBy(WeatherDay::getMaxTempC, counting()));

        // The same as above, but using an auxiliary method to create histograms
        //Map<Integer, Long> histogramMaxTemperatures = createHistogram(wdr.getWeatherDays().stream(), WeatherDay::getMaxTempC);
        //createHistogram(wdr.getWeatherDays().stream(), WeatherDay::getMinTempC);


        histogramMaxTemperatures.entrySet().stream().forEach(System.out::println);
    }


    @Test
    public void shouldGetTheDaysWithMostSunnyHoursByEachMaxTemperature() throws Exception {
        Map<Integer, String> maxSunTimeByTemp = wdr.getWeatherDays().stream()
                .collect(groupingBy(WeatherDay::getMaxTempC,
                        collectingAndThen(
                                maxBy(comparing((wd) -> getSunHours(wd))),
                                (owd) -> owd.get().getDate())
                ));


        maxSunTimeByTemp.entrySet().stream().forEach(System.out::println);
    }

    @Test
    public void shouldUseToListCollectorWithTheSameResultsAstoListCollectorFactory() throws Exception {
        final List<WeatherDay> listWithToList = wdr.getWeatherDays().stream().collect(toList());
        final List<WeatherDay> listWithToListCollector = wdr.getWeatherDays().stream().collect(new ToListCollector<>());

        // Using collect 3 arguments overload
        final List<WeatherDay> listWithCollectWithThreeArguments =
                wdr.getWeatherDays().stream()
                    .collect(ArrayList::new, List::add, List::addAll);

        assertEquals(listWithToList, listWithToListCollector);

    }

    private long getSunHours(WeatherDay wd) {
        String strDateFormat = "HH:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        Date sunrise = null;
        Date sunset = null;
        try {
            sunset = sdf.parse(wd.getSunset());
            sunrise = sdf.parse(wd.getSunrise());

            return sunset.getTime()-sunrise.getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void shouldParseSimpleDateFormat() throws Exception {
        String strDateFormat = "HH:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        Date d = sdf.parse("07:00 AM");
        Date d1 = sdf.parse("07:02 AM");

        System.out.println(d1.getTime() - d.getTime());
        System.out.println(d.toString());


    }
}
