package isel.mpd.algorithms;

/**
 * Created by lfalcao on 29/04/2015.
 */

import isel.mpd.weather.WeatherDay;
import isel.mpd.weather.data.HttpUrlStreamSupplier;
import isel.mpd.weather.data.WeatherDayCsvCreator;
import isel.mpd.weather.data.WeatherDayRepositoryCsv;
import isel.mpd.weather.data.stringsuppliers.SimpleStringSupplierFromStream;
import isel.mpd.weather.data.stringsuppliers.StringSupplier;

import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static isel.mpd.misc.LazyInitializer.lazily;


/**
 *
 */
public class SortingAlgorithms {

    public static void print(List<WeatherDay> src) {

        System.out.println("#############################################");
        src.stream().forEach(System.out::println);

//        for (WeatherDay l : src) {
//            System.out.println(l);
//        }
    }

    private static class MyComparator<T, R extends Comparable<R>> implements Comparator<T> {
        private final Function<T, R> keyExtractor;

        private final Supplier<MyComparator<T, R>> revertedSupplier;

        public MyComparator(Function<T, R> keyExtractor) {
            this.keyExtractor = keyExtractor;

            revertedSupplier = lazily(
                    () -> new MyComparator<T, R>(keyExtractor) {
                        @Override
                        public int compare(T t1, T t2) {
                            return super.compare(t2, t1);
                        }
                    }
            );
        }

        @Override
        public int compare(T t1, T t2) {
            return keyExtractor.apply(t1).compareTo(keyExtractor.apply(t2));
        }

        MyComparator<T, R> revert() {
            return revertedSupplier.get();
        }

        public <U extends Comparable<U>> MyComparator<T, U> andThen(Function<T, U> thenKeyExtractor) {
            final MyComparator<T, R> previousComparator = this;
            return new MyComparator<T, U>(thenKeyExtractor) {
                @Override
                public int compare(T t1, T t2) {
                    final int result = previousComparator.compare(t1, t2);
                    return result != 0 ? result : super.compare(t1, t2);
                }
            };
        }
    }


    public static <T, R extends Comparable<R>> MyComparator<T, R> comparing(Function<T, R> keyExtractor) {
        return new MyComparator<>(keyExtractor);
    }

    public static void main(String[] args) throws ParseException {
        final String endDate = "2015-04-29";
        String url2 = "http://api.worldweatheronline.com/free/v2/past-weather.ashx?q=Lisbon&format=csv&key=44a2b619601959766a08667fa3891&date=2015-04-10&enddate=" + endDate;
        StringSupplier strSupplier = new SimpleStringSupplierFromStream(new HttpUrlStreamSupplier(url2));
        WeatherDayRepositoryCsv repository = new WeatherDayRepositoryCsv(strSupplier, new WeatherDayCsvCreator());


        List<WeatherDay> h = repository.getWeatherDays();

        h.sort((wd1, wd2) -> wd1.getMaxtempC() - wd2.getMaxtempC());
        h.sort((wd1, wd2) -> wd1.getMintempC() - wd2.getMintempC());

        h.sort((wd1, wd2) -> wd2.getMaxtempC() - wd1.getMaxtempC());

        final MyComparator<WeatherDay, Integer> comparing1 = comparing(WeatherDay::getMaxtempC);
        final MyComparator<WeatherDay, Integer> comparing2 = comparing1.revert();
        final MyComparator<WeatherDay, Integer> comparing3 = comparing2.revert();
        final MyComparator<WeatherDay, Integer> comparing4 = comparing1.revert();
        h.sort(comparing1);

        print(h);

        final MyComparator<WeatherDay, String> comparing5 = comparing1.andThen(WeatherDay::getDate);
        h.sort(comparing5);
        print(h);
//
//        h.sort(comparing(WeatherDay::getMaxtempC).andThen(WeatherDay::getMintempC));


        //h.sort((w1, w2) -> ((Double)w1.precipMM).compareTo(w2.precipMM));
        // h.sort((w1, w2) -> w1.weatherDesc.compareTo(w2.weatherDesc));
        // h.sort(comparing(WeatherInfo::getTempC).reversed());
        /*
        h.sort(
                comparing(WeatherInfo::getWeatherDesc).andThen(WeatherInfo::getTempC)
                        .andThen(WeatherInfo::getPrecipMM));
        */

//        h.sort(Comparator
//                .comparing(WeatherDay::getWeatherDesc)
//                .thenComparing(WeatherDay::getTempC)
//                .thenComparing(WeatherInfo::getPrecipMM));

        print(h);
    }
}

