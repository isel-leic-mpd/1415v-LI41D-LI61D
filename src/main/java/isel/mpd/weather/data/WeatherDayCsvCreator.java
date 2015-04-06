package isel.mpd.weather.data;

import isel.mpd.weather.HourlyInfo;
import isel.mpd.weather.WeatherDay;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * Created by lfalcao on 05/04/2015.
 */
public class WeatherDayCsvCreator implements Function<Collection<String>, WeatherDay> {
    private static final int DATE_POS = 0;
    private static final int SUNRISE_POS = 5;
    private static final int SUNSET_POS = 6;
    private static final int MOONRISE_POS = 7;
    private static final int MOONSET_POS = 8;
    private static final int MAXTEMPC_POS = 1;
    private static final int MAXTEMPF_POS = 2;
    private static final int MINTEMPC_POS = 3;
    private static final int MINTEMPF_POS = 4;
    private static final int HOUR_POS = 1;
    private static final int TEMPC_POS = 2;
    private static final int TEMPF_POS = 3;
    private static final int ICON_URL_POS = 9;
    private static final int DESCRIPTION_POS = 10;

    @Override
    public WeatherDay apply(Collection<String> csvStrings) {
        // parse weather day in the first line
        String [] wdStrings = csvStrings.stream().findFirst().get().split(",");
        // date,maxtempC,maxtempF,mintempC,mintempF,sunrise,sunset,moonrise,moonset
        //String date, String sunrise, String sunset, String moonrise, String moonset,
        // int maxtempC, int maxtempF, int mintempC, int mintempF, int dayofweek, int month, int year
        final List<HourlyInfo> hourlyInfos = csvStrings.stream().skip(1).map(WeatherDayCsvCreator::createHourlyInfo).collect(toList());

        return new WeatherDay(wdStrings[DATE_POS], wdStrings[SUNRISE_POS], wdStrings[SUNSET_POS],
                wdStrings[MOONRISE_POS], wdStrings[MOONSET_POS], Integer.parseInt(wdStrings[MAXTEMPC_POS]),
                Integer.parseInt(wdStrings[MAXTEMPF_POS]), Integer.parseInt(wdStrings[MINTEMPC_POS]),
                Integer.parseInt(wdStrings[MINTEMPF_POS]), hourlyInfos
                );
    }

    private static HourlyInfo createHourlyInfo(String hourInfoString) {
        String [] hiStrings = hourInfoString.split(",");
        return new HourlyInfo(
                Integer.parseInt(hiStrings[HOUR_POS]),
                Integer.parseInt(hiStrings[TEMPC_POS]),
                Integer.parseInt(hiStrings[TEMPF_POS]),
                hiStrings[ICON_URL_POS],
                hiStrings[DESCRIPTION_POS]
                );
    }
}
