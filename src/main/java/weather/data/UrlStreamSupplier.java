package weather.data;

import com.google.common.io.ByteStreams;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.function.Supplier;

/**
 * Created by lfalcao on 25/03/2015.
 */
public class UrlStreamSupplier implements Supplier<InputStream> {
    private final String url;

    public UrlStreamSupplier(String url) {

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
            System.out.printf("%d bytes available", size);
            byte[] data = new byte[size];

            connInputStream = urlConnection.getInputStream();
            // int read = connInputStream.read(data);
            ByteStreams.readFully(connInputStream, data);
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
