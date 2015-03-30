package weather;

import com.google.common.io.ByteStreams;
import org.junit.Test;
import weather.data.UrlStreamSupplier;

import java.io.*;
import java.text.MessageFormat;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by lfalcao on 25/03/2015.
 */
public class InputStreamSupplierTests {

    @Test
    public void shouldSupplyAValidInputStreamForCsvFormat() throws Exception {
        String str = validStreamForFormat("csv");

        assertTrue(str.contains("#Hourly information follows below the day in the following way:-"));
    }

    @Test
    public void shouldSupplyAValidInputStreamForJsonFormat() throws Exception {
        validStreamForFormat("json");

    }

    private String validStreamForFormat(String format) {
        // Arrange
        String url = MessageFormat.format("http://api.worldweatheronline.com/free/v2/past-weather.ashx?q=Lisbon&format={0}&key=44a2b619601959766a08667fa3891&date=2015-03-20&enddate=2015-03-25", format);

        UrlStreamSupplier urlSupplier = new UrlStreamSupplier(url);

        // Act
        InputStream is = urlSupplier.get();

        // Assert
        assertNotNull(is);
        String str = getStringFromInputStream(is);
        System.out.println(str);

        assertTrue(str.length() > 0);
        assertTrue(str.contains("2015-03-25"));


        return str;
    }


    private String getStringFromInputStream(InputStream is) {
        try {
            return new String(ByteStreams.toByteArray(is));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
//        StringBuilder buf = new StringBuilder();
//        try(BufferedReader reader = new BufferedReader(new InputStreamReader(is)) ) {
//            String str = null;
//            try {
//                while ((str = reader.readLine()) != null) {
//                    buf.append(str + "\n");
//                }
//            } catch (IOException e) {
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return buf.toString();
    }
}
