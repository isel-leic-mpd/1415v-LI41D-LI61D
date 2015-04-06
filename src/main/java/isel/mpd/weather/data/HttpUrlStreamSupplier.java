package isel.mpd.weather.data;

import com.google.common.io.ByteStreams;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Supplier;

/**
 * Supplies a Stream from am HTTP Url.
 *
 * The stream is the result of making the request to the specified Url and contains the response.
 */
public class HttpUrlStreamSupplier implements Supplier<InputStream> {
    private final String url;

    public HttpUrlStreamSupplier(String url) {
        this.url = url;
    }

    public InputStream get() {
        HttpURLConnection urlConnection = null;

        InputStream connInputStream = null;
        try {
            URL uri = new URL(url);

            urlConnection = (HttpURLConnection) uri.openConnection();
            if(urlConnection.getResponseCode() != 200) {
                System.out.println("Server returned code " + urlConnection.getResponseCode());
                return null;
            }
            int size = urlConnection.getContentLength();
            System.out.printf("%d bytes available\n", size);

            connInputStream = urlConnection.getInputStream();
            // int read = connInputStream.read(data);
            byte[] data = ByteStreams.toByteArray(connInputStream);
            System.out.printf("Read %d bytes\n", data.length);
            return new ByteArrayInputStream(data);
            
            
        } catch (IOException e) {
            System.out.println(e);
            return null;
        } finally {
            if (connInputStream != null) {
                try {
                    connInputStream.close();
                } catch (IOException e) {
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
