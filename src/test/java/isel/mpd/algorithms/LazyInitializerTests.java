package isel.mpd.algorithms;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.function.Supplier;

import static isel.mpd.misc.LazyInitializer.lazily;
import static org.junit.Assert.assertEquals;

/**
 * Created by lfalcao on 27/04/2015.
 */
public class LazyInitializerTests {
    static class TestLazy {
        private Supplier<Integer> field1Supplier = lazily(this::getField1Value);

        private Supplier<String> field2Supplier = lazily(this::getField2Value);

        private Supplier<List<String>> field3Supplier = this::getField3Value;

        private int field1InitCount = 0;
        private int field2InitCount = 0;
        private int field3InitCount = 0;


        private int getField1Value() {
            field1InitCount++;
            return 0;
        }

        private String getField2Value() {
            field2InitCount++;
            return "";
        }

        private List<String> getField3Value() {
            field3InitCount++;
            return null;
        }

        public int getField1() {
            return field1Supplier.get();
        }

        public String getField2() {
            return field2Supplier.get();
        }


        public List<String> getField3() {
            return field3Supplier.get();
        }
    }

    private TestLazy testLazy;


    @Before
    public void setUp() throws Exception {
        testLazy = new TestLazy();

    }

    @Test
    public void shouldLazyInitializeField1OnlyOnce() throws Exception {
        // Arrange
        testLazy.getField1();
        testLazy.getField1();

        // Act
        testLazy.getField1();

        assertEquals(1, testLazy.field1InitCount);
    }

    @Test
    public void shouldLazyInitializeField2OnlyOnce() throws Exception {
        // Arrange
        testLazy.getField2();
        testLazy.getField2();

        // Act
        testLazy.getField2();

        assertEquals(1, testLazy.field2InitCount);
    }

    @Test
    public void shouldInitializeField3SeveralTimes() throws Exception {
        // Arrange
        testLazy.getField3();
        testLazy.getField3();

        // Act
        testLazy.getField3();

        assertEquals(3, testLazy.field3InitCount);


    }
}
