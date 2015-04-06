package reflection;

import isel.mpd.weather.WeatherDay;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests with reflection API
 */
public class ReflectionTests {
    void dumpObjectMethods(Object o) {
        Class<?> c = o.getClass();

        for(Method m : c.getMethods()) {
            System.out.println(m.getName());
        }
    }

    @Test
    public void testDumpObjectMethods() {
        dumpObjectMethods("SLB");
    }

    @Test
    public void setPrivateField() throws IllegalAccessException, NoSuchFieldException {
        WeatherDay wd = new WeatherDay();

        Field f = wd.getClass().getDeclaredField("date");

        f.setAccessible(true);
        f.set(wd, "2015-04-06");

        System.out.println(f.get(wd));
    }


    @Test
    public void constructorTests() throws IllegalAccessException, NoSuchFieldException {
        WeatherDay wd = new WeatherDay();

        Constructor[] f = wd.getClass().getConstructors();

        Arrays.asList(f).stream().forEach(ReflectionTests::showConstructor);
    }

    @Test
    public void intClassIsNotTheIntegerClass() {
        assertFalse(Integer.class == int.class);
        assertTrue(Integer.TYPE == int.class);
    }

    private static void showConstructor(Constructor c) {
        System.out.println("--------------------------");
        System.out.println(c.getName());
        System.out.println("generic parameter types:" + Arrays.toString(c.getGenericParameterTypes()));
        System.out.println("parameter types:" + Arrays.toString(c.getParameterTypes()));
    }

}
