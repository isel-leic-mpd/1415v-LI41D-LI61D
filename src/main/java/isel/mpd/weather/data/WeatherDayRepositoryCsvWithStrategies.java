package isel.mpd.weather.data;

import isel.mpd.weather.WeatherDay;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 */
public class WeatherDayRepositoryCsvWithStrategies implements WeatherDayRepository {
    class WebSupplierStrategy implements Supplier<List<WeatherDay>> {

        @Override
        public List<WeatherDay> get() {
            final List<WeatherDay> weatherDays = getWeatherDaysFromWebService();
            weatherDaysSupplier = new LocalSupplierStrategy(weatherDays);
            return weatherDays;
        }
    }

    class LocalSupplierStrategy implements Supplier<List<WeatherDay>> {
        List<WeatherDay> weatherDays;

        public LocalSupplierStrategy(List<WeatherDay> weatherDays) {

            this.weatherDays = weatherDays;
        }

        @Override
        public List<WeatherDay> get() {
            return weatherDays;
        }
    }

    Supplier<List<WeatherDay>> weatherDaysSupplier;
    private final Supplier<String> stringSupplier;
    private final Function<Collection<String>, WeatherDay> weatherInfoCreator;

    public WeatherDayRepositoryCsvWithStrategies(Supplier<String> stringSupplier, Function<Collection<String>, WeatherDay> weatherDayCreator) {
        this.stringSupplier = stringSupplier;
        this.weatherInfoCreator = weatherDayCreator;

        weatherDaysSupplier = new WebSupplierStrategy();
    }


    @Override
    public Collection<WeatherDay> getWeatherDaysSupplier() {
//        List<WeatherDay> weatherDaysSupplier = null;
//        final String[] splittedString = stringSupplier.get().split("(\r\n)|(\n)");
//        Stream<String> stream = Arrays.stream(splittedString).filter(s -> !(s.startsWith("#") || s.indexOf(",") ==-1) );
//        final Map<String, List<String>> collect = stream.collect(groupingBy(s -> s.substring(0, s.indexOf(','))));
//        final Stream<WeatherDay> weatherDayStream = collect.keySet().stream().map(k -> weatherInfoCreator.apply(collect.get(k).toArray(new String[]{})));
//
//        weatherDaysSupplier = weatherDayStream.collect(toList());
//        return weatherDaysSupplier;
        return weatherDaysSupplier.get();

    }

    private List<WeatherDay> getWeatherDaysFromWebService() {
        final Map<String, List<String>> collect =
                Arrays.stream(stringSupplier.get().split("(\r\n)|(\n)"))
                        .filter(s -> !s.startsWith("#") && s.contains(","))
                        .collect(groupingBy(s -> s.substring(0, s.indexOf(','))));
        return collect.values().stream()
                .map(weatherInfoCreator::apply).collect(toList());
    }
}
