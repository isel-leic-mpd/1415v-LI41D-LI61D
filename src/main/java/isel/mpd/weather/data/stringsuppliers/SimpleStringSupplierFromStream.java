package isel.mpd.weather.data.stringsuppliers;

import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

/**
 * Simple {@link Supplier} for strings given an {@link InputStream} with bytes encoded in
 * UTF-8..
 */
public class SimpleStringSupplierFromStream extends StringSupplier {
    private final Supplier<InputStream> inputStreamSupplier;

    /**
     * Initializes a newly created {@code SimpleStringSupplierFromStream} object
     * given a {@link Supplier} for {@link InputStream}
     * @param inputStreamSupplier the {@link Supplier} for {@link InputStream}
     *
     */
    public SimpleStringSupplierFromStream(Supplier<InputStream> inputStreamSupplier) {
        this.inputStreamSupplier = inputStreamSupplier;
    }

    @Override
    public String get() {
        try {
            return new String(ByteStreams.toByteArray(inputStreamSupplier.get()));
        } catch (IOException e) {
            // This has to be changed
            e.printStackTrace();
            return "";
        }
    }
}
