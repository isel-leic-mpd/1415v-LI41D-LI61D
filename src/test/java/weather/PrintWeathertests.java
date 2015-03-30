package weather;

import isel.mpd.weather.printers.Printer;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 */
public class PrintWeatherTests {
    @Test
    public void testName() {
        Printer printer = new Printer();

        printer.print(null, PrintWeatherTests::createConsoleOutputStream);

        printer.print(null, PrintWeatherTests::createFileOutputStream);

    }

    private static OutputStream createConsoleOutputStream() {
        return NonCloseableSystemOutOutputStream.out;
    }

    private static OutputStream createFileOutputStream() {
        try {
            FileOutputStream out = new FileOutputStream("out.txt");

        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}