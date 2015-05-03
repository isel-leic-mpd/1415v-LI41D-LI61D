package isel.mpd.weather.data;

import isel.mpd.weather.WeatherDay;

import java.util.Collection;
import java.util.List;

/**
 * Interface for weather information repository.
 */
public interface WeatherDayRepository {
    List<WeatherDay> getWeatherDays();
}
