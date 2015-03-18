package isel.mpd.weather;

/**
 * Instances of this class represent weather information for one day.
 */
public final class Day {

    private final String
            date,
            day,
            sunrise,
            sunset,
            moonrise,
            moonset;
    private final int
            maxtempC,
            maxtempF,
            mintempC,
            mintempF,
            dayofweek,
            month,
            year
    ;

    public Day(String date, String day, String sunrise, String sunset, String moonrise, String moonset, int maxtempC, int maxtempF, int mintempC, int mintempF, int dayofweek, int month, int year) {
        this.date = date;
        this.day = day;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.moonrise = moonrise;
        this.moonset = moonset;
        this.maxtempC = maxtempC;
        this.maxtempF = maxtempF;
        this.mintempC = mintempC;
        this.mintempF = mintempF;
        this.dayofweek = dayofweek;
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString()
    {
        return "Date: " + date + '\n' + "Max Temperature = " + maxtempC + "(ºC) | " + maxtempF + "(ºF)\n"
                + "Min Temperature = " + mintempC + "(ºC) | " + mintempF + "(ºF)\n"
                + "Day/Night Cycle : " + sunrise + " (Sunrise) -> " + sunset + " (Sunset) -> " + moonrise
                + " (Moonrise) -> " + moonset + " Moonset)";

    }
    public static class Builder{

        private final String date;

        private String
                sunrise,
                sunset,
                moonrise,
                moonset;

        private int
                maxtempC,
                maxtempF,
                mintempC,
                mintempF;

        public Builder(String date)
        {
            this.date = date;
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
    }
}
