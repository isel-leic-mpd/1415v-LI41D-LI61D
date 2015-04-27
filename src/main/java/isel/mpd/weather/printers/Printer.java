package isel.mpd.weather.printers;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.function.Supplier;

/**
 * Created by lfalcao on 23/03/2015.
 */
public class Printer {


    public void print(Iterable<String> weatherData, Supplier<OutputStream> supOut) {
        try(PrintWriter w = new PrintWriter(supOut.get())) {
            for (String s : weatherData)
                w.println(s + "\n");
        }
    }
}
