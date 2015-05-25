package isel.mpd.algorithms;


import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by lfalcao on 20/05/2015.
 */
public class StreamCreation {
    @Test
    public void shouldCreateAStreamFromValues() throws Exception {
        final Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 6);

        final Stream<String> THE_NAMES = Stream.of("Sport", "Lisboa", "e", "Benfica");
    }


    @Test
    public void shouldCreateAStreamFromArrays() throws Exception {
        final IntStream integerStream = Arrays.stream(new int[]{1, 2, 3, 4, 5, 6});

    }

    @Test
    public void shouldCreateAStreamFromAnIterable() throws Exception {
        Iterable<String> iter = new ArrayList<String>();

        final Stream<String> stream = StreamSupport.stream(iter.spliterator(), true);
    }


    @Test
    public void shouldCreateAStreamFromAFile() throws Exception {

        try (Stream<String> lines = Files.lines(Paths.get(ClassLoader.getSystemClassLoader().getResource("weatherReport.txt").getPath().substring(1)))) {
            lines.filter(s -> !s.trim().startsWith("#"))
                    .flatMapToInt(s -> s.chars())
                    .distinct()
                    .forEach(c -> System.out.print((char)c));
        }
    }


}
