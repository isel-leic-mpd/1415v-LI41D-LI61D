package isel.mpd.weather;

/**
 * Created by lfalcao on 05/04/2015.
 */
public class HourlyInfo {
    int hour;
    int tempC;
    int tempF;
    String weatherIconUrl;
    String weatherDesc;

    //,windspeedMiles,windspeedKmph,winddirdegree,winddir16point,weatherCode,weatherIconUrl,weatherDesc,precipMM,humidity,visibilityKm,pressureMB,cloudcover,HeatIndexC,HeatIndexF,DewPointC,DewPointF,WindChillC,WindChillF,WindGustMiles,WindGustKmph,FeelsLikeC,FeelsLikeF


    public HourlyInfo(int hour, int tempC, int tempF, String weatherIconUrl, String weatherDesc) {
        this.weatherIconUrl = weatherIconUrl;
        this.hour = hour;
        this.tempC = tempC;
        this.tempF = tempF;
        this.weatherDesc = weatherDesc;
    }
}
