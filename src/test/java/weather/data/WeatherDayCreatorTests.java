package weather.data;

import isel.mpd.weather.WeatherDay;
import isel.mpd.weather.data.WeatherDayCsvCreator;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lfalcao on 05/04/2015.
 */
public class WeatherDayCreatorTests {
    @Test
    public void shouldCreateAWheatherDayFromCsvStrings() throws Exception {
        // Arrange
        final Collection<String> wdStrings = Arrays.asList("2015-03-28,22,71,12,54,07:28 AM,07:56 PM,01:49 PM,03:20 AM",
                "2015-03-28,0,12,53,10,17,355,N,113,http://cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0008_clear_sky_night.png,Clear ,0.0,88,10,1031,5,12,53,10,49,10,49,19,30,10,49",
                "2015-03-28,300,11,52,9,15,354,N,113,http://cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0008_clear_sky_night.png,Clear ,0.0,92,10,1030,18,11,52,10,50,9,49,17,28,9,49",
                "2015-03-28,600,11,52,10,15,353,N,116,http://cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0004_black_low_cloud.png,Partly Cloudy ,0.0,93,10,1030,27,11,52,10,50,9,48,18,28,9,48",
                "2015-03-28,900,16,60,12,19,356,N,113,http://cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0001_sunny.png,Sunny,0.0,75,10,1030,11,16,60,11,53,16,60,13,22,16,60",
                "2015-03-28,1200,22,71,15,23,350,N,113,http://cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0001_sunny.png,Sunny,0.0,57,10,1031,12,25,76,13,55,22,71,17,27,22,71",
                "2015-03-28,1500,21,70,16,26,346,NNW,113,http://cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0001_sunny.png,Sunny,0.0,52,10,1030,4,21,70,11,52,21,70,19,30,21,70",
                "2015-03-28,1800,16,62,16,26,345,NNW,113,http://cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0001_sunny.png,Sunny,0.0,65,10,1029,9,16,62,10,49,16,62,22,36,16,62",
                "2015-03-28,2100,12,54,14,22,349,NNW,113,http://cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0008_clear_sky_night.png,Clear ,0.0,80,10,1031,1,12,54,9,48,10,50,24,38,10,50"
        );

        final WeatherDayCsvCreator weatherDayCsvCreator = new WeatherDayCsvCreator();

        // Act
        final WeatherDay wd = weatherDayCsvCreator.apply(wdStrings);

        // Assert
        assertNotNull(wd);
        assertEquals("2015-03-28", wd.getDate());
        assertEquals(22, wd.getMaxTempC());

        assertNotNull(wd.getHourlyInfo());

        assertEquals(8, wd.getHourlyInfo().size());
    }
}
