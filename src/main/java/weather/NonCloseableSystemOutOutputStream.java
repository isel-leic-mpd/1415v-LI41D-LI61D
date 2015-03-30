package weather;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by lfalcao on 23/03/2015.
 */
public class NonCloseableSystemOutOutputStream extends OutputStream {
    public final static NonCloseableSystemOutOutputStream out = new NonCloseableSystemOutOutputStream();


    private NonCloseableSystemOutOutputStream() {
    }

    @Override
    public void write(int b) throws IOException {
        System.out.write(b);
    }

    @Override
    public void close() throws IOException {

    }
}
