package weather.data;

import isel.mpd.weather.WeatherDay;
import isel.mpd.weather.data.HttpUrlStreamSupplier;
import isel.mpd.weather.data.WeatherDayCsvCreator;
import isel.mpd.weather.data.WeatherDayRepositoryCsv;
import isel.mpd.weather.data.stringsuppliers.CompositeStringSupplierFromStream;
import isel.mpd.weather.data.stringsuppliers.SimpleStringSupplierFromStream;
import isel.mpd.weather.data.stringsuppliers.StringSupplier;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link isel.mpd.weather.data.WeatherDayRepository}
 */
public class WeatherDayRepositoryTests {

    @Test
    public void shouldGetWeathersDayFromUrlSupplierAndCsvFormatWithSimpleStringSupplier() throws Exception {
        // Arrange
        final String startDate = "2015-03-20";
        final String endDate = "2015-03-25";
        String url = "http://api.worldweatheronline.com/free/v2/past-weather.ashx?q=Lisbon&format=csv&key=44a2b619601959766a08667fa3891&date=" + startDate + "&enddate=" + endDate;
        HttpUrlStreamSupplier urlSupplier = new HttpUrlStreamSupplier(url);

        getWeatherDayAndAssertHelper(new SimpleStringSupplierFromStream(urlSupplier),startDate, endDate, 6);
    }

    @Test
    public void shouldGetWeathersDayFromUrlSupplierAndCsvFormatWithCompositeStringSupplier() throws Exception {
        // Arrange
        final String startDate = "2015-03-14";
        String url1 = "http://api.worldweatheronline.com/free/v2/past-weather.ashx?q=Lisbon&format=csv&key=44a2b619601959766a08667fa3891&date=" + startDate + "&enddate=2015-03-19";
        StringSupplier stringSupplier1 = new SimpleStringSupplierFromStream(new HttpUrlStreamSupplier(url1));

        final String endDate = "2015-03-25";
        String url2 = "http://api.worldweatheronline.com/free/v2/past-weather.ashx?q=Lisbon&format=csv&key=44a2b619601959766a08667fa3891&date=2015-03-20&enddate=" + endDate;
        StringSupplier stringSupplier2 = new SimpleStringSupplierFromStream(new HttpUrlStreamSupplier(url2));

        getWeatherDayAndAssertHelper(new CompositeStringSupplierFromStream(Arrays.asList(stringSupplier1, stringSupplier2).stream()), startDate, endDate,12);
    }

    private void getWeatherDayAndAssertHelper(StringSupplier strSupplier, String startDate, String endDate, int numDays) {
        // Arrange
        WeatherDayRepositoryCsv wsr = new WeatherDayRepositoryCsv(strSupplier, new WeatherDayCsvCreator());

        // Act
        final Collection<WeatherDay> weatherDays = wsr.getWeatherDays();

        // Assert
        assertNotNull(weatherDays);
        assertEquals(numDays, weatherDays.size());
        weatherDays.stream().forEach(wd -> validateWeatherDay(wd, startDate, endDate));
    }


    private static void validateWeatherDay(WeatherDay wd, String startDate, String endDate) {
        assertNotNull(wd);
        assertTrue(startDate.compareTo(wd.getDate()) <= 0);
        assertTrue(endDate.compareTo(wd.getDate()) >= 0);

        assertNotNull(wd.getHourlyInfo());

        assertEquals(8, wd.getHourlyInfo().size());

    }
}
