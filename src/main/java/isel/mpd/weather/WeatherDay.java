package isel.mpd.weather;

import java.util.Collection;

/**
 * Instances of this class represent weather information for one day.
 */
public final class WeatherDay {
    private String
            date,
            sunrise,
            sunset,
            moonrise,
            moonset;

    private int
            maxtempC,
            maxtempF,
            mintempC,
            mintempF
    ;

    private Collection<HourlyInfo> hourlyInfo;

    public WeatherDay(String date, String sunrise, String sunset, String moonrise, String moonset, int maxtempC, int maxtempF, int mintempC, int mintempF, Collection<HourlyInfo> hourlyInfos) {
        this.date = date;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.moonrise = moonrise;
        this.moonset = moonset;
        this.maxtempC = maxtempC;
        this.maxtempF = maxtempF;
        this.mintempC = mintempC;
        this.mintempF = mintempF;
        this.hourlyInfo = hourlyInfos;
    }

    public WeatherDay() {

    }

    @Override
    public String toString()
    {
        return "Date: " + date + '\n' + "Max Temperature = " + maxtempC + "(ºC) | " + maxtempF + "(ºF)\n"
                + "Min Temperature = " + mintempC + "(ºC) | " + mintempF + "(ºF)\n"
                + "WeatherDay/Night Cycle : " + sunrise + " (Sunrise) -> " + sunset + " (Sunset) -> " + moonrise
                + " (Moonrise) -> " + moonset + " Moonset)";

    }

    public String getDate() {
        return date;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getMoonrise() {
        return moonrise;
    }

    public String getMoonset() {
        return moonset;
    }

    public int getMaxtempC() {
        return maxtempC;
    }

    public int getMaxtempF() {
        return maxtempF;
    }

    public int getMintempC() {
        return mintempC;
    }

    public int getMintempF() {
        return mintempF;
    }

    public Collection<HourlyInfo> getHourlyInfo() {
        return hourlyInfo;
    }

}
