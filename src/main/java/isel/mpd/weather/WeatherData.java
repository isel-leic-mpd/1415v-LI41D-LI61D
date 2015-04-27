package isel.mpd.weather;

import java.util.ArrayList;

/**
 */
public class WeatherData {
    private ArrayList<WeatherDetails> data = new ArrayList<>();

    String d;
    int x;

    public WeatherData() {
        data = new ArrayList<>();
    }

    public ArrayList<WeatherDetails> getData() {
        return data;
    }
}
