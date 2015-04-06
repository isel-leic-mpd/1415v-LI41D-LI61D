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
 * Created by lfalcao on 05/04/2015.
 */
public class WeatherDayRepositoryCsv implements WeatherDayRepository {
    List<WeatherDay> weatherDays;
    private final Supplier<String> stringSupplier;
    private final Function<Collection<String>, WeatherDay> weatherInfoCreator;

    public WeatherDayRepositoryCsv(Supplier<String> stringSupplier, Function<Collection<String>, WeatherDay> weatherDayCreator) {
        this.stringSupplier = stringSupplier;
        this.weatherInfoCreator = weatherDayCreator;
    }


    @Override
    public Collection<WeatherDay> getWeatherDays() {
//        List<WeatherDay> weatherDays = null;
//        final String[] splittedString = stringSupplier.get().split("(\r\n)|(\n)");
//        Stream<String> stream = Arrays.stream(splittedString).filter(s -> !(s.startsWith("#") || s.indexOf(",") ==-1) );
//        final Map<String, List<String>> collect = stream.collect(groupingBy(s -> s.substring(0, s.indexOf(','))));
//        final Stream<WeatherDay> weatherDayStream = collect.keySet().stream().map(k -> weatherInfoCreator.apply(collect.get(k).toArray(new String[]{})));
//
//        weatherDays = weatherDayStream.collect(toList());
//        return weatherDays;

        if(weatherDays == null) {
            final Map<String, List<String>> collect =
                    Arrays.stream(stringSupplier.get().split("(\r\n)|(\n)"))
                            .filter(s -> !s.startsWith("#") && s.contains(","))
                            .collect(groupingBy(s -> s.substring(0, s.indexOf(','))))
                    ;
            weatherDays = collect.values().stream()
                    .map(weatherInfoCreator::apply).collect(toList());
        }
        return weatherDays;
    }
}
