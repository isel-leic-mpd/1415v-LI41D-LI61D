package reflection;

import isel.mpd.weather.WeatherDay;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Supplier;

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

        Field f = wd.getClass().getDeclaredField("day");

        f.setAccessible(true);
        f.set(wd, "Mon");

        System.out.println(f.get(wd));
    }

}
