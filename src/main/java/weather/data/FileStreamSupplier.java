package weather.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.function.Supplier;

/**
 * Created by lfalcao on 25/03/2015.
 */
class FileStreamSupplier implements Supplier<InputStream> {
    private String path;

    public FileStreamSupplier(String path) {
        this.path = path;
    }

    /**
     * Provides a file input stream, or {@code null} if some problem ocurrs.
     * @return the file input stream, or {@code null} if some problem ocurrs.
     */
    @Override
    public InputStream get() {
        try {
            return new FileInputStream(path);
        } catch (FileNotFoundException e) {
            System.out.println(e);
            return null;
        }
    }
}
