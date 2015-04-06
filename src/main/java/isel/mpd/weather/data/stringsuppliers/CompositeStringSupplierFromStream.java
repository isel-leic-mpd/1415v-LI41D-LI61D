package isel.mpd.weather.data.stringsuppliers;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

/**
 * Created by lfalcao on 06/04/2015.
 */
public class CompositeStringSupplierFromStream extends StringSupplier {
    private final Stream<StringSupplier> stringSuppliers;

    public CompositeStringSupplierFromStream(Stream<StringSupplier> stringSuppliers) {
        this.stringSuppliers = stringSuppliers;
    }

    @Override
    public String get() {
        return stringSuppliers.map(StringSupplier::get).collect(joining("\r\n"));
    }
}
