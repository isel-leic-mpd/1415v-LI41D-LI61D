package isel.mpd.weather.data;

import isel.mpd.weather.WeatherDay;

import java.util.Collection;

/**
 * Interface for weather information repository.
 */
public interface WeatherDayRepository {
    Collection<WeatherDay> getWeatherDaysSupplier();
}
